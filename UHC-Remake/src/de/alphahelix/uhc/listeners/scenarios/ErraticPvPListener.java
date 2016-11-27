package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.InGameStartEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class ErraticPvPListener extends SimpleListener {

	public ErraticPvPListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onStart(InGameStartEvent e) {
		if (!scenarioCheck(Scenarios.ERRATIC_PVP))
			return;
		
		int temp = 1;
		
		if(Math.random() < 0.5) {
			temp = 3;
		}
		final int delay = temp;

		new BukkitRunnable() {
			public void run() {
				new BukkitRunnable() {
					public void run() {
						getRegister().getLocationsFile().getArena().getWorld().setPVP(!getRegister().getLocationsFile().getArena().getWorld().getPVP());
					}
				}.runTaskTimer(getUhc(), 0, (20 * 60) * delay);
			}
		}.runTaskLater(getUhc(), (20 * 60) * 25);
	}

}
