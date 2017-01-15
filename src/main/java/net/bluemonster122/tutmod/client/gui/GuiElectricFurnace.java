package net.bluemonster122.tutmod.client.gui;

import com.google.common.collect.ImmutableList;
import net.bluemonster122.tutmod.container.ContainerElectricFurnace;
import net.bluemonster122.tutmod.lib.ModInfo;
import net.bluemonster122.tutmod.tileentity.TileEntityElectricFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiElectricFurnace extends GuiContainer {
  
  ResourceLocation GUI_LOCATION = new ResourceLocation(ModInfo.MOD_ID, "textures/gui/electric_furnace.png");
  EntityPlayer player;
  TileEntityElectricFurnace tile;
  
  public GuiElectricFurnace(EntityPlayer player, TileEntityElectricFurnace treefarm) {
    super(new ContainerElectricFurnace(player, treefarm));
    this.xSize = 194;
    this.ySize = 115;
    this.player = player;
    this.tile = treefarm;
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(GUI_LOCATION);
    int left = (width - xSize) / 2;
    int top = (height - ySize) / 2;
    drawTexturedModalRect(left, top, 0, 0, xSize, ySize);
    IEnergyStorage battery = tile.getCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN);
    int energyPercent = (int) ((float) battery.getEnergyStored() / (float) battery.getMaxEnergyStored() * 56);
    drawTexturedModalRect(left + 173, top + 85 - energyPercent, 194, 85 - energyPercent, 16, energyPercent);
    if (tile.getBurntime() != -1) {
      int burnPerc = (int) ((float) (100 - tile.getBurntime()) / 100F * 109);
      drawTexturedModalRect(left + 30, top + 11, 30, 116, burnPerc, 15);
    }
  }
  
  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    String s = I18n.format("tile.tutmod:furnace_block.name");
    this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2 - 20, 5, 4210752);
    if (isPointInRegion(174, 29, 16, 56, mouseX, mouseY)) {
      IEnergyStorage battery = tile.getCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN);
      drawHoveringText(ImmutableList.of(battery.getEnergyStored() + " / " + battery.getMaxEnergyStored() + " " + "Forge Units"), mouseX - guiLeft, mouseY - guiTop);
    }
  }
}
