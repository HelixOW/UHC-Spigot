package de.alpha.uhc.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class Stats {

    private Core pl;
    private boolean isMysql;
    private Registery r;
    
    private String player;
    private String kills;
    private String deaths;
    private String coins;
    private String points;
    private String kits;
    
    public Stats(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
		this.isMysql = pl.isMySQLActive();
	}
    
    public void setMySQL(boolean check) {
    	this.isMysql = check;
    }

    public int getCoins(Player p) {
        if (isMysql) {
            String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Coins").toString();
            return Integer.parseInt(sql);
        }
        return r.getPlayerFile().getPlayerCoins(p);
    }
    
    public int getPoints(OfflinePlayer p) {
    	if(isMysql) {
    		String sql = MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "Points").toString();
    		return Integer.parseInt(sql);
    	}
    	return r.getPlayerFile().getPlayerPoints(p);
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
    
    public void addPoints(int amount, OfflinePlayer p) {
    	if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Points", Integer.toString(getPoints(p) + amount));
        }
        r.getPlayerFile().addPlayerPoints(p, amount);
    }

    public void removeCoins(int amount, Player p) {
        if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Coins", Integer.toString(getCoins(p) - amount));
        }
        r.getPlayerFile().removePlayerCoins(p, amount);
    }
    
    public void removePoints(int amount, OfflinePlayer p) {
    	if (isMysql) {
            MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Points", Integer.toString(getPoints(p) - amount));
        }
        r.getPlayerFile().removePlayerPoints(p, amount);
    }

    public void sendStats(Player p) {

        p.sendMessage("§8---===XXX===---\n"
                + getPlayerM() + p.getDisplayName() + "\n"
                + getKillsM() + getKills(p) + "\n"
                + getDeathsM() + getDeaths(p) + "\n"
                + getCoinsM() + getCoins(p) + "\n"
                + getPointsM() + getPoints(p) + "\n"
                + getKitsM() + getKits(p) + "\n"
                + "§8---===XXX===---");
    }

	public String getPlayerM() {
		return player;
	}

	public void setPlayerM(String player) {
		this.player = player;
	}

	public String getKillsM() {
		return kills;
	}

	public void setKillsM(String kills) {
		this.kills = kills;
	}

	public String getDeathsM() {
		return deaths;
	}

	public void setDeathsM(String deaths) {
		this.deaths = deaths;
	}

	public String getCoinsM() {
		return coins;
	}

	public void setCoinsM(String coins) {
		this.coins = coins;
	}

	public String getPointsM() {
		return points;
	}

	public void setPointsM(String points) {
		this.points = points;
	}

	public String getKitsM() {
		return kits;
	}

	public void setKitsM(String kits) {
		this.kits = kits;
	}
}
