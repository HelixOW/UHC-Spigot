package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class NoSprintListener extends SimpleListener {

	public NoSprintListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onPlayerToggleSprint(PlayerToggleSprintEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.NO_SPRINT))
			return;

		final Player p = e.getPlayer();

		if (e.isSprinting()) {
			final int foodlevel = p.getFoodLevel();
			p.setFoodLevel(5);

			new BukkitRunnable() {
				public void run() {
					p.setFoodLevel(foodlevel);
				}
			}.runTaskLater(getUhc(), 20 * 2);
		}
	}

}
