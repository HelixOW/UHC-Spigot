package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class OneHealListener extends SimpleListener {

    private ArrayList<String> healed = new ArrayList<>();

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.ONE_HEAL))
            return;

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.getInventory().addItem(new ItemStack(Material.GOLD_HOE));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.ONE_HEAL))
            return;
        if (healed.contains(e.getPlayer().getName()))
            return;
        if (!e.getAction().name().contains("RIGHT"))
            return;

        if (e.getPlayer().getInventory().getItemInHand().getType().equals(Material.GOLD_HOE)) {
            healed.add(e.getPlayer().getName());
            e.getPlayer().setHealth(20.0);
            e.getPlayer().getInventory().remove(Material.GOLD_HOE);
        }
    }
}
