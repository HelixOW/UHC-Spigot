package de.alphahelix.uhc.listeners.scenarios;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class VeinMinerListener extends SimpleListener {

	public VeinMinerListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.VEIN_MINER))
			return;
		if (!e.getBlock().getType().name().contains("ORE"))
			return;

		for (Block ores : getRelatives(e.getBlock())) {
			for(Block oores : getRelatives(ores)) {
				ores.breakNaturally();
				oores.breakNaturally();
			}
		}
	}

	private ArrayList<Block> getRelatives(Block b) {
		ArrayList<Block> tr = new ArrayList<>();

		for (int rad = 1; rad < 5; rad++) {
			for (BlockFace bf : BlockFace.values()) {
				if (b.getRelative(bf, rad).getType().equals(b.getType()))
					tr.add(b.getRelative(bf, rad));
			}
		}
		return tr;
	}
}
