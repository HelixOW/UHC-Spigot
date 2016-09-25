package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.popokaka.alphalibary.item.ItemBuilder;

public class GoneFishingListener extends SimpleListener {

	public GoneFishingListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.GONE_FISHIN))
			return;

		for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.setLevel(Integer.MAX_VALUE);
			p.getInventory().addItem(new ItemStack(Material.ANVIL, 20), new ItemBuilder(Material.FISHING_ROD)
					.addEnchantment(Enchantment.LURE, 250).addEnchantment(Enchantment.DURABILITY, 150).build());
		}
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if(e.isCancelled()) return;
		if(!scenarioCheck(Scenarios.GONE_FISHIN)) return;
		
		if(e.getRecipe().getResult().getType().equals(Material.ENCHANTMENT_TABLE)) e.setCancelled(true);
	}
	
	@EventHandler
	public void onInvOpen(InventoryOpenEvent e) {
		if(e.isCancelled()) return;
		if(!scenarioCheck(Scenarios.GONE_FISHIN)) return;
		
		if(e.getInventory().getType().equals(InventoryType.ENCHANTING)) e.setCancelled(true);
	}
}
