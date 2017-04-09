package net.bluemonster122.tutmod.proxy;

import net.bluemonster122.tutmod.ModObjects;
import net.bluemonster122.tutmod.TutorialMod;
import net.bluemonster122.tutmod.block.base.BlockBase;
import net.bluemonster122.tutmod.client.gui.GuiHandler;
import net.bluemonster122.tutmod.tab.CreativeTabTut;
import net.bluemonster122.tutmod.util.IHasTile;
import net.bluemonster122.tutmod.worldgen.WorldGenOres;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy{
  public static CreativeTabs tab = new CreativeTabTut();
  
  WorldGenOres oreGenerator = new WorldGenOres();
  
  public void preInit(FMLPreInitializationEvent event) {
    ModObjects.getItems().forEach(GameRegistry::register);
    for (BlockBase block : ModObjects.getBlocks()) {
      GameRegistry.register(block);
      GameRegistry.register(block.getItemBlock(), block.getRegistryName());
      if (block instanceof IHasTile) {
        GameRegistry.registerTileEntity(((IHasTile)block).getTileClass(), block.getRegistryName().toString());
      }
    }
    GameRegistry.registerWorldGenerator(oreGenerator, 0);
  }
  
  public void init(FMLInitializationEvent event) {
    NetworkRegistry.INSTANCE.registerGuiHandler(TutorialMod.instance, new GuiHandler());
  }
  
  public void postInit(FMLPostInitializationEvent event) {
    
  }
}
