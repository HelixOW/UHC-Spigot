package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class VeinMinerListener extends SimpleListener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.VEIN_MINER))
            return;
        if (!e.getBlock().getType().name().contains("ORE"))
            return;

        for (Block ores : getRelatives(e.getBlock())) {
            for (Block oores : getRelatives(ores)) {
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
