package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class NineSlotsListener extends SimpleListener {

	public NineSlotsListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.NINE_SLOTS))
			return;
		if (e.getPlayer().getInventory().firstEmpty() <= 8)
			return;

		e.setCancelled(true);
	}

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.NINE_SLOTS))
			return;

		if (e.getWhoClicked().getInventory().firstEmpty() <= 8)
			return;

		e.setCancelled(true);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.NINE_SLOTS))
			return;
		if (e.getClickedInventory() == null)
			return;

		if (e.getSlot() > 8 && e.getSlot() < 36 && !e.isShiftClick())
			e.setCancelled(true);
		
	}
}
