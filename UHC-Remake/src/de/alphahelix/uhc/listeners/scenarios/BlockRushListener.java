package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockRushListener extends SimpleListener {

    private HashMap<String, ArrayList<Material>> hasBeenMind = new HashMap<>();

    public BlockRushListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.BLOCK_RUSH))
            return;

        if (!hasBeenMind.containsKey(e.getPlayer().getName())) {
            hasBeenMind.put(e.getPlayer().getName(), new ArrayList<Material>());
            hasBeenMind.get(e.getPlayer().getName()).add(e.getBlock().getType());

            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
        } else {
            if (!hasBeenMind.get(e.getPlayer().getName()).contains(e.getBlock().getType())) {
                hasBeenMind.get(e.getPlayer().getName()).add(e.getBlock().getType());

                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),
                        new ItemStack(Material.GOLD_INGOT));
            }
        }
    }
}
