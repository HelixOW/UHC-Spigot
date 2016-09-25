package de.alphahelix.uhc.timers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;

public class EntropyTimer extends Util{
	
	private static BukkitTask draining;

	public EntropyTimer(UHC uhc) {
		super(uhc);
	}

	public void stopTimer() {
		if (draining != null)
			draining.cancel();
		draining = null;
	}

	public boolean isRunning() {
		if (draining != null)
			return true;
		return false;
	}

	public void startDrainingTimer() {
		if (draining != null) {
			if (Bukkit.getScheduler().isCurrentlyRunning(draining.getTaskId()))
				return;
			return;
		}

		draining = new BukkitRunnable() {
			public void run() {
				for(String pName : getRegister().getPlayerUtil().getSurvivors()) {
					if(Bukkit.getPlayer(pName) == null) continue;
					Player p = Bukkit.getPlayer(pName);
					
					if(p.getLevel() == 0) {
						p.setHealth(20.0);
					} else {
						p.setLevel(p.getLevel() - 1);
					}
				}
			}
		}.runTaskTimer(getUhc(), 0, (20 * 60) * 10);
	}
}
