package de.alpha.uhc.teams;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.minetopix.library.main.item.ItemCreator;

public class TeamSel {
	
	private static Inventory teams;
	public static String title;
	
	public static String m;
	public static String itemName;
	
	public static void fill() {
		teams = Bukkit.createInventory(null, 27, title);
		
		for(ItemStack is : ATeam.decompileBlocks()) {
			teams.addItem(is);
		}
	}
	
	public static void open(Player p) {
		p.openInventory(teams);
	}
	
	public static ItemStack getItemStack() {
		return new ItemCreator(Material.getMaterial(m.toUpperCase())).setName(itemName).build();
	}
	
	public static void give(Player p) {
		p.getInventory().setItem(2, new ItemCreator(Material.getMaterial(m.toUpperCase())).setName(itemName).build());
	}

}
