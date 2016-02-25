package de.alpha.uhc.files;

import org.bukkit.entity.Player;

import net.minetopix.library.main.file.SimpleFile;

public class PlayerFileManager {
	
	public SimpleFile getPlayerFile() {
        return new SimpleFile("plugins/UHC", "players.yml");
    }
	
	private SimpleFile file = getPlayerFile();
	
	public void addPlayer(Player p) {
		
		file.setDefault("Players."+p.getUniqueId().toString()+".name", p.getName());
		file.setDefault("Players."+p.getUniqueId().toString()+".kills", 0);
		file.setDefault("Players."+p.getUniqueId().toString()+".deaths", 0);
		
	}
	
	public int getPlayerKills(Player p) {
		
		return file.getInt("Players."+p.getUniqueId().toString()+".kills");
	}
	
	public int getPlayerDeaths(Player p) {
		
		return file.getInt("Players."+p.getUniqueId().toString()+".deaths");
	}
	
	public void addPlayerKill(Player p) {
		file.set("Players."+p.getUniqueId().toString()+".kills", getPlayerKills(p)+1);
		file.save();
	}
	
	public void addPlayerDeath(Player p) {
		file.set("Players."+p.getUniqueId().toString()+".deaths", getPlayerDeaths(p)+1);
		file.save();
	}

}
