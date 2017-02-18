package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.nms.SimpleTablist;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TablistUtil extends Util {

    public TablistUtil(UHC uhc) {
        super(uhc);
    }

    public void sendTablist() {
        for (String pNames : UHCRegister.getPlayerUtil().getAll()) {
            if (Bukkit.getPlayer(pNames) == null) continue;
            Player p = Bukkit.getPlayer(pNames);

            SimpleTablist.setTablistHeaderFooter(p, null, null);

            SimpleTablist.setTablistHeaderFooter(p,
                    UHCFileRegister.getTablistFile().getHeader()
                            .replace("[player]", p.getDisplayName())
                            .replace("[playercount]", Integer.toString(UHCRegister.getPlayerUtil().getAll().size()))
                            .replace("[gamestatus]", GState.getGameStateName()),
                    UHCFileRegister.getTablistFile().getFooter()
                            .replace("[player]", p.getDisplayName())
                            .replace("[playercount]", Integer.toString(UHCRegister.getPlayerUtil().getAll().size()))
                            .replace("[gamestatus]", GState.getGameStateName()));
        }
    }

    public void sortTablist(Player p) {
        p.setPlayerListName(UHCRegister.getStatsUtil().getUHCRank(p).getPrefix() + p.getDisplayName());
    }
}
