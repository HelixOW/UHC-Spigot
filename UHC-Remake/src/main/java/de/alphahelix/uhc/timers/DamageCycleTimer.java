package de.alphahelix.uhc.timers;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class DamageCycleTimer {

    private static BukkitTask cycle;

    public void stopTimer() {
        if (cycle != null)
            cycle.cancel();
        cycle = null;
    }

    public boolean isRunning() {
        return cycle != null;
    }

    public void startCycleTimer() {
        if (cycle != null) {
            if (Bukkit.getScheduler().isCurrentlyRunning(cycle.getTaskId()))
                return;
            return;
        }

        cycle = new BukkitRunnable() {
            public void run() {
                String pName = PlayerUtil.getSurvivors()
                        .get(new Random().nextInt(PlayerUtil.getSurvivors().size()));
                if (Bukkit.getPlayer(pName) == null)
                    return;

                Bukkit.getPlayer(pName).damage(1);
            }
        }.runTaskTimer(UHC.getInstance(), 0, (20 * 60) * 5);
    }

}
