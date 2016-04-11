package de.popokaka.alphalibary;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.popokaka.alphalibary.mysql.MySQLAPI;

public class AlphaLibary extends JavaPlugin {
	
	private static AlphaLibary libary;
	private String PREFIX = "§7[§3AlphaLibary§7] §r";
	
	/**
	 * Get the main class of the AlphaLibary
	 * @return the main class of the AlphaLibary
	 * @author AlphaHelix
	 */
	public static AlphaLibary getLibary() {
		return libary;
	}
	
	/**
	 * Get the console of the plugin's server
	 * @return The console of the plugin's server
	 * @author AlphaHelix
	 */
	public ConsoleCommandSender getConsole() {
		return Bukkit.getConsoleSender();
	}
	
	/**
	 * Get the prefix of this plugin(Standard: §7[§3AlphaLibary§7])
	 * @return The prefix of this plugin
	 * @author AlphaHelix
	 */
	public String getPrefix() {
		return PREFIX;
	}
	
	@Override
	public void onEnable() {
		libary = this;
		
		MySQLAPI.initMySQLAPI(libary);
		
		getConsole().sendMessage(getPrefix() + "has been loaded");
	}
}
