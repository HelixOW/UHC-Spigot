package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class FlowerPowerListener extends SimpleListener {

    public FlowerPowerListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.FLOWER_POWER))
            return;

        if (e.getBlock().getType().equals(Material.RED_ROSE) || e.getBlock().getType().equals(Material.YELLOW_FLOWER)) {
            double r = Math.random();

            if (!(r < 0.2))
                return;
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(getRandomMaterial()));
        }
    }

    private Material getRandomMaterial() {
        int index = new Random().nextInt(Material.values().length);
        return Material.values()[index];
    }
}
