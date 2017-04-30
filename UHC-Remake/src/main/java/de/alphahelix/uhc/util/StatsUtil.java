package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.mysql.MySQLDatabase;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.PlayerInfo;
import de.alphahelix.uhc.instances.UHCRank;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatsUtil {

    private static final MySQLDatabase DATABASE = UHC.getDB();
    private static final HashMap<UUID, UHCRank> UHC_RANKS = new HashMap<>();
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yy HH:mm");

    public static String getListAsString(List<?> list) {
        return list.toString().replace("[", "").replace("]", "").replace(", ", " ;");
    }

    public static long getKills(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return PlayerInfo.getPlayerInfo(id).getKills();
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                return Long.parseLong(DATABASE.getResult("uuid", id.toString(), "Kills").toString());
            }
        }
        return UHCFileRegister.getPlayerFile().getKills(id);
    }

    public static long getDeaths(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return PlayerInfo.getPlayerInfo(id).getDeaths();
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                return Long.parseLong(DATABASE.getResult("uuid", id.toString(), "Deaths").toString());
            }
        }
        return UHCFileRegister.getPlayerFile().getDeaths(id);
    }

    public static double getKillDeathRate(UUID id) {
        if (getDeaths(id) == 0) return getKills(id);
        return Util.round((Double.valueOf(Long.toString(getKills(id))) / Double.valueOf(Long.toString(getDeaths(id)))), 3);
    }

    public static long getCoins(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return PlayerInfo.getPlayerInfo(id).getCoins();
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                return Long.parseLong(DATABASE.getResult("uuid", id.toString(), "Coins").toString());
            }
        }
        return UHCFileRegister.getPlayerFile().getCoins(id);
    }

    public static long getPoints(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return PlayerInfo.getPlayerInfo(id).getPoints();
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                return Long.parseLong(DATABASE.getResult("uuid", id.toString(), "Points").toString());
            }
        }
        return UHCFileRegister.getPlayerFile().getPoints(id);
    }

    public static long getWins(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return PlayerInfo.getPlayerInfo(id).getWins();
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                return Long.parseLong(DATABASE.getResult("uuid", id.toString(), "Wins").toString());
            }
        }
        return UHCFileRegister.getPlayerFile().getWins(id);
    }

    public static long getGames(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return PlayerInfo.getPlayerInfo(id).getGames();
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                return Long.parseLong(DATABASE.getResult("uuid", id.toString(), "Games").toString());
            }
        }
        return UHCFileRegister.getPlayerFile().getGames(id);
    }

    public static ArrayList<Kit> getKitList(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return PlayerInfo.getPlayerInfo(id).getKits();
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                ArrayList<Kit> kits = new ArrayList<>();

                if (DATABASE.getResult("uuid", id.toString(), "Kits").toString().equals("-")) {
                    return kits;
                }

                for (String kit : DATABASE.getResult("uuid", id.toString(), "Kits").toString().split(" ;")) {
                    kits.add(Kit.fromString(kit));
                }
                return kits;
            }
        }
        return UHCFileRegister.getPlayerFile().getKits(id);
    }

    public static String getKitsAsReadableString(UUID id) {
        String kits = "";
        for (Kit k : getKitList(id)) {
            kits = kits + k.getName() + " ;";
        }
        return kits;
    }

    public static ArrayList<UHCAchievements> getAchievementList(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return PlayerInfo.getPlayerInfo(id).getAchievements();
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                ArrayList<UHCAchievements> achievements = new ArrayList<>();

                if (DATABASE.getResult("uuid", id.toString(), "Achievements").toString().equals("-")) {
                    return achievements;
                }

                for (String achievement : DATABASE.getResult("uuid", id.toString(), "Achievements").toString().split(" ;")) {
                    achievements.add(UHCAchievements.fromString(achievement));
                }
                return achievements;
            }
        }
        return UHCFileRegister.getPlayerFile().getAchievements(id);
    }

    public static String getAchievementsAsReadableString(UUID id) {
        String achievement = "";
        for (UHCAchievements uhcAchievements : getAchievementList(id)) {
            achievement = achievement + uhcAchievements.getName() + " ;";
        }
        return achievement;
    }

    public static List<String> getCratesList(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return PlayerInfo.getPlayerInfo(id).getCrates();
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                List<String> crateNames = new ArrayList<>();

                if (DATABASE.getResult("uuid", id.toString(), "Crates").toString().equals("-")) {
                    return crateNames;
                }

                for (String crateName : DATABASE.getResult("uuid", id.toString(), "Crates").toString().split(";")) {
                    crateNames.add(crateName);
                }
                return crateNames;
            }
        }
        return UHCFileRegister.getPlayerFile().getCrates(id);
    }

    public static Date getNextReward(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return PlayerInfo.getPlayerInfo(id).getNextReward();
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                String sdf = DATABASE.getResult("uuid", id.toString(), "NextReward").toString();

                try {
                    return SDF.parse(sdf);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return new Date();
                }
            }
        }
        return UHCFileRegister.getPlayerFile().getNextReward(id);
    }

    public static void setNextRewardTime(final UUID id) {
        if (getUHCRank(id) == null) return;

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "NextReward",
                                new SimpleDateFormat("dd/MM/yy HH:mm").format(TimeUtil.increaseDate(getUHCRank(id).getRewardCooldownTime())));
                    }
                }
                UHCFileRegister.getPlayerFile().setNextReward(id);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 10);
    }

    public static void addKill(final UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).addKill(1);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "Kills", Long.toString(getKills(id) + 1));
                    }
                }
                UHCFileRegister.getPlayerFile().addKills(id, 1);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void addDeath(UUID id) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).addDeath(1);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "Deaths", Long.toString(getDeaths(id) + 1));
                    }
                }
                UHCFileRegister.getPlayerFile().addDeaths(id, 1);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void addCoins(final UUID id, final long amount) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).addCoins(amount);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "Coins", Long.toString(getCoins(id) + amount));
                    }
                }
                UHCFileRegister.getPlayerFile().addCoins(id, amount);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void removeCoins(final UUID id, final long amount) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).removeCoins(amount);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        if (getCoins(id) - amount <= 0) {
                            DATABASE.update(id, "Coins", "0");
                        } else {
                            DATABASE.update(id, "Coins", Long.toString(getCoins(id) - amount));
                        }
                    }
                }
                UHCFileRegister.getPlayerFile().removeCoins(id, amount);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void addPoints(final UUID id, final long amount) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).addPoints(amount);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "Points", Long.toString(getPoints(id) + amount));
                    }
                }
                UHCFileRegister.getPlayerFile().addPoints(id, amount);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void removePoints(final UUID id, final long amount) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).removePoints(amount);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        if (getCoins(id) - amount <= 0) {
                            DATABASE.update(id, "Points", "0");
                        } else {
                            DATABASE.update(id, "Points", Long.toString(getPoints(id) - amount));
                        }
                    }
                }
                UHCFileRegister.getPlayerFile().removePoints(id, amount);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void addWins(final UUID id, final long amount) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).addWin(amount);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "Wins", Long.toString(getWins(id) + amount));
                    }
                }
                UHCFileRegister.getPlayerFile().addWins(id, amount);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void addGames(final UUID id, final long amount) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).addGame(amount);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "Games", Long.toString(getGames(id) + amount));
                    }
                }
                UHCFileRegister.getPlayerFile().addGames(id, amount);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void addKits(final UUID id, final Kit kit) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).addKits(kit);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "Kits", getListAsString(getKitList(id)) + kit.serialize() + " ;");
                    }
                }
                UHCFileRegister.getPlayerFile().addKits(id, kit);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void addAchievement(final UUID id, final UHCAchievements achievement) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).addAchievement(achievement);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "Achievements", getListAsString(getAchievementList(id)) + achievement.toString() + " ;");
                    }
                }
                UHCFileRegister.getPlayerFile().addAchievemetns(id, achievement);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void addCrate(final UUID id, final Crate crate) {
        Crate.addCrate(crate, id);
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).addCrate(crate);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "Crates", getListAsString(getCratesList(id)) + crate.getRawName() + " ;");
                    }
                }
                UHCFileRegister.getPlayerFile().addCrate(id, crate);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static void removeCrate(final UUID id, final Crate crate) {
        Crate.removeCrate(crate, id);
        if (PlayerInfo.getPlayerInfo(id) != null) {
            PlayerInfo.getPlayerInfo(id).removeCrate(crate);
            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode()) {
                    if (DATABASE.containsPlayer(id)) {
                        DATABASE.update(id, "Crates", getListAsString(getCratesList(id)).replaceFirst(crate.getRawName() + " ;", ""));
                    }
                }
                UHCFileRegister.getPlayerFile().removeCrate(id, crate);
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 5);
    }

    public static long getCrateCount(UUID id, Crate crate) {
        if (PlayerInfo.getPlayerInfo(id) != null) {
            return Crate.getCrateCount(crate, id);
        }

        if (UHC.isMySQLMode()) {
            if (DATABASE.containsPlayer(id)) {
                long count = 0;
                String qCrates = DATABASE.getResult("uuid", id.toString(), "Crates").toString();

                for (String crateNames : qCrates.split(" ;")) {
                    if (Crate.getCrateByRawName(crateNames) == null) continue;
                    if (Crate.getCrateByRawName(crateNames).equals(crate))
                        count++;
                }
                return count;
            }
        }
        return UHCFileRegister.getPlayerFile().getCrateCount(id, crate);
    }

    public static long getRank(UUID uuid) {
        HashMap<UUID, Long> points = new HashMap<>();

        for (UUID id : getOfflinePlayers()) {
            points.put(id, getPoints(id));
        }

        Map<UUID, Long> ss = MapUtil.sortByValue(points);

        int pos = ss.size();

        for (UUID id : ss.keySet()) {
            if (!id.equals(uuid)) pos--;
            else break;
        }

        return pos;
    }

    public static String getPlayernameByRank(long r) {
        HashMap<Long, String> rank = new HashMap<>();

        for (UUID id : getOfflinePlayers()) {
            rank.put(getRank(id), UUIDFetcher.getName(id));
        }

        return rank.get(r);
    }

    private static List<UUID> getOfflinePlayers() {
        ArrayList<UUID> ids = new ArrayList<>();
        if (UHC.isMySQLMode()) {
            for (String uuid : UHC.getDB().getList("UUID")) {
                ids.add(UUID.fromString(uuid));
            }
        } else {
            for (String uuid : UHCFileRegister.getPlayerFile().getUUIDS()) {
                ids.add(UUID.fromString(uuid));
            }
        }
        return ids;
    }

    public static UHCRank getUHCRank(UUID id) {
        if (hasRank(id)) return UHC_RANKS.get(id);
        return null;
    }

    public static void setUhcRank(UUID id, UHCRank uhcRank) {
        UHC_RANKS.put(id, uhcRank);
    }

    public static boolean hasRank(UUID id) {
        return UHC_RANKS.containsKey(id);
    }

    public static boolean canClaimReward(UUID id) {
        return TimeUtil.getDateDiff(getNextReward(id)) <= 0;
    }

    public static boolean hasAchievement(UUID id, UHCAchievements achievement) {
        return getAchievementList(id).contains(achievement);
    }

    public static boolean hasKit(UUID id, Kit k) {
        return getKitList(id).contains(k);
    }

    public static boolean hasCrate(UUID id, Crate c) {
        return getCrateCount(id, c) > 0;
    }

    public static void sendStats(Player toSend, UUID id) {
        new BukkitRunnable() {
            public void run() {

                String msg = UHCFileRegister.getStatsFile().getMessage();
                msg = msg.replace("[player]", UUIDFetcher.getName(id));
                msg = msg.replace("[rank]", Long.toString(getRank(id)));
                msg = msg.replace("[kills]", Long.toString(getKills(id)));
                msg = msg.replace("[deaths]", Long.toString(getDeaths(id)));
                msg = msg.replace("[kdr]", Double.toString(getKillDeathRate(id)));
                msg = msg.replace("[coins]", Long.toString(getCoins(id)));
                msg = msg.replace("[points]", Long.toString(getPoints(id)));
                msg = msg.replace("[wins]", Long.toString(getWins(id)));
                msg = msg.replace("[games]", Long.toString(getGames(id)));
                msg = msg.replace("[kits]", getKitsAsReadableString(id));
                msg = msg.replace("[achievements]", getAchievementsAsReadableString(id));

                toSend.sendMessage("§8---===XXX===---\n" + msg + "\n§8---===XXX===---");
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 10);
    }
}
