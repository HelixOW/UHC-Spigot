package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PvCListener extends SimpleListener {

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.PVC)) return;

        if (!e.getCause().equals(DamageCause.CONTACT)) e.setCancelled(true);
    }

}
