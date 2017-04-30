package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;

public class LocationsFile extends SimpleFile {

    public LocationsFile() {
        super("plugins/UHC-Remake","locations.uhc");
    }

    public void addArmorStand(Location loc, String name) {
        setLocation("ArmorStands." + name, loc);
    }

    public void addStatsNPC(Location loc) {
        setLocation("StatsNPC", loc);
    }

    public void addRewardsNPC(Location loc) {
        setLocation("RewardsNPC", loc);

    }

    public void addRankingArmorStand(Location loc, int rank) {
        setLocation("Rankings." + rank, loc);
    }

    public void removeArmorStand(String name) {
        override("ArmorStands." + name, null);
    }

    public Location getLobby() {
        if (configContains("Lobby")) {
            if (UHC.isLobbyAsSchematic()) {
                return Bukkit.getWorld("UHC").getSpawnLocation().clone().add(0, 140, 0);
            } else {
                return getLocation("Lobby", true);
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

    public Location getDeathmatch() {
        if (configContains("Deathmatch")) {
            return getLocation("Deathmatch", true);
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
            return getLocation("StatsNPC", true);
        }
        return null;
    }

    public Location getRewardNPCLocation() {
        if (configContains("RewardsNPC")) {
            return getLocation("RewardsNPC", true);
        }
        return null;
    }

    public Location getRankingArmorstandLocation(int rank) {
        if (configContains("Rankings." + rank)) {
            return getLocation("Rankings." + rank, true);
        }
        return Bukkit.getWorlds().get(0).getSpawnLocation();
    }
}
