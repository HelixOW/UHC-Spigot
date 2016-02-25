package de.alpha.uhc.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.alpha.uhc.Core;

public class Spectator implements Listener{
	
	public void setSpec(Player p) {
		
		Core.addSpec(p);
		p.setCanPickupItems(false);
		p.setFoodLevel(20);
		p.setHealth(20);
		p.setVelocity(p.getVelocity().setY(20D));
		p.setTotalExperience(0);
		p.setGameMode(GameMode.SPECTATOR);
		
		p.setPlayerListName("§7[§4X§7] §c" + p.getDisplayName());
		
		
	}
	
	@EventHandler
	public void onDmg(EntityDamageEvent e){
		
		if(!(e.getEntity() instanceof Player)){
			return;
		}
		Player p = (Player) e.getEntity();
		if(Core.getSpecs().contains(p)){
			e.setCancelled(true);
		}
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
	

}
