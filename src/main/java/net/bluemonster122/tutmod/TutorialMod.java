package net.bluemonster122.tutmod;

import net.bluemonster122.tutmod.lib.ModInfo;
import net.bluemonster122.tutmod.proxy.IProxy;
import net.bluemonster122.tutmod.tab.CreativeTabTut;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Random;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDANCIES)
public class TutorialMod {
  
  public static Random random = new Random();
  
  @Instance(ModInfo.MOD_ID)
  public static TutorialMod instance;
  
  @SidedProxy(clientSide = "net.bluemonster122.tutmod.proxy.ClientProxy", serverSide = "net.bluemonster122.tutmod.proxy.ServerProxy")
  public static IProxy proxy;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    proxy.preInit(event);
  }
  
  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }
}
