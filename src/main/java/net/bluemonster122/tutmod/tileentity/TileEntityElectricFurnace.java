package net.bluemonster122.tutmod.tileentity;

import net.bluemonster122.tutmod.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityElectricFurnace extends TileEntity implements ITickable, IItemHandler {
  public TileEntityElectricFurnace() {
    burntime = -1;
  }
  
  private int burntime;
  private ItemStack input = ItemStack.EMPTY;
  private ItemStack output = ItemStack.EMPTY;
  
  @Override
  public void update() {
    if (!world.isRemote) {
      if (!FurnaceRecipes.instance().getSmeltingResult(input).isEmpty()) {
        if (burntime == -1) {
          burntime = 100;
        }
        if (burntime == 0) {
          if (smeltItem()) {
            burntime = -1;
          }
        }
        if (burntime > 0)
          burntime--;
      } else {
        burntime = -1;
      }
      IBlockState state = world.getBlockState(pos);
      if (burntime > -1 && !state.getValue(BlockFurnace.ACTIVE)){
        world.setBlockState(pos, state.withProperty(BlockFurnace.ACTIVE, true), 3);
        markDirty();
      } else if (burntime == -1 && state.getValue(BlockFurnace.ACTIVE)) {
        world.setBlockState(pos, state.withProperty(BlockFurnace.ACTIVE, false), 3);
        markDirty();
      }
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
    }
    return false;
  }
  
  @Override
  public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
    return oldState.getBlock() != newSate.getBlock();
  }
  
  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
  }
  
  @Nullable
  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    return capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this) : null;
  }
  
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound.setTag("inputInventory", input.serializeNBT());
    compound.setTag("outputInventory", output.serializeNBT());
    return super.writeToNBT(compound);
  }
  
  @Override
  public void readFromNBT(NBTTagCompound compound) {
    input = new ItemStack(compound.getCompoundTag("inputInventory"));
    output = new ItemStack(compound.getCompoundTag("outputInventory"));
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
}
