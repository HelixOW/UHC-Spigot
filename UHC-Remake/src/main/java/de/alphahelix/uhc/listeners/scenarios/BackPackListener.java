package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class BackPackListener extends SimpleListener {

    // Player -> BackPack
    private HashMap<String, Inventory> backpacks = new HashMap<>();

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BACKPACKS))
            return;
        if (!e.getAction().name().contains("RIGHT"))
            return;

        Player p = e.getPlayer();

        if (p.getInventory().getItemInHand() == null)
            return;
        if (!(p.getInventory().getItemInHand().hasItemMeta()
                || p.getInventory().getItemInHand().getItemMeta().hasDisplayName()))
            return;

        e.setCancelled(true);

        if (!p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(p.getName())) {
            p.openInventory(backpacks.get(p.getInventory().getItemInHand().getItemMeta().getDisplayName()));
        } else {
            if (!backpacks.containsKey(p.getName())) {
                backpacks.put(p.getName(), Bukkit.createInventory(null, 27,
                        UHCFileRegister.getScenarioFile().getCustomScenarioName(Scenarios.BACKPACKS)));
                p.openInventory(backpacks.get(p.getName()));
            } else {
                p.openInventory(backpacks.get(p.getName()));
            }
        }

        p.updateInventory();
    }

    @EventHandler
    public void onStart(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BACKPACKS))
            return;

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.getInventory().addItem(new ItemBuilder(Material.TRAPPED_CHEST).setName(p.getName()).build());
        }
    }
}
