package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;

public class LocationsFile extends EasyFile {
	
	private static boolean isArenaDefaultWorld;

	public LocationsFile(UHC uhc) {
		super("locations.uhc", uhc);
	}

	public void setLobby(Location loc) {
		setLocation("Lobby", loc, true);
	}

	public void setArena(Location loc) {
		setLocation("Arena", loc, true);
	}

	public void setDeathmatch(Location loc) {
		setLocation("Deathmatch", loc, true);
	}

	public void setNetherArena(Location loc) {
		setLocation("Arena Nether", loc, true);
	}

	public void addArmorStand(Location loc, String name) {
		setLocation("ArmorStands." + name + ".location", loc, true);
	}

	public void addNPC(Location loc) {
		setLocation("StatsNPC", loc, true);
	}

	public void addRankingArmorStand(Location loc, int rank) {
		setLocation("Rankings.Armorstands." + rank, loc, true);
	}

	public void removeArmorStand(String name) {
		set("ArmorStands." + name, null);
		save();
	}

	public void removeNPC() {
		set("StatsNPC", null);
		save();
	}

	public void removeRankingArmorstand(int rank) {
		set("Rankings.Armorstands." + rank, null);
		save();
	}

	public void initalizeLobbyAndArena() {

		if (getString("Lobby") != null) {
			if (!(getLocation("Lobby", true) == null)) {
				if (getLocation("Lobby", true).build().getWorld() == null) {
					String[] values = getString("Lobby").split(",");

					Bukkit.createWorld(new WorldCreator(values[3]));
				}
			}
		}
		if (getString("Arena Nether") != null) {
			if (!(getLocation("Arena Nether", true) == null)) {
				if (getLocation("Arena Nether", true).build().getWorld() == null) {
					String[] values = getString("Arena Nether").split(",");

					Bukkit.createWorld(new WorldCreator(values[3]));
				}
			}
		}
		if (getString("Deathmatch") != null) {
			if (!(getLocation("Deathmatch", true) == null)) {
				if (getLocation("Deathmatch", true).build().getWorld() == null) {
					String[] values = getString("Deathmatch").split(",");

					Bukkit.createWorld(new WorldCreator(values[3]));
				}
			}
		}
	}

	public Location getLobby() {
		if (configContains("Lobby")) {
			if (getLocation("Lobby", true).build() != null)
				if(getPluginInstance().isLobbyAsSchematic()) {
					if(isArenaDefaultWorld)
						return getArena();
					return getArena().clone().add(0, 140, 0);
				}
				else
					return getLocation("Lobby", true).build();
		}
		return Bukkit.getWorlds().get(0).getHighestBlockAt(Bukkit.getWorlds().get(0).getSpawnLocation()).getLocation();
	}

	public Location getArena() {
		if (configContains("Arena")) {
			if (getLocation("Arena", true).build() != null)
				isArenaDefaultWorld = false;
				return getLocation("Arena", true).build();
		}
		if (Bukkit.getWorld("UHC") == null)
			return Bukkit.getWorld("world").getSpawnLocation();
		isArenaDefaultWorld = true;
		return Bukkit.getWorld("UHC").getHighestBlockAt(Bukkit.getWorld("UHC").getSpawnLocation()).getLocation();
	}
	
	public String getArenaWorldName() {
		if (configContains("Arena")) {
			if (getLocation("Arena", true).build() != null)
				return getString("Arena").split(",")[3];
		}
		if (Bukkit.getWorld("UHC") == null)
			return "world";
		return "UHC";
	}

	public Location getNetherArena() {
		if (configContains("Arena Nether")) {
			if (getLocation("Arena Nether", true).build() != null)
				return getLocation("Arena Nether", true).build();
		}
		if (Bukkit.getWorld("UHC-Nether") == null)
			return null;
		return Bukkit.getWorld("UHC-Nether").getSpawnLocation();
	}

	public Location getDeathmatch() {
		if (configContains("Deathmatch")) {
			if (getLocation("Deathmatch", true).build() != null)
				return getLocation("Deathmatch", true).build();
		}
		if (Bukkit.getWorld("UHC") == null)
			return null;
		return Bukkit.getWorld("UHC").getHighestBlockAt(Bukkit.getWorld("UHC").getSpawnLocation()).getLocation();
	}
	
	public Location getStatsNPCLocation() {
		if(configContains("StatsNPC")) {
			if (getLocation("StatsNPC", true).build() != null)
				return getLocation("StatsNPC", true).build();
		}
		return Bukkit.getWorlds().get(0).getSpawnLocation();
	}

	public Location getRankingNPCLocation(int rank) {
		if (contains("Rankings.Armorstands." + rank)) {
			if (getLocation("Rankings.Armorstands." + rank, true).build() != null)
				return getLocation("Rankings.Armorstands." + rank, true).build();
		}
		return Bukkit.getWorlds().get(0).getSpawnLocation();
	}

	@Override
	public void addValues() {
	}
}
