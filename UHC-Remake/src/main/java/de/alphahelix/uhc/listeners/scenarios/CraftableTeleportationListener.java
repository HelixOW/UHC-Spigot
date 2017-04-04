package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CraftableTeleportationListener extends SimpleListener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.isCancelled()) return;
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.CRAFTABLE_TELEPORTATION)) return;

        Player p = e.getPlayer();

        if (p.getInventory().getItemInHand().getType() != Material.ENDER_PEARL) return;
        if (!p.getInventory().getItemInHand().hasItemMeta()) return;
        if (!p.getInventory().getItemInHand().getItemMeta().hasDisplayName()) return;
        if (Bukkit.getPlayer(p.getInventory().getItemInHand().getItemMeta().getDisplayName()) == null) return;

        Player t = Bukkit.getPlayer(p.getInventory().getItemInHand().getItemMeta().getDisplayName());

        if (!PlayerUtil.isSurivor(t)) return;

        p.teleport(UHC.getRandomLocation(t.getLocation(), 25, 25, 25, 25));
    }
}
