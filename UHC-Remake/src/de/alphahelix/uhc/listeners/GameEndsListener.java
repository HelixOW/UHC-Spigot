package de.alphahelix.uhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.instances.Spectator;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class GameEndsListener extends SimpleListener {

	public GameEndsListener(UHC uhc) {
		super(uhc);
	}

	private String winnerName = "AlphaHelix";

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		final Player dead = e.getEntity();

		getRegister().getPlayerUtil().removeSurvivor(dead);
		getRegister().getPlayerUtil().addDead(dead);

		new Spectator(dead);

		dead.getWorld().strikeLightning(dead.getLocation());

		getRegister().getTablistUtil().sendTablist();

		if (dead.getKiller() != null) {
			getRegister().getStatsUtil().addKill(dead.getKiller());
			getRegister().getStatsUtil().addPoints(dead.getKiller(),
					getRegister().getMainOptionsFile().getInt("Points + on kill"));
			getRegister().getStatsUtil().addCoins(dead.getKiller(),
					getRegister().getMainOptionsFile().getInt("Coins + on kill"));
		}

		getRegister().getStatsUtil().addDeath(dead);
		getRegister().getStatsUtil().removePoints(dead, getRegister().getMainOptionsFile().getInt("Points - on kill"));
		getRegister().getStatsUtil().addCoins(dead, getRegister().getMainOptionsFile().getInt("Coins - on kill"));

		if (!getRegister().getMainOptionsFile().getString("Command on kill").equals(""))
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					getRegister().getMainOptionsFile().getString("Command on kill").replace("[player]", dead.getName())
							.replace("[killer]", (dead.getKiller() == null ? "" : dead.getKiller().getName())));
		if (!getRegister().getMainOptionsFile().getString("Command on death").equals(""))
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					getRegister().getMainOptionsFile().getString("Command on death").replace("[player]", dead.getName())
							.replace("[killer]", (dead.getKiller() == null ? "" : dead.getKiller().getName())));

		for (String other : getRegister().getPlayerUtil().getAll()) {
			if (Bukkit.getPlayer(other) == null)
				continue;
			getRegister().getScoreboardUtil().updateAlive(Bukkit.getPlayer(other));
			getRegister().getScoreboardUtil().updateSpecs(Bukkit.getPlayer(other));
		}

		e.getDrops().clear();

		for (final ItemStack drops : getRegister().getDropsFile().readValues("Player")) {
			if (getRegister().getDropsFile().getBoolean("Deathchest")) {
				dead.getLocation().getBlock().setType(Material.CHEST);
				final Chest c = (Chest) dead.getLocation().getBlock().getState();

				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							c.getBlockInventory().addItem(drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					c.getBlockInventory().addItem(drops);
				}
			} else {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							dead.getWorld().dropItemNaturally(dead.getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					dead.getWorld().dropItemNaturally(dead.getLocation(), drops);
				}
			}
		}

		if (getRegister().getPlayerUtil().getSurvivors().size() == 4) {
			getRegister().getDeathmatchTimer().startDeathMatchTimer();
		}

		if (getRegister().getPlayerUtil().getSurvivors().size() <= 1) {

			GState.setCurrentState(GState.END);

			if (getRegister().getPlayerUtil().getSurvivors().size() == 0) {

				getRegister().getRestartTimer().startEndTimer();
				return;
			}

			setWinnerName(getRegister().getPlayerUtil().getSurvivors().get(0));
			for (String pName : getRegister().getPlayerUtil().getAll()) {
				Bukkit.getPlayer(pName).sendMessage(
						getUhc().getPrefix() + getRegister().getMessageFile().getColorString("Winning message"));
				SimpleTitle.sendTitle(Bukkit.getPlayer(pName), " ",
						getUhc().getPrefix() + getRegister().getMessageFile().getColorString("Winning message"), 2, 2,
						2);
			}

			getRegister().getStatsUtil().addPoints(Bukkit.getPlayer(getWinnerName()),
					getRegister().getMainOptionsFile().getInt("Points + on win"));

			if (!getRegister().getMainOptionsFile().getString("Command on win").equals(""))
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getRegister().getMainOptionsFile()
						.getString("Command on win").replace("[player]", getWinnerName()));
			
			getRegister().getRestartTimer().startEndTimer();
		}
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}
}
