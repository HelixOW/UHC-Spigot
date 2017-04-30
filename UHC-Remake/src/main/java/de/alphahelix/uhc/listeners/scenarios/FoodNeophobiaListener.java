package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class FoodNeophobiaListener extends SimpleListener {

    private HashMap<String, ItemStack> food = new HashMap<>();

    @EventHandler
    public void onItemComsune(PlayerItemConsumeEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.FOOD_NEOPHOBIA))
            return;
        if (e.getItem().getType().equals(Material.MILK_BUCKET) || e.getItem().getType().equals(Material.POTION))
            return;

        if (!food.containsKey(e.getPlayer().getName()))
            food.put(e.getPlayer().getName(), e.getItem());
        else {
            if (!food.get(e.getPlayer().getName()).getType().equals(e.getItem().getType())) {
                e.getPlayer().damage(2.0);
            }
        }
    }
}
