package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.InGameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class ErraticPvPListener extends SimpleListener {

    @EventHandler
    public void onStart(InGameStartEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.ERRATIC_PVP))
            return;

        int temp = 1;

        if (Math.random() < 0.5) {
            temp = 3;
        }
        final int delay = temp;

        new BukkitRunnable() {
            public void run() {
                Bukkit.getWorld("UHC").setPVP(!Bukkit.getWorld("UHC").getPVP());
            }
        }.runTaskTimer(UHC.getInstance(), 0, (20 * 60) * delay);
    }

}
