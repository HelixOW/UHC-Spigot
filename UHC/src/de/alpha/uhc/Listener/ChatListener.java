package de.alpha.uhc.Listener;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.aclasses.ATeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        e.setCancelled(true);

        if (Core.getSpecs().contains(e.getPlayer())) {
            for (Player specs : Core.getSpecs()) {
                specs.sendMessage("§7[§4X§7] §c" + e.getPlayer().getDisplayName() + "§7: " + e.getMessage());
            }
            return;
        }

        if (GState.isState(GState.INGAME) || GState.isState(GState.GRACE) || GState.isState(GState.RESTART)) {
            for (Player all : Core.getInGamePlayers()) {
                if (!(e.getMessage().startsWith("#"))) {
                    all.sendMessage(Core.getPrefix() + " " + e.getPlayer().getDisplayName() + "§7: " + e.getMessage());
                }
            }
        } else {
            Bukkit.broadcastMessage(Core.getPrefix() + " " + e.getPlayer().getDisplayName() + "§7: " + e.getMessage());
        }
    }

    @EventHandler
    public void onTeamChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        for (Player all : Core.getInGamePlayers()) {
            if (e.getMessage().startsWith("#") && ATeam.hasSameTeam(e.getPlayer(), all)) {
                all.sendMessage(Core.getPrefix() + "§7[" + ATeam.getTeamColor(ATeam.getPlayerTeam(e.getPlayer())) + ATeam.getPlayerTeam(e.getPlayer()) + "§7] " + e.getPlayer().getDisplayName() + " §7: " + e.getMessage().replaceFirst("#", ""));
            }
        }
    }
    
    @EventHandler
    public void onReward(PlayerAchievementAwardedEvent e) {
    	e.setCancelled(true);
    }

}
