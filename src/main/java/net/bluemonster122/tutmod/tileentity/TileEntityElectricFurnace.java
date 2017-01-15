package net.bluemonster122.tutmod.tileentity;

import net.bluemonster122.tutmod.block.BlockElectricFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityElectricFurnace extends TileEntityMachineBase implements IHaveBasicInventory {
  public TileEntityElectricFurnace() {
    burn_time = -1;
  }
  
  private int burn_time;
  private ItemStackHandler inventory = new ItemStackHandler(2);
  
  @Override
  protected void tickServer() {
    if (!FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(0)).isEmpty() && getEnergy().getEnergyStored() > 0) {
      if (burn_time == -1) {
        burn_time = 100;
      }
      if (burn_time == 0) {
        if (smeltItem()) {
          burn_time = -1;
          getEnergy().extractEnergy(1500, false);
          markDirty();
        }
      }
      if (burn_time > 0) {
        burn_time--;
        getEnergy().extractEnergy(100, false);
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
  }
  
  @Override
  protected void tickClient() {
    
  }
  
  @Override
  protected EnergyStorage makeEnergy() {
    return new EnergyStorage(100000);
  }
  
  private boolean smeltItem() {
    ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(0));
    if (inventory.getStackInSlot(1).isEmpty()) {
      inventory.setStackInSlot(1, result.copy());
      world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY(), pos.getZ(), (int) (FurnaceRecipes.instance().getSmeltingExperience(result))));
      inventory.getStackInSlot(0).shrink(1);
      return true;
    } else if (canCombine(inventory.getStackInSlot(1), result)) {
      inventory.getStackInSlot(1).grow(1);
      world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY(), pos.getZ(), (int) (FurnaceRecipes.instance().getSmeltingExperience(result))));
      inventory.getStackInSlot(0).shrink(1);
      return true;
    }
    return false;
  }
  
  private static boolean canCombine(ItemStack thisStack, ItemStack thatStack) {
    return thisStack.getItem() == thatStack.getItem() && (thisStack.getMetadata() == thatStack.getMetadata() && (thisStack.getCount() <= thisStack.getMaxStackSize() && ItemStack.areItemStackTagsEqual(thisStack, thatStack)));
  }
  
  public void dropInv() {
    if (world.isRemote) return;
    for (int i = 0; i < inventory.getSlots(); i++) {
      world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i)));
    }
  }
  
  public int getBurntime() {
    return burn_time;
  }
  
  @Override
  public ItemStackHandler getInventory() {
    return inventory;
  }
  
  @Nullable
  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    T t = getInvCapability(capability);
    return t == null ? super.getCapability(capability, facing) : t;
  }
  
  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return CapabilityHelper.isInvCap(capability) || super.hasCapability(capability, facing);
  }
  
  @Override
  public void readFromNBT(NBTTagCompound compound) {
    burn_time = compound.getInteger("burntime");
    readInvFromNBT(compound);
    super.readFromNBT(compound);
  }
  
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound.setInteger("burntime", burn_time);
    return super.writeToNBT(writeInvToNBT(compound));
  }
}
