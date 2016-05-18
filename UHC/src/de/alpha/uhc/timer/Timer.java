package de.alpha.uhc.timer;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Listener.GameEndListener;
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.aclasses.AScoreboard;
import de.alpha.uhc.aclasses.ATablist;
import de.alpha.uhc.aclasses.AWorld;
import de.alpha.uhc.border.Border;
import de.alpha.uhc.border.BorderManager;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.OptionsFileManager;
import de.alpha.uhc.kits.KitFileManager;
import de.alpha.uhc.utils.LobbyPasteUtil;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.nms.SimpleActionBar;
import de.popokaka.alphalibary.nms.SimpleTitle;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static de.alpha.uhc.files.SpawnFileManager.*;


public class Timer {

    private static String countmsg;
    private static String nep;
    private static String gracemsg;
    private static String end;
    private static String endmsg;
    private static String dmmsg;
    private static String pvpmsg;
    private static String pvpstart;
    private static boolean comMode;
    private static Material comItem;
    private static String comName;
    private static boolean dm;
    private static int pc;
    private static int high;
    private static int gracetime;
    private static int max;
    private static int uDM;
    private static int tbpvp;
    private static int prePvP;
    private static int endTime;
    private static BukkitTask a;
    private static BukkitTask b;
    private static BukkitTask c;
    private static BukkitTask d;
    private static BukkitTask e;
    private static BukkitTask dd;
    private static BukkitTask ee;
    private static BukkitTask f;
    private static boolean BungeeMode;
    private static String BungeeServer;

    public static void setCountmsg(String countmsg) {
        Timer.countmsg = countmsg;
    }

    public static void setNep(String nep) {
        Timer.nep = nep;
    }

    public static void setGracemsg(String gracemsg) {
        Timer.gracemsg = gracemsg;
    }

    public static void setEnd(String end) {
        Timer.end = end;
    }

    public static void setEndmsg(String endmsg) {
        Timer.endmsg = endmsg;
    }

    public static void setDmmsg(String dmmsg) {
        Timer.dmmsg = dmmsg;
    }

    public static void setPvpmsg(String pvpmsg) {
        Timer.pvpmsg = pvpmsg;
    }

    public static void setPvpstart(String pvpstart) {
        Timer.pvpstart = pvpstart;
    }

    public static void setComMode(boolean comMode) {
        Timer.comMode = comMode;
    }

    public static void setComItem(Material comItem) {
        Timer.comItem = comItem;
    }

    public static void setComName(String comName) {
        Timer.comName = comName;
    }

    public static boolean isDm() {
        return dm;
    }

    public static void setDm(boolean dm) {
        Timer.dm = dm;
    }

    public static int getPc() {
        return pc;
    }

    public static void setPc(int pc) {
        Timer.pc = pc;
    }

    public static int getGracetime() {
        return gracetime;
    }

    public static void setMax(int max) {
        Timer.max = max;
    }

    public static int getuDM() {
        return uDM;
    }

    public static void setuDM(int uDM) {
        Timer.uDM = uDM;
    }

    public static void setTbpvp(int tbpvp) {
        Timer.tbpvp = tbpvp;
    }

    public static int getPrePvP() {
        return prePvP;
    }

    public static void setPrePvP(int prePvP) {
        Timer.prePvP = prePvP;
    }

    public static BukkitTask getA() {
        return a;
    }

    public static void setA(BukkitTask a) {
        Timer.a = a;
    }

    public static BukkitTask getB() {
        return b;
    }

    public static void setB(BukkitTask b) {
        Timer.b = b;
    }

    public static BukkitTask getC() {
        return c;
    }

    public static void setC(BukkitTask c) {
        Timer.c = c;
    }

    public static BukkitTask getE() {
        return e;
    }

    public static void setE(BukkitTask e) {
        Timer.e = e;
    }

    public static BukkitTask getDd() {
        return dd;
    }

    public static void setBungeeMode(boolean bungeeMode) {
        BungeeMode = bungeeMode;
    }

    public static void setBungeeServer(String bungeeServer) {
        BungeeServer = bungeeServer;
    }

    public static void startCountdown() {

        if (GState.isState(GState.LOBBY)) {

            createSpawnWorld();

            a = new BukkitRunnable() {

                @Override
                public void run() {

                    if (high > 0) {

                        high--;

                        b = new BukkitRunnable() {

                            @Override
                            public void run() {

                                if (Bukkit.getOnlinePlayers().size() >= pc) {

                                    for (Player all : Core.getInGamePlayers()) {

                                        all.setLevel(high);

                                        if (high % 10 == 0 && high > 10 && high != 0) {
                                            countmsg = countmsg.replace("[time]", Integer.toString(high));
                                            all.sendMessage(Core.getPrefix() + countmsg);
                                            SimpleTitle.sendTitle(all, " ", countmsg, 1, 2, 1);
                                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BASS, 1F, 0F);//TODO: multi
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {

                                                @Override
                                                public void run() {

                                                    countmsg = MessageFileManager.getMSGFile().getColorString("Announcements.Countdown");

                                                }
                                            }, 2);
                                        }

                                        if (high < 10 && high != 0) {

                                            countmsg = countmsg.replace("[time]", Integer.toString(high));
                                            all.sendMessage(Core.getPrefix() + countmsg);
                                            SimpleActionBar.send(all, countmsg);
                                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BASS, 1F, 0F);//TODO: multi
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {

                                                @Override
                                                public void run() {

                                                    countmsg = MessageFileManager.getMSGFile().getColorString("Announcements.Countdown");
                                                }
                                            }, 2);
                                        }

                                        if (high == 0) {

                                            a.cancel();

                                            if (AWorld.isLobbyAsSchematic()) {
                                                LobbyPasteUtil.removeLobby();
                                            }

                                            for (Player ig : Core.getInGamePlayers()) {

                                                if (!AWorld.isLobbyAsSchematic()) {

                                                    try {

                                                        if (getSpawn() == null) {
                                                            ig.teleport(ig.getWorld().getHighestBlockAt(ig.getWorld().getSpawnLocation()).getLocation());
                                                            Border.setDistanceLoc(ig.getWorld().getSpawnLocation());
                                                        } else {
                                                            Location l = getSpawn();

                                                            Location r = l.getWorld().getHighestBlockAt(getRandomLocation(l, l.getBlockX() - max, l.getBlockX() + max, l.getBlockZ() - max, l.getBlockZ() + max)).getLocation();

                                                            ig.teleport(r);
                                                            Border.setDistanceLoc(getSpawn());
                                                        }
                                                    } catch (Exception e) {
                                                        ig.teleport(ig.getWorld().getHighestBlockAt(ig.getWorld().getSpawnLocation()).getLocation());
                                                        Border.setDistanceLoc(ig.getWorld().getSpawnLocation());
                                                    }

                                                } else {
                                                    Border.setDistanceLoc(getLobby().getWorld().getHighestBlockAt(getLobby()).getLocation());
                                                }
                                                b.cancel();

                                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_HARP, 1F, 0F);
                                                all.getWorld().setGameRuleValue("naturalRegeneration", "false");
                                                startGracePeriod();
                                                Border.border();
                                                GState.setGameState(GState.GRACE);
                                                all.setGameMode(GameMode.SURVIVAL);
                                                ATablist.sendStandingInGameTablist();

                                                if (LobbyListener.hasSelKit(ig)) {
                                                    for (ItemStack is : new KitFileManager().getContents(LobbyListener.getSelKit(ig)).getContents()) {
                                                        if (is != null) {
                                                            if (!(ig.getInventory().contains(is))) {
                                                                ig.getInventory().addItem(is);
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }
                                } else {
                                    Bukkit.broadcastMessage(Core.getPrefix() + nep);
                                    resetTime();
                                    a.cancel();
                                    b.cancel();
                                }

                            }
                        }.runTask(Core.getInstance());

                    }
                }
            }.runTaskTimer(Core.getInstance(), 0, 20);
        }
    }

    private static void startGracePeriod() {

        if (GState.isState(GState.GRACE)) {
            return;
        }
        for (Player all : Core.getInGamePlayers()) {
            for (Entity kill : all.getWorld().getEntities()) {

                if (!(kill instanceof Player)) {
                    kill.remove();
                }
            }
            all.getInventory().clear();
            AScoreboard.sendAntiFlickerInGameBoard(all);
        }

        c = new BukkitRunnable() {

            @Override
            public void run() {

                if (gracetime > 0) {

                    gracetime--;

                    e = new BukkitRunnable() {

                        @Override
                        public void run() {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                AScoreboard.updateInGamePvPTime(all);
                            }
                            if (gracetime % 10 == 0 && gracetime > 0) {
                                gracemsg = gracemsg.replace("[time]", Integer.toString(gracetime));
                                Bukkit.broadcastMessage(Core.getPrefix() + gracemsg);
                                gracemsg = MessageFileManager.getMSGFile().getColorString("Announcements.Peaceperiod.timer");
                                return;
                            }

                            if (gracetime == 0) {

                                e.cancel();

                                Bukkit.broadcastMessage(Core.getPrefix() + end);
                                new BorderManager().set();
                                for (final Player all : Core.getInGamePlayers()) {
                                    all.showPlayer(all);
                                    giveCompass(all);
                                    GState.setGameState(GState.PREGAME);
                                    startSilentGStateWatcher();
                                    ATablist.sendStandingInGameTablist();
                                    all.damage(1);
                                    new BukkitRunnable() {
                                        public void run() {
                                            all.setHealth(all.getMaxHealth());
                                        }
                                    }.runTaskLaterAsynchronously(Core.getInstance(), 15);
                                }
                                c.cancel();
                            }

                        }
                    }.runTask(Core.getInstance());
                }
            }
        }.runTaskTimer(Core.getInstance(), 0, 20);
    }

    private static void startSilentGStateWatcher() {
        d = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    AScoreboard.updateInGamePvPTime(all);
                }
                if (prePvP > 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        String a = pvpmsg.replace("[time]", Integer.toString(prePvP));
                        SimpleActionBar.send(all, Core.getPrefix() + a);
                    }
                }

                if (prePvP == 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        SimpleActionBar.send(all, Core.getPrefix() + pvpstart);
                    }
                    GState.setGameState(GState.INGAME);
                    if (dm) startSilentDeathMatchTimer();
                    d.cancel();
                }
                prePvP--;
            }
        }.runTaskTimer(Core.getInstance(), 0, 20 * 60);
    }

    private static void startSilentDeathMatchTimer() {
        dd = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    AScoreboard.updateInGamePvPTime(all);
                }
                if (uDM % 5 == 0 && uDM > 10) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        String a = dmmsg.replace("[time]", Integer.toString(uDM));
                        SimpleActionBar.send(all, Core.getPrefix() + a);
                    }
                }

                if (uDM > 0 && uDM < 10) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        String a = dmmsg.replace("[time]", Integer.toString(uDM));
                        SimpleActionBar.send(all, Core.getPrefix() + a);
                    }
                }
                if (uDM == 0) {
                    startDeathMatch();
                    dd.cancel();
                }
                uDM--;
            }
        }.runTaskTimer(Core.getInstance(), 0, 20 * 60);
    }

    public static void startDeathMatch() {
        for (Player ingame : Core.getInGamePlayers()) {
            if (getSpawn() == null) {
                Location l = ingame.getWorld().getSpawnLocation();

                Location r = getRandomLocation(l, l.getBlockX() - 20, l.getBlockX() + 20, l.getBlockZ() - 20, l.getBlockZ() + 20);

                Location lr = r != null ? r.getWorld().getHighestBlockAt(r.getBlockX(), r.getBlockZ()).getLocation() : null;
                ingame.teleport(lr);
                Border.setSize(50);
            } else {
                Location l = getSpawn();

                Location r = getRandomLocation(l, l.getBlockX() - 20, l.getBlockX() + 20, l.getBlockZ() - 20, l.getBlockZ() + 20);

                Location lr = r != null ? r.getWorld().getHighestBlockAt(r.getBlockX(), r.getBlockZ()).getLocation() : null;

                ingame.teleport(lr);
                Border.setSize(50);
            }
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            AScoreboard.setInGamePvPTime(all);
        }
        GState.setGameState(GState.PREDEATHMATCH);
        ee = new BukkitRunnable() {
            @Override
            public void run() {
                new BukkitRunnable() {
                    @Override
                    public void run() {

                        if (tbpvp % 5 == 0 && tbpvp > 10) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                String a = dmmsg.replace("[time]", Integer.toString(tbpvp)).replace("minutes", "seconds");
                                SimpleActionBar.send(all, Core.getPrefix() + a);
                            }
                        }

                        if (tbpvp > 0 && tbpvp < 10) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                String a = dmmsg.replace("[time]", Integer.toString(tbpvp)).replace("minutes", "seconds");
                                SimpleActionBar.send(all, Core.getPrefix() + a);
                            }
                        }

                        if (tbpvp == 0) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                GState.setGameState(GState.DEATHMATCH);
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_PLING, 10F, 0);//TODO: multi
                            }
                            ee.cancel();
                        }
                        tbpvp--;
                    }
                }.runTask(Core.getInstance());
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
                if (endTime <= 10 && endTime != 0) {

                    endmsg = endmsg.replace("[time]", Integer.toString(endTime));

                    Bukkit.broadcastMessage(Core.getPrefix() + endmsg);
                    endTime = endTime - 1;

                    endmsg = MessageFileManager.getMSGFile().getColorString("Announcements.End");
                } else if (endTime == 0) {

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (BungeeMode) {
                            ByteArrayDataOutput out = ByteStreams.newDataOutput();

                            out.writeUTF("Connect");
                            out.writeUTF(BungeeServer);

                            all.sendPluginMessage(Core.getInstance(), "BungeeCord", out.toByteArray());
                        } else {
                            all.kickPlayer(Core.getPrefix() + GameEndListener.getKick());
                        }
                    }
                    f.cancel();

                }
            }
        }.runTaskTimer(Core.getInstance(), 0, 20);

    }

    public static void changeTime() {
        high = 10;
    }

    private static void resetTime() {
        high = OptionsFileManager.getConfigFile().getInt("Countdown.lobby");
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(high);
        }
    }

    public static void setCountdownTime() {
        high = OptionsFileManager.getConfigFile().getInt("Countdown.lobby");
        gracetime = OptionsFileManager.getConfigFile().getInt("Countdown.graceperiod");
    }

    private static void giveCompass(Player p) {
        if (comMode) {
            p.getInventory().addItem(new ItemBuilder(comItem).setName(comName).build());
        }
    }
}
