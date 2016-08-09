package de.alpha.uhc.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;

public class RankTriggerListener implements Listener {

	private Core pl;
	private Registery r;

	public RankTriggerListener(Core plugin) {
		pl = plugin;
		r = pl.getRegistery();
	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("[UHC]")) {
			if (e.getLine(1).equalsIgnoreCase("[ranking]")) {
				if (e.getLine(2).equalsIgnoreCase("Place 1")) {
					r.getARanking().setFirstPlace(e.getBlock().getLocation().add(0, 1, 0));
					r.getRankingFile().addSign(e.getBlock().getLocation(), 1);
					r.getARanking().update();
				} else if (e.getLine(2).equalsIgnoreCase("Place 2")) {
					r.getARanking().setSecondPlace(e.getBlock().getLocation().add(0, 1, 0));
					r.getRankingFile().addSign(e.getBlock().getLocation(), 2);
					r.getARanking().update();
				} else if (e.getLine(2).equalsIgnoreCase("Place 3")) {
					r.getARanking().setThirdPlace(e.getBlock().getLocation().add(0, 1, 0));
					r.getRankingFile().addSign(e.getBlock().getLocation(), 3);
					r.getARanking().update();
				}
			}
		}

	}
}
