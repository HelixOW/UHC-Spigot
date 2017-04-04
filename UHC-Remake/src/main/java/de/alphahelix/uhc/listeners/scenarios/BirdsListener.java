package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class BirdsListener extends SimpleListener {

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BIRDS)) return;

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.setAllowFlight(true);
        }
    }
}
