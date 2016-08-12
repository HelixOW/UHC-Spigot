package de.alphahelix.uhc.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.SimpleListener;
import de.popokaka.alphalibary.item.ItemBuilder;

public class EquipListener extends SimpleListener {

	public EquipListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();

		if (getUhc().isScenarios()) {
			p.getInventory()
					.setItem(getRegister().getScenarioFile().getInt("Scenarios Item Slot"),
							new ItemBuilder(Material.getMaterial(
									getRegister().getScenarioFile().getString("Scenarios Item").replace(" ", "_")))
											.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
											.setName(getRegister().getKitsFile().getColorString("Scenarios Item Name"))
											.build());
		} else if (getUhc().isKits()) {
			p.getInventory()
					.setItem(getRegister().getKitsFile().getInt("Kit.Item Slot"),
							new ItemBuilder(Material
									.getMaterial(getRegister().getKitsFile().getString("Kit.Item").replace(" ", "_")))
											.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
											.setName(getRegister().getKitsFile().getColorString("Kit.Item Name"))
											.build());
		}

	}

}
