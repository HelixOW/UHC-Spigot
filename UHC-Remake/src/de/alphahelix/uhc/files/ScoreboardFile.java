package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class ScoreboardFile extends EasyFile {

	public ScoreboardFile(UHC uhc) {
		super("scoreboard.uhc", uhc);
	}
	
	@Override
	public void addValues() {
		setDefault("Lobby.show.scoreboard", true);
		setDefault("Lobby.show.kills", true);
		setDefault("Lobby.show.deaths", true);
		setDefault("Lobby.show.coins", true);
		setDefault("Lobby.show.points", true);
		setDefault("Lobby.show.team", true);
		setDefault("Lobby.show.kit", true);
		setDefault("Lobby.show.bar", true);
		
		setDefault("Lobby.message.identifier ([i])", "&7>");
		
		setDefault("Lobby.title", "&7>-= &aLobby &7=-<");
		setDefault("Lobby.message.kills", " &cKills [i] [kills]");
		setDefault("Lobby.message.deaths", " &cDeaths [i] [deaths]");
		setDefault("Lobby.message.coins", " &6Coins [i] [coins]");
		setDefault("Lobby.message.points", " &bPoints [i] [points]");
		setDefault("Lobby.message.kit", " &aKit [i] [kit]");
		setDefault("Lobby.message.team", " &bTeam [i] [team]");
		setDefault("Lobby.message.bar", "&7>>----------<<");
		
		setDefault("Ingame.show.scoreboard", true);
		setDefault("Ingame.show.alive", true);
		setDefault("Ingame.show.specs", true);
		setDefault("Ingame.show.kit", true);
		setDefault("Ingame.show.team", true);
		setDefault("Ingame.show.center", true);
		setDefault("Ingame.show.border", true);
		setDefault("Ingame.show.time infos", true);
		setDefault("Ingame.show.bar", true);
		
		setDefault("Ingame.title", "&7> &cIngame &7<");
		setDefault("Ingame.message.alive", " &aAlive &7> [alive]");
		setDefault("Ingame.message.specs", " &cSpecs &7> [specs]");
		setDefault("Ingame.message.kit", " &6Kit &7> [kit]");
		setDefault("Ingame.message.team", " &bTeam &7> [team]");
		setDefault("Ingame.message.center", " &dCenter &7> [center]");
		setDefault("Ingame.message.border", " &4Border &7> [border]");
		setDefault("Ingame.message.time infos.until deathmatch", " &eDeathmatch &7> [time]");
		setDefault("Ingame.message.time infos.until damage", " &1Damage &7> [time]");
		setDefault("Ingame.message.time infos.until pvp", " &3PvP &7> [time]");
		setDefault("Ingame.message.time infos.deathmatch", " &eDeathmatch &7> now");
		setDefault("Ingame.message.end", " &3 &7> Restart");
		setDefault("Ingame.message.bar", "&7>>----------<<");
	}

}
