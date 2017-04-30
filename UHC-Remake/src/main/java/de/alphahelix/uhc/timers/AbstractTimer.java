package de.alphahelix.uhc.timers;

import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.interfaces.ITimer;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class AbstractTimer {

    private BukkitTask timer;
    private double seconds;

    public double getHourTime() {
        return seconds / 3600;
    }

    public double getMinTime() {
        return (seconds % 3600) / 60;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    public String getSecondsText() {
        if (getHourTime() > 1 && getMinTime() > 60 && getSeconds() > 60)
            return Double.toString(Util.round(getHourTime(), 1)) + UHCFileRegister.getUnitFile().getHours();
        else if (getHourTime() < 1 && getMinTime() < 60 && getSeconds() > 60)
            return Double.toString(Util.round(getMinTime(), 1)) + UHCFileRegister.getUnitFile().getMinutes();
        else
            return Double.toString(getSeconds()) + UHCFileRegister.getUnitFile().getSeconds();
    }

    public boolean isRunning() {
        return timer != null;
    }

    public void stopTimer() {
        if (timer != null)
            timer.cancel();
        timer = null;
        resetTime();
    }

    public abstract void startTimer();

    public void setSecondTimer(ITimer timer) {
        setTimer(new BukkitRunnable() {
            @Override
            public void run() {
                timer.run();
            }
        }.runTaskTimer(UHC.getInstance(), 0, 20));
    }

    public void changeTime(int time) {
        this.seconds = time;
    }

    public void resetTime() {
        seconds = UHCFileRegister.getTimerFile().getLenght(GState.LOBBY);
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel((int) seconds);
        }
    }

    public BukkitTask getTimer() {
        return timer;
    }

    public void setTimer(BukkitTask timer) {
        this.timer = timer;
    }
}
