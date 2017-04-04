package de.alphahelix.uhc.listeners.scenarios;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class BowfighterListener extends SimpleListener {

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BOWFIGHTERS))
            return;
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();

        meta.addStoredEnchant(Enchantment.ARROW_INFINITE, 1, true);

        book.setItemMeta(meta);

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.getInventory().addItem(new ItemStack(Material.ARROW), new ItemStack(Material.STRING, 2),
                    book);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BOWFIGHTERS))
            return;

        if (e.getRecipe().getResult().getType().name().contains("SWORD")
                && !e.getRecipe().getResult().getType().equals(Material.WOOD_SWORD))
            e.setCancelled(true);
        else if (e.getRecipe().getResult().getType().name().contains("AXE")
                && !(e.getRecipe().getResult().getType().equals(Material.STONE_AXE)
                || e.getRecipe().getResult().getType().equals(Material.WOOD_AXE)))
            e.setCancelled(true);
    }

}
