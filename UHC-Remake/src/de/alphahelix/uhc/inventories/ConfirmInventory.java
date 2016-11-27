package de.alphahelix.uhc.inventories;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ConfirmInventory extends Util {

	private Inventory i;

	public ConfirmInventory(UHC uhc) {
		super(uhc);
		setInventory(Bukkit.createInventory(null, 27, getRegister().getConfirmFile().getColorString("Name")));
	}

	public void fillInventory() {
		for (int slot = 0; slot < getInv().getSize(); slot++) {
			getInv().setItem(slot,
					new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7).build());
		}

		String[] acceptMaterial = getRegister().getConfirmFile().getString("Accept.Material").replace(" ", "_").toUpperCase()
				.split(":");
		String[] denyMaterial = getRegister().getConfirmFile().getString("Denied.Material").replace(" ", "_").toUpperCase()
				.split(":");

		getInv().setItem(11,
				new ItemBuilder(Material.getMaterial(acceptMaterial[0])).setDamage(Short.parseShort(acceptMaterial[1]))
						.setName(getRegister().getConfirmFile().getColorString("Accept.name")).build());

		getInv().setItem(15,
				new ItemBuilder(Material.getMaterial(denyMaterial[0])).setDamage(Short.parseShort(denyMaterial[1]))
						.setName(getRegister().getConfirmFile().getColorString("Denied.name")).build());
	}
	
	public void openInventory(Player p) {
		p.closeInventory();
		p.openInventory(getInv());
	}

	private Inventory getInv() {
		return i;
	}

	private void setInventory(Inventory i) {
		this.i = i;
	}

}
