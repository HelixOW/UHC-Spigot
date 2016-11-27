package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.event.EventHandler;

public class GoToHellListener extends SimpleListener {

	public GoToHellListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.GO_TO_HELL))
			return;

		getRegister().getDeathmatchTimer().setTime((45 * 60) + getRegister().getTimerFile().getInt("Deathmatch warmup.length"));
		getRegister().getGoToHellTimer().startCooldown();
	}

}
