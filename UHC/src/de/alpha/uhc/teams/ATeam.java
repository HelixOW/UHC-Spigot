package de.alpha.uhc.teams;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.TeamFile;
import net.minetopix.library.main.item.ItemCreator;

public class ATeam implements Listener {
	
	public static String chosen;
	public static String noExist;
	public static String allTeams;
	
	public static String materialName;
	public static String blockName;
	public static String title;
	
	private static Inventory teamInv;
	
	public static ArrayList<String> teamNames = new ArrayList<String>();
	public static ArrayList<String> teamColors = new ArrayList<String>();
	
	private static HashMap<Player, String> teams = new HashMap<Player, String>();
	
	public static void addPlayerToTeam(Player p, String teamToPut) {
		if(teamNames.contains(teamToPut)) {
			teams.put(p, teamToPut);
			chosen = chosen.replace("[team]", getTeamColor(teamToPut)+teamToPut);
			p.sendMessage(Core.getPrefix() + chosen);
			p.setDisplayName(getTeamColor(teamToPut) + p.getName());
			p.setPlayerListName(getTeamColor(teamToPut) + p.getName());
			chosen = MessageFileManager.getMSGFile().getColorString("Teams.chosen");
		} else {
			String a = noExist.replace("[team]", teamToPut);
			String b = allTeams.replace("[teams]", ""+teamNames);
			p.sendMessage(Core.getPrefix() + a + "\n       " + b);
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		
		if(!(e.getEntity() instanceof Player && e.getDamager() instanceof Player)) return;
		
		Player p = (Player) e.getEntity();
		Player other = (Player) e.getDamager();
		
		if(hasSameTeam(p, other)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		if(!(GState.isState(GState.LOBBY))) return;
		
		Player p = e.getPlayer();
		Material m = p.getItemInHand().getType();
		Material toCompare = Material.getMaterial(materialName.toUpperCase());
		
		if(m.equals(toCompare)) {
			
			teamInv  = Bukkit.createInventory(null, 36, title);
			
			for(String name : teamNames) {
				
				ItemStack item = new ItemCreator(Material.getMaterial(blockName.toUpperCase()))
						.setName(getTeamColor(name) + name)
						.setDamage((short) getTeamColorAsInteger(name))
						.build();
						
				
				teamInv.addItem(item);
			}
			p.openInventory(teamInv);
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		if(!(GState.isState(GState.LOBBY))) return;
		if(e.getClickedInventory() == null) return;
		if(e.getClickedInventory().getTitle() == null) return;
		if(!(e.getClickedInventory().getTitle().equals(title))) return;
		if(!(e.getWhoClicked() instanceof Player)) return;
		if(!(e.getCurrentItem().hasItemMeta())) return;
		if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		Player p =(Player) e.getWhoClicked();
		e.setCancelled(true);
		
		if(e.getCurrentItem().getType().equals(Material.getMaterial(blockName.toUpperCase()))) {
			
			int dura = e.getCurrentItem().getDurability();
			
			for(String names : teamNames) {
					
				if(dura == getTeamColorAsInteger(names)) {
					addPlayerToTeam(p, names);
					return;
				}
			}
		}
		
		
	}
	
	public static ChatColor getTeamColor(String team) {
		try {
			return ChatColor.valueOf(TeamFile.getTeamColorAsString(team));
		} catch (IllegalArgumentException e) {
			Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cThe Team §4" + team + " §cis invalid.");
		}
		return ChatColor.RESET;
	}
	
	public static int getTeamColorAsInteger(String team) {
		try {
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("orange")) {
				return 1;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("light_purple")) {
				return 2;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("aqua")) {
				return 3;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("yellow")) {
				return 4;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("green")) {
				return 5;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("red")) {
				return 6;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("dark_grey")) {
				return 7;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("grey")) {
				return 8;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("dark_aqua")) {
				return 9;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("dark_purple")) {
				return 10;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("blue")) {
				return 11;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("dark_green")) {
				return 13;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("dark_red")) {
				return 14;
			}
			if(TeamFile.getTeamColorAsString(team).equalsIgnoreCase("black")) {
				return 15;
			}
			
		} catch (IllegalArgumentException e) {
			Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cThe Team §4" + team + " §cis invalid.");
		}
		
		return 12;
	}
	
	public static boolean hasTeam(Player p) {
		if(teams.containsKey(p)) {
			return true;
		}
		return false;
	}
	
	public static String getPlayerTeam(Player p) {
		if(teams.containsKey(p)) {
			return teams.get(p);
		}
		return "";
	}
	
	public static boolean hasSameTeam(Player p, Player other) {
		if(teams.containsKey(p) && teams.containsKey(other)) {
			if(getPlayerTeam(p).equals(getPlayerTeam(other))) {
				return true;
			}
		}
		return false;
	}
}
