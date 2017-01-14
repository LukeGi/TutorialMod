package net.bluemonster122.tutmod.block;

import net.bluemonster122.tutmod.block.base.BlockBase;
import net.bluemonster122.tutmod.lib.Names;
import net.bluemonster122.tutmod.tileentity.TileEntityElectricFurnace;
import net.bluemonster122.tutmod.util.IHasTile;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
      TileEntityElectricFurnace tileEntity = (TileEntityElectricFurnace) worldIn.getTileEntity(pos);
      System.out.println("input: " + tileEntity.getStackInSlot(0));
      System.out.println("output: " + tileEntity.getStackInSlot(1));
    }
    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
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
