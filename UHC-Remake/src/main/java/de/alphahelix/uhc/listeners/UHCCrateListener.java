package de.alphahelix.uhc.listeners;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class UHCCrateListener extends SimpleListener {

    public UHCCrateListener(UHC uhc) {
        super(uhc);
    }

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

        new BukkitRunnable() {
            public void run() {
                UHCRegister.getCrateInventory().fillInventory(p, UHCRegister.getStatsUtil().getCratesAsArray(p));
            }
        }.runTaskLaterAsynchronously(getUhc(), 2);
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

        if(Crate.getCrateByName(name) != null) {
            Crate c = Crate.getCrateByName(name);
            if(!UHCRegister.getStatsUtil().hasCrate(c, p)) return;
            p.closeInventory();
            p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 10, 1);

            UHCRegister.getCrateOpenInventory().openInventory(c, p);
            UHCRegister.getStatsUtil().removeCrate(c, p);
        }
    }
}
