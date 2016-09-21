package de.alphahelix.uhc.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.nms.SimpleTablist;

public class TablistUtil extends Util {

	public TablistUtil(UHC uhc) {
		super(uhc);
	}

	public void sendTablist() {
		for (String pNames : getRegister().getPlayerUtil().getAll()) {
			if(Bukkit.getPlayer(pNames) == null) continue;
			Player p = Bukkit.getPlayer(pNames);

			SimpleTablist.setTablistHeaderFooter(p, null, null);

			SimpleTablist.setTablistHeaderFooter(p,
					getRegister().getTablistFile().getColorString("Header")
						.replace("[player]", p.getDisplayName())
						.replace("[playercount]", Integer.toString(getRegister().getPlayerUtil().getAll().size()))
						.replace("[gamestatus]", GState.getGameStateName()),
					getRegister().getTablistFile().getColorString("Footer")
						.replace("[player]", p.getDisplayName())
						.replace("[playercount]", Integer.toString(getRegister().getPlayerUtil().getAll().size()))
						.replace("[gamestatus]", GState.getGameStateName()));
		}
	}
}
