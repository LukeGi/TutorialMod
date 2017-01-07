package net.bluemonster122.tutmod.proxy;

import net.bluemonster122.tutmod.WorldGenOres;
import net.bluemonster122.tutmod.init.ModBlocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy{
  WorldGenOres oreGenerator = new WorldGenOres();
  
  public void preInit(FMLPreInitializationEvent event) {
    ModBlocks.init();
  
    GameRegistry.registerWorldGenerator(oreGenerator, 0);
  }
  
  public void init(FMLInitializationEvent event) {
    
  }
  
  public void postInit(FMLPostInitializationEvent event) {
    
  }
}
