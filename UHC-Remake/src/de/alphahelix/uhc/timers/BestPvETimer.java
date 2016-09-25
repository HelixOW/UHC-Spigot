package de.alphahelix.uhc.timers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;

public class BestPvETimer extends Util {

	private static BukkitTask best;

	public BestPvETimer(UHC uhc) {
		super(uhc);
	}

	public void stopTimer() {
		if (best != null)
			best.cancel();
		best = null;
	}

	public boolean isRunning() {
		if (best != null)
			return true;
		return false;
	}

	public void startBestTimer(final ArrayList<String> bestList) {
		if (best != null) {
			if (Bukkit.getScheduler().isCurrentlyRunning(best.getTaskId()))
				return;
			return;
		}

		best = new BukkitRunnable() {
			public void run() {
				for (String pName : bestList) {
					if (Bukkit.getPlayer(pName) == null)
						continue;
					if (Bukkit.getPlayer(pName).getHealth() == 20.0 || Bukkit.getPlayer(pName).getHealth() == 0.0)
						continue;
					Bukkit.getPlayer(pName).setHealth(Bukkit.getPlayer(pName).getHealth() + 1);
				}
			}
		}.runTaskTimer(getUhc(), 0, (20 * 60) * 10);
	}
}
