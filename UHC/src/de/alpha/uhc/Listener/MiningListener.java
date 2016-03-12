package de.alpha.uhc.Listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class MiningListener implements Listener {
	
	public static boolean wood;
	public static boolean ore;
	
	public static boolean coal;
	public static boolean iron;
	public static boolean gold;
	public static boolean dia;
	
	@EventHandler
	public void onMine(BlockBreakEvent e) {
		
		Block b = e.getBlock();
		Material m = b.getType();
		
		if(m.equals(Material.LOG) || m.equals(Material.LOG_2)) {
			
			if(wood == true) {

				if(e.getPlayer() != null) {
					e.setCancelled(true);
					boolean inWood = false;
					
					for(int y = -13; y <= 13; y++) {
						Location loc = e.getBlock().getLocation().add(0, y, 0);
						if(loc.getBlock().getType() == Material.LOG || loc.getBlock().getType() == Material.LOG_2) {
							
							if(!inWood) {
								inWood = true;
							}
							loc.getBlock().breakNaturally();
						}
					}
				}
			}
		}
		
		if(ore == true) {
		
			if(m.equals(Material.IRON_ORE)) {
				if(iron == false) {
					e.setCancelled(true);
					return;
				}
				b.setType(Material.AIR);
				b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.IRON_INGOT , 2));
				e.setCancelled(true);
			}
			
			if(m.equals(Material.GRAVEL)) {
				b.setType(Material.AIR);
				b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.ARROW));
				e.setCancelled(true);
			}
			
			if(m.equals(Material.GOLD_ORE)) {
				if(gold == false) {
					e.setCancelled(true);
					return;
				}
				b.setType(Material.AIR);
				b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.GOLD_INGOT, 5));
				e.setCancelled(true);
			}
			
			if(m.equals(Material.DIAMOND_ORE)) {
				if(dia == false) {
					e.setCancelled(true);
					return;
				}
				b.setType(Material.AIR);
				b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.DIAMOND, 7));
				e.setCancelled(true);
			}
			
			if(m.equals(Material.COAL_ORE)) {
				if(coal == false) {
					e.setCancelled(true);
					return;
				}
				b.setType(Material.AIR);
				b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.COAL, 2));
				e.setCancelled(true);
			}
		}
		
	}

}
