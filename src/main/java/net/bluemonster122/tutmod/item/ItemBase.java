package net.bluemonster122.tutmod.item;

import net.bluemonster122.tutmod.ModObjects;
import net.bluemonster122.tutmod.lib.ModInfo;
import net.minecraft.item.Item;

public class ItemBase extends Item {
  public ItemBase(String name) {
    super();
    setRegistryName(ModInfo.MOD_ID, name);
    setUnlocalizedName(getRegistryName().toString());
    
    ModObjects.items.add(this);
  }
}
