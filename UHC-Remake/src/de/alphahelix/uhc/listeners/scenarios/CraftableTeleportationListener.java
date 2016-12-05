package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by AlphaHelixDev.
 */
public class CraftableTeleportationListener extends SimpleListener {

    public CraftableTeleportationListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.isCancelled()) return;
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (!scenarioCheck(Scenarios.CRAFTABLE_TELEPORTATION)) return;

        Player p = e.getPlayer();

        if (p.getInventory().getItemInHand().getType() != Material.ENDER_PEARL) return;
        if (!p.getInventory().getItemInHand().hasItemMeta()) return;
        if (!p.getInventory().getItemInHand().getItemMeta().hasDisplayName()) return;
        if (Bukkit.getPlayer(p.getInventory().getItemInHand().getItemMeta().getDisplayName()) == null) return;

        Player t = Bukkit.getPlayer(p.getInventory().getItemInHand().getItemMeta().getDisplayName());

        if (!getRegister().getPlayerUtil().isSurivor(t)) return;

        p.teleport(getRandomLocation(t.getLocation(), 25, 25, 25, 25));
    }

    private Location getRandomLocation(Location player, int Xminimum, int Xmaximum, int Zminimum, int Zmaximum) {
        try {
            World world = player.getWorld();
            int randomZ = Zminimum + ((int) (Math.random() * ((Zmaximum - Zminimum) + 1)));
            double x = Double.parseDouble(
                    Integer.toString(Xminimum + ((int) (Math.random() * ((Xmaximum - Xminimum) + 1))))) + 0.5d;
            double z = Double.parseDouble(Integer.toString(randomZ)) + 0.5d;
            player.setY(200);
            return new Location(world, x, player.getY(), z);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
