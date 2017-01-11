package net.bluemonster122.tutmod.block;

import net.bluemonster122.tutmod.block.base.BlockEnum;
import net.bluemonster122.tutmod.block.base.IEnumMeta;
import net.bluemonster122.tutmod.lib.Names;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BlockOre extends BlockEnum {
  public static final PropertyEnum<OreTypes> VARIENTS = PropertyEnum.create("variant", OreTypes.class);
  
  public BlockOre() {
    super(Material.ROCK);
    setDefaultState(getDefaultState().withProperty(VARIENTS, OreTypes.BLUE));
    setHarvestLevel("pickaxe", 2);
    setResistance(5.0f);
    setHardness(3.0f);
  }
  
  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, VARIENTS);
  }
  
  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(VARIENTS).getMeta();
  }
  
  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(VARIENTS, OreTypes.values()[meta]);
  }
  
  @Override
  public int damageDropped(IBlockState state) {
    return getMetaFromState(state);
  }
  
  @Override
  public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
    for (OreTypes varient : OreTypes.values()) {
      list.add(new ItemStack(this,1,  varient.getMeta()));
    }
  }
  
  @Override
  protected String getName() {
    return Names.ORE_BLOCK;
  }
  
  @Override
  public IEnumMeta[] getVariants() {
    return OreTypes.values();
  }
  
  public enum OreTypes implements IEnumMeta {
    BLUE, PURPLE, GREEN;
    
    @Override
    public String getName() {
      return name().toLowerCase() + "_ore";
    }
  
    @Override
    public int getMeta() {
      return ordinal();
    }
  }
}
