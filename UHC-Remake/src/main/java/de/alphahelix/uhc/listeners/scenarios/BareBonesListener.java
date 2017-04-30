package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BareBonesListener extends SimpleListener {

    @EventHandler
    public void onPortalEnter(PlayerPortalEvent e) {
        if (e.isCancelled())
            return;
        if (!e.getCause().equals(TeleportCause.NETHER_PORTAL))
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BAREBONES))
            return;

        e.setCancelled(true);
        e.getPlayer().setVelocity(new Vector(1, 0.3, 1));
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BAREBONES))
            return;
        Material res = e.getRecipe().getResult().getType();

        if (res.equals(Material.ENCHANTMENT_TABLE) || res.equals(Material.ANVIL) || res.equals(Material.GOLDEN_APPLE))
            e.setCancelled(true);
    }

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BAREBONES))
            return;
        if (!e.getBlock().getType().name().contains("ORE"))
            return;

        if (!(e.getBlock().getType().equals(Material.COAL_ORE) || e.getBlock().getType().equals(Material.IRON_ORE))) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_ORE));
        }
    }


}
