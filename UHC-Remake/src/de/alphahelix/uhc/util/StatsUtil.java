package de.alphahelix.uhc.util;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.UHCCrateRarerity;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.PlayerInfo;
import de.alphahelix.uhc.instances.UHCCrate;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.mysql.MySQLAPI;
import de.popokaka.alphalibary.mysql.MySQLManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

public class StatsUtil extends Util {

    public StatsUtil(UHC uhc) {
        super(uhc);
    }

    public void pushInformations() {
        for(String pp : getRegister().getPlayerUtil().getAll()) {
            if(Bukkit.getPlayer(pp) == null) continue;
            Player p = Bukkit.getPlayer(pp);
            PlayerInfo playerInfo = getRegister().getPlayerUtil().getPlayerInfo(p);
            getRegister().getPlayerUtil().removePlayerInfo(p);

            long k = getKills(p);
            long d = getDeaths(p);

            while(k < playerInfo.getKills()) {
                addKill(p);
            }

            while (d < playerInfo.getDeaths()) {
                addDeath(p);
            }

            if(getCoins(p) > playerInfo.getCoins()) {
                removeCoins(p, (int) (getCoins(p) - playerInfo.getCoins()));
            } else if(getCoins(p) < playerInfo.getCoins()) {
                addCoins(p, (int) (playerInfo.getCoins() - getCoins(p)));
            }

            if(getPoints(p) > playerInfo.getPoints()) {
                removePoints(p, (int) (getPoints(p) - playerInfo.getPoints()));
            } else if(getPoints(p) < playerInfo.getPoints()) {
                addPoints(p, (int) (playerInfo.getPoints() - getPoints(p)));
            }

            if(!getKitsAsString(p).equals(playerInfo.getKits())) {
                for(String newKits : playerInfo.getKits().split(" ,")) {
                    if(newKits == null) continue;
                    if(newKits.equalsIgnoreCase("") || newKits.equalsIgnoreCase(" ")) continue;
                    if(getKitsAsList(p).contains(newKits)) continue;
                    else addKit(getRegister().getKitsFile().getKit(newKits), p);
                }
            }

            long normals = getCrateCount(UHCCrateRarerity.NORMAL, p);
            long uncommons = getCrateCount(UHCCrateRarerity.UNCOMMON, p);
            long rares = getCrateCount(UHCCrateRarerity.RARE, p);
            long superrares = getCrateCount(UHCCrateRarerity.SUPERRARE, p);
            long epics = getCrateCount(UHCCrateRarerity.EPIC, p);
            long legendarys = getCrateCount(UHCCrateRarerity.LEGENDARY, p);

            while(normals != playerInfo.getNormalC()) {
                if(normals < playerInfo.getNormalC())
                    addCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.NORMAL), p);
                else
                    removeCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.NORMAL), p);
            }
            while(uncommons != playerInfo.getUncommonC()) {
                if(uncommons < playerInfo.getUncommonC())
                    addCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.UNCOMMON), p);
                else
                    removeCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.UNCOMMON), p);
            }
            while(rares != playerInfo.getRareC()) {
                if(rares < playerInfo.getRareC())
                    addCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.RARE), p);
                else
                    removeCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.RARE), p);
            }
            while(superrares != playerInfo.getSuperrareC()) {
                if(superrares < playerInfo.getSuperrareC())
                    addCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.SUPERRARE), p);
                else
                    removeCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.SUPERRARE), p);
            }
            while(epics != playerInfo.getEpicC()) {
                if(epics < playerInfo.getEpicC())
                    addCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.EPIC), p);
                else
                    removeCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.EPIC), p);
            }
            while(legendarys != playerInfo.getLegendaryC()) {
                if(legendarys < playerInfo.getLegendaryC())
                    addCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.LEGENDARY), p);
                else
                    removeCrate(getRegister().getUhcCrateFile().getCrate(UHCCrateRarerity.LEGENDARY), p);
            }

        }
    }

    // Kills

    public long getKills(final OfflinePlayer p) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            return getRegister().getPlayerUtil().getPlayerInfo(p).getKills();
        }
        if (getUhc().isMySQLMode()) {
            if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                    "Kills") == null)
                return getRegister().getPlayerFile()
                        .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
            String kills = MySQLManager
                    .getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kills").toString();
            return Long.parseLong(kills);
        }
        return getRegister().getPlayerFile()
                .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
    }

    // Deaths

    public void addKill(final OfflinePlayer p) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            getRegister().getPlayerUtil().getPlayerInfo(p).setKills(getKills(p) + 1);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Kills") == null) {
                        long kills = getRegister().getPlayerFile()
                                .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
                        getRegister().getPlayerFile()
                                .set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills", kills + 1);
                        getRegister().getPlayerFile().save();
                    }
                    MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kills",
                            Long.toString(getKills(p) + 1));
                    return;
                }
                long kills = getRegister().getPlayerFile()
                        .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
                getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills",
                        kills + 1);
                getRegister().getPlayerFile().save();
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public long getDeaths(final OfflinePlayer p) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            return getRegister().getPlayerUtil().getPlayerInfo(p).getDeaths();
        }
        if (getUhc().isMySQLMode()) {
            if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                    "Deaths") == null)
                return getRegister().getPlayerFile()
                        .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
            String deaths = MySQLManager
                    .getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Deaths").toString();
            return Long.parseLong(deaths);
        }
        return getRegister().getPlayerFile()
                .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
    }

    // Coins

    public void addDeath(final OfflinePlayer p) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            getRegister().getPlayerUtil().getPlayerInfo(p).setDeaths(getDeaths(p) + 1);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Deaths") == null) {
                        long deaths = getRegister().getPlayerFile()
                                .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
                        getRegister().getPlayerFile()
                                .set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths", deaths + 1);
                        getRegister().getPlayerFile().save();
                    }
                    MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Deaths",
                            Long.toString(getDeaths(p) + 1));
                    return;
                }
                long deaths = getRegister().getPlayerFile()
                        .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
                getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths",
                        deaths + 1);
                getRegister().getPlayerFile().save();
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public long getCoins(final OfflinePlayer p) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            return getRegister().getPlayerUtil().getPlayerInfo(p).getCoins();
        }
        if (getUhc().isMySQLMode()) {
            if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                    "Coins") == null)
                return getRegister().getPlayerFile()
                        .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
            String coins = MySQLManager
                    .getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Coins").toString();
            return Long.parseLong(coins);
        }
        return getRegister().getPlayerFile()
                .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
    }

    public void addCoins(final OfflinePlayer p, final int amount) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            getRegister().getPlayerUtil().getPlayerInfo(p).setCoins(getCoins(p) + amount);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Coins") == null) {
                        long coins = getRegister().getPlayerFile()
                                .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
                        getRegister().getPlayerFile()
                                .set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", coins + 1);
                        getRegister().getPlayerFile().save();
                    }
                    MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins",
                            Long.toString(getCoins(p) + amount));
                    return;
                }
                long coins = getRegister().getPlayerFile()
                        .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
                getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins",
                        coins + 1);
                getRegister().getPlayerFile().save();
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    // Kits

    public void removeCoins(final OfflinePlayer p, final int amount) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            getRegister().getPlayerUtil().getPlayerInfo(p).setCoins(getCoins(p) - amount);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Coins") == null) {
                        long coins = getCoins(p);
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
                                Long.toString(0));
                    else
                        MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins",
                                Long.toString(getCoins(p) - amount));
                    return;
                }
                long coins = getCoins(p);
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

    public String getKitsAsString(OfflinePlayer p) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            return getRegister().getPlayerUtil().getPlayerInfo(p).getKits().replace("§", "&");
        }
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
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            getRegister().getPlayerUtil().getPlayerInfo(p).setKits(getKitsAsString(p) + kit.getName().replace("§", "&") + " ,");
            return;
        }
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

    // Crates

    public boolean hasKit(Kit k, OfflinePlayer p) {
        return getKitsAsList(p).contains(" " + k.getName().replace("§", "&"))
                || getKitsAsList(p).contains(" " + k.getName().replace("§", "&") + ",")
                || getKitsAsList(p).contains("," + k.getName().replace("§", "&"))
                || getKitsAsList(p).contains("," + k.getName().replace("§", "&") + " ")
                || getKitsAsList(p).contains(k.getName().replace("§", "&") + ",")
                || getKitsAsList(p).contains(k.getName().replace("§", "&"));
    }

    public String[] getCratesAsArray(OfflinePlayer p) {
        ArrayList<String> tT = new ArrayList<>();

        for (UHCCrate c : getRegister().getUhcCrateFile().getCrates()) {
            if (getCrateCount(c.getCrateRarerity(), p) <= 0) continue;
            tT.add(c.getCrateRarerity().name() + ":" + getCrateCount(c.getCrateRarerity(), p));
        }

        return tT.toArray(new String[tT.size()]);
    }

    public HashMap<UHCCrate, Long> getCrates(OfflinePlayer p) {
        HashMap<UHCCrate, Long> crates = new HashMap<>();

        for (UHCCrate c : getRegister().getUhcCrateFile().getCrates()) {
            crates.put(c, getCrateCount(c.getCrateRarerity(), p));
        }

        return crates;
    }

    public long getCrateCount(UHCCrateRarerity crate, OfflinePlayer p) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            switch (crate) {
                case NORMAL:
                    return getRegister().getPlayerUtil().getPlayerInfo(p).getNormalC();
                case UNCOMMON:
                    return getRegister().getPlayerUtil().getPlayerInfo(p).getUncommonC();
                case RARE:
                    return getRegister().getPlayerUtil().getPlayerInfo(p).getRareC();
                case SUPERRARE:
                    return getRegister().getPlayerUtil().getPlayerInfo(p).getSuperrareC();
                case EPIC:
                    return getRegister().getPlayerUtil().getPlayerInfo(p).getEpicC();
                case LEGENDARY:
                    return getRegister().getPlayerUtil().getPlayerInfo(p).getLegendaryC();
            }
        }
        if (getUhc().isMySQLMode()) {
            if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                    crate.name() + "Crates") == null) {

                return getRegister().getPlayerFile().getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString()
                        + ".crates." + crate.name().toLowerCase());
            }

            String crateCount = MySQLManager.getObjectConditionResult("UUID",
                    UUIDFetcher.getUUID(p.getName()).toString(), crate.name() + "Crates").toString();
            return Long.parseLong(crateCount);
        }
        return getRegister().getPlayerFile().getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString()
                + ".crates." + crate.name().toLowerCase());
    }

    public void addCrate(final UHCCrate crate, final OfflinePlayer p) {
        if (p.isOnline())
            ((Player) p).sendMessage(getUhc().getPrefix() + getRegister().getMessageFile().getColorString("Crate dropped")
                    .replace("[crate]", crate.getCrateRarerity().getPrefix() + crate.getName()));
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            switch (crate.getCrateRarerity()) {
                case NORMAL:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setNormalC(getCrateCount(UHCCrateRarerity.NORMAL, p) + 1);
                    break;
                case UNCOMMON:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setUncommonC(getCrateCount(UHCCrateRarerity.UNCOMMON, p) + 1);
                    break;
                case RARE:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setRareC(getCrateCount(UHCCrateRarerity.RARE, p) + 1);
                    break;
                case SUPERRARE:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setSuperrareC(getCrateCount(UHCCrateRarerity.SUPERRARE, p) + 1);
                    break;
                case EPIC:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setEpicC(getCrateCount(UHCCrateRarerity.EPIC, p) + 1);
                    break;
                case LEGENDARY:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setLegendaryC(getCrateCount(UHCCrateRarerity.LEGENDARY, p) + 1);
                    break;
            }
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                            crate.getCrateRarerity().name() + "Crates") == null) {

                        getRegister()
                                .getPlayerFile().set(
                                "Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates."
                                        + crate.getCrateRarerity().name().toLowerCase(),
                                getCrateCount(crate.getCrateRarerity(), p) + 1);
                        getRegister().getPlayerFile().save();
                    }
                    MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(),
                            crate.getCrateRarerity().name() + "Crates", Long.toString(getCrateCount(crate.getCrateRarerity(), p) + 1));
                    return;
                }
                getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates."
                        + crate.getCrateRarerity().name().toLowerCase(), getCrateCount(crate.getCrateRarerity(), p) + 1);
                getRegister().getPlayerFile().save();
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    public void removeCrate(final UHCCrate crate, final OfflinePlayer p) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            switch (crate.getCrateRarerity()) {
                case NORMAL:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setNormalC(getCrateCount(UHCCrateRarerity.NORMAL, p) - 1);
                case UNCOMMON:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setUncommonC(getCrateCount(UHCCrateRarerity.UNCOMMON, p) - 1);
                case RARE:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setRareC(getCrateCount(UHCCrateRarerity.RARE, p) - 1);
                case SUPERRARE:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setSuperrareC(getCrateCount(UHCCrateRarerity.SUPERRARE, p) - 1);
                case EPIC:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setEpicC(getCrateCount(UHCCrateRarerity.EPIC, p) - 1);
                case LEGENDARY:
                    getRegister().getPlayerUtil().getPlayerInfo(p).setLegendaryC(getCrateCount(UHCCrateRarerity.LEGENDARY, p) - 1);
            }
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                            crate.getCrateRarerity().name() + "Crates") == null) {

                        if (hasCrate(crate, p))
                            getRegister().getPlayerFile()
                                    .set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates."
                                                    + crate.getCrateRarerity().name().toLowerCase(),
                                            getCrateCount(crate.getCrateRarerity(), p) - 1);
                        else
                            getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString()
                                    + ".crates." + crate.getCrateRarerity().name().toLowerCase(), 0);
                        getRegister().getPlayerFile().save();
                    }

                    if (hasCrate(crate, p))
                        MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(),
                                crate.getCrateRarerity().name() + "Crates",
                                Long.toString(getCrateCount(crate.getCrateRarerity(), p) - 1));
                    else
                        MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(),
                                crate.getCrateRarerity().name() + "Crates", Integer.toString(0));
                    return;
                }

                if (hasCrate(crate, p))
                    getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString()
                            + ".crates." + crate.getCrateRarerity().name().toLowerCase(), getCrateCount(crate.getCrateRarerity(), p) - 1);
                else
                    getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString()
                            + ".crates." + crate.getCrateRarerity().name().toLowerCase(), 0);
                getRegister().getPlayerFile().save();
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    // Points

    public boolean hasCrate(UHCCrate c, OfflinePlayer p) {
        return getCrateCount(c.getCrateRarerity(), p) >= 1;
    }

    public long getPoints(OfflinePlayer p) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            return getRegister().getPlayerUtil().getPlayerInfo(p).getPoints();
        }
        if (getUhc().isMySQLMode()) {
            if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                    "Points") == null) {
                return getRegister().getPlayerFile()
                        .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points");
            }
            String points = MySQLManager
                    .getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Points").toString();
            return Long.parseLong(points);
        }
        return getRegister().getPlayerFile()
                .getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points");
    }

    public void addPoints(final OfflinePlayer p, final int amount) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            getRegister().getPlayerUtil().getPlayerInfo(p).setPoints(getPoints(p) + amount);
            return;
        }
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
                            Long.toString(getPoints(p) + amount));
                    return;
                }
                getRegister().getPlayerFile().set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points",
                        getPoints(p) + amount);
                getRegister().getPlayerFile().save();
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }

    // Rank

    public void removePoints(final OfflinePlayer p, final int amount) {
        if (getRegister().getPlayerUtil().isInInfoMap(p)) {
            getRegister().getPlayerUtil().getPlayerInfo(p).setPoints(getPoints(p) - amount);
            return;
        }
        new BukkitRunnable() {
            public void run() {
                if (getUhc().isMySQLMode()) {
                    if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(),
                            "Points") == null) {
                        long points = getPoints(p);
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
                                Long.toString(getPoints(p) - amount));
                    return;
                }
                long points = getPoints(p);
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

    public OfflinePlayer getPlayerByRank(long r) {
        TreeMap<String, Long> points = new TreeMap<>();
        TreeMap<Long, String> rank = new TreeMap<>();

        if (getUhc().isMySQLMode()) {
            try {
                ResultSet rs = MySQLAPI.getMySQLConnection().createStatement()
                        .executeQuery("SELECT " + "UUID" + " FROM " + "UHC" + " ORDER BY " + "Points" + " asc");

                long in = MySQLManager.getCountNumber("uhc") + 1;

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

        SortedSet<Map.Entry<String, Long>> ss = entriesSortedByValues(points);

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
                ResultSet rs;
                rs = MySQLAPI.getMySQLConnection().createStatement()
                        .executeQuery("SELECT " + "UUID" + " FROM " + "UHC" + " ORDER BY " + "Points" + " asc");

                long in = MySQLManager.getCountNumber("uhc") + 1;

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

        SortedSet<Map.Entry<String, Long>> ss = entriesSortedByValues(points);
        for (Entry<String, Long> name : ss) {
            rank.put(name.getKey(), name.getValue());
        }

        return rank.containsKey(p.getName()) ? rank.get(p.getName()) + 1 : -1;
    }

    static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<>((e1, e2) -> {
            int res = e1.getValue().compareTo(e2.getValue());
            return res != 0 ? res : 1;
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
                msg = msg.replace("[rank]", Long.toString(getRank(p)));
                msg = msg.replace("[kills]", Long.toString(getKills(p)));
                msg = msg.replace("[deaths]", Long.toString(getDeaths(p)));
                msg = msg.replace("[coins]", Long.toString(getCoins(p)));
                msg = msg.replace("[points]", Long.toString(getPoints(p)));
                msg = msg.replace("[kits]", getKitsAsString(p));

                toSend.sendMessage("§8---===XXX===---\n" + msg + "§8---===XXX===---");
            }
        }.runTaskLaterAsynchronously(getUhc(), 10);
    }
}
