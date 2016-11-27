package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class ChickenListener extends SimpleListener {

	public ChickenListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.CHICKEN))
			return;
		
		for(Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.setHealthScale(0.5);
			p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
		}
	}
}
