package de.alpha.uhc.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.alpha.uhc.utils.Cuboid;
import de.alpha.uhc.utils.Regions;

public class SpawnFileManager {
	
	private static File f = new File("plugins/UHC", "locations.yml");
	private static FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
	
	public static void saveCfg() {
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
	
	
	
	public static void addRegion(Location loc1, Location loc2) {
		
		int id = cfg.getKeys(false).size();
		cfg.set(id + ".pos1.world", loc1.getWorld().getName());
		cfg.set(id + ".pos1.x", loc1.getBlockX());
		cfg.set(id + ".pos1.y", loc1.getBlockY());
		cfg.set(id + ".pos1.z", loc1.getBlockZ());
		
		cfg.set(id + ".pos2.world", loc2.getWorld().getName());
		cfg.set(id + ".pos2.x", loc2.getBlockX());
		cfg.set(id + ".pos2.y", loc2.getBlockY());
		cfg.set(id + ".pos2.z", loc2.getBlockZ());
		saveCfg();
	}
	
	public void registerRegions() {
		
		for(int i = 0; i < cfg.getKeys(false).size(); i++) {
			
			Regions.addRegion(new Cuboid(getRegionLoc(i, "pos1"), getRegionLoc(i, "pos2")));
			
		}
		
	}
	
	public Location getRegionLoc(int id, String name) {
		
		World w =  Bukkit.getWorld(cfg.getString(id + "." + name + ".world"));
		
		return new Location(w,
				cfg.getInt(id+"."+name+".x"),
				cfg.getInt(id+"."+name+".y"),
				cfg.getInt(id+"."+name+".z"));
	}

}
