package net.bluemonster122.tutmod.item;

import net.bluemonster122.tutmod.ModObjects;
import net.bluemonster122.tutmod.lib.ModInfo;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item {
  public ItemBase(String name) {
    super();
    setRegistryName(ModInfo.MOD_ID, name);
    setUnlocalizedName(getRegistryName().toString());
    ModObjects.register(this);
  }
  
  @SideOnly(Side.CLIENT)
  public void initModelsAndVariants() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName().toString()));
  }
}
