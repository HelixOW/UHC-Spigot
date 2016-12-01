package de.alphahelix.alphalibary.item.data;

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

public class FireworkData extends ItemData {
    private final List<FireworkEffect> allEffects = new ArrayList<>();

    public FireworkData(SimpleFireworkEffect... effects) {
        for (SimpleFireworkEffect effect : effects) {
            allEffects.add(effect.build());
        }
    }

    @Override
    public void applyOn(ItemStack applyOn) throws WrongDataException {
        try {
            if (!(applyOn.getType() == Material.FIREWORK)) {
                return;
            }

            FireworkMeta fireworkMeta = (FireworkMeta) applyOn.getItemMeta();
            fireworkMeta.addEffects(allEffects);
            applyOn.setItemMeta(fireworkMeta);
        } catch (Exception e) {
            try {
                throw new WrongDataException(this);
            } catch (WrongDataException ignored) {

            }
        }
    }
}
