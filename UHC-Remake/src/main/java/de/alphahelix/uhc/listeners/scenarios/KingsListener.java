package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.util.TeamManagerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class KingsListener extends SimpleListener {


    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.KINGS))
            return;
        if (UHC.isTeams())
            TeamManagerUtil.splitPlayersIntoTeams();

        for (UHCTeam team : TeamManagerUtil.getTeams()) {
            Player king = team.getPlayers().get(new Random().nextInt(team.getPlayers().size()));

            king.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 2));
            king.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2));
            king.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 2));
        }
    }
}
