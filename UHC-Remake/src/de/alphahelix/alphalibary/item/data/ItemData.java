package de.alphahelix.alphalibary.item.data;

import org.bukkit.inventory.ItemStack;

public abstract class ItemData {
    public abstract void applyOn(ItemStack applyOn) throws WrongDataException;
}
