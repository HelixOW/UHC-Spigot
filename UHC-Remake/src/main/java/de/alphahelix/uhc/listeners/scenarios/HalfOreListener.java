package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class HalfOreListener extends SimpleListener {

    private static ArrayList<String> miners = new ArrayList<>();

    public HalfOreListener(UHC uhc) {
        super(uhc);
    }

    private boolean hasMined(Player p) {
        return miners.contains(p.getName());
    }

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if (e.isCancelled()) return;

        if (!scenarioCheck(Scenarios.HALF_ORES)) return;

        if (!e.getBlock().getType().name().contains("ORE")) return;

        if (hasMined(p)) {
            miners.remove(p.getName());
        } else {
            miners.add(p.getName());
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
        }
    }
}
