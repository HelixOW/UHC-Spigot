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
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.item.data.SkullData;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class GameEndListener implements Listener {
	
	private Core pl;
	private Registery r;
	
	public GameEndListener(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  String win;
    private  String kick;
    private  String quit;
    private  String BungeeServer;
    private  String cmdEnd;
    private  String cmdDeath;

    private  boolean BungeeMode;
    private  boolean cmdOnEnd;
    private  boolean cmdOnDeath;

    public  void setCmdDeath(String cmdDeath) {
    	this.cmdDeath = cmdDeath;
    }

    private  boolean isCmdOnDeath() {
        return cmdOnDeath;
    }

    public  void setCmdOnDeath(boolean cmdOnDeath) {
    	this.cmdOnDeath = cmdOnDeath;
    }

    public  void setCmdOnEnd(boolean cmdOnEnd) {
    	this.cmdOnEnd = cmdOnEnd;
    }

    public  void setCmdEnd(String cmd) {
    	this.cmdEnd = cmd;
    }

    public  void setWin(String win) {
    	this.win = win;
    }

    public  String getKick() {
        return kick;
    }

    public  void setKick(String kick) {
    	this.kick = kick;
    }

    public  void setQuit(String quit) {
    	this.quit = quit;
    }

    public  String getBungeeServer() {
        return BungeeServer;
    }

    public  void setBungeeServer(String bungeeServer) {
        BungeeServer = bungeeServer;
    }

    public  boolean isBungeeMode() {
        return BungeeMode;
    }

    public  void setBungeeMode(boolean bungeeMode) {
        BungeeMode = bungeeMode;
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        final Player p = e.getEntity();

        //                        -=X Spectator X=-

        pl.removeInGamePlayer(p);
        pl.addSpec(p);

        r.getSpectator().setSpec(p);

        p.getWorld().strikeLightningEffect(p.getLocation());


        //                        -=X Tablist X=-

        r.getATablist().sendStandingInGameTablist();


        //                        -=X Stats X=-


        if(p.getKiller() != null) {
        	r.getStats().addKill(p.getKiller());
        	r.getStats().addPoints(50, p.getKiller());
        }
        
        r.getStats().addDeath(p);
        r.getStats().removePoints(25, p);

        String a = cmdDeath.replace("[player]", p.getName());
        if (isCmdOnDeath()) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), a);


        //                        -=X Scoreboard X=-


        for (Player all : Bukkit.getOnlinePlayers()) {
            r.getAScoreboard().updateInGamePlayersLiving(all);
            r.getAScoreboard().updateInGameSpectators(all);
        }

        //                        -=X ItemDrop X=-

        p.getWorld().dropItemNaturally(p.getLocation(), new ItemBuilder(Material.GOLD_INGOT).setAmount().build());
        new BukkitRunnable() {

            @Override
            public void run() {
                p.getWorld().dropItemNaturally(p.getLocation(), new ItemBuilder(Material.SKULL_ITEM).addItemData(new SkullData(p.getName())).build());
            }
        }.runTaskLater(pl, 10);


        //                       -=X Game End X=-

        if (pl.getInGamePlayers().size() == 4) {
            if (r.getTimer().isDm()) {
                r.getTimer().getDd().cancel();
                r.getTimer().startDeathMatch();
            }
        }

        if (pl.getInGamePlayers().size() <= 1) {

            if (pl.getInGamePlayers().size() == 0) {

            	r.getTimer().startRestartTimer();

                new BukkitRunnable() {

                    @Override
                    public void run() {

                        Bukkit.reload();

                    }
                }.runTaskLater(pl, 20 * 20);
                return;
            }


            for (Player winner : pl.getInGamePlayers()) {

                win = win.replace("[Player]", winner.getDisplayName());

                Bukkit.broadcastMessage(pl.getPrefix() + win);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    SimpleTitle.sendTitle(all, " ", win, 1, 2, 1);
                }
                
                r.getStats().addPoints(500, winner);

                String b = cmdEnd.replace("[player]", winner.getName());
                if (cmdOnEnd) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), b);

                win = r.getMessageFile().getMSGFile().getColorString("Announcements.Win");

                r.getTimer().startRestartTimer();

                Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

                    @Override
                    public void run() {

                        Bukkit.reload();

                    }
                }, 20 * 20);

            }
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);

        int apc;
        Player p = e.getPlayer();
        quit = quit.replace("[Player]", p.getDisplayName());

        pl.removeInGamePlayer(p);

        if (GState.isState(GState.LOBBY)) {
            apc = Bukkit.getOnlinePlayers().size() - 1;
        } else {
            apc = pl.getInGamePlayers().size();
        }

        if (pl.getSpecs().contains(p)) {
        	pl.removeSpec(p);
            for (Player o : pl.getSpecs()) {
                quit = quit.replace("[PlayerCount]", "§7[" + apc + " left]");

                o.sendMessage(pl.getPrefix() + quit);

                quit = r.getMessageFile().getMSGFile().getColorString("Announcements.Leave");
            }
        } else {
            quit = quit.replace("[PlayerCount]", "§7[" + apc + " left]");

            Bukkit.broadcastMessage(pl.getPrefix() + quit);

            quit = r.getMessageFile().getMSGFile().getColorString("Announcements.Leave");
        }

        r.getATablist().sendStandingInGameTablist();

        p.getInventory().clear();

        if (GState.isState(GState.INGAME) || GState.isState(GState.GRACE)) {

            if (!(pl.getSpecs().contains(p))) {
                p.getWorld().dropItemNaturally(p.getLocation(), new ItemBuilder(Material.GOLD_INGOT).setAmount().build());
                p.getWorld().dropItemNaturally(p.getLocation(), new ItemBuilder(Material.SKULL_ITEM).addItemData(new SkullData(p.getName())).build());
                p.getWorld().strikeLightningEffect(p.getLocation());
            }

            for (Player all : Bukkit.getOnlinePlayers()) {
                r.getAScoreboard().updateInGamePlayersLiving(all);
                r.getAScoreboard().updateInGameSpectators(all);
            }
            
            r.getStats().addDeath(p);
            r.getStats().removePoints(25, p);

            String b = cmdDeath.replace("[player]", p.getName());
            if (isCmdOnDeath()) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), b);

            p.setGameMode(GameMode.SURVIVAL);

            if (pl.getInGamePlayers().size() <= 1) {

                if (pl.getInGamePlayers().size() == 0) {

                	r.getTimer().startRestartTimer();

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            Bukkit.reload();

                        }
                    }.runTaskLater(pl, 20 * 20);
                    return;
                }

                for (Player winner : pl.getInGamePlayers()) {

                    win = win.replace("[Player]", winner.getDisplayName());

                    Bukkit.broadcastMessage(pl.getPrefix() + win);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        SimpleTitle.sendTitle(all, " ", win, 1, 2, 1);
                    }
                    
                    r.getStats().addPoints(500, winner);
                    
                    String a = cmdEnd.replace("[player]", winner.getName());
                    if (cmdOnEnd) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), a);


                    win = r.getMessageFile().getMSGFile().getColorString("Announcements.Win");

                    r.getTimer().startRestartTimer();

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            Bukkit.reload();

                        }
                    }.runTaskLater(pl, 20 * 20);
                }
            }
        }
    }
}
