package net.bluemonster122.tutmod.tileentity;

import net.bluemonster122.tutmod.block.BlockElectricFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityElectricFurnace extends TileEntity implements ITickable, IItemHandlerModifiable, IEnergyStorage {
  public TileEntityElectricFurnace() {
    burn_time = -1;
  }
  
  private int burn_time;
  private ItemStack input = ItemStack.EMPTY;
  private ItemStack output = ItemStack.EMPTY;
  private EnergyStorage energy = new EnergyStorage(10000);
  
  @Override
  public void update() {
    if (!world.isRemote) {
      if (!FurnaceRecipes.instance().getSmeltingResult(input).isEmpty() && energy.getEnergyStored() > 0) {
        if (burn_time == -1) {
          burn_time = 100;
        }
        if (burn_time == 0) {
          if (smeltItem()) {
            burn_time = -1;
            energy.extractEnergy(150, false);
            markDirty();
          }
        }
        if (burn_time > 0) {
          burn_time--;
          energy.extractEnergy(10, false);
        }
      } else {
        burn_time = -1;
      }
      IBlockState state = world.getBlockState(pos);
      if (burn_time > -1 && !state.getValue(BlockElectricFurnace.ACTIVE)) {
        world.setBlockState(pos, state.withProperty(BlockElectricFurnace.ACTIVE, true), 3);
        markDirty();
      } else if (burn_time == -1 && state.getValue(BlockElectricFurnace.ACTIVE)) {
        world.setBlockState(pos, state.withProperty(BlockElectricFurnace.ACTIVE, false), 3);
        markDirty();
      }
      world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }
  }
  
  private boolean smeltItem() {
    ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
    if (output.isEmpty()) {
      output = result.copy();
      world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY(), pos.getZ(), (int) (FurnaceRecipes.instance().getSmeltingExperience(result) % 1)));
      input.shrink(1);
      return true;
    } else if (canCombine(output, result)) {
      output.grow(1);
      world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY(), pos.getZ(), (int) (FurnaceRecipes.instance().getSmeltingExperience(result) % 1)));
      input.shrink(1);
      return true;
    }
    return false;
  }
  
  @Override
  public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
    return oldState.getBlock() != newSate.getBlock();
  }
  
  
  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {
    return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    this.readFromNBT(pkt.getNbtCompound());
  }
  
  @Nonnull
  @Override
  public NBTTagCompound getUpdateTag() {
    return writeToNBT(super.getUpdateTag());
  }
  
  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) || capability.equals(CapabilityEnergy.ENERGY);
  }
  
  @Nullable
  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability.equals(CapabilityEnergy.ENERGY)){
      return CapabilityEnergy.ENERGY.cast(this);
    }
    if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)){
      return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this);
    }
    return null;
  }
  
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound.setTag("inputInventory", input.serializeNBT());
    compound.setTag("outputInventory", output.serializeNBT());
    compound.setInteger("energy", energy.getEnergyStored());
    compound.setInteger("burntime", burn_time);
    return super.writeToNBT(compound);
  }
  
  @Override
  public void readFromNBT(NBTTagCompound compound) {
    input = new ItemStack(compound.getCompoundTag("inputInventory"));
    output = new ItemStack(compound.getCompoundTag("outputInventory"));
    energy = new EnergyStorage(10000);
    energy.receiveEnergy(compound.getInteger("energy"), false);
    burn_time = compound.getInteger("burntime");
    super.readFromNBT(compound);
  }
  
  @Override
  public int getSlots() {
    return 2;
  }
  
  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {
    switch (slot) {
      case 0:
        return input;
      case 1:
        return output;
    }
    return ItemStack.EMPTY;
  }
  
  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (stack.isEmpty())
      return ItemStack.EMPTY;
    if (slot != 0) return stack;
    
    int m;
    if (!input.isEmpty()) {
      if (!ItemHandlerHelper.canItemStacksStack(stack, input))
        return stack;
      
      m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot)) - input.getCount();
      
      if (stack.getCount() <= m) {
        if (!simulate) {
          ItemStack copy = stack.copy();
          copy.grow(input.getCount());
          input = copy;
          markDirty();
        }
        
        return ItemStack.EMPTY;
      } else {
        // copy the stack to not modify the original one
        stack = stack.copy();
        if (!simulate) {
          ItemStack copy = stack.splitStack(m);
          copy.grow(input.getCount());
          input = copy;
          markDirty();
          return stack;
        } else {
          stack.shrink(m);
          return stack;
        }
      }
    } else {
      m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot));
      if (m < stack.getCount()) {
        // copy the stack to not modify the original one
        stack = stack.copy();
        if (!simulate) {
          input = stack.splitStack(m);
          markDirty();
          return stack;
        } else {
          stack.shrink(m);
          return stack;
        }
      } else {
        if (!simulate) {
          input = stack;
          markDirty();
        }
        return ItemStack.EMPTY;
      }
    }
  }
  
  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    if (amount == 0 || output.isEmpty() || slot != 1)
      return ItemStack.EMPTY;
    
    if (simulate) {
      if (output.getCount() < amount) {
        return output.copy();
      } else {
        ItemStack copy = output.copy();
        copy.setCount(amount);
        return copy;
      }
    } else {
      int m = Math.min(output.getCount(), amount);
      
      ItemStack decrStackSize = output.copy();
      output.shrink(m);
      markDirty();
      return decrStackSize;
    }
  }
  
  @Override
  public int getSlotLimit(int slot) {
    return slot == 0 ? input.getMaxStackSize() : slot == 1 ? output.getMaxStackSize() : 0;
  }
  
  private static boolean canCombine(ItemStack thisStack, ItemStack thatStack) {
    return thisStack.getItem() == thatStack.getItem() && (thisStack.getMetadata() == thatStack.getMetadata() && (thisStack.getCount() <= thisStack.getMaxStackSize() && ItemStack.areItemStackTagsEqual(thisStack, thatStack)));
  }
  
  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    return energy.receiveEnergy(maxReceive, simulate);
  }
  
  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    return energy.extractEnergy(maxExtract, simulate);
  }
  
  @Override
  public int getEnergyStored() {
    return energy.getEnergyStored();
  }
  
  @Override
  public int getMaxEnergyStored() {
    return energy.getMaxEnergyStored();
  }
  
  @Override
  public boolean canExtract() {
    return false;
  }
  
  @Override
  public boolean canReceive() {
    return true;
  }
  
  public void dropInv() {
    if (world.isRemote) return;
    world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), input));
    world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), output));
  }
  
  @Override
  public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
    if (slot == 0) input = stack;
  }
  
  public int getBurntime() {
    return burn_time;
  }
}
