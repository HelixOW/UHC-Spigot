package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.item.ItemBuilder;
import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.TeamManagerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class TeamInventoryListener extends SimpleListener {

    private static HashMap<String, Inventory> tInvs = new HashMap<>();

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.TEAM_INVENTORY))
            return;

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            if (TeamManagerUtil.isInOneTeam(p) != null)
                p.getInventory()
                        .addItem(new ItemBuilder(Material.ENDER_CHEST)
                                .setName(TeamManagerUtil.isInOneTeam(p).getPrefix()
                                        + TeamManagerUtil.isInOneTeam(p).getName())
                                .build());
        }

        for (UHCTeam team : TeamManagerUtil.getTeams()) {
            tInvs.put(team.getName(), Bukkit.createInventory(null, 54, team.getPrefix() + team.getName()));
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.TEAM_INVENTORY))
            return;
        if (!e.hasItem())
            return;
        if (!e.getAction().name().contains("RIGHT"))
            return;
        if (!e.getPlayer().getInventory().getItemInHand().getType().equals(Material.ENDER_CHEST))
            return;

        if (TeamManagerUtil.isInOneTeam(e.getPlayer()) != null) {
            e.setCancelled(true);
            e.getPlayer()
                    .openInventory(tInvs.get(TeamManagerUtil.isInOneTeam(e.getPlayer()).getName()));
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.TEAM_INVENTORY))
            return;
        if (TeamManagerUtil.isInOneTeam(e.getPlayer()) == null)
            return;
        if (e.getPlayer().getInventory().getItemInHand().hasItemMeta()
                && e.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName()
                && e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName()
                .contains(TeamManagerUtil.isInOneTeam(e.getPlayer()).getName()))
            e.setBuild(true);
    }
}
