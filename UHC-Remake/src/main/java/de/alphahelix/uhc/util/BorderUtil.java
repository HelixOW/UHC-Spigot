package de.alphahelix.uhc.util;

import de.alphahelix.alphaapi.nms.SimpleTitle;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Random;

public class BorderUtil {

    private static int size;
    private static Location arena;
    private static BukkitTask message;

    public static int getSize() {
        return size;
    }

    public static void createBorder() {
        new BukkitRunnable() {
            public void run() {
                for (String pName : PlayerUtil.getAll()) {
                    if (Bukkit.getPlayer(pName) == null) continue;
                    Player p = Bukkit.getPlayer(pName);

                    if (arena != null && arena.getWorld().getName().equals(p.getWorld().getName())) {
                        if (p.getLocation().distance(arena) >= size) {
                            Vector plV = p.getLocation().toVector();
                            Vector spV = arena.toVector();
                            Vector v = spV.clone().subtract(plV).multiply(2.0 / spV.distance(plV)).setY(0.5);
                            p.setVelocity(v);
                            p.getWorld().playSound(p.getLocation(), Sounds.CAT_PURR.bukkitSound(), 1F, 0.8F);
                            p.damage(UHCFileRegister.getBorderFile().getDamageInHearts());
                        }
                    } else {
                        if (p.getLocation().distance(p.getWorld().getSpawnLocation()) >= size) {
                            Vector plV = p.getLocation().toVector();
                            Vector spV = p.getWorld().getSpawnLocation().toVector();
                            Vector v = spV.clone().subtract(plV).multiply(2.0 / spV.distance(plV)).setY(0.5);
                            p.setVelocity(v);
                            p.getWorld().playSound(p.getLocation(), Sounds.CAT_PURR.bukkitSound(), 1F, 0.8F);
                            p.damage(UHCFileRegister.getBorderFile().getDamageInHearts());
                        }
                    }
                }
            }
        }.runTaskTimer(UHC.getInstance(), 0L, 20);

        new BukkitRunnable() {
            public void run() {
                for (String pName : PlayerUtil.getAll()) {
                    if (Bukkit.getPlayer(pName) == null) continue;
                    Player p = Bukkit.getPlayer(pName);

                    if (arena != null && arena.getWorld().getName().equals(p.getWorld().getName())) {
                        if (p.getLocation().distance(arena) >= size - 15) {
                            Location min = p.getLocation().add(-10, -10, -10);
                            Location max = p.getLocation().add(10, 10, 10);
                            Random r = new Random();
                            for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
                                for (int y = min.getBlockY(); y < max.getBlockY(); y++) {
                                    for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
                                        Location loc = new Location(p.getWorld(), x, y, z);
                                        if (loc.distance(arena) > size && loc.distance(arena) < size + 1) {
                                            if (r.nextInt(20 + 1) == 0) {
                                                for (int i = 0; i < 10; i++) {
                                                    p.getWorld().playEffect(loc, UHCFileRegister.getBorderFile().getEffect(), 1);
                                                    p.getWorld().playEffect(loc, UHCFileRegister.getBorderFile().getEffect(), 1);
                                                    p.getWorld().playEffect(loc, UHCFileRegister.getBorderFile().getEffect(), 1);
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
                            Random r = new Random();
                            for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
                                for (int y = min.getBlockY(); y < max.getBlockY(); y++) {
                                    for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
                                        Location loc = new Location(p.getWorld(), x, y, z);
                                        if (loc.distance(p.getWorld().getSpawnLocation()) > size
                                                && loc.distance(p.getWorld().getSpawnLocation()) < size + 1) {
                                            if (r.nextInt(20 + 1) == 0) {
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
        }.runTaskTimer(UHC.getInstance(), 0, 20);
    }

    public static void changeSize(int newSize) {
        size = newSize;
    }

    public static void setArena(Location spawn) {
        arena = spawn;
    }

    public static void setMovingListener() {
        if (!UHCFileRegister.getBorderFile().doShrinking())
            return;
        if (message != null)
            return;

        message = new BukkitRunnable() {
            public void run() {
                changeSize(size - UHCFileRegister.getBorderFile().getMovingDistance());

                for (String pname : PlayerUtil.getAll()) {
                    if (Bukkit.getPlayer(pname) == null) continue;
                    Player p = Bukkit.getPlayer(pname);

                    ScoreboardUtil.updateBorder(p);
                    SimpleTitle.sendTitle(p, " ",
                            UHCFileRegister.getMessageFile().getBorderHasMoved().replace("[blocks]",
                                    Integer.toString(UHCFileRegister.getBorderFile().getMovingDistance())),
                            1, 2, 1);
                    p.sendMessage(UHC.getPrefix()
                            + UHCFileRegister.getMessageFile().getBorderHasMoved().replace("[blocks]",
                            Integer.toString(UHCFileRegister.getBorderFile().getMovingDistance())));

                }
            }
        }.runTaskTimer(UHC.getInstance(), ((UHCFileRegister.getBorderFile().getDelay() * 20) * 60), ((UHCFileRegister.getBorderFile().getDelay() * 20) * 60));
    }
}
