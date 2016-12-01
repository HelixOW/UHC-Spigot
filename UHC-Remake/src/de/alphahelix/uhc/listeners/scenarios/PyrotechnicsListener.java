package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class PyrotechnicsListener extends SimpleListener {

    public PyrotechnicsListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.PYROTECHNICS)) return;

        for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
            p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.isCancelled()) return;
        if (!scenarioCheck(Scenarios.PYROTECHNICS)) return;
        if (!(e.getEntity() instanceof Player)) return;

        if (!e.getCause().name().contains("FIRE")) e.getEntity().setFireTicks(20 * 8);
    }

}
