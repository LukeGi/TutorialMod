package net.bluemonster122.tutmod.block;

import net.bluemonster122.tutmod.TutorialMod;
import net.bluemonster122.tutmod.lib.Names;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockOre extends Block {
  public BlockOre() {
    super(Material.ROCK);
    setRegistryName(TutorialMod.MOD_ID, Names.ORE_BLOCK);
    setUnlocalizedName(getRegistryName().toString());
    //Sets the required tool to be at least an iron pickaxe
    setHarvestLevel("pickaxe", 2);
    //set Resistance and Hardness to be the same as iron ore.
    setResistance(5.0f);
    setHardness(3.0f);
    setCreativeTab(TutorialMod.tab);
  }
}
