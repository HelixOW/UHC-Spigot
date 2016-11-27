package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.popokaka.alphalibary.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class BackPackListener extends SimpleListener {

	// Player -> BackPack
	private HashMap<String, Inventory> backpacks = new HashMap<>();

	public BackPackListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.BACKPACKS))
			return;
		if (!e.getAction().name().contains("RIGHT"))
			return;

		Player p = e.getPlayer();

		if (p.getInventory().getItemInMainHand() == null)
			return;
		if (!(p.getInventory().getItemInMainHand().hasItemMeta()
				|| p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()))
			return;
		
		e.setCancelled(true);

		if (!p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(p.getName())) {
			p.openInventory(backpacks.get(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName()));
		} else {
			if (!backpacks.containsKey(p.getName())) {
				backpacks.put(p.getName() , Bukkit.createInventory(null, 27,
						getRegister().getScenarioFile().getCustomScenarioName(Scenarios.BACKPACKS)));
				p.openInventory(backpacks.get(p.getName()));
			} else {
				p.openInventory(backpacks.get(p.getName()));
			}
		}
		
		p.updateInventory();
	}

	@EventHandler
	public void onStart(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.BACKPACKS))
			return;

		for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.getInventory().addItem(new ItemBuilder(Material.TRAPPED_CHEST).setName(p.getName()).build());
		}
	}
}
