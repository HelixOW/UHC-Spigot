package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.DeathMatchStartEvent;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class BlitzListener extends SimpleListener {

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BLITZ)) return;
        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.setHealth(2.5);
        }
    }

    @EventHandler
    public void onStart(DeathMatchStartEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BLITZ)) return;
        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.setHealth(20.0);
        }
    }
}
