package de.alpha.uhc.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.alpha.uhc.Core;

public class Regions implements Listener {
	
	private static ArrayList<Cuboid> regions = new ArrayList<Cuboid>();
	private static HashMap<Player, Location> pos1 = new HashMap<Player, Location>();
	private static HashMap<Player, Location> pos2 = new HashMap<Player, Location>();
	
	public static String material;
	public static boolean lobby;
	
	public static void addRegion(Cuboid toAdd) {
		regions.add(toAdd);
	}
	
	public static boolean getDefined(Player p) {
		if(pos1.containsKey(p) && pos2.containsKey(p)) {
			return true;
		}
		return false;
	}
	
	public static Location getPos1(Player p) {
		if(pos1.containsKey(p)) {
			return pos1.get(p);
		}
		return null;
	}
	
	public static Location getPos2(Player p) {
		if(pos2.containsKey(p)) {
			return pos2.get(p);
		}
		return null;
	}
	
	public static boolean isInRegion(Location loc) {
		
		for(Cuboid c : regions) {
			
			if(c.contains(loc)) {
				return true;
			}
			
		}
		return false;
		
	}
	
	@EventHandler
	public void onDefine(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		Material m = Material.getMaterial(material.toUpperCase());
		
		if(p.getItemInHand() == null) return;
		if(!(p.getItemInHand().getType().equals(m))) return;
		if(!(p.hasPermission("uhc.admin"))) return;
		if(lobby == false) return;
		
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			e.setCancelled(true);
			
			pos1.put(p, e.getClickedBlock().getLocation());
			p.sendMessage(Core.getPrefix() + "§7The §afirst Lobbypoint §7has been set. [1/2]");
			
		} else if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			e.setCancelled(true);
			
			pos2.put(p, e.getClickedBlock().getLocation());
			p.sendMessage(Core.getPrefix() + "§7The §asecond Lobbypoint §7has been set. [2/2]");
			
		}
		
		if(pos1.containsKey(p) && pos2.containsKey(p)) {
			p.sendMessage(Core.getPrefix()+"§7You can now create the lobby with /uhc lobby create");
		}
		
		
	}
}
