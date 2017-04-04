package de.alphahelix.uhc.instances;

import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.StatsUtil;
import de.alphahelix.uhc.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by AlphaHelixDev.
 */
public class UHCRank {

    private static ArrayList<UHCRank> ranks = new ArrayList<>();
    private String prefix;
    private String name;
    private long minWins, minKills, minPoints;
    private String rewardcooldowntime;
    private String rewardCommand;

    public UHCRank(String prefix, String name, long minWins, long minKills, long minPoints, String rewardCommand, String rewardcooldowntime) {
        this.prefix = prefix;
        this.name = name;
        this.minWins = minWins;
        this.minKills = minKills;
        this.minPoints = minPoints;
        this.rewardCommand = rewardCommand;
        this.rewardcooldowntime = rewardcooldowntime;
    }

    public static ArrayList<UHCRank> getRanks() {
        return ranks;
    }

    public String getPrefix() {
        return prefix;
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

    public long getMinKills() {
        return minKills;
    }

    public long getMinPoints() {
        return minPoints;
    }

    public String getRewardCommand() {
        return rewardCommand;
    }

    public String getRewardcooldowntime() {
        return rewardcooldowntime;
    }

    public int getRewardCooldownTime() {
        if (getRewardcooldowntime().equalsIgnoreCase("hour")) {
            return Calendar.HOUR_OF_DAY;
        } else if (getRewardcooldowntime().equalsIgnoreCase("day")) {
            return Calendar.DAY_OF_MONTH;
        } else if (getRewardcooldowntime().equalsIgnoreCase("week")) {
            return Calendar.WEEK_OF_MONTH;
        } else if (getRewardcooldowntime().equalsIgnoreCase("month")) {
            return Calendar.MONTH;
        } else {
            return Calendar.MILLISECOND;
        }
    }

    public void rewardPlayer(Player p) {
        UUID id = UUIDFetcher.getUUID(p);
        if (StatsUtil.canClaimReward(id)) {
            if (getRewardCommand().equalsIgnoreCase("-")) {
                p.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getNoReward());
                return;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getRewardCommand().replace("[player]", p.getName()));

            StatsUtil.setNextRewardTime(id);
        } else {
            p.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getRewardCooldown(TimeUtil.getRemainingTimeTillNextReward(StatsUtil.getNextReward(id))));
        }
    }
}
