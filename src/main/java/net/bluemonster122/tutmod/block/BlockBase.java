package net.bluemonster122.tutmod.block;

import net.bluemonster122.tutmod.ModObjects;
import net.bluemonster122.tutmod.item.ItemBlockBase;
import net.bluemonster122.tutmod.lib.ModInfo;
import net.bluemonster122.tutmod.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block{
  public BlockBase(Material materialIn, String name) {
    super(materialIn);
    setRegistryName(ModInfo.MOD_ID, name);
    setUnlocalizedName(getRegistryName().toString());
    setCreativeTab(CommonProxy.tab);
  
    ModObjects.blocks.add(this);
    ModObjects.items.add(new ItemBlockBase(this));
  }
}
