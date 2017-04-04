package de.alphahelix.uhc.inventories;

import de.alphahelix.alphaapi.item.ItemBuilder;
import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;

public class CrateOpenInventory extends SimpleListener {

    private static final HashMap<String, Thread> THREADS = new HashMap<>();

    public CrateOpenInventory() {
        super();
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (THREADS.containsKey(e.getPlayer().getName()))
            THREADS.get(e.getPlayer().getName()).stop();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (THREADS.containsKey(e.getWhoClicked().getName()))
            e.setCancelled(true);
    }

    @SuppressWarnings("deprecation")
    public void openInventory(final Crate c, final Player p) {
        if (THREADS.containsKey(p.getName())) {
            THREADS.get(p.getName()).stop();
        }

        final Inventory inv = Bukkit.createInventory(null, 9 * 3, UHCFileRegister.getCrateFile().getOpenInventoryName(c.getRawName()));

        final Thread thread = new Thread(
                new Runnable() {
                    public void run() {
                        ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7)
                                .build();
                        ItemStack winGlass = new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 5)
                                .build();

                        for (int slot = 0; slot < 9; slot++) {
                            inv.setItem(slot, glass);
                        }

                        for (int slot = 18; slot < inv.getSize(); slot++) {
                            inv.setItem(slot, glass);
                        }

                        inv.setItem(4, winGlass);
                        inv.setItem(22, winGlass);

                        p.openInventory(inv);

                        int showns = new Random().nextInt(20) + 50;
                        int maximalSleep = 3 * 100;
                        int toSleep = 0;
                        double differenceBetweenRuns = maximalSleep / showns;

                        for (int index = 0; index < showns; index++) {
                            for (int slot = 9; slot <= 17; slot++) {
                                if (slot > 9) {
                                    inv.setItem(slot - 1, inv.getItem(slot));
                                }
                            }

                            if (c.getKitIcons().size() <= 0)
                                continue;

                            int r = new Random().nextInt(
                                    c.getKitIcons().size());

                            ItemStack obtain = new ItemStack(c.getKitIcons().get(r));

                            inv.setItem(17, obtain);

                            toSleep += differenceBetweenRuns;

                            try {
                                Thread.sleep(toSleep);
                            } catch (InterruptedException ignored) {
                            }

                            p.playSound(p.getLocation(), Sounds.CLICK.bukkitSound(), 1F, 1F);
                            p.updateInventory();
                        }

                        if (inv.getItem(13) == null)
                            return;

                        ItemStack won = inv.getItem(13).clone();

                        if (!hasDisplayName(won))
                            return;

                        inv.clear();
                        inv.setItem(13, won);

                        for (int slot = 0; slot < 9; slot++) {
                            inv.setItem(slot, winGlass);
                        }

                        for (int slot = 18; slot < inv.getSize(); slot++) {
                            inv.setItem(slot, winGlass);
                        }

                        p.playSound(p.getLocation(), Sounds.LEVEL_UP.bukkitSound(), 1F, 0F);
                        p.updateInventory();

                        if (!StatsUtil.hasKit(
                                UUIDFetcher.getUUID(p),
                                UHCFileRegister.getKitsFile().getKit(won.getItemMeta().getDisplayName())))

                            StatsUtil.addKits(UUIDFetcher.getUUID(p),
                                    UHCFileRegister.getKitsFile().getKit(won.getItemMeta().getDisplayName()));

                        new BukkitRunnable() {
                            public void run() {
                                THREADS.remove(p.getName());
                                p.closeInventory();
                            }
                        }.runTaskLaterAsynchronously(UHC.getInstance(), 20 * 3);
                    }
                });

        THREADS.put(p.getName(), thread);
        thread.start();
    }

    private boolean hasDisplayName(ItemStack itemStack) {
        return (itemStack != null && itemStack.hasItemMeta()
                && itemStack.getItemMeta().hasDisplayName());
    }
}
