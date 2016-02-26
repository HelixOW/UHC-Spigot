package de.alpha.uhc.files;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.minetopix.library.main.file.SimpleFile;

public class PlayerFileManager {
	
	public static SimpleFile getPlayerFile() {
        return new SimpleFile("plugins/UHC", "players.yml");
    }
	
	private SimpleFile file = getPlayerFile();
	public static Inventory i;
	
	public void addPlayer(Player p) {
		
		file.setDefault("Players."+p.getUniqueId().toString()+".name", p.getName());
		file.setDefault("Players."+p.getUniqueId().toString()+".kills", 0);
		file.setDefault("Players."+p.getUniqueId().toString()+".deaths", 0);
		file.setDefault("Players."+p.getUniqueId().toString()+".coins", 0);
		file.setDefault("Players."+p.getUniqueId().toString()+".kits", "");
		
	}
	
	public int getPlayerCoins(Player p) {
		return file.getInt("Players."+p.getUniqueId().toString()+".coins");
	}
	
	public int getPlayerKills(Player p) {
		
		return file.getInt("Players."+p.getUniqueId().toString()+".kills");
	}
	
	public int getPlayerDeaths(Player p) {
		
		return file.getInt("Players."+p.getUniqueId().toString()+".deaths");
	}
	
	public String getPlayerKits(Player p) {
		return file.getString("Players."+p.getUniqueId().toString()+".kits");
	}
	
	public void addPlayerKit(Player p, String kit) {
		file.set("Players."+p.getUniqueId().toString()+".kits", getPlayerKits(p)+kit+",");
		file.save();
	}
	
	public void addPlayerKill(Player p) {
		file.set("Players."+p.getUniqueId().toString()+".kills", getPlayerKills(p)+1);
		file.save();
	}
	
	public void addPlayerDeath(Player p) {
		file.set("Players."+p.getUniqueId().toString()+".deaths", getPlayerDeaths(p)+1);
		file.save();
	}
	
	public void addPlayerCoins(Player p, int coins) {
		file.set("Players."+p.getUniqueId().toString()+".coins", getPlayerCoins(p)+coins);
		file.save();
	}
	
	public void removePlayerCoins(Player p, int coins) {
		file.set("Players."+p.getUniqueId().toString()+".coins", getPlayerCoins(p)-coins);
		file.save();
	}
	
}
