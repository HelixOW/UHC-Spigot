package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CatsEyesListener extends SimpleListener {

    public CatsEyesListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.CATEYES)) return;

        for (Player p : makeArray(UHCRegister.getPlayerUtil().getSurvivors())) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999999, 1), true);
        }
    }
}
