package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class SheepLoversListener extends SimpleListener {

	public SheepLoversListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onShear(PlayerShearEntityEvent e) {
		if(e.isCancelled()) return;
		if(!scenarioCheck(Scenarios.SHEEP_LOVERS)) return;
		
		if(Math.random() < 0.05)
			e.getPlayer().getWorld().dropItemNaturally(e.getEntity().getLocation(), new ItemStack(Material.GOLD_INGOT));
	}

}
