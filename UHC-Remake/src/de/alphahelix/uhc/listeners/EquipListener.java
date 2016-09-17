package de.alphahelix.uhc.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.popokaka.alphalibary.item.ItemBuilder;

public class EquipListener extends SimpleListener {

	public EquipListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		
		getRegister().getPlayerUtil().clearUp(p);
		getRegister().getPlayerUtil().addAll(p);
		
		if(!GState.isState(GState.LOBBY)) return;
		
		getRegister().getScoreboardUtil().setLobbyScoreboard(p);

		if (getUhc().isScenarios()) {
			p.getInventory()
					.setItem(getRegister().getScenarioFile().getInt("Scenarios Item Slot"),
							new ItemBuilder(Material.getMaterial(
									getRegister().getScenarioFile().getString("Scenarios Item").replace(" ", "_").toUpperCase()))
											.setName(getRegister().getScenarioFile().getColorString("Scenarios Item Name"))
											.build());
		} else if (getUhc().isKits()) {
			p.getInventory()
					.setItem(getRegister().getKitsFile().getInt("Kit.Item Slot"),
							new ItemBuilder(Material
									.getMaterial(getRegister().getKitsFile().getString("Kit.Item").replace(" ", "_").toUpperCase()))
											.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
											.setName(getRegister().getKitsFile().getColorString("Kit.Item Name"))
											.build());
		}
		
		if(getUhc().isTeams()) {
			p.getInventory().setItem(getRegister().getTeamFile().getInt("Team.Item Slot"),
					new ItemBuilder(Material.
							getMaterial(getRegister().getTeamFile().getString("Team.Item").replace(" ", "_").toUpperCase()))
											.setName(getRegister().getTeamFile().getColorString("Team.Item Name")).build());
		}

	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent e)  {
		Player p = e.getPlayer();
		
		getRegister().getPlayerUtil().removeDead(p);
	}
}
