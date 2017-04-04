package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.nms.SimpleActionBar;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;

public class SoulBrothersListener extends SimpleListener {

    private HashMap<String, Double> soulBrotherhealth = new HashMap<>();

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.SOUL_BROTHERS))
            return;
        if (!(e.getEntity() instanceof Player))
            return;

        Player p = (Player) e.getEntity();

        if (!p.isSneaking())
            return;
        if (soulBrotherhealth.containsKey(p.getName())) {
            if (soulBrotherhealth.get(p.getName()) > e.getDamage()) {
                soulBrotherhealth.put(p.getName(), soulBrotherhealth.get(p.getName()) - e.getDamage());
                e.setCancelled(true);
                SimpleActionBar.send(p, "§7" + soulBrotherhealth.get(p.getName()).intValue());
            }
        } else {
            soulBrotherhealth.put(p.getName(), p.getMaxHealth() - e.getDamage());
            e.setCancelled(true);
            SimpleActionBar.send(p, "§7" + soulBrotherhealth.get(p.getName()).intValue());
        }
    }
}
