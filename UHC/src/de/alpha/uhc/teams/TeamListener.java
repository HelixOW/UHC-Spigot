package de.alpha.uhc.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.files.MessageFileManager;

public class TeamListener implements Listener {
	
	public static String inTeam;
	public static String full;
	public static String choosen;
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getDamager() instanceof Player)) return;
		if(GState.isState(GState.LOBBY)) return;
		
		Player p = (Player) e.getEntity();
		Player d = (Player) e.getDamager();
		
		if(TeamManager.hasTeam(p) == false) return;
		
		if(TeamManager.getTeam(p).equals(TeamManager.getTeam(d))) {
			e.setCancelled(true);
			
			if(Core.getInGamePlayers().size() <= TeamManager.getTeam(p).getAllPlayers().size()) {
				e.setCancelled(false);
			}
			
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
		if(e.getItem() == null) return;
		if(GState.isState(GState.INGAME)) return;
		if(!(e.getItem().getType().equals(Material.getMaterial(TeamSel.m.toUpperCase())))) return;
		
		e.setCancelled(true);
		TeamSel.open(e.getPlayer());
		
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		
		Inventory inv = e.getInventory();
		Player p = (Player) e.getWhoClicked();
		
		if(inv.getTitle().equals(TeamSel.title)) {
			if(e.getCurrentItem() == null) {
				return;
			}
			
			if(e.getCurrentItem().getType().equals(TeamSel.block)) {
				Team t = TeamManager.getTeamByColor(DyeColor.getByData((byte) e.getCurrentItem().getDurability()));
				
				if(t == null) {
					e.setCancelled(true);
					return;
				}
			
				if(t.equals(TeamManager.getTeam(p))) {
					p.sendMessage(Core.getPrefix() + inTeam);
					e.setCancelled(true);
					return;
				}				
				
				if(!(TeamManager.hasSpaceForPlayer(t))) {
					p.sendMessage(Core.getPrefix() + full);
					e.setCancelled(true);
					return;
				}
				
				TeamManager.addPlayerToTeam(p, t);
				choosen = choosen.replace("[team]", t.getName());
				p.sendMessage(Core.getPrefix() + choosen);
				choosen = MessageFileManager.getMSGFile().getColorString("Teams.chosen");
				
				p.getInventory().setItem(1, e.getCurrentItem());
				for(Player o : Bukkit.getOnlinePlayers()) {
					if(TeamManager.hasTeam(o)) {
						o.setPlayerListName(ChatColor.valueOf(TeamManager.getTeam(o).getTeamColor().toString())+ o.getName());
						o.setDisplayName(ChatColor.valueOf(TeamManager.getTeam(o).getTeamColor().toString())+ o.getName());
					}
				}
				p.setPlayerListName(ChatColor.valueOf(t.getTeamColor().toString())+ p.getName());
				p.setDisplayName(ChatColor.valueOf(t.getTeamColor().toString())+ p.getName());
				p.closeInventory();
				return;
			}
			if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
				e.setCancelled(true);
				return;
			}
		}
	}

}
