package de.alpha.uhc.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.alpha.uhc.GState;

public class SoupListener implements Listener {
	
	public static double boost;
	
	@EventHandler
	public void onSoup(PlayerInteractEvent e) {
		
		if(GState.isState(GState.INGAME)) {
			
			Player p = e.getPlayer();
			
			if(p.getItemInHand().getType().equals(Material.MUSHROOM_SOUP)) {
				
				if(e.getAction().equals(Action.RIGHT_CLICK_AIR) 
						|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					
					if(p.getHealth() == 20) {
						return;
					}
					
					p.getItemInHand().setType(Material.BOWL);
					if(p.getHealth() <= 19 - boost) {
						p.setHealth(p.getHealth() + boost);
					} else {
						p.setHealth(20);
					}
					
				}
			}
			
		}
		
	}

}
