package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HashtagBowListener extends SimpleListener {

    public HashtagBowListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onHurt(EntityDamageByEntityEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.HASHTAGBOW))
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
