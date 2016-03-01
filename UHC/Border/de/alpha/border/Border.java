package de.alpha.border;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.alpha.uhc.Core;
import de.alpha.uhc.files.SpawnFileManager;

public class Border {
	
	public static int size;
	public static double dmg;
	
	private static int random(Integer d, Integer x) {
		Random r = new Random();
		return r.nextInt(x-d+1)+d;
	}
	
	public static void border() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(SpawnFileManager.getSpawn() != null) {
						if(p.getLocation().distance(SpawnFileManager.getSpawn()) >= size) {
							Vector plV = p.getLocation().toVector();
							Vector spV = SpawnFileManager.getSpawn().toVector();
							Vector v = spV.clone().subtract(plV).multiply(2.0 / spV.distance(plV)).setY(0.5);
							p.setVelocity(v);
							p.getWorld().playSound(p.getLocation(), Sound.BURP, 1F, 0.8F);
						}
					} else {
						if(p.getLocation().distance(p.getWorld().getSpawnLocation()) >= size) {
							Vector plV = p.getLocation().toVector();
							Vector spV = p.getWorld().getSpawnLocation().toVector();
							Vector v = spV.clone().subtract(plV).multiply(2.0 / spV.distance(plV)).setY(0.5);
							p.setVelocity(v);
							p.damage(dmg);
						}
					}
				}
				
			}
		}.runTaskTimer(Core.getInstance(), 0L, 15L);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(SpawnFileManager.getSpawn() != null) {
						if(p.getLocation().distance(SpawnFileManager.getSpawn()) >= size-15) {
							Location min = p.getLocation().add(-10, -10, -10);
							Location max = p.getLocation().add(10, 10, 10);
							for(int x = min.getBlockX(); x < max.getBlockX(); x++) {
								for(int y = min.getBlockY(); y < max.getBlockY(); y++) {
									for(int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
										Location loc = new Location(p.getWorld(), x, y, z);
										if(loc.distance(SpawnFileManager.getSpawn()) > size && loc.distance(SpawnFileManager.getSpawn()) < size+1) {
											if(random(0, 20) == 0) {
												for(int i = 0; i < 10; i++) {
													p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
													p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
													p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
												}
											}
										}
									}
								}
							}
						}
					} else {
						if(p.getLocation().distance(p.getWorld().getSpawnLocation()) >= size-15) {
							Location min = p.getLocation().add(-10, -10, -10);
							Location max = p.getLocation().add(10, 10, 10);
							for(int x = min.getBlockX(); x < max.getBlockX(); x++) {
								for(int y = min.getBlockY(); y < max.getBlockY(); y++) {
									for(int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
										Location loc = new Location(p.getWorld(), x, y, z);
										if(loc.distance(p.getWorld().getSpawnLocation()) > size && loc.distance(p.getWorld().getSpawnLocation()) < size+1) {
											if(random(0, 20) == 0) {
												for(int i = 0; i < 10; i++) {
													p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
													p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
													p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
			}
		}.runTaskTimer(Core.getInstance(), 0L, 15L);
		
	}

	
	public void changesize(int newSize) {
		size = newSize;
	}
}
