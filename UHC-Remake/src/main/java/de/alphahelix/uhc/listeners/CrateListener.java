package de.alphahelix.uhc.listeners;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CrateListener extends SimpleListener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (!GState.isState(GState.LOBBY))
            return;
        if (e.getPlayer().getInventory().getItemInHand() == null)
            return;
        if (e.getPlayer().getInventory().getItemInHand().getType()
                != UHCFileRegister.getCrateFile().getItem().getItemStack().getType())
            return;

        final Player p = e.getPlayer();

        UHCRegister.getCrateInventory().fillInventory(
                p,
                StatsUtil.getCratesList(UUIDFetcher.getUUID(p)));
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        if (!e.getInventory().getName().contains(UHCFileRegister.getCrateFile().getInventoryName()))
            return;

        Player p = (Player) e.getWhoClicked();

        String name = e.getCurrentItem().getItemMeta().getDisplayName();
        e.setCancelled(true);

        if (Crate.getCrateByName(name) != null) {
            Crate c = Crate.getCrateByName(name);
            if (!StatsUtil.hasCrate(UUIDFetcher.getUUID(p), c)) return;
            p.closeInventory();
            p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 10, 1);

            UHCRegister.getCrateOpenInventory().openInventory(c, p);
            StatsUtil.removeCrate(UUIDFetcher.getUUID(p), c);
        }
    }
}
