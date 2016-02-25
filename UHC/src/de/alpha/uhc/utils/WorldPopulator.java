package de.alpha.uhc.utils;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class WorldPopulator extends BlockPopulator {

	@Override
	public void populate(World w, Random r, Chunk chunk) {
	
		int x = chunk.getX()*16;
		int z = chunk.getZ()*16;
		int cx = x + r.nextInt(10); 
		int cz = z + r.nextInt(10); 
		
		if(r.nextInt(50) < 10) {
			w.generateTree(new Location(w, cx, w.getHighestBlockYAt(cx, cz), cz), TreeType.RED_MUSHROOM);
			w.generateTree(new Location(w, cx, w.getHighestBlockYAt(cx, cz), cz), TreeType.BROWN_MUSHROOM);
			w.generateTree(new Location(w, cx, w.getHighestBlockYAt(cx, cz), cz), TreeType.SWAMP);
		}
	}
	
	
	
	

}
