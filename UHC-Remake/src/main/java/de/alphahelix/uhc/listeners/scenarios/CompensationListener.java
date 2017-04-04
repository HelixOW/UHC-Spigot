package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CompensationListener extends SimpleListener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.COMPENSATION))
            return;
        if (!(e.getEntity().getKiller() != null))
            return;

        e.getEntity().getKiller().setMaxHealth(e.getEntity().getKiller().getMaxHealth() + (e.getEntity().getMaxHealth() / 4));
        e.getEntity().getKiller().setHealth(e.getEntity().getKiller().getMaxHealth());
    }
}
