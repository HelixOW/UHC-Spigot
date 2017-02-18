package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class FoodNeophobiaListener extends SimpleListener {

    private HashMap<String, ItemStack> food = new HashMap<>();

    public FoodNeophobiaListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onItemComsune(PlayerItemConsumeEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.FOOD_NEOPHOBIA))
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
