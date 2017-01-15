package net.bluemonster122.tutmod.block.base;

import net.bluemonster122.tutmod.lib.Names;
import net.bluemonster122.tutmod.tileentity.TileEntityGenerator;
import net.bluemonster122.tutmod.util.IHasTile;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockGenerator extends BlockBase implements IHasTile{
  public BlockGenerator() {
    super(Material.IRON);
  }
  
  @Override
  public Class<? extends TileEntity> getTileClass() {
    return TileEntityGenerator.class;
  }
  
  @Override
  protected String getName() {
    return Names.GENERATOR;
  }
  
  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityGenerator();
  }
}
