package de.alphahelix.uhc.util;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

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
		
		File path = getRegister().getLocationsFile().getArena().getWorld().getWorldFolder();
		
		unloadWorld(getRegister().getLocationsFile().getArena().getWorld());
		deleteWorld(path);
		
		String[] values = getRegister().getLocationsFile().getString("Arena").split(",");
		
		new BiomeUtil();
		
		return Bukkit.createWorld(new WorldCreator(values[3]));
	}
}
