package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class DamageDogersListener extends SimpleListener {

    private int amount = 0;

    public DamageDogersListener() {
        amount = new Random().nextInt(25);
    }

    @EventHandler
    public void onHurt(EntityDamageEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.DAMAGE_DODGERS))
            return;
        if (!(e.getEntity() instanceof Player))
            return;

        if (amount > 0) {
            ((Player) e.getEntity()).setHealth(0.0);

            amount--;
        }
    }
}
