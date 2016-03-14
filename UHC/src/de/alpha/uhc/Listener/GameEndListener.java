package de.alpha.uhc.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.manager.ScoreboardManager;
import de.alpha.uhc.manager.TitleManager;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.Spectator;
import de.alpha.uhc.utils.Stats;
import net.minetopix.library.main.item.ItemCreator;
import net.minetopix.library.main.item.data.SkullData;

public class GameEndListener implements Listener {
	
	public static String death;
	public static String win;
	public static String kick;
	public static String rew;
	public static String quit;
	public static String BungeeServer;
	
	public static String header;
	public static String footer;
	
	private static int apc;
	public static int opc;
	
	public static int reward;
	public static int deathreward;
	
	public static boolean BungeeMode;
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		final Player p = e.getEntity();
		
		//                        -=X Spectator X=-
		
		Core.removeInGamePlayer(p);
		Core.addSpec(p);
		
		Spectator.setSpec(p);
		
		p.getWorld().strikeLightningEffect(p.getLocation());
		
		
		//                        -=X Death Note X=-
		
		apc = Core.getInGamePlayers().size();
		death = death.replace("[Player]", p.getDisplayName());
		death = death.replace("[PlayerCount]", "§7["+apc+" left]");
		
		header = header.replace("[player]", p.getDisplayName());
		header = header.replace("[playercount]", Integer.toString(Core.getInGamePlayers().size()));
		header = header.replace("[gamestatus]", GState.getGStateName());
		
		footer = footer.replace("[player]", p.getDisplayName());
		footer = footer.replace("[playercount]", Integer.toString(Core.getInGamePlayers().size()));
		footer = footer.replace("[gamestatus]", GState.getGStateName());
		
		e.setDeathMessage(Core.getPrefix() + death);
		TitleManager.sendTabTitle(p, "", "");
		TitleManager.sendTabTitle(p, header, footer);
		
		death = MessageFileManager.getMSGFile().getColorString("Announcements.Death");
		header = MessageFileManager.getMSGFile().getColorString("Tablist.Top");
		footer = MessageFileManager.getMSGFile().getColorString("Tablist.Bottom");
		
		
		//                        -=X Stats X=-
		
		
		if(p.getKiller() instanceof Player) new Stats(p.getKiller()).addKill();
		
		new Stats(p).addDeath();
		new Stats(p).addCoins(deathreward);
		
		
		//                        -=X Rewarding X=-
		
		
		rew = rew.replace("[Coins]", Integer.toString(deathreward));
		
		p.sendMessage(Core.getPrefix() + rew);
		TitleManager.sendTitle(p, 10, 20, 10, " ", rew);
		
		rew = MessageFileManager.getMSGFile().getColorString("Reward");
		
		
		//                        -=X Scoreboard X=-
		
		
		ScoreboardManager.updatePlayerIGScore();
		ScoreboardManager.updatePlayerSpecScore();
		
		
		//                        -=X ItemDrop X=-
		
		p.getWorld().dropItem(p.getLocation(), new ItemCreator(Material.GOLD_INGOT).setAmount(8).build());
		new BukkitRunnable() {
			
			@Override
			public void run() {
				p.getWorld().dropItem(p.getLocation(), new ItemCreator(Material.SKULL_ITEM).addItemData(new SkullData(p.getName())).build());
			}
		}.runTaskLater(Core.getInstance(), 10);
		
		
		//                       -=X Game End X=-
		
		if(Core.getInGamePlayers().size() <= 1) {
			
			if(Core.getInGamePlayers().size() == 0) {
				
				Timer.startRestartTimer();
				
				new BukkitRunnable() {
					
					@Override
					public void run() {

						Bukkit.reload();

					}
				}.runTaskLater(Core.getInstance(), 20*20);
				return;
			}
			
			
			
			for(Player winner: Core.getInGamePlayers()) {
				
				win = win.replace("[Player]", winner.getDisplayName());
				rew = rew.replace("[Coins]", Integer.toString(reward));
				
				Bukkit.broadcastMessage(Core.getPrefix() + win);
				for(Player all : Bukkit.getOnlinePlayers()) {
					TitleManager.sendTitle(all, 10, 20, 10, " ", win);
				}
				
				new Stats(winner).addCoins(reward);
				
				winner.sendMessage(Core.getPrefix() + rew);
				
				rew = MessageFileManager.getMSGFile().getColorString("Reward");
				win = MessageFileManager.getMSGFile().getColorString("Announcements.Win");
				
				Timer.startRestartTimer();
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						Bukkit.reload();
						
					}
				}, 20*20);
				
			}
		}
	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		
		Player p = e.getPlayer();
		quit = quit.replace("[Player]", p.getDisplayName());
		
		Core.removeInGamePlayer(p);
		
		if(GState.isState(GState.LOBBY)) {
			apc = Bukkit.getOnlinePlayers().size() - 1;
		} else {
			apc = Core.getInGamePlayers().size();
		}
		
		if(Core.getSpecs().contains(p)) {
			Core.removeSpec(p);
			for(Player o : Core.getSpecs()) {
				quit = quit.replace("[PlayerCount]", "§7["+apc+" left]");
				
				o.sendMessage(Core.getPrefix() + quit);
				
				quit = MessageFileManager.getMSGFile().getColorString("Announcements.Leave");
			}
		} else {
			quit = quit.replace("[PlayerCount]", "§7["+apc+" left]");
		
			Bukkit.broadcastMessage(Core.getPrefix() + quit);
		
			quit = MessageFileManager.getMSGFile().getColorString("Announcements.Leave");
		}
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			header = header.replace("[player]", all.getDisplayName());
			header = header.replace("[playercount]", Integer.toString(Core.getInGamePlayers().size()));
			header = header.replace("[gamestatus]", GState.getGStateName());
			
			footer = footer.replace("[player]", all.getDisplayName());
			footer = footer.replace("[playercount]", Integer.toString(Core.getInGamePlayers().size()));
			footer = footer.replace("[gamestatus]", GState.getGStateName());
			
			TitleManager.sendTabTitle(all, "", "");
			TitleManager.sendTabTitle(all, header, footer);
			
			header = MessageFileManager.getMSGFile().getColorString("Tablist.Top");
			footer = MessageFileManager.getMSGFile().getColorString("Tablist.Bottom");
		}
		
		p.getInventory().clear();
		
		if(GState.isState(GState.INGAME) || GState.isState(GState.GRACE)) {
			
			if(!(Core.getSpecs().contains(p))) {
				p.getWorld().dropItem(p.getLocation(), new ItemCreator(Material.GOLD_INGOT).setAmount(8).build());
				p.getWorld().dropItem(p.getLocation(), new ItemCreator(Material.SKULL_ITEM).addItemData(new SkullData(p.getName())).build());
				p.getWorld().strikeLightningEffect(p.getLocation());
			}	
			
			ScoreboardManager.updatePlayerIGScore();
			ScoreboardManager.updatePlayerSpecScore();
			
			p.setGameMode(GameMode.SURVIVAL);
			
			if(Core.getInGamePlayers().size() <= 1) {
				
				if(Core.getInGamePlayers().size() == 0) {
					
					Timer.startRestartTimer();
					
					new BukkitRunnable() {
						
						@Override
						public void run() {

							Bukkit.reload();

						}
					}.runTaskLater(Core.getInstance(), 20*20);
					return;
				}
				
				for(Player winner: Core.getInGamePlayers()) {
					
					win = win.replace("[Player]", winner.getDisplayName());
					rew = rew.replace("[Coins]", Integer.toString(reward));
					
					Bukkit.broadcastMessage(Core.getPrefix() + win);
					for(Player all : Bukkit.getOnlinePlayers()) {
						TitleManager.sendTitle(all, 10, 20, 10, " ", win);
					}
					
					new Stats(winner).addCoins(reward);
					
					
					winner.sendMessage(Core.getPrefix() + rew);
					
					rew = MessageFileManager.getMSGFile().getColorString("Reward");
					win = MessageFileManager.getMSGFile().getColorString("Announcements.Win");
					
					Timer.startRestartTimer();
					
					new BukkitRunnable() {
						
						@Override
						public void run() {

							Bukkit.reload();

						}
					}.runTaskLater(Core.getInstance(), 20*20);
				}
			}
		}
	}
}
