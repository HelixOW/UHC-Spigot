package de.alphahelix.uhc.listeners;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.ScoreboardUtil;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ConfirmListener extends SimpleListener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() == null)
            return;

        if (!e.getClickedInventory().getTitle().contains(UHCFileRegister.getConfirmFile().getInventoryName()))
            return;

        e.setCancelled(true);

        if (e.getCurrentItem().hasItemMeta()) {

            if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

                Player p = (Player) e.getWhoClicked();

                if (e.getCurrentItem().getItemMeta().getDisplayName()
                        .equals(UHCFileRegister.getConfirmFile().getItem(true).getItemStack().getItemMeta().getDisplayName())) {

                    StatsUtil.removeCoins(
                            UUIDFetcher.getUUID(p),
                            (int) UHCRegister.getKitChooseListener().getKitWhichPlayerWantToBuy(p).getPrice());

                    ScoreboardUtil.updateCoins(p, StatsUtil.getCoins(UUIDFetcher.getUUID(p)));


                    StatsUtil.addKits(
                            UUIDFetcher.getUUID(p),
                            UHCRegister.getKitChooseListener().getKitWhichPlayerWantToBuy(p));

                    String msg = UHCFileRegister.getMessageFile().getKitChosen(UHCRegister.getKitChooseListener().getKitWhichPlayerWantToBuy(p));

                    UHCFileRegister.getKitsFile().setPlayedKit(
                            p,
                            UHCRegister.getKitChooseListener().getKitWhichPlayerWantToBuy(p));

                    e.getWhoClicked().closeInventory();

                    ScoreboardUtil.updateKit(
                            p,
                            UHCRegister.getKitChooseListener().getKitWhichPlayerWantToBuy(p));
                    e.getWhoClicked().sendMessage(UHC.getPrefix() + msg);

                } else if (e.getCurrentItem().getItemMeta().getDisplayName()
                        .equals(UHCFileRegister.getConfirmFile().getItem(false).getItemStack().getItemMeta().getDisplayName())) {
                    e.getWhoClicked().closeInventory();
                    UHCRegister.getKitInventory().openInventory(p);
                }
            }
        }
    }
}
