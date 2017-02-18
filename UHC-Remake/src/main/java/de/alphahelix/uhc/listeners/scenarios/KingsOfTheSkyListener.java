package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.LinkedList;

public class KingsOfTheSkyListener extends SimpleListener {

    private LinkedList<String> justHealed = new LinkedList<>();

    public KingsOfTheSkyListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.KINGS_OF_THE_SKY))
            return;

        UHCFileRegister.getLocationsFile().getArena().clone()
                .add(0, (200 - UHCFileRegister.getLocationsFile().getArena().getY()), 0).getBlock()
                .setType(Material.GOLD_BLOCK);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.KINGS_OF_THE_SKY))
            return;

        if (e.getBlock().getLocation().equals(UHCFileRegister.getLocationsFile().getArena().clone().add(0,
                (200 - UHCFileRegister.getLocationsFile().getArena().getY()), 0)))
            e.setCancelled(true);

    }

    @EventHandler
    public void onSeak(PlayerToggleSneakEvent e) {
        if (e.isCancelled())
            return;
        if (!e.isSneaking())
            return;
        if (!scenarioCheck(Scenarios.KINGS_OF_THE_SKY))
            return;

        if (e.getPlayer().getLocation().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.GOLD_BLOCK)
                && e.getPlayer().getLocation().getBlockY() > 199) {
            if (justHealed.contains(e.getPlayer().getName())) return;
            e.getPlayer().setHealth(e.getPlayer().getHealth() > 19 ? 20 : e.getPlayer().getHealth() + 1.0);
            cooldown(20 * 60, e.getPlayer().getName(), justHealed);
        }
    }
}
