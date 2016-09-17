package de.alphahelix.uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.util.PlayerData;
import de.alphahelix.uhc.util.StatsUtil;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.mysql.MySQLAPI;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class RegisterListener extends SimpleListener {

	public RegisterListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(getUhc().isMySQLMode()) {
			if (MySQLManager.containsPlayer(p)) {
				MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString() , "Player", p.getName());
			} else {
				MySQLManager.exInsertQry(p.getName(), UUIDFetcher.getUUID(p.getName()).toString(), "0", "0", "0", "0", Integer.toString(MySQLAPI.getCountNumber() + 1), "0");
			}
		} else {
			getRegister().getPlayerFile().addPlayer(p);
		}
		
		StatsUtil su = getRegister().getStatsUtil();
		
		PlayerData pd = new PlayerData(su.getKills(p), su.getDeaths(p), su.getCoins(p), su.getPoints(p), su.getRank(p), su.getKitsAsList(p));
		
		su.initalizeCache(p, pd);
	}
}
