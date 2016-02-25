package de.alpha.uhc.utils;

import org.bukkit.inventory.ItemStack;

public abstract class ItemData {
	public abstract void applyOn(ItemStack applyOn) throws WrongDataException;
}
