package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.event.EventHandler;

public class DamageCycleListener extends SimpleListener {

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.DAMAGE_CYCLE)) return;

        UHCRegister.getDeathmatchTimer().setSeconds((45 * 60) + UHCFileRegister.getTimerFile().getLenght(GState.DEATHMATCH_WARMUP));
        UHCRegister.getDamageCycleTimer().startCycleTimer();
    }

}
