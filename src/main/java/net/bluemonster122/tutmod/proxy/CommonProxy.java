package net.bluemonster122.tutmod.proxy;

import net.bluemonster122.tutmod.ModObjects;
import net.bluemonster122.tutmod.worldgen.WorldGenOres;
import net.bluemonster122.tutmod.tab.CreativeTabTut;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy{
  public static CreativeTabs tab = new CreativeTabTut();
  
  WorldGenOres oreGenerator = new WorldGenOres();
  
  public void preInit(FMLPreInitializationEvent event) {
    ModObjects.registerObjects();
    GameRegistry.registerWorldGenerator(oreGenerator, 0);
  }
  
  public void init(FMLInitializationEvent event) {
    
  }
  
  public void postInit(FMLPostInitializationEvent event) {
    
  }
}
