package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class JackpotListener extends SimpleListener{

	public JackpotListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) return;
		if(!scenarioCheck(Scenarios.JACKPOT)) return;
		
		if(e.getBlock().getType().equals(Material.GOLD_ORE)) {
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
			
			for(Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
				p.getInventory().addItem(new ItemStack(Material.GOLD_ORE));
			}
		} else if(e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
			
			for(Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
				p.getInventory().addItem(new ItemStack(Material.DIAMOND));
			}
		}
	}
	
}
