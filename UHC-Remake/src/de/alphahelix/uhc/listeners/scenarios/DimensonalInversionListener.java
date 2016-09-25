package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;

public class DimensonalInversionListener extends SimpleListener {

	public DimensonalInversionListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if(!scenarioCheck(Scenarios.DIMENSIONAL_INVERSION)) return;
		for(Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.teleport(getRegister().getLocationsFile().getNetherArena());
		}
	}
}
