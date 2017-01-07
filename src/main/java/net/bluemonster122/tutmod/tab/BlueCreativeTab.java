package net.bluemonster122.tutmod.tab;

import net.bluemonster122.tutmod.lib.Names;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class BlueCreativeTab extends CreativeTabs {
  public BlueCreativeTab() {
    super(Names.BLUE_TAB);
  }
  
  @Override
  public ItemStack getTabIconItem() {
    return new ItemStack(Blocks.AIR);
  }
}
