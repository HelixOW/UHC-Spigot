package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.event.EventHandler;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;

public class EntropyListener extends SimpleListener {

	public EntropyListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if(!scenarioCheck(Scenarios.ENTROPY)) return;
		
		getRegister().getEntropyTimer().startDrainingTimer();
	}
	
}
