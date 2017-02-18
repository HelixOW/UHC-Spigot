package de.alphahelix.uhc.listeners;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

public class ChatListener extends SimpleListener {

    public ChatListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        e.setCancelled(true);

        if (UHCRegister.getPlayerUtil().isDead(p)) {
            for (String dead : UHCRegister.getPlayerUtil().getDeads()) {
                Bukkit.getPlayer(dead).sendMessage(UHCFileRegister.getOptionsFile().getSpectatorPrefix()
                        + UHCRegister.getStatsUtil().getUHCRank(p).getPrefix() + p.getDisplayName() + "§8: " + e.getMessage());
            }
        }

        if (GState.isState(GState.IN_GAME) || GState.isState(GState.PERIOD_OF_PEACE) || GState.isState(GState.END)) {
            for (String survivor : UHCRegister.getPlayerUtil().getSurvivors()) {
                if (!e.getMessage().startsWith(UHCFileRegister.getTeamFile().getTeamchatSymbol())) {
                    if (Bukkit.getPlayer(survivor) != null) {
                        Bukkit.getPlayer(survivor).sendMessage(getUhc().getPrefix() + UHCRegister.getStatsUtil().getUHCRank(p).getPrefix() + p.getDisplayName() + "§8: " + e.getMessage());
                    }
                }
            }
        } else {
            for (String all : UHCRegister.getPlayerUtil().getAll()) {
                Bukkit.getPlayer(all).sendMessage(getUhc().getPrefix() + UHCRegister.getStatsUtil().getUHCRank(p).getPrefix() + p.getDisplayName() + "§8: " + e.getMessage());
            }
        }

        if (e.getMessage().startsWith(UHCFileRegister.getTeamFile().getTeamchatSymbol())) {
            for (Player teamler : UHCRegister.getTeamManagerUtil().getTeamByPlayer(p).getPlayers()) {
                teamler.sendMessage(getUhc().getPrefix() + UHCRegister.getStatsUtil().getUHCRank(p).getPrefix() + p.getDisplayName() + "§8: " + e.getMessage().substring(1, e.getMessage().length()));
            }
        }
    }

    @EventHandler
    public void onAchivementAward(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }
}
