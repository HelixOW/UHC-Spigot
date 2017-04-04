package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SheepLoversListener extends SimpleListener {

    @EventHandler
    public void onShear(PlayerShearEntityEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.SHEEP_LOVERS)) return;

        if (Math.random() < 0.05)
            e.getPlayer().getWorld().dropItemNaturally(e.getEntity().getLocation(), new ItemStack(Material.GOLD_INGOT));
    }

}
