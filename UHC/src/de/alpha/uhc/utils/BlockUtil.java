package de.alpha.uhc.utils;

import java.util.ArrayList;

import org.bukkit.block.BlockFace;

public class BlockUtil {
	
	public static ArrayList<BlockFace> getRelative() {
		ArrayList<BlockFace> faces = new ArrayList<>();
		
		for(BlockFace bf : BlockFace.values()) { faces.add(bf); }
		
		faces.remove(BlockFace.SELF);
		faces.remove(BlockFace.UP);
		faces.remove(BlockFace.DOWN);
		return faces;
	}

}
