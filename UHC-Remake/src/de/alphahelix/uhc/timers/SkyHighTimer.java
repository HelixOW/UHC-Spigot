package de.alphahelix.uhc.timers;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;

public class SkyHighTimer extends Util {

	private static BukkitTask cooldown, damage;

	public SkyHighTimer(UHC uhc) {
		super(uhc);
	}

	public void stopTimer() {
		if (cooldown != null)
			cooldown.cancel();
		cooldown = null;
		if (damage != null)
			damage.cancel();
		damage = null;
	}

	public boolean isRunning() {
		if (damage != null)
			return true;
		return false;
	}

	public void startCooldown() {
		if (cooldown != null) {
			if (Bukkit.getScheduler().isCurrentlyRunning(cooldown.getTaskId()))
				return;
			return;
		}

		cooldown = new BukkitRunnable() {
			public void run() {
				startDamageTimer();
			}
		}.runTaskLaterAsynchronously(getUhc(), (20 * 60) * 45);
	}

	private void startDamageTimer() {
		if (damage != null) {
			if (Bukkit.getScheduler().isCurrentlyRunning(damage.getTaskId()))
				return;
			return;
		}
		damage = new BukkitRunnable() {
			public void run() {
				for (String pName : getRegister().getPlayerUtil().getSurvivors()) {
					if (Bukkit.getPlayer(pName) == null)
						continue;
					if (Bukkit.getPlayer(pName).getLocation().getBlockY() < 101)
						Bukkit.getPlayer(pName).damage(1.0);
				}
			}
		}.runTaskTimer(getUhc(), 0, (20 * 30));
	}

}
