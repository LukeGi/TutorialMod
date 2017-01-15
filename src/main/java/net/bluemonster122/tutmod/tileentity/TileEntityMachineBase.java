package net.bluemonster122.tutmod.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileEntityMachineBase extends TileEntity implements ITickable {
  
  public TileEntityMachineBase() {
    energy = makeEnergy();
  }
  
  private EnergyStorage energy;
  
  @Override
  public void update() {
    if (world.isRemote) {
      tickClient();
    } else {
      tickServer();
      world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }
  }
  
  protected abstract void tickServer();
  
  protected abstract void tickClient();
  
  protected abstract EnergyStorage makeEnergy();
  
  @Override
  public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
    return oldState.getBlock() != newSate.getBlock();
  }
  
  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {
    return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    this.readFromNBT(pkt.getNbtCompound());
  }
  
  @Nonnull
  @Override
  public NBTTagCompound getUpdateTag() {
    return writeToNBT(super.getUpdateTag());
  }
  
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound.setInteger("energyStored", energy.getEnergyStored());
    return super.writeToNBT(compound);
  }
  
  @Override
  public void readFromNBT(NBTTagCompound compound) {
    energy = makeEnergy();
    energy.receiveEnergy(compound.getInteger("energyStored"), false);
    super.readFromNBT(compound);
  }
  
  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return capability.equals(CapabilityEnergy.ENERGY);
  }
  
  @Nullable
  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability.equals(CapabilityEnergy.ENERGY)) {
      return CapabilityEnergy.ENERGY.cast(getEnergy());
    }
    return null;
  }
  
  public EnergyStorage getEnergy() {
    return energy;
  }
  
  public void setEnergy(EnergyStorage energy) {
    this.energy = energy;
  }
}
