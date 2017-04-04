package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class NoNetherListener extends SimpleListener {

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NO_GOING_BACK))
            return;

        if (e.getCause().equals(TeleportCause.NETHER_PORTAL))
            e.setCancelled(true);
    }
}
