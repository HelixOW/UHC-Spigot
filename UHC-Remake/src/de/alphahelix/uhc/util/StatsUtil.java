package de.alphahelix.uhc.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
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

import de.alphahelix.uhc.UHC;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.mysql.MySQLAPI;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class StatsUtil extends Util {
	
	public StatsUtil(UHC uhc) {
		super(uhc);
	}

	// Kills

	public int getKills(OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			String kills = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kills").toString();
			return Integer.parseInt(kills);
		}
		return getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
	}

	public void addKill(Player p) {
		if (getUhc().isMySQLMode()) {
			MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kills",
					Integer.toString(getKills(p) + 1));
		}
		int kills = getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
		getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills",
				kills + 1);
	}

	// Deaths

	public int getDeaths(OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			String deaths = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Deaths").toString();
			return Integer.parseInt(deaths);
		}
		return getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
	}

	public void addDeath(Player p) {
		if (getUhc().isMySQLMode()) {
			MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Deaths",
					Integer.toString(getDeaths(p) + 1));
		}
		int deaths = getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
		getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths",
				deaths + 1);
	}

	// Coins

	public int getCoins(OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			String coins = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Coins").toString();
			return Integer.parseInt(coins);
		}
		return getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
	}

	public void addCoins(Player p, int amount) {
		if (getUhc().isMySQLMode()) {
			MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins",
					Integer.toString(getCoins(p) + amount));
		}
		int coins = getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
		getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins",
				coins + 1);
	}

	public void removeCoins(Player p, int amount) {
		if (getUhc().isMySQLMode()) {
			MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins",
					Integer.toString(getCoins(p) - amount));
		}
		int coins = getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
		getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins",
				coins - 1);
	}

	// Kits

	public String getKitsAsString(OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			String kit = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kits").toString();
			return kit;
		}
		String kit = getRegister().getPlayerFile()
				.getString("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits");
		return kit;
	}

	public ArrayList<String> getKitsAsList(Player p) {
		ArrayList<String> playerKits = new ArrayList<>();
		String kit = getKitsAsString(p);
		String[] kits = kit.split(" ,");

		for (String kitName : kits) {
			playerKits.add(kitName);
		}
		return playerKits;
	}

	public void addKit(Kit kit, Player p) {
		if (getUhc().isMySQLMode()) {
			MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kits",
					getKitsAsString(p) + kit.getName() + " ,");
		}
		getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits",
				getKitsAsString(p) + kit.getName() + " ,");
	}

	// Points

	public int getPoints(OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			String points = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Points").toString();
			return Integer.parseInt(points);
		}
		return getRegister().getPlayerFile()
				.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points");
	}

	public void addPoints(OfflinePlayer p, int amount) {
		if (getUhc().isMySQLMode()) {
			MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Points",
					Integer.toString(getPoints(p) + amount));
		}
		getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points",
				getPoints(p) + amount);
	}

	public void removePoints(OfflinePlayer p, int amount) {
		if (getUhc().isMySQLMode()) {
			MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Points",
					Integer.toString(getPoints(p) - amount));
		}
		getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points",
				getPoints(p) - amount);
	}

	// Rank

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
					getLog().log(Level.SEVERE, "Something went during loading the stats. Skipping...",
							e.getMessage());
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

		return rank.containsKey(p.getName()) ? rank.get(p.getName())+1 : -1;
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
	
	public void sendStats(Player toSend, OfflinePlayer p) {
		String msg = getRegister().getStatsFile().getMessage();
		msg = msg.replace("[player]", p.getName());
		msg = msg.replace("[rank]", Integer.toString(getRank(p)));
		msg = msg.replace("[kills]", Integer.toString(getKills(p)));
		msg = msg.replace("[deaths]", Integer.toString(getDeaths(p)));
		msg = msg.replace("[coins]", Integer.toString(getCoins(p)));
		msg = msg.replace("[points]", Integer.toString(getPoints(p)));
		msg = msg.replace("[kits]", getKitsAsString(p));
		
		toSend.sendMessage("§8---===XXX===---\n"
					+ msg
                	+ "§8---===XXX===---");
	}
}
