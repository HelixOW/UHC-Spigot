package de.alphahelix.uhc.listeners;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SchematicListener extends SimpleListener {

    public SchematicListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onDefine(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (p.getInventory().getItemInHand().getType().equals(Material.GOLD_AXE) && (p.hasPermission("uhc.admin") || p.hasPermission("uhc.region"))) {
            if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                e.setCancelled(true);
                UHCRegister.getSchematicManagerUtil().putFirstLocation(p, e.getClickedBlock().getLocation());
                p.sendMessage(getUhc().getPrefix() + "You've set the first Location to your location!");
            } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                e.setCancelled(true);
                UHCRegister.getSchematicManagerUtil().putSecondLocation(p, e.getClickedBlock().getLocation());
                p.sendMessage(getUhc().getPrefix() + "You've set the second Location to your location!");
            }
        }
    }
}
