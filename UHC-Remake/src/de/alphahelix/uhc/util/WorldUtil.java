package de.alphahelix.uhc.util;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.utils.Cuboid;

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
		World tr = null;
		if (getRegister().getLocationsFile().getArena() == null) {

			if (getRegister().getScenarioFile().isEnabled(Scenarios.getRawScenarioName(Scenarios.URBAN))) {
				if (Scenarios.isScenario(Scenarios.URBAN)) {
					tr = Bukkit.createWorld(new WorldCreator("UHC").type(WorldType.FLAT));
					tr.setDifficulty(Difficulty.HARD);
					tr.getSpawnLocation().getChunk().load();
					return tr;
				}
			} else if (getRegister().getScenarioFile()
					.isEnabled(Scenarios.getRawScenarioName(Scenarios.VAST_TRACK_O_MOUNTAIN))) {
				if (Scenarios.isScenario(Scenarios.VAST_TRACK_O_MOUNTAIN)) {
					tr = Bukkit.createWorld(new WorldCreator("UHC").type(WorldType.AMPLIFIED));
					tr.setDifficulty(Difficulty.HARD);
					tr.getSpawnLocation().getChunk().load();
					return tr;
				}
			}

			tr = Bukkit.createWorld(new WorldCreator("UHC"));
			tr.setDifficulty(Difficulty.HARD);
			tr.getSpawnLocation().getChunk().load();
			return tr;

		}

		File path = null;
		String name = "";

		if (getRegister().getLocationsFile().getArena().getWorld() == null) {
			path = new File(getRegister().getLocationsFile().getArenaWorldName());
			name = getRegister().getLocationsFile().getArenaWorldName();
		} else {
			path = getRegister().getLocationsFile().getArena().getWorld().getWorldFolder();
			name = getRegister().getLocationsFile().getArena().getWorld().getName();

			unloadWorld(getRegister().getLocationsFile().getArena().getWorld());
		}

		deleteWorld(path);

		if (getRegister().getScenarioFile().isEnabled(Scenarios.getRawScenarioName(Scenarios.URBAN))) {
			if (Scenarios.isScenario(Scenarios.URBAN))
				tr = Bukkit.createWorld(new WorldCreator(name).type(WorldType.FLAT));
			else
				tr = Bukkit.createWorld(new WorldCreator(name));
		}

		else if (getRegister().getScenarioFile()
				.isEnabled(Scenarios.getRawScenarioName(Scenarios.VAST_TRACK_O_MOUNTAIN))) {
			if (Scenarios.isScenario(Scenarios.VAST_TRACK_O_MOUNTAIN))
				tr = Bukkit.createWorld(new WorldCreator(name).type(WorldType.AMPLIFIED));
			else
				tr = Bukkit.createWorld(new WorldCreator(name));
		}

		else
			tr = Bukkit.createWorld(new WorldCreator(name));

		tr.setDifficulty(Difficulty.HARD);
		tr.getSpawnLocation().getChunk().load();

		return tr;
	}

	public World createNetherWorld() {
		if (!getRegister().getScenarioFile().isEnabled(Scenarios.getRawScenarioName(Scenarios.DIMENSIONAL_INVERSION)))
			return getRegister().getLocationsFile().getArena().getWorld();
		if (!Scenarios.isScenario(Scenarios.DIMENSIONAL_INVERSION))
			return getRegister().getLocationsFile().getArena().getWorld();

		if (getRegister().getLocationsFile().getNetherArena() == null) {
			World tr = Bukkit.createWorld(new WorldCreator("UHC-Nether").environment(Environment.NETHER));

			tr.setDifficulty(Difficulty.HARD);
			tr.getSpawnLocation().getChunk().load();

			return tr;
		}
		File path = getRegister().getLocationsFile().getNetherArena().getWorld().getWorldFolder();
		String name = getRegister().getLocationsFile().getNetherArena().getWorld().getName();

		unloadWorld(getRegister().getLocationsFile().getNetherArena().getWorld());
		deleteWorld(path);

		World tr = Bukkit.createWorld(new WorldCreator(name).environment(Environment.NETHER));

		tr.setDifficulty(Difficulty.HARD);
		tr.getSpawnLocation().getChunk().load();

		return tr;
	}

	public void preGenerateWorld() {
		int startx = getRegister().getLocationsFile().getArena().getBlockX()
				- (getRegister().getMainOptionsFile().getInt("Pregenerate World.size") / 2);
		int startz = getRegister().getLocationsFile().getArena().getBlockZ()
				- -(getRegister().getMainOptionsFile().getInt("Pregenerate World.size") / 2);
		int endx = getRegister().getLocationsFile().getArena().getBlockX()
				+ (getRegister().getMainOptionsFile().getInt("Pregenerate World.size") / 2);
		int endz = getRegister().getLocationsFile().getArena().getBlockZ()
				+ (getRegister().getMainOptionsFile().getInt("Pregenerate World.size") / 2);

		long start = System.currentTimeMillis();

		getRegister().getLocationsFile().getArena().getWorld().save();

		Cuboid r = new Cuboid(getRegister().getLocationsFile().getArena().getWorld(), startx, 3, startz, endx, 200,
				endz);

		r.generateChunks();
		long duration = Long.valueOf(System.currentTimeMillis() - start);
		long seconds = Long.valueOf(duration / 1000L);
		long minutes = Long.valueOf(seconds / 60L);

		new BiomeUtil();
		UHC.getInstance().getLog().log(Level.INFO,
				"Generating done! (" + duration + "ms, =" + seconds + "s, =" + minutes + "m)");
	}
}
