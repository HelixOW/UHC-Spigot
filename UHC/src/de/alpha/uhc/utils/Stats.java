package de.alpha.uhc.utils;

import org.bukkit.entity.Player;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class Stats {

    private Player p;
    private Core pl;
    private boolean isMysql;
    private Registery r;
    
    
    public Stats(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
		this.isMysql = pl.isMySQLActive();
	}

    /**
     * @param killer to add/remove coins
     */
    public Stats(Player killer) {
        p = killer;
    }

    public int getCoins() {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Coins").toString();
            return Integer.parseInt(sql);
        }
        return r.getPlayerFile().getPlayerCoins(p);
    }

    public int getKills() {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kills").toString();
            return Integer.parseInt(sql);
        }
        return r.getPlayerFile().getPlayerKills(p);
    }

    public int getDeaths() {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Deaths").toString();
            return Integer.parseInt(sql);
        }
        return r.getPlayerFile().getPlayerDeaths(p);
    }

    public String getKits() {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kits").toString();
            sql = sql.replaceAll(",", "");
            return sql;
        }
        return r.getPlayerFile().getPlayerKits(p);
    }

    public void addKit(String kit) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kits", getKits() + kit + " ,");
            return;
        }
        r.getPlayerFile().addPlayerKit(p, kit);
    }


    public void addKill() {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kills", Integer.toString(getKills() + 1));
            return;
        }
        r.getPlayerFile().addPlayerKill(p);
    }

    public void addDeath() {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Deaths", Integer.toString(getDeaths() + 1));
        }
        r.getPlayerFile().addPlayerDeath(p);
    }

    public void addCoins(int amount) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins", Integer.toString(getCoins() + amount));
        }
        r.getPlayerFile().addPlayerCoins(p, amount);
    }

    public void removeCoins(int amount) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins", Integer.toString(getCoins() - amount));
        }
        r.getPlayerFile().removePlayerCoins(p, amount);
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
