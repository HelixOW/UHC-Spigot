package de.alpha.uhc.border;

import de.alpha.uhc.Core;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class Border {

    private static int size;
    private static double dmg;
    private static Location arena;

    public static void setDmg(double dmg) {
        Border.dmg = dmg;
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int newSize) {
        size = newSize;
    }

    private static int random() {
        Random r = new Random();
        return r.nextInt(20 + 1);
    }

    public static void setDistanceLoc(Location loc) {
        arena = loc;
    }

    public static void border() {

        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (arena != null && arena.getWorld().getName().equals(p.getWorld().getName())) {
                        if (p.getLocation().distance(arena) >= size) {
                            Vector plV = p.getLocation().toVector();
                            Vector spV = arena.toVector();
                            Vector v = spV.clone().subtract(plV).multiply(2.0 / spV.distance(plV)).setY(0.5);
                            p.setVelocity(v);
                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1F, 0.8F);//TODO: multi
                            p.damage(dmg);
                        }
                    } else {
                        if (p.getLocation().distance(p.getWorld().getSpawnLocation()) >= size) {
                            Vector plV = p.getLocation().toVector();
                            Vector spV = p.getWorld().getSpawnLocation().toVector();
                            Vector v = spV.clone().subtract(plV).multiply(2.0 / spV.distance(plV)).setY(0.5);
                            p.setVelocity(v);
                            p.damage(dmg);
                        }
                    }
                }

            }
        }.runTaskTimer(Core.getInstance(), 0L, 15L);
        new BukkitRunnable() {

            @Override
            public void run() {

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (arena != null && arena.getWorld().getName().equals(p.getWorld().getName())) {
                        if (p.getLocation().distance(arena) >= size - 15) {
                            Location min = p.getLocation().add(-10, -10, -10);
                            Location max = p.getLocation().add(10, 10, 10);
                            for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
                                for (int y = min.getBlockY(); y < max.getBlockY(); y++) {
                                    for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
                                        Location loc = new Location(p.getWorld(), x, y, z);
                                        if (loc.distance(arena) > size && loc.distance(arena) < size + 1) {
                                            if (random() == 0) {
                                                for (int i = 0; i < 10; i++) {
                                                    p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
                                                    p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
                                                    p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (p.getLocation().distance(p.getWorld().getSpawnLocation()) >= size - 15) {
                            Location min = p.getLocation().add(-10, -10, -10);
                            Location max = p.getLocation().add(10, 10, 10);
                            for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
                                for (int y = min.getBlockY(); y < max.getBlockY(); y++) {
                                    for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
                                        Location loc = new Location(p.getWorld(), x, y, z);
                                        if (loc.distance(p.getWorld().getSpawnLocation()) > size && loc.distance(p.getWorld().getSpawnLocation()) < size + 1) {
                                            if (random() == 0) {
                                                for (int i = 0; i < 10; i++) {
                                                    p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
                                                    p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
                                                    p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }.runTaskTimer(Core.getInstance(), 0L, 15L);

    }

    public static void changesize(int newSize) {
        size = newSize;
    }
}
