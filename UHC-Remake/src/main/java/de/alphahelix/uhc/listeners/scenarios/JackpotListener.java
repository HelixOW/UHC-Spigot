package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class JackpotListener extends SimpleListener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.JACKPOT)) return;

        if (e.getBlock().getType().equals(Material.GOLD_ORE)) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);

            for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
                p.getInventory().addItem(new ItemStack(Material.GOLD_ORE));
            }
        } else if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);

            for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
                p.getInventory().addItem(new ItemStack(Material.DIAMOND));
            }
        }
    }

}
