package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

public class TreeDropsListener extends SimpleListener {

    public TreeDropsListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.TREE_DROPS))
            return;

        double r = Math.random();

        if (r < 0.1 && r > 0.005) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.FEATHER, 2));
        } else if (r <= 0.005 && r > 0.001) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
        } else if (r <= 0.001) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.TREE_DROPS))
            return;

        if (e.getBlock().getType().name().contains("LEAVES")) {
            double r = Math.random();

            if (r < 0.1 && r > 0.005) {
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),
                        new ItemStack(Material.FEATHER, 2));
            } else if (r <= 0.005 && r > 0.001) {
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),
                        new ItemStack(Material.GOLD_INGOT));
            } else if (r <= 0.001) {
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND));
            }
        } else if (e.getBlock().getType().equals(Material.DIAMOND_ORE)
                || e.getBlock().getType().equals(Material.GOLD_ORE)) {
            int xp = e.getExpToDrop();
            e.setCancelled(true);
            ExperienceOrb exp = e.getBlock().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class);
            exp.setExperience(xp);
        }
    }
}
