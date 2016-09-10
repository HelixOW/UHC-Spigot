package de.alphahelix.uhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.SimpleListener;

public class ChatListener extends SimpleListener {

	public ChatListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		e.setCancelled(true);

		if (getRegister().getPlayerUtil().isDead(p)) {
			for (String dead : getRegister().getPlayerUtil().getDeads()) {
				Bukkit.getPlayer(dead).sendMessage(getRegister().getMessageFile().getColorString("Spectator Prefix")
						+ p.getDisplayName() + "§8: " + e.getMessage());
			}
		}

		if (GState.isState(GState.IN_GAME) || GState.isState(GState.PERIOD_OF_PEACE) || GState.isState(GState.END)) {
			for (String survivor : getRegister().getPlayerUtil().getSurvivors()) {
				if (!e.getMessage().startsWith("#")) {
					Bukkit.getPlayer(survivor)
							.sendMessage(getUhc().getPrefix() + p.getDisplayName() + "§8: " + e.getMessage());
				}
			}
		} else {
			for (String all : getRegister().getPlayerUtil().getAll()) {
				Bukkit.getPlayer(all).sendMessage(getUhc().getPrefix() + p.getDisplayName() + "§8: " + e.getMessage());
			}
		}
	}

	@EventHandler
	public void onTeamChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);

		// TODO: Teams
	}

	@EventHandler
	public void onAchivementAward(PlayerAchievementAwardedEvent e) {
		e.setCancelled(true);
	}
}
