package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;

public class LocationsFile extends SimpleFile<UHC> {

    public LocationsFile(UHC uhc) {
        super("locations.uhc", uhc);
    }

    public void addArmorStand(Location loc, String name) {
        setLocation("ArmorStands." + name, loc);
    }

    public void addStatsNPC(Location loc) {
        setLocation("StatsNPC", loc);
    }

    public void addRankingArmorStand(Location loc, int rank) {
        setLocation("Rankings." + rank, loc);
    }

    public void removeArmorStand(String name) {
        override("ArmorStands." + name, null);
    }

    public void removeRankingArmorstand(int rank) {
        override("Rankings.Armorstands." + rank, null);
    }

    public Location getLobby() {
        if (configContains("Lobby")) {
            if (getPluginInstance().isLobbyAsSchematic()) {
                return getArena().clone().add(0, 140, 0);
            } else {
                return getLocation("Lobby");
            }
        } else if (Bukkit.getWorld("Lobby") == null) {
            Bukkit.createWorld(new WorldCreator("Lobby"));
            return Bukkit.getWorlds().get(0).getHighestBlockAt(Bukkit.getWorlds().get(0).getSpawnLocation()).getLocation();
        }
        return Bukkit.getWorld("Lobby").getHighestBlockAt(Bukkit.getWorld("Lobby").getSpawnLocation()).getLocation();
    }

    public void setLobby(Location loc) {
        setLocation("Lobby", loc);
    }

    public Location getArena() {
        if (configContains("Arena")) {
            return getLocation("Arena");
        } else if (Bukkit.getWorld("UHC") == null) {
            Bukkit.createWorld(new WorldCreator("UHC"));
        }
        return Bukkit.getWorld("UHC").getHighestBlockAt(Bukkit.getWorld("UHC").getSpawnLocation()).getLocation();
    }

    public void setArena(Location loc) {
        loc.setWorld(Bukkit.getWorld("UHC"));
        setLocation("Arena", loc);
    }

    public Location getNetherArena() {
        if (configContains("Arena Nether")) {
            return getLocation("Arena Nether");
        } else if (Bukkit.getWorld("UHC-Nether") == null)
            Bukkit.createWorld(new WorldCreator("UHC-Nether"));
        return Bukkit.getWorld("UHC-Nether").getSpawnLocation();
    }

    public void setNetherArena(Location loc) {
        loc.setWorld(Bukkit.getWorld("UHC-Nether"));
        setLocation("Arena Nether", loc);
    }

    public Location getDeathmatch() {
        if (configContains("Deathmatch")) {
            return getLocation("Deathmatch");
        }
        if (Bukkit.getWorld("UHC") == null)
            Bukkit.createWorld(new WorldCreator("UHC"));
        return Bukkit.getWorld("UHC").getHighestBlockAt(Bukkit.getWorld("UHC").getSpawnLocation()).getLocation();
    }

    public void setDeathmatch(Location loc) {
        loc.setWorld(Bukkit.getWorld("UHC"));
        setLocation("Deathmatch", loc);
    }

    public Location getStatsNPCLocation() {
        if (configContains("StatsNPC")) {
            return getLocation("StatsNPC");
        }
        return Bukkit.getWorlds().get(0).getSpawnLocation();
    }

    public Location getRankingArmorstandLocation(int rank) {
        if (contains("Rankings.Armorstands." + rank)) {
            return getLocation("Rankings.Armorstands." + rank);
        }
        return Bukkit.getWorlds().get(0).getSpawnLocation();
    }

    @Override
    public void addValues() {
    }
}
