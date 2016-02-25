package de.alpha.uhc.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftListener implements Listener {
	
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		
		Material r = e.getRecipe().getResult().getType();
		Player p = (Player) e.getWhoClicked();
		
		if(r.equals(Material.WOOD_AXE)) {
			e.setCancelled(true);
			e.getRecipe().getResult().setType(Material.AIR);
			e.getInventory().clear();
			p.getInventory().addItem(new ItemStack(Material.STONE_AXE));
			
		}
		
		if(r.equals(Material.WOOD_HOE)) {
			e.setCancelled(true);
			e.getRecipe().getResult().setType(Material.AIR);
			e.getInventory().clear();
			p.getInventory().addItem(new ItemStack(Material.STONE_HOE));
			
		}
		
		if(r.equals(Material.WOOD_SPADE)) {
			e.setCancelled(true);
			e.getRecipe().getResult().setType(Material.AIR);
			e.getInventory().clear();
			p.getInventory().addItem(new ItemStack(Material.STONE_SPADE));
			
		}
		
		if(r.equals(Material.WOOD_PICKAXE)) {
			e.setCancelled(true);
			e.getRecipe().getResult().setType(Material.AIR);
			e.getInventory().clear();
			p.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE));
			
		}
		
		if(r.equals(Material.STONE_SWORD)) {
			if(!(p.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 2))) {
				e.setCancelled(true);
				e.getInventory().clear();
				e.getRecipe().getResult().setType(Material.AIR);
				p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
			}
		}
		
	}

}
