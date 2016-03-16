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
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener {
	
	public static boolean pig;
	public static boolean zombie;
	public static boolean cow;
	public static boolean chicken;
	public static boolean spider;
	public static boolean skeleton;
	public static boolean sheep;
	public static boolean rabbit;
	
	public static int pigA;
	public static int zombieA;
	public static int cowA;
	public static int chickenA;
	public static int spiderA;
	public static int skeletonA;
	public static int sheepA;
	public static int rabbitA;
	
	public static Material pigM;
	public static Material zombieM;
	public static Material cowM;
	public static Material chickenM;
	public static Material spiderM;
	public static Material skeletonM;
	public static Material sheepM;
	public static Material rabbitM;
	
	@EventHandler
	public void onDie(EntityDeathEvent e) {
		
		Entity p = e.getEntity();
		
		if(p instanceof Player) {
			return;
		}
		
		if(p instanceof Pig) {
			if(!(pig)) return;
			e.getDrops().clear();
			p.getWorld().dropItem(p.getLocation(), new ItemStack(pigM, pigA));
		}
			
		if(p instanceof Zombie) {
			if(!(zombie)) return;
			e.getDrops().clear();
			p.getWorld().dropItem(p.getLocation(), new ItemStack(zombieM, zombieA));
		}
			
		if(p instanceof Cow) {
			if(!(cow)) return;
			e.getDrops().clear();
			p.getWorld().dropItem(p.getLocation(), new ItemStack(cowM, cowA));
		}
			
		if(p instanceof Chicken) {
			if(!(chicken)) return;
			e.getDrops().clear();
			p.getWorld().dropItem(p.getLocation(), new ItemStack(chickenM, chickenA));
		}
			
		if(p instanceof Spider) {
			if(!(spider)) return;
			e.getDrops().clear();
			p.getWorld().dropItem(p.getLocation(), new ItemStack(spiderM, spiderA));
		}
			
		if(p instanceof Skeleton) {
			if(!(skeleton)) return;
			e.getDrops().clear();
			p.getWorld().dropItem(p.getLocation(), new ItemStack(skeletonM, skeletonA));
		}
			
		if(p instanceof Sheep) {
			if(!(sheep)) return;
			e.getDrops().clear();
			p.getWorld().dropItem(p.getLocation(), new ItemStack(sheepM, sheepA));
		}
			
		if(p instanceof Rabbit) {
			if(!(rabbit)) return;
			e.getDrops().clear();
			p.getWorld().dropItem(p.getLocation(), new ItemStack(rabbitM, rabbitA));
		}
	}
}
