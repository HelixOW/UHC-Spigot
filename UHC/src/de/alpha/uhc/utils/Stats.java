package de.alpha.uhc.utils;

import de.alpha.uhc.Core;
import de.alpha.uhc.files.PlayerFileManager;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.mysql.MySQLManager;
import org.bukkit.entity.Player;

public class Stats {

    private final Player p;
    private final boolean isMysql;

    /**
     * @param killer to add/remove coins
     */
    public Stats(Player killer) {
        p = killer;
        isMysql = Core.isMySQLActive();
    }

    public int getCoins() {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Coins").toString();
            return Integer.parseInt(sql);
        }
        return new PlayerFileManager().getPlayerCoins(p);
    }

    public int getKills() {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kills").toString();
            return Integer.parseInt(sql);
        }
        return new PlayerFileManager().getPlayerKills(p);
    }

    public int getDeaths() {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Deaths").toString();
            return Integer.parseInt(sql);
        }
        return new PlayerFileManager().getPlayerDeaths(p);
    }

    public String getKits() {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kits").toString();
            sql = sql.replaceAll(",", "");
            return sql;
        }
        return new PlayerFileManager().getPlayerKits(p);
    }

    public void addKit(String kit) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kits", getKits() + kit + " ,");
            return;
        }
        new PlayerFileManager().addPlayerKit(p, kit);
    }


    public void addKill() {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kills", Integer.toString(getKills() + 1));
            return;
        }
        new PlayerFileManager().addPlayerKill(p);
    }

    public void addDeath() {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Deaths", Integer.toString(getDeaths() + 1));
        }
        new PlayerFileManager().addPlayerDeath(p);
    }

    public void addCoins(int amount) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins", Integer.toString(getCoins() + amount));
        }
        new PlayerFileManager().addPlayerCoins(p, amount);
    }

    public void removeCoins(int amount) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins", Integer.toString(getCoins() - amount));
        }
        new PlayerFileManager().removePlayerCoins(p, amount);
    }

    public void sendStats() {

        p.sendMessage("§8---===XXX===---\n"
                + "§6Player§7: " + p.getDisplayName() + "\n"
                + "§6Kills§7: §a" + new Stats(p).getKills() + "\n"
                + "§6Deaths§7: §c" + new Stats(p).getDeaths() + "\n"
                + "§6Coins§7: §c" + new Stats(p).getCoins() + "\n"
                + "§6Kits§7: §c" + new Stats(p).getKits() + "\n"
                + "§8---===XXX===---");
    }
}
