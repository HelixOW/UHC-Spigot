package de.alphahelix.uhc.util;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import org.bukkit.Location;

public class RankingUtil extends Util {

    // private HashMap<Integer, String> rank = new HashMap<>();

    public RankingUtil(UHC uhc) {
        super(uhc);
    }

    public void updateArmorStands() {
        if (!getRegister().getLocationsFile().isConfigurationSection("Rankings.Armorstands")) return;
        for (String ranks : getRegister().getLocationsFile().getConfigurationSection("Rankings.Armorstands").getKeys(false)) {
            Location l = getRegister().getLocationsFile().getRankingNPCLocation(Integer.parseInt(ranks));

            getRegister().getNpcUtil().removeRankingArmorStand(l, Integer.parseInt(ranks));

            getRegister().getNpcUtil().spawnRankingArmorStand(l, Integer.parseInt(ranks));
        }
    }
}
