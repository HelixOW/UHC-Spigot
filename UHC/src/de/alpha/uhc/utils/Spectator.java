package de.alpha.uhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.alpha.uhc.Core;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.item.data.SkullData;

public class Spectator implements Listener{
	
	private static String specItem;
	private static String specName;
	private static String title;

	public static  String getSpecItem() {
		return specItem;
	}

	public static  void setSpecItem(String specItem) {
		Spectator.specItem = specItem;
	}

	public static  String getSpecName() {
		return specName;
	}

	public static  void setSpecName(String specName) {
		Spectator.specName = specName;
	}

	public static  String getTitle() {
		return title;
	}

	public static  void setTitle(String title) {
		Spectator.title = title;
	}

	public static void setSpec(Player p) {
		
		p.setCanPickupItems(false);
		p.setFoodLevel(20);
		p.setHealth(20);
		p.setVelocity(p.getVelocity().setY(20D));
		p.setTotalExperience(0);
		p.setGameMode(GameMode.ADVENTURE);
		p.setPlayerListName("§7[§4X§7] §c" + p.getDisplayName());
		p.setAllowFlight(true);
		p.setFlying(true);
		equipSpecStuff(p);
		for(Player ig : Core.getInGamePlayers()) {
			ig.hidePlayer(p);
		}
	}
	
	@EventHandler
	public void onDmg(EntityDamageEvent e){
		
		if(!(e.getEntity() instanceof Player)) return;
		
		Player p = (Player) e.getEntity();
		if(Core.getSpecs().contains(p)) e.setCancelled(true);
	}
	
	@EventHandler
	public void onHungerMeterChange(FoodLevelChangeEvent e){
		
		Player p = (Player) e.getEntity();
		
		if(Core.getSpecs().contains(p)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		
		Player p = e.getPlayer();
		
		if(Core.getSpecs().contains(p)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e){	
		
		if(!(e.getDamager() instanceof Player)) return;
		
		if(Core.getSpecs().contains(e.getDamager())){
			e.setCancelled(true);	
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e){
		if(Core.getSpecs().contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e){
		
		Player p = e.getPlayer();
		
		if(Core.getSpecs().contains(p)){
			e.setCancelled(true);
		}	
	}
	
	private static void equipSpecStuff(Player p) {
		p.getInventory().addItem(new ItemBuilder(Material.getMaterial(specItem.toUpperCase())).setName(specName).build());
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		
		if(!(Core.getSpecs().contains(p))) return;
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			
			if(p.getInventory().getItemInMainHand().getType().equals(Material.getMaterial(specItem.toUpperCase()))){
				
				Inventory inv = Bukkit.createInventory(null, 54, title);
				
				for(Player pl : Core.getInGamePlayers()) {
						
					ItemStack item = new ItemBuilder(Material.SKULL_ITEM).setDamage((short) 3).setName("§l§o" + pl.getDisplayName()).addItemData(new SkullData(pl.getName())).build();
						
					inv.addItem(item);
						
				}
				
				for(int i = 45; i < 54; i++) {
					inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 15).build());
				}
				
				p.openInventory(inv);
			}
		}
	}
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
	
		Player p = (Player) e.getWhoClicked();
		if(e.getClickedInventory() == null) return;
		Inventory inv = e.getClickedInventory();
		
		if(inv.getTitle().equals(title)) {
			
			if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()){
				
				String playername = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
				Player player = Bukkit.getPlayerExact(playername);
				
				if(player != null){
					
					e.setCancelled(true);
					p.teleport(player);
					p.closeInventory();
					
				}
				
			}
			
			if(e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
				e.setCancelled(true);
			}
		}
	}
	

}
