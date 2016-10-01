package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class UltraParanoidListener extends SimpleListener {

	public UltraParanoidListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.ULTRA_PARANOID))
			return;
		Player p = e.getPlayer();
		Block block = e.getBlock();

		if (block.getType() == Material.DIAMOND_ORE) {
			for (Player others : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
				others.sendMessage(getUhc().getPrefix() + p.getDisplayName() + "§8:" + location(p.getLocation()));
			}
		}

		if (block.getType() == Material.GOLD_ORE) {
			for (Player others : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
				others.sendMessage(getUhc().getPrefix() + p.getDisplayName() + "§8:" + location(p.getLocation()));
			}
		}
	}

	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.ULTRA_PARANOID))
			return;
		Player p = e.getPlayer();

		if (e.getItem().getType() == Material.GOLDEN_APPLE) {
			for (Player others : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
				others.sendMessage(getUhc().getPrefix() + p.getDisplayName() + "§8:" + location(p.getLocation()));
			}
		}
	}

	@EventHandler
	public void onPrepareItemCraftEvent(CraftItemEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.ULTRA_PARANOID))
			return;
		Player p = (Player) e.getWhoClicked();

		if (e.getRecipe().getResult().getType() == Material.GOLDEN_APPLE) {
			for (Player others : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
				others.sendMessage(getUhc().getPrefix() + p.getDisplayName() + "§8:" + location(p.getLocation()));
			}
		}

		if (e.getRecipe().getResult().getType() == Material.ANVIL) {
			for (Player others : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
				others.sendMessage(getUhc().getPrefix() + p.getDisplayName() + "§8:" + location(p.getLocation()));
			}
		}

		if (e.getRecipe().getResult().getType() == Material.ENCHANTMENT_TABLE) {
			for (Player others : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
				others.sendMessage(getUhc().getPrefix() + p.getDisplayName() + "§8:" + location(p.getLocation()));
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (!scenarioCheck(Scenarios.ULTRA_PARANOID))
			return;
		Player p = e.getEntity();

		for (Player others : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			others.sendMessage(getUhc().getPrefix() + p.getDisplayName() + "§8:" + location(p.getLocation()));
		}
	}

	/**
	 * Get the given location in string form.
	 * 
	 * @param loc
	 *            the given location.
	 * @return Location in String form.
	 */
	private String location(Location loc) {
		return "x:" + loc.getBlockX() + " y:" + loc.getBlockY() + " z:" + loc.getBlockZ();
	}
}
