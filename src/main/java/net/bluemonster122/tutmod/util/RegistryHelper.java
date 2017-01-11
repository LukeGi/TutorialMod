package net.bluemonster122.tutmod.util;

import net.bluemonster122.tutmod.block.base.BlockBase;
import net.bluemonster122.tutmod.block.base.BlockEnum;
import net.bluemonster122.tutmod.item.ItemBlockEnum;
import net.minecraft.item.ItemBlock;

public class RegistryHelper {
  public static ItemBlock getItemBlockFor(BlockBase block) {
    return block instanceof BlockEnum ? new ItemBlockEnum((BlockEnum) block) : new ItemBlock(block);
  }
}
