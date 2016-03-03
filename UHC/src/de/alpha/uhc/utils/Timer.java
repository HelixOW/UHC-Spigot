package de.alpha.uhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.alpha.border.Border;
import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.OptionsFileManager;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.kits.KitFileManager;
import de.alpha.uhc.manager.BorderManager;
import de.alpha.uhc.manager.ScoreboardManager;
import de.alpha.uhc.manager.TitleManager;
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
	 public	static int max;
	
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
									
									for(Player all : Core.getInGamePlayers()) {
										
										all.setLevel(high);
										
										if(high % 10 == 0 && high > 10 && high != 0) {
											countmsg = countmsg.replace("[time]", Integer.toString(high));
											all.sendMessage(Core.getPrefix() + countmsg);
											TitleManager.sendTitle(all, 10, 20, 10, " ", countmsg);
											all.playSound(all.getLocation(), Sound.NOTE_BASS, 1F, 0F);
											Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
												
												@Override
												public void run() {
													
													countmsg = MessageFileManager.getMSGFile().getColorString("Announcements.Countdown");
													
												}
											}, 2);
										}
										
										if(high % 1 == 0 && high < 10 && high != 0) {
											
											countmsg = countmsg.replace("[time]", Integer.toString(high));
											all.sendMessage(Core.getPrefix() + countmsg);
											TitleManager.sendAction(all, countmsg);
											all.playSound(all.getLocation(), Sound.NOTE_BASS, 1F, 0F);
											Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
												
												@Override
												public void run() {
													
													countmsg = MessageFileManager.getMSGFile().getColorString("Announcements.Countdown");
												}
											}, 2);
										}
											
											
										if(high == 0) {
											
											a.cancel();
											
											LobbyPasteUtil.removeLobby();
													
											for(Player ig : Core.getInGamePlayers()) {
												
												if(WorldUtil.lobbySchematic == false) {
												
													if(SpawnFileManager.getSpawn() == null) {
														ig.teleport(ig.getWorld().getSpawnLocation());
													} else {
														Location l = SpawnFileManager.getSpawn();
														
														l = SpawnFileManager.getRandomLocation(l, l.getBlockX()-max,l.getBlockX()+max, l.getBlockZ()-max,l.getBlockZ()+max);
																
														ig.teleport(l);
													}
													
													Border.setDistanceLoc(SpawnFileManager.getSpawn());
													
												} else {
													Border.setDistanceLoc(SpawnFileManager.getLobby().getWorld().getHighestBlockAt(SpawnFileManager.getLobby()).getLocation());
												}
												b.cancel();
												
												all.playSound(all.getLocation(), Sound.NOTE_PIANO, 1F, 0F);
												all.getWorld().setGameRuleValue("naturalRegeneration", "false");
												grace = true;
												startGracePeriod();
												Border.border();
												GState.setGameState(GState.INGAME);
												
												if(!(LobbyListener.hasSelKit(ig))) {
													ig.getInventory().addItem(new ItemCreator(Material.APPLE).build());
												} else {
													for(ItemStack is : new KitFileManager().getContents(LobbyListener.getSelKit(ig)).getContents()) {
														if(is != null) {
															if(!(ig.getInventory().contains(is))) {
																ig.getInventory().addItem(is);
															}
														}
													}
												}
												
												ScoreboardManager.setInGameBoard(ig);
								
												new BukkitRunnable() {
													
													@Override
													public void run() {
														return;
														
													}
												}.runTaskLater(Core.getInstance(), 10);
												
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
								gracemsg = gracemsg.replace("[time]", Integer.toString(gracetime));
								Bukkit.broadcastMessage(Core.getPrefix() + gracemsg);
								gracemsg = MessageFileManager.getMSGFile().getColorString("Announcements.Peaceperiod.timer");
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
	
	public static void changeTime() {
		high = 10;
	}
	
	public static void setCountdownTime() {
		high = OptionsFileManager.getConfigFile().getInt("Countdown.lobby");
		gracetime = OptionsFileManager.getConfigFile().getInt("Countdown.graceperiod");
	}
	
	private static void giveCompass(Player p) {
		p.getInventory().addItem(new ItemStack(Material.COMPASS));
	}
	
}
