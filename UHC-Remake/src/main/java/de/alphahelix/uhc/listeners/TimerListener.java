package de.alphahelix.uhc.listeners;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class TimerListener extends SimpleListener {

    public TimerListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UHCRegister.getLobbyTimer().startLobbyCountdown();
    }
}
