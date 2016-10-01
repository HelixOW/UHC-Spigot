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

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.mysql.MySQLAPI;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class StatsUtil extends Util {

	// String = p.getName();
	private HashMap<String, PlayerData> cache = new HashMap<>();

	public StatsUtil(UHC uhc) {
		super(uhc);
	}

	public void initalizeCache(OfflinePlayer p, PlayerData pd) {
		cache.put(p.getName(), pd);
	}

	public void pushCacheToBackUp() {
		getUhc().getLog().log(Level.INFO, getUhc().getConsolePrefix() + "Sending playerdata to Backup! Might lag now");
		for (String pName : getRegister().getPlayerUtil().getAll()) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(pName));
			PlayerData pd = new PlayerData(getKills(p), getDeaths(p), getCoins(p), getPoints(p), getRank(p),
					getKitsAsList(p));
			cache.remove(p.getName());

			while (pd.getKills() > getKills(p)) {
				addKill(p);
			}
			while (pd.getDeaths() > getDeaths(p)) {
				addDeath(p);
			}

			if (pd.getCoins() < getCoins(p)) {
				addCoins(p, getCoins(p) - pd.getCoins());
			} else if (pd.getCoins() > getCoins(p)) {
				removeCoins(p, pd.getCoins() - getCoins(p));
			}

			if (pd.getPoints() < getPoints(p)) {
				addPoints(p, getPoints(p) - pd.getPoints());
			} else if (pd.getPoints() > getPoints(p)) {
				removePoints(p, pd.getPoints() - getPoints(p));
			}

			for (String kit : pd.getKits()) {
				if (!getKitsAsList(p).contains(kit)) {
					addKit(getRegister().getKitsFile().getKit(kit), p);
				}
			}
		}
	}

	// Kills

	public int getKills(OfflinePlayer p) {
		if (cache.containsKey(p.getName())) {
			return cache.get(p.getName()).getKills();
		}

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

	public void addKill(OfflinePlayer p) {
		if (cache.containsKey(p.getName())) {
			cache.get(p.getName()).setKills(getKills(p) + 1);
			return;
		}

		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Kills") == null) {
				int kills = getRegister().getPlayerFile()
						.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills",
						kills + 1);
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

	// Deaths

	public int getDeaths(OfflinePlayer p) {
		if (cache.containsKey(p.getName())) {
			return cache.get(p.getName()).getDeaths();
		}

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

	public void addDeath(OfflinePlayer p) {
		if (cache.containsKey(p.getName())) {
			cache.get(p.getName()).setDeaths(getDeaths(p) + 1);
			return;
		}
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Deaths") == null) {
				int deaths = getRegister().getPlayerFile()
						.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths",
						deaths + 1);
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

	// Coins

	public int getCoins(OfflinePlayer p) {
		if (cache.containsKey(p.getName())) {
			return cache.get(p.getName()).getCoins();
		}

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

	public void addCoins(OfflinePlayer p, int amount) {
		if (cache.containsKey(p.getName())) {
			cache.get(p.getName()).setCoins(getCoins(p) + amount);
			return;
		}
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Coins") == null) {
				int coins = getRegister().getPlayerFile()
						.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins",
						coins + 1);
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

	public void removeCoins(OfflinePlayer p, int amount) {
		if (cache.containsKey(p.getName())) {
			cache.get(p.getName()).setCoins(getCoins(p) - amount);
			return;
		}
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Coins") == null) {
				int coins = getCoins(p);
				if (coins - amount < 0)
					getRegister().getPlayerFile()
							.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", 0);
				else
					getRegister().getPlayerFile()
							.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", coins - amount);

				getRegister().getPlayerFile().save();
			}
			if (getCoins(p) - amount < 0)
				MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins", Integer.toString(0));
			else
				MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins",
						Integer.toString(getCoins(p) - amount));
			return;
		}
		int coins = getCoins(p);
		if (coins - amount < 0)
			getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", 0);
		else
			getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins",
					coins - amount);

		getRegister().getPlayerFile().save();
	}

	// Kits

	public String getKitsAsString(OfflinePlayer p) {
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Kits") == null) {
				String kit = getRegister().getPlayerFile()
						.getString("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits");
				return kit;
			}
			String kit = MySQLManager
					.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kits").toString();
			return kit;
		}
		String kit = getRegister().getPlayerFile()
				.getString("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits");
		return kit;
	}

	public ArrayList<String> getKitsAsList(OfflinePlayer p) {
		if (cache.containsKey(p.getName())) {
			return cache.get(p.getName()).getKits();
		}

		ArrayList<String> playerKits = new ArrayList<>();
		String kit = getKitsAsString(p);

		if (kit == null)
			return playerKits;
		if (kit.equals(""))
			return playerKits;

		String[] kits = kit.split(" ,");

		for (String kitName : kits) {
			playerKits.add(kitName);
		}
		return playerKits;
	}

	public void addKit(Kit kit, OfflinePlayer p) {
		if (cache.containsKey(p.getName())) {
			cache.get(p.getName()).addKits(kit);
			return;
		}

		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Kits") == null) {
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits",
						getKitsAsString(p) + kit.getName() + " ,");
				getRegister().getPlayerFile().save();
			}
			MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kits",
					getKitsAsString(p) + kit.getName() + " ,");
			return;
		}
		getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits",
				getKitsAsString(p) + kit.getName() + " ,");
		getRegister().getPlayerFile().save();
	}

	public boolean hasKit(Kit k, OfflinePlayer p) {
		if (getKitsAsList(p).contains(k.getName()))
			return true;
		return false;
	}

	// Points

	public int getPoints(OfflinePlayer p) {
		if (cache.containsKey(p.getName())) {
			return cache.get(p.getName()).getPoints();
		}

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

	public void addPoints(OfflinePlayer p, int amount) {
		if (cache.containsKey(p.getName())) {
			cache.get(p.getName()).setPoints(getPoints(p) + amount);
			return;
		}
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Points") == null) {
				getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points",
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

	public void removePoints(OfflinePlayer p, int amount) {
		if (cache.containsKey(p.getName())) {
			cache.get(p.getName()).setPoints(getPoints(p) - amount);
			return;
		}
		if (getUhc().isMySQLMode()) {
			if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
					"Points") == null) {
				int points = getPoints(p);
				if (points - amount < 0)
					getRegister().getPlayerFile()
							.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points", 0);
				else
					getRegister().getPlayerFile()
							.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points", points - amount);

				getRegister().getPlayerFile().save();
			}
			if (getPoints(p) - amount < 0)
				MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Points", Integer.toString(0));
			else
				MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Points",
						Integer.toString(getPoints(p) - amount));
			return;
		}
		int points = getPoints(p);
		if (points - amount < 0)
			getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points", 0);
		else
			getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points",
					points - amount);

		getRegister().getPlayerFile().save();
	}

	// Rank
	
	// /uhcsetup createRankingArmorstand 1
	
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

		if (cache.containsKey(p.getName())) {
			return cache.get(p.getName()).getRank();
		}

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

	public void sendStats(Player toSend, OfflinePlayer p) {
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
}
