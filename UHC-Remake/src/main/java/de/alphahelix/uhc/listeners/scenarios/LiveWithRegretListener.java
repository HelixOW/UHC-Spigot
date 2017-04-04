package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LiveWithRegretListener extends SimpleListener {

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.LIVE_WITH_REGRET)) return;

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            if (StatsUtil.getDeaths(UUIDFetcher.getUUID(p)) > 0) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 99999, 1));
            }
        }
    }
}
