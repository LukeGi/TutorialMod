package net.bluemonster122.tutmod.tileentity;

import net.bluemonster122.tutmod.block.BlockGenerator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class TileEntityGenerator extends TileEntityMachineBase {
  
  private boolean isActive = false;
  
  @Override
  protected void tickServer() {
    if (isActive()) {
      getEnergy().receiveEnergy(15, false);
      List<TileEntity> list = new ArrayList<>();
      for (int x = -25; x < 25; x++) {
        for (int y = -25; y < 25; y++) {
          for (int z = -25; z < 25; z++) {
            TileEntity tileEntity = world.getTileEntity(pos.add(x, y, z));
            if (tileEntity != null && !(tileEntity instanceof TileEntityGenerator) && tileEntity.hasCapability(CapabilityEnergy.ENERGY, null)) {
              list.add(tileEntity);
            }
          }
        }
      }
      if (!list.isEmpty()) {
        int i = getEnergy().getEnergyStored() / list.size();
        for (TileEntity tileEntity : list) {
          giveEnergy(tileEntity.getCapability(CapabilityEnergy.ENERGY, null), i);
        }
      }
    } else {
      setActive(true);
    }
  }
  
  private void giveEnergy(IEnergyStorage t, int i) {
    if (getEnergy().getEnergyStored() != 0 && t.canReceive() && t.getEnergyStored() != t.getMaxEnergyStored()) {
      int energy = getEnergy().extractEnergy(i, false);
      int notRecieved = energy - t.receiveEnergy(energy, false);
      getEnergy().receiveEnergy(notRecieved, false);
    }
  }
  
  @Override
  protected void tickClient() {
    
  }
  
  @Override
  protected EnergyStorage makeEnergy() {
    return new EnergyStorage(100000);
  }
  
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound.setBoolean("isActive", isActive());
    return super.writeToNBT(compound);
  }
  
  @Override
  public void readFromNBT(NBTTagCompound compound) {
    setActive(compound.getBoolean("isActive"));
    super.readFromNBT(compound);
  }
  
  public boolean isActive() {
    return isActive;
  }
  
  public void setActive(boolean active) {
    world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockGenerator.ACTIVE, active), 3);
    isActive = active;
  }
}
