package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.event.EventHandler;

public class FalloutListener extends SimpleListener {

    public FalloutListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.FALLOUT))
            return;

        UHCRegister.getDeathmatchTimer().setTime((45 * 60) + UHCFileRegister.getTimerFile().getLenght(GState.DEATHMATCH_WARMUP));
        UHCRegister.getFalloutTimer().startCooldown();
    }
}
