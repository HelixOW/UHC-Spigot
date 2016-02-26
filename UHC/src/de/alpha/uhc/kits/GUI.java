package de.alpha.uhc.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.alpha.uhc.files.KitFileManager;
import net.minetopix.library.main.item.ItemCreator;

public class GUI {
	
	private static Inventory kits;
	public static String title;
	
	public static void fill() {
		kits = Bukkit.createInventory(null, 54, title);

		for(String kitName : new KitFileManager().getAllKits()) {
			
			int slot = new KitFileManager().getSlot(kitName);
			Material m = Material.getMaterial(new KitFileManager().getMaterial(kitName).toUpperCase());
			String lore = new KitFileManager().getLore(kitName);
			
			kits.setItem(slot,
					new ItemCreator(m)
					.setName(kitName)
					.setLore(new String[]{lore})
					.build());
		}
	}
	
	public static void open(Player p) {
		p.openInventory(kits);
	}

}
