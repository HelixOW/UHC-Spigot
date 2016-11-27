package de.alphahelix.uhc.timers;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.Sounds;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.InGameStartEvent;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.nms.SimpleActionBar;
import de.popokaka.alphalibary.nms.SimpleTitle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class DeathmatchTimer extends Util {

	private static BukkitTask timer, deathmatch;
	private int time;
	private double min, h;
	private boolean hourSend, customTime;

	public DeathmatchTimer(UHC uhc) {
		super(uhc);
	}

	public void stopTimer() {
		if (timer != null)
			timer.cancel();
		timer = null;
		if (deathmatch != null)
			deathmatch.cancel();
		deathmatch = null;
		resetTime();
	}

	private double getMinTime() {
		return min;
	}

	private double getHourTime() {
		return calcHours(time);
	}

	public String getTime() {
		if (getHourTime() > 1 && getMinTime() > 60 && time > 60)
			return Double.toString(round(getHourTime(), 1)) + getRegister().getUnitFile().getColorString("Hours");
		else if (getHourTime() < 1 && getMinTime() < 60 && time > 60)
			return Double.toString(round(getMinTime(), 1)) + getRegister().getUnitFile().getColorString("Minutes");
		else
			return Integer.toString(time) + getRegister().getUnitFile().getColorString("Seconds");
	}

	public boolean isRunning() {
		return timer != null;
	}

	public void startDeathMatchTimer() {
		if (!GState.isState(GState.IN_GAME))
			return;

		if (timer != null) {
			if (Bukkit.getScheduler().isCurrentlyRunning(timer.getTaskId()))
				return;
			return;
		}
		if (!customTime)
			resetTime();
		
		Bukkit.getPluginManager().callEvent(new InGameStartEvent());

		timer = new BukkitRunnable() {
			public void run() {
				if (time > 0) {
					time--;

					deathmatch = new BukkitRunnable() {
						public void run() {
							for (String pName : getRegister().getPlayerUtil().getAll()) {
								Player p = Bukkit.getPlayer(pName);

								if (p == null)
									return;

								min = calcMin(time);
								h = calcHours(time);

								getRegister().getScoreboardUtil().updateTime(p);

								if ((h % 1 == 0 && !hourSend) && min > 60 && time != 0) {
									hourSend = true;
									p.sendMessage(getUhc().getPrefix() + getRegister().getMessageFile()
											.getColorString("Deathmatch time left info")
											.replace("[time]", Double.toString(round(h, 1))).replace("[unit]",
													(h >= 1 ? getRegister().getUnitFile().getColorString("Hours")
															: getRegister().getUnitFile().getColorString("Minutes"))));
									SimpleTitle.sendTitle(p, getUhc().getPrefix(), getRegister().getMessageFile()
											.getColorString("Deathmatch time left info")
											.replace("[time]", Double.toString(round(h, 1))).replace("[unit]",
													(h >= 1 ? getRegister().getUnitFile().getColorString("Hours")
															: getRegister().getUnitFile().getColorString("Minutes"))),
											1, 2, 1);
									p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
									continue;
								}

								if (min % 5 == 0 && time > 10 && time != 0) {
									p.sendMessage(getUhc().getPrefix() + getRegister().getMessageFile()
											.getColorString("Deathmatch time left info")
											.replace("[time]", Double.toString(round(min, 1))).replace("[unit]",
													(min >= 1 ? getRegister().getUnitFile().getColorString("Minutes")
															: getRegister().getUnitFile().getColorString("Seconds"))));
									SimpleTitle.sendTitle(p, getUhc().getPrefix(), getRegister().getMessageFile()
											.getColorString("Deathmatch time left info")
											.replace("[time]", Double.toString(round(min, 1))).replace("[unit]",
													(min >= 1 ? getRegister().getUnitFile().getColorString("Minutes")
															: getRegister().getUnitFile().getColorString("Seconds"))),
											1, 2, 1);
									p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
								}

								else if (time < 10 && time != 0) {
									p.sendMessage(getUhc().getPrefix() + getRegister().getMessageFile()
											.getColorString("Deathmatch time left info")
											.replace("[time]", Double.toString(time)).replace("[unit]", "seconds"));
									SimpleActionBar.send(p,
											getUhc().getPrefix() + getRegister().getMessageFile()
													.getColorString("Deathmatch time left info")
													.replace("[time]", Double.toString(time))
													.replace("[unit]", "seconds"));
									p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
								}

								else if (time == 0) {
									timer.cancel();

									p.sendMessage(getUhc().getPrefix()
											+ getRegister().getMessageFile().getColorString("Deathmatch ended"));

									SimpleActionBar.send(p, getUhc().getPrefix()
											+ getRegister().getMessageFile().getColorString("Deathmatch ended"));

									p.teleport(getRegister().getLocationsFile().getDeathmatch());

									GState.setCurrentState(GState.DEATHMATCH_WARMUP);

									getRegister().getStartDeathmatchTimer().startDeathMatchTimer();

									deathmatch.cancel();
								}
							}
						}
					}.runTask(getUhc());
				}
			}
		}.runTaskTimer(getUhc(), 0, 20);
	}

	private void resetTime() {
		time = getRegister().getTimerFile().getInt("Deathmatch warmup.length");
	}

	public void setTime(int t) {
		time = t;
		customTime = true;
	}
}
