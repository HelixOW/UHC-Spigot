package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;

public class PuppyPowerListener extends SimpleListener {

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.PUPPY_POWER))
            return;

        ItemStack spawnEgg = new SpawnEgg(EntityType.WOLF).toItemStack(12);

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.getInventory().addItem(new ItemStack(Material.BONE, 64), new ItemStack(Material.ROTTEN_FLESH, 64), spawnEgg);
        }
    }

}
