package de.alpha.uhc.teams;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.alpha.uhc.manager.TitleManager;
import net.minetopix.library.main.item.ItemCreator;

public class ATeam implements Listener {
	
	public static String chosen;
	
	public static ArrayList<String> teamNames = new ArrayList<String>();
	public static ArrayList<String> teamColors = new ArrayList<String>();
	public static ArrayList<String> teamBlocks = new ArrayList<String>();
	
	private static HashMap<Player, String> team = new HashMap<Player, String>();
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		if(!(e.getWhoClicked() instanceof Player)) return;
		if(e.getClickedInventory() == null) return;
		if(!(e.getClickedInventory().getName().equals(TeamSel.title))) return;
		if(e.getCurrentItem() == null) return;
		
		Player p = (Player) e.getWhoClicked();
		
		for(ItemStack is : items) {
			if(e.getCurrentItem().equals(is)) {
				
				team.put(p, is.getItemMeta().getDisplayName());
				p.closeInventory();
				
				String a = chosen.replace("[team]", getPlayerTeam(p));
				TitleManager.sendAction(p, a);
			}
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
	public void onClick(PlayerInteractEvent e) {
		
		if(e.getPlayer().getItemInHand().equals(TeamSel.getItemStack())) {
			e.setCancelled(true);
			e.getPlayer().closeInventory();
			TeamSel.open(e.getPlayer());
		} else {
			return;
		}
		
	}
	
	public static boolean hasTeam(Player p) {
		if(team.containsKey(p)) {
			return true;
		}
		return false;
	}
	
	public static String getPlayerTeam(Player p) {
		if(team.containsKey(p)) {
			return team.get(p);
		}
		return "";
	}
	
	public static boolean hasSameTeam(Player p, Player other) {
		if(team.containsKey(p) && team.containsKey(other)) {
			if(getPlayerTeam(p).equals(getPlayerTeam(other))) {
				return true;
			}
		}
		return false;
	}
	
	private static ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	
	public static ArrayList<ItemStack> decompileBlocks() {
		
		for(String m : teamBlocks) {
			for(String name : teamNames) {
				for(String color : teamColors) {
					
					String[] segements = m.split(":");
					int id = Integer.parseInt(segements[0]);
					String a = segements[1];
					ChatColor cc = ChatColor.valueOf(color.toUpperCase());
					
					ItemStack is = new ItemCreator(Material.getMaterial(a.toUpperCase()))
							.setDamage(id)
							.setName(cc + name)
							.build();
					
					if(!(items.contains(is))) {
						items.add(is);
					} 
				}
			}
			return items;
		}
		return items;
	}
}
