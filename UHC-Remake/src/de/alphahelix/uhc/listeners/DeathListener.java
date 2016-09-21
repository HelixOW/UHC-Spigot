package de.alphahelix.uhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class DeathListener extends SimpleListener {

	public DeathListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onDeath(final EntityDeathEvent e) {
		if (GState.isState(GState.LOBBY) || GState.isState(GState.END))
			return;

		if (e.getEntity() instanceof Villager && e.getEntity().isCustomNameVisible()) {
			e.getDrops().clear();
			getRegister().getPlayerUtil().removeSurvivor(e.getEntity().getCustomName());
			getRegister().getPlayerUtil().addDead(e.getEntity().getCustomName());

			for (String other : getRegister().getPlayerUtil().getAll()) {
				if (Bukkit.getPlayer(other) == null)
					return;
				Bukkit.getPlayer(other)
						.sendMessage(getRegister().getDeathMessageFile()
								.getMessage(e.getEntity().getLastDamageCause().getCause())
								.replace("[player]", e.getEntity().getCustomName())
								.replace("[entity]", (e.getEntity().getKiller() == null
										? getRegister().getDeathMessageFile().getColorString("[entity] is a mob")
										: e.getEntity().getKiller().getName())));
			}

			for (final ItemStack drops : getRegister().getDropsFile().readValues("Player")) {
				if (getRegister().getDropsFile().getBoolean("Deathchest")) {
					e.getEntity().getLocation().getBlock().setType(Material.CHEST);
					final Chest c = (Chest) e.getEntity().getLocation().getBlock().getState();

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
								e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
							}
						}.runTaskLater(getUhc(), 10);
					} else {
						e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
					}
				}
			}

			getRegister().getStatsUtil()
					.addDeath(Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName())));
			getRegister().getStatsUtil().removePoints(
					Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName())),
					getRegister().getMainOptionsFile().getInt("Points - on kill"));
			getRegister().getStatsUtil().addCoins(
					Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName())),
					getRegister().getMainOptionsFile().getInt("Coins - on kill"));

			if (!getRegister().getMainOptionsFile().getString("Command on kill").equals(""))
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getRegister().getMainOptionsFile()
						.getString("Command on kill").replace("[player]", e.getEntity().getCustomName())
						.replace("[killer]",
								(e.getEntity().getKiller() == null ? "" : e.getEntity().getKiller().getName())));
			if (!getRegister().getMainOptionsFile().getString("Command on death").equals(""))
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getRegister().getMainOptionsFile()
						.getString("Command on death").replace("[player]", e.getEntity().getCustomName())
						.replace("[killer]",
								(e.getEntity().getKiller() == null ? "" : e.getEntity().getKiller().getName())));

			if (getRegister().getPlayerUtil().getSurvivors().size() <= 1) {

				GState.setCurrentState(GState.END);

				if (getRegister().getPlayerUtil().getSurvivors().size() == 0) {

					getRegister().getRestartTimer().startEndTimer();
					return;
				}

				getRegister().getGameEndsListener().setWinnerName(getRegister().getPlayerUtil().getSurvivors().get(0));
				for (String pName : getRegister().getPlayerUtil().getAll()) {
					Bukkit.getPlayer(pName)
							.sendMessage(getUhc().getPrefix()
									+ getRegister().getMessageFile().getColorString("Winning message")
											.replace("[player]", getRegister().getGameEndsListener().getWinnerName()));
					SimpleTitle.sendTitle(Bukkit.getPlayer(pName), " ",
							getUhc().getPrefix() + getRegister().getMessageFile().getColorString("Winning message")
									.replace("[player]", getRegister().getGameEndsListener().getWinnerName()),
							2, 2, 2);
				}

				getRegister().getStatsUtil().addPoints(
						Bukkit.getPlayer(getRegister().getGameEndsListener().getWinnerName()),
						getRegister().getMainOptionsFile().getInt("Points + on win"));

				if (!getRegister().getMainOptionsFile().getString("Command on win").equals(""))
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							getRegister().getMainOptionsFile().getString("Command on win").replace("[player]",
									getRegister().getGameEndsListener().getWinnerName()));

				getRegister().getRestartTimer().startEndTimer();
			}

		} else if (e.getEntity() instanceof Player) {
			for (String other : getRegister().getPlayerUtil().getAll()) {
				if (Bukkit.getPlayer(other) == null)
					return;
				Bukkit.getPlayer(other)
						.sendMessage(getRegister().getDeathMessageFile()
								.getMessage(e.getEntity().getLastDamageCause().getCause())
								.replace("[player]", ((Player) e.getEntity()).getName())
								.replace("[entity]", (e.getEntity().getKiller() == null
										? getRegister().getDeathMessageFile().getColorString("[entity] is a mob")
										: e.getEntity().getKiller().getName())));
			}
		} else if (e.getEntity() instanceof Pig) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Pig")) {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
				}
			}
		}  else if (e.getEntity() instanceof Zombie) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Zombie")) {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
				}
			}
		}  else if (e.getEntity() instanceof Cow) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Cow")) {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
				}
			}
		}  else if (e.getEntity() instanceof Chicken) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Chicken")) {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
				}
			}
		}  else if (e.getEntity() instanceof Spider) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Spider")) {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
				}
			}
		}  else if (e.getEntity() instanceof Skeleton) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Skeleton")) {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
				}
			}
		}  else if (e.getEntity() instanceof Sheep) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Sheep")) {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
				}
			}
		}  else if (e.getEntity() instanceof Rabbit) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Rabbit")) {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
				}
			}
		}  else if (e.getEntity() instanceof Horse) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Horse")) {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
				}
			}
		}  else if (e.getEntity() instanceof Creeper) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Creeper")) {
				if (drops.getType().equals(Material.SKULL_ITEM) && drops.getDurability() == 3) {

					new BukkitRunnable() {
						public void run() {
							e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
						}
					}.runTaskLater(getUhc(), 10);
				} else {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drops);
				}
			}
		}
	}

}
