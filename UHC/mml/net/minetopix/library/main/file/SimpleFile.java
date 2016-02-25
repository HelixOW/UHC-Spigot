package net.minetopix.library.main.file;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import net.minetopix.library.main.instance.NotInitLocation;

public class SimpleFile extends YamlConfiguration {

	private File source = null;

	public SimpleFile(String path, String name) {
		source = new File(path, name);
		createIfNotExist();
	}

	public SimpleFile(Plugin plugin, String name) {
		if (plugin == null) {
			return;
		}
		source = new File(plugin.getDataFolder().getPath(), name);
		createIfNotExist();
	}

	public SimpleFile(File f) {
		source = f;
		createIfNotExist();
	}

	private void finishSetup() {
		try {
			load(source);
		} catch (Exception e) {

		}
	}

	private void createIfNotExist() {
			
		options().copyDefaults(true);
		if (source == null || !source.exists()) {
			try {
				source.createNewFile();
			} catch (IOException e) {
				new BukkitRunnable() {
					
					@Override
					public void run() {

						try {
							source.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
				}.runTaskLater(Core.getInstance(), 20);
			}
		}
		finishSetup();
	}

	public String getColorString(String path) {

		if (!contains(path)) {
			return "";
		}

		try {
			String toReturn = getString(path);
			return toReturn.replaceAll("&", "§");
		} catch (Exception e) {
			return "";
		}
	}

	public void setDefault(String path, Object value) {
		if (value instanceof String) {
			value = ((String) value).replaceAll("§", "&");
		}
		addDefault(path, value);
		save();
	}

	/*
	 * Location Util
	 */

	public void setLocation(String path, Location loc, boolean deserialized) {
		if (deserialized) {
			String location = loc.getX() + "," + loc.getY() + "," + loc.getZ()
					+ "," + String.valueOf(loc.getWorld().getName()) + ","
					+ loc.getYaw() + "," + loc.getPitch();
			set(path, location);
		} else {
			set(path + ".x", loc.getX());
			set(path + ".y", loc.getY());
			set(path + ".z", loc.getZ());
			set(path + ".world", loc.getWorld().getName());
			set(path + ".yaw", loc.getYaw());
			set(path + ".pitch", loc.getPitch());
		}
		save();
	}

	public NotInitLocation getLocation(String path, boolean serialized) {

		if (serialized) {
			try {

				String s = getString(path);
				String[] array = s.split(",");
				double x = Double.valueOf(array[0]);
				double y = Double.valueOf(array[1]);
				double z = Double.valueOf(array[2]);
				String world = array[3];
				float yaw = Float.valueOf(array[4]);
				float pitch = Float.valueOf(array[5]);

				NotInitLocation loc = new NotInitLocation(x, y, z, world, yaw,
						pitch);

				return loc;
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println("Die Location war nicht deserialized");
			}
			return null;
		} else {

			double x;
			double y;
			double z;
			String world = "";
			float yaw = 0F;
			float pitch = 0F;

			try {
				x = getDouble(path + ".x");
				y = getDouble(path + ".y");
				z = getDouble(path + ".z");
			} catch (Exception e) {
				System.out.println("Location " + path + ": "
						+ ChatColor.DARK_RED + "Konnte nicht gelesen werden!");
				return null;
			}

			try {
				world = String.valueOf(get(path + ".world"));
			} catch (Exception e) {
				System.out.println("Location " + path + ": Weltname nicht vorhanden!");
			}
			try {
				yaw = getLong(path + ".yaw");
				pitch = getLong(path + ".pitch");
			} catch (Exception e) {
				System.out.println("Location " + path + ": Weltname nicht vorhanden!");
			}

			NotInitLocation loc = new NotInitLocation(x, y, z, world, yaw,
					pitch);
			return loc;
		}
	}

	/*
	 * Inventory
	 */

	public void setItemStackArray(String path, ItemStack[] array) {
		set(path, array);
		save();
	}

	public ItemStack[] getItemStackArray(String path) {
		ItemStack[] items = getList(path).toArray(new ItemStack[0]);
		return items;
	}

	public void save() {
		try {
			save(source);
		} catch (IOException e) {

		}
	}
}
