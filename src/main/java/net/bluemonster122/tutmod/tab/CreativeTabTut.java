package net.bluemonster122.tutmod.tab;

import net.bluemonster122.tutmod.lib.Names;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Comparator;
import java.util.Map;

public class CreativeTabTut extends CreativeTabs {
  public CreativeTabTut() {
    super(Names.TUT_TAB);
  }
  
  @Override
  public ItemStack getTabIconItem() {
    return new ItemStack(Blocks.AIR);
  }
  
  @Override
  public void displayAllRelevantItems(NonNullList<ItemStack> list) {
    ForgeRegistries.ITEMS.getEntries().stream().filter(e -> e.getValue() instanceof IInTab).map(Map.Entry::getValue).forEach(i -> list.add(new ItemStack(i)));
    ForgeRegistries.BLOCKS.getEntries().stream().filter(e -> e.getValue() instanceof IInTab).map(Map.Entry::getValue).forEach(b -> list.add(new ItemStack(b)));
    list.sort(Comparator.comparingInt(o -> I18n.format(o.getItem().getUnlocalizedName()).charAt(1)));
  }
}
