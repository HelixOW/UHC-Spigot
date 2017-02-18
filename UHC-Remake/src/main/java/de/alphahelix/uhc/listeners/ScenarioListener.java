package de.alphahelix.uhc.listeners;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ScenarioListener extends SimpleListener {

    public ScenarioListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getPlayer().getInventory().getItemInHand() == null)
            return;
        if (!(getUhc().isScenarios() || getUhc().isScenarioVoting()))
            return;

        if (e.getPlayer().getInventory().getItemInHand().getType()
                == UHCFileRegister.getScenarioFile().getItem(null).getItemStack().getType()
                &&

                ChatColor.stripColor(e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName())
                .equals(ChatColor
                        .stripColor(UHCFileRegister.getScenarioFile().getItem(null).getItemStack().getItemMeta().getDisplayName()))) {
            e.setCancelled(true);
            UHCRegister.getScenarioInventory().openInventory(e.getPlayer());
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() == null)
            return;
        if (!ChatColor.stripColor(e.getClickedInventory().getTitle())
                .equals(ChatColor.stripColor(UHCFileRegister.getScenarioFile().getInventoryName())))
            return;

        e.setCancelled(true);

        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(" "))
            return;

        UHCRegister.getScenarioInventory().castVote((Player) e.getWhoClicked(),
                UHCFileRegister.getScenarioFile().getScenarioByItem(e.getCurrentItem()));
    }

    @EventHandler
    public void onInvClickInManagement(InventoryClickEvent e) {
        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() == null)
            return;
        if (!ChatColor.stripColor(e.getInventory().getTitle()).equals("Change Scenarios"))
            return;

        e.setCancelled(true);

        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(" "))
            return;

        UHCFileRegister.getScenarioFile().toggleScenarioStatus(UHCFileRegister.getScenarioFile().getScenarioByItem(e.getCurrentItem()));
        e.getWhoClicked().closeInventory();
        UHCRegister.getScenarioAdminInventory().fillInventory((Player) e.getWhoClicked());
    }
}
