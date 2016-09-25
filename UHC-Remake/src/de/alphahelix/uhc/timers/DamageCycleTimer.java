package de.alphahelix.uhc.timers;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;

public class DamageCycleTimer extends Util {

	private static BukkitTask cycle;

	public DamageCycleTimer(UHC uhc) {
		super(uhc);
	}

	public void stopTimer() {
		if (cycle != null)
			cycle.cancel();
		cycle = null;
	}

	public boolean isRunning() {
		if (cycle != null)
			return true;
		return false;
	}

	public void startCycleTimer() {
		if (cycle != null) {
			if (Bukkit.getScheduler().isCurrentlyRunning(cycle.getTaskId()))
				return;
			return;
		}

		cycle = new BukkitRunnable() {
			public void run() {
				String pName = getRegister().getPlayerUtil().getSurvivors()
						.get(new Random().nextInt(getRegister().getPlayerUtil().getSurvivors().size()));
				if (Bukkit.getPlayer(pName) == null)
					return;

				Bukkit.getPlayer(pName).damage(1);
			}
		}.runTaskTimer(getUhc(), 0, (20 * 60) * 5);
	}

}
