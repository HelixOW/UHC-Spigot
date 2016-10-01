package de.alphahelix.uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class ConfirmListener extends SimpleListener {

	public ConfirmListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		if (e.getClickedInventory() == null)
			return;
		if (e.getCurrentItem() == null)
			return;

		if (!e.getClickedInventory().getTitle().contains(getRegister().getConfirmFile().getColorString("Name")))
			return;

		e.setCancelled(true);

		if (e.getCurrentItem().hasItemMeta()) {

			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

				if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equals(getRegister().getConfirmFile().getColorString("Accept.name"))) {

					getRegister().getStatsUtil().removePoints((Player) e.getWhoClicked(), getRegister()
							.getKitChooseListener().getKitWhichPlayerWantToBuy((Player) e.getWhoClicked()).getPrice());

					getRegister().getStatsUtil().addKit(
							getRegister().getKitChooseListener().getKitWhichPlayerWantToBuy((Player) e.getWhoClicked()),
							(Player) e.getWhoClicked());

					String msg = getRegister().getMessageFile().getColorString("Kit chosen").replace("[kit]",
							getRegister().getKitChooseListener().getKitWhichPlayerWantToBuy((Player) e.getWhoClicked())
									.getName().replace("_", " "));

					getRegister().getKitsFile().setKit((Player) e.getWhoClicked(), getRegister().getKitChooseListener()
							.getKitWhichPlayerWantToBuy((Player) e.getWhoClicked()));
					
					e.getWhoClicked().closeInventory();
					
					getRegister().getScoreboardUtil().updateKit((Player) e.getWhoClicked(), getRegister()
							.getKitChooseListener().getKitWhichPlayerWantToBuy((Player) e.getWhoClicked()));
					e.getWhoClicked().sendMessage(getUhc().getPrefix() + msg);
				}

				else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equals(getRegister().getConfirmFile().getColorString("Denied.name"))) {
					e.getWhoClicked().closeInventory();
					getRegister().getKitInventory().openInventory((Player) e.getWhoClicked());
				}
			}
		}
	}
}
