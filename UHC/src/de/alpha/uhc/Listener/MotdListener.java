package de.alpha.uhc.Listener;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdListener implements Listener {
	
	private Core pl;
	
	public MotdListener(Core c) {
		this.pl = c;
	}

    private static String lobby;
    private static String grace;
    private static String ingame;
    private static String restart;
    private static boolean custommotd;

    public static void setLobby(String lobby) {
        MotdListener.lobby = lobby;
    }

    public static void setGrace(String grace) {
        MotdListener.grace = grace;
    }

    public static void setIngame(String ingame) {
        MotdListener.ingame = ingame;
    }

    public static void setRestart(String restart) {
        MotdListener.restart = restart;
    }

    public static void setCustommotd(boolean custommotd) {
        MotdListener.custommotd = custommotd;
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {

        if (!custommotd) return;

        if (GState.isState(GState.LOBBY)) {
            e.setMotd(lobby);
        } else if (GState.isState(GState.INGAME)) {
            e.setMotd(ingame);
        } else if (GState.isState(GState.RESTART)) {
            e.setMotd(restart);
        } else if (GState.isState(GState.GRACE)) {
            e.setMotd(grace);
        }

    }
}
