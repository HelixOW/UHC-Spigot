package de.alphahelix.uhc.files;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.PlayerInfo;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.OfflinePlayer;

import java.util.*;

public class PlayerStatsFile extends SimpleFile {

    public PlayerStatsFile() {
        super("plugins/UHC-Remake", "playerstats.uhc");
    }

    public void addPlayer(OfflinePlayer p) {
        String uuid = UUIDFetcher.getUUID(p.getName()).toString();

        if (configContains(uuid)) {
            if (!getName(UUIDFetcher.getUUID(p.getName())).equals(p.getName())) {
                override(uuid + ".name", p.getName());
            }
            return;
        }

        setDefault(uuid + ".name", p.getName());
        setDefault(uuid + ".infos", new PlayerInfo(p,
                0,
                0,
                0,
                0,
                0,
                0,
                new ArrayList<Kit>(),
                new ArrayList<UHCAchievements>(),
                new ArrayList<String>(),
                new Date()).encode());
    }

    public String getName(UUID id) {
        return getString(id + ".name");
    }

    public List<String> getUUIDS() {
        return Arrays.asList(getKeys(false).toArray(new String[getKeys(false).size()]));
    }

    public PlayerInfo getInfo(UUID id) {
        if (getUUIDS().contains(id.toString())) {
            return PlayerInfo.decode(getString(id + ".infos"));
        }
        return null;
    }

    public void updateInfo(UUID id, PlayerInfo info) {
        override(id + ".infos", info.encode());
    }

    //######################################################
    //## Getter
    //#######################################################

    public long getKills(UUID id) {
        return getInfo(id).getKills();
    }

    public long getDeaths(UUID id) {
        return getInfo(id).getDeaths();
    }

    public long getCoins(UUID id) {
        return getInfo(id).getCoins();
    }

    public long getPoints(UUID id) {
        return getInfo(id).getPoints();
    }

    public long getWins(UUID id) {
        return getInfo(id).getWins();
    }

    public long getGames(UUID id) {
        return getInfo(id).getGames();
    }

    public ArrayList<Kit> getKits(UUID id) {
        return getInfo(id).getKits();
    }

    public ArrayList<UHCAchievements> getAchievements(UUID id) {
        return getInfo(id).getAchievements();
    }

    public List<String> getCrates(UUID id) {
        return getInfo(id).getCrates();
    }

    public long getCrateCount(UUID id, Crate crate) {
        long count = 0;
        String qCrates = getCrates(id).toString().replace("[", "").replace("]", "").replace(", ", " ;");

        for (String crateNames : qCrates.split(" ;")) {
            if (Crate.getCrateByRawName(crateNames) == null) continue;
            if (Crate.getCrateByRawName(crateNames).equals(crate))
                count++;
        }
        return count;
    }

    public Date getNextReward(UUID id) {
        return getInfo(id).getNextReward();
    }

    //######################################################
    //## Kills and Deaths
    //#######################################################

    public void addKills(UUID id, long amount) {
        PlayerInfo info = getInfo(id);

        info.addKill(amount);

        updateInfo(id, info);
    }

    public void addDeaths(UUID id, long amount) {
        PlayerInfo info = getInfo(id);

        info.addDeath(amount);

        updateInfo(id, info);
    }

    //######################################################
    //## Coins
    //#######################################################

    public void addCoins(UUID id, long amount) {
        PlayerInfo info = getInfo(id);

        info.addCoins(amount);

        updateInfo(id, info);
    }

    public void removeCoins(UUID id, long amount) {
        PlayerInfo info = getInfo(id);

        info.removeCoins(amount);

        updateInfo(id, info);
    }

    //######################################################
    //## Points
    //#######################################################

    public void addPoints(UUID id, long amount) {
        PlayerInfo info = getInfo(id);

        info.addPoints(amount);

        updateInfo(id, info);
    }

    public void removePoints(UUID id, long amount) {
        PlayerInfo info = getInfo(id);

        info.removePoints(amount);

        updateInfo(id, info);
    }

    //######################################################
    //## Wins / Games
    //#######################################################

    public void addWins(UUID id, long amount) {

        PlayerInfo info = getInfo(id);

        info.addWin(amount);

        updateInfo(id, info);
    }

    public void addGames(UUID id, long amount) {

        PlayerInfo info = getInfo(id);

        info.addGame(amount);

        updateInfo(id, info);
    }

    //######################################################
    //## Kits / Achievements
    //#######################################################

    public void addKits(UUID id, Kit... kits) {

        PlayerInfo info = getInfo(id);

        info.addKits(kits);

        updateInfo(id, info);
    }

    public void addAchievemetns(UUID id, UHCAchievements... achievements) {

        PlayerInfo info = getInfo(id);

        info.addAchievement(achievements);

        updateInfo(id, info);
    }

    //######################################################
    //## Crates
    //#######################################################

    public void addCrate(UUID id, Crate crate) {

        PlayerInfo info = getInfo(id);

        info.addCrate(crate);

        updateInfo(id, info);
    }

    public void removeCrate(UUID id, Crate crate) {

        PlayerInfo info = getInfo(id);

        info.removeCrate(crate);

        updateInfo(id, info);
    }

    //######################################################
    //## Reward
    //#######################################################

    public void setNextReward(UUID id) {

        PlayerInfo info = getInfo(id);

        info.setNextReward(StatsUtil.getUHCRank(id).getRewardCooldownTime());

        updateInfo(id, info);
    }
}
