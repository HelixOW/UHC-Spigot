package de.popokaka.alphalibary.utils;

import org.bukkit.Location;

public class LocationUtil {

	public static boolean isInside(Location loc, Location l1, Location l2) {
		int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
		int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
		int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
		int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());

		return loc.getX() >= x1 && loc.getX() <= x2 && loc.getZ() >= z1 && loc.getZ() <= z2;
	}
}
