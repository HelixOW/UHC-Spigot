package de.alphahelix.uhc.timers;

import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class BestPvETimer {

    private static BukkitTask best;

    public void stopTimer() {
        if (best != null)
            best.cancel();
        best = null;
    }

    public boolean isRunning() {
        return best != null;
    }

    public void startBestTimer(final ArrayList<String> bestList) {
        if (best != null) {
            if (Bukkit.getScheduler().isCurrentlyRunning(best.getTaskId()))
                return;
            return;
        }

        best = new BukkitRunnable() {
            public void run() {
                for (Player p : Util.makePlayerArray(bestList)) {
                    if (p.getHealth() == 20.0 || p.getHealth() == 0.0)
                        continue;
                    p.setHealth(p.getHealth() + 1);
                }
            }
        }.runTaskTimer(UHC.getInstance(), 0, (20 * 60) * 10);
    }
}
