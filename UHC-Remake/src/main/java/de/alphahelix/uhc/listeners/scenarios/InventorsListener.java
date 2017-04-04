package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InventorsListener extends SimpleListener {

    private ArrayList<ItemStack> hasBeenCrafted = new ArrayList<>();

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.INVENTORS))
            return;

        if (!hasBeenCrafted.contains(e.getRecipe().getResult())) {
            hasBeenCrafted.add(e.getRecipe().getResult());
            for (Player p : Util.makePlayerArray(PlayerUtil.getAll())) {
                p.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getFirstCraftedItem((Player) e.getWhoClicked(), e.getRecipe().getResult()));
            }
        }
    }
}
