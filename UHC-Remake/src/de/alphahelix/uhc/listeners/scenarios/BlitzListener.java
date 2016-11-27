package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.DeathMatchStartEvent;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class BlitzListener extends SimpleListener {

	public BlitzListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if(!scenarioCheck(Scenarios.BLITZ)) return;
		for(Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.setHealth(2.5);
		}
	}
	
	@EventHandler
	public void onStart(DeathMatchStartEvent e) {
		if(!scenarioCheck(Scenarios.BLITZ)) return;
		for(Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.setHealth(20.0);
		}
	}
}
