package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

public class AppleFamineListener extends SimpleListener {

    public AppleFamineListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.APPLE_FAMINE))
            return;

        e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
        if ((Math.random()) < 0.2) {
            for (ItemStack is : getRegister().getDropsFile().readValues("Leaves")) {
                if (is.getType().equals(Material.APPLE))
                    continue;
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), is);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.APPLE_FAMINE))
            return;

        if (!(e.getBlock().getType().equals(Material.LEAVES) || e.getBlock().getType().equals(Material.LEAVES_2)))
            return;

        e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
        if ((Math.random()) < 0.2) {
            for (ItemStack is : getRegister().getDropsFile().readValues("Leaves")) {
                if (is.getType().equals(Material.APPLE))
                    continue;
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), is);
            }
        }
    }
}
