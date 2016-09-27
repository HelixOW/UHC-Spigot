package de.alphahelix.uhc.listeners.scenarios;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.instances.UHCTeam;
import de.popokaka.alphalibary.item.ItemBuilder;

public class TeamInventoryListener extends SimpleListener {

	private static HashMap<String, Inventory> tInvs = new HashMap<>();

	public TeamInventoryListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.TEAM_INVENTORY))
			return;

		for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			if (getRegister().getTeamManagerUtil().isInOneTeam(p) != null)
				p.getInventory()
						.addItem(new ItemBuilder(Material.ENDER_CHEST)
								.setName(getRegister().getTeamManagerUtil().isInOneTeam(p).getPrefix()
										+ getRegister().getTeamManagerUtil().isInOneTeam(p).getName())
								.build());
		}

		for (UHCTeam team : getRegister().getTeamManagerUtil().getTeams()) {
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
		if (!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ENDER_CHEST))
			return;

		if (getRegister().getTeamManagerUtil().isInOneTeam(e.getPlayer()) != null) {
			e.setCancelled(true);
			e.getPlayer()
					.openInventory(tInvs.get(getRegister().getTeamManagerUtil().isInOneTeam(e.getPlayer()).getName()));
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.TEAM_INVENTORY))
			return;
		if (getRegister().getTeamManagerUtil().isInOneTeam(e.getPlayer()) == null)
			return;
		if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()
				&& e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()
				&& e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.contains(getRegister().getTeamManagerUtil().isInOneTeam(e.getPlayer()).getName()))
			e.setBuild(true);;
	}
}
