package net.bluemonster122.tutmod.util;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;

public interface IHasTile extends ITileEntityProvider {
  Class<? extends TileEntity> getTileClass();
}
