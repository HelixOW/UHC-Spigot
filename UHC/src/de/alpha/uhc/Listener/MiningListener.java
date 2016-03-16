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
	public static boolean coal;
	public static boolean iron;
	public static boolean gold;
	public static boolean dia;
	public static boolean gravel;
	
	public static int coalA;
	public static int ironA;
	public static int goldA;
	public static int diaA;
	public static int gravelA;
	
	public static Material coalM;
	public static Material ironM;
	public static Material goldM;
	public static Material diaM;
	public static Material gravelM;
	
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
		
		if(m.equals(Material.IRON_ORE)) {
			if(iron == false) {
				e.setCancelled(true);
				return;
			}
			b.setType(Material.AIR);
			b.getWorld().dropItem(b.getLocation(), new ItemStack(ironM , ironA));
			e.setCancelled(true);
		}
			
		if(m.equals(Material.GRAVEL)) {
			if(gravel == false) return;
			b.setType(Material.AIR);
			b.getWorld().dropItem(b.getLocation(), new ItemStack(gravelM , gravelA));
			e.setCancelled(true);
		}
		
		if(m.equals(Material.GOLD_ORE)) {
			if(gold == false) {
				e.setCancelled(true);
				return;
			}
			b.setType(Material.AIR);
			b.getWorld().dropItem(b.getLocation(), new ItemStack(goldM, goldA));
			e.setCancelled(true);
		}
			
		if(m.equals(Material.DIAMOND_ORE)) {
			if(dia == false) {
				e.setCancelled(true);
				return;
			}
			b.setType(Material.AIR);
			b.getWorld().dropItem(b.getLocation(), new ItemStack(diaM, diaA));
			e.setCancelled(true);
		}
			
		if(m.equals(Material.COAL_ORE)) {
			if(coal == false) {
				e.setCancelled(true);
				return;
			}
			b.setType(Material.AIR);
			b.getWorld().dropItem(b.getLocation(), new ItemStack(coalM, coalA));
			e.setCancelled(true);
		}
	}
}
