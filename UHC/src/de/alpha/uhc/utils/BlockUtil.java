package de.alpha.uhc.utils;

import org.bukkit.block.BlockFace;

import de.alpha.uhc.Core;

import java.util.ArrayList;
import java.util.Collections;

public class BlockUtil {
	
	
	public BlockUtil(Core c) {
	}

    public static ArrayList<BlockFace> getRelative() {
        ArrayList<BlockFace> faces = new ArrayList<>();

        Collections.addAll(faces, BlockFace.values());

        faces.remove(BlockFace.SELF);
        faces.remove(BlockFace.UP);
        faces.remove(BlockFace.DOWN);
        return faces;
    }

}
