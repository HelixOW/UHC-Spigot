package de.alphahelix.uhc.instances;

import de.alphahelix.alphalibary.utils.SerializationUtil;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.Serializable;
import java.util.*;

public class PlayerInfo implements Serializable {

    private static final transient ArrayList<PlayerInfo> INFOS = new ArrayList<>();
    private static final transient SerializationUtil<PlayerInfo> SERIALIZER = new SerializationUtil<>();

    private UUID owner;
    private String name;
    private long kills, deaths, coins, points, wins, games;
    private ArrayList<Kit> kits;
    private ArrayList<UHCAchievements> achievements;
    private List<String> crates;
    private Date nextReward;

    public PlayerInfo(UUID owner, String name, long kills, long deaths, long coins, long points, long wins, long games, ArrayList<Kit> kits, ArrayList<UHCAchievements> achievements, List<String> crates, Date nextReward) {
        this.owner = owner;
        this.name = name;
        this.kills = kills;
        this.deaths = deaths;
        this.coins = coins;
        this.points = points;
        this.wins = wins;
        this.games = games;
        this.kits = kits;
        this.achievements = achievements;
        this.crates = crates;
        this.nextReward = nextReward;
        Crate.setCrates(Bukkit.getOfflinePlayer(owner), crates.toString().replace("[", "").replace("]", "").replace(", ", " ;"));

        if (getPlayerInfo(owner) == null) INFOS.add(this);
    }

    public PlayerInfo(OfflinePlayer p, long kills, long deaths, long coins, long points, long wins, long games, ArrayList<Kit> kits, ArrayList<UHCAchievements> achievements, List<String> crates, Date nextReward) {
        this.owner = UUIDFetcher.getUUID(p);
        this.name = p.getName();
        this.kills = kills;
        this.deaths = deaths;
        this.coins = coins;
        this.points = points;
        this.wins = wins;
        this.games = games;
        this.kits = kits;
        this.achievements = achievements;
        this.crates = crates;
        this.nextReward = nextReward;

        Crate.setCrates(p, crates.toString().replace("[", "").replace("]", "").replace(", ", " ;"));

        if (getPlayerInfo(owner) == null) INFOS.add(this);
    }

    public static PlayerInfo getPlayerInfo(UUID id) {
        for (PlayerInfo playerInfo : INFOS) {
            if (id.equals(playerInfo.getOwner())) return playerInfo;
        }
        return null;
    }

    public static PlayerInfo decode(String json) {
        PlayerInfo info = SERIALIZER.deserialize(SerializationUtil.stringToJson(json));

        if (getPlayerInfo(info.owner) != null)
            INFOS.remove(info);

        INFOS.add(info);
        return info;
    }

    public String encode() {
        return SerializationUtil.jsonToString(SERIALIZER.serialize(this));
    }

    public void removePlayerInfo() {
        INFOS.remove(this);
    }

    public UUID getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public long getKills() {
        return kills;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getCoins() {
        return coins;
    }

    public long getPoints() {
        return points;
    }

    public long getWins() {
        return wins;
    }

    public long getGames() {
        return games;
    }

    public ArrayList<Kit> getKits() {
        return kits;
    }

    public ArrayList<UHCAchievements> getAchievements() {
        return achievements;
    }

    public List<String> getCrates() {
        return crates;
    }

    public Date getNextReward() {
        return nextReward;
    }

    //######################################################
    //## Kills / Deaths
    //#######################################################

    public void setNextReward(int unit) {
        nextReward = TimeUtil.increaseDate(unit);
    }

    public void addKill(long amount) {
        this.kills = getKills() + amount;
    }

    //######################################################
    //## Coins
    //#######################################################

    public void addDeath(long amount) {
        this.deaths = getDeaths() + amount;
    }

    public void addCoins(long amount) {
        this.coins = getCoins() + amount;
    }

    //######################################################
    //## Points
    //#######################################################

    public void removeCoins(long amount) {
        if (getCoins() - amount <= 0) {
            this.coins = 0;
        } else {
            this.coins = getCoins() - amount;
        }
    }

    public void addPoints(long amount) {
        this.points = getPoints() + amount;
    }

    //######################################################
    //## Wins
    //#######################################################

    public void removePoints(long amount) {
        if (getPoints() - amount <= 0) {
            this.points = 0;
        } else {
            this.points = getPoints() - amount;
        }
    }

    public void addWin(long amount) {
        this.wins = wins + amount;
    }

    //######################################################
    //## Kits / Achievements
    //#######################################################

    public void addGame(long amount) {
        this.games = games + amount;
    }

    public void addKits(Kit... kits) {
        Collections.addAll(this.kits, kits);
    }

    //######################################################
    //## Crates
    //#######################################################

    public void addAchievement(UHCAchievements... achievements) {
        Collections.addAll(this.achievements, achievements);
    }

    public void addCrate(Crate crate) {
        this.crates.add(crate.getRawName());
    }

    //######################################################
    //## Reward
    //#######################################################

    public void removeCrate(Crate crate) {
        this.crates.remove(crate.getRawName());
    }
}
