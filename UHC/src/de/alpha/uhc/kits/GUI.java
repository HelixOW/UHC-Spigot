package de.alpha.uhc.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.alpha.uhc.Core;
import net.minetopix.library.main.item.ItemCreator;

public class GUI {
	
	private static Inventory kits;
	public static String title;
	
	public static void fill() {
		kits = Bukkit.createInventory(null, 54, title);

		for(String kitName : new KitFileManager().getAllKits()) {
			
			try {
				int slot = new KitFileManager().getSlot(kitName);
				Material m = Material.getMaterial(new KitFileManager().getMaterial(kitName).toUpperCase());
				String lore = new KitFileManager().getLore(kitName);
				
				kits.setItem(slot,
						new ItemCreator(m)
						.setName(kitName)
						.setLore(new String[]{lore})
						.build());
			} catch (NullPointerException e) {
				Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cYour Kits.yml is invalid. Block has to be a valid Material [google Bukkit materials]");
			}
		}
	}
	
	public static void open(Player p) {
		p.openInventory(kits);
	}

}
