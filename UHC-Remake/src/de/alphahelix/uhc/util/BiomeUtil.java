package de.alphahelix.uhc.util;

import java.lang.reflect.Field;

import org.bukkit.WorldCreator;

import de.alphahelix.uhc.GState;
import de.popokaka.alphalibary.reflection.ReflectionUtil;

public class BiomeUtil {

	/**
	 * id's 0 = Ocean 10 = Frozen_Ocean 24 = Deep_Ozean 124 = Void
	 */

	public BiomeUtil() {
		removeOceanFromBioms();
	}

	public static void removeOceanFromBioms() {
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
			GState.setCurrentState(GState.LOBBY);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public static void generatorWorld(String name) {
		new WorldCreator(name).createWorld();
	}

	private static Object getBiomeFor(String key) {
		return ReflectionUtil.getDeclaredMethode("a", ReflectionUtil.getNmsClass("Biomes"), String.class).invoke(null, false, key);
	}

}