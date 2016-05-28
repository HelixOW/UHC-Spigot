package de.alpha.uhc.utils;

import org.bukkit.entity.Player;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class Stats {

    private Core pl;
    private boolean isMysql;
    private Registery r;
    
    
    public Stats(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
		this.isMysql = pl.isMySQLActive();
	}

    public int getCoins(Player p) {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Coins").toString();
            return Integer.parseInt(sql);
        }
        return r.getPlayerFile().getPlayerCoins(p);
    }

    public int getKills(Player p) {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kills").toString();
            return Integer.parseInt(sql);
        }
        return r.getPlayerFile().getPlayerKills(p);
    }

    public int getDeaths(Player p) {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Deaths").toString();
            return Integer.parseInt(sql);
        }
        return r.getPlayerFile().getPlayerDeaths(p);
    }

    public String getKits(Player p) {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Kits").toString();
            sql = sql.replaceAll(",", "");
            return sql;
        }
        return r.getPlayerFile().getPlayerKits(p);
    }

    public void addKit(String kit, Player p) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kits", getKits(p) + kit + " ,");
            return;
        }
        r.getPlayerFile().addPlayerKit(p, kit);
    }


    public void addKill(Player p) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Kills", Integer.toString(getKills(p) + 1));
            return;
        }
        r.getPlayerFile().addPlayerKill(p);
    }

    public void addDeath(Player p) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Deaths", Integer.toString(getDeaths(p) + 1));
        }
        r.getPlayerFile().addPlayerDeath(p);
    }

    public void addCoins(int amount, Player p) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins", Integer.toString(getCoins(p) + amount));
        }
        r.getPlayerFile().addPlayerCoins(p, amount);
    }

    public void removeCoins(int amount, Player p) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins", Integer.toString(getCoins(p) - amount));
        }
        r.getPlayerFile().removePlayerCoins(p, amount);
    }

    public void sendStats(Player p) {

        p.sendMessage("§8---===XXX===---\n"
                + "§6Player§7: " + p.getDisplayName() + "\n"
                + "§6Kills§7: §a" + getKills(p) + "\n"
                + "§6Deaths§7: §c" + getDeaths(p) + "\n"
                + "§6Coins§7: §c" + getCoins(p) + "\n"
                + "§6Kits§7: §c" + getKits(p) + "\n"
                + "§8---===XXX===---");
    }
}
