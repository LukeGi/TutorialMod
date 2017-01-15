package net.bluemonster122.tutmod.tileentity;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class CapabilityHelper {
  public static boolean isInvCap(@Nonnull Capability<?> capability) {
    return capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
  }
}
