package de.alphahelix.uhc.timers;

import de.alphahelix.alphalibary.nms.SimpleActionBar;
import de.alphahelix.alphalibary.nms.SimpleTitle;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.events.timers.InGameStartEvent;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class DeathmatchTimer extends Util {

    private static BukkitTask timer, deathmatch;
    private int time;
    private double min, h;
    private boolean hourSend, customTime;

    public DeathmatchTimer(UHC uhc) {
        super(uhc);
    }

    public void stopTimer() {
        if (timer != null)
            timer.cancel();
        timer = null;
        if (deathmatch != null)
            deathmatch.cancel();
        deathmatch = null;
        resetTime();
    }

    private double getMinTime() {
        return min;
    }

    private double getHourTime() {
        return calcHours(time);
    }

    public String getTime() {
        if (getHourTime() > 1 && getMinTime() > 60 && time > 60)
            return Double.toString(round(getHourTime(), 1)) + UHCFileRegister.getUnitFile().getHours();
        else if (getHourTime() < 1 && getMinTime() < 60 && time > 60)
            return Double.toString(round(getMinTime(), 1)) + UHCFileRegister.getUnitFile().getMinutes();
        else
            return Integer.toString(time) + UHCFileRegister.getUnitFile().getSeconds();
    }

    public void setTime(int t) {
        time = t;
        customTime = true;
    }

    public boolean isRunning() {
        return timer != null;
    }

    public void startDeathMatchTimer() {
        if (!GState.isState(GState.IN_GAME))
            return;

        if (timer != null) {
            if (Bukkit.getScheduler().isCurrentlyRunning(timer.getTaskId()))
                return;
            return;
        }
        if (!customTime)
            resetTime();

        Bukkit.getPluginManager().callEvent(new InGameStartEvent());

        timer = new BukkitRunnable() {
            public void run() {
                if (time > 0) {
                    time--;

                    deathmatch = new BukkitRunnable() {
                        public void run() {
                            for (String pName : UHCRegister.getPlayerUtil().getAll()) {
                                Player p = Bukkit.getPlayer(pName);

                                if (p == null)
                                    return;

                                min = calcMin(time);
                                h = calcHours(time);

                                UHCRegister.getScoreboardUtil().updateTime(p);

                                if ((h % 1 == 0 && !hourSend) && min > 60 && time != 0) {
                                    hourSend = true;
                                    p.sendMessage(getUhc().getPrefix() + UHCFileRegister.getMessageFile()
                                            .getTimeLeftInfo(round(h, 1),
                                                    (h >= 1 ? UHCFileRegister.getUnitFile().getHours()
                                                            : UHCFileRegister.getUnitFile().getMinutes())));
                                    SimpleTitle.sendTitle(
                                            p,
                                            getUhc().getPrefix(),
                                            getUhc().getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(round(h, 1), (h >= 1 ? UHCFileRegister.getUnitFile().getHours() : UHCFileRegister.getUnitFile().getMinutes())),
                                            1,
                                            2,
                                            1);

                                    p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                                    continue;
                                }

                                if (min % 5 == 0 && time > 10 && time != 0) {

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
                                } else if (time < 10 && time != 0) {

                                    p.sendMessage(
                                            getUhc().getPrefix()
                                                    + UHCFileRegister.getMessageFile().getTimeLeftInfo(time, UHCFileRegister.getUnitFile().getSeconds()));

                                    SimpleActionBar.send(p,
                                            getUhc().getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(time, UHCFileRegister.getUnitFile().getSeconds()));
                                    p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                                } else if (time == 0) {
                                    timer.cancel();

                                    p.sendMessage(getUhc().getPrefix()
                                            + UHCFileRegister.getMessageFile().getEnd());

                                    SimpleActionBar.send(p, getUhc().getPrefix()
                                            + UHCFileRegister.getMessageFile().getEnd());

                                    p.teleport(UHCFileRegister.getLocationsFile().getDeathmatch());

                                    GState.setCurrentState(GState.DEATHMATCH_WARMUP);

                                    UHCRegister.getStartDeathmatchTimer().startDeathMatchTimer();

                                    deathmatch.cancel();
                                }
                            }
                        }
                    }.runTask(getUhc());
                }
            }
        }.runTaskTimer(getUhc(), 0, 20);
    }

    private void resetTime() {
        time = UHCFileRegister.getTimerFile().getLenght(GState.DEATHMATCH_WARMUP);
    }
}
