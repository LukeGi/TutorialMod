package net.bluemonster122.tutmod.container;

import net.bluemonster122.tutmod.tileentity.TileEntityElectricFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerElectricFurnace extends Container {
  
  private EntityPlayer player;
  private TileEntityElectricFurnace tileEntity;
  
  
  public ContainerElectricFurnace(EntityPlayer player, TileEntityElectricFurnace tileEntity) {
    this.player = player;
    this.tileEntity = tileEntity;
    int i;
    int j;
    
    // ME
    
    IItemHandler inventory = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
    // INPUT
    this.addSlotToContainer(new SlotItemHandler(inventory, 0, 8, 9));
    // OUTPUT
    this.addSlotToContainer(new SlotItemHandler(inventory, 1, 148, 9));
    
    // VANILLA
    
    for (i = 0; i < 3; ++i) {
      for (j = 0; j < 9; ++j) {
        this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 33 + i * 18));
      }
    }
    
    for (i = 0; i < 9; ++i) {
      this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 91));
    }
  }
  
  @Override
  @Nonnull
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
    ItemStack previous = null;
    Slot slot = this.inventorySlots.get(fromSlot);
    
    if (slot != null && slot.getHasStack()) {
      ItemStack current = slot.getStack();
      previous = current.copy();
      
      if (fromSlot < 72) {
        // From TE Inventory to Player Inventory
        if (!this.mergeItemStack(current, 2, 38, true))
          return ItemStack.EMPTY;
      } else {
        // From Player Inventory to TE Inventory
        if (!this.mergeItemStack(current, 0, 2, false))
          return ItemStack.EMPTY;
      }
      
      if (current.getCount() == 0)
        slot.putStack(ItemStack.EMPTY);
      else
        slot.onSlotChanged();
      
      if (current.getCount() == previous.getCount())
        return ItemStack.EMPTY;
      slot.onTake(playerIn, current);
    }
    return previous;
  }
  
  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {
    return playerIn.getPosition().distanceSq(tileEntity.getPos()) < 100;
  }
}
