package de.alphahelix.uhc.instances;

import de.popokaka.alphalibary.UUID.UUIDFetcher;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

/**
 * Created by AlphaHelixDev.
 */
public class PlayerInfo {

    private String name;
    private UUID uuid;
    private long kills, deaths, coins, points;
    private long normalC, uncommonC, rareC, superrareC, epicC, legendaryC;
    private String kits;

    public PlayerInfo(OfflinePlayer p, long kills, long deaths, long coins, long points, long normalC, long uncommonC, long rareC, long superrareC, long epicC, long legendaryC, String kits) {
        this.name = p.getName();
        this.uuid = UUIDFetcher.getUUID(p.getName());
        this.kills = kills;
        this.deaths = deaths;
        this.coins = coins;
        this.points = points;
        this.normalC = normalC;
        this.uncommonC = uncommonC;
        this.rareC = rareC;
        this.superrareC = superrareC;
        this.epicC = epicC;
        this.legendaryC = legendaryC;
        this.kits = kits;
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

    public long getNormalC() {
        return normalC;
    }

    public void setNormalC(long normalC) {
        this.normalC = normalC;
    }

    public long getUncommonC() {
        return uncommonC;
    }

    public void setUncommonC(long uncommonC) {
        this.uncommonC = uncommonC;
    }

    public long getRareC() {
        return rareC;
    }

    public void setRareC(long rareC) {
        this.rareC = rareC;
    }

    public long getSuperrareC() {
        return superrareC;
    }

    public void setSuperrareC(long superrareC) {
        this.superrareC = superrareC;
    }

    public long getEpicC() {
        return epicC;
    }

    public void setEpicC(long epicC) {
        this.epicC = epicC;
    }

    public long getLegendaryC() {
        return legendaryC;
    }

    public void setLegendaryC(long legendaryC) {
        this.legendaryC = legendaryC;
    }

    public String getKits() {
        return kits;
    }

    public void setKits(String kits) {
        this.kits = kits;
    }
}
