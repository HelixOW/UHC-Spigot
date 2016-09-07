package de.alphahelix.uhc.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.Kit;
import de.alphahelix.uhc.util.SimpleListener;

public class KitChooseListener extends SimpleListener {

	public KitChooseListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

		if (e.getPlayer().getInventory().getItemInMainHand() == null)
			return;
		if (!getUhc().isKits()) {
			e.getPlayer().sendMessage(
					getUhc().getPrefix() + getRegister().getMessageFile().getColorString("Warnings.Scenario Mode"));
			return;
		}
		if (e.getPlayer().getInventory().getItemInMainHand().getType()
				.equals(Material.getMaterial(getRegister().getKitsFile().getString("Kit.Item").replace(" ", "_")))) {
			getRegister().getKitInventory().fillInventory();
			getRegister().getKitInventory().openInventory(e.getPlayer());
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		if (e.getClickedInventory() == null)
			return;
		if (e.getCurrentItem() == null)
			return;

		for (Kit k : getRegister().getKitsFile().getKits()) {
			if (e.getCurrentItem().getType().equals(k.getGuiBlock().getType())) {
				
				String msg = getRegister().getMessageFile().getColorString("Kit chosen").replace("[kit]", k.getName());
				
				getRegister().getKitsFile().setKit((Player) e.getWhoClicked(), k);
				e.getWhoClicked().closeInventory();
				e.getWhoClicked().sendMessage(getUhc().getPrefix() + msg);
			}
		}
	}
}
