package de.alphahelix.uhc.util;

import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RankingUtil {

    public static void updateArmorStands(Player p) {
        if (!UHCFileRegister.getLocationsFile().isConfigurationSection("Rankings")) return;
        for (String ranks : UHCFileRegister.getLocationsFile().getConfigurationSection("Rankings").getKeys(false)) {
            Location l = UHCFileRegister.getLocationsFile().getRankingArmorstandLocation(Integer.parseInt(ranks));

            NPCUtil.spawnRanking(l, Integer.parseInt(ranks), p);
        }
    }
}
