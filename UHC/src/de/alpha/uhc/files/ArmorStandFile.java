package de.alpha.uhc.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import de.popokaka.alphalibary.file.SimpleFile;

public class ArmorStandFile {
	
	public ArmorStandFile() {
		// TODO Auto-generated constructor stub
	}
	
    private static SimpleFile file = getASFile();

    public static SimpleFile getASFile() {
        return new SimpleFile("plugins/UHC", "armorstands.yml");
    }

    public static void addArmorStand(Location l) {
    	
    	int id = file.getKeys(false).size();
    	
    	file.setDefault(id+".x", l.getBlockX());
    	file.setDefault(id+".y", l.getBlockY());
    	file.setDefault(id+".z", l.getBlockZ());
    	file.setDefault(id+".world", l.getWorld().getName());
    	file.save();
    }
    
    public static void removeArmorStand(Location l) {
    	for(String id : file.getKeys(false)) {
    		int x = file.getInt(id+".x");
    		int y = file.getInt(id+".y");
    		int z = file.getInt(id+".z");
    		World w = Bukkit.getWorld(file.getString(id+".world"));
    		
    		if(l.getBlockX() == x && l.getBlockY() == y && l.getBlockZ() == z && l.getWorld().getName() == w.getName()) {
    			file.set(id, null);
    			file.save();
    		}
    	}
    }
}
