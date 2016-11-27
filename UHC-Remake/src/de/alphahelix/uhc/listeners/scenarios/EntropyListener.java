package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.event.EventHandler;

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
