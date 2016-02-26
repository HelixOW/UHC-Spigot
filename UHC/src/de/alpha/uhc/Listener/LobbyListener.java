package de.alpha.uhc.Listener;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.files.KitFileManager;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.kits.GUI;
import de.alpha.uhc.manager.ScoreboardManager;
import de.alpha.uhc.utils.Regions;
import de.alpha.uhc.utils.Stats;

public class LobbyListener implements Listener {
	
	public static String sel;
	public static String bought;
	public static String coinsneed;
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		
		if(GState.isState(GState.LOBBY)) {
			e.setFoodLevel(20);
		}
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(!(e.getFrom().equals(e.getTo()))) return;
		if(!(Regions.isInRegion(e.getTo()))) {
			Vector plV = p.getLocation().toVector();
			Vector spV = p.getWorld().getSpawnLocation().toVector();
			Vector v = spV.clone().subtract(plV).multiply(2.0 / spV.distance(plV)).setY(0.5);
			p.setVelocity(v);
			p.getWorld().playSound(p.getLocation(), Sound.BURP, 1F, 0.8F);
		}
		
	}
	
	@EventHandler
	public void onHurt(EntityDamageEvent e) {
		
		if(e.getEntity() instanceof Player) {
			if(GState.isState(GState.LOBBY)) {
				e.setCancelled(true);
			}
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockBreakEvent e) {
		
		if(GState.isState(GState.LOBBY)) {
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		if(GState.isState(GState.LOBBY)) {
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onInterAct(PlayerInteractEvent e) {
		
		if(!(GState.isState(GState.LOBBY))) return;
		if(e.getItem() == null) return;
		if(!(e.getItem().getType().equals(PlayerJoinListener.kitItem))) return;
		
		e.setCancelled(true);
		GUI.open(e.getPlayer());
		
	}
	
	private static HashMap<Player, String> kit = new HashMap<Player, String>();
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		
		if(e.getClickedInventory() == null) return;
		if(!(e.getClickedInventory().getName().equalsIgnoreCase(GUI.title))) return;
		if(!(e.getWhoClicked() instanceof Player)) return;
		
		Player p = (Player) e.getWhoClicked();
		
		e.setCancelled(true);
		
		for(String kits : new KitFileManager().getAllKits()) {
			if(e.getCurrentItem().getType().equals(Material.getMaterial(new KitFileManager().getMaterial(kits).toUpperCase()))) {
				if(new Stats(p).getKits().contains(kits)) {
					kit.put(p, kits);
					sel = sel.replace("[Kit]", kits);
					p.sendMessage(Core.getPrefix() + sel);
					sel = MessageFileManager.getMSGFile().getColorString("Kits.GUI.Selected");
					p.closeInventory();
					break;
				} else if(new Stats(p).getCoins() >= new KitFileManager().getPrice(kits)) {
					new Stats(p).removeCoins(new KitFileManager().getPrice(kits));
					new Stats(p).addKit(kits);
					if(kit.containsKey(p)) {
						kit.remove(p);
					}
					kit.put(p, kits);
					bought = bought.replace("[Kit]", kits);
					bought = bought.replace("[Coins]", Integer.toString(new KitFileManager().getPrice(kits)));
					p.sendMessage(Core.getPrefix() + bought);
					bought = MessageFileManager.getMSGFile().getColorString("Kits.GUI.Bought");
					p.closeInventory();
					ScoreboardManager.setLobbyBoard(p);
					break;
				} else {
					p.sendMessage(Core.getPrefix()+coinsneed);
				}
			}
		}
	}
	
	public static boolean hasSelKit(Player p) {
		if(kit.containsKey(p)) {
			return true;
		}
		return false;
	}
	
	public static String getSelKit(Player p) {
		if(kit.containsKey(p)) {
			return kit.get(p);
		}
		return "";
	}
	
}
