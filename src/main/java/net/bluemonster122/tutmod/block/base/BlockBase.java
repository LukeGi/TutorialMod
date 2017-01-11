package net.bluemonster122.tutmod.block.base;

import net.bluemonster122.tutmod.ModObjects;
import net.bluemonster122.tutmod.lib.ModInfo;
import net.bluemonster122.tutmod.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockBase extends Block {
  public BlockBase(Material materialIn) {
    super(materialIn);
    setRegistryName(ModInfo.MOD_ID, getName());
    setUnlocalizedName(getRegistryName().toString());
    setCreativeTab(CommonProxy.tab);
  
    ModObjects.register(this);
  }
  
  @SideOnly(Side.CLIENT)
  public void initModelsAndVariants() {
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName().toString()));
  }
  
  protected abstract String getName();
}
