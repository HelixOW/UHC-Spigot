package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BloodyLapisListener extends SimpleListener {

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BLOODY_LAPIS)) return;
        if (!e.getBlock().getType().equals(Material.LAPIS_ORE)) return;

        e.getPlayer().damage(2.0);
    }
}
