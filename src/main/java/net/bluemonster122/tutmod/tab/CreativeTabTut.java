package net.bluemonster122.tutmod.tab;

import net.bluemonster122.tutmod.lib.Names;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CreativeTabTut extends CreativeTabs {
  public CreativeTabTut() {
    super(Names.TUT_TAB);
  }
  
  @Override
  public ItemStack getTabIconItem() {
    return new ItemStack(Blocks.AIR);
  }
}
