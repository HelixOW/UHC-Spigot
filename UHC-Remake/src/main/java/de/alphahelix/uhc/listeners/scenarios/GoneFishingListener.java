package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.item.ItemBuilder;
import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class GoneFishingListener extends SimpleListener {

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.GONE_FISHIN))
            return;

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.setLevel(Integer.MAX_VALUE);
            p.getInventory().addItem(new ItemStack(Material.ANVIL, 20), new ItemBuilder(Material.FISHING_ROD)
                    .addEnchantment(Enchantment.LURE, 250).addEnchantment(Enchantment.DURABILITY, 150).build());
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.GONE_FISHIN)) return;

        if (e.getRecipe().getResult().getType().equals(Material.ENCHANTMENT_TABLE)) e.setCancelled(true);
    }

    @EventHandler
    public void onInvOpen(InventoryOpenEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.GONE_FISHIN)) return;

        if (e.getInventory().getType().equals(InventoryType.ENCHANTING)) e.setCancelled(true);
    }
}
