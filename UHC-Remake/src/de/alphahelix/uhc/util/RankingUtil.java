package de.alphahelix.uhc.util;

import org.bukkit.Location;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;

public class RankingUtil extends Util {

	// private HashMap<Integer, String> rank = new HashMap<>();

	public RankingUtil(UHC uhc) {
		super(uhc);
	}

	public void updateArmorStands() {
		for(String ranks : getRegister().getLocationsFile().getConfigurationSection("Rankings.Armorstands").getKeys(false)) {
			Location l = getRegister().getLocationsFile().getRankingNPCLocation(Integer.parseInt(ranks));
			
			getRegister().getNpcUtil().removeRankingArmorStand(l, Integer.parseInt(ranks));
			
			getRegister().getNpcUtil().spawnRankingArmorStand(l, Integer.parseInt(ranks));
		}
	}
}
