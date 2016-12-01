package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockedListener extends SimpleListener {

    private HashMap<String, ArrayList<Block>> placed = new HashMap<>();

    public BlockedListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) return;
        if (!scenarioCheck(Scenarios.BLOCKED)) return;

        if (placed.containsKey(e.getPlayer().getName()))
            placed.get(e.getPlayer().getName()).add(e.getBlockPlaced());
        else {
            placed.put(e.getPlayer().getName(), new ArrayList<Block>());
            placed.get(e.getPlayer().getName()).add(e.getBlockPlaced());
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (!scenarioCheck(Scenarios.BLOCKED)) return;
        if (!placed.containsKey(e.getPlayer().getName())) return;

        if (placed.get(e.getPlayer().getName()).contains(e.getBlock()))
            e.setCancelled(true);
    }
}
