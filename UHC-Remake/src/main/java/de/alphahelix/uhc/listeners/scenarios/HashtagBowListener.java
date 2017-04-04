package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HashtagBowListener extends SimpleListener {

    @EventHandler
    public void onHurt(EntityDamageByEntityEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.HASHTAGBOW))
            return;
        if (!(e.getEntity() instanceof Player))
            return;
        if (!(e.getDamager() instanceof Player))
            return;

        Player dmger = (Player) e.getDamager();

        if (dmger.getInventory().getItemInHand() == null)
            return;
        if (!dmger.getInventory().getItemInHand().getType().equals(Material.BOW))
            e.setCancelled(true);
    }

}
