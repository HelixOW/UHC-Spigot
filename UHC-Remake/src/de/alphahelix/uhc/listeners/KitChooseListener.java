package de.alphahelix.uhc.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.SimpleListener;

public class KitChooseListener extends SimpleListener {

	public KitChooseListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

		if (e.getPlayer().getInventory().getItemInMainHand() == null)
			return;
		if (!getUhc().isKits())
			return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType()
				.equals(Material.getMaterial(getRegister().getKitsFile().getString("Kit.Item").replace(" ", "_")))) {
			if (getUhc().isScenarios()) {
				e.getPlayer().sendMessage(
						getUhc().getPrefix() + getRegister().getMessageFile().getColorString("Warnings.Scenario Mode"));
				return;
			}
			getRegister().getKitInventory().fillInventory();
			getRegister().getKitInventory().openInventory(e.getPlayer());
		}
	}

}
