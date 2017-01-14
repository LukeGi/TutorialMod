package net.bluemonster122.tutmod.block;

import net.bluemonster122.tutmod.block.base.BlockBase;
import net.bluemonster122.tutmod.lib.Names;
import net.bluemonster122.tutmod.tileentity.TileEntityElectricFurnace;
import net.bluemonster122.tutmod.util.IHasTile;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockFurnace extends BlockBase implements IHasTile {
  public static final PropertyDirection FACING = BlockHorizontal.FACING;
  public static final PropertyBool ACTIVE = PropertyBool.create("active");
  
  public BlockFurnace() {
    super(Material.IRON);
    setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
    setHardness(5.0F);
    setResistance(10.0F);
  }
  
  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FACING, ACTIVE);
  }
  
  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(FACING, EnumFacing.HORIZONTALS[meta % 4]).withProperty(ACTIVE, meta < 4);
  }
  
  @Override
  public int getMetaFromState(IBlockState state) {
    switch (state.getValue(FACING)) {
      case SOUTH:
        return state.getValue(ACTIVE) ? 0 : 4;
      case WEST:
        return state.getValue(ACTIVE) ? 1 : 5;
      case NORTH:
        return state.getValue(ACTIVE) ? 2 : 6;
      case EAST:
        return state.getValue(ACTIVE) ? 3 : 7;
      default:
        return 0;
    }
  }
  
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, false), 2);
  }
  
  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    ((TileEntityElectricFurnace)worldIn.getTileEntity(pos)).dropInv();
    super.breakBlock(worldIn, pos, state);
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public void initModelsAndVariants() {
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),0,new ModelResourceLocation(getRegistryName(), "active=false,facing=north"));
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
