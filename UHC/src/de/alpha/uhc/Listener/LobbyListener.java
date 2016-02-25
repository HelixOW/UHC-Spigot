package de.alpha.uhc.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.alpha.uhc.GState;

public class LobbyListener implements Listener {
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		
		if(GState.isState(GState.LOBBY)) {
			e.setFoodLevel(20);
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
	public void onPlace(BlockPlaceEvent e) {
		
		if(GState.isState(GState.LOBBY)) {
			e.setCancelled(true);
		}
		
	}
	
	

}
