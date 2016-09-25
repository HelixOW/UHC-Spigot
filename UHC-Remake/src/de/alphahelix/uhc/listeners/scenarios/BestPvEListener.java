package de.alphahelix.uhc.listeners.scenarios;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;

public class BestPvEListener extends SimpleListener {

	private ArrayList<String> best = new ArrayList<>();

	public BestPvEListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.BEST_PVE))
			return;
		for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			best.add(p.getName());
		}
		getRegister().getBestPvETimer().startBestTimer(best);
	}

	@EventHandler
	public void onHurt(EntityDamageEvent e) {
		if (e.isCancelled())
			return;
		if (!(e.getEntity() instanceof Player))
			return;
		if (!scenarioCheck(Scenarios.BEST_PVE))
			return;

		Player p = (Player) e.getEntity();

		if (best.contains(p.getName()))
			best.remove(p.getName());
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (!scenarioCheck(Scenarios.BEST_PVE))
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
