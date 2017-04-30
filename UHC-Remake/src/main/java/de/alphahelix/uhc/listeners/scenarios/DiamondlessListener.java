package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DiamondlessListener extends SimpleListener {

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.DIAMONDLESS))
            return;

        Bukkit.getWorld("UHC").getSpawnLocation().getBlock().setType(Material.ENCHANTMENT_TABLE);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.DIAMONDLESS))
            return;

        if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            if (Math.random() < 0.5) {
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.ROTTEN_FLESH));
            }
        } else if (Math.random() < 0.5 && e.getBlock().getType().equals(Material.GOLD_ORE)) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
        }
    }
}
