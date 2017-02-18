package de.alphahelix.uhc.instances;

import de.alphahelix.alphalibary.UUID.UUIDFetcher;
import de.alphahelix.uhc.enums.UHCAchievements;
import org.bukkit.OfflinePlayer;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by AlphaHelixDev.
 */
public class PlayerInfo {

    private OfflinePlayer p;
    private String name;
    private UUID uuid;
    private long games, kills, deaths, coins, points, wins;
    private List<Kit> kits;
    private List<UHCAchievements> achievements;
    private String cratenames;

    public PlayerInfo(OfflinePlayer p, long games, long kills, long deaths, long coins, long points, long wins, List<Kit> kits, List<UHCAchievements> achievements, String crates) {
        this.p = p;
        this.name = p.getName();
        this.uuid = UUIDFetcher.getUUID(p.getName());
        this.games = games;
        this.kills = kills;
        this.deaths = deaths;
        this.coins = coins;
        this.points = points;
        this.wins = wins;
        this.kits = kits;
        this.achievements = achievements;
        this.cratenames = crates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getGames() {
        return games;
    }

    public void setGames(long games) {
        this.games = games;
    }

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public List<Kit> getKits() {
        return kits;
    }

    public void addKit(Kit... kit) {
        Collections.addAll(this.kits, kit);
    }

    public List<UHCAchievements> getAchievements() {
        return achievements;
    }

    public void addAchievement(UHCAchievements... achievement) {
        Collections.addAll(this.achievements, achievement);
    }

    public String getCrates() {
        return cratenames;
    }

    public long getCrateCount(Crate crate) {
        long count = 0;
        for (String c : getCrates().split(";")) {
            if (c.equalsIgnoreCase(crate.getRawName())) count++;
        }
        return count;
    }

    public void addCrate(String crate) {
        cratenames += crate + ";";
        Crate.addCrate(Crate.getCrateByRawName(crate), p);
    }

    public void removeCrate(Crate crate) {
        cratenames = cratenames.replaceFirst(crate.getRawName() + ";", "");
        Crate.removeCrate(crate, p);
    }
}
