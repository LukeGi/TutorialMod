package net.bluemonster122.tutmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IHaveBasicInventory {
  ItemStackHandler getInventory();
  
  @Nullable
  default <T> T getInvCapability(@Nonnull Capability<T> capability) {
    return CapabilityHelper.isInvCap(capability) ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getInventory()) : null;
  }
  
  default NBTTagCompound writeInvToNBT(NBTTagCompound compound) {
    compound.setTag("inventory", getInventory().serializeNBT());
    return compound;
  }
  
  default void readInvFromNBT(NBTTagCompound compound) {
    getInventory().deserializeNBT(compound.getCompoundTag("inventory"));
  }
}
