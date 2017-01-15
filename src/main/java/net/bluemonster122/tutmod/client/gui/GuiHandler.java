package net.bluemonster122.tutmod.client.gui;

import net.bluemonster122.tutmod.container.ContainerElectricFurnace;
import net.bluemonster122.tutmod.tileentity.TileEntityElectricFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler
{
  public static final int ELECTRIC_FURNACE_ID = 0;
  
  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    switch (ID)
    {
      case ELECTRIC_FURNACE_ID:
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (tile instanceof TileEntityElectricFurnace)
          return new ContainerElectricFurnace(player, (TileEntityElectricFurnace) tile);
        throw new IllegalStateException("Cannot open Electric Furnace gui, if tree farm is not at location of tree farm block.");
      default:
        return null;
    }
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
  {
    switch (ID)
    {
      case ELECTRIC_FURNACE_ID:
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (tile instanceof TileEntityElectricFurnace)
          return new GuiElectricFurnace(player, (TileEntityElectricFurnace) tile);
        throw new IllegalStateException("Cannot open Electric Furnace gui, if tree farm is not at location of tree farm block.");
      default:
        return null;
    }
  }
}
