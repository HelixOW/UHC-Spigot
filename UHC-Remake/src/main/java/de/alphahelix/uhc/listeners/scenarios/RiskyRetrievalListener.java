package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class RiskyRetrievalListener extends SimpleListener {

    private Chest rr;

    public RiskyRetrievalListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.RISKY_RETRIEVAL))
            return;

        UHCFileRegister.getLocationsFile().getArena().getBlock().setType(Material.CHEST);
        rr = (Chest) UHCFileRegister.getLocationsFile().getArena().getBlock().getState();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.RISKY_RETRIEVAL))
            return;

        if (e.getBlock().getType().equals(Material.GOLD_ORE) || e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
            rr.getBlockInventory()
                    .addItem(e.getBlock().getDrops().toArray(new ItemStack[e.getBlock().getDrops().size()]));
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
        } else if (e.getBlock().getX() == rr.getX() && e.getBlock().getY() == rr.getY()
                && e.getBlock().getZ() == rr.getZ())
            e.setCancelled(true);
    }
}
