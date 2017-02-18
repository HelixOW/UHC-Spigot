package de.alphahelix.uhc.listeners;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ConfirmListener extends SimpleListener {

    public ConfirmListener(UHC uhc) {
        super(uhc);
    }

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

                    UHCRegister.getStatsUtil().removePoints(
                            p,
                            (int) UHCRegister.getKitChooseListener().getKitWhichPlayerWantToBuy(p).getPrice());

                    UHCRegister.getStatsUtil().addKit(
                            UHCRegister.getKitChooseListener().getKitWhichPlayerWantToBuy(p),
                            p);

                    String msg = UHCFileRegister.getMessageFile().getKitChosen(UHCRegister.getKitChooseListener().getKitWhichPlayerWantToBuy(p));

                    UHCFileRegister.getKitsFile().setPlayedKit(
                            p,
                            UHCRegister.getKitChooseListener().getKitWhichPlayerWantToBuy(p));

                    e.getWhoClicked().closeInventory();

                    UHCRegister.getScoreboardUtil().updateKit(
                            p,
                            UHCRegister.getKitChooseListener().getKitWhichPlayerWantToBuy(p));
                    e.getWhoClicked().sendMessage(getUhc().getPrefix() + msg);

                } else if (e.getCurrentItem().getItemMeta().getDisplayName()
                        .equals(UHCFileRegister.getConfirmFile().getItem(false).getItemStack().getItemMeta().getDisplayName())) {
                    e.getWhoClicked().closeInventory();
                    UHCRegister.getKitInventory().openInventory(p);
                }
            }
        }
    }
}
