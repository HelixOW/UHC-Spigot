package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class NoFurnaceListener extends SimpleListener {

	public NoFurnaceListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if(e.isCancelled()) return;
		if(!scenarioCheck(Scenarios.NO_FURNACE)) return;
		
		if(e.getRecipe().getResult().getType().equals(Material.FURNACE)) e.setCancelled(true);
	}

}
