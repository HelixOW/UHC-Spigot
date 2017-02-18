package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.utils.Cuboid;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.*;
import org.bukkit.World.Environment;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.logging.Level;

public class WorldUtil extends Util {

    private World tr;
    private File path = null;
    private String name = "";

    public WorldUtil(UHC uhc) {
        super(uhc);
    }

    private void unloadWorld(World world) {
        if (world != null) {
            Bukkit.getServer().unloadWorld(world, false);
        }
    }

    private void deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < (files != null ? files.length : 0); i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
    }

    public void createWorld() {
        if (UHCFileRegister.getLocationsFile().getArena() == null) {
            if (UHCFileRegister.getScenarioFile().isEnabled(Scenarios.getRawScenarioName(Scenarios.URBAN))) {
                if (Scenarios.isScenario(Scenarios.URBAN)) {
                    tr = Bukkit.createWorld(new WorldCreator("UHC").type(WorldType.FLAT));
                    tr.setDifficulty(Difficulty.HARD);

                    if (getUhc().isPregen()) {
                        preGenerateWorld();
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            new BiomeUtil();
                        }
                    }.runTaskLater(getUhc(), 20);

                    return;
                }
            } else if (UHCFileRegister.getScenarioFile()
                    .isEnabled(Scenarios.getRawScenarioName(Scenarios.VAST_TRACK_O_MOUNTAIN))) {
                if (Scenarios.isScenario(Scenarios.VAST_TRACK_O_MOUNTAIN)) {
                    tr = Bukkit.createWorld(new WorldCreator("UHC").type(WorldType.AMPLIFIED));
                    tr.setDifficulty(Difficulty.HARD);
                    if (getUhc().isPregen()) {
                        preGenerateWorld();
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            new BiomeUtil();
                        }
                    }.runTaskLater(getUhc(), 20);

                    return;
                }
            }

            tr = Bukkit.createWorld(new WorldCreator("UHC"));
            tr.setDifficulty(Difficulty.HARD);
            if (getUhc().isPregen()) {
                preGenerateWorld();
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    new BiomeUtil();
                }
            }.runTaskLater(getUhc(), 20);

            return;

        }


        path = UHCFileRegister.getLocationsFile().getArena().getWorld().getWorldFolder();
        name = UHCFileRegister.getLocationsFile().getArena().getWorld().getName();

        new BukkitRunnable() {
            public void run() {
                unloadWorld(UHCFileRegister.getLocationsFile().getArena().getWorld());
            }
        }.runTaskLater(getUhc(), 5);

        new BukkitRunnable() {
            public void run() {

                deleteWorld(path);

                if (UHCFileRegister.getScenarioFile().isEnabled(Scenarios.getRawScenarioName(Scenarios.URBAN))) {
                    if (Scenarios.isScenario(Scenarios.URBAN))
                        tr = Bukkit.createWorld(new WorldCreator(name).type(WorldType.FLAT));
                    else
                        tr = Bukkit.createWorld(new WorldCreator(name));
                } else if (UHCFileRegister.getScenarioFile()
                        .isEnabled(Scenarios.getRawScenarioName(Scenarios.VAST_TRACK_O_MOUNTAIN))) {
                    if (Scenarios.isScenario(Scenarios.VAST_TRACK_O_MOUNTAIN))
                        tr = Bukkit.createWorld(new WorldCreator(name).type(WorldType.AMPLIFIED));
                    else
                        tr = Bukkit.createWorld(new WorldCreator(name));
                } else
                    tr = Bukkit.createWorld(new WorldCreator(name));

                tr.setDifficulty(Difficulty.HARD);
                if (getUhc().isPregen()) {
                    preGenerateWorld();
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new BiomeUtil();
                    }
                }.runTaskLater(getUhc(), 20);

            }
        }.runTaskLater(getUhc(), 10);
    }

    public World createNetherWorld() {
        if (!UHCFileRegister.getScenarioFile().isEnabled(Scenarios.getRawScenarioName(Scenarios.DIMENSIONAL_INVERSION)))
            return UHCFileRegister.getLocationsFile().getArena().getWorld();
        if (!Scenarios.isScenario(Scenarios.DIMENSIONAL_INVERSION))
            return UHCFileRegister.getLocationsFile().getArena().getWorld();

        if (UHCFileRegister.getLocationsFile().getNetherArena() == null) {
            tr = Bukkit.createWorld(new WorldCreator("UHC-Nether").environment(Environment.NETHER));

            tr.setDifficulty(Difficulty.HARD);
            if (getUhc().isPregen()) {
                preGenerateWorld();
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    new BiomeUtil();
                }
            }.runTaskLater(getUhc(), 20);

            return tr;
        }
        path = UHCFileRegister.getLocationsFile().getNetherArena().getWorld().getWorldFolder();
        name = UHCFileRegister.getLocationsFile().getNetherArena().getWorld().getName();

        unloadWorld(UHCFileRegister.getLocationsFile().getNetherArena().getWorld());
        deleteWorld(path);

        tr = Bukkit.createWorld(new WorldCreator(name).environment(Environment.NETHER));

        tr.setDifficulty(Difficulty.HARD);
        if (getUhc().isPregen()) {
            preGenerateWorld();
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                new BiomeUtil();
            }
        }.runTaskLater(getUhc(), 20);

        return tr;
    }

    public void preGenerateWorld() {
        int startx = UHCFileRegister.getLocationsFile().getArena().getBlockX()
                - (UHCFileRegister.getOptionsFile().getPregenerateWorldSize() / 2);
        int startz = UHCFileRegister.getLocationsFile().getArena().getBlockZ()
                - -(UHCFileRegister.getOptionsFile().getPregenerateWorldSize() / 2);
        int endx = UHCFileRegister.getLocationsFile().getArena().getBlockX()
                + (UHCFileRegister.getOptionsFile().getPregenerateWorldSize() / 2);
        int endz = UHCFileRegister.getLocationsFile().getArena().getBlockZ()
                + (UHCFileRegister.getOptionsFile().getPregenerateWorldSize() / 2);

        long start = System.currentTimeMillis();

        UHCFileRegister.getLocationsFile().getArena().getWorld().save();


        Cuboid r = new Cuboid(UHCFileRegister.getLocationsFile().getArena().getWorld(), startx, 3, startz, endx, 200,
                endz);

        try {
            r.generateChunks();
        } catch (Exception ignore) {
        }

        long duration = System.currentTimeMillis() - start;
        long seconds = duration / 1000L;
        long minutes = seconds / 60L;

        new BiomeUtil();
        UHC.getInstance().getLog().log(Level.INFO,
                "Generating done! (" + duration + "ms, =" + seconds + "s, =" + minutes + "m)");
    }
}
