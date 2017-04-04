package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;

public class BestPvEListener extends SimpleListener {

    private ArrayList<String> best = new ArrayList<>();

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BEST_PVE))
            return;
        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            best.add(p.getName());
        }
        UHCRegister.getBestPveTimer().startBestTimer(best);
    }

    @EventHandler
    public void onHurt(EntityDamageEvent e) {
        if (e.isCancelled())
            return;
        if (!(e.getEntity() instanceof Player))
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BEST_PVE))
            return;

        Player p = (Player) e.getEntity();

        if (best.contains(p.getName()))
            best.remove(p.getName());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BEST_PVE))
            return;
        if (e.getEntity().getKiller() == null)
            return;
        if (!(e.getEntity().getKiller() instanceof Player))
            return;
        Player k = e.getEntity().getKiller();

        if (!best.contains(k.getName()))
            best.add(k.getName());
    }
}
