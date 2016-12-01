package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.Door;

import java.util.ArrayList;
import java.util.Random;

public class MonsterIncListener extends SimpleListener {

    private static Random r = new Random();
    private ArrayList<Location> doors = new ArrayList<>();

    public MonsterIncListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlockPlaced() == null)
            return;
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.MONSTER_INC))
            return;

        if (!(e.getBlockPlaced().getType().name().contains("DOOR")))
            return;

        if (!doors.contains(e.getBlockPlaced().getLocation()))
            doors.add(e.getBlockPlaced().getLocation());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!(e.getBlock().getState() instanceof Door))
            return;

        if (doors.contains(e.getBlock().getLocation()))
            doors.remove(e.getBlock().getLocation());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.MONSTER_INC))
            return;
        if (doors.size() < 3) return;

        Player p = e.getPlayer();
        Location door = doors.get(r.nextInt(doors.size()));

        if (!(e.getTo().getBlockX() == door.getBlockX() && e.getTo().getBlockZ() == door.getBlockZ()
                && e.getTo().getBlockY() == door.getBlockY()))
            return;

        p.teleport(door);
    }
}
