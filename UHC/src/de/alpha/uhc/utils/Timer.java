package de.alpha.uhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.files.KitFileManager;
import de.alpha.uhc.files.OptionsFileManager;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.manager.BorderManager;
import de.alpha.uhc.manager.ScoreboardManager;
import net.minetopix.library.main.item.ItemCreator;


public class Timer {
	
	public static String countmsg;
	public static  String nep;
	public static  String gracemsg;
	public static  String end;
	
	public static  boolean grace;
	
	public static  int pc;
	
	 static int high;
	 static int gracetime;
	
	private static   BukkitTask a;
	private  static  BukkitTask b;
	
	private  static  BukkitTask c;
	private static   BukkitTask e;
	
	
	public static void startCountdown() {
		
		if(GState.isState(GState.LOBBY)) {
			
			a = new BukkitRunnable() {
				
				@Override
				public void run() {
				
					if(high > 0) {
						
						high--;
						
						b = new BukkitRunnable() {
							
							@Override
							public void run() {
								
								if(Bukkit.getOnlinePlayers().size() >= pc) {
									
									for(final Player all : Bukkit.getOnlinePlayers()) {
										
										if(high % 10 == 0 && high > 10 && high != 0) {
											Bukkit.broadcastMessage(Core.getPrefix() + countmsg + "§7" + high + "§8 seconds");
											all.playSound(all.getLocation(), Sound.NOTE_BASS, 1F, 0F);
											return;
										}
										
										if(high % 1 == 0 && high < 10 && high != 0) {
											
											Bukkit.broadcastMessage(Core.getPrefix() + countmsg + "§7" + high + "§8 seconds");
											all.playSound(all.getLocation(), Sound.NOTE_BASS, 1F, 0F);
											return;
										}
											
											
										if(high == 0) {
											
											a.cancel();
													
											for(Player ig : Core.getInGamePlayers()) {
												
												if(SpawnFileManager.getSpawn() == null) {
													ig.teleport(ig.getWorld().getSpawnLocation());
												} else {
													ig.teleport(SpawnFileManager.getSpawn());
												}
												b.cancel();
												
												all.getWorld().setGameRuleValue("naturalRegeneration", "false");
												grace = true;
												startGracePeriod();
												GState.setGameState(GState.INGAME);
												
												if(!(LobbyListener.hasSelKit(ig))) {
													ig.getInventory().addItem(new ItemCreator(Material.APPLE).build());
												} else {
													for(ItemStack is : new KitFileManager().getContents(LobbyListener.getSelKit(ig)).getContents()) {
														if(is != null) {
															ig.getInventory().addItem(is);
														}
													}
												}
												
												ScoreboardManager.setInGameBoard(ig);
												new BukkitRunnable() {
													
													@Override
													public void run() {
														return;
														
													}
												}.runTaskLater(Core.getInstance(), 0);
												
											}
												
										}
									}
								} else {
									Bukkit.broadcastMessage(Core.getPrefix() + nep);
									a.cancel();
									b.cancel();
									return;
								}
		
							}
						}.runTask(Core.getInstance());
					
					}
				}
			}.runTaskTimer(Core.getInstance(), 0, 20);
		}
	}
	
	public static void startGracePeriod() {
		
		if(GState.isState(GState.INGAME)) {
			return;
		}
		for(Player all : Core.getInGamePlayers()) {
			for(Entity kill : all.getWorld().getEntities()) {
				
				if(!(kill instanceof Player)) {
					kill.remove();
				}
			}
			all.getInventory().clear();
		}
		
		c = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(gracetime > 0) {
					
					gracetime--;
				
					e = new BukkitRunnable() {
						
						@Override
						public void run() {
							
							if(gracetime % 10 == 0 && gracetime > 0) {
								
								Bukkit.broadcastMessage(Core.getPrefix() + gracemsg + "§7 " + gracetime + "§8 seconds");
								return;
							}
							
							if(gracetime == 0) {
								
								e.cancel();
								
								grace = false;
								Bukkit.broadcastMessage(Core.getPrefix() + end);
								new BorderManager().set();
								for(Player all : Core.getInGamePlayers()) {
									giveCompass(all);
								}
								c.cancel();
								return;
							}
							
						}
					}.runTask(Core.getInstance());
				}
			}
		}.runTaskTimer(Core.getInstance(), 0, 20);
	}
	
	public static void setCountdownTime() {
		high = new OptionsFileManager().getConfigFile().getInt("Countdown.lobby");
		gracetime = new OptionsFileManager().getConfigFile().getInt("Countdown.graceperiod");
	}
	
	private static void giveCompass(Player p) {
		p.getInventory().addItem(new ItemStack(Material.COMPASS));
	}
	
}
