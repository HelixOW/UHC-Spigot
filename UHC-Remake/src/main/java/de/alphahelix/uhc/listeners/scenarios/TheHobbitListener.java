package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TheHobbitListener extends SimpleListener {

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.THE_HOBBIT))
            return;

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET));
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.THE_HOBBIT))
            return;
        if (!e.getAction().name().contains("RIGHT"))
            return;

        if (e.getPlayer().getInventory().getItemInHand().getType().equals(Material.GOLD_NUGGET)) {
            e.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (20 * 30), 1), true);
        }
    }
}
