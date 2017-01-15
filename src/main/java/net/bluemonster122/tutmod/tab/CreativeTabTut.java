package net.bluemonster122.tutmod.tab;

import net.bluemonster122.tutmod.lib.Names;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class CreativeTabTut extends CreativeTabs {
  public CreativeTabTut() {
    super(Names.TUT_TAB);
  }
  
  @Override
  public ItemStack getTabIconItem() {
    return new ItemStack(Blocks.AIR);
  }
  
  @SideOnly(Side.CLIENT)
  @Override
  public void displayAllRelevantItems(NonNullList<ItemStack> list) {
    ForgeRegistries.ITEMS.getEntries().stream().filter(e -> e.getValue() instanceof IInTab || (e.getValue() instanceof ItemBlock && ((ItemBlock) e.getValue()).getBlock() instanceof IInTab)).map(Map.Entry::getValue).forEach(item -> item.getSubItems(item, this, list));
  }
}
