package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.uhc.UHC;

public class ScoreboardFile extends SimpleFile<UHC> {

    public ScoreboardFile(UHC uhc) {
        super("scoreboard.uhc", uhc);
    }

    public boolean isLobbyShow(String part) {
        return getBoolean("Lobby.show." + part);
    }

    public String getLobbyIdentifier() {
        return getColorString("Lobby.message.identifier ([i])");
    }

    public String getLobbyTitle() {
        return getColorString("Lobby.title");
    }

    public String getLobbyPart(String part) {
        return getColorString("Lobby.message." + part);
    }

    public boolean isIngameShow(String part) {
        return getBoolean("Ingame.show." + part);
    }

    public String getIngameIdentifier() {
        return getColorString("Ingame.message.identifier ([i])");
    }

    public String getIngameTitle() {
        return getColorString("Ingame.title");
    }

    public String getIngamePart(String part) {
        return getColorString("Ingame.message." + part);
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
        setDefault("Lobby.show.scenario", true);
        setDefault("Lobby.show.bar", true);

        setDefault("Lobby.message.identifier ([i])", "&7>");

        setDefault("Lobby.title", "&7>-= &aLobby &7=-<");
        setDefault("Lobby.message.kills", " &cKills [i] [kills]");
        setDefault("Lobby.message.deaths", " &cDeaths [i] [deaths]");
        setDefault("Lobby.message.coins", " &6Coins [i] [coins]");
        setDefault("Lobby.message.points", " &bPoints [i] [points]");
        setDefault("Lobby.message.kit", " &aKit [i] [kit]");
        setDefault("Lobby.message.scenario", " &4Scenario [i] [scenario]");
        setDefault("Lobby.message.team", " &bTeam [i] [team]");
        setDefault("Lobby.message.bar", "&7=------------=");

        setDefault("Ingame.show.scoreboard", true);
        setDefault("Ingame.show.alive", true);
        setDefault("Ingame.show.specs", true);
        setDefault("Ingame.show.kit", true);
        setDefault("Ingame.show.scenario", true);
        setDefault("Ingame.show.team", true);
        setDefault("Ingame.show.center", true);
        setDefault("Ingame.show.border", true);
        setDefault("Ingame.show.time infos", true);
        setDefault("Ingame.show.bar", true);

        setDefault("Ingame.title", "&7> &cIngame &7<");
        setDefault("Ingame.message.alive", " &aAlive &7> [alive]");
        setDefault("Ingame.message.specs", " &cSpecs &7> [specs]");
        setDefault("Ingame.message.kit", " &6Kit &7> [kit]");
        setDefault("Ingame.message.scenario", " &4Scenario [i] [scenario]");
        setDefault("Ingame.message.team", " &bTeam &7> [team]");
        setDefault("Ingame.message.center", " &dCenter &7> [center]");
        setDefault("Ingame.message.border", " &4Border &7> [border]");
        setDefault("Ingame.message.time infos.until deathmatch", " &eDeathmatch &7> [time]");
        setDefault("Ingame.message.time infos.until damage", " &1Damage &7> [time]");
        setDefault("Ingame.message.time infos.until pvp", " &3PvP &7> [time]");
        setDefault("Ingame.message.time infos.deathmatch", " &eDeathmatch &7> now");
        setDefault("Ingame.message.end", " &3 &7> Restart");
        setDefault("Ingame.message.bar", "&7=------------=");
    }

    public static class ScoreboardConstructFile extends SimpleFile<UHC> {

        public ScoreboardConstructFile(UHC uhc) {
            super("scoreboardConstruction.uhc", uhc);
        }

        public int getLobbyLines() {
            return getInt("Lobby.lines");
        }

        public String getLobbyLine(int line) {
            return getString("Lobby.line." + Integer.toString(line));
        }

        public int getIngameLines() {
            return getInt("Ingame.lines");
        }

        public String getIngameLine(int line) {
            return getString("Ingame.line." + Integer.toString(line));
        }

        @Override
        public void addValues() {
            setDefault("Lobby.lines", 16);

            setDefault("Lobby.line.16", "[out]");
            setDefault("Lobby.line.15", "[bar]");
            setDefault("Lobby.line.14", "[blank]");
            setDefault("Lobby.line.13", "[kills]");
            setDefault("Lobby.line.12", "[deaths]");
            setDefault("Lobby.line.11", "[blank]");
            setDefault("Lobby.line.10", "[bar]");
            setDefault("Lobby.line.9", "[blank]");
            setDefault("Lobby.line.8", "[coins]");
            setDefault("Lobby.line.7", "[points]");
            setDefault("Lobby.line.6", "[blank]");
            setDefault("Lobby.line.5", "[bar]");
            setDefault("Lobby.line.4", "[blank]");
            setDefault("Lobby.line.3", "[kit]");
            setDefault("Lobby.line.2", "[team]");
            setDefault("Lobby.line.1", "[bar]");

            setDefault("Ingame.lines", 16);

            setDefault("Ingame.line.16", "[out]");
            setDefault("Ingame.line.15", "[blank]");
            setDefault("Ingame.line.14", "[alive]");
            setDefault("Ingame.line.13", "[specs]");
            setDefault("Ingame.line.12", "[blank]");
            setDefault("Ingame.line.11", "[kit]");
            setDefault("Ingame.line.10", "[team]");
            setDefault("Ingame.line.9", "[blank]");
            setDefault("Ingame.line.8", "[center]");
            setDefault("Ingame.line.7", "[border]");
            setDefault("Ingame.line.6", "[blank]");
            setDefault("Ingame.line.5", "[time infos]");
            setDefault("Ingame.line.4", "[bar]");
            setDefault("Ingame.line.3", "[out]");
            setDefault("Ingame.line.2", "[out]");
            setDefault("Ingame.line.1", "[out]");
        }
    }
}