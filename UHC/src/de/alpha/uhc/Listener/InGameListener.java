package de.alpha.uhc.Listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
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
import de.alpha.uhc.utils.SkullData;
import de.alpha.uhc.utils.Spectator;
import de.alpha.uhc.utils.Stats;
import de.alpha.uhc.utils.Timer;
import de.alpha.uhc.utils.WrongDataException;

public class InGameListener implements Listener {
	
	public static String death;
	public static String quit;
	public static String win;
	public static String kick;
	public static String ntrack;
	public static String track;
	
	private int apc;
	public static  int opc;
	public static int size;
	
	
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
			if(Core.getInGamePlayers().size() == 0) {
				new Core();
				new BukkitRunnable() {
					
					@Override
					public void run() {
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.kickPlayer(Core.getPrefix() + kick);
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
				return;
			}
			
			for(Player winner: Core.getInGamePlayers()) {
				
				win = win.replace("[Player]", winner.getDisplayName());
				
				Bukkit.broadcastMessage(Core.getPrefix() + win);
				
				win = MessageFileManager.getMSGFile().getColorString("Announcements.Win");
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							all.kickPlayer(Core.getPrefix() + kick);
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
				
				Bukkit.getConsoleSender().sendMessage(""+Core.getInGamePlayers());
				
				if(Core.getInGamePlayers().size() == 0) {
					new BukkitRunnable() {
						
						@Override
						public void run() {
							
							for(Player all : Bukkit.getOnlinePlayers()) {
								all.kickPlayer(Core.getPrefix() + kick);
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
					return;
				}
				
				for(Player winner: Core.getInGamePlayers()) {
					
					win = win.replace("[Player]", winner.getDisplayName());
					
					Bukkit.broadcastMessage(Core.getPrefix() + win);
					
					win = MessageFileManager.getMSGFile().getColorString("Announcements.Win");
					
					new BukkitRunnable() {
						
						@Override
						public void run() {
							
							for(Player all : Bukkit.getOnlinePlayers()) {
								all.kickPlayer(Core.getPrefix() + kick);
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
	
}
