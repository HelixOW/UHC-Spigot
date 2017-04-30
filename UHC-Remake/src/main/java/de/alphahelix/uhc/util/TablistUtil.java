package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.nms.SimpleTablist;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TablistUtil {

    public static void sendTablist() {
        for (String pNames : PlayerUtil.getAll()) {
            if (Bukkit.getPlayer(pNames) == null) continue;
            Player p = Bukkit.getPlayer(pNames);

            SimpleTablist.setTablistHeaderFooter(p, null, null);

            SimpleTablist.setTablistHeaderFooter(p,
                    UHCFileRegister.getTablistFile().getHeader()
                            .replace("[player]", p.getDisplayName())
                            .replace("[playercount]", Integer.toString(PlayerUtil.getAll().size()))
                            .replace("[gamestatus]", GState.getGameStateName()),
                    UHCFileRegister.getTablistFile().getFooter()
                            .replace("[player]", p.getDisplayName())
                            .replace("[playercount]", Integer.toString(PlayerUtil.getAll().size()))
                            .replace("[gamestatus]", GState.getGameStateName()));
        }
    }

    public static void sortTablist(Player p) {
        p.setPlayerListName(StatsUtil.getUHCRank(UUIDFetcher.getUUID(p)).getPrefix() + p.getDisplayName());
    }
}
