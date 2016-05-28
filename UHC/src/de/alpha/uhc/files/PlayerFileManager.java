package de.alpha.uhc.files;

import de.alpha.uhc.Core;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.file.SimpleFile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerFileManager {
	
	public PlayerFileManager(Core c) {
	}

    public  Inventory i;
    private final SimpleFile file = getPlayerFile();

    public  SimpleFile getPlayerFile() {
        return new SimpleFile("plugins/UHC", "players.yml");
    }

    public void addPlayer(Player p) {
        try {
            file.setDefault("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".name", p.getName());
            file.setDefault("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills", 0);
            file.setDefault("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths", 0);
            file.setDefault("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", 0);
            file.setDefault("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits", "");
        } catch (NullPointerException ignore){}

    }

    public int getPlayerCoins(Player p) {
        return file.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
    }

    public int getPlayerKills(Player p) {

        return file.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
    }

    public int getPlayerDeaths(Player p) {

        return file.getInt("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
    }

    public String getPlayerKits(Player p) {
        return file.getString("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits");
    }

    public void addPlayerKit(Player p, String kit) {
        file.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits", getPlayerKits(p) + kit + ",");
        file.save();
    }

    public void addPlayerKill(Player p) {
        file.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills", getPlayerKills(p) + 1);
        file.save();
    }

    public void addPlayerDeath(Player p) {
        file.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths", getPlayerDeaths(p) + 1);
        file.save();
    }

    public void addPlayerCoins(Player p, int coins) {
        file.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", getPlayerCoins(p) + coins);
        file.save();
    }

    public void removePlayerCoins(Player p, int coins) {
        file.set("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", getPlayerCoins(p) - coins);
        file.save();
    }

}
