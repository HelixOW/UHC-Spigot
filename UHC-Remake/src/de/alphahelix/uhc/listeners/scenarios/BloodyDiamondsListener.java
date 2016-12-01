package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BloodyDiamondsListener extends SimpleListener {

    public BloodyDiamondsListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (!scenarioCheck(Scenarios.BLOODY_DIAMONDS)) return;
        if (!e.getBlock().getType().equals(Material.DIAMOND_ORE)) return;

        e.getPlayer().damage(2.0);
    }

}
