package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.UUID.UUIDFetcher;
import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.Kit;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PlayerFile extends SimpleFile<UHC> {

    public PlayerFile(UHC uhc) {
        super("players.uhc", uhc);
    }

    public boolean containsPlayer(Player p) {
        return configContains("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".name");
    }

    public void changeName(OfflinePlayer p) {
        override("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".name", p.getName());
    }

    public void setGames(OfflinePlayer p, long games) {
        override("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".games", games);
    }

    public void setKills(OfflinePlayer p, long kills) {
        override("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills", kills);
    }

    public void setDeaths(OfflinePlayer p, long deaths) {
        override("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths", deaths);
    }

    public void setCoins(OfflinePlayer p, long coins) {
        override("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins", coins);
    }

    public void addKit(OfflinePlayer p, Kit k) {
        if (isConfigurationSection("Players." + UUIDFetcher.getUUID(p.getName()).toString())) {
            if (getBase64ArgumentList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits").isEmpty()) {
                setBase64ArgumentList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits", k);
            }
        } else
            addBase64ArgumentsToList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits", k);
    }

    public void addAchievement(OfflinePlayer p, UHCAchievements achievement) {
        if (isConfigurationSection("Players." + UUIDFetcher.getUUID(p.getName()).toString())) {
            if (getBase64ArgumentList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".achievements").isEmpty()) {
                setBase64ArgumentList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".achievements", achievement);
            }
        } else
            addBase64ArgumentsToList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".achievements", achievement);
    }

    public void addCrate(OfflinePlayer p, Crate c) {
        if (isConfigurationSection("Players." + UUIDFetcher.getUUID(p.getName()).toString())) {
            if (getStringList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates").isEmpty()) {
                setArgumentList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates", c.getRawName());
            }
        } else
            addArgumentsToList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates", c.getRawName());
    }

    public List<String> getCrates(OfflinePlayer p) {
        return getStringList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates");
    }

    public void removeCrate(OfflinePlayer p, Crate c) {
        if (isConfigurationSection("Players." + UUIDFetcher.getUUID(p.getName()).toString())) {
            removeArgumentsFromList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates", c.getRawName());
        }
    }

    public void setPoints(OfflinePlayer p, long points) {
        override("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points", points);
    }

    public void setWins(OfflinePlayer p, long wins) {
        override("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".wins", wins);
    }

    public long getGames(OfflinePlayer p) {
        return getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".games");
    }

    public long getKills(OfflinePlayer p) {
        return getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kills");
    }

    public long getDeaths(OfflinePlayer p) {
        return getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".deaths");
    }

    public long getCoins(OfflinePlayer p) {
        return getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".coins");
    }

    public ArrayList<Kit> getKits(OfflinePlayer p) {
        return getBase64ArgumentList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".kits");
    }

    public ArrayList<UHCAchievements> getAchievements(OfflinePlayer p) {
        return getBase64ArgumentList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".achievements");
    }

    public long getCrate(OfflinePlayer p, Crate crate) {
        for(String c : getStringList("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".crates")) {
            if(c.equals(crate.getRawName())) return Crate.getCrateCount(Crate.getCrateByRawName(c), p);
        }
        return 0;
    }

    public long getPoints(OfflinePlayer p) {
        return getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".points");
    }

    public long getWins(OfflinePlayer p) {
        return getLong("Players." + UUIDFetcher.getUUID(p.getName()).toString() + ".wins");
    }

    public void addPlayer(Player p) {
        try {
            String uuid = UUIDFetcher.getUUID(p.getName()).toString();
            setDefault("Players." + uuid + ".name", p.getName());
            setDefault("Players." + uuid + ".games", 0);
            setDefault("Players." + uuid + ".kills", 0);
            setDefault("Players." + uuid + ".deaths", 0);
            setDefault("Players." + uuid + ".coins", 0);

            setDefault("Players." + uuid + ".crates.normal", 0);
            setDefault("Players." + uuid + ".crates.unccommon", 0);
            setDefault("Players." + uuid + ".crates.rare", 0);
            setDefault("Players." + uuid + ".crates.superrare", 0);
            setDefault("Players." + uuid + ".crates.epic", 0);
            setDefault("Players." + uuid + ".crates.legendary", 0);

            setDefault("Players." + uuid + ".points", 0);
            setDefault("Players." + uuid + ".wins", 0);

            if (!getString("Players." + uuid + ".name").equals(p.getName()))
                changeName(p);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Can't add given player.");
        }
    }

    @Override
    public void addValues() {
    }
}
