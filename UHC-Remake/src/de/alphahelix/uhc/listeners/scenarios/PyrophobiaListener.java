package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

public class PyrophobiaListener extends SimpleListener {

    public PyrophobiaListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent e) {
        if (e.isCancelled()) return;
        if (!scenarioCheck(Scenarios.PYROPHOBIA)) return;
        if (e.getItemStack().getType() == Material.WATER_BUCKET) {
            e.setItemStack(new ItemStack(Material.BUCKET));
            e.setCancelled(true);
            e.getBlockClicked().setType(Material.AIR);
            e.getPlayer().updateInventory();
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {
        if (e.isCancelled()) return;
        if (!scenarioCheck(Scenarios.PYROPHOBIA)) return;
        IgniteCause cause = e.getCause();

        if (cause == IgniteCause.LAVA)
            e.setCancelled(true);
        else if (cause == IgniteCause.SPREAD)
            e.setCancelled(true);
    }

    @EventHandler
    public void CreatureSpawnEvent(CreatureSpawnEvent e) {
        if (e.isCancelled()) return;
        if (!scenarioCheck(Scenarios.PYROPHOBIA)) return;
        e.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1726272000, 2));
    }

    @EventHandler
    public void LeavesDecayEvent(LeavesDecayEvent e) {
        if (e.isCancelled()) return;
        if (!scenarioCheck(Scenarios.PYROPHOBIA)) return;
        Random r = new Random();

        if (r.nextInt(100) < 2) {
            Item item = e.getBlock().getWorld().dropItem(e.getBlock().getLocation().add(0.5, 0.7, 0.5),
                    new ItemStack(Material.SUGAR_CANE, 1 + r.nextInt(1)));
            item.setVelocity(new Vector(0, 0.2, 0));
        }
    }
}
