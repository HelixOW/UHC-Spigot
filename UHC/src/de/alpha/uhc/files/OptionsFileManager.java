package de.alpha.uhc.files;

import org.bukkit.Material;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.file.SimpleFile;

public class OptionsFileManager {
	
	private Core pl;
	private Registery r;
	
	public OptionsFileManager(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    public  SimpleFile getConfigFile() {
        return new SimpleFile("plugins/UHC", "options.yml");
    }

    public  void addOptions() {
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
        file.setDefault("InGame.Compassitemname", "&aPlayertracker");

        file.setDefault("Status Motd", true);

        file.setDefault("Spectator.Item", "magma_cream");
        file.setDefault("Spectator.Itemname", "&aPlayer Teleporter");
        file.setDefault("Spectator.GUI.Title", "&7-=X &cSpectator &7X=-");

        file.setDefault("Command.on End", true);
        file.setDefault("Command.to execute.on End", "coins add 100 [player]");
        file.setDefault("Command.on Death", true);
        file.setDefault("Command.to execute.on Death", "coins add 5 [player]");


    }

    public void loadOptions() {
        SimpleFile file = getConfigFile();

        r.getATeam().setMaterialName(file.getString("Team.Item"));
        r.getATeam().setTitle(file.getColorString("Team.GUI.Title"));
        r.getATeam().setBlockName(file.getString("Team.GUI.Block"));

        r.getSpectator().setSpecItem(file.getString("Spectator.Item"));
        r.getSpectator().setSpecName(file.getColorString("Spectator.Itemname"));
        r.getSpectator().setTitle(file.getColorString("Spectator.GUI.Title"));

        r.getMotdListener().setCustommotd(file.getBoolean("Status Motd"));

        pl.getRegistery().getAWorld().setWr(file.getBoolean("Reset World"));
        pl.getRegistery().getAWorld().setLobbyAsSchematic(file.getBoolean("Lobby.asSchematic"));

        r.getTimer().setPrePvP(file.getInt("Countdown.no PvP period in minutes"));
        r.getTimer().setTbpvp(file.getInt("Deathmatch.time before pvp in seconds"));
        r.getTimer().setDm(file.getBoolean("Deathmatch.enabled"));
        r.getTimer().setuDM(file.getInt("Deathmatch.begins after min"));
        r.getTimer().setBungeeMode(file.getBoolean("BungeeMode"));
        r.getTimer().setBungeeServer(file.getString("BungeeServer"));
        r.getTimer().setMax(file.getInt("Spawnradius"));
        r.getTimer().setPc(file.getInt("Countdown.minimum_Player_Count"));
        r.getTimer().setComMode(file.getBoolean("InGame.give Compass"));
        r.getTimer().setComName(file.getColorString("InGame.Compassitemname"));
        r.getTimer().setComItem(Material.getMaterial(file.getString("InGame.Compassitem").toUpperCase()));

        r.getGameEndListener().setBungeeMode(file.getBoolean("BungeeMode"));
        r.getGameEndListener().setBungeeServer(file.getString("BungeeServer"));
        r.getGameEndListener().setCmdEnd(file.getString("Command.to execute.on End"));
        r.getGameEndListener().setCmdOnEnd(file.getBoolean("Command.on End"));
        r.getGameEndListener().setCmdDeath(file.getString("Command.to execute.on Death"));
        r.getGameEndListener().setCmdOnDeath(file.getBoolean("Command.on Death"));

        r.getUHCCommand().setTeamMode(file.getBoolean("TeamMode"));

        r.getRegions().setMaterial(file.getString("Lobby.createTool"));
        r.getRegions().setLobby(file.getBoolean("Lobby.region"));

        r.getBorder().setSize(file.getInt("Border.size"));
        r.getBorder().setDmg(file.getDouble("Border.damage"));

        r.getInGameListener().setSize(file.getInt("Border.size"));

        Core.getInstance().setPrefix(file.getColorString("Prefix"));
        Core.getInstance().setMySQLActive(file.getBoolean("MySQL"));

        r.getPlayerJoinListener().setMpc(file.getInt("Countdown.maximum_Player_Count"));
        r.getPlayerJoinListener().setKitItem(Material.getMaterial(file.getString("Kit.item").toUpperCase()));
        r.getPlayerJoinListener().setKitName(file.getColorString("Kit.name"));
        r.getPlayerJoinListener().setKitMode(file.getBoolean("Kits"));
        r.getPlayerJoinListener().setTeamName(file.getColorString("Team.Name"));
        r.getPlayerJoinListener().setTeamItem(Material.getMaterial(file.getString("Team.Item").toUpperCase()));
        r.getPlayerJoinListener().setLeaveMode(file.getBoolean("Lobby.give leaveitem"));
        r.getPlayerJoinListener().setLeaveName(file.getColorString("Lobby.Leaveitemname"));
        r.getPlayerJoinListener().setLeaveItem(Material.getMaterial(file.getString("Lobby.Leaveitem").toUpperCase()));
        r.getPlayerJoinListener().setStartMode(file.getBoolean("Lobby.give startitem"));
        r.getPlayerJoinListener().setStartName(file.getColorString("Lobby.Startitemname"));
        r.getPlayerJoinListener().setStartItem(Material.getMaterial(file.getString("Lobby.Startitem").toUpperCase()));

        r.getSoupListener().setBoost(file.getDouble("Soup.healthboost"));

        r.getBorderManager().setMoveable(file.getBoolean("Border.getCloser"));
        r.getBorderManager().setMoving(file.getInt("Border.movingBlocks"));
        r.getBorderManager().setTime((file.getInt("Border.moving after min") * 20) * 60);
    }
}
