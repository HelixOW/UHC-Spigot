package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;

public class PuppyPowerListener extends SimpleListener {

	public PuppyPowerListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.PUPPY_POWER))
			return;
		
		ItemStack spawnEgg = new SpawnEgg(EntityType.WOLF).toItemStack(12);

		for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.getInventory().addItem(new ItemStack(Material.BONE , 64), new ItemStack(Material.ROTTEN_FLESH, 64), spawnEgg);
		}
	}

}
