package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import de.alphahelix.alphalibary.utils.MinecraftVersion;
import de.alphahelix.uhc.UHC;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

public class BiomeUtil {

    /**
     * id's 0 = Ocean 10 = Frozen_Ocean 24 = Deep_Ozean 124 = Void
     */

    static void removeBiomes() {
        new BukkitRunnable() {
            @Override
            public void run() {
                removeOceanFromBioms();
            }
        }.runTaskLater(UHC.getInstance(), 20);
    }

    private static void removeOceanFromBioms() {
        if (MinecraftVersion.getServer() != MinecraftVersion.EIGHT) {
            try {
                Object plainBiome = getBiomeFor("taiga");

                Object registerID = ReflectionUtil.getNmsClass("BiomeBase").getDeclaredField("REGISTRY_ID").get(ReflectionUtil.getNmsClass("BiomeBase"));
                Field a = ReflectionUtil.getNmsClass("RegistryMaterials").getDeclaredField("a");
                a.setAccessible(true);
                Object id = a.get(registerID);

                Field c = ReflectionUtil.getNmsClass("RegistryID").getDeclaredField("d");
                c.setAccessible(true);
                Object[] array = (Object[]) c.get(id);

                array[0] = plainBiome;
                array[10] = plainBiome;
                array[24] = plainBiome;

                Field b = ReflectionUtil.getNmsClass("RegistryID").getDeclaredField("d");
                b.setAccessible(true);
                b.set(id, array);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class clazz = ReflectionUtil.getNmsClass("BiomeBase");

                Field plainsField = clazz.getDeclaredField("TAIGA");
                plainsField.setAccessible(true);
                Object plainsBiome = plainsField.get(null);

                Field biomesField = clazz.getDeclaredField("biomes");
                biomesField.setAccessible(true);
                Object[] biomes = (Object[]) biomesField.get(null);

                biomes[0] = plainsBiome;
                biomes[10] = plainsBiome;
                biomes[24] = plainsBiome;
                biomes[124] = plainsBiome;

                biomesField.set(null, biomes);
            } catch (Exception ignore) {
            }
        }
    }

    private static Object getBiomeFor(String key) {
        return ReflectionUtil.getDeclaredMethod("a", ReflectionUtil.getNmsClass("Biomes"), String.class).invoke(null, false, key);
    }
}