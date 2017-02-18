package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

public class SelectOresListener extends SimpleListener {

    private HashMap<String, Integer> d = new HashMap<>();
    private HashMap<String, Integer> g = new HashMap<>();
    private HashMap<String, Integer> i = new HashMap<>();

    public SelectOresListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.RISKY_RETRIEVAL))
            return;

        if (e.getBlock().getType().equals(Material.GOLD_ORE)) {
            if (!g.containsKey(e.getPlayer().getName())) {
                g.put(e.getPlayer().getName(), 1);
            } else {
                if (g.get(e.getPlayer().getName()) < 32) {
                    g.put(e.getPlayer().getName(), g.get(e.getPlayer().getName()) + 1);
                } else {
                    e.setCancelled(true);
                    e.getBlock().setType(Material.AIR);
                }
            }
        } else if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
            if (!d.containsKey(e.getPlayer().getName())) {
                d.put(e.getPlayer().getName(), 1);
            } else {
                if (d.get(e.getPlayer().getName()) < 10) {
                    d.put(e.getPlayer().getName(), d.get(e.getPlayer().getName()) + 1);
                } else {
                    e.setCancelled(true);
                    e.getBlock().setType(Material.AIR);
                }
            }
        } else if (e.getBlock().getType().equals(Material.IRON_ORE)) {
            if (!i.containsKey(e.getPlayer().getName())) {
                i.put(e.getPlayer().getName(), 1);
            } else {
                if (i.get(e.getPlayer().getName()) < 64) {
                    i.put(e.getPlayer().getName(), i.get(e.getPlayer().getName()) + 1);
                } else {
                    e.setCancelled(true);
                    e.getBlock().setType(Material.AIR);
                }
            }
        }
    }
}
