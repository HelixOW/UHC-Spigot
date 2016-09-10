package de.alphahelix.uhc.timers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.Util;

public class LobbyTimer extends Util {

	private BukkitTask timer;
	private BukkitTask lobby;
	private int time;

	public LobbyTimer(UHC uhc) {
		super(uhc);
	}

	public void startLobbyCountdown() {
		if (GState.isState(GState.LOBBY)) {

			timer = new BukkitRunnable() {
				public void run() {

					if (time > 0) {
						time --;

						lobby = new BukkitRunnable() {
							public void run() {
								if(getRegister().getPlayerUtil().getAll().size() >= getRegister().getPlayerUtil().getMinimumPlayerCount()) {
									for(String pName : getRegister().getPlayerUtil().getAll()) {
										Player p = Bukkit.getPlayer(pName);
										
										p.setLevel(time);
										
										if(time % 10 == 0 && time > 10 && time != 0) {
											p.sendMessage(getUhc().getPrefix() + getRegister().getMessageFile().getColorString("Lobby time left info"));
											
										}
									}
								}
							}
						}.runTask(getUhc());
					}
				}
			}.runTaskTimer(getUhc(), 0, 20);

		}
	}

}
