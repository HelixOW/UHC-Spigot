package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class GoldenFleeceListener extends SimpleListener {

	public GoldenFleeceListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		if(e.isCancelled()) return;
		if(!scenarioCheck(Scenarios.GOLDEN_FLEECE)) return;
		
		if(e.getEntity() instanceof Skeleton) {
			if(Math.random() < 0.4) {
				Skeleton s = (Skeleton) e.getEntity();
				
				s.getEquipment().setHelmet(new ItemStack(Material.GOLD_HELMET));
				s.getEquipment().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
				s.getEquipment().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
				s.getEquipment().setBoots(new ItemStack(Material.GOLD_BOOTS));
			}
		}
	}
}
