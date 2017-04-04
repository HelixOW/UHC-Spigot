package de.alphahelix.uhc.files;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.instances.UHCRank;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by AlphaHelixDev.
 */
public class RanksFile extends SimpleFile {

    public RanksFile() {
        super("ranks.uhc");
    }

    @Override
    public void addValues() {
        setDefault("Rookie.min.kills", 0);
        setDefault("Rookie.min.wins", 0);
        setDefault("Rookie.min.points", 0);
        setDefault("Rookie.prefix", "&7{&8Rookie&7} ");
        setDefault("Rookie.reward.command", "-");
        setDefault("Rookie.reward.cooldown", "day");

        setDefault("Semi-Pro.min.kills", 5);
        setDefault("Semi-Pro.min.wins", 0);
        setDefault("Semi-Pro.min.points", 0);
        setDefault("Semi-Pro.prefix", "&7{Semi-Pro} ");
        setDefault("Semi-Pro.reward.command", "-");
        setDefault("Semi-Pro.reward.cooldown", "day");

        setDefault("Pro.min.kills", 25);
        setDefault("Pro.min.wins", 1);
        setDefault("Pro.min.points", 0);
        setDefault("Pro.prefix", "&7{&0Pro&7} ");
        setDefault("Pro.reward.command", "-");
        setDefault("Pro.reward.cooldown", "week");

        setDefault("Veteran.min.kills", 50);
        setDefault("Veteran.min.wins", 5);
        setDefault("Veteran.min.points", 0);
        setDefault("Veteran.prefix", "&7{&6Veteran&7} ");
        setDefault("Veteran.reward.command", "-");
        setDefault("Veteran.reward.cooldown", "week");

        setDefault("Expert.min.kills", 75);
        setDefault("Expert.min.wins", 10);
        setDefault("Expert.min.points", 0);
        setDefault("Expert.prefix", "&7{&5Expert&7} ");
        setDefault("Expert.reward.command", "-");
        setDefault("Expert.reward.cooldown", "month");

        setDefault("Master.min.kills", 100);
        setDefault("Master.min.wins", 25);
        setDefault("Master.min.points", 500);
        setDefault("Master.prefix", "&7{&eMaster&7} ");
        setDefault("Master.reward.command", "-");
        setDefault("Master.reward.cooldown", "month");

        setDefault("Legend.min.kills", 150);
        setDefault("Legend.min.wins", 50);
        setDefault("Legend.min.points", 1500);
        setDefault("Legend.prefix", "&7{&cLegend&7} ");
        setDefault("Legend.reward.command", "-");
        setDefault("Legend.reward.cooldown", "month");
    }

    public void initRanks() {
        for (String ranks : getKeys(false)) {
            UHCRank.getRanks().add(
                    new UHCRank(
                            getColorString(ranks + ".prefix"),
                            ranks,
                            getLong(ranks + ".min.wins"),
                            getLong(ranks + ".min.kills"),
                            getLong(ranks + ".min.points"),
                            getString(ranks + ".reward.command"),
                            getString(ranks + ".reward.cooldown")));
        }
    }

    public void initRank(Player p) {
        UUID id = UUIDFetcher.getUUID(p);

        long kills = StatsUtil.getKills(id);
        long wins = StatsUtil.getWins(id);
        long points = StatsUtil.getPoints(id);

        UHCRank toPut = null;

        for (UHCRank rank : UHCRank.getRanks()) {
            if (kills >= rank.getMinKills() && points >= rank.getMinPoints() && wins >= rank.getMinWins())
                toPut = rank;
        }

        StatsUtil.setUhcRank(id, toPut);
    }
}
