package de.alphahelix.uhc.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class DeathListener extends SimpleListener {

	private List<ItemStack> dropList = new ArrayList<>();

	public DeathListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(final EntityDeathEvent e) {
		if (GState.isState(GState.LOBBY) || GState.isState(GState.END))
			return;

		double random = Math.random();

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
					Chest c = (Chest) e.getEntity().getLocation().getBlock().getState();

					if (random < getRegister().getDropsFile().getChance("Player", drops))
						c.getBlockInventory().addItem(drops);
				} else {
					if (random < getRegister().getDropsFile().getChance("Player", drops))
						dropList.add(drops);
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
								.getMessage(e.getEntity().getLastDamageCause() == null ? DamageCause.SUICIDE
										: e.getEntity().getLastDamageCause().getCause())
								.replace("[player]", ((Player) e.getEntity()).getName())
								.replace("[entity]", (e.getEntity().getKiller() == null
										? getRegister().getDeathMessageFile().getColorString("[entity] is a mob")
										: e.getEntity().getKiller().getName())));
			}
		} else if (e.getEntity() instanceof Pig) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Pig")) {
				if (random < getRegister().getDropsFile().getChance("Pig", drops))
					dropList.add(drops);
			}
		} else if (e.getEntity() instanceof Zombie) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Zombie")) {
				if (random < getRegister().getDropsFile().getChance("Zombie", drops))
					dropList.add(drops);
			}
		} else if (e.getEntity() instanceof Cow) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Cow")) {
				if (random < getRegister().getDropsFile().getChance("Cow", drops))
					dropList.add(drops);
			}
		} else if (e.getEntity() instanceof Chicken) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Chicken")) {
				if (random < getRegister().getDropsFile().getChance("Chicken", drops))
					dropList.add(drops);
			}
		} else if (e.getEntity() instanceof Spider) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Spider")) {
				if (random < getRegister().getDropsFile().getChance("Spider", drops))
					dropList.add(drops);
			}
		} else if (e.getEntity() instanceof Skeleton) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Skeleton")) {
				if (random < getRegister().getDropsFile().getChance("Skeleton", drops))
					dropList.add(drops);
			}
		} else if (e.getEntity() instanceof Sheep) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Sheep")) {
				if (random < getRegister().getDropsFile().getChance("Sheep", drops))
					dropList.add(drops);
			}
		} else if (e.getEntity() instanceof Rabbit) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Rabbit")) {
				if (random < getRegister().getDropsFile().getChance("Rabbit", drops))
					dropList.add(drops);
			}
		} else if (e.getEntity() instanceof Horse) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Horse")) {
				if (random < getRegister().getDropsFile().getChance("Horse", drops))
					dropList.add(drops);
			}
		} else if (e.getEntity() instanceof Creeper) {
			e.getDrops().clear();
			for (final ItemStack drops : getRegister().getDropsFile().readValues("Creeper")) {
				if (random < getRegister().getDropsFile().getChance("Creeper", drops))
					dropList.add(drops);
			}
		}

		// Bald Chicken

		if (e.getEntity() instanceof Chicken) {
			if (scenarioCheck(Scenarios.BALD_CHICKEN)) {
				ItemStack tr = null;
				for (ItemStack drop : dropList) {
					if (drop.getType().equals(Material.FEATHER))
						tr = drop;
				}
				if (tr != null)
					dropList.remove(tr);
			}
		} else if (e.getEntity() instanceof Skeleton) {
			if (scenarioCheck(Scenarios.BALD_CHICKEN)) {
				ItemStack tr = null;
				for (ItemStack drop : dropList) {
					if (drop.getType().equals(Material.ARROW))
						tr = drop;
				}
				if (tr != null)
					dropList.remove(tr);
				dropList.add(new ItemStack(Material.ARROW, new Random(4).nextInt(8)));
			}
		}

		// BareBones

		if (e.getEntity() instanceof Villager && e.getEntity().isCustomNameVisible()) {
			if (scenarioCheck(Scenarios.BAREBONES)) {
				dropList = new ArrayList<>();

				dropList.add(new ItemStack(Material.DIAMOND, 1));
				dropList.add(new ItemStack(Material.GOLDEN_APPLE, 1));
				dropList.add(new ItemStack(Material.ARROW, 32));
				dropList.add(new ItemStack(Material.STRING, 2));
			}
		}

		// Beta Zombie
		if (e.getEntity() instanceof Zombie)
			if (scenarioCheck(Scenarios.BETA_ZOMBIE))
				dropList.add(new ItemStack(Material.FEATHER));

		// Bombers
		if (scenarioCheck(Scenarios.BOMBERS))
			dropList.add(new ItemStack(Material.TNT));

		// Golden Fleece
		if (e.getEntity() instanceof Sheep) {
			if (scenarioCheck(Scenarios.GOLDEN_FLEECE)) {
				if (random < 0.6) {
					dropList.add(makeArray(new ItemStack(Material.IRON_INGOT), new ItemStack(Material.GOLD_INGOT),
							new ItemStack(Material.DIAMOND))[new Random().nextInt(
									makeArray(new ItemStack(Material.IRON_INGOT), new ItemStack(Material.GOLD_INGOT),
											new ItemStack(Material.DIAMOND)).length)]);
				}
			}
		}

		for (ItemStack is : dropList) {
			e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), is);
		}
	}
}
