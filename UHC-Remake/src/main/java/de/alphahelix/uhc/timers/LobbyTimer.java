package de.alphahelix.uhc.timers;

import de.alphahelix.alphalibary.nms.SimpleActionBar;
import de.alphahelix.alphalibary.nms.SimpleTitle;
import de.alphahelix.alphalibary.utils.Cuboid;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class LobbyTimer extends Util {

    private static BukkitTask timer;
    private static BukkitTask lobby;
    private static boolean isAlreadyPasted;
    private int time;
    private double min;

    public LobbyTimer(UHC uhc) {
        super(uhc);
    }

    public void stopTimer() {
        if (timer != null)
            timer.cancel();
        timer = null;
        if (lobby != null)
            lobby.cancel();
        lobby = null;
        resetTime();
    }

    public boolean isRunning() {
        return timer != null;
    }

    public int getCurrentTimeInSeconds() {
        return this.time;
    }

    public void startLobbyCountdown() {
        if (GState.isState(GState.LOBBY)) {

            if (timer != null) {
                if (Bukkit.getScheduler().isCurrentlyRunning(timer.getTaskId()))
                    return;
                return;
            }

            resetTime();

            if (!isAlreadyPasted) {
                isAlreadyPasted = true;
                UHCRegister.getSchematicManagerUtil()
                        .load(UHCFileRegister.getOptionsFile().getLobbyFileName());
                UHCRegister.getSchematicManagerUtil()
                        .paste(UHCFileRegister.getLocationsFile().getArena().add(0, 140, 0));
            }

            timer = new BukkitRunnable() {
                public void run() {

                    if (!(UHCRegister.getPlayerUtil().getAll().size() >= UHCRegister.getPlayerUtil()
                            .getMinimumPlayerCount())) {
                        resetTime();
                        return;
                    }

                    if (time > 0) {
                        time--;

                        lobby = new BukkitRunnable() {
                            private double h;

                            public void run() {
                                if (UHCRegister.getPlayerUtil().getAll().size() >= UHCRegister.getPlayerUtil()
                                        .getMinimumPlayerCount()) {
                                    if (time == 0) {
                                        new BukkitRunnable() {
                                            public void run() {
                                                Bukkit.getPluginManager().callEvent(new LobbyEndEvent());
                                                if (getUhc().isLobbyAsSchematic()) {
                                                    World w = UHCFileRegister.getLocationsFile().getArena().getWorld();
                                                    Location l1 = new Location(w, -75, 155, -75);
                                                    Location l2 = new Location(w, 75, 255, 75);

                                                    for (Block b : new Cuboid(l1, l2).getBlocks()) {
                                                        if (b.getType().equals(Material.AIR))
                                                            continue;
                                                        b.setType(Material.AIR);
                                                    }
                                                }
                                            }
                                        }.runTaskLater(getUhc(), 20);
                                    } else if (time == 10) {
                                        Scenarios.setPlayedScenario(
                                                UHCRegister.getScenarioInventory().getScenarioWithMostVotes());
                                    }
                                    for (String pName : UHCRegister.getPlayerUtil().getAll()) {
                                        Player p = Bukkit.getPlayer(pName);

                                        if (p == null)
                                            return;

                                        min = calcMin(time);

                                        p.setLevel(time);

                                        if (min % 1 == 0 && time > 10 && time != 0) {
                                            p.sendMessage(getUhc().getPrefix() + UHCFileRegister.getMessageFile()
                                                    .getTimeLeftInfo(round(h, 1),
                                                            (h >= 1 ? UHCFileRegister.getUnitFile().getHours()
                                                                    : UHCFileRegister.getUnitFile().getMinutes())));
                                            SimpleTitle.sendTitle(p, getUhc().getPrefix(), getUhc().getPrefix() + UHCFileRegister.getMessageFile()
                                                            .getTimeLeftInfo(round(h, 1),
                                                                    (h >= 1 ? UHCFileRegister.getUnitFile().getHours()
                                                                            : UHCFileRegister.getUnitFile().getMinutes())),
                                                    1, 2, 1);
                                            p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);

                                        }

                                        if (time % 30 == 0 && !(min % 1 == 0)) {
                                            p.sendMessage(
                                                    getUhc().getPrefix()
                                                            + UHCFileRegister.getMessageFile().getTimeLeftInfo(round(min, 1), (min >= 1 ? UHCFileRegister.getUnitFile().getMinutes() : UHCFileRegister.getUnitFile().getSeconds())));
                                            SimpleTitle.sendTitle(
                                                    p,
                                                    getUhc().getPrefix(),
                                                    UHCFileRegister.getMessageFile().getTimeLeftInfo(round(min, 1), (min >= 1 ? UHCFileRegister.getUnitFile().getMinutes() : UHCFileRegister.getUnitFile().getSeconds())),
                                                    1,
                                                    2,
                                                    1);
                                            p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                                        }

                                        if (time == 10) {
                                            UHCRegister.getScoreboardUtil().updateScenario(p,
                                                    Scenarios.getScenario());
                                            p.getInventory().setItem(
                                                    UHCFileRegister.getScenarioFile().getItem(null).getSlot(),
                                                    UHCFileRegister.getScenarioFile().getItem(Scenarios.getScenario()).getItemStack());
                                        }

                                        if (time < 10 && time != 0) {
                                            p.sendMessage(
                                                    getUhc().getPrefix()
                                                            + UHCFileRegister.getMessageFile().getTimeLeftInfo(time, UHCFileRegister.getUnitFile().getSeconds()));

                                            SimpleActionBar.send(p,
                                                    getUhc().getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(time, UHCFileRegister.getUnitFile().getSeconds()));
                                            p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                                        }

                                        if (time == 0) {
                                            timer.cancel();

                                            p.getInventory().clear();

                                            Location worldSpawn = UHCFileRegister.getLocationsFile().getArena();
                                            Location playerSpawn = worldSpawn.getWorld()
                                                    .getHighestBlockAt(getRandomLocation(worldSpawn,
                                                            worldSpawn.getBlockX() - getUhc().getSpawnradius(),
                                                            worldSpawn.getBlockX() + getUhc().getSpawnradius(),
                                                            worldSpawn.getBlockZ() - getUhc().getSpawnradius(),
                                                            worldSpawn.getBlockZ() + getUhc().getSpawnradius()))
                                                    .getLocation();

                                            p.teleport(playerSpawn);
                                            UHCRegister.getBorderUtil().setArena(worldSpawn);

                                            lobby.cancel();

                                            p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1F, 0F);
                                            p.getWorld().setGameRuleValue("naturalRegenration", "false");

                                            UHCRegister.getBorderUtil().createBorder();
                                            UHCRegister.getBorderUtil().setMovingListener();

                                            GState.setCurrentState(GState.PERIOD_OF_PEACE);
                                            p.setGameMode(GameMode.SURVIVAL);

                                            UHCRegister.getGraceTimer().startGraceTimer();

                                            UHCRegister.getPlayerUtil().addSurvivor(p);

                                            UHCRegister.getStatsUtil().addGame(p);

                                            UHCRegister.getTablistUtil().sendTablist();
                                            UHCRegister.getScoreboardUtil().setIngameScoreboard(p);
                                            for (String o : UHCRegister.getPlayerUtil().getAll()) {
                                                if (Bukkit.getPlayer(o) == null)
                                                    continue;
                                                UHCRegister.getScoreboardUtil()
                                                        .setIngameScoreboard(Bukkit.getPlayer(o));
                                            }

                                            if (UHCRegister.getPlayerUtil().getAll().size() <= 1) {
                                                GState.setCurrentState(GState.END);
                                                UHCRegister.getRestartTimer().startEndTimer();
                                            }

                                            if (getUhc().isKits()) {
                                                if (UHCFileRegister.getKitsFile().hasKit(p)) {
                                                    for (ItemStack is : UHCFileRegister.getKitsFile().getPlayedKit(p).getItems()) {
                                                        if (is != null) {
                                                            p.getInventory().addItem(is);
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }
                                } else {
                                    Bukkit.broadcastMessage(getUhc().getPrefix()
                                            + UHCFileRegister.getMessageFile().getNotEnoughPlayers());
                                    timer.cancel();
                                    lobby.cancel();
                                    resetTime();
                                }
                            }
                        }.runTask(getUhc());
                    }
                }
            }.runTaskTimer(getUhc(), 0, 20);
        }
    }

    private Location getRandomLocation(Location player, int Xminimum, int Xmaximum, int Zminimum, int Zmaximum) {
        try {
            World world = player.getWorld();
            int randomZ = Zminimum + ((int) (Math.random() * ((Zmaximum - Zminimum) + 1)));
            double x = Double.parseDouble(
                    Integer.toString(Xminimum + ((int) (Math.random() * ((Xmaximum - Xminimum) + 1))))) + 0.5d;
            double z = Double.parseDouble(Integer.toString(randomZ)) + 0.5d;
            player.setY(200);
            return new Location(world, x, player.getY(), z);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changeTime() {
        time = 10;
    }

    public void changeTime(int time) {
        this.time = time;
    }

    private void resetTime() {
        time = UHCFileRegister.getTimerFile().getLenght(GState.LOBBY);
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(time);
        }
    }
}
