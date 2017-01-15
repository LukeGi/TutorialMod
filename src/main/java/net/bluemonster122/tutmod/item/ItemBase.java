package net.bluemonster122.tutmod.item;

import net.bluemonster122.tutmod.ModObjects;
import net.bluemonster122.tutmod.lib.ModInfo;
import net.bluemonster122.tutmod.tab.IInTab;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item implements IInTab {
  public ItemBase(String name) {
    super();
    setRegistryName(ModInfo.MOD_ID, name);
    setUnlocalizedName(getRegistryName().toString());
    ModObjects.register(this);
  }
  
  @Override
  public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
    super.getSubItems(itemIn, tab, subItems);
  }
  
  @SideOnly(Side.CLIENT)
  public void initModelsAndVariants() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName().toString()));
  }
}
