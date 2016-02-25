package de.alpha.uhc.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener {
	
	public static boolean mobs;
	
	@EventHandler
	public void onDie(EntityDeathEvent e) {
		
		Entity p = e.getEntity();
		
		if(p instanceof Player) {
			return;
		}
		
		if(mobs == true) {
			
			if(p instanceof Pig) {
				e.getDrops().clear();
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.GRILLED_PORK, 3));
			}
			
			if(p instanceof Cow) {
				e.getDrops().clear();
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.COOKED_BEEF, 6));
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.LEATHER));
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.GRILLED_PORK, 3));
			}
			
			if(p instanceof Chicken) {
				e.getDrops().clear();
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.COOKED_CHICKEN, 3));
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.ARROW, 2));
			}
			
			if(p instanceof Spider) {
				e.getDrops().clear();
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.SPIDER_EYE));
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.STRING, 2));
			}
			
			if(p instanceof Skeleton) {
				e.getDrops().clear();
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.BOW));
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.ARROW, 3));
			}
			
			if(p instanceof Sheep) {
				e.getDrops().clear();
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.COOKED_MUTTON, 3));
			}
			
			if(p instanceof Rabbit) {
				e.getDrops().clear();
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.COOKED_RABBIT, 3));
			}
			
		}
		
	}

}
