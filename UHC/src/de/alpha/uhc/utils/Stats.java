package de.alpha.uhc.utils;

import org.bukkit.entity.Player;

import de.alpha.uhc.Core;
import de.alpha.uhc.files.PlayerFileManager;
import net.minetopix.mysqlapi.MySQLManager;

public class Stats {
	
	private Player p;
	private boolean isMysql;
	
	
	public Stats(Player killer) {
		p = killer;
		isMysql = Core.isMySQLActive;
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
	
	public void sendStats() {
		
		p.sendMessage("§8---===XXX===---\n"
				    + "§6Player§7: " + p.getDisplayName() + "\n"
				    + "§6Kills§7: §a"+ new Stats(p).getKills() + "\n"
				    + "§6Deaths§7: §c"+ new Stats(p).getDeaths() + "\n"
				    + "§8---===XXX===---");
		
	}
	

}
