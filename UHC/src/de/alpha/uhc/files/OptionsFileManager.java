package de.alpha.uhc.files;

import org.bukkit.Material;

import de.alpha.uhc.Core;
import de.alpha.uhc.Listener.GameEndListener;
import de.alpha.uhc.Listener.InGameListener;
import de.alpha.uhc.Listener.MotdListener;
import de.alpha.uhc.Listener.PlayerJoinListener;
import de.alpha.uhc.Listener.SoupListener;
import de.alpha.uhc.aclasses.ATeam;
import de.alpha.uhc.aclasses.AWorld;
import de.alpha.uhc.border.Border;
import de.alpha.uhc.border.BorderManager;
import de.alpha.uhc.commands.UHCCommand;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.Regions;
import de.alpha.uhc.utils.Spectator;
import de.popokaka.alphalibary.file.SimpleFile;

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
		
		ATeam.setMaterialName(file.getString("Team.Item"));
		ATeam.setTitle(file.getColorString("Team.GUI.Title"));
		ATeam.setBlockName(file.getString("Team.GUI.Block"));
		
		Spectator.setSpecItem(file.getString("Spectator.Item"));
		Spectator.setSpecName(file.getColorString("Spectator.Itemname"));
		Spectator.setTitle(file.getColorString("Spectator.GUI.Title"));
		
		MotdListener.setCustommotd(file.getBoolean("Status Motd"));
		
		AWorld.setWr(file.getBoolean("Reset World"));
		AWorld.setLobbyAsSchematic(file.getBoolean("Lobby.asSchematic"));
		
		Timer.setPrePvP(file.getInt("Countdown.no PvP period in minutes"));
		Timer.setTbpvp(file.getInt("Deathmatch.time before pvp in seconds"));
		Timer.setDm(file.getBoolean("Deathmatch.enabled"));
		Timer.setuDM(file.getInt("Deathmatch.begins after min"));
		Timer.setBungeeMode(file.getBoolean("BungeeMode"));
		Timer.setBungeeServer(file.getString("BungeeServer"));
		Timer.setMax(file.getInt("Spawnradius"));
		Timer.setPc(file.getInt("Countdown.minimum_Player_Count"));
		Timer.setComMode(file.getBoolean("InGame.give Compass"));
		Timer.setComName(file.getColorString("InGame.Compassitemname"));
		Timer.setComItem(Material.getMaterial(file.getString("InGame.Compassitem").toUpperCase()));
		
		GameEndListener.setBungeeMode(file.getBoolean("BungeeMode"));
		GameEndListener.setBungeeServer(file.getString("BungeeServer"));
		GameEndListener.setReward(file.getInt("Coins.on Win"));
		GameEndListener.setDeathreward(file.getInt("Coins.on Death"));
		
		UHCCommand.setTeamMode(file.getBoolean("TeamMode"));
		
		Regions.setMaterial(file.getString("Lobby.createTool"));
		Regions.setLobby(file.getBoolean("Lobby.region"));
		
		Border.setSize(file.getInt("Border.size"));
		Border.setDmg(file.getDouble("Border.damage"));
		
		InGameListener.setSize(file.getInt("Border.size"));
		
		Core.setPrefix(file.getColorString("Prefix"));
		Core.setMySQLActive(file.getBoolean("MySQL"));
		
		PlayerJoinListener.setMpc(file.getInt("Countdown.maximum_Player_Count"));
		PlayerJoinListener.setKitItem(Material.getMaterial(file.getString("Kit.item").toUpperCase()));
		PlayerJoinListener.setKitName(file.getColorString("Kit.name"));
		PlayerJoinListener.setKitMode(file.getBoolean("Kits"));
		PlayerJoinListener.setTeamName(file.getColorString("Team.Name"));
		PlayerJoinListener.setTeamItem(Material.getMaterial(file.getString("Team.Item").toUpperCase()));
		PlayerJoinListener.setLeaveMode(file.getBoolean("Lobby.give leaveitem"));
		PlayerJoinListener.setLeaveName(file.getColorString("Lobby.Leaveitemname"));
		PlayerJoinListener.setLeaveItem(Material.getMaterial(file.getString("Lobby.Leaveitem").toUpperCase()));
		PlayerJoinListener.setStartMode(file.getBoolean("Lobby.give startitem"));
		PlayerJoinListener.setStartName(file.getColorString("Lobby.Startitemname"));
		PlayerJoinListener.setStartItem(Material.getMaterial(file.getString("Lobby.Startitem").toUpperCase()));
		
		SoupListener.setBoost(file.getDouble("Soup.healthboost"));
		
		BorderManager.setMoveable(file.getBoolean("Border.getCloser"));
		BorderManager.setMoving(file.getInt("Border.movingBlocks"));
		BorderManager.setTime((file.getInt("Border.moving after min")*20)*60);
	}
}
