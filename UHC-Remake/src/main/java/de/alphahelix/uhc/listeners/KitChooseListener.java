package de.alphahelix.uhc.listeners;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.ScoreboardUtil;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class KitChooseListener extends SimpleListener {

    private HashMap<String, Kit> boughts = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (e.getPlayer().getInventory().getItemInHand() == null)
            return;

        if (!GState.isState(GState.LOBBY))
            return;

        if (e.getPlayer().getInventory().getItemInHand().getType() == UHCFileRegister.getKitsFile().getItem().getItemStack().getType()) {

            if (!UHC.isKits() && UHC.isScenarios()) {
                e.getPlayer().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getScenariomode());
                return;
            }
            UHCRegister.getKitInventory().openInventory(e.getPlayer());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() == null)
            return;

        if (!e.getClickedInventory().getTitle().contains(UHCFileRegister.getKitsFile().getInventoryName()))
            return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        UUID id = UUIDFetcher.getUUID(p);

        for (Kit k : UHCFileRegister.getKitsFile().getKits()) {

            if (e.getCurrentItem().getType().equals(k.getGuiBlock().getType())
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals(k.getName().replace("_", " "))) {

                if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
                    if ((UHCFileRegister.getKitsFile().hasKit(p) && UHCFileRegister.getKitsFile()
                            .getPlayedKit(p).getName().equals(k.getName())))
                        return;

                    if (StatsUtil.hasKit(id, k)) {
                        String msg = UHCFileRegister.getMessageFile().getKitChosen(k);

                        UHCFileRegister.getKitsFile().setPlayedKit(p, k);
                        e.getWhoClicked().closeInventory();

                        ScoreboardUtil.updateKit(p, k);
                        e.getWhoClicked().sendMessage(UHC.getPrefix() + msg);
                    } else if (StatsUtil.getCoins(id) >= k.getPrice()) {
                        boughts.put(e.getWhoClicked().getName(), k);

                        UHCRegister.getConfirmInventory().openInventory(p);
                    } else {
                        e.getWhoClicked().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile()
                                .getNotEnoughCoins(k));
                        return;
                    }
                } else {
                    if (k.getName().replace("_", " ").equals(e.getCurrentItem().getItemMeta().getDisplayName()))
                        UHCRegister.getPreviewInventory().fillInventory(p, k);
                }
            }
        }
    }

    public Kit getKitWhichPlayerWantToBuy(Player p) {
        if (boughts.containsKey(p.getName())) {
            return boughts.get(p.getName());
        }
        return null;
    }
}