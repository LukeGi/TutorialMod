package net.bluemonster122.tutmod;

import net.bluemonster122.tutmod.proxy.IProxy;
import net.bluemonster122.tutmod.tab.BlueCreativeTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Random;

@Mod(modid = TutorialMod.MOD_ID, name = TutorialMod.MOD_NAME, version = TutorialMod.VERSION, dependencies = TutorialMod.DEPENDANCIES)
public class TutorialMod {
  public static final String MOD_ID = "tutmod";
  public static final String MOD_NAME = "Tutorial Mod";
  public static final String VERSION = "@VERSION@";
  public static final String DEPENDANCIES = "";//"requires-after:forge@[13.19.1.2188,)";
  public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";
  
  public static Random random = new Random();
  
  @Instance(MOD_ID)
  public static TutorialMod instance;
  
  @SidedProxy(clientSide = "net.bluemonster122.tutmod.proxy.ClientProxy", serverSide = "net.bluemonster122.tutmod.proxy.ServerProxy")
  public static IProxy proxy;
  public static CreativeTabs tab = new BlueCreativeTab();
  
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
