package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.event.EventHandler;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;

public class DamageCycleListener extends SimpleListener {

	public DamageCycleListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if(!scenarioCheck(Scenarios.DAMAGE_CYCLE)) return;
		
		getRegister().getDamageCycleTimer().startCycleTimer();
	}
	
}
