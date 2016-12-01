package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;
import de.alphahelix.uhc.instances.UHCRank;
import org.bukkit.entity.Player;

/**
 * Created by AlphaHelixDev.
 */
public class RanksFile extends EasyFile {

    public RanksFile(UHC uhc) {
        super("ranks.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Rookie.min.kills", 0);
        setDefault("Rookie.min.wins", 0);
        setDefault("Rookie.min.points", 0);
        setDefault("Rookie.prefix", "&7{&8Rookie&7} ");

        setDefault("Semi-Pro.min.kills", 5);
        setDefault("Semi-Pro.min.wins", 0);
        setDefault("Semi-Pro.min.points", 0);
        setDefault("Semi-Pro.prefix", "&7{Semi-Pro} ");

        setDefault("Pro.min.kills", 25);
        setDefault("Pro.min.wins", 1);
        setDefault("Pro.min.points", 0);
        setDefault("Pro.prefix", "&7{&0Pro} ");

        setDefault("Veteran.min.kills", 50);
        setDefault("Veteran.min.wins", 5);
        setDefault("Veteran.min.points", 0);
        setDefault("Veteran.prefix", "&7{&6Veteran} ");

        setDefault("Expert.min.kills", 75);
        setDefault("Expert.min.wins", 10);
        setDefault("Expert.min.points", 0);
        setDefault("Expert.prefix", "&7{&5Expert} ");

        setDefault("Master.min.kills", 100);
        setDefault("Master.min.wins", 25);
        setDefault("Master.min.points", 500);
        setDefault("Master.prefix", "&7{&eMaster} ");

        setDefault("Legend.min.kills", 150);
        setDefault("Legend.min.wins", 50);
        setDefault("Legend.min.points", 1500);
        setDefault("Legend.prefix", "&7{&cLegend} ");
    }

    public void initRanks() {
        for (String ranks : getKeys(false)) {
            UHCRank.getRanks().add(new UHCRank(getColorString(ranks + ".prefix"),
                    ranks,
                    getLong(ranks + ".min.wins"),
                    getLong(ranks + ".min.kills"),
                    getLong(ranks + ".min.points")));
        }
    }

    public void initRank(Player p) {
        long kills = getRegister().getStatsUtil().getKills(p);
        long wins = getRegister().getStatsUtil().getWins(p);
        long points = getRegister().getStatsUtil().getPoints(p);

        UHCRank toPut = null;

        for (UHCRank rank : UHCRank.getRanks()) {
            if (kills >= rank.getMinKills() && points >= rank.getMinPoints() && wins >= rank.getMinWins())
                toPut = rank;
        }


        getRegister().getStatsUtil().setUhcRank(toPut, p);
    }
}
