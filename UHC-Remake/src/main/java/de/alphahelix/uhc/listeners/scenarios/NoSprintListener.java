package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class NoSprintListener extends SimpleListener {

    @EventHandler
    public void onPlayerToggleSprint(PlayerToggleSprintEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NO_SPRINT))
            return;

        final Player p = e.getPlayer();

        if (e.isSprinting()) {
            final int foodlevel = p.getFoodLevel();
            p.setFoodLevel(5);

            new BukkitRunnable() {
                public void run() {
                    p.setFoodLevel(foodlevel);
                }
            }.runTaskLater(UHC.getInstance(), 20 * 2);
        }
    }

}
