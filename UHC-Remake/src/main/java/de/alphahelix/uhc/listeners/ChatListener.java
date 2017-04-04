package de.alphahelix.uhc.listeners;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.StatsUtil;
import de.alphahelix.uhc.util.TeamManagerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import java.util.UUID;

public class ChatListener extends SimpleListener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        UUID id = UUIDFetcher.getUUID(p);

        e.setCancelled(true);

        if (PlayerUtil.isDead(p)) {
            for (String dead : PlayerUtil.getDeads()) {
                Bukkit.getPlayer(dead).sendMessage(UHCFileRegister.getOptionsFile().getSpectatorPrefix()
                        + StatsUtil.getUHCRank(id).getPrefix() + p.getDisplayName() + "§8: " + e.getMessage());
            }
        }

        if (GState.isState(GState.IN_GAME) || GState.isState(GState.PERIOD_OF_PEACE) || GState.isState(GState.END)) {
            for (String survivor : PlayerUtil.getSurvivors()) {
                if (!e.getMessage().startsWith(UHCFileRegister.getTeamFile().getTeamchatSymbol())) {
                    if (Bukkit.getPlayer(survivor) != null) {
                        Bukkit.getPlayer(survivor).sendMessage(UHC.getPrefix() + StatsUtil.getUHCRank(id).getPrefix() + p.getDisplayName() + "§8: " + e.getMessage());
                    }
                }
            }
        } else {
            for (String all : PlayerUtil.getAll()) {
                Bukkit.getPlayer(all).sendMessage(UHC.getPrefix() + StatsUtil.getUHCRank(id).getPrefix() + p.getDisplayName() + "§8: " + e.getMessage());
            }
        }

        if (e.getMessage().startsWith(UHCFileRegister.getTeamFile().getTeamchatSymbol())) {
            for (Player teamler : TeamManagerUtil.getTeamByPlayer(p).getPlayers()) {
                teamler.sendMessage(UHC.getPrefix() + StatsUtil.getUHCRank(id).getPrefix() + p.getDisplayName() + "§8: " + e.getMessage().substring(1, e.getMessage().length()));
            }
        }
    }

    @EventHandler
    public void onAchivementAward(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }
}
