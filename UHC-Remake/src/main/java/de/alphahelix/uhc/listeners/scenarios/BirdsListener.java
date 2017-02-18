package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

/**
 * Created by AlphaHelixDev.
 */
public class BirdsListener extends SimpleListener {

    public BirdsListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.BIRDS)) return;

        for (Player p : makeArray(UHCRegister.getPlayerUtil().getSurvivors())) {
            p.setAllowFlight(true);
        }
    }
}
