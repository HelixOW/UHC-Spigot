package de.alphahelix.uhc.util;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Location;

public class RankingUtil extends Util {

    // private HashMap<Integer, String> rank = new HashMap<>();

    public RankingUtil(UHC uhc) {
        super(uhc);
    }

    public void updateArmorStands() {
        if (!UHCFileRegister.getLocationsFile().isConfigurationSection("Rankings.Armorstands")) return;
        for (String ranks : UHCFileRegister.getLocationsFile().getConfigurationSection("Rankings.Armorstands").getKeys(false)) {
            Location l = UHCFileRegister.getLocationsFile().getRankingArmorstandLocation(Integer.parseInt(ranks));

            UHCRegister.getNpcUtil().removeRankingArmorStand(l, Integer.parseInt(ranks));

            UHCRegister.getNpcUtil().spawnRankingArmorStand(l, Integer.parseInt(ranks));
        }
    }
}
