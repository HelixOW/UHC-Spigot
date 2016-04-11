package de.alpha.uhc.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import de.popokaka.alphalibary.file.SimpleFile;

public class HologramFileManager {
	
	public static SimpleFile getHologramFile() {
		return new SimpleFile("plugins/UHC", "holograms.yml");
	}
	
	private static SimpleFile file = getHologramFile();
	
	public void addHoloram(String name, Location loc, double minus) {
		
		int id = file.getKeys(false).size();
		file.setDefault(id + ".name", name);
		file.setDefault(id + ".x", loc.getX());
		file.setDefault(id + ".y", loc.subtract(0, minus, 0).getY());
		file.setDefault(id + ".z", loc.getZ());
		file.setDefault(id + ".world", loc.getWorld().getName());
		
	}
	
	public String getName(int id) {
		return file.getColorString(id + ".name");
	}
	
	public int holocount() {
		return file.getKeys(false).size();
	}
	
	public Location getLocation(int id) {
		
		double x = file.getDouble(id + ".x");
		double y = file.getDouble(id + ".y");
		double z = file.getDouble(id + ".z");
		World w = null;
		if(Bukkit.getWorld(file.getString(id + ".world"))!= null) {
			w = Bukkit.getWorld(file.getString(id + ".world"));
		} else {
			w = Bukkit.getWorld(SpawnFileManager.getLobbyWorldName());
		}
		
		return new Location(w, x, y, z);
		
	}

}
