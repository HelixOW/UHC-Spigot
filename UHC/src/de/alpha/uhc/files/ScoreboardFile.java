package de.alpha.uhc.files;

import de.alpha.uhc.aclasses.AScoreboard;
import net.minetopix.library.main.file.SimpleFile;

public class ScoreboardFile {
	
	public static SimpleFile getScoreboardFile() {
		return new SimpleFile("plugins/UHC", "scoreboard.yml");
	}
	
	private static SimpleFile file = getScoreboardFile();
	
	public static void addScores() {
		
		file.setDefault("Scoreboard.Lobby.show", true);
		file.setDefault("Scoreboard.Lobby.title", "&7-=X &aLobby &7X=-");
		
		file.setDefault("Scoreboard.Lobby.Kills.show", true);
		file.setDefault("Scoreboard.Lobby.Kills.message", "&7Your &cKills&7: &c[kills]");
		
		file.setDefault("Scoreboard.Lobby.Deaths.show", true);
		file.setDefault("Scoreboard.Lobby.Deaths.message", "&7Your &cDeaths&7: &c[deaths]");
		
		file.setDefault("Scoreboard.Lobby.Coins.show", true);
		file.setDefault("Scoreboard.Lobby.Coins.message", "&7Your &6Coins&7: &6[coins]");
		
		file.setDefault("Scoreboard.Lobby.Playercount.show", true);
		file.setDefault("Scoreboard.Lobby.Playercount.message", "&7Online &8Players&7: &8[playercount]");
		
		file.setDefault("Scoreboard.Lobby.Team.show", true);
		file.setDefault("Scoreboard.Lobby.Team.message", "&7Your &bTeam&7: [team]");
		
		file.setDefault("Scoreboard.Lobby.Kit.show", true);
		file.setDefault("Scoreboard.Lobby.Kit.message", "&7Your &aKit&7: &a[kit]");
		
		file.setDefault("Scoreboard.Lobby.Bar.show", true);
		file.setDefault("Scoreboard.Lobby.Bar.message", "&7--------------");
		
		file.setDefault("Scoreboard.InGame.show", true);
		file.setDefault("Scoreboard.InGame.title", "&7-=X &cInGame &7X=-");
		
		file.setDefault("Scoreboard.InGame.Living Players.show", true);
		file.setDefault("Scoreboard.InGame.Living Players.message", "&aLiving Players&7: &a[livingPlayerscount]");
		
		file.setDefault("Scoreboard.InGame.Spectators.show", true);
		file.setDefault("Scoreboard.InGame.Spectators.message", "&cSpectators&7: &c[spectatorcount]");
		
		file.setDefault("Scoreboard.InGame.Kit.show", true);
		file.setDefault("Scoreboard.InGame.Kit.message", "&7Your &aKit&7: &a[kit]");
		
		file.setDefault("Scoreboard.InGame.Team.show", true);
		file.setDefault("Scoreboard.InGame.Team.message", "&7Your &bTeam&7: [team]");
		
		file.setDefault("Scoreboard.InGame.Center.show", true);
		file.setDefault("Scoreboard.InGame.Center.message", "&2Center&7: &2[distanceToCenter] &7Blocks away");
		
		file.setDefault("Scoreboard.InGame.Border.show", true);
		file.setDefault("Scoreboard.InGame.Border.message", "&4Border&7: &4[bordersize] &7Blocks");
		
		file.setDefault("Scoreboard.InGame.Until Deathmatch.show", true);
		file.setDefault("Scoreboard.InGame.Until Deathmatch.message", "&eDeathmatch in&7: &e[time] &7minutes");
		file.setDefault("Scoreboard.InGame.Until Detahmatch.Until Damage.message", "&dDamage in&7: &d[time] &7seconds");
		file.setDefault("Scoreboard.InGame.Until Detahmatch.Until PvP.message", "&6PvP in&7: &6[time] &7minutes");
		
		file.setDefault("Scoreboard.InGame.Deathmatch.message", "&eDeathmatch&7: &enow");
		
		file.setDefault("Scoreboard.InGame.Bar.show", true);
		file.setDefault("Scoreboard.InGame.Bar.message", "&7--------------");
		
		file.setDefault("Scoreboard.Health.UnderName.show", true);
		file.setDefault("Scoreboard.Health.InTab.show", true);
	}
	
	
	public static void loadScores() {
		
		AScoreboard.ShowLobbyScoreboard = file.getBoolean("Scoreboard.Lobby.show");
		AScoreboard.ShowLobbyKills = file.getBoolean("Scoreboard.Lobby.Kills.show");
		AScoreboard.ShowLobbyDeaths = file.getBoolean("Scoreboard.Lobby.Deaths.show");
		AScoreboard.ShowLobbyCoins = file.getBoolean("Scoreboard.Lobby.Coins.show");
		AScoreboard.ShowLobbyPlayercount = file.getBoolean("Scoreboard.Lobby.Playercount.show");
		AScoreboard.ShowLobbyTeam = file.getBoolean("Scoreboard.Lobby.Team.show");
		AScoreboard.ShowLobbyKit = file.getBoolean("Scoreboard.Lobby.Kit.show");
		AScoreboard.ShowLobbyBar = file.getBoolean("Scoreboard.Lobby.Bar.show");
		
		AScoreboard.lobbyTitle = file.getColorString("Scoreboard.Lobby.title");
		AScoreboard.lobbyKills = file.getColorString("Scoreboard.Lobby.Kills.message");
		AScoreboard.lobbyDeaths = file.getColorString("Scoreboard.Lobby.Deaths.message");
		AScoreboard.lobbyCoins = file.getColorString("Scoreboard.Lobby.Coins.message");
		AScoreboard.lobbyPlayercount = file.getColorString("Scoreboard.Lobby.Playercount.message");
		AScoreboard.lobbyTeam = file.getColorString("Scoreboard.Lobby.Team.message");
		AScoreboard.lobbyKit = file.getColorString("Scoreboard.Lobby.Kit.message");
		AScoreboard.lobbyBar = file.getColorString("Scoreboard.Lobby.Bar.message");
		
		AScoreboard.ShowInGameScoreboard = file.getBoolean("Scoreboard.InGame.show");
		AScoreboard.ShowInGamePlayersLiving = file.getBoolean("Scoreboard.InGame.Living Players.show");
		AScoreboard.ShowInGameSpectators = file.getBoolean("Scoreboard.InGame.Spectators.show");
		AScoreboard.ShowInGameKit = file.getBoolean("Scoreboard.InGame.Kit.show");
		AScoreboard.ShowInGameTeam = file.getBoolean("Scoreboard.InGame.Team.show");
		AScoreboard.ShowInGameCenter = file.getBoolean("Scoreboard.InGame.Center.show");
		AScoreboard.ShowInGameBorder = file.getBoolean("Scoreboard.InGame.Border.show");
		AScoreboard.ShowInGamePvP = file.getBoolean("Scoreboard.InGame.Until Deathmatch.show");
		AScoreboard.ShowInGameBar = file.getBoolean("Scoreboard.InGame.Bar.show");
		
		AScoreboard.ingameTitle = file.getColorString("Scoreboard.InGame.title");
		AScoreboard.ingamePlayersLiving = file.getColorString("Scoreboard.InGame.Living Players.message");
		AScoreboard.ingameSpectators = file.getColorString("Scoreboard.InGame.Spectators.message");
		AScoreboard.ingameKit = file.getColorString("Scoreboard.InGame.Kit.message");
		AScoreboard.ingameTeam = file.getColorString("Scoreboard.InGame.Team.message");
		AScoreboard.ingameCenter = file.getColorString("Scoreboard.InGame.Center.message");
		AScoreboard.ingameBorder = file.getColorString("Scoreboard.InGame.Border.message");
		AScoreboard.ingamePvP = file.getColorString("Scoreboard.InGame.Until Deathmatch.message");
		AScoreboard.ingamePvPmsg = file.getColorString("Scoreboard.InGame.Deathmatch.message");
		AScoreboard.ingameBar = file.getColorString("Scoreboard.InGame.Bar.message");
		
		AScoreboard.dmgin = file.getColorString("Scoreboard.InGame.Until Detahmatch.Until Damage.message");
		AScoreboard.pvpin = file.getColorString("Scoreboard.InGame.Until Detahmatch.Until PvP.message");
		
		AScoreboard.ShowHealthUName = file.getBoolean("Scoreboard.Health.UnderName.show");
		AScoreboard.ShowHealthInTab = file.getBoolean("Scoreboard.Health.InTab.show");
	}
}
