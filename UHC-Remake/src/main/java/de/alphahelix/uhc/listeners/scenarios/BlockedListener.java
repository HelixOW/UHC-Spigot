package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockedListener extends SimpleListener {

    private HashMap<String, ArrayList<Block>> placed = new HashMap<>();

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BLOCKED)) return;

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
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BLOCKED)) return;
        if (!placed.containsKey(e.getPlayer().getName())) return;

        if (placed.get(e.getPlayer().getName()).contains(e.getBlock()))
            e.setCancelled(true);
    }
}
