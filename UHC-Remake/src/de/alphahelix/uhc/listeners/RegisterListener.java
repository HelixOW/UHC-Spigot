package de.alphahelix.uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.mysql.MySQLAPI;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class RegisterListener extends SimpleListener {

	public RegisterListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(getUhc().isMySQLMode()) {
			if (MySQLManager.containsPlayer(p)) {
				MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString() , "Player", p.getName());
			} else {
				MySQLManager.exInsertQry(
						p.getName(),                                     //Playername
						UUIDFetcher.getUUID(p.getName()).toString(),     //UUID
						"0",                                             //Kills
						"0",                                             //Deaths
						"0",                                             //Coins
						"0",                                             //Points
						Integer.toString(MySQLAPI.getCountNumber() + 1), //Count
						" ",                                             //Kits
						
						"0",                                              //Common Crates
						"0",                                              //Uncommon Crates
						"0",                                              //Rare Crates
						"0",                                              //Superrare Crates
						"0",                                              //Epic Crates
						"0"                                               //Legendary Crates
						);
			}
		} else {
			getRegister().getPlayerFile().addPlayer(p);
		}
	}
}
