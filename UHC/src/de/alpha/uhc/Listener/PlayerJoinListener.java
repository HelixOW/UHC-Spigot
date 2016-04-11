package de.alpha.uhc.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.aclasses.AScoreboard;
import de.alpha.uhc.aclasses.ATablist;
import de.alpha.uhc.aclasses.ATeam;
import de.alpha.uhc.commands.UHCCommand;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.PlayerFileManager;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.HoloUtil;
import de.alpha.uhc.utils.Spectator;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.mysql.MySQLManager;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class PlayerJoinListener implements Listener {
	
	public static String join;
	public static String full;
	
	public static String title;
	public static String subtitle;
	
	public static Material teamItem;
	public static String teamName;
	
	public static Material kitItem;
	public static String kitName;
	public static boolean kitMode;
	
	public static boolean leaveMode;
	public static Material leaveItem;
	public static String leaveName;
	
	public static boolean startMode;
	public static Material startItem;
	public static String startName;
	
	private int apc;
	public static int mpc;
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		
		e.setJoinMessage(null);
		Player p = e.getPlayer();
		
		if(GState.isState(GState.RESTART)) {
			p.kickPlayer(Core.getPrefix() + GameEndListener.kick);
			return;
		}
		
		if(GState.isState(GState.INGAME) || GState.isState(GState.GRACE)) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			if(SpawnFileManager.getSpawn() == null) {
				p.teleport(Bukkit.getWorld("UHC").getSpawnLocation());
			} else {
				p.teleport(SpawnFileManager.getSpawn());
			}
			Core.addSpec(p);
			Spectator.setSpec(p);
			AScoreboard.setInGameScoreboard(p);

			ATablist.sendStandingInGameTablist();
			for(Player all : Core.getInGamePlayers()) {
				AScoreboard.updateInGameSpectators(all);
			}
			return;
		}
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			
			all.showPlayer(p);
			
		}
		
		ATablist.sendStandingLobbyTablist();
		
		if(Core.isMySQLActive == true) {
			if(MySQLManager.getObjectConditionResult("UHC", "UUID", p.getUniqueId().toString(), "UUID") == null) {
				MySQLManager.exInsertQry("UHC", p.getName(), p.getUniqueId().toString(), "0", "0", "0", "");
			} else if(MySQLManager.getObjectConditionResult("UHC", "UUID ", p.getUniqueId().toString(), "UUID") != null) {
				MySQLManager.exUpdateQry("UHC", "UUID", p.getUniqueId().toString(), "Player", p.getName());
			}
		} else {
			if(!(PlayerFileManager.getPlayerFile().contains("Player: "+ p.getName()))) {
				new PlayerFileManager().addPlayer(p);
			}
		}
		
		if(Bukkit.getOnlinePlayers().size() == mpc+1) {
			p.kickPlayer(Core.getPrefix() + full);
		} 
		
		for(Entity kill : p.getWorld().getEntities()) {
			
			if(!(kill instanceof Player || kill instanceof ArmorStand)) {
				kill.remove();
			}
			
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				apc = Bukkit.getOnlinePlayers().size();
				
				join = join.replace("[Player]", p.getDisplayName());
				join = join.replace("[PlayerCount]", "§7["+apc+"/"+mpc+"]");
				
				Bukkit.broadcastMessage(Core.getPrefix() + join);
				
				join = MessageFileManager.getMSGFile().getColorString("Announcements.Join");
				
			}
		}, 20);
		
		p.setLevel(0);
		p.setExp(0);
		
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		
		new HoloUtil().showHologram(p);
		
		if(kitMode == true) {
			if(kitItem == null || kitName == null) {
				Bukkit.getConsoleSender().sendMessage(Core.getPrefix()+"§cYou don't have any Kits in your kits.yml");
			} else {
				p.getInventory().setHeldItemSlot(0);
				p.getInventory().setItemInMainHand(new ItemBuilder(kitItem).setName(kitName).build());
		
			}
		}
		if(UHCCommand.teamMode == true) {
			if(teamItem == null || teamName == null) {
				Bukkit.getConsoleSender().sendMessage(Core.getPrefix()+"§cYou don't have any Kits in your kits.yml");
			} else {
				p.getInventory().setItem(1, new ItemBuilder(teamItem).setName(teamName).build());
			}
		}
		if(leaveMode == true && GameEndListener.BungeeMode == true) {
			p.getInventory().setItem(8, new ItemBuilder(leaveItem).setName(leaveName).build());
		}
		if(startMode == true && p.hasPermission("uhc.start")) {
			p.getInventory().setItem(4, new ItemBuilder(startItem).setName(startName).build());
		}
		if(SpawnFileManager.getLobby() != null) {
			if(Bukkit.getWorld(SpawnFileManager.getLobbyWorldName()) == null) {
				p.teleport(p.getWorld().getSpawnLocation());
			} else {
				p.teleport(SpawnFileManager.getLobby());
			}
		}
		Core.addInGamePlayer(p);
		p.setGameMode(GameMode.SURVIVAL);
		p.setHealth(20);
		p.setFoodLevel(20);
		for(PotionEffect pe : p.getActivePotionEffects()) {
			if(p.hasPotionEffect(pe.getType())) {
				p.removePotionEffect(pe.getType());
			}
		}
		
		if(title.contains("[Player]")) {
			String aa = title.replace("[Player]", p.getDisplayName());
			if(subtitle.contains("[Player]")) {
				String bb = subtitle.replace("[Player]", p.getDisplayName());
				SimpleTitle.sendTitle(p, aa, bb, 10, 20, 10);
			} else {
				SimpleTitle.sendTitle(p, aa, subtitle, 10, 20, 10);
			}
		} else {
			SimpleTitle.sendTitle(p, title, subtitle, 10, 20, 10);
		}
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			AScoreboard.setLobbyScoreboard(all);
		}
		
		if(Bukkit.getOnlinePlayers().size() == Timer.pc) {
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					
					Timer.startCountdown();
					
				}
			}.runTaskLater(Core.getInstance(), 20);
		
		}
	}
	
	@EventHandler
	public void onLeaveClick(PlayerInteractEvent e) {
		if(!(GState.isState(GState.LOBBY))) return;
		if(e.getItem() == null) return;
		if(e.getItem().getType().equals(leaveItem)) {
			if(GameEndListener.BungeeMode == true) {
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
					
				out.writeUTF("Connect");
				out.writeUTF(GameEndListener.BungeeServer);
					
				e.getPlayer().sendPluginMessage(Core.getInstance(), "BungeeCord", out.toByteArray());
			}
		} else if(e.getPlayer().hasPermission("uhc.start") && startMode == true && e.getItem().getType().equals(startItem)) {
				Timer.changeTime();
				startMode = false;
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		ATeam.removePlayerFromTeam(e.getPlayer());
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					AScoreboard.setLobbyScoreboard(all);
				}
			}
		}.runTaskLater(Core.getInstance(), 5);
	}
}
