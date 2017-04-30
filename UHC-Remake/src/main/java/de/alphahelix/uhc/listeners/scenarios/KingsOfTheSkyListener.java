package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.LinkedList;

public class KingsOfTheSkyListener extends SimpleListener {

    private LinkedList<String> justHealed = new LinkedList<>();

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.KINGS_OF_THE_SKY))
            return;

        Bukkit.getWorld("UHC").getSpawnLocation().clone()
                .add(0, (200 - Bukkit.getWorld("UHC").getSpawnLocation().getY()), 0).getBlock()
                .setType(Material.GOLD_BLOCK);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.KINGS_OF_THE_SKY))
            return;

        if (e.getBlock().getLocation().equals(Bukkit.getWorld("UHC").getSpawnLocation().clone().add(0,
                (200 - Bukkit.getWorld("UHC").getSpawnLocation().getY()), 0)))
            e.setCancelled(true);

    }

    @EventHandler
    public void onSeak(PlayerToggleSneakEvent e) {
        if (e.isCancelled())
            return;
        if (!e.isSneaking())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.KINGS_OF_THE_SKY))
            return;

        if (e.getPlayer().getLocation().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.GOLD_BLOCK)
                && e.getPlayer().getLocation().getBlockY() > 199) {
            if (justHealed.contains(e.getPlayer().getName())) return;
            e.getPlayer().setHealth(e.getPlayer().getHealth() > 19 ? 20 : e.getPlayer().getHealth() + 1.0);
            Util.cooldown(20 * 60, e.getPlayer().getName(), justHealed);
        }
    }
}
