package de.alphahelix.uhc.files;

import com.mojang.authlib.GameProfile;
import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.utils.GameProfileBuilder;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import org.bukkit.Material;

import java.io.IOException;
import java.util.UUID;

public class OptionsFile extends SimpleFile {

    public OptionsFile() {
        super("plugins/UHC-Remake","options.uhc");
    }

    public void addValues() {
        setDefault("Prefix", "&7[&6UHC&8-&6Remake&7] ");
        setDefault("Spectator prefix", "&7[&4X&7] &c");

        setDefault("Restartmessage", "&7The Server has stopped. Now loading a new round of UHC.");

        setDefault("Bungeecord", false);
        setDefault("Bungeecord fallbackserver", "lobby");
        setDefault("MySQL", false);
        setDefault("Debug", true);

        setDefault("Spawndispersal", 20);

        setDefault("Remove attack cooldown", true);

        setDefault("Pregenerate world.enabled", false);
        setDefault("Pregenerate world.size", 1000);

        setDefault("Tracker.equip", true);
        setInventoryItem("Tracker.item", new ItemBuilder(Material.COMPASS).setName("§dTracker").build(), 1);

        setDefault("Lobby.as schematic", false);
        setDefault("Lobby.filename", "lobby");
        setMaterial("Lobby.spawnblock.type", Material.DIAMOND_BLOCK);
        setDefault("Lobby.spawnblock.lower", 5);

        setDefault("Status MOTD", true);

        setDefault("Minimum players", 4);
        setDefault("Maximum players", 64);

        setDefault("Points.kill +", 50);
        setDefault("Points.death -", 25);
        setDefault("Points.win +", 500);

        setDefault("Coins.kill +", 50);
        setDefault("Coins.death -", 0);
        setDefault("Coins.win +", 500);

        setDefault("Command.kill", "");
        setDefault("Command.death", "");
        setDefault("Command.win", "");

        setDefault("Reward skin", "Zealock");
    }

    public GameProfile getRewardPlayer() {
        try {
            return GameProfileBuilder.fetch(UUIDFetcher.getUUID(getString("Reward skin")));
        } catch (IOException e) {
            e.printStackTrace();
            return new GameProfile(UUID.randomUUID(), "");
        }
    }

    public String getPrefix() {
        return getColorString("Prefix");
    }

    public String getSpectatorPrefix() {
        return getColorString("Spectator Prefix");
    }

    public String getRestart() {
        return getColorString("Restartmessage");
    }

    public boolean isBungeecord() {
        return getBoolean("Bungeecord");
    }

    public String getBungeecordFallbackserver() {
        return getString("Bungeecord fallbackserver");
    }

    public boolean isMySQL() {
        return getBoolean("MySQL");
    }

    public boolean isDebug() {
        return getBoolean("Debug");
    }

    public int getSpawndispersal() {
        return getInt("Spawndispersal");
    }

    public boolean isRemoveAttackCooldown() {
        return getBoolean("Remove attack cooldown");
    }

    public boolean isPregenerateWorldEnabled() {
        return getBoolean("Pregenerate world.enabled");
    }

    public int getPregenerateWorldSize() {
        return getInt("Pregenerate world.size");
    }

    public boolean isTrackerEquip() {
        return getBoolean("Tracker.equip");
    }

    public InventoryItem getTrackerItem() {
        return getInventoryItem("Tracker.item");
    }

    public boolean isLobbyAsSchematic() {
        return getBoolean("Lobby.as schematic");
    }

    public String getLobbyFileName() {
        return getString("Lobby.filename");
    }

    public Material getLobbySpawnBlock() {
        return getMaterial("Lobby.spawnblock.type");
    }

    public int getLobbySpawnBlockLowerPositionY() {
        return getInt("Lobby.spawnblock.lower");
    }

    public boolean isStatusMOTD() {
        return getBoolean("Status MOTD");
    }

    public int getMinimumPlayers() {
        return getInt("Minimum players");
    }

    public int getMaximumPlayers() {
        return getInt("Maximum players");
    }

    public int getPointsOnKill() {
        return getInt("Points.kill +");
    }

    public int getPointsOnDeath() {
        return getInt("Points.death -");
    }

    public int getPointsOnWin() {
        return getInt("Points.win +");
    }

    public int getCoinssOnKill() {
        return getInt("Coins.kill +");
    }

    public int getCoinsOnDeath() {
        return getInt("Coins.death -");
    }

    public int getCoinsOnWin() {
        return getInt("Coins.win +");
    }

    public String getCommandOnKill() {
        return getString("Command.kill");
    }

    public String getCommandOnDeath() {
        return getString("Command.death");
    }

    public String getCommandOnWin() {
        return getString("Command.win");
    }
}
