package net.minetopix.library.main.utils;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ValidateNull {
	
	public static boolean hasDisplayName(ItemStack itemStack) {
		if(itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName()) {
			return false;
		}
		return true;
	}
	
	public static boolean existsFile(File source) {
		if (source == null || !source.exists()) {
			return false;
		}
		return true;
	}
}
