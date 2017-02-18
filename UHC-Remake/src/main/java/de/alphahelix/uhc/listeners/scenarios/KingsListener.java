package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class KingsListener extends SimpleListener {

    public KingsListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.KINGS))
            return;
        if (getUhc().isTeams())
            UHCRegister.getTeamManagerUtil().splitPlayersIntoTeams();

        for (UHCTeam team : UHCRegister.getTeamManagerUtil().getTeams()) {
            Player king = team.getPlayers().get(new Random().nextInt(team.getPlayers().size()));

            king.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 2));
            king.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2));
            king.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 2));
        }
    }
}
