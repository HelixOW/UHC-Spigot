package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.ArrayList;

public class NoGoingBackListener extends SimpleListener {

    ArrayList<String> went = new ArrayList<>();

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NO_GOING_BACK))
            return;

        if (e.getCause().equals(TeleportCause.NETHER_PORTAL)) {
            if (went.contains(e.getPlayer().getName()))
                e.setCancelled(true);
            else
                went.add(e.getPlayer().getName());
        } else if (e.getCause().equals(TeleportCause.END_PORTAL)) {
            if (went.contains(e.getPlayer().getName()))
                e.setCancelled(true);
            else
                went.add(e.getPlayer().getName());
        }
    }
}
