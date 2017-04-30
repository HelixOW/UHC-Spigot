package de.alphahelix.uhc.timers;

import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EntropyTimer {

    private static BukkitTask draining;

    public void stopTimer() {
        if (draining != null)
            draining.cancel();
        draining = null;
    }

    public boolean isRunning() {
        return draining != null;
    }

    public void startDrainingTimer() {
        if (draining != null) {
            if (Bukkit.getScheduler().isCurrentlyRunning(draining.getTaskId()))
                return;
            return;
        }

        draining = new BukkitRunnable() {
            public void run() {
                for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
                    if (p.getLevel() == 0)
                        p.setHealth(20.0);
                    else
                        p.setLevel(p.getLevel() - 1);
                }
            }
        }.runTaskTimer(UHC.getInstance(), 0, (20 * 60) * 10);
    }
}
