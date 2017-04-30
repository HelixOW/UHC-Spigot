package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class PyrotechnicsListener extends SimpleListener {

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.PYROTECHNICS)) return;

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.PYROTECHNICS)) return;
        if (!(e.getEntity() instanceof Player)) return;

        if (!e.getCause().name().contains("FIRE")) e.getEntity().setFireTicks(20 * 8);
    }

}
