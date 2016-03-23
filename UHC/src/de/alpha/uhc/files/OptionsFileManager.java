package de.alpha.uhc.files;

import org.bukkit.Material;

import de.alpha.border.Border;
import de.alpha.uhc.Core;
import de.alpha.uhc.Listener.GameEndListener;
import de.alpha.uhc.Listener.InGameListener;
import de.alpha.uhc.Listener.MotdListener;
import de.alpha.uhc.Listener.PlayerJoinListener;
import de.alpha.uhc.Listener.SoupListener;
import de.alpha.uhc.aclasses.ATeam;
import de.alpha.uhc.aclasses.AWorld;
import de.alpha.uhc.commands.UHCCommand;
import de.alpha.uhc.manager.BorderManager;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.Regions;
import de.alpha.uhc.utils.Spectator;
import net.minetopix.library.main.file.SimpleFile;

public class OptionsFileManager {
	
	public static SimpleFile getConfigFile() {
        return new SimpleFile("plugins/UHC", "options.yml");
    }

    public static void addOptions() {
        SimpleFile file = getConfigFile();
        
        file.setDefault("Prefix", "&7[&bUHC&7] ");
        file.setDefault("BungeeMode", false);
        file.setDefault("BungeeServer", "lobby");
        file.setDefault("Reset World", true);
        
        file.setDefault("Border.size", 3000);
        file.setDefault("Border.getCloser", true);
        file.setDefault("Border.movingBlocks", 500);
        file.setDefault("Border.moving after min", 10);
        file.setDefault("Border.damage", 2);
        
        file.setDefault("Countdown.lobby", 60);
        file.setDefault("Countdown.graceperiod", 60);
        file.setDefault("Countdown.no PvP period in minutes", 5);
        file.setDefault("Countdown.minimum_Player_Count", 2);
        file.setDefault("Countdown.maximum_Player_Count", 24);
        
        file.setDefault("Deathmatch.enabled", true);
        file.setDefault("Deathmatch.begins after min", 30);
        file.setDefault("Deathmatch.time before pvp in seconds", 30);
        
        file.setDefault("Soup.healthboost", 3);
        
        file.setDefault("MySQL", false);
        
        file.setDefault("Kits", true);
        
        file.setDefault("Kit.item", "chest");
        file.setDefault("Kit.name", "&aKits");
        
        file.setDefault("TeamMode", true);
        
        file.setDefault("Team.Item", "blaze_rod");
        file.setDefault("Team.Name", "&bTeams");
        file.setDefault("Team.GUI.Title", "&7-=X &bTeams &7X=-");
        file.setDefault("Team.GUI.Block", "stained_clay");
        
        file.setDefault("Coins.on Win", 100);
        file.setDefault("Coins.on Death", 5);
        
        file.setDefault("Spawnradius", 20);
        
        file.setDefault("Lobby.region", false);
        file.setDefault("Lobby.createTool", "gold_axe");
        file.setDefault("Lobby.asSchematic", false);
        
        file.setDefault("Lobby.give startitem", true);
        file.setDefault("Lobby.Startitem", "nether_star");
        file.setDefault("Lobby.Startitemname", "&5Start UHC");
        
        file.setDefault("Lobby.give leaveitem", true);
        file.setDefault("Lobby.Leaveitem", "redstone");
        file.setDefault("Lobby.Leaveitemname", "&cLeave");
        
        file.setDefault("InGame.give Compass", true);
        file.setDefault("InGame.Compassitem", "compass");
        file.setDefault("InGame.Compassitemname", "§aPlayertracker");
        
        file.setDefault("Status Motd", true);
        
        file.setDefault("Spectator.Item", "magma_cream");
        file.setDefault("Spectator.Itemname", "&aPlayer Teleporter");
        file.setDefault("Spectator.GUI.Title", "&7-=X &cSpectator &7X=-");
        
        
    }
	
	public static void loadOptions() {
		SimpleFile file = getConfigFile();
		
		ATeam.materialName = file.getString("Team.Item");
		ATeam.title = file.getColorString("Team.GUI.Title");
		ATeam.blockName = file.getString("Team.GUI.Block");
		
		Spectator.specItem = file.getString("Spectator.Item");
		Spectator.specName = file.getColorString("Spectator.Itemname");
		Spectator.title = file.getColorString("Spectator.GUI.Title");
		
		MotdListener.custommotd = file.getBoolean("Status Motd");
		
		AWorld.wr = file.getBoolean("Reset World");
		AWorld.lobbyAsSchematic = file.getBoolean("Lobby.asSchematic");
		
		Timer.prePvP = file.getInt("Countdown.no PvP period in minutes");
		Timer.tbpvp = file.getInt("Deathmatch.time before pvp in seconds");
		Timer.dm = file.getBoolean("Deathmatch.enabled");
		Timer.uDM = file.getInt("Deathmatch.begins after min");
		Timer.BungeeMode = file.getBoolean("BungeeMode");
		Timer.BungeeServer = file.getString("BungeeServer");
		Timer.max = file.getInt("Spawnradius");
		Timer.pc = file.getInt("Countdown.minimum_Player_Count");
		Timer.comMode = file.getBoolean("InGame.give Compass");
		Timer.comName = file.getColorString("InGame.Compassitemname");
		Timer.comItem = Material.getMaterial(file.getString("InGame.Compassitem").toUpperCase());
		
		GameEndListener.BungeeMode = file.getBoolean("BungeeMode");
		GameEndListener.BungeeServer = file.getString("BungeeServer");
		GameEndListener.reward = file.getInt("Coins.on Win");
		GameEndListener.deathreward = file.getInt("Coins.on Death");
		
		UHCCommand.teamMode = file.getBoolean("TeamMode");
		
		Regions.material = file.getString("Lobby.createTool");
		Regions.lobby = file.getBoolean("Lobby.region");
		
		Border.size = file.getInt("Border.size");
		Border.dmg = file.getDouble("Border.damage");
		
		InGameListener.size = file.getInt("Border.size");
		
		Core.prefix = file.getColorString("Prefix");
		Core.isMySQLActive = file.getBoolean("MySQL");
		
		PlayerJoinListener.mpc = file.getInt("Countdown.maximum_Player_Count");
		PlayerJoinListener.kitItem = Material.getMaterial(file.getString("Kit.item").toUpperCase());
		PlayerJoinListener.kitName = file.getColorString("Kit.name");
		PlayerJoinListener.kitMode = file.getBoolean("Kits");
		PlayerJoinListener.teamName = file.getColorString("Team.Name");
		PlayerJoinListener.teamItem = Material.getMaterial(file.getString("Team.Item").toUpperCase());
		PlayerJoinListener.leaveMode = file.getBoolean("Lobby.give leaveitem");
		PlayerJoinListener.leaveName = file.getColorString("Lobby.Leaveitemname");
		PlayerJoinListener.leaveItem = Material.getMaterial(file.getString("Lobby.Leaveitem").toUpperCase());
		PlayerJoinListener.startMode = file.getBoolean("Lobby.give startitem");
		PlayerJoinListener.startName = file.getColorString("Lobby.Startitemname");
		PlayerJoinListener.startItem = Material.getMaterial(file.getString("Lobby.Startitem").toUpperCase());
		
		SoupListener.boost = file.getDouble("Soup.healthboost");
		
		BorderManager.moveable = file.getBoolean("Border.getCloser");
		BorderManager.moving = file.getInt("Border.movingBlocks");
		BorderManager.time = (file.getInt("Border.moving after min")*20)*60;
	}
}
