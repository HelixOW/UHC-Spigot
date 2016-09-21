package de.alphahelix.uhc.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class LocationsFile extends EasyFile {

	public LocationsFile(UHC uhc) {
		super("locations.uhc", uhc);
	}

	public void setLobby(Location loc) {
		setLocation("Lobby", loc, true);
	}

	public void setDeathmatch(Location loc) {
		setLocation("Deathmatch", loc, true);
	}

	public void initalizeLobbyAndArena() {
		
		if(getString("Lobby") != null) {
			if (!(getLocation("Lobby", true) == null)) {
				if (getLocation("Lobby", true).build().getWorld() == null) {
					String[] values = getString("Lobby").split(",");
					
					Bukkit.createWorld(new WorldCreator(values[3]));
				}
			}
		}
		if(getString("Arena") != null) {
			if (!(getLocation("Arena", true) == null)) {
				if (getLocation("Arena", true).build().getWorld() == null) {
					String[] values = getString("Arena").split(",");
					
					Bukkit.createWorld(new WorldCreator(values[3]));
				}
			}
		}
		if(getString("Deathmatch") != null) {
			if (!(getLocation("Deathmatch", true) == null)) {
				if (getLocation("Deathmatch", true).build().getWorld() == null) {
					String[] values = getString("Deathmatch").split(",");
	
					Bukkit.createWorld(new WorldCreator(values[3]));
				}
			}
		}
	}

	public Location getLobby() {
		if (getLocation("Lobby", true).build() != null)
			return getLocation("Lobby", true).build();
		return Bukkit.getWorld("UHC").getHighestBlockAt(Bukkit.getWorld("UHC").getSpawnLocation()).getLocation();
	}

	public void setArena(Location loc) {
		setLocation("Arena", loc, true);
	}

	public Location getArena() {
		if (getLocation("Arena", true).build() != null)
			return getLocation("Arena", true).build();
		return Bukkit.getWorld("UHC").getHighestBlockAt(Bukkit.getWorld("UHC").getSpawnLocation()).getLocation();
	}

	public Location getDeathmatch() {
		if(contains("Deathmatch")) {
			if (getLocation("Deathmatch", true).build() != null)
				return getLocation("Deathmatch", true).build();
		}
		return Bukkit.getWorld("UHC").getHighestBlockAt(Bukkit.getWorld("UHC").getSpawnLocation()).getLocation();
	}

	@Override
	public void addValues() {

	}
}
