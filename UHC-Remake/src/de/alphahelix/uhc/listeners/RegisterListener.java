package de.alphahelix.uhc.listeners;

import de.alphahelix.alphalibary.UUID.UUIDFetcher;
import de.alphahelix.alphalibary.mysql.MySQLManager;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.UHCCrateRarerity;
import de.alphahelix.uhc.instances.PlayerInfo;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class RegisterListener extends SimpleListener {

    public RegisterListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        PlayerInfo playerInfo = new PlayerInfo(p, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", "");
        StatsUtil su = getRegister().getStatsUtil();

        if (getUhc().isMySQLMode()) {
            if (MySQLManager.containsPlayer(p)) {
                MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Player", p.getName());
                playerInfo = new PlayerInfo(
                        p,
                        su.getKills(p),
                        su.getDeaths(p),
                        su.getCoins(p),
                        su.getPoints(p),
                        su.getWins(p),
                        su.getCrateCount(UHCCrateRarerity.NORMAL, p),
                        su.getCrateCount(UHCCrateRarerity.UNCOMMON, p),
                        su.getCrateCount(UHCCrateRarerity.RARE, p),
                        su.getCrateCount(UHCCrateRarerity.SUPERRARE, p),
                        su.getCrateCount(UHCCrateRarerity.EPIC, p),
                        su.getCrateCount(UHCCrateRarerity.LEGENDARY, p),
                        su.getKitsAsString(p),
                        su.getAchievementsAsString(p));
            } else {
                MySQLManager.exInsertQry(
                        p.getName(),                                     //Playername
                        UUIDFetcher.getUUID(p.getName()).toString(),     //UUID
                        "0",                                             //Kills
                        "0",                                             //Deaths
                        "0",                                             //Coins
                        "0",                                             //Points
                        "0",                                             //Wins
                        Integer.toString(MySQLManager.getCountNumber("uhc") + 1), //Count
                        " ",                                             //Kits
                        " ",                                             //Achievements
                        "0",                                              //Common Crates
                        "0",                                              //Uncommon Crates
                        "0",                                              //Rare Crates
                        "0",                                              //Superrare Crates
                        "0",                                              //Epic Crates
                        "0"                                               //Legendary Crates
                );
            }
        } else if (!getRegister().getPlayerFile().containsPlayer(p)) {
            getRegister().getPlayerFile().addPlayer(p);
        }
        getRegister().getPlayerUtil().setPlayerInfo(p, playerInfo);
    }
}
