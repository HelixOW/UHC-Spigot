package de.alpha.uhc.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SpawnFileManager {
	
	private static File f = new File("plugins/UHC", "locations.yml");
	private static FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
	
	public void saveCfg() {
		try {
			cfg.save(f);
		} catch (IOException ignore) {}
		
	}
	
	public void SetLobby(double x, double y, double z, World w) {
		
		cfg.set("Lobby.x", x);
		cfg.set("Lobby.y", y);
		cfg.set("Lobby.z", z);
		cfg.set("Lobby.world", w.getName());
		
		saveCfg();
		
	}
	
	public void SetSpawn(double x, double y, double z, World w) {
		
		cfg.set("Spawn.x", x);
		cfg.set("Spawn.y", y);
		cfg.set("Spawn.z", z);
		cfg.set("Spawn.world", w.getName());
		
		saveCfg();
		
	}
	
	public static Location getLobby() {
		
		if(cfg.getString("Lobby.world") == null) {
			return null;
		}
		
		World w = Bukkit.getWorld(cfg.getString("Lobby.world"));
		int x = cfg.getInt("Lobby.x");
		int y = cfg.getInt("Lobby.y");
		int z = cfg.getInt("Lobby.z");
		
		Location l = new Location(w, x, y, z);
		
		return l;
		
	}
	
	public static Location getSpawn() {
		
		if(cfg.getString("Spawn.world") == null) {
			return null;
		}
		
		World w = Bukkit.getWorld(cfg.getString("Spawn.world"));
		int x = cfg.getInt("Spawn.x");
		int y = cfg.getInt("Spawn.y");
		int z = cfg.getInt("Spawn.z");
		
		Location l = new Location(w, x, y, z);
		
		return l;
		
	}
	
    public static Location getRandomLocation(Location player, int Xminimum, int Xmaximum, int Zminimum, int Zmaximum) {
        World world = player.getWorld();
        int randomZ = Zminimum + ((int) (Math.random() * ((double) ((Zmaximum - Zminimum) + 1))));
        double x = Double.parseDouble(Integer.toString(Xminimum + ((int) (Math.random() * ((double) ((Xmaximum - Xminimum) + 1)))))) + 0.5d;
        double z = Double.parseDouble(Integer.toString(randomZ)) + 0.5d;
        return new Location(world, x, (double) world.getHighestBlockYAt(new Location(world, x, player.getY(), z)), z);
    }
	
	public static String getSpawnWorldName() {
		
		if(cfg.getString("Spawn.world") == null) {
			return null;
		}
		
		return cfg.getString("Spawn.world");
	}

}
