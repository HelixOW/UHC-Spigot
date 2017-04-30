package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.utils.Cuboid;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.apache.commons.io.FileDeleteStrategy;
import org.bukkit.*;

import java.io.File;
import java.io.IOException;

public class WorldUtil {

    private static World arenaWorld;

    private static void deleteWorld(String world) {
        if (Bukkit.getServer().unloadWorld(world, false)) {
            try {
                FileDeleteStrategy.FORCE.delete(new File("./UHC"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createWorld() {
        deleteWorld("UHC");

        UHCFileRegister.getLocationsFile().getLobby();

        WorldCreator c = new WorldCreator("UHC");

        if (Scenarios.isPlayedAndEnabled(Scenarios.URBAN)) {
            c = new WorldCreator("UHC").type(WorldType.FLAT);
        } else if (Scenarios.isPlayedAndEnabled(Scenarios.VAST_TRACK_O_MOUNTAIN)) {
            c = new WorldCreator("UHC").type(WorldType.AMPLIFIED);
        }

        arenaWorld = Bukkit.createWorld(c);

        arenaWorld.setDifficulty(Difficulty.HARD);

        if (UHC.isPregen()) {
            preGenerateWorld();
        }

        BiomeUtil.removeBiomes();
    }

    public static void createNetherWorld() {
        WorldCreator c = new WorldCreator("UHC-Nether");

        if (Scenarios.isPlayedAndEnabled(Scenarios.URBAN)) {
            c = new WorldCreator("UHC-Nether").type(WorldType.FLAT);
        } else if (Scenarios.isPlayedAndEnabled(Scenarios.VAST_TRACK_O_MOUNTAIN)) {
            c = new WorldCreator("UHC-Nether").type(WorldType.AMPLIFIED);
        }

        arenaWorld = Bukkit.createWorld(c);

        arenaWorld.setDifficulty(Difficulty.HARD);

        if (UHC.isPregen()) {
            preGenerateWorld();
        }

        BiomeUtil.removeBiomes();
    }

    private static void preGenerateWorld() {
        int startx = Bukkit.getWorld("UHC").getSpawnLocation().getBlockX()
                - (UHCFileRegister.getOptionsFile().getPregenerateWorldSize() / 2);
        int startz = Bukkit.getWorld("UHC").getSpawnLocation().getBlockZ()
                - -(UHCFileRegister.getOptionsFile().getPregenerateWorldSize() / 2);
        int endx = Bukkit.getWorld("UHC").getSpawnLocation().getBlockX()
                + (UHCFileRegister.getOptionsFile().getPregenerateWorldSize() / 2);
        int endz = Bukkit.getWorld("UHC").getSpawnLocation().getBlockZ()
                + (UHCFileRegister.getOptionsFile().getPregenerateWorldSize() / 2);

        long start = System.currentTimeMillis();

        Cuboid r = new Cuboid(Bukkit.getWorld("UHC"), startx, 3, startz, endx, 200,
                endz);

        try {
            r.generateChunks();
        } catch (Exception ignore) {
        }

        long duration = System.currentTimeMillis() - start;
        long seconds = duration / 1000L;
        long minutes = seconds / 60L;

        new BiomeUtil();
        System.out.println(UHC.getConsolePrefix() + "Generating done! (" + duration + "ms, =" + seconds + "s, =" + minutes + "m)");
    }
}
