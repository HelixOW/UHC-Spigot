package de.alpha.uhc.files;

import de.alpha.border.Border;
import de.alpha.uhc.Core;
import de.alpha.uhc.Listener.DeathListener;
import de.alpha.uhc.Listener.InGameListener;
import de.alpha.uhc.Listener.MiningListener;
import de.alpha.uhc.Listener.PlayerJoinListener;
import de.alpha.uhc.Listener.SoupListener;
import de.alpha.uhc.manager.BorderManager;
import de.alpha.uhc.utils.Timer;
import net.minetopix.library.main.file.SimpleFile;

public class OptionsFileManager {
	
	//TODO: Scoreboard mit Prefix als Titel, Spieler left, Specs left
	
	public SimpleFile getConfigFile() {
        return new SimpleFile("plugins/UHC", "options.yml");
    }

    public void addOptions() {
        SimpleFile file = getConfigFile();
        
        file.setDefault("Prefix", "&7[&bUHC&7] ");
        
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
        
    }
	
	public void loadOptions() {
		SimpleFile file = getConfigFile();
		
		 Border.size = file.getInt("Border.size");
		 InGameListener.size = file.getInt("Border.size");
		Border.dmg = file.getDouble("Border.damage");
		
		Core.prefix = file.getColorString("Prefix");
		Core.isMySQLActive = file.getBoolean("MySQL");
		
		Timer.pc = file.getInt("Countdown.minimum_Player_Count");
		PlayerJoinListener.mpc = file.getInt("Countdown.maximum_Player_Count");
		
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
