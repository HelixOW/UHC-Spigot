package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PvCListener extends SimpleListener {

    public PvCListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent e) {
        if (e.isCancelled()) return;
        if (!scenarioCheck(Scenarios.PVC)) return;

        if (!e.getCause().equals(DamageCause.CONTACT)) e.setCancelled(true);
    }

}
