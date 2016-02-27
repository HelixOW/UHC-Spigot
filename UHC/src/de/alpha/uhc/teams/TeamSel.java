package de.alpha.uhc.teams;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minetopix.library.main.item.ItemCreator;

public class TeamSel {
	
	public static ArrayList<Team> team = TeamManager.getAllTeams();
	
	private static Inventory teams;
	public static String title;
	
	public static String m;
	public static String itemName;
	
	public static Material block;
	public static String names;
	
	@SuppressWarnings("deprecation")
	public static void fill() {
		teams = Bukkit.createInventory(null, 27, title);
		
		//Füllen mit Clay Blöcken Name, in Options einstellen lassen
		
		
		ItemStack clay = new ItemStack(block, 1, (short) 5);
		ItemMeta claymeta = clay.getItemMeta();
		
		for(int i = 0; i < team.size(); i++) {
			claymeta.setDisplayName(team.get(i).getChatColor() + team.get(i).getName());
			clay.setDurability((short) DyeColor.valueOf(team.get(i).getTeamColor().toString()).getData());
			clay.setItemMeta(claymeta);
			teams.setItem(i, clay);	
		}
	}
	
	public static void open(Player p) {
		p.openInventory(teams);
	}
	
	public static void give(Player p) {
		p.getInventory().setItem(2, new ItemCreator(Material.getMaterial(m.toUpperCase())).setName(itemName).build());
	}

}
