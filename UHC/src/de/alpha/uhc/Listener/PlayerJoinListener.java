package de.alpha.uhc.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.commands.UHCCommand;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.PlayerFileManager;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.manager.ScoreboardManager;
import de.alpha.uhc.manager.TitleManager;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.HoloUtil;
import de.alpha.uhc.utils.Spectator;
import net.minetopix.library.main.item.ItemCreator;
import net.minetopix.mysqlapi.MySQLManager;

public class PlayerJoinListener implements Listener {
	
	public static String join;
	public static String full;
	
	public static String header;
	public static String footer;
	
	public static String title;
	public static String subtitle;
	
	public static Material teamItem;
	public static String teamName;
	
	public static Material kitItem;
	public static String kitName;
	public static boolean kitMode;
	
	private int apc;
	public static int mpc;
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		
		e.setJoinMessage(null);
		
		if(GState.isState(GState.RESTART)) {
			e.getPlayer().kickPlayer(Core.getPrefix() + GameEndListener.kick);
			return;
		}
		
		header = header.replace("[player]", e.getPlayer().getDisplayName());
		header = header.replace("[playercount]", Integer.toString(Bukkit.getOnlinePlayers().size()));
		header = header.replace("[gamestatus]", GState.getGStateName());
		
		footer = footer.replace("[player]", e.getPlayer().getDisplayName());
		footer = footer.replace("[playercount]", Integer.toString(Bukkit.getOnlinePlayers().size()));
		footer = footer.replace("[gamestatus]", GState.getGStateName());
		
		if(GState.isState(GState.INGAME) || GState.isState(GState.GRACE)) {
			header = header.replace("[playercount]", Integer.toString(Core.getInGamePlayers().size()));
			footer = footer.replace("[playercount]", Integer.toString(Core.getInGamePlayers().size()));
			e.getPlayer().getInventory().clear();
			e.getPlayer().getInventory().setArmorContents(null);
			if(SpawnFileManager.getSpawn() == null) {
				e.getPlayer().teleport(Bukkit.getWorld("UHC").getSpawnLocation());
			} else {
				e.getPlayer().teleport(SpawnFileManager.getSpawn());
			}
			Core.addSpec(e.getPlayer());
			Spectator.setSpec(e.getPlayer());
			ScoreboardManager.setInGameBoard(e.getPlayer());
			TitleManager.sendTabTitle(e.getPlayer(), header, footer);
			for(Player all : Core.getInGamePlayers()) {
				ScoreboardManager.setInGameBoard(all);
			}
			return;
		}
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			header = header.replace("[player]", all.getDisplayName());
			header = header.replace("[playercount]", Integer.toString(Bukkit.getOnlinePlayers().size()));
			header = header.replace("[gamestatus]", GState.getGStateName());
			
			footer = footer.replace("[player]", all.getDisplayName());
			footer = footer.replace("[playercount]", Integer.toString(Bukkit.getOnlinePlayers().size()));
			footer = footer.replace("[gamestatus]", GState.getGStateName());
			
			
			TitleManager.sendTabTitle(all, "", "");
			TitleManager.sendTabTitle(all, header, footer);
			
			all.showPlayer(e.getPlayer());
			
			header = MessageFileManager.getMSGFile().getColorString("Tablist.Top");
			footer = MessageFileManager.getMSGFile().getColorString("Tablist.Bottom");
		}
		
		if(Core.isMySQLActive == true) {
			if(MySQLManager.getObjectConditionResult("UHC", "UUID", e.getPlayer().getUniqueId().toString(), "UUID") == null) {
				MySQLManager.exInsertQry("UHC", e.getPlayer().getName(), e.getPlayer().getUniqueId().toString(), "0", "0", "0", "");
			} else if(MySQLManager.getObjectConditionResult("UHC", "UUID ", e.getPlayer().getUniqueId().toString(), "UUID") != null) {
				MySQLManager.exUpdateQry("UHC", "UUID", e.getPlayer().getUniqueId().toString(), "Player", e.getPlayer().getName());
			}
		} else {
			if(!(PlayerFileManager.getPlayerFile().contains("Player: "+ e.getPlayer().getName()))) {
				new PlayerFileManager().addPlayer(e.getPlayer());
			}
		}
		
		if(Bukkit.getOnlinePlayers().size() == mpc+1) {
			e.getPlayer().kickPlayer(Core.getPrefix() + full);
		} 
		
		for(Entity kill : e.getPlayer().getWorld().getEntities()) {
			
			if(!(kill instanceof Player)) {
				kill.remove();
			}
			
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				apc = Bukkit.getOnlinePlayers().size();
				
				join = join.replace("[Player]", e.getPlayer().getDisplayName());
				join = join.replace("[PlayerCount]", "§7["+apc+"/"+mpc+"]");
				
				Bukkit.broadcastMessage(Core.getPrefix() + join);
				
				join = MessageFileManager.getMSGFile().getColorString("Announcements.Join");
				
			}
		}, 20);
		
		e.getPlayer().setLevel(0);
		e.getPlayer().setExp(0);
		
		e.getPlayer().getInventory().clear();
		e.getPlayer().getInventory().setArmorContents(null);
		
		new HoloUtil().showHologram(e.getPlayer());
		
		if(kitMode == true) {
			if(kitItem == null || kitName == null) {
				Bukkit.getConsoleSender().sendMessage(Core.getPrefix()+"§cYou don't have any Kits in your kits.yml");
			} else {
				e.getPlayer().getInventory().setHeldItemSlot(0);
				e.getPlayer().getInventory().setItemInHand(new ItemCreator(kitItem).setName(kitName).build());
		
			}
		}
		if(UHCCommand.teamMode == true) {
			if(teamItem == null || teamName == null) {
				Bukkit.getConsoleSender().sendMessage(Core.getPrefix()+"§cYou don't have any Kits in your kits.yml");
			} else {
				e.getPlayer().getInventory().setItem(1, new ItemCreator(teamItem).setName(teamName).build());
			}
		}
		if(SpawnFileManager.getLobby() != null) {
			if(Bukkit.getWorld(SpawnFileManager.getLobbyWorldName()) == null) {
				e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
			} else {
				e.getPlayer().teleport(SpawnFileManager.getLobby());
			}
		}
		Core.addInGamePlayer(e.getPlayer());
		e.getPlayer().setGameMode(GameMode.SURVIVAL);
		e.getPlayer().setHealth(20);
		e.getPlayer().setFoodLevel(20);
		for(PotionEffect pe : e.getPlayer().getActivePotionEffects()) {
			if(e.getPlayer().hasPotionEffect(pe.getType())) {
				e.getPlayer().removePotionEffect(pe.getType());
			}
		}
		
		if(title.contains("[Player]")) {
			String aa = title.replace("[Player]", e.getPlayer().getDisplayName());
			if(subtitle.contains("[Player]")) {
				String bb = subtitle.replace("[Player]", e.getPlayer().getDisplayName());
				TitleManager.sendTitle(e.getPlayer(), 20, 20, 20, aa, bb);
			} else {
				TitleManager.sendTitle(e.getPlayer(), 20, 20, 20, aa, subtitle);
			}
		} else {
			TitleManager.sendTitle(e.getPlayer(), 20, 20, 20, title, subtitle);
		}
		
		
		
		ScoreboardManager.setLobbyBoard(e.getPlayer());
		
		if(Bukkit.getOnlinePlayers().size() == Timer.pc) {
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					
					Timer.startCountdown();
					
				}
			}.runTaskLater(Core.getInstance(), 20);
		
		}
		
		
	}

}
