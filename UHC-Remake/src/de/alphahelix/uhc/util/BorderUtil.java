package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.nms.SimpleTitle;
import de.alphahelix.uhc.Sounds;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Random;

public class BorderUtil extends Util {

    private int size;
    private Location arena;
    private BukkitTask message;

    public BorderUtil(UHC uhc) {
        super(uhc);
    }

    public int getSize() {
        return size;
    }

    public void createBorder() {
        new BukkitRunnable() {
            public void run() {
                for (String pName : getRegister().getPlayerUtil().getAll()) {
                    if (Bukkit.getPlayer(pName) == null) continue;
                    Player p = Bukkit.getPlayer(pName);

                    if (arena != null && arena.getWorld().getName().equals(p.getWorld().getName())) {
                        if (p.getLocation().distance(arena) >= size) {
                            Vector plV = p.getLocation().toVector();
                            Vector spV = arena.toVector();
                            Vector v = spV.clone().subtract(plV).multiply(2.0 / spV.distance(plV)).setY(0.5);
                            p.setVelocity(v);
                            p.getWorld().playSound(p.getLocation(), Sounds.CAT_PURR.bukkitSound(), 1F, 0.8F);
                            p.damage(getRegister().getBorderFile().getDouble("damage (hearts)"));
                        }
                    } else {
                        if (p.getLocation().distance(p.getWorld().getSpawnLocation()) >= size) {
                            Vector plV = p.getLocation().toVector();
                            Vector spV = p.getWorld().getSpawnLocation().toVector();
                            Vector v = spV.clone().subtract(plV).multiply(2.0 / spV.distance(plV)).setY(0.5);
                            p.setVelocity(v);
                            p.getWorld().playSound(p.getLocation(), Sounds.CAT_PURR.bukkitSound(), 1F, 0.8F);
                            p.damage(getRegister().getBorderFile().getDouble("damage (hearts)"));
                        }
                    }
                }
            }
        }.runTaskTimer(getUhc(), 0L, 20);

        new BukkitRunnable() {
            public void run() {
                for (String pName : getRegister().getPlayerUtil().getAll()) {
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
                                                    p.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);
                                                    p.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);
                                                    p.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);
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
        }.runTaskTimer(getUhc(), 0, 20);
    }

    public void changeSize(int newSize) {
        size = newSize;
    }

    public void setArena(Location spawn) {
        arena = spawn;
    }

    public void setMovingListener() {

        if (!getRegister().getBorderFile().getBoolean("shrinks"))
            return;
        if (message != null)
            return;

        new BukkitRunnable() {
            public void run() {
                message = new BukkitRunnable() {
                    public void run() {
                        changeSize(size - getRegister().getBorderFile().getInt("moving distance"));

                        for (String pname : getRegister().getPlayerUtil().getAll()) {
                            if (Bukkit.getPlayer(pname) == null) continue;
                            Player p = Bukkit.getPlayer(pname);

                            getRegister().getScoreboardUtil().updateBorder(p);
                            SimpleTitle.sendTitle(p, " ",
                                    getRegister().getMessageFile().getColorString("Border has moved").replace("[blocks]",
                                            Integer.toString(getRegister().getBorderFile().getInt("moving distance"))),
                                    1, 2, 1);
                            p.sendMessage(getUhc().getPrefix()
                                    + getRegister().getMessageFile().getColorString("Border has moved").replace("[blocks]",
                                    Integer.toString(getRegister().getBorderFile().getInt("moving distance"))));

                        }
                    }
                }.runTaskTimer(getUhc(), 0, ((getRegister().getBorderFile().getLong("delay (min)") * 20) * 60));
            }
        }.runTaskLater(getUhc(), ((getRegister().getBorderFile().getLong("delay (min)") * 20) * 60));
    }
}
