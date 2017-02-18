package de.alphahelix.uhc.instances;

import java.util.ArrayList;

/**
 * Created by AlphaHelixDev.
 */
public class UHCRank {

    private static ArrayList<UHCRank> ranks = new ArrayList<>();
    private String prefix;
    private String name;
    private long minWins, minKills, minPoints;

    public UHCRank(String prefix, String name, long minWins, long minKills, long minPoints) {
        this.prefix = prefix;
        this.name = name;
        this.minWins = minWins;
        this.minKills = minKills;
        this.minPoints = minPoints;
    }

    public static ArrayList<UHCRank> getRanks() {
        return ranks;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMinWins() {
        return minWins;
    }

    public void setMinWins(long minWins) {
        this.minWins = minWins;
    }

    public long getMinKills() {
        return minKills;
    }

    public void setMinKills(long minKills) {
        this.minKills = minKills;
    }

    public long getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(long minPoints) {
        this.minPoints = minPoints;
    }
}
