package de.alpha.uhc.files;

import org.bukkit.Material;

import de.alpha.border.Border;
import de.alpha.uhc.Core;
import de.alpha.uhc.Listener.DeathListener;
import de.alpha.uhc.Listener.InGameListener;
import de.alpha.uhc.Listener.MiningListener;
import de.alpha.uhc.Listener.PlayerJoinListener;
import de.alpha.uhc.Listener.SoupListener;
import de.alpha.uhc.commands.UHCCommand;
import de.alpha.uhc.manager.BorderManager;
import de.alpha.uhc.utils.Regions;
import de.alpha.uhc.utils.Timer;
import de.alpha.uhc.utils.WorldUtil;
import net.minetopix.library.main.file.SimpleFile;

public class OptionsFileManager {
	
	//TODO: Scoreboard mit Prefix als Titel, Spieler left, Specs left
	
	public static SimpleFile getConfigFile() {
        return new SimpleFile("plugins/UHC", "options.yml");
    }

    public void addOptions() {
        SimpleFile file = getConfigFile();
        
        file.setDefault("Prefix", "&7[&bUHC&7] ");
        file.setDefault("BungeeMode", false);
        file.setDefault("BungeeServer", "lobby");
        
        file.setDefault("Border.size", 3000);
        file.setDefault("Border.getCloser", true);
        file.setDefault("Border.movingBlocks", 500);
        file.setDefault("Border.moving after min", 10);
        file.setDefault("Border.damage", 2);
        
        file.setDefault("Countdown.lobby", 60);
        file.setDefault("Countdown.graceperiod", 60);
        file.setDefault("Countdown.minimum_Player_Count", 2);
        file.setDefault("Countdown.maximum_Player_Count", 24);
        
        file.setDefault("Drops.ores", true);
        file.setDefault("Drops.wood", true);
        file.setDefault("Drops.mobs", true);
        
        file.setDefault("Mine.CoalOre", true);
        file.setDefault("Mine.IronOre", true);
        file.setDefault("Mine.GoldOre", true);
        file.setDefault("Mine.DiamondOre", true);
        
        file.setDefault("Soup.healthboost", 3);
        
        file.setDefault("MySQL", false);
        
        file.setDefault("Kits", true);
        
        file.setDefault("Kit.item", "chest");
        file.setDefault("Kit.name", "&aKits");
        
        file.setDefault("TeamMode", true);
        
        file.setDefault("Coins.on Win", 100);
        file.setDefault("Coins.on Death", 5);
        
        file.setDefault("Worldreset", true);
        
        file.setDefault("Spawnradius", 20);
        
        file.setDefault("Lobby.region", false);
        file.setDefault("Lobby.createTool", "gold_axe");
        file.setDefault("Lobby.asSchematic", false);
        
    }
	
	public void loadOptions() {
		SimpleFile file = getConfigFile();
		
		WorldUtil.lobbySchematic = file.getBoolean("Lobby.asSchematic");
		
		InGameListener.BungeeMode = file.getBoolean("BungeeMode");
		InGameListener.BungeeServer = file.getString("BungeeServer");
		
		InGameListener.newWorld = file.getBoolean("Worldreset");
		
		UHCCommand.teamMode = file.getBoolean("TeamMode");
		
		Regions.material = file.getString("Lobby.createTool");
		Regions.lobby = file.getBoolean("Lobby.region");
		
		Timer.max = file.getInt("Spawnradius");
		
		InGameListener.reward = file.getInt("Coins.on Win");
		InGameListener.deathreward = file.getInt("Coins.on Death");
		
		 Border.size = file.getInt("Border.size");
		 InGameListener.size = file.getInt("Border.size");
		Border.dmg = file.getDouble("Border.damage");
		
		Core.prefix = file.getColorString("Prefix");
		Core.isMySQLActive = file.getBoolean("MySQL");
		
		Timer.pc = file.getInt("Countdown.minimum_Player_Count");
		PlayerJoinListener.mpc = file.getInt("Countdown.maximum_Player_Count");
		
		PlayerJoinListener.kitItem = Material.getMaterial(file.getString("Kit.item").toUpperCase());
		PlayerJoinListener.kitName = file.getColorString("Kit.name");
		PlayerJoinListener.kitMode = file.getBoolean("Kits");
		
		MiningListener.wood = file.getBoolean("Drops.wood");
		 MiningListener.ore = file.getBoolean("Drops.ores");
		 DeathListener.mobs = file.getBoolean("Drops.mobs");
		
		 SoupListener.boost = file.getDouble("Soup.healthboost");
		
		 BorderManager.moveable = file.getBoolean("Border.getCloser");
		 BorderManager.moving = file.getInt("Border.movingBlocks");
		 BorderManager.time = (file.getInt("Border.moving after min")*20)*60;
		
		 MiningListener.coal = file.getBoolean("Mine.CoalOre");
		 MiningListener.iron = file.getBoolean("Mine.IronOre");
		 MiningListener.gold = file.getBoolean("Mine.GoldOre");
		 MiningListener.dia = file.getBoolean("Mine.DiamondOre");
	}
}
