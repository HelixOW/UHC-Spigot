package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.event.EventHandler;

public class SkyHighListener extends SimpleListener {

    public SkyHighListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.SKY_HIGH))
            return;

        getRegister().getDeathmatchTimer().setTime((45 * 60) + getRegister().getTimerFile().getInt("Deathmatch warmup.length"));
        getRegister().getFalloutTimer().startCooldown();
    }
}
