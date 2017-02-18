package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.mysql.Database;
import de.alphahelix.alphalibary.mysql.MySQLAPI;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.instances.*;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.ResultSet;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

public class StatsUtil extends Util {

    private static HashMap<String, UHCRank> uhcRankHashMap = new HashMap<>();

    private long g;
    private long k;
    private long d;
    private long w;

    private BukkitTask pushGames;
    private BukkitTask pushKills;
    private BukkitTask pushDeaths;
    private BukkitTask pushWins;
    private BukkitTask pushCrates;

    public StatsUtil(UHC uhc) {
        super(uhc);
    }

    // Kills

    public void pushInformations(final Player p) {
        final PlayerInfo playerInfo = UHCRegister.getPlayerUtil().getPlayerInfo(p);
        UHCRegister.getPlayerUtil().removePlayerInfo(p);

        k = getKills(p);
        d = getDeaths(p);
        w = getWins(p);

        pushKills = new BukkitRunnable() {
            @Override
            public void run() {
                if (k < playerInfo.getKills()) {
                    addKill(p);
                    k++;
                } else
                    pushKills.cancel();
            }
        }.runTaskTimerAsynchronously(getUhc(), 0, 20);

        pushDeaths = new BukkitRunnable() {
            @Override
            public void run() {
                if (d < playerInfo.getDeaths()) {
                    addDeath(p);
                    d++;
                } else
                    pushDeaths.cancel();
            }
        }.runTaskTimerAsynchronously(getUhc(), 0, 20);

        pushWins = new BukkitRunnable() {
            @Override
            public void run() {
                if (w < playerInfo.getWins()) {
                    addWin(p);
                    w++;
                } else
                    pushWins.cancel();
            }
        }.runTaskTimerAsynchronously(getUhc(), 0, 20);

        if (getCoins(p) > playerInfo.getCoins()) {
            removeCoins(p, (int) (getCoins(p) - playerInfo.getCoins()));
        } else if (getCoins(p) < playerInfo.getCoins()) {
            addCoins(p, (int) (playerInfo.getCoins() - getCoins(p)));
        }

        if (getPoints(p) > playerInfo.getPoints()) {
            removePoints(p, (int) (getPoints(p) - playerInfo.getPoints()));
        } else if (getPoints(p) < playerInfo.getPoints()) {
            addPoints(p, (int) (playerInfo.getPoints() - getPoints(p)));
        }

        if (!getKitsAsString(p).equals(playerInfo.getKits())) {
            for (Kit newKits : playerInfo.getKits()) {
                if (newKits != null) {
                    if (!getKitsAsList(p).contains(newKits)) {
                        addKit(newKits, p);
                    }
                }
            }
        }

        pushCrates = new BukkitRunnable() {
            public void run() {
                for (Crate c : Crate.getCrateArrayList()) {
                    if (getCrateCount(c, p) < playerInfo.getCrateCount(c)) {
                        addCrate(c, p);
                    } else if (getCrateCount(c, p) > playerInfo.getCrateCount(c)) {
                        removeCrate(c, p);
                    } else {
                        pushCrates.cancel();
                    }
                }
            }
        }.runTaskTimerAsynchronously(getUhc(), 0, 20);
    }

    public void pushInformations() {
        for (String pp : UHCRegister.getPlayerUtil().getAll()) {
            if (Bukkit.getPlayer(pp) == null) continue;
            final Player p = Bukkit.getPlayer(pp);
            final PlayerInfo playerInfo = UHCRegister.getPlayerUtil().getPlayerInfo(p);
            UHCRegister.getPlayerUtil().removePlayerInfo(p);

            g = getGames(p);
            k = getKills(p);
            d = getDeaths(p);
            w = getWins(p);

            pushGames = new BukkitRunnable() {
                @Override
                public void run() {
                    if (g < playerInfo.getGames()) {
                        addGame(p);
                        g++;
                    } else
                        pushGames.cancel();
                }
            }.runTaskTimerAsynchronously(getUhc(), 0, 20);

            pushKills = new BukkitRunnable() {
                @Override
                public void run() {
                    if (k < playerInfo.getKills()) {
                        addKill(p);
                        k++;
                    } else
                        pushKills.cancel();
                }
            }.runTaskTimerAsynchronously(getUhc(), 0, 20);

            pushDeaths = new BukkitRunnable() {
                @Override
                public void run() {
                    if (d < playerInfo.getDeaths()) {
                        addDeath(p);
                        d++;
                    } else
                        pushDeaths.cancel();
                }
            }.runTaskTimerAsynchronously(getUhc(), 0, 20);

            pushWins = new BukkitRunnable() {
                @Override
                public void run() {
                    if (w < playerInfo.getWins()) {
                        addWin(p);
                        w++;
                    } else
                        pushWins.cancel();
                }
            }.runTaskTimerAsynchronously(getUhc(), 0, 20);


            if (getCoins(p) > playerInfo.getCoins()) {
                removeCoins(p, (int) (getCoins(p) - playerInfo.getCoins()));
            } else if (getCoins(p) < playerInfo.getCoins()) {
                addCoins(p, (int) (playerInfo.getCoins() - getCoins(p)));
            }

            if (getPoints(p) > playerInfo.getPoints()) {
                removePoints(p, (int) (getPoints(p) - playerInfo.getPoints()));
            } else if (getPoints(p) < playerInfo.getPoints()) {
                addPoints(p, (int) (playerInfo.getPoints() - getPoints(p)));
            }

            if (!getKitsAsString(p).equals(playerInfo.getKits())) {
                for (Kit newKits : playerInfo.getKits()) {
                    if (newKits != null) {
                        if (!getKitsAsList(p).contains(newKits)) {
                            addKit(newKits, p);
                        }
                    }
                }
            }

            pushCrates = new BukkitRunnable() {
                public void run() {
                    for (Crate c : Crate.getCrateArrayList()) {
                        if (getCrateCount(c, p) < playerInfo.getCrateCount(c)) {
                            addCrate(c, p);
                        } else if (getCrateCount(c, p) > playerInfo.getCrateCount(c)) {
                            removeCrate(c, p);
                        } else {
                            pushCrates.cancel();
                        }
                    }
                }
            }.runTaskTimerAsynchronously(getUhc(), 0, 20);
        }
    }

    public long getGames(final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            return UHCRegister.getPlayerUtil().getPlayerInfo(p).getGames();
        }
        if (getUhc().isMySQLMode()) {
            if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                    "Games") != null) {
                return Long.parseLong(Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Games").toString());
            }
        }
        return UHCFileRegister.getPlayerFile().getGames(p);
    }

    public void addGame(final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).setGames(getGames(p) + 1);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Games") != null) {
                        Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Games",
                                Long.toString(getGames(p) + 1));
                    }
                }
                long games = getGames(p);
                UHCFileRegister.getPlayerFile().setGames(p, games + 1);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public long getKills(final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            return UHCRegister.getPlayerUtil().getPlayerInfo(p).getKills();
        }
        if (getUhc().isMySQLMode()) {
            if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                    "Kills") != null) {
                return Long.parseLong(Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Kills").toString());
            }
        }
        return UHCFileRegister.getPlayerFile().getKills(p);
    }

    public void addKill(final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).setKills(getKills(p) + 1);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Kills") != null) {
                        Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Kills",
                                Long.toString(getKills(p) + 1));
                    }
                }
                long kills = getKills(p);
                UHCFileRegister.getPlayerFile().setKills(p, kills + 1);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    // Coins

    public long getDeaths(final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            return UHCRegister.getPlayerUtil().getPlayerInfo(p).getDeaths();
        }
        if (getUhc().isMySQLMode()) {
            if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                    "Deaths") != null) {
                return Long.parseLong(Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Deaths").toString());
            }
        }
        return UHCFileRegister.getPlayerFile().getDeaths(p);
    }

    public void addDeath(final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).setDeaths(getDeaths(p) + 1);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Deaths") != null) {
                        Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Deaths",
                                Long.toString(getDeaths(p) + 1));
                    }
                }
                long deaths = getDeaths(p);
                UHCFileRegister.getPlayerFile().setDeaths(p, deaths + 1);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public long getCoins(final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            return UHCRegister.getPlayerUtil().getPlayerInfo(p).getCoins();
        }
        if (getUhc().isMySQLMode()) {
            if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                    "Coins") != null) {
                String coins = Database
                        .getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Coins").toString();
                return Long.parseLong(coins);
            }
        }
        return UHCFileRegister.getPlayerFile().getCoins(p);
    }

    public double getKillDeathRate(OfflinePlayer p) {
        if (getDeaths(p) == 0) return getKills(p);
        return round((Double.valueOf(Long.toString(getKills(p))) / Double.valueOf(Long.toString(getDeaths(p)))), 3);
    }

    // Kits

    public void addCoins(final OfflinePlayer p, final int amount) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).setCoins(getCoins(p) + amount);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                long coins = getCoins(p);
                if (getUhc().isMySQLMode()) {
                    if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Coins") != null) {
                        Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Coins",
                                Long.toString(coins + amount));
                    }
                }
                UHCFileRegister.getPlayerFile().setCoins(p, coins + amount);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public void removeCoins(final OfflinePlayer p, final int amount) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).setCoins(getCoins(p) - amount);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                long coins = getCoins(p);
                if (getUhc().isMySQLMode()) {
                    if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Coins") != null) {
                        if (getCoins(p) - amount < 0)
                            Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Coins",
                                    Long.toString(0));
                        else
                            Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Coins",
                                    Long.toString(coins - amount));
                    }
                }
                if (coins - amount < 0)
                    UHCFileRegister.getPlayerFile().setCoins(p, 0);
                else
                    UHCFileRegister.getPlayerFile().setCoins(p, coins - amount);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public String getKitsAsString(OfflinePlayer p) {
        String kits = "";
        for (Kit k : getKitsAsList(p)) {
            kits = kits + k.serialize() + " ;";
        }
        return kits;
    }

    public String getKitsAsReadableString(OfflinePlayer p) {
        String kits = "";
        for (Kit k : getKitsAsList(p)) {
            kits = kits + k.getName() + " ;";
        }
        return kits;
    }

    public List<Kit> getKitsAsList(OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            return UHCRegister.getPlayerUtil().getPlayerInfo(p).getKits();
        }
        if (getUhc().isMySQLMode()) {
            if (Database.containsPlayer("UHC", p)) {
                ArrayList<Kit> kits = new ArrayList<>();

                if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Kits").toString().equals("-")) {
                    return kits;
                }

                for (String kit : Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Kits").toString().split(" ;")) {
                    kits.add(Kit.fromString(kit));
                }
                return kits;
            }
        }
        return UHCFileRegister.getPlayerFile().getKits(p);
    }

    // Crates

    public void addKit(final Kit kit, final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).addKit(kit);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (Database.containsPlayer("UHC", p)) {
                        Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Kits", getKitsAsString(p) + kit.serialize() + " ;");
                    }
                }
                UHCFileRegister.getPlayerFile().addKit(p, kit);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public boolean hasKit(Kit k, OfflinePlayer p) {
        return getKitsAsString(p).contains(k.serialize());
    }

    public List<String> getCratesAsList(OfflinePlayer p) {
        ArrayList<String> names = new ArrayList<>();
        for (String name : getCratesAsArray(p)) {
            names.add(name);
        }
        return names;
    }

    public String getCratesAsString(OfflinePlayer p) {
        String crates = "";
        for (String name : getCratesAsArray(p)) {
            crates += name + ";";
        }
        return crates;
    }

    public String[] getCratesAsArray(OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            return UHCRegister.getPlayerUtil().getPlayerInfo(p).getCrates().split(";");
        }

        if (getUhc().isMySQLMode()) {
            if (Database.containsPlayer("UHC", p)) {
                ArrayList<String> crateNames = new ArrayList<>();

                for (String crate : Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Crates").toString().split(";")) {
                    crateNames.add(crate);
                }

                return crateNames.toArray(new String[crateNames.size()]);
            }
        }
        return UHCFileRegister.getPlayerFile().getCrates(p).toArray(new String[UHCFileRegister.getPlayerFile().getCrates(p).size()]);
    }

    public long getCrateCount(Crate crate, OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            return Crate.getCrateCount(crate, p);
        }
        if (getUhc().isMySQLMode()) {
            if (Database.containsPlayer("UHC", p)) {
                long count = 0;
                String qCrates = Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Crates").toString();

                for (String crateNames : qCrates.split(";")) {
                    if (Crate.getCrateByRawName(crateNames) == null) continue;
                    if (Crate.getCrateByRawName(crateNames).equals(crate))
                        count++;
                }
                return count;
            }
        }
        return UHCFileRegister.getPlayerFile().getCrate(p, crate);
    }

    public void addCrate(final Crate crate, final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).addCrate(crate.getRawName());
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (Database.containsPlayer("UHC", p)) {

                        String crates;

                        if (!Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Crates").toString().equals("-")) {
                            crates = Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Crates").toString() + crate.getRawName() + ";";
                        } else {
                            crates = crate.getRawName() + ";";
                        }

                        Database.update("UHC", UUIDFetcher.getUUID(p), "Crates", crates);
                    }
                }
                UHCFileRegister.getPlayerFile().removeCrate(p, crate);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    // Points

    public void removeCrate(final Crate crate, final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).removeCrate(crate);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (Database.containsPlayer("UHC", p)) {
                        if (!Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Crates").equals("-")) {
                            String crates = Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Crates").toString();

                            if (crates.contains(crate.getRawName())) {
                                crates = crates.replaceFirst(crate.getRawName() + ";", "");
                            }

                            if(crates.isEmpty() || crates.equals(" ")) crates = "-";

                            Database.update("UHC", UUIDFetcher.getUUID(p), "Crates", crates);
                        }
                    }
                }
                UHCFileRegister.getPlayerFile().removeCrate(p, crate);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public boolean hasCrate(Crate c, OfflinePlayer p) {
        return getCrateCount(c, p) > 0;
    }

    public long getPoints(OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            return UHCRegister.getPlayerUtil().getPlayerInfo(p).getPoints();
        }
        if (getUhc().isMySQLMode()) {
            if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                    "Points") != null) {
                String points = Database
                        .getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Points").toString();
                return Long.parseLong(points);
            }
        }
        return UHCFileRegister.getPlayerFile().getPoints(p);
    }

    // Rank

    public void addPoints(final OfflinePlayer p, final int amount) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).setPoints(getPoints(p) + amount);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Points") != null) {
                        Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Points",
                                Long.toString(getPoints(p) + amount));
                    }
                }
                UHCFileRegister.getPlayerFile().setPoints(p, getPoints(p) + amount);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public void removePoints(final OfflinePlayer p, final int amount) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).setPoints(getPoints(p) - amount);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                long points = getPoints(p);
                if (getUhc().isMySQLMode()) {
                    if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Points") != null) {
                        if (points - amount < 0)
                            Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Points",
                                    Integer.toString(0));
                        else
                            Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Points",
                                    Long.toString(points - amount));
                    }
                }
                if (points - amount < 0)
                    UHCFileRegister.getPlayerFile().setPoints(p, 0);
                else
                    UHCFileRegister.getPlayerFile().setPoints(p, points - amount);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public OfflinePlayer getPlayerByRank(long r) {
        TreeMap<String, Long> points = new TreeMap<>();
        TreeMap<Long, String> rank = new TreeMap<>();

        if (getUhc().isMySQLMode()) {
            try {
                ResultSet rs = MySQLAPI.getMySQLConnection().createStatement()
                        .executeQuery("SELECT " + "uuid" + " FROM " + "UHC" + " ORDER BY " + "Points" + " asc");

                ResultSet counts = MySQLAPI.getMySQLConnection().createStatement()
                        .executeQuery("SELECT COUNT(*) FROM UHC");

                long in = 1;

                while (counts.next()) {
                    in++;
                }

                while (rs.next()) {
                    in--;
                    rank.put(in, rs.getString("uuid"));
                }

                return Bukkit.getOfflinePlayer(UUID.fromString(rank.get(r)));
            } catch (Exception e) {
                if (getUhc().isDebug())
                    getLog().log(Level.SEVERE, "Something went during loading the stats. Skipping...", e.getMessage());
                return Bukkit.getOfflinePlayer(UUIDFetcher.getUUID("AlphaHelix"));
            }
        }
        for (String UUIDs : UHCFileRegister.getPlayerFile().getConfigurationSection("Players").getKeys(false)) {
            String playerName = UUIDFetcher.getName(UUID.fromString(UUIDs));
            points.put(playerName, getPoints(Bukkit.getOfflinePlayer(UUID.fromString(UUIDs))));
        }

        SortedSet<Map.Entry<String, Long>> ss = MapUtil.entriesSortedByValues(points);

        for (Entry<String, Long> name : ss) {
            rank.put(name.getValue(), name.getKey());
        }

        return Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(rank.get(r)));
    }

    public long getRank(OfflinePlayer p) {
        TreeMap<String, Long> points = new TreeMap<>();
        TreeMap<String, Long> rank = new TreeMap<>();

        if (getUhc().isMySQLMode()) {
            try {
                ResultSet rs = MySQLAPI.getMySQLConnection().createStatement()
                        .executeQuery("SELECT " + "uuid" + " FROM " + "UHC" + " ORDER BY " + "Points" + " asc");

                ResultSet counts = MySQLAPI.getMySQLConnection().createStatement()
                        .executeQuery("SELECT COUNT(*) FROM UHC");

                long in = 1;

                while (counts.next()) {
                    in++;
                }

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
        for (String UUIDs : UHCFileRegister.getPlayerFile().getConfigurationSection("Players").getKeys(false)) {
            String playerName = UUIDFetcher.getName(UUID.fromString(UUIDs));
            points.put(playerName, getPoints(Bukkit.getOfflinePlayer(UUID.fromString(UUIDs))));
        }

        SortedSet<Entry<String, Long>> ss = MapUtil.entriesSortedByValues(points);
        for (Entry<String, Long> name : ss) {
            rank.put(name.getKey(), name.getValue());
        }

        return rank.containsKey(p.getName()) ? rank.get(p.getName()) + 1 : -1;
    }

    // wins

    public long getWins(OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            return UHCRegister.getPlayerUtil().getPlayerInfo(p).getWins();
        }
        if (getUhc().isMySQLMode()) {
            if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                    "Wins") != null)
                return Long.parseLong(Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Wins").toString());
        }
        return UHCFileRegister.getPlayerFile().getWins(p);
    }

    public void addWin(final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).setWins(getWins(p) + 1);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Wins") != null) {
                        Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Wins",
                                Long.toString(getWins(p) + 1));
                        return;
                    }
                }
                UHCFileRegister.getPlayerFile().setWins(p, getWins(p) + 1);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    // achievements

    public String getAchievementsAsString(OfflinePlayer p) {
        String achievements = "";
        for (UHCAchievements a : getAchievementsAsList(p)) {
            achievements = achievements + a.toString() + " ;";
        }
        return achievements;
    }

    public String getAchievementsAsReadableString(OfflinePlayer p) {
        String achievements = "";
        for (UHCAchievements a : getAchievementsAsList(p)) {
            achievements = achievements + a.getName() + " ;";
        }
        return achievements;
    }

    public List<UHCAchievements> getAchievementsAsList(OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            return UHCRegister.getPlayerUtil().getPlayerInfo(p).getAchievements();
        }
        if (getUhc().isMySQLMode()) {
            if (Database.containsPlayer("UHC", p)) {
                ArrayList<UHCAchievements> achievements = new ArrayList<>();

                if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Achievements").toString().equals("-")) {
                    return achievements;
                }

                for (String achievement : Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(), "Achievements").toString().split(" ;")) {
                    achievements.add(UHCAchievements.fromString(achievement));
                }
                return achievements;
            }
        }
        return UHCFileRegister.getPlayerFile().getAchievements(p);
    }

    public void addAchievement(final UHCAchievements achievement, final OfflinePlayer p) {
        if (UHCRegister.getPlayerUtil().isInInfoMap(p)) {
            UHCRegister.getPlayerUtil().getPlayerInfo(p).addAchievement(achievement);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (Database.getResult("UHC", "uuid", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Achievements") != null) {
                        Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Achievements", getAchievementsAsString(p));
                        return;
                    }
                }
                UHCFileRegister.getPlayerFile().addAchievement(p, achievement);
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public boolean hasAchievement(UHCAchievements achievement, OfflinePlayer p) {
        return getAchievementsAsList(p).contains(achievement);
    }

    public void setUhcRank(UHCRank uhcRank, Player p) {
        uhcRankHashMap.put(p.getName(), uhcRank);
    }

    public UHCRank getUHCRank(Player p) {
        if (hasRank(p)) return uhcRankHashMap.get(p.getName());
        return null;
    }

    public boolean hasRank(Player p) {
        return uhcRankHashMap.containsKey(p.getName());
    }

    // sending Stats

    public void sendStats(final Player toSend, final OfflinePlayer p) {
        new BukkitRunnable() {
            public void run() {
                String msg = UHCFileRegister.getStatsFile().getMessage();
                msg = msg.replace("[player]", p.getName());
                msg = msg.replace("[rank]", Long.toString(getRank(p)));
                msg = msg.replace("[kills]", Long.toString(getKills(p)));
                msg = msg.replace("[deaths]", Long.toString(getDeaths(p)));
                msg = msg.replace("[kdr]", Double.toString(getKillDeathRate(p)));
                msg = msg.replace("[coins]", Long.toString(getCoins(p)));
                msg = msg.replace("[points]", Long.toString(getPoints(p)));
                msg = msg.replace("[wins]", Long.toString(getWins(p)));
                msg = msg.replace("[games]", Long.toString(getGames(p)));
                msg = msg.replace("[kits]", getKitsAsReadableString(p));
                msg = msg.replace("[achievements]", getAchievementsAsReadableString(p));

                toSend.sendMessage("§8---===XXX===---\n" + msg + "\n§8---===XXX===---");
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }
}
