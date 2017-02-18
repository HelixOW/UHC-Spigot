package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class GunsNRosesListener extends SimpleListener {

    public GunsNRosesListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.GUNS_N_ROSES))
            return;

        if (e.getBlock().getType().equals(Material.RED_ROSE) || e.getBlock().getType().equals(Material.DOUBLE_PLANT)) {
            if (Math.random() < 0.02) {
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.BOW));
            }
        }

        if (e.getBlock().getType().equals(Material.RED_ROSE)) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.ARROW));
        } else if (e.getBlock().getType().equals(Material.DOUBLE_PLANT)) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.ARROW, 4));
        }
    }
}
