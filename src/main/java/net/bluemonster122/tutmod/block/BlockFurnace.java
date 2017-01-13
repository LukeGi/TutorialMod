package net.bluemonster122.tutmod.block;

import net.bluemonster122.tutmod.block.base.BlockBase;
import net.bluemonster122.tutmod.lib.Names;
import net.bluemonster122.tutmod.tileentity.TileEntityElectricFurnace;
import net.bluemonster122.tutmod.util.IHasTile;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFurnace extends BlockBase implements IHasTile {
  public static final PropertyDirection FACING = BlockHorizontal.FACING;
  
  public BlockFurnace() {
    super(Material.IRON);
    setHardness(5.0F);
    setResistance(10.0F);
  }
  
  @Override
  protected String getName() {
    return Names.furnace_block;
  }
  
  @Override
  public Class<? extends TileEntity> getTileClass() {
    return TileEntityElectricFurnace.class;
  }
  
  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileEntityElectricFurnace();
  }
}
