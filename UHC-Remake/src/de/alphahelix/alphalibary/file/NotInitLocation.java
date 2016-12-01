package de.alphahelix.alphalibary.file;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class NotInitLocation {

    String worldName = "";
    float yaw = 0F;
    float pitch = 0F;
    private double x = 0D;
    private double y = 0D;
    private double z = 0D;

    public NotInitLocation(double x, double y, double z, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldName = world;
    }

    public NotInitLocation(double x, double y, double z, String world,
                           float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldName = world;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public NotInitLocation inWorld(String worldName) {
        NotInitLocation toReturn = this;

        this.worldName = worldName;

        return toReturn;
    }

    public NotInitLocation align(NotInitLocation p) {
        NotInitLocation toReturn = this;

        Location loc = this.build();
        loc.setDirection(p.build().toVector().subtract(loc.toVector()));
        toReturn.yaw = loc.getYaw();
        toReturn.pitch = loc.getPitch();

        return toReturn;
    }

    public Location build() {
        try {
            return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        } catch (Exception e) {
            return null;
        }
    }

}
