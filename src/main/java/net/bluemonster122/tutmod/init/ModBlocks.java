package net.bluemonster122.tutmod.init;

import net.bluemonster122.tutmod.block.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
  
  public static BlockOre ore_block;
  
  public static void init() {
    registerBlock(ore_block = new BlockOre());
  }
  
  @SideOnly(Side.CLIENT)
  public static void initClient() {
    initModel(ore_block);
  }
  
  private static void registerBlock(Block block) {
    GameRegistry.register(block);
    GameRegistry.register(new ItemBlock(block), block.getRegistryName());
  }
  
  @SideOnly(Side.CLIENT)
  private static void initModel(Block block) {
    Item item = Item.getItemFromBlock(block);
    ModelResourceLocation location = new ModelResourceLocation(block.getRegistryName(), "inventory");
    ModelLoader.registerItemVariants(item, location);
    ModelLoader.setCustomModelResourceLocation(item, 0, location);
  }
}
