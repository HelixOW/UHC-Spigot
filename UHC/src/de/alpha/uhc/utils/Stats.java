package de.alpha.uhc.utils;

import org.bukkit.entity.Player;

import de.alpha.uhc.Core;
import de.alpha.uhc.files.PlayerFileManager;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class Stats {
	
	private Player p;
	private boolean isMysql;
	
	
	public Stats(Player killer) {
		p = killer;
		isMysql = Core.isMySQLActive();
	}
	
	public int getCoins() {
		if(isMysql == true) {
			String sql = MySQLManager.getObjectConditionResult("UHC", "UUID", p.getUniqueId().toString(), "Coins").toString();
			return Integer.parseInt(sql);
		}
		return new PlayerFileManager().getPlayerCoins(p);
	}
	
	public int getKills() {
		if(isMysql == true) {
			String sql = MySQLManager.getObjectConditionResult("UHC", "UUID", p.getUniqueId().toString(), "Kills").toString();
			return Integer.parseInt(sql);
		}
		return new PlayerFileManager().getPlayerKills(p);
	}
	
	public int getDeaths() {
		if(isMysql == true) {
			String sql = MySQLManager.getObjectConditionResult("UHC", "UUID", p.getUniqueId().toString(), "Deaths").toString();
			return Integer.parseInt(sql);
		}
		return new PlayerFileManager().getPlayerDeaths(p);
	}
	
	public String getKits() {
		if(isMysql == true) {
			String sql = MySQLManager.getObjectConditionResult("UHC", "UUID", p.getUniqueId().toString(), "Kits").toString();
			sql = sql.replaceAll(",", "");
			return sql;
		}
		return new PlayerFileManager().getPlayerKits(p);
	}
	
	public void addKit(String kit) {
		if(isMysql == true) {
			MySQLManager.exUpdateQry("UHC", "UUID", p.getUniqueId().toString(), "Kits", getKits()+kit+" ,");
			return;
		}
		new PlayerFileManager().addPlayerKit(p, kit);
	}
	
	
	public void addKill() {
		if(isMysql == true) {
			MySQLManager.exUpdateQry("UHC", "UUID", p.getUniqueId().toString(), "Kills", Integer.toString(getKills()+1));
			return;
		}
		new PlayerFileManager().addPlayerKill(p);
	}
	
	public void addDeath() {
		if(isMysql == true) {
			MySQLManager.exUpdateQry("UHC", "UUID", p.getUniqueId().toString(), "Deaths", Integer.toString(getDeaths()+1));
		}
		new PlayerFileManager().addPlayerDeath(p);
	}
	
	public void addCoins(int amount) {
		if(isMysql == true) {
			MySQLManager.exUpdateQry("UHC", "UUID", p.getUniqueId().toString(), "Coins", Integer.toString(getCoins()+amount));
		}
		new PlayerFileManager().addPlayerCoins(p, amount);
	}
	
	public void removeCoins(int amount) {
		if(isMysql == true) {
			MySQLManager.exUpdateQry("UHC", "UUID", p.getUniqueId().toString(), "Coins", Integer.toString(getCoins()-amount));
		}
		new PlayerFileManager().removePlayerCoins(p, amount);
	}
	
	public void sendStats() {
		
		p.sendMessage("§8---===XXX===---\n"
				    + "§6Player§7: " + p.getDisplayName() + "\n"
				    + "§6Kills§7: §a"+ new Stats(p).getKills() + "\n"
				    + "§6Deaths§7: §c"+ new Stats(p).getDeaths() + "\n"
				    + "§6Coins§7: §c"+ new Stats(p).getCoins() + "\n"
				    + "§6Kits§7: §c"+ new Stats(p).getKits() + "\n"
				    + "§8---===XXX===---");
		
	}
	

}
