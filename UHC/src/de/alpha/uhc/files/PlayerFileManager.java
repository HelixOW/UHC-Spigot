package de.alpha.uhc.files;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.file.SimpleFile;

public class PlayerFileManager {
	
	public static SimpleFile getPlayerFile() {
        return new SimpleFile("plugins/UHC", "players.yml");
    }
	
	private SimpleFile file = getPlayerFile();
	public static Inventory i;
	
	public void addPlayer(Player p) {
		
		file.setDefault("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".name", p.getName());
		file.setDefault("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".kills", 0);
		file.setDefault("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".deaths", 0);
		file.setDefault("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".coins", 0);
		file.setDefault("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".kits", "");
		
	}
	
	public int getPlayerCoins(Player p) {
		return file.getInt("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".coins");
	}
	
	public int getPlayerKills(Player p) {
		
		return file.getInt("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".kills");
	}
	
	public int getPlayerDeaths(Player p) {
		
		return file.getInt("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".deaths");
	}
	
	public String getPlayerKits(Player p) {
		return file.getString("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".kits");
	}
	
	public void addPlayerKit(Player p, String kit) {
		file.set("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".kits", getPlayerKits(p)+kit+",");
		file.save();
	}
	
	public void addPlayerKill(Player p) {
		file.set("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".kills", getPlayerKills(p)+1);
		file.save();
	}
	
	public void addPlayerDeath(Player p) {
		file.set("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".deaths", getPlayerDeaths(p)+1);
		file.save();
	}
	
	public void addPlayerCoins(Player p, int coins) {
		file.set("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".coins", getPlayerCoins(p)+coins);
		file.save();
	}
	
	public void removePlayerCoins(Player p, int coins) {
		file.set("Players."+UUIDFetcher.getUUID(p.getName()).toString()+".coins", getPlayerCoins(p)-coins);
		file.save();
	}
	
}
