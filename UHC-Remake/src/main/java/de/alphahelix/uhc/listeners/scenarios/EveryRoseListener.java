package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class EveryRoseListener extends SimpleListener {


    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.EVERY_ROSE)) return;

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.getInventory().addItem(new ItemBuilder(Material.GOLD_CHESTPLATE).addEnchantment(Enchantment.THORNS, 3).build());
        }
    }
}
