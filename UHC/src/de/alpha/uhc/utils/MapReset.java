package de.alpha.uhc.utils;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("deprecation")
public class MapReset implements Listener {
	
	private Core pl;
	
	public MapReset(Core c) {
		this.pl = c;
	}

    private static final List<String> breaked = new LinkedList<>();
    private static final List<String> placed = new LinkedList<>();

    public static void restore() {

        for (String b : breaked) {

            String[] blockdata = b.split(":");

            int id = Integer.parseInt(blockdata[0]);
            World world = Bukkit.getWorld(blockdata[2]);
            int x = Integer.parseInt(blockdata[3]);
            int y = Integer.parseInt(blockdata[4]);
            int z = Integer.parseInt(blockdata[5]);

            world.getBlockAt(x, y, z).setTypeId(id);

        }

        for (String b : placed) {

            String[] blockdata = b.split(":");

            World world = Bukkit.getWorld(blockdata[0]);
            int x = Integer.parseInt(blockdata[1]);
            int y = Integer.parseInt(blockdata[2]);
            int z = Integer.parseInt(blockdata[3]);

            world.getBlockAt(x, y, z).setType(Material.AIR);

        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        if (!(GState.isState(GState.LOBBY))) {

            Block b = e.getBlock();

            String block = b.getTypeId() + ":" + b.getData() + ":" + b.getWorld().getName() +
                    ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();

            breaked.add(block);
        }

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        if (!(GState.isState(GState.LOBBY))) {

            BlockState b = e.getBlockReplacedState();

            String block = b.getWorld().getName() +
                    ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();

            placed.add(block);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {

        if (!(GState.isState(GState.LOBBY))) {

            for (int i = 0; i < e.blockList().size(); i++) {

                Block b = e.blockList().get(i);

                String block = b.getTypeId() + ":" + b.getData() + ":" + b.getWorld().getName() +
                        ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();

                breaked.add(block);

            }
        }
    }

    @EventHandler
    public void onInterAct(PlayerInteractEvent e) {

        if (e.getItem() == null) return;

        if (e.getItem().getType().equals(Material.INK_SACK)) {
            if (e.getItem().getDurability() == 15) {
                e.setCancelled(true);
            }
        }

    }
}
