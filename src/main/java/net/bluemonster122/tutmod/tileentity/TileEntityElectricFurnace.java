package net.bluemonster122.tutmod.tileentity;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

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
    if (FurnaceRecipes.instance().getSmeltingResult(input) != ItemStack.EMPTY) {
      if (burntime == -1)
        burntime = 100;
      if (burntime > 0)
        burntime--;
      if (burntime == 0)
        if (smeltItem()) {
          burntime--;
        }
    } else {
      burntime = -1;
    }
  }
  
  private boolean smeltItem() {
    ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
    if (insertItem(1, result, true) == ItemStack.EMPTY) {
      insertItem(1, result, false);
      world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY(), pos.getZ(), (int) (FurnaceRecipes.instance().getSmeltingExperience(result) % 1)));
      input.shrink(1);
      return true;
    }
    return false;
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
    if (slot == 0) {
      if (input.isEmpty()) {
        input = stack;
        return ItemStack.EMPTY;
      } else if (canCombine(input, stack)) {
        int i = stack.getMaxStackSize() - input.getCount();
        int j = Math.min(stack.getCount(), i);
        stack.shrink(j);
        if (!simulate)
          input.grow(j);
        return stack;
      }
    }
    return stack;
  }
  
  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    if (amount == 0 || output.isEmpty()) return ItemStack.EMPTY;
    
    if (amount >= output.getCount()) {
      return output;
    }
    if (amount < output.getCount()) {
      if (!simulate)
        output.shrink(amount);
      ItemStack ret = output.copy();
      ret.setCount(amount);
      return ret;
    }
    return ItemStack.EMPTY;
  }
  
  @Override
  public int getSlotLimit(int slot) {
    return slot == 0 ? input.getMaxStackSize() : slot == 1 ? output.getMaxStackSize() : 0;
  }
  
  private static boolean canCombine(ItemStack thisStack, ItemStack thatStack) {
    return thisStack.getItem() == thatStack.getItem() && (thisStack.getMetadata() == thatStack.getMetadata() && (thisStack.getCount() <= thisStack.getMaxStackSize() && ItemStack.areItemStackTagsEqual(thisStack, thatStack)));
  }
}
