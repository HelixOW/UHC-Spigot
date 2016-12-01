package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class EnderDragonRushListener extends SimpleListener {

    private boolean first = true;

    public EnderDragonRushListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.ENDERDRAGON_RUSH))
            return;
        if (!e.getCause().equals(TeleportCause.END_PORTAL))
            return;

        if (first) {
            e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
            first = false;
        }
    }

}
