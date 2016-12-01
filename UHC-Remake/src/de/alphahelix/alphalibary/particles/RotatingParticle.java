package de.alphahelix.alphalibary.particles;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RotatingParticle {

    private Player p;
    private double t;
    private double r;
    private Effect part;

    private int id;
    private int data;

    private float offX;
    private float offY;
    private float offZ;

    private float speed;
    private int count;

    public RotatingParticle(Player player, double t, double radius, Effect particle, int id, int data, float oX,
                            float oY, float oZ, float speed, int count) {
        this.p = player;
        this.t = t;
        this.r = radius;
        this.part = particle;

        this.id = id;
        this.data = data;

        this.offX = oX;
        this.offY = oY;
        this.offZ = oZ;

        this.speed = speed;
        this.count = count;
    }

    public void rotateAroundYAxis() {
        Location loc = p.getLocation();
        t = t + Math.PI / 16;
        double x = r * Math.cos(t);
        double z = r * Math.sin(t);
        Vector v = new Vector(x, 0, z);
        v = rotateAroundAxisY(v, 10);
        loc.add(v.getX(), v.getY(), v.getZ());

        p.spigot().playEffect(loc, part, id, data, offX, offY, offZ, speed, count, (int) r);
        loc.subtract(v.getX(), v.getY(), v.getZ());
    }

    public void rotateAroundXAxis() {
        Location loc = p.getLocation();

        t = t + Math.PI / 16;
        double x = r * Math.cos(t);
        double z = r * Math.sin(t);
        Vector v = new Vector(x, 0, z);
        v = rotateAroundAxisX(v, 10);
        loc.add(v.getX(), v.getY(), v.getZ());

        p.spigot().playEffect(loc, part, id, data, offX, offY, offZ, speed, count, (int) r);
        loc.subtract(v.getX(), v.getY(), v.getZ());
    }

    public void rotateAroundZAxis() {
        Location loc = p.getLocation();
        t = t + Math.PI / 16;
        double x = r * Math.cos(t);
        double z = r * Math.sin(t);
        Vector v = new Vector(x, 0, z);
        v = rotateAroundAxisZ(v, 10);
        loc.add(v.getX(), v.getY(), v.getZ());

        p.spigot().playEffect(loc, part, id, data, offX, offY, offZ, speed, count, (int) r);
        loc.subtract(v.getX(), v.getY(), v.getZ());
    }

    private Vector rotateAroundAxisX(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    private Vector rotateAroundAxisY(Vector v, double angle) {
        angle = -angle;
        angle = Math.toRadians(angle);
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    private Vector rotateAroundAxisZ(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos - v.getY() * sin;
        y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }
}
