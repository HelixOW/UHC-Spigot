package de.alphahelix.alphalibary.item.data;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

public class PotionData extends ItemData {

    private final ArrayList<PotionEffect> toApply = new ArrayList<>();

    public PotionData addEffect(SimplePotionEffect... effects) {
        for (SimplePotionEffect effect : effects) {
            this.toApply.add(effect.createEffect());
        }
        return this;
    }

    @Override
    public void applyOn(ItemStack applyOn) throws WrongDataException {
        try {

            PotionMeta meta = (PotionMeta) applyOn.getItemMeta();
            for (PotionEffect effect : toApply) {
                meta.addCustomEffect(effect, false);
            }
            applyOn.setItemMeta(meta);

        } catch (Exception e) {
            throw new WrongDataException(this);
        }
    }
}
