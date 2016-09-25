package de.alphahelix.uhc.listeners.scenarios;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;

public class CertainCircumstancesListener extends SimpleListener {

	public CertainCircumstancesListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if(!scenarioCheck(Scenarios.CERTAIN_CIRCUMSTANCES)) return;
		for(Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.getInventory().addItem(new ItemStack(getRandomMaterial()));
		}
	}
	
	private Material getRandomMaterial() {
		int index = new Random().nextInt(Material.values().length);
		return Material.values()[index];
	}
}
