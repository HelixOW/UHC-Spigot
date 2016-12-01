package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class MainOptionsFile extends EasyFile {

    public MainOptionsFile(UHC uhc) {
        super("mainOptions.uhc", uhc);
    }

    public void addValues() {
        setDefault("Prefix", "&7[&6UHC&8-&6Remake&7] ");
        setDefault("Spectator Prefix", "&7[&4X&7] &c");

        setDefault("Restartmessage", "&7The Server has stopped. Now loading a new round of UHC.");

        setDefault("Bungeecord", false);
        setDefault("Bungeecord Fallbackserver", "lobby");

        setDefault("MySQL", false);

        setDefault("Soup", false);

        setDefault("debugging", true);

        setDefault("Spawndispersal", 20);

        setDefault("Remove Attack Cooldown", true);
        setDefault("Pregenerate World.enabled", true);
        setDefault("Pregenerate World.size", 1000);

        setDefault("Tracker.equip", true);
        setDefault("Tracker.name", "&dTracker");

        setDefault("Lobby.as schematic", true);
        setDefault("Lobby.filename", "lobby");
        setDefault("Lobby.spawnblock.type", "diamond block");
        setDefault("Lobby.spawnblock.lower", 5);

        setDefault("Status MOTD", true);

        setDefault("Minimum players", 4);
        setDefault("Maximum players", 64);

        setDefault("Points + on kill", 50);
        setDefault("Points - on death", 25);
        setDefault("Points + on win", 500);

        setDefault("Coins + on kill", 50);
        setDefault("Coins - on death", 25);
        setDefault("Coins + on win", 500);

        setDefault("Command on kill", "");
        setDefault("Command on death", "");
        setDefault("Command on win", "");
    }

    public void loadValues() {
        getPluginInstance().setPrefix(getColorString("Prefix"));
        getPluginInstance().setBunggeMode(getBoolean("Bungeecord"));
        getPluginInstance().setLobbyServer("Bungeecord Fallbackserver");
        getPluginInstance().setMySQLMode(getBoolean("MySQL"));
        getPluginInstance().setSoup(getBoolean("Soup"));
        getPluginInstance().setScenarios(getBoolean("Scenarios"));
        getPluginInstance().setSpawnradius(getInt("Spawndispersal"));
        getPluginInstance().setStatusMOTD(getBoolean("Status MOTD"));
    }
}
