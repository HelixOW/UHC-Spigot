package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class HealthDonorListener extends SimpleListener {

    public HealthDonorListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.HEALTH_DONOR))
            return;

        for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
            p.getInventory().addItem(new ItemStack(Material.END_ROD));
        }
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.HEALTH_DONOR))
            return;
        if (!(e.getRightClicked() instanceof Player))
            return;
        if (!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.END_ROD))
            return;
        Player receiver = (Player) e.getRightClicked();

        if (receiver.getHealth() >= 19.5)
            return;
        if (e.getPlayer().getHealth() < 1.5)
            return;

        e.getPlayer().damage(2.0);
        receiver.setHealth(receiver.getHealth() + 2.0);
    }
}
