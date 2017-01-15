package net.bluemonster122.tutmod;

import net.bluemonster122.tutmod.block.BlockElectricFurnace;
import net.bluemonster122.tutmod.block.BlockOre;
import net.bluemonster122.tutmod.block.base.BlockBase;
import net.bluemonster122.tutmod.block.base.BlockGenerator;
import net.bluemonster122.tutmod.item.ItemBase;
import net.bluemonster122.tutmod.lib.ModInfo;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ObjectHolder(ModInfo.MOD_ID)
public class ModObjects {
  public ModObjects() {
  }
  
  private static final List<BlockBase> blocks = new ArrayList<>();
  private static final List<ItemBase> items = new ArrayList<>();
  
  public static final BlockBase block_ore = new BlockOre();
  public static final BlockBase furnace_block = new BlockElectricFurnace();
  public static final BlockBase generator = new BlockGenerator();
  
  public static final ItemBase item = new ItemBase("test_item");
  
  public static void register(BlockBase block) {
    blocks.add(block);
  }
  
  public static void register(ItemBase item) {
    items.add(item);
  }
  
  public static Collection<BlockBase> getBlocks() {
    return blocks;
  }
  
  public static Collection<ItemBase> getItems() {
    return items;
  }
}
