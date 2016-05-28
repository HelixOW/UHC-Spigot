package de.alpha.uhc.files;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.file.SimpleFile;

public class ScoreboardFile {
	
	private Core pl;
	private Registery r;
	
	public ScoreboardFile(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  final SimpleFile file = getScoreboardFile();

    public  void addScores() {

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

    private  SimpleFile getScoreboardFile() {
        return new SimpleFile("plugins/UHC", "scoreboard.yml");
    }

    public  void loadScores() {

        r.getAScoreboard().setShowLobbyScoreboard(file.getBoolean("Scoreboard.Lobby.show"));
        r.getAScoreboard().setShowLobbyKills(file.getBoolean("Scoreboard.Lobby.Kills.show"));
        r.getAScoreboard().setShowLobbyDeaths(file.getBoolean("Scoreboard.Lobby.Deaths.show"));
        r.getAScoreboard().setShowLobbyCoins(file.getBoolean("Scoreboard.Lobby.Coins.show"));
        r.getAScoreboard().setShowLobbyPlayercount(file.getBoolean("Scoreboard.Lobby.Playercount.show"));
        r.getAScoreboard().setShowLobbyTeam(file.getBoolean("Scoreboard.Lobby.Team.show"));
        r.getAScoreboard().setShowLobbyBar(file.getBoolean("Scoreboard.Lobby.Bar.show"));

        r.getAScoreboard().setLobbyTitle(file.getColorString("Scoreboard.Lobby.title"));
        r.getAScoreboard().setLobbyKills(file.getColorString("Scoreboard.Lobby.Kills.message"));
        r.getAScoreboard().setLobbyDeaths(file.getColorString("Scoreboard.Lobby.Deaths.message"));
        r.getAScoreboard().setLobbyCoins(file.getColorString("Scoreboard.Lobby.Coins.message"));
        r.getAScoreboard().setLobbyPlayercount(file.getColorString("Scoreboard.Lobby.Playercount.message"));
        r.getAScoreboard().setLobbyTeam(file.getColorString("Scoreboard.Lobby.Team.message"));
        r.getAScoreboard().setLobbyBar(file.getColorString("Scoreboard.Lobby.Bar.message"));

        r.getAScoreboard().setShowInGameScoreboard(file.getBoolean("Scoreboard.InGame.show"));
        r.getAScoreboard().setShowInGamePlayersLiving(file.getBoolean("Scoreboard.InGame.Living Players.show"));
        r.getAScoreboard().setShowInGameSpectators(file.getBoolean("Scoreboard.InGame.Spectators.show"));
        r.getAScoreboard().setShowInGameKit(file.getBoolean("Scoreboard.InGame.Kit.show"));
        r.getAScoreboard().setShowInGameTeam(file.getBoolean("Scoreboard.InGame.Team.show"));
        r.getAScoreboard().setShowInGameCenter(file.getBoolean("Scoreboard.InGame.Center.show"));
        r.getAScoreboard().setShowInGameBorder(file.getBoolean("Scoreboard.InGame.Border.show"));
        r.getAScoreboard().setShowInGamePvP(file.getBoolean("Scoreboard.InGame.Until Deathmatch.show"));
        r.getAScoreboard().setShowInGameBar(file.getBoolean("Scoreboard.InGame.Bar.show"));

        r.getAScoreboard().setIngameTitle(file.getColorString("Scoreboard.InGame.title"));
        r.getAScoreboard().setIngamePlayersLiving(file.getColorString("Scoreboard.InGame.Living Players.message"));
        r.getAScoreboard().setIngameSpectators(file.getColorString("Scoreboard.InGame.Spectators.message"));
        r.getAScoreboard().setIngameKit(file.getColorString("Scoreboard.InGame.Kit.message"));
        r.getAScoreboard().setIngameTeam(file.getColorString("Scoreboard.InGame.Team.message"));
        r.getAScoreboard().setIngameCenter(file.getColorString("Scoreboard.InGame.Center.message"));
        r.getAScoreboard().setIngameBorder(file.getColorString("Scoreboard.InGame.Border.message"));
        r.getAScoreboard().setIngamePvP(file.getColorString("Scoreboard.InGame.Until Deathmatch.message"));
        r.getAScoreboard().setIngamePvPmsg(file.getColorString("Scoreboard.InGame.Deathmatch.message"));
        r.getAScoreboard().setIngameBar(file.getColorString("Scoreboard.InGame.Bar.message"));

        r.getAScoreboard().setDmgin(file.getColorString("Scoreboard.InGame.Until Detahmatch.Until Damage.message"));
        r.getAScoreboard().setPvpin(file.getColorString("Scoreboard.InGame.Until Detahmatch.Until PvP.message"));

        r.getAScoreboard().setShowHealthUName(file.getBoolean("Scoreboard.Health.UnderName.show"));
        r.getAScoreboard().setShowHealthInTab(file.getBoolean("Scoreboard.Health.InTab.show"));
    }
}
