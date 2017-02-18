package de.alphahelix.uhc.listeners;

import de.alphahelix.alphalibary.mysql.Database;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.PlayerInfo;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class RegisterListener extends SimpleListener {

    public RegisterListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        PlayerInfo playerInfo = new PlayerInfo(p, 0, 0, 0, 0, 0, 0, new ArrayList<Kit>(), new ArrayList<UHCAchievements>(), "");
        StatsUtil su = UHCRegister.getStatsUtil();

        if (getUhc().isMySQLMode()) {
            if (Database.containsPlayer("UHC", p)) {

                Database.update("UHC", UUIDFetcher.getUUID(p.getName()), "Player", p.getName());

                playerInfo = new PlayerInfo(
                        p,
                        su.getGames(p),
                        su.getKills(p),
                        su.getDeaths(p),
                        su.getCoins(p),
                        su.getPoints(p),
                        su.getWins(p),
                        su.getKitsAsList(p),
                        su.getAchievementsAsList(p),
                        su.getCratesAsString(p));
            } else {
                Database.exInsertQry(
                        "UHC",
                        p.getName(),                                     //Playername
                        UUIDFetcher.getUUID(p.getName()).toString(),     //uuid
                        "0",                                             //Games
                        "0",                                             //Kills
                        "0",                                             //Deaths
                        "0",                                             //Coins
                        "0",                                             //Points
                        "0",                                             //Wins
                        "-",                                             //Kits
                        "-",                                             //Achievements
                        "-"                                              //Crates
                );
            }
        } else if (!UHCFileRegister.getPlayerFile().containsPlayer(p)) {
            UHCFileRegister.getPlayerFile().addPlayer(p);
        } else {
            playerInfo = new PlayerInfo(
                    p,
                    su.getGames(p),
                    su.getKills(p),
                    su.getDeaths(p),
                    su.getCoins(p),
                    su.getPoints(p),
                    su.getWins(p),
                    su.getKitsAsList(p),
                    su.getAchievementsAsList(p),
                    su.getCratesAsString(p));
        }
        UHCRegister.getPlayerUtil().setPlayerInfo(p, playerInfo);
    }
}
