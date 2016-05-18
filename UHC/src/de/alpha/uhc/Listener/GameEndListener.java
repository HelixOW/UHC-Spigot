package de.alpha.uhc.Listener;

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
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class GameEndListener implements Listener {

    private static String win;
    private static String kick;
    private static String quit;
    private static String BungeeServer;
    private static String cmdEnd;
    private static String cmdDeath;

    private static boolean BungeeMode;
    private static boolean cmdOnEnd;
    private static boolean cmdOnDeath;

    public static void setCmdDeath(String cmdDeath) {
        GameEndListener.cmdDeath = cmdDeath;
    }

    private static boolean isCmdOnDeath() {
        return cmdOnDeath;
    }

    public static void setCmdOnDeath(boolean cmdOnDeath) {
        GameEndListener.cmdOnDeath = cmdOnDeath;
    }

    public static void setCmdOnEnd(boolean cmdOnEnd) {
        GameEndListener.cmdOnEnd = cmdOnEnd;
    }

    public static void setCmdEnd(String cmd) {
        GameEndListener.cmdEnd = cmd;
    }

    public static void setWin(String win) {
        GameEndListener.win = win;
    }

    public static String getKick() {
        return kick;
    }

    public static void setKick(String kick) {
        GameEndListener.kick = kick;
    }

    public static void setQuit(String quit) {
        GameEndListener.quit = quit;
    }

    public static String getBungeeServer() {
        return BungeeServer;
    }

    public static void setBungeeServer(String bungeeServer) {
        BungeeServer = bungeeServer;
    }

    public static boolean isBungeeMode() {
        return BungeeMode;
    }

    public static void setBungeeMode(boolean bungeeMode) {
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


        if (p.getKiller() != null) new Stats(p.getKiller()).addKill();

        new Stats(p).addDeath();

        String a = cmdDeath.replace("[player]", p.getName());
        if (isCmdOnDeath()) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), a);


        //                        -=X Scoreboard X=-


        for (Player all : Bukkit.getOnlinePlayers()) {
            AScoreboard.updateInGamePlayersLiving(all);
            AScoreboard.updateInGameSpectators(all);
        }

        //                        -=X ItemDrop X=-

        p.getWorld().dropItemNaturally(p.getLocation(), new ItemBuilder(Material.GOLD_INGOT).setAmount().build());
        new BukkitRunnable() {

            @Override
            public void run() {
                p.getWorld().dropItemNaturally(p.getLocation(), new ItemBuilder(Material.SKULL_ITEM).addItemData(new SkullData(p.getName())).build());
            }
        }.runTaskLater(Core.getInstance(), 10);


        //                       -=X Game End X=-

        if (Core.getInGamePlayers().size() == 4) {
            if (Timer.isDm()) {
                Timer.getDd().cancel();
                Timer.startDeathMatch();
            }
        }

        if (Core.getInGamePlayers().size() <= 1) {

            if (Core.getInGamePlayers().size() == 0) {

                Timer.startRestartTimer();

                new BukkitRunnable() {

                    @Override
                    public void run() {

                        Bukkit.reload();

                    }
                }.runTaskLater(Core.getInstance(), 20 * 20);
                return;
            }


            for (Player winner : Core.getInGamePlayers()) {

                win = win.replace("[Player]", winner.getDisplayName());

                Bukkit.broadcastMessage(Core.getPrefix() + win);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    SimpleTitle.sendTitle(all, " ", win, 1, 2, 1);
                }

                String b = cmdEnd.replace("[player]", winner.getName());
                if (cmdOnEnd) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), b);

                win = MessageFileManager.getMSGFile().getColorString("Announcements.Win");

                Timer.startRestartTimer();

                Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {

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

        Core.removeInGamePlayer(p);

        if (GState.isState(GState.LOBBY)) {
            apc = Bukkit.getOnlinePlayers().size() - 1;
        } else {
            apc = Core.getInGamePlayers().size();
        }

        if (Core.getSpecs().contains(p)) {
            Core.removeSpec(p);
            for (Player o : Core.getSpecs()) {
                quit = quit.replace("[PlayerCount]", "§7[" + apc + " left]");

                o.sendMessage(Core.getPrefix() + quit);

                quit = MessageFileManager.getMSGFile().getColorString("Announcements.Leave");
            }
        } else {
            quit = quit.replace("[PlayerCount]", "§7[" + apc + " left]");

            Bukkit.broadcastMessage(Core.getPrefix() + quit);

            quit = MessageFileManager.getMSGFile().getColorString("Announcements.Leave");
        }

        ATablist.sendStandingInGameTablist();

        p.getInventory().clear();

        if (GState.isState(GState.INGAME) || GState.isState(GState.GRACE)) {

            if (!(Core.getSpecs().contains(p))) {
                p.getWorld().dropItemNaturally(p.getLocation(), new ItemBuilder(Material.GOLD_INGOT).setAmount().build());
                p.getWorld().dropItemNaturally(p.getLocation(), new ItemBuilder(Material.SKULL_ITEM).addItemData(new SkullData(p.getName())).build());
                p.getWorld().strikeLightningEffect(p.getLocation());
            }

            for (Player all : Bukkit.getOnlinePlayers()) {
                AScoreboard.updateInGamePlayersLiving(all);
                AScoreboard.updateInGameSpectators(all);
            }

            p.setGameMode(GameMode.SURVIVAL);

            if (Core.getInGamePlayers().size() <= 1) {

                if (Core.getInGamePlayers().size() == 0) {

                    Timer.startRestartTimer();

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            Bukkit.reload();

                        }
                    }.runTaskLater(Core.getInstance(), 20 * 20);
                    return;
                }

                for (Player winner : Core.getInGamePlayers()) {

                    win = win.replace("[Player]", winner.getDisplayName());

                    Bukkit.broadcastMessage(Core.getPrefix() + win);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        SimpleTitle.sendTitle(all, " ", win, 1, 2, 1);
                    }

                    String a = cmdEnd.replace("[player]", winner.getName());
                    if (cmdOnEnd) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), a);


                    win = MessageFileManager.getMSGFile().getColorString("Announcements.Win");

                    Timer.startRestartTimer();

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            Bukkit.reload();

                        }
                    }.runTaskLater(Core.getInstance(), 20 * 20);
                }
            }
        }
    }
}
