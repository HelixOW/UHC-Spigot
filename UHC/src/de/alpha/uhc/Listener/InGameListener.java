package de.alpha.uhc.Listener;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.manager.ScoreboardManager;
import de.alpha.uhc.manager.TitleManager;
import de.alpha.uhc.utils.Spectator;
import de.alpha.uhc.utils.Stats;
import de.alpha.uhc.utils.Timer;
import net.minetopix.library.main.item.data.SkullData;
import net.minetopix.library.main.item.data.WrongDataException;

public class InGameListener implements Listener {
	
	public static String death;
	public static String quit;
	public static String win;
	public static String kick;
	public static String ntrack;
	public static String track;
	public static String rew;
	
	private int apc;
	public static  int opc;
	public static int size;
	public static int reward;
	public static int deathreward;
	
	public static boolean newWorld;
	
	
	public ArrayList<Player> ig = new ArrayList<Player>();
	
	@EventHandler
	public void onDMG(EntityDamageEvent e) {
		
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		if(Timer.grace == true) {
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		Player p = e.getEntity();
		death = death.replace("[Player]", p.getDisplayName());
		
		Core.removeInGamePlayer(p);
		
		apc = Core.getInGamePlayers().size();
		
		death = death.replace("[PlayerCount]", "§7["+apc+" left]");
		
		e.setDeathMessage(Core.getPrefix() + death);
		
		death =  MessageFileManager.getMSGFile().getColorString("Announcements.Death");
		
		new Spectator().setSpec(p);
		
		if(p.getKiller() instanceof Player) {
			new Stats(p.getKiller()).addKill();
		}
		
		new Stats(p).addDeath();
		rew = rew.replace("[Coins]", Integer.toString(deathreward));
		new Stats(p).addCoins(deathreward);
		p.sendMessage(Core.getPrefix() + rew);
		TitleManager.sendTitle(p, 10, 20, 10, " ", rew);
		rew = MessageFileManager.getMSGFile().getColorString("Reward");
		for(Player all : Bukkit.getOnlinePlayers()) {
			ScoreboardManager.setInGameBoard(all);
		}
		
		p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
		ItemStack skull = new ItemStack(Material.SKULL_ITEM);
		try {
			new SkullData(p.getName()).applyOn(skull);
		} catch (WrongDataException e1) {
			e1.printStackTrace();
		}
		
		p.getWorld().dropItem(p.getLocation(), skull);
		
		p.setHealth(20);
		p.getWorld().strikeLightningEffect(p.getLocation());
		
		if(Core.getInGamePlayers().size() <= 1) {
			if(newWorld == true) {
				delete = p.getWorld();
			}
			if(Core.getInGamePlayers().size() == 0) {
				new Core();
				new BukkitRunnable() {
					
					@Override
					public void run() {
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.kickPlayer(Core.getPrefix() + kick);
						}
						if(newWorld == true) {
							File deleteFolder = delete.getWorldFolder();
							deleteWorld(deleteFolder);
						}
						
					}
				}.runTaskLater(Core.getInstance(), 200);
				
				new BukkitRunnable() {
					
					@Override
					public void run() {

						Bukkit.reload();

					}
				}.runTaskLater(Core.getInstance(), 240);
				return;
			}
			
			for(Player winner: Core.getInGamePlayers()) {
				
				GState.setGameState(GState.LOBBY);
				
				win = win.replace("[Player]", winner.getDisplayName());
				
				Bukkit.broadcastMessage(Core.getPrefix() + win);
				for(Player all : Bukkit.getOnlinePlayers()) {
					TitleManager.sendTitle(all, 10, 20, 10, " ", win);
				}
				new Stats(winner).addCoins(reward);
				rew = rew.replace("[Coins]", Integer.toString(reward));
				winner.sendMessage(Core.getPrefix() + rew);
				rew = MessageFileManager.getMSGFile().getColorString("Reward");
				
				win = MessageFileManager.getMSGFile().getColorString("Announcements.Win");
				if(newWorld == true) {
					delete = winner.getWorld();
				}
				new BukkitRunnable() {
					
					@Override
					public void run() {
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.kickPlayer(Core.getPrefix() + kick);
						}
						if(newWorld == true) {
							File deleteFolder = delete.getWorldFolder();
							deleteWorld(deleteFolder);
						}
						
					}
				}.runTaskLater(Core.getInstance(), 200);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						Bukkit.getConsoleSender().sendMessage("§cPlaying World unloaded.");
						Bukkit.reload();
						
					}
				}, 240);
				
			}
		}
	}
	
	private World delete;
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		quit = quit.replace("[Player]", p.getDisplayName());
		
		e.setQuitMessage(null);
		
		Core.removeInGamePlayer(p);
		Core.removeSpec(p);
		
		if(GState.isState(GState.LOBBY)) {
			apc = Bukkit.getOnlinePlayers().size() - 1;
		} else {
			apc = Core.getInGamePlayers().size();
		}
		
		quit = quit.replace("[PlayerCount]", "§7["+apc+" left]");
		
		Bukkit.broadcastMessage(Core.getPrefix() + quit);
		
		quit = MessageFileManager.getMSGFile().getColorString("Announcements.Leave");
		
		p.getInventory().clear();
		
		if(GState.isState(GState.INGAME)) {
			p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
			
			ItemStack skull = new ItemStack(Material.SKULL_ITEM);
			try {
				new SkullData(p.getName()).applyOn(skull);
			} catch (WrongDataException e1) {
				e1.printStackTrace();
			}
			
			for(Player all : Bukkit.getOnlinePlayers()) {
				ScoreboardManager.setInGameBoard(all);
			}
			
			p.getWorld().dropItem(p.getLocation(), skull);
			
			p.getWorld().strikeLightningEffect(p.getLocation());
			
			p.setGameMode(GameMode.SURVIVAL);
			
			if(Core.getInGamePlayers().size() <= 1) {
				if(newWorld == true) {
					delete = p.getWorld();
				}
				if(Core.getInGamePlayers().size() == 0) {
					new BukkitRunnable() {
						
						@Override
						public void run() {
							
							for(Player all : Bukkit.getOnlinePlayers()) {
								all.kickPlayer(Core.getPrefix() + kick);
							}
							if(newWorld == true) {
								File deleteFolder = delete.getWorldFolder();
								deleteWorld(deleteFolder);
							}
							
						}
					}.runTaskLater(Core.getInstance(), 200);
					
					new BukkitRunnable() {
						
						@Override
						public void run() {

							Bukkit.reload();

						}
					}.runTaskLater(Core.getInstance(), 240);
					return;
				}
				
				for(Player winner: Core.getInGamePlayers()) {
					
					GState.setGameState(GState.LOBBY);
					
					win = win.replace("[Player]", winner.getDisplayName());
					
					Bukkit.broadcastMessage(Core.getPrefix() + win);
					for(Player all : Bukkit.getOnlinePlayers()) {
						TitleManager.sendTitle(all, 10, 20, 10, " ", win);
					}
					new Stats(winner).addCoins(reward);
					rew = rew.replace("[Coins]", Integer.toString(reward));
					winner.sendMessage(Core.getPrefix() + rew);
					rew = MessageFileManager.getMSGFile().getColorString("Reward");
					
					win = MessageFileManager.getMSGFile().getColorString("Announcements.Win");
					
					if(newWorld == true) {
						delete = winner.getWorld();
					}
					new BukkitRunnable() {
						
						@Override
						public void run() {
							
							for(Player all : Bukkit.getOnlinePlayers()) {
								all.kickPlayer(Core.getPrefix() + kick);
							}
							if(newWorld == true) {
								File deleteFolder = delete.getWorldFolder();
								deleteWorld(deleteFolder);
							}
						}
					}.runTaskLater(Core.getInstance(), 200);
					
					new BukkitRunnable() {
						
						@Override
						public void run() {

							Bukkit.getConsoleSender().sendMessage("§cPlaying World unloaded.");
							Bukkit.reload();

						}
					}.runTaskLater(Core.getInstance(), 240);
					
					
					
				}
			}
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		if(e.getMaterial().equals(Material.COMPASS)) {
			
			Player target = getNearest(p);
			if(target == null) {
				p.sendMessage(Core.getPrefix() + ntrack);
				return;
			}
			
			track = track.replace("[Player]", target.getDisplayName());
			
			int blocks = (int) p.getLocation().distance(getNearest(p).getLocation());
			
			track = track.replace("[distance]", Integer.toString(blocks));
			
			p.sendMessage(Core.getPrefix() + track);
			TitleManager.sendTitle(p, 10, 20, 10, " ", track);
			p.setCompassTarget(getNearest(p).getLocation());
			
			track = MessageFileManager.getMSGFile().getColorString("Compass.PlayerInRange");
			
		}
		
	}
	
	private Player getNearest(Player p) {
		
		double distance = Double.MAX_VALUE;
		Player target = null;
		
		for(Entity entity : p.getNearbyEntities(size, size, size)) {
			if(entity instanceof Player) {
				
				double dis = p.getLocation().distance(entity.getLocation());
				
				if(dis < distance) {
					distance = dis;
					target = (Player) entity;
				}
				
			}
		}
		return target;
		
	}
	
	public boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
}
