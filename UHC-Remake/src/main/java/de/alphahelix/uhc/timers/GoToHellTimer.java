package de.alphahelix.uhc.timers;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GoToHellTimer extends SimpleListener {

    private static BukkitTask damage;

    public void stopTimer() {
        if (damage != null)
            damage.cancel();
        damage = null;
    }

    public boolean isRunning() {
        return damage != null;
    }


    public void startDamageTimer() {
        if (isRunning()) {
            if (Bukkit.getScheduler().isCurrentlyRunning(damage.getTaskId()))
                return;
            return;
        }
        damage = new BukkitRunnable() {
            public void run() {
                for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
                    if (p.getLocation().getWorld().getEnvironment().equals(Environment.NORMAL))
                        p.damage(1.0);
                }
            }
        }.runTaskTimer(UHC.getInstance(), (20 * 60) * 45, (20 * 30));
    }
}
