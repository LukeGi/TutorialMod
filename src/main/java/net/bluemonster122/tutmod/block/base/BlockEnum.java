package net.bluemonster122.tutmod.block.base;

import net.bluemonster122.tutmod.item.ItemBlockEnum;
import net.bluemonster122.tutmod.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockEnum extends BlockBase {
  
  public BlockEnum(Material materialIn) {
    super(materialIn);
  }
  
  public String getUnlocalizedName(ItemStack itemStack) {
    if (itemStack != null && itemStack.getItem() != null && Block.getBlockFromItem(itemStack.getItem()) instanceof BlockBase) {
      if (getVariants().length > 0) {
        return String.format("tile.%s:%s", ModInfo.MOD_ID, getVariants()[Math.abs(itemStack.getMetadata() % getVariants().length)].getName());
      }
    }
    
    return super.getUnlocalizedName();
  }
  
  @SideOnly(Side.CLIENT)
  public void initModelsAndVariants() {
    if (getVariants().length > 0) {
      for (IEnumMeta variant : getVariants()) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), variant.getMeta(), new ModelResourceLocation(getRegistryName(), "variant=" + variant.getName()));
      }
    } else {
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName().toString()));
    }
  }
  
  public abstract IEnumMeta[] getVariants();
  
  @Override
  public ItemBlock getItemBlock() {
    return new ItemBlockEnum(this);
  }
}
