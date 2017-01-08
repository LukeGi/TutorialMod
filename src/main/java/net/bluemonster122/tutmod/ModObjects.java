package net.bluemonster122.tutmod;

import net.bluemonster122.tutmod.block.BlockBase;
import net.bluemonster122.tutmod.block.BlockOre;
import net.bluemonster122.tutmod.item.ItemBase;
import net.bluemonster122.tutmod.lib.ModInfo;
import net.bluemonster122.tutmod.lib.Names;
import net.bluemonster122.tutmod.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@ObjectHolder(ModInfo.MOD_ID)
public class ModObjects {
  public static List<Block> blocks = new ArrayList<>();
  public static List<Item> items = new ArrayList<>();
  
  public static BlockBase green_ore = new BlockOre(Names.GREEN_ORE);
  public static BlockBase purple_ore = new BlockOre(Names.PURPLE_ORE);
  public static BlockBase blue_ore = new BlockOre(Names.BLUE_ORE);
  
  public static ItemBase item = new ItemBase("test_item");
  
  public static void registerObjects() {
    blocks.forEach(GameRegistry::register);
    items.forEach(GameRegistry::register);
  }
  
  @SideOnly(Side.CLIENT)
  public static void registerObjectModels() {
    blocks.stream().map(Item::getItemFromBlock).forEach(ClientProxy::registerItemModel);
    items.forEach(ClientProxy::registerItemModel);
  }
}
