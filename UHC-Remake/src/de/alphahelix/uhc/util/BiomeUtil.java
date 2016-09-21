package de.alphahelix.uhc.util;

import java.lang.reflect.Field;

import org.bukkit.WorldCreator;

import de.popokaka.alphalibary.reflection.ReflectionUtil;
import net.minecraft.server.v1_9_R1.BiomeBase;
import net.minecraft.server.v1_9_R1.Biomes;
import net.minecraft.server.v1_9_R1.RegistryID;
import net.minecraft.server.v1_9_R1.RegistryMaterials;

public class BiomeUtil {

	/**
	 * id's 0 = Ocean 10 = Frozen_Ocean 24 = Deep_Ozean 124 = Void
	 */

	public BiomeUtil() {
		removeOceanFromBioms();
	}

	@SuppressWarnings("rawtypes")
	public static void removeOceanFromBioms() {
		try {
			Object plainBiome = getBiomeFor("taiga");

			RegistryMaterials registerID = BiomeBase.REGISTRY_ID;
			Field a = ReflectionUtil.getNmsClass("RegistryMaterials").getDeclaredField("a");
			a.setAccessible(true);
			RegistryID id = (RegistryID) a.get(registerID);

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
	}

	public static void generatorWorld(String name) {
		new WorldCreator(name).createWorld();
	}

	private static Object getBiomeFor(String key) {
		return ReflectionUtil.getDeclaredMethode("a", Biomes.class, String.class).invoke(null, false, key);
	}

}