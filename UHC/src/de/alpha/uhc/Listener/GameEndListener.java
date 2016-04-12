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
import de.alpha.uhc.aclasses.AScoreboard;
import de.alpha.uhc.aclasses.ATablist;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.Spectator;
import de.alpha.uhc.utils.Stats;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.item.data.SkullData;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class GameEndListener implements Listener {
	
	private static String win;
	private static String kick;
	private static String rew;
	private static String quit;
	private static String BungeeServer;
	
	private static int apc;
	private static int opc;
	
	private static int reward;
	private static int deathreward;
	
	private static boolean BungeeMode;
	
	
	
	public static  String getWin() {
		return win;
	}


	public static  void setWin(String win) {
		GameEndListener.win = win;
	}


	public static  String getKick() {
		return kick;
	}


	public static  void setKick(String kick) {
		GameEndListener.kick = kick;
	}


	public static  String getRew() {
		return rew;
	}


	public static  void setRew(String rew) {
		GameEndListener.rew = rew;
	}


	public static  String getQuit() {
		return quit;
	}


	public static  void setQuit(String quit) {
		GameEndListener.quit = quit;
	}


	public static  String getBungeeServer() {
		return BungeeServer;
	}


	public static  void setBungeeServer(String bungeeServer) {
		BungeeServer = bungeeServer;
	}


	public static  int getApc() {
		return apc;
	}


	public static  void setApc(int apc) {
		GameEndListener.apc = apc;
	}


	public static  int getOpc() {
		return opc;
	}


	public static  void setOpc(int opc) {
		GameEndListener.opc = opc;
	}


	public static  int getReward() {
		return reward;
	}


	public static  void setReward(int reward) {
		GameEndListener.reward = reward;
	}


	public static  int getDeathreward() {
		return deathreward;
	}


	public static  void setDeathreward(int deathreward) {
		GameEndListener.deathreward = deathreward;
	}


	public static  boolean isBungeeMode() {
		return BungeeMode;
	}


	public static  void setBungeeMode(boolean bungeeMode) {
		BungeeMode = bungeeMode;
	}


	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		final Player p = e.getEntity();
		
		//                        -=X Spectator X=-
		
		Core.removeInGamePlayer(p);
		Core.addSpec(p);
		
		Spectator.setSpec(p);
		
		p.getWorld().strikeLightningEffect(p.getLocation());
		
		
		//                        -=X Tablist X=-
		
		ATablist.sendStandingInGameTablist();
		
		
		//                        -=X Stats X=-
		
		
		if(p.getKiller() instanceof Player) new Stats(p.getKiller()).addKill();
		
		new Stats(p).addDeath();
		new Stats(p).addCoins(deathreward);
		
		
		//                        -=X Rewarding X=-
		
		
		rew = rew.replace("[Coins]", Integer.toString(deathreward));
		
		p.sendMessage(Core.getPrefix() + rew);
		SimpleTitle.sendTitle(p, " ", rew, 10, 20, 10);
		
		rew = MessageFileManager.getMSGFile().getColorString("Reward");
		
		
		//                        -=X Scoreboard X=-
		
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			AScoreboard.updateInGamePlayersLiving(all);
			AScoreboard.updateInGameSpectators(all);
		}
		
		//                        -=X ItemDrop X=-
		
		p.getWorld().dropItem(p.getLocation(), new ItemBuilder(Material.GOLD_INGOT).setAmount(8).build());
		new BukkitRunnable() {
			
			@Override
			public void run() {
				p.getWorld().dropItem(p.getLocation(), new ItemBuilder(Material.SKULL_ITEM).addItemData(new SkullData(p.getName())).build());
			}
		}.runTaskLater(Core.getInstance(), 10);
		
		
		//                       -=X Game End X=-
		
		if(Core.getInGamePlayers().size() == 4) {
			if(Timer.isDm()) {
				Timer.getDd().cancel();
				Timer.startDeathMatch();
			}
		}
		
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
					SimpleTitle.sendTitle(all, " ", win, 10, 20, 10);
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
		
		ATablist.sendStandingInGameTablist();
		
		p.getInventory().clear();
		
		if(GState.isState(GState.INGAME) || GState.isState(GState.GRACE)) {
			
			if(!(Core.getSpecs().contains(p))) {
				p.getWorld().dropItem(p.getLocation(), new ItemBuilder(Material.GOLD_INGOT).setAmount(8).build());
				p.getWorld().dropItem(p.getLocation(), new ItemBuilder(Material.SKULL_ITEM).addItemData(new SkullData(p.getName())).build());
				p.getWorld().strikeLightningEffect(p.getLocation());
			}	
			
			for(Player all : Bukkit.getOnlinePlayers()) {
				AScoreboard.updateInGamePlayersLiving(all);
				AScoreboard.updateInGameSpectators(all);
			}
			
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
						SimpleTitle.sendTitle(all, " ", win, 10, 20, 10);
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
