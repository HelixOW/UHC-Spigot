package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class GoldenFleeceListener extends SimpleListener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.GOLDEN_FLEECE)) return;

        if (e.getEntity() instanceof Skeleton) {
            if (Math.random() < 0.4) {
                Skeleton s = (Skeleton) e.getEntity();

                s.getEquipment().setHelmet(new ItemStack(Material.GOLD_HELMET));
                s.getEquipment().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
                s.getEquipment().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
                s.getEquipment().setBoots(new ItemStack(Material.GOLD_BOOTS));
            }
        }
    }
}
