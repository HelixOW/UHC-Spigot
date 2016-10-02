package de.alphahelix.uhc.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.UHCCrate;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.mysql.MySQLAPI;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class StatsUtil extends Util {

	public StatsUtil(UHC uhc) {
		super(uhc);
	}

	// Kills

	public int getKills(final OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Kills") == null)
				return getRegister().getPlayerFile()
						.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
			String kills = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kills").toString();
			return Integer.parseInt(kills);
		}
		return getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
	}

	public void addKill(final OfflinePlayer p) {
		new BukkitRunnable() {
			public void run() {
				if (getUhc().isMySQLMode()) {
					if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
							"Kills") == null) {
						int kills = getRegister().getPlayerFile()
								.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
						getRegister().getPlayerFile()
								.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills", kills + 1);
						getRegister().getPlayerFile().save();
					}
					MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kills",
							Integer.toString(getKills(p) + 1));
					return;
				}
				int kills = getRegister().getPlayerFile()
						.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills",
						kills + 1);
				getRegister().getPlayerFile().save();
			}
		}.runTaskLaterAsynchronously(getUhc(), 10);
	}

	// Deaths

	public int getDeaths(final OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Deaths") == null)
				return getRegister().getPlayerFile()
						.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
			String deaths = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Deaths").toString();
			return Integer.parseInt(deaths);
		}
		return getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
	}

	public void addDeath(final OfflinePlayer p) {
		new BukkitRunnable() {
			public void run() {
				if (getUhc().isMySQLMode()) {
					if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
							"Deaths") == null) {
						int deaths = getRegister().getPlayerFile()
								.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
						getRegister().getPlayerFile()
								.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths", deaths + 1);
						getRegister().getPlayerFile().save();
					}
					MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Deaths",
							Integer.toString(getDeaths(p) + 1));
					return;
				}
				int deaths = getRegister().getPlayerFile()
						.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths",
						deaths + 1);
				getRegister().getPlayerFile().save();
			}
		}.runTaskLaterAsynchronously(getUhc(), 10);
	}

	// Coins

	public int getCoins(final OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Coins") == null)
				return getRegister().getPlayerFile()
						.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
			String coins = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Coins").toString();
			return Integer.parseInt(coins);
		}
		return getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
	}

	public void addCoins(final OfflinePlayer p, final int amount) {
		new BukkitRunnable() {
			public void run() {
				if (getUhc().isMySQLMode()) {
					if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
							"Coins") == null) {
						int coins = getRegister().getPlayerFile()
								.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
						getRegister().getPlayerFile()
								.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", coins + 1);
						getRegister().getPlayerFile().save();
					}
					MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins",
							Integer.toString(getCoins(p) + amount));
					return;
				}
				int coins = getRegister().getPlayerFile()
						.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins",
						coins + 1);
				getRegister().getPlayerFile().save();
			}
		}.runTaskLaterAsynchronously(getUhc(), 10);
	}

	public void removeCoins(final OfflinePlayer p, final int amount) {
		new BukkitRunnable() {
			public void run() {
				if (getUhc().isMySQLMode()) {
					if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
							"Coins") == null) {
						int coins = getCoins(p);
						if (coins - amount < 0)
							getRegister().getPlayerFile()
									.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", 0);
						else
							getRegister().getPlayerFile().set(
									"Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins",
									coins - amount);

						getRegister().getPlayerFile().save();
					}
					if (getCoins(p) - amount < 0)
						MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins",
								Integer.toString(0));
					else
						MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins",
								Integer.toString(getCoins(p) - amount));
					return;
				}
				int coins = getCoins(p);
				if (coins - amount < 0)
					getRegister().getPlayerFile()
							.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", 0);
				else
					getRegister().getPlayerFile()
							.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", coins - amount);

				getRegister().getPlayerFile().save();
			}
		}.runTaskLaterAsynchronously(getUhc(), 10);
	}

	// Kits

	public String getKitsAsString(OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Kits") == null) {
				String kit = getRegister().getPlayerFile()
						.getString("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits");
				return kit.replace("§", "&");
			}
			String kit = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kits").toString();
			return kit.replace("§", "&");
		}
		String kit = getRegister().getPlayerFile()
				.getString("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits");
		return kit.replace("§", "&");
	}

	public ArrayList<String> getKitsAsList(OfflinePlayer p) {
		ArrayList<String> playerKits = new ArrayList<>();
		String kit = getKitsAsString(p);
		
		if (kit == null)
			return playerKits;
		if (kit.equals(""))
			return playerKits;

		String[] kits = kit.split(" ,");

		for (String kitName : kits) {
			playerKits.add(kitName.replace("§", "&"));
		}
		return playerKits;
	}

	public void addKit(final Kit kit, final OfflinePlayer p) {
		new BukkitRunnable() {
			public void run() {
				if (getUhc().isMySQLMode()) {
					if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
							"Kits") == null) {
						getRegister().getPlayerFile().set(
								"Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits",
								getKitsAsString(p) + kit.getName().replace("§", "&") + " ,");
						getRegister().getPlayerFile().save();
					}
					MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kits",
							getKitsAsString(p) + kit.getName().replace("§", "&") + " ,");
					return;
				}
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits",
						getKitsAsString(p) + kit.getName().replace("§", "&") + " ,");
				getRegister().getPlayerFile().save();
			}
		}.runTaskLaterAsynchronously(getUhc(), 10);
	}

	public boolean hasKit(Kit k, OfflinePlayer p) {
		if (getKitsAsList(p).contains(" "+k.getName().replace("§", "&")) 
				|| getKitsAsList(p).contains(" "+k.getName().replace("§", "&")+",")
				|| getKitsAsList(p).contains(","+k.getName().replace("§", "&"))
				|| getKitsAsList(p).contains(","+k.getName().replace("§", "&")+" ")
				|| getKitsAsList(p).contains(k.getName().replace("§", "&")+",")
				|| getKitsAsList(p).contains(k.getName().replace("§", "&")))
			return true;
		return false;
	}

	// Crates
	
	public String[] getCratesAsArray(OfflinePlayer p) {
		ArrayList<String> tT = new ArrayList<>();
		
		for (UHCCrate c : getRegister().getUhcCrateFile().getCrates()) {
			if(getCrateCount(c, p) <= 0) continue;
			tT.add(c.getCrateRarerity().name()+":"+getCrateCount(c, p));
		}
		
		return tT.toArray(new String[tT.size()]);
	}

	public HashMap<UHCCrate, Integer> getCrates(OfflinePlayer p) {
		HashMap<UHCCrate, Integer> crates = new HashMap<>();

		for (UHCCrate c : getRegister().getUhcCrateFile().getCrates()) {
			crates.put(c, getCrateCount(c, p));
		}

		return crates;
	}

	public int getCrateCount(UHCCrate crate, OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					crate.getCrateRarerity().name() + "Crates") == null) {

				return getRegister().getPlayerFile().getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString()
						+ ".crates." + crate.getCrateRarerity().name().toLowerCase());
			}

			String crateCount = MySQLManager.getObjectConditionResult("UUID",
					UUIDFetcher.getUUID(p.getName()).toString(), crate.getCrateRarerity().name() + "Crates").toString();
			return Integer.parseInt(crateCount);
		}
		return getRegister().getPlayerFile().getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString()
				+ ".crates." + crate.getCrateRarerity().name().toLowerCase());
	}

	public void addCrate(final UHCCrate crate, final OfflinePlayer p) {
		if (p.isOnline())
			((Player) p).sendMessage(getUhc().getPrefix() + getRegister().getMessageFile().getColorString("Crate dropped")
							.replace("[crate]", crate.getCrateRarerity().getPrefix() + crate.getName()));
		new BukkitRunnable() {
			public void run() {
				if (getUhc().isMySQLMode()) {
					if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
							crate.getCrateRarerity().name() + "Crates") == null) {

						getRegister()
								.getPlayerFile().set(
										"Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates."
												+ crate.getCrateRarerity().name().toLowerCase(),
										getCrateCount(crate, p) + 1);
						getRegister().getPlayerFile().save();
					}
					MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(),
							crate.getCrateRarerity().name() + "Crates", Integer.toString(getCrateCount(crate, p) + 1));
					return;
				}
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates."
						+ crate.getCrateRarerity().name().toLowerCase(), getCrateCount(crate, p) + 1);
				getRegister().getPlayerFile().save();
			}
		}.runTaskLaterAsynchronously(getUhc(), 10);
	}

	public void removeCrate(final UHCCrate crate, final OfflinePlayer p) {
		new BukkitRunnable() {
			public void run() {
				if (getUhc().isMySQLMode()) {
					if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
							crate.getCrateRarerity().name() + "Crates") == null) {

						if (hasCrate(crate, p))
							getRegister().getPlayerFile()
									.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates."
											+ crate.getCrateRarerity().name().toLowerCase(),
											getCrateCount(crate, p) - 1);
						else
							getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString()
									+ ".crates." + crate.getCrateRarerity().name().toLowerCase(), 0);
						getRegister().getPlayerFile().save();
					}

					if (hasCrate(crate, p))
						MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(),
								crate.getCrateRarerity().name() + "Crates",
								Integer.toString(getCrateCount(crate, p) - 1));
					else
						MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(),
								crate.getCrateRarerity().name() + "Crates", Integer.toString(0));
					return;
				}

				if (hasCrate(crate, p))
					getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString()
							+ ".crates." + crate.getCrateRarerity().name().toLowerCase(), getCrateCount(crate, p) - 1);
				else
					getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString()
							+ ".crates." + crate.getCrateRarerity().name().toLowerCase(), 0);
				getRegister().getPlayerFile().save();
			}
		}.runTaskLaterAsynchronously(getUhc(), 10);
	}

	public boolean hasCrate(UHCCrate c, OfflinePlayer p) {
		return getCrateCount(c, p) >= 1;
	}

	// Points

	public int getPoints(OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Points") == null) {
				return getRegister().getPlayerFile()
						.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points");
			}
			String points = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Points").toString();
			return Integer.parseInt(points);
		}
		return getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points");
	}

	public void addPoints(final OfflinePlayer p, final int amount) {
		new BukkitRunnable() {
			public void run() {
				if (getUhc().isMySQLMode()) {
					if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
							"Points") == null) {
						getRegister().getPlayerFile().set(
								"Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points",
								getPoints(p) + amount);
						getRegister().getPlayerFile().save();
					}
					MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Points",
							Integer.toString(getPoints(p) + amount));
					return;
				}
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points",
						getPoints(p) + amount);
				getRegister().getPlayerFile().save();
			}
		}.runTaskLaterAsynchronously(getUhc(), 10);
	}

	public void removePoints(final OfflinePlayer p, final int amount) {
		new BukkitRunnable() {
			public void run() {
				if (getUhc().isMySQLMode()) {
					if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
							"Points") == null) {
						int points = getPoints(p);
						if (points - amount < 0)
							getRegister().getPlayerFile()
									.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points", 0);
						else
							getRegister().getPlayerFile().set(
									"Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points",
									points - amount);

						getRegister().getPlayerFile().save();
					}
					if (getPoints(p) - amount < 0)
						MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Points",
								Integer.toString(0));
					else
						MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Points",
								Integer.toString(getPoints(p) - amount));
					return;
				}
				int points = getPoints(p);
				if (points - amount < 0)
					getRegister().getPlayerFile()
							.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points", 0);
				else
					getRegister().getPlayerFile()
							.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points", points - amount);

				getRegister().getPlayerFile().save();
			}
		}.runTaskLaterAsynchronously(getUhc(), 10);
	}

	// Rank

	public OfflinePlayer getPlayerByRank(int r) {
		TreeMap<String, Integer> points = new TreeMap<>();
		TreeMap<Integer, String> rank = new TreeMap<>();

		if (getUhc().isMySQLMode()) {
			try {
				ResultSet rs = MySQLAPI.getMySQLConnection().createStatement()
						.executeQuery("SELECT " + "UUID" + " FROM " + "UHC" + " ORDER BY " + "Points" + " asc");

				int in = MySQLAPI.getCountNumber() + 1;

				while (rs.next()) {
					in--;
					rank.put(in, rs.getString("UUID"));
				}

				return Bukkit.getOfflinePlayer(UUID.fromString(rank.get(r)));
			} catch (Exception e) {
				if (getUhc().isDebug())
					getLog().log(Level.SEVERE, "Something went during loading the stats. Skipping...", e.getMessage());
				return Bukkit.getOfflinePlayer(UUIDFetcher.getUUID("AlphaHelix"));
			}
		}
		for (String UUIDs : getRegister().getPlayerFile().getConfigurationSection("Players").getKeys(false)) {
			String playerName = UUIDFetcher.getName(UUID.fromString(UUIDs));
			points.put(playerName, getPoints(Bukkit.getOfflinePlayer(UUID.fromString(UUIDs))));
		}

		SortedSet<Map.Entry<String, Integer>> ss = entriesSortedByValues(points);

		for (Entry<String, Integer> name : ss) {
			rank.put(name.getValue(), name.getKey());
		}

		return Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(rank.get(r)));
	}

	public int getRank(OfflinePlayer p) {
		TreeMap<String, Integer> points = new TreeMap<>();
		TreeMap<String, Integer> rank = new TreeMap<>();

		if (getUhc().isMySQLMode()) {
			try {
				ResultSet rs = null;
				rs = MySQLAPI.getMySQLConnection().createStatement()
						.executeQuery("SELECT " + "UUID" + " FROM " + "UHC" + " ORDER BY " + "Points" + " asc");

				int in = MySQLAPI.getCountNumber() + 1;

				while (rs.next()) {
					in--;
					rank.put(p.getName(), in);
				}

				return rank.get(p.getName());
			} catch (Exception e) {
				if (getUhc().isDebug())
					getLog().log(Level.SEVERE, "Something went during loading the stats. Skipping...", e.getMessage());
				return -1;
			}
		}
		for (String UUIDs : getRegister().getPlayerFile().getConfigurationSection("Players").getKeys(false)) {
			String playerName = UUIDFetcher.getName(UUID.fromString(UUIDs));
			points.put(playerName, getPoints(Bukkit.getOfflinePlayer(UUID.fromString(UUIDs))));
		}

		SortedSet<Map.Entry<String, Integer>> ss = entriesSortedByValues(points);
		for (Entry<String, Integer> name : ss) {
			rank.put(name.getKey(), name.getValue());
		}

		return rank.containsKey(p.getName()) ? rank.get(p.getName()) + 1 : -1;
	}

	static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				int res = e1.getValue().compareTo(e2.getValue());
				return res != 0 ? res : 1;
			}
		});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

	// sending Stats

	public void sendStats(final Player toSend, final OfflinePlayer p) {
		new BukkitRunnable() {
			public void run() {
				String msg = getRegister().getStatsFile().getMessage();
				msg = msg.replace("[player]", p.getName());
				msg = msg.replace("[rank]", Integer.toString(getRank(p)));
				msg = msg.replace("[kills]", Integer.toString(getKills(p)));
				msg = msg.replace("[deaths]", Integer.toString(getDeaths(p)));
				msg = msg.replace("[coins]", Integer.toString(getCoins(p)));
				msg = msg.replace("[points]", Integer.toString(getPoints(p)));
				msg = msg.replace("[kits]", getKitsAsString(p));

				toSend.sendMessage("§8---===XXX===---\n" + msg + "§8---===XXX===---");
			}
		}.runTaskLaterAsynchronously(getUhc(), 10);
	}
}
