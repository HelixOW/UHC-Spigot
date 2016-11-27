package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class NoNetherListener extends SimpleListener {

	public NoNetherListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onPortal(PlayerPortalEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.NO_GOING_BACK))
			return;

		if (e.getCause().equals(TeleportCause.NETHER_PORTAL))
			e.setCancelled(true);
	}
}
