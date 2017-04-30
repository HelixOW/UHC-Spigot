package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CivilisationListener extends SimpleListener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.CIVILISATION)) return;

        int random = new Random().nextInt(2) + 1;

        if (random == 1) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.CIVILISATION)) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.CIVILISATION)) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.CIVILISATION)) return;

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.getInventory().addItem(new ItemStack(Material.EMERALD, 64), new ItemStack(Material.BREAD, 64), new ItemStack(Material.CARROT_ITEM, 3));
        }
    }
}
