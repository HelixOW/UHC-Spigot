package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class NineSlotsListener extends SimpleListener {

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NINE_SLOTS))
            return;
        if (e.getPlayer().getInventory().firstEmpty() <= 8)
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NINE_SLOTS))
            return;

        if (e.getWhoClicked().getInventory().firstEmpty() <= 8)
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NINE_SLOTS))
            return;
        if (e.getClickedInventory() == null)
            return;

        if (e.getSlot() > 8 && e.getSlot() < 36 && !e.isShiftClick())
            e.setCancelled(true);

    }
}
