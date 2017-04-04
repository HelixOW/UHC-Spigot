package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class NightmareModeListener extends SimpleListener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NIGHTMARE_MODE))
            return;
        if (event.getEntity() instanceof Witch) {
            event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1726272000, 4));
        } else if (event.getEntity() instanceof Spider) {
            event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1726272000, 3));
        } else if (event.getEntity() instanceof Zombie) {
            event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1726272000, 1));
        } else if (event.getEntity() instanceof Creeper) {
            event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1726272000, 1));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NIGHTMARE_MODE))
            return;
        if (event.getEntity() instanceof Zombie) {
            if (event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.FIRE_TICK) {
                if ((new Random().nextInt(99) + 1) >= 50) {
                    event.setCancelled(true);
                }
            }
            return;
        }

        if (event.getEntity() instanceof Enderman) {
            if ((new Random().nextInt(99) + 1) <= 10) {
                event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Blaze.class);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NIGHTMARE_MODE))
            return;
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Arrow) {
                if ((event.getDamager()) instanceof Skeleton) {
                    ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1));
                }
                return;
            }

            if (event.getDamager() instanceof Enderman) {
                event.getEntity().setFireTicks(60);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NIGHTMARE_MODE))
            return;
        if (event.getEntity() instanceof Creeper) {
            event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Silverfish.class);
            event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Silverfish.class);
            event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Silverfish.class);
            event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Silverfish.class);
            event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Silverfish.class);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NIGHTMARE_MODE))
            return;
        if (event.getEntity() instanceof Creeper) {
            if ((new Random().nextInt(99) + 1) >= 50) {
                event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Silverfish.class);
            }
        } else if (event.getEntity() instanceof Zombie) {
            Skeleton skelly = event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Skeleton.class);
            skelly.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
            skelly.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1726272000, 0));
        } else if (event.getEntity() instanceof Spider) {
            Location loc = event.getEntity().getLocation();
            Block b = loc.getBlock();

            b.getRelative(BlockFace.EAST).setType(Material.WEB);
            b.getRelative(BlockFace.WEST).setType(Material.WEB);
            b.getRelative(BlockFace.SOUTH).setType(Material.WEB);
            b.getRelative(BlockFace.NORTH).setType(Material.WEB);
            b.getRelative(BlockFace.NORTH_EAST).setType(Material.REDSTONE_WIRE);
            b.getRelative(BlockFace.NORTH_WEST).setType(Material.REDSTONE_WIRE);
            b.getRelative(BlockFace.SOUTH_EAST).setType(Material.REDSTONE_WIRE);
            b.getRelative(BlockFace.SOUTH_WEST).setType(Material.REDSTONE_WIRE);
        }
    }

}
