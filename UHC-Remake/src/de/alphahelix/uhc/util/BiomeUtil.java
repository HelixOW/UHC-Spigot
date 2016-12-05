package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import org.bukkit.WorldCreator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BiomeUtil {

    /**
     * id's 0 = Ocean 10 = Frozen_Ocean 24 = Deep_Ozean 124 = Void
     */

    public BiomeUtil() {
        removeOceanFromBioms();
    }

    private static void removeOceanFromBioms() {
        if (UHC.getInstance().isOneNine()) {
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
        GState.setCurrentState(GState.LOBBY);
    }

    public static void generatorWorld(String name) {
        new WorldCreator(name).createWorld();
    }

    private static Object getBiomeFor(String key) {
        return ReflectionUtil.getDeclaredMethode("a", ReflectionUtil.getNmsClass("Biomes"), String.class).invoke(null, false, key);
    }

    private static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

}