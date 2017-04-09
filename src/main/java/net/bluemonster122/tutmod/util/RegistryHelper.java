package net.bluemonster122.tutmod.util;

import net.bluemonster122.tutmod.block.base.BlockBase;
import net.minecraft.item.ItemBlock;

public class RegistryHelper {
  public static ItemBlock getItemBlockFor(BlockBase block) {
    return block.getItemBlock();
  }
}
