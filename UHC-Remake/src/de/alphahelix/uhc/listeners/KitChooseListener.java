package de.alphahelix.uhc.listeners;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class KitChooseListener extends SimpleListener {

    private HashMap<String, Kit> boughts = new HashMap<>();

    public KitChooseListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (e.getPlayer().getInventory().getItemInHand() == null)
            return;

        if (!GState.isState(GState.LOBBY))
            return;

        if (e.getPlayer().getInventory().getItemInHand().getType()
                .equals(Material.getMaterial(getRegister().getKitsFile().getString("Kit.Item").replace(" ", "_")))) {
            if (!getUhc().isKits() && getUhc().isScenarios()) {
                e.getPlayer().sendMessage(getUhc().getPrefix()
                        + getRegister().getMainOptionsFile().getColorString("Warnings.Scenario Mode"));
                return;
            }
            getRegister().getKitInventory().openInventory(e.getPlayer());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() == null)
            return;

        if (!e.getClickedInventory().getTitle().contains(getRegister().getKitsFile().getColorString("GUI.Name")))
            return;

        e.setCancelled(true);

        for (Kit k : getRegister().getKitsFile().getKits()) {

            if (e.getCurrentItem().getType().equals(k.getGuiBlock().getType())
                    && e.getCurrentItem().getItemMeta().getDisplayName().equals(k.getName().replace("_", " "))) {

                if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
                    if ((getRegister().getKitsFile().hasKit((Player) e.getWhoClicked()) && getRegister().getKitsFile()
                            .getKitByPlayer((Player) e.getWhoClicked()).getName().equals(k.getName())))
                        return;

                    if (getRegister().getStatsUtil().hasKit(k, (Player) e.getWhoClicked())) {
                        String msg = getRegister().getMessageFile().getColorString("Kit chosen").replace("[kit]",
                                k.getName().replace("_", " "));

                        getRegister().getKitsFile().setKit((Player) e.getWhoClicked(), k);
                        e.getWhoClicked().closeInventory();
                        getRegister().getScoreboardUtil().updateKit((Player) e.getWhoClicked(), k);
                        e.getWhoClicked().sendMessage(getUhc().getPrefix() + msg);
                    } else if (getRegister().getStatsUtil().getCoins((Player) e.getWhoClicked()) >= k.getPrice()) {
                        boughts.put(e.getWhoClicked().getName(), k);

                        getRegister().getConfirmInventory().openInventory((Player) e.getWhoClicked());
                    } else {
                        e.getWhoClicked().sendMessage(getUhc().getPrefix() + getRegister().getMessageFile()
                                .getColorString("Not enough Coins").replace("[kit]", k.getName().replace("_", " ")));
                        return;
                    }
                } else {
                    if (k.getName().replace("_", " ").equals(e.getCurrentItem().getItemMeta().getDisplayName()))
                        getRegister().getPreviewInventory().fillInventory((Player) e.getWhoClicked(), k);
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