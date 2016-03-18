package de.alpha.uhc.timer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.alpha.border.Border;
import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.aclasses.AScoreboard;
import de.alpha.uhc.aclasses.ATablist;
import de.alpha.uhc.aclasses.AWorld;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.OptionsFileManager;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.kits.KitFileManager;
import de.alpha.uhc.manager.BorderManager;
import de.alpha.uhc.manager.TitleManager;
import de.alpha.uhc.utils.LobbyPasteUtil;


public class Timer {
	
	public static String countmsg;
	public static  String nep;
	public static  String gracemsg;
	public static  String end;
	public static String endmsg;
	
	public static  boolean grace;
	
	public static  int pc;
	
	 static int high;
	 static int gracetime;
	 public	static int max;
	 
	private static int endTime;
	
	private static   BukkitTask a;
	private  static  BukkitTask b;
	
	private  static  BukkitTask c;
	private static   BukkitTask e;
	
	private static BukkitTask f;
	
	public static boolean BungeeMode;
	public static String BungeeServer;
	public static String kick;
	
	public static boolean mcv;
	
	
	public static void startCountdown() {
		
		if(GState.isState(GState.LOBBY)) {
			
			SpawnFileManager.createSpawnWorld();
			
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
											if(mcv == false) {
												all.playSound(all.getLocation(), Sound.NOTE_BASS, 1F, 0F);
											}
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
											if(mcv == false) {
												all.playSound(all.getLocation(), Sound.NOTE_BASS, 1F, 0F);
											}
											Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
												
												@Override
												public void run() {
													
													countmsg = MessageFileManager.getMSGFile().getColorString("Announcements.Countdown");
												}
											}, 2);
										}
											
										if(high == 0) {
											
											a.cancel();
											
											if(AWorld.lobbyAsSchematic == true) {
												LobbyPasteUtil.removeLobby();
											}
													
											for(Player ig : Core.getInGamePlayers()) {
												
												if(AWorld.lobbyAsSchematic == false) {
													
													try {
												
														if(SpawnFileManager.getSpawn() == null) {
															ig.teleport(ig.getWorld().getSpawnLocation());
															Border.setDistanceLoc(ig.getWorld().getSpawnLocation());
														} else {
															Location l = SpawnFileManager.getSpawn();
															
															Location r = SpawnFileManager.getRandomLocation(l, l.getBlockX()-max,l.getBlockX()+max, l.getBlockZ()-max,l.getBlockZ()+max);
															
															ig.teleport(r);
															Border.setDistanceLoc(SpawnFileManager.getSpawn());
														}
													} catch (Exception e) {
														ig.teleport(ig.getWorld().getSpawnLocation());
														Border.setDistanceLoc(ig.getWorld().getSpawnLocation());
													}
													
												} else {
													Border.setDistanceLoc(SpawnFileManager.getLobby().getWorld().getHighestBlockAt(SpawnFileManager.getLobby()).getLocation());
												}
												b.cancel();
												
												if(mcv == false) {
													all.playSound(all.getLocation(), Sound.NOTE_PIANO, 1F, 0F);
												}
												all.getWorld().setGameRuleValue("naturalRegeneration", "false");
												grace = true;
												startGracePeriod();
												Border.border();
												GState.setGameState(GState.GRACE);

												ATablist.sendStandingInGameTablist();
												
												if(LobbyListener.hasSelKit(ig)) {
													for(ItemStack is : new KitFileManager().getContents(LobbyListener.getSelKit(ig)).getContents()) {
														if(is != null) {
															if(!(ig.getInventory().contains(is))) {
																ig.getInventory().addItem(is);
															}
														}
													}
												}
												
												AScoreboard.setInGameScoreboard(ig);
								
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
		
		if(GState.isState(GState.GRACE)) {
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
									all.showPlayer(all);
									giveCompass(all);
									GState.setGameState(GState.INGAME);

									ATablist.sendStandingInGameTablist();
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
	
	public static void startRestartTimer() {
		
		endTime = 10;
		GState.setGameState(GState.RESTART);
		
		ATablist.sendStandingInGameTablist();
		
		f = new BukkitRunnable() {
			
			@Override
			public void run() {
				if(endTime % 1 == 0 && endTime <= 10 && endTime != 0) {
					
					endmsg = endmsg.replace("[time]", Integer.toString(endTime));
					
					Bukkit.broadcastMessage(Core.getPrefix() + endmsg);
					endTime = endTime - 1;
					
					endmsg = MessageFileManager.getMSGFile().getColorString("Announcements.End");
				} 
				
				else if(endTime == 0) {
					
					for(Player all : Bukkit.getOnlinePlayers()) {
						if(BungeeMode == true) {
							ByteArrayDataOutput out = ByteStreams.newDataOutput();
							
							out.writeUTF("Connect");
							out.writeUTF(BungeeServer);
							
							all.sendPluginMessage(Core.getInstance(), "BungeeCord", out.toByteArray());
						} else {
							all.kickPlayer(Core.getPrefix() + kick);
						}
					}
					f.cancel();
					
				}
			}
		}.runTaskTimer(Core.getInstance(), 0 , 20);
		
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
