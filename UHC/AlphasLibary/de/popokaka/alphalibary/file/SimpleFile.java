package de.popokaka.alphalibary.file;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;

public class SimpleFile extends YamlConfiguration {
	
	private File source = null;
	
	/**
	 * Create a new SimpleFile inside the given path with the name 'name'
	 * @param path the path where the file should be created in
	 * @param name the name which the file should have
	 */
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
	
	
	/**
	 * Convert a normal File into a SimpleFile
	 * @param f the old File which you want to convert
	 */
	public SimpleFile(File f) {
		source = f;
		createIfNotExist();
	}
	
	/**
	 * Finish the setup of the SimpleFile
	 */
	private void finishSetup() {
		try {
			load(source);
		} catch (Exception ignored) {

		}
	}
	
	/**
	 * Create a new SimpleFile if it's not existing
	 */
	private void createIfNotExist() {
		
		options().copyDefaults(true);
		if (source == null || !source.exists()) {
			try {
				source.createNewFile();
			} catch (IOException e) {
				new BukkitRunnable() { @Override public void run() {
						try { source.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
					}
				}.runTaskLaterAsynchronously(Core.getInstance(), 20);
			}
		}
		finishSetup();
	}
	
	/**
	 * Get a colored String out of e.g.(&aHey)
	 * @param path the path inside your file
	 * @return the String with Colors
	 */
	public String getColorString(String path) {
		if (!contains(path)) return "";

		try {
			String toReturn = getString(path);
			return toReturn.replaceAll("&", "ง");
		} catch (Exception e) { return ""; }
	}
	
	/**
	 * Save a ItemStackArray inside an file
	 * @param path The path inside the file where the ItemStackArray should be serialized to
	 * @param array The ItemStackArray you want to serialize
	 */
	public void setItemStackArray(String path, ItemStack[] array) {
		set(path, array);
		save();
	}

	/**
	 * Get a ItemStackArray out of an file
	 * @param path The path where the ItemStackArray should be serialized in
	 * @return The ItemStackArray at the given path
	 */
	public ItemStack[] getItemStackArray(String path) {
		java.util.List<?> var = getList(path);
		return var.toArray(new ItemStack[var.size()]);
	}

	/**
	 * Save & load the file
	 */
	public void save() {
		try { save(source); } catch (IOException ignored) {}
	}
	
	/**
	 * Add a new value to your file
	 * @param path The path where the value should be saved at
	 * @param value The value which you want to save inside your file
	 */
	public void setDefault(String path, Object value) {
		if (value instanceof String) value = ((String) value).replaceAll("ยง", "&");
		
		addDefault(path, value);
		save();
	}
}
