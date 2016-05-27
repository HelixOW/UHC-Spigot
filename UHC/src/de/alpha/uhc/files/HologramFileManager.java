package de.alpha.uhc.files;

import de.alpha.uhc.Core;
import de.popokaka.alphalibary.file.SimpleFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class HologramFileManager {
	
	private Core pl;
	
	public HologramFileManager(Core c) {
		this.pl = c;
	}

    private static final SimpleFile file = getHologramFile();

    public static SimpleFile getHologramFile() {
        return new SimpleFile("plugins/UHC", "holograms.yml");
    }

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
        World w;
        if (Bukkit.getWorld(file.getString(id + ".world")) != null) {
            w = Bukkit.getWorld(file.getString(id + ".world"));
        } else {
            w = Bukkit.getWorld(SpawnFileManager.getLobbyWorldName());
        }

        return new Location(w, x, y, z);

    }

}
