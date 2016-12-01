package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LiveWithRegretListener extends SimpleListener {

    public LiveWithRegretListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.LIVE_WITH_REGRET)) return;

        for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
            if (getRegister().getStatsUtil().getDeaths(p) > 0) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 99999, 1));
            }
        }
    }
}
