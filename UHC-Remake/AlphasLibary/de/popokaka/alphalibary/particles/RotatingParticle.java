package de.popokaka.alphalibary.particles;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RotatingParticle {

	private Player p;
	private double t;
	private double r;
	private Effect part;
	private int[] intData;
	private float[] floatData;

	public RotatingParticle(Player player, double t, double radius, Effect particle, int[] iData, float[] fData) {
		this.p = player;
		this.t = t;
		this.r = radius;
		this.part = particle;
		this.intData = iData;
		this.floatData = fData;
	}

	public void rotateAroundYAxis() {
		Location loc = p.getLocation();
		t = t + Math.PI / 16;
		double x = r * Math.cos(t);
		double z = r * Math.sin(t);
		Vector v = new Vector(x, 0, z);
		v = rotateAroundAxisY(v, 10);
		loc.add(v.getX(), v.getY(), v.getZ());

		p.spigot().playEffect(loc, part, intData[0], intData[1], floatData[0], floatData[1], floatData[2], floatData[3],
				intData[2], intData[3]);
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

		p.spigot().playEffect(loc, part, intData[0], intData[1], floatData[0], floatData[1], floatData[2], floatData[3],
				intData[2], intData[3]);
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

		p.spigot().playEffect(loc, part, intData[0], intData[1], floatData[0], floatData[1], floatData[2], floatData[3],
				intData[2], intData[3]);
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
