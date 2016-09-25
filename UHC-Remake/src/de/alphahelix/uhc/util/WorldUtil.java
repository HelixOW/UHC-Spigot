package de.alphahelix.uhc.util;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;

public class WorldUtil extends Util {

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

	public World createWorld() {
		if (getRegister().getLocationsFile().getArena() == null) {
			new BiomeUtil();
			return Bukkit.createWorld(new WorldCreator("UHC"));
		}
		File path = getRegister().getLocationsFile().getArena().getWorld().getWorldFolder();
		String name = getRegister().getLocationsFile().getArena().getWorld().getName();

		unloadWorld(getRegister().getLocationsFile().getArena().getWorld());
		deleteWorld(path);

		new BiomeUtil();
		
		World tr = Bukkit.createWorld(new WorldCreator(name));
		
		tr.setDifficulty(Difficulty.HARD);

		return tr;
	}

	public World createNetherWorld() {
		if (!getRegister().getScenarioFile().isEnabled(Scenarios.getRawScenarioName(Scenarios.DIMENSIONAL_INVERSION)))
			return null;
		if(!Scenarios.isScenario(Scenarios.DIMENSIONAL_INVERSION)) return null;
		
		if (getRegister().getLocationsFile().getNetherArena() == null) {
			return Bukkit.createWorld(new WorldCreator("UHC-Nether").environment(Environment.NETHER));
		}
		File path = getRegister().getLocationsFile().getNetherArena().getWorld().getWorldFolder();
		String name = getRegister().getLocationsFile().getNetherArena().getWorld().getName();

		unloadWorld(getRegister().getLocationsFile().getNetherArena().getWorld());
		deleteWorld(path);

		return Bukkit.createWorld(new WorldCreator(name).environment(Environment.NETHER));
	}
}
