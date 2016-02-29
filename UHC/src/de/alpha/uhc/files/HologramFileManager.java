package de.alpha.uhc.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import net.minetopix.library.main.file.SimpleFile;

public class HologramFileManager {
	
	public static SimpleFile getHologramFile() {
		return new SimpleFile("plugins/UHC", "holograms.yml");
	}
	
	private static SimpleFile file = getHologramFile();
	
	public void addHoloram(String name, Location loc) {
		
		int id = file.getKeys(false).size();
		file.setDefault(id + ".name", name);
		file.setDefault(id + ".x", loc.getBlockX());
		file.setDefault(id + ".y", loc.getBlockY());
		file.setDefault(id + ".z", loc.getBlockZ());
		file.setDefault(id + ".world", loc.getWorld().getName());
		
	}
	
	public String getName(int id) {
		return file.getColorString(id + ".name");
	}
	
	public int holocount() {
		return file.getKeys(false).size();
	}
	
	public Location getLocation(int id) {
		
		int x = file.getInt(id + ".x");
		int y = file.getInt(id + ".y");
		int z = file.getInt(id + ".z");
		World w = null;
		if(Bukkit.getWorld(file.getString(id + ".world"))!= null) {
			w = Bukkit.getWorld(file.getString(id + ".world"));
		} else {
			w = Bukkit.getWorld(SpawnFileManager.getLobbyWorldName());
		}
		
		return new Location(w, x, y, z);
		
	}

}
