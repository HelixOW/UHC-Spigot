package de.alpha.uhc.files;

import de.alpha.uhc.aclasses.AScoreboard;
import de.popokaka.alphalibary.file.SimpleFile;

public class ScoreboardFile {
	
	public ScoreboardFile() {
		// TODO Auto-generated constructor stub
	}

    private static final SimpleFile file = getScoreboardFile();

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

    private static SimpleFile getScoreboardFile() {
        return new SimpleFile("plugins/UHC", "scoreboard.yml");
    }

    public static void loadScores() {

        AScoreboard.setShowLobbyScoreboard(file.getBoolean("Scoreboard.Lobby.show"));
        AScoreboard.setShowLobbyKills(file.getBoolean("Scoreboard.Lobby.Kills.show"));
        AScoreboard.setShowLobbyDeaths(file.getBoolean("Scoreboard.Lobby.Deaths.show"));
        AScoreboard.setShowLobbyCoins(file.getBoolean("Scoreboard.Lobby.Coins.show"));
        AScoreboard.setShowLobbyPlayercount(file.getBoolean("Scoreboard.Lobby.Playercount.show"));
        AScoreboard.setShowLobbyTeam(file.getBoolean("Scoreboard.Lobby.Team.show"));
        AScoreboard.setShowLobbyBar(file.getBoolean("Scoreboard.Lobby.Bar.show"));

        AScoreboard.setLobbyTitle(file.getColorString("Scoreboard.Lobby.title"));
        AScoreboard.setLobbyKills(file.getColorString("Scoreboard.Lobby.Kills.message"));
        AScoreboard.setLobbyDeaths(file.getColorString("Scoreboard.Lobby.Deaths.message"));
        AScoreboard.setLobbyCoins(file.getColorString("Scoreboard.Lobby.Coins.message"));
        AScoreboard.setLobbyPlayercount(file.getColorString("Scoreboard.Lobby.Playercount.message"));
        AScoreboard.setLobbyTeam(file.getColorString("Scoreboard.Lobby.Team.message"));
        AScoreboard.setLobbyBar(file.getColorString("Scoreboard.Lobby.Bar.message"));

        AScoreboard.setShowInGameScoreboard(file.getBoolean("Scoreboard.InGame.show"));
        AScoreboard.setShowInGamePlayersLiving(file.getBoolean("Scoreboard.InGame.Living Players.show"));
        AScoreboard.setShowInGameSpectators(file.getBoolean("Scoreboard.InGame.Spectators.show"));
        AScoreboard.setShowInGameKit(file.getBoolean("Scoreboard.InGame.Kit.show"));
        AScoreboard.setShowInGameTeam(file.getBoolean("Scoreboard.InGame.Team.show"));
        AScoreboard.setShowInGameCenter(file.getBoolean("Scoreboard.InGame.Center.show"));
        AScoreboard.setShowInGameBorder(file.getBoolean("Scoreboard.InGame.Border.show"));
        AScoreboard.setShowInGamePvP(file.getBoolean("Scoreboard.InGame.Until Deathmatch.show"));
        AScoreboard.setShowInGameBar(file.getBoolean("Scoreboard.InGame.Bar.show"));

        AScoreboard.setIngameTitle(file.getColorString("Scoreboard.InGame.title"));
        AScoreboard.setIngamePlayersLiving(file.getColorString("Scoreboard.InGame.Living Players.message"));
        AScoreboard.setIngameSpectators(file.getColorString("Scoreboard.InGame.Spectators.message"));
        AScoreboard.setIngameKit(file.getColorString("Scoreboard.InGame.Kit.message"));
        AScoreboard.setIngameTeam(file.getColorString("Scoreboard.InGame.Team.message"));
        AScoreboard.setIngameCenter(file.getColorString("Scoreboard.InGame.Center.message"));
        AScoreboard.setIngameBorder(file.getColorString("Scoreboard.InGame.Border.message"));
        AScoreboard.setIngamePvP(file.getColorString("Scoreboard.InGame.Until Deathmatch.message"));
        AScoreboard.setIngamePvPmsg(file.getColorString("Scoreboard.InGame.Deathmatch.message"));
        AScoreboard.setIngameBar(file.getColorString("Scoreboard.InGame.Bar.message"));

        AScoreboard.setDmgin(file.getColorString("Scoreboard.InGame.Until Detahmatch.Until Damage.message"));
        AScoreboard.setPvpin(file.getColorString("Scoreboard.InGame.Until Detahmatch.Until PvP.message"));

        AScoreboard.setShowHealthUName(file.getBoolean("Scoreboard.Health.UnderName.show"));
        AScoreboard.setShowHealthInTab(file.getBoolean("Scoreboard.Health.InTab.show"));
    }
}
