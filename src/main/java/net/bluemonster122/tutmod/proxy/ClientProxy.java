package net.bluemonster122.tutmod.proxy;

import net.bluemonster122.tutmod.ModObjects;
import net.bluemonster122.tutmod.block.base.BlockBase;
import net.bluemonster122.tutmod.item.ItemBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
  @Override
  public void preInit(FMLPreInitializationEvent event) {
    super.preInit(event);
    ModObjects.getBlocks().forEach(BlockBase::initModelsAndVariants);
    ModObjects.getItems().forEach(ItemBase::initModelsAndVariants);
  }
  
  @Override
  public void init(FMLInitializationEvent event) {
    super.init(event);
  }
  
  @Override
  public void postInit(FMLPostInitializationEvent event) {
    super.postInit(event);
  }
  
  public static void registerItemModel(Item item) {
    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
  }
}
