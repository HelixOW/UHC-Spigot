package de.alpha.uhc.timer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.nms.SimpleActionBar;
import de.popokaka.alphalibary.nms.SimpleTitle;


public class Timer {
	
	private Core pl;
	private Registery r;
	
	public Timer(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  String countmsg;
    private  String nep;
    private  String gracemsg;
    private  String end;
    private  String endmsg;
    private  String dmmsg;
    private  String pvpmsg;
    private  String pvpstart;
    private  boolean comMode;
    private  Material comItem;
    private  String comName;
    private  boolean dm;
    private  int pc;
    private  int high;
    private  int gracetime;
    private  int max;
    private  int uDM;
    private  int tbpvp;
    private  int prePvP;
    private  int endTime;
    private  BukkitTask a;
    private  BukkitTask b;
    private  BukkitTask c;
    private  BukkitTask d;
    private  BukkitTask e;
    private  BukkitTask dd;
    private  BukkitTask ee;
    private  BukkitTask f;
    private  boolean BungeeMode;
    private  String BungeeServer;

    public  void setCountmsg(String countmsg) {
        this.countmsg = countmsg;
    }

    public  void setNep(String nep) {
    	this.nep = nep;
    }

    public  void setGracemsg(String gracemsg) {
    	this.gracemsg = gracemsg;
    }

    public  void setEnd(String end) {
    	this.end = end;
    }

    public  void setEndmsg(String endmsg) {
    	this.endmsg = endmsg;
    }

    public  void setDmmsg(String dmmsg) {
    	this.dmmsg = dmmsg;
    }

    public  void setPvpmsg(String pvpmsg) {
    	this.pvpmsg = pvpmsg;
    }

    public  void setPvpstart(String pvpstart) {
    	this.pvpstart = pvpstart;
    }

    public  void setComMode(boolean comMode) {
    	this.comMode = comMode;
    }

    public  void setComItem(Material comItem) {
    	this.comItem = comItem;
    }

    public  void setComName(String comName) {
    	this.comName = comName;
    }

    public  boolean isDm() {
        return dm;
    }

    public  void setDm(boolean dm) {
    	this.dm = dm;
    }

    public  int getPc() {
        return pc;
    }

    public  void setPc(int pc) {
    	this.pc = pc;
    }

    public  int getGracetime() {
        return gracetime;
    }

    public  void setMax(int max) {
    	this.max = max;
    }

    public  int getuDM() {
        return uDM;
    }

    public  void setuDM(int uDM) {
    	this.uDM = uDM;
    }

    public  void setTbpvp(int tbpvp) {
    	this.tbpvp = tbpvp;
    }

    public  int getPrePvP() {
        return prePvP;
    }

    public  void setPrePvP(int prePvP) {
    	this.prePvP = prePvP;
    }

    public  BukkitTask getA() {
        return a;
    }

    public  void setA(BukkitTask a) {
    	this.a = a;
    }

    public  BukkitTask getB() {
        return b;
    }

    public  void setB(BukkitTask b) {
    	this.b = b;
    }

    public  BukkitTask getC() {
        return c;
    }

    public  void setC(BukkitTask c) {
    	this.c = c;
    }

    public  BukkitTask getE() {
        return e;
    }

    public  void setE(BukkitTask e) {
    	this.e = e;
    }

    public  BukkitTask getDd() {
        return dd;
    }

    public  void setBungeeMode(boolean bungeeMode) {
        BungeeMode = bungeeMode;
    }

    public  void setBungeeServer(String bungeeServer) {
        BungeeServer = bungeeServer;
    }

    public void startCountdown() {

        if (GState.isState(GState.LOBBY)) {

            r.getSpawnFileManager().createSpawnWorld();

            a = new BukkitRunnable() {

                @Override
                public void run() {

                    if (high > 0) {

                        high--;

                        b = new BukkitRunnable() {

                            @Override
                            public void run() {

                                if (Bukkit.getOnlinePlayers().size() >= pc) {

                                    for (Player all : pl.getInGamePlayers()) {

                                        all.setLevel(high);

                                        if (high % 10 == 0 && high > 10 && high != 0) {
                                            countmsg = countmsg.replace("[time]", Integer.toString(high));
                                            all.sendMessage(pl.getPrefix() + countmsg);
                                            SimpleTitle.sendTitle(all, " ", countmsg, 1, 2, 1);
                                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BASS, 1F, 0F);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {

                                                @Override
                                                public void run() {

                                                    countmsg = r.getMessageFile().getMSGFile().getColorString("Announcements.Countdown");

                                                }
                                            }, 2);
                                        }

                                        if (high < 10 && high != 0) {

                                            countmsg = countmsg.replace("[time]", Integer.toString(high));
                                            all.sendMessage(pl.getPrefix() + countmsg);
                                            SimpleActionBar.send(all, countmsg);
                                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BASS, 1F, 0F);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {

                                                @Override
                                                public void run() {

                                                    countmsg = r.getMessageFile().getMSGFile().getColorString("Announcements.Countdown");
                                                }
                                            }, 2);
                                        }

                                        if (high == 0) {

                                            a.cancel();

                                            if (r.getAWorld().isLobbyAsSchematic()) {
                                                r.getLobbyPasteUtil().removeLobby();
                                            }

                                            for (Player ig : pl.getInGamePlayers()) {

                                                if (!r.getAWorld().isLobbyAsSchematic()) {

                                                    try {

                                                        if (r.getSpawnFileManager().getSpawn() == null) {
                                                            ig.teleport(ig.getWorld().getHighestBlockAt(ig.getWorld().getSpawnLocation()).getLocation());
                                                            r.getBorder().setDistanceLoc(ig.getWorld().getSpawnLocation());
                                                        } else {
                                                            Location l = r.getSpawnFileManager().getSpawn();

                                                            Location rr = l.getWorld().getHighestBlockAt(r.getSpawnFileManager().getRandomLocation(l, l.getBlockX() - max, l.getBlockX() + max, l.getBlockZ() - max, l.getBlockZ() + max)).getLocation();

                                                            ig.teleport(rr);
                                                            r.getBorder().setDistanceLoc(r.getSpawnFileManager().getSpawn());
                                                        }
                                                    } catch (Exception e) {
                                                        ig.teleport(ig.getWorld().getHighestBlockAt(ig.getWorld().getSpawnLocation()).getLocation());
                                                        r.getBorder().setDistanceLoc(ig.getWorld().getSpawnLocation());
                                                    }

                                                } else {
                                                    r.getBorder().setDistanceLoc(r.getSpawnFileManager().getLobby().getWorld().getHighestBlockAt(r.getSpawnFileManager().getLobby()).getLocation());
                                                }
                                                b.cancel();

                                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_HARP, 1F, 0F);
                                                all.getWorld().setGameRuleValue("naturalRegeneration", "false");
                                                startGracePeriod();
                                                r.getBorder().border();
                                                GState.setGameState(GState.GRACE);
                                                all.setGameMode(GameMode.SURVIVAL);
                                                r.getATablist().sendStandingInGameTablist();

                                                if (r.getLobbyListener().hasSelKit(ig)) {
                                                    for (ItemStack is : r.getKitFile().getContents(r.getLobbyListener().getSelKit(ig)).getContents()) {
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
                                    Bukkit.broadcastMessage(pl.getPrefix() + nep);
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

    private void startGracePeriod() {

        if (GState.isState(GState.GRACE)) {
            return;
        }
        for (Player all : pl.getInGamePlayers()) {
            for (Entity kill : all.getWorld().getEntities()) {

                if (!(kill instanceof Player)) {
                    kill.remove();
                }
            }
            all.getInventory().clear();
            r.getAScoreboard().sendAntiFlickerInGameBoard(all);
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
                            	r.getAScoreboard().updateInGamePvPTime(all);
                            }
                            if (gracetime % 10 == 0 && gracetime > 0) {
                                gracemsg = gracemsg.replace("[time]", Integer.toString(gracetime));
                                Bukkit.broadcastMessage(pl.getPrefix() + gracemsg);
                                gracemsg = r.getMessageFile().getMSGFile().getColorString("Announcements.Peaceperiod.timer");
                                return;
                            }

                            if (gracetime == 0) {

                                e.cancel();

                                Bukkit.broadcastMessage(pl.getPrefix() + end);
//                                r.getBorderManager().set();
                                for (final Player all : pl.getInGamePlayers()) {
                                    all.showPlayer(all);
                                    giveCompass(all);
                                    GState.setGameState(GState.PREGAME);
                                    startSilentGStateWatcher();
                                    r.getATablist().sendStandingInGameTablist();
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

    private void startSilentGStateWatcher() {
        d = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    r.getAScoreboard().updateInGamePvPTime(all);
                }
                if (prePvP > 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        String a = pvpmsg.replace("[time]", Integer.toString(prePvP));
                        SimpleActionBar.send(all, pl.getPrefix() + a);
                    }
                }

                if (prePvP == 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        SimpleActionBar.send(all, pl.getPrefix() + pvpstart);
                    }
                    GState.setGameState(GState.INGAME);
                    if (dm) startSilentDeathMatchTimer();
                    d.cancel();
                }
                prePvP--;
            }
        }.runTaskTimer(Core.getInstance(), 0, 20 * 60);
    }

    private void startSilentDeathMatchTimer() {
        dd = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    r.getAScoreboard().updateInGamePvPTime(all);
                }
                if (uDM % 5 == 0 && uDM > 10) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        String a = dmmsg.replace("[time]", Integer.toString(uDM));
                        SimpleActionBar.send(all, pl.getPrefix() + a);
                    }
                }

                if (uDM > 0 && uDM < 10) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        String a = dmmsg.replace("[time]", Integer.toString(uDM));
                        SimpleActionBar.send(all, pl.getPrefix() + a);
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

    public void startDeathMatch() {
        for (Player ingame : pl.getInGamePlayers()) {
            if (r.getSpawnFileManager().getSpawn() == null) {
                Location l = ingame.getWorld().getSpawnLocation();

                Location rr = r.getSpawnFileManager().getRandomLocation(l, l.getBlockX() - 20, l.getBlockX() + 20, l.getBlockZ() - 20, l.getBlockZ() + 20);

                Location lr = rr != null ? rr.getWorld().getHighestBlockAt(rr.getBlockX(), rr.getBlockZ()).getLocation() : null;
                ingame.teleport(lr);
                r.getBorder().setSize(50);
            } else {
                Location l = r.getSpawnFileManager().getSpawn();

                Location rr = r.getSpawnFileManager().getRandomLocation(l, l.getBlockX() - 20, l.getBlockX() + 20, l.getBlockZ() - 20, l.getBlockZ() + 20);

                Location lr = rr != null ? rr.getWorld().getHighestBlockAt(rr.getBlockX(), rr.getBlockZ()).getLocation() : null;

                ingame.teleport(lr);
                r.getBorder().setSize(50);
            }
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            r.getAScoreboard().setInGamePvPTime(all);
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
                                SimpleActionBar.send(all, pl.getPrefix() + a);
                            }
                        }

                        if (tbpvp > 0 && tbpvp < 10) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                String a = dmmsg.replace("[time]", Integer.toString(tbpvp)).replace("minutes", "seconds");
                                SimpleActionBar.send(all, pl.getPrefix() + a);
                            }
                        }

                        if (tbpvp == 0) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                GState.setGameState(GState.DEATHMATCH);
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_PLING, 10F, 0);
                            }
                            ee.cancel();
                        }
                        tbpvp--;
                    }
                }.runTask(Core.getInstance());
            }
        }.runTaskTimer(Core.getInstance(), 0, 20);
    }

    public void startRestartTimer() {

        endTime = 10;
        GState.setGameState(GState.RESTART);

        r.getATablist().sendStandingInGameTablist();

        f = new BukkitRunnable() {

            @Override
            public void run() {
                if (endTime <= 10 && endTime != 0) {

                    endmsg = endmsg.replace("[time]", Integer.toString(endTime));

                    Bukkit.broadcastMessage(pl.getPrefix() + endmsg);
                    endTime = endTime - 1;

                    endmsg = r.getMessageFile().getMSGFile().getColorString("Announcements.End");
                } else if (endTime == 0) {

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (BungeeMode) {
                            ByteArrayDataOutput out = ByteStreams.newDataOutput();

                            out.writeUTF("Connect");
                            out.writeUTF(BungeeServer);

                            all.sendPluginMessage(pl, "BungeeCord", out.toByteArray());
                        } else {
                            all.kickPlayer(pl.getPrefix() + r.getGameEndListener().getKick());
                        }
                    }
                    f.cancel();

                }
            }
        }.runTaskTimer(pl, 0, 20);

    }

    public  void changeTime() {
        high = 10;
    }

    private  void resetTime() {
        high = r.getOptionsFile().getConfigFile().getInt("Countdown.lobby");
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(high);
        }
    }

    public  void setCountdownTime() {
        high = r.getOptionsFile().getConfigFile().getInt("Countdown.lobby");
        gracetime = r.getOptionsFile().getConfigFile().getInt("Countdown.graceperiod");
    }

    private  void giveCompass(Player p) {
        if (comMode) {
            p.getInventory().addItem(new ItemBuilder(comItem).setName(comName).build());
        }
    }
}
