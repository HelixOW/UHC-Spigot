package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.PlayerDummie;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.util.TeamManagerUtil;
import org.bukkit.OfflinePlayer;
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
            OfflinePlayer king = team.getPlayers().get(new Random().nextInt(team.getPlayers().size()));

            if (king.isOnline()) {
                king.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 2));
                king.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2));
                king.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 2));
            } else {
                PlayerDummie pd = PlayerDummie.getDummie(UUIDFetcher.getUUID(king));

                pd.getDummie().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 2));
                pd.getDummie().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2));
                pd.getDummie().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 2));
            }
        }
    }
}
