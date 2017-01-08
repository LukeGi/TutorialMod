package net.bluemonster122.tutmod.block;

import net.minecraft.block.material.Material;

public class BlockOre extends BlockBase {
  public BlockOre(String name) {
    super(Material.ROCK, name);
    setHarvestLevel("pickaxe", 2);
    setResistance(5.0f);
    setHardness(3.0f);
  }
}
