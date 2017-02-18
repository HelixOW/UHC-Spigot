package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.register.UHCRegister;
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

    public TeamInventoryListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.TEAM_INVENTORY))
            return;

        for (Player p : makeArray(UHCRegister.getPlayerUtil().getSurvivors())) {
            if (UHCRegister.getTeamManagerUtil().isInOneTeam(p) != null)
                p.getInventory()
                        .addItem(new ItemBuilder(Material.ENDER_CHEST)
                                .setName(UHCRegister.getTeamManagerUtil().isInOneTeam(p).getPrefix()
                                        + UHCRegister.getTeamManagerUtil().isInOneTeam(p).getName())
                                .build());
        }

        for (UHCTeam team : UHCRegister.getTeamManagerUtil().getTeams()) {
            tInvs.put(team.getName(), Bukkit.createInventory(null, 54, team.getPrefix() + team.getName()));
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.TEAM_INVENTORY))
            return;
        if (!e.hasItem())
            return;
        if (!e.getAction().name().contains("RIGHT"))
            return;
        if (!e.getPlayer().getInventory().getItemInHand().getType().equals(Material.ENDER_CHEST))
            return;

        if (UHCRegister.getTeamManagerUtil().isInOneTeam(e.getPlayer()) != null) {
            e.setCancelled(true);
            e.getPlayer()
                    .openInventory(tInvs.get(UHCRegister.getTeamManagerUtil().isInOneTeam(e.getPlayer()).getName()));
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.TEAM_INVENTORY))
            return;
        if (UHCRegister.getTeamManagerUtil().isInOneTeam(e.getPlayer()) == null)
            return;
        if (e.getPlayer().getInventory().getItemInHand().hasItemMeta()
                && e.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName()
                && e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName()
                .contains(UHCRegister.getTeamManagerUtil().isInOneTeam(e.getPlayer()).getName()))
            e.setBuild(true);
    }
}
