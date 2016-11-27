package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class BowfighterListener extends SimpleListener {

	public BowfighterListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.BOWFIGHTERS))
			return;
		ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
		
		meta.addStoredEnchant(Enchantment.ARROW_INFINITE, 1, true);
		
		book.setItemMeta(meta);
		
		for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.getInventory().addItem(new ItemStack(Material.ARROW), new ItemStack(Material.STRING, 2),
					book);
		}
	}

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.BOWFIGHTERS))
			return;

		if (e.getRecipe().getResult().getType().name().contains("SWORD")
				&& !e.getRecipe().getResult().getType().equals(Material.WOOD_SWORD))
			e.setCancelled(true);
		else if (e.getRecipe().getResult().getType().name().contains("AXE")
				&& !(e.getRecipe().getResult().getType().equals(Material.STONE_AXE)
						|| e.getRecipe().getResult().getType().equals(Material.WOOD_AXE)))
			e.setCancelled(true);
	}

}
