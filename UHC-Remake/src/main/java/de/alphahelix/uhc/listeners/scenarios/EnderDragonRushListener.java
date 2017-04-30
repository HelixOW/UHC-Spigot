package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class EnderDragonRushListener extends SimpleListener {

    private boolean first = true;

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.ENDERDRAGON_RUSH))
            return;
        if (!e.getCause().equals(TeleportCause.END_PORTAL))
            return;

        if (first) {
            e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
            first = false;
        }
    }

}
