package de.alphahelix.alphalibary.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class SimpleMovingInventory implements Listener {

    private static HashMap<UUID, SimpleMovingInventory> users = new HashMap<>();
    private String title;
    private String nextPageName = "§aNext!";
    private String previousPageName = "§cPrevious!";
    private ArrayList<Inventory> pages = new ArrayList<>();
    private int currpage = 0;

    public SimpleMovingInventory(Plugin pl, ArrayList<ItemStack> items, String name, Player p, int size, String nextPage, String prevPage) {
        Bukkit.getPluginManager().registerEvents(this, pl);
        title = name;
        Inventory page = getBlankPage(name, size);
        this.nextPageName = nextPage;
        this.previousPageName = prevPage;
        for (ItemStack item : items) {
            if (page.firstEmpty() == -1) {
                pages.add(page);
                page = getBlankPage(name, size);
                page.addItem(item);
            } else {
                page.addItem(item);
            }
        }
        pages.add(page);
        p.openInventory(pages.get(currpage));
        users.put(p.getUniqueId(), this);
    }

    private Inventory getBlankPage(String name, int size) {
        Inventory inv = Bukkit.createInventory(null, size, name);

        ItemStack nextpage = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
        ItemMeta meta = nextpage.getItemMeta();
        meta.setDisplayName(nextPageName);
        nextpage.setItemMeta(meta);

        ItemStack prevpage = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        meta = prevpage.getItemMeta();
        meta.setDisplayName(previousPageName);
        prevpage.setItemMeta(meta);

        for (int i = size - 8; i < size - 1; i++) {
            inv.setItem(i,
                    new de.alphahelix.alphalibary.item.ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7).build());
        }

        inv.setItem(size - 1, nextpage);
        inv.setItem(size - 9, prevpage);
        return inv;
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player p = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null) return;
        if (Objects.equals(event.getClickedInventory().getTitle(), title)) event.setCancelled(true);

        if (!SimpleMovingInventory.users.containsKey(p.getUniqueId()))
            return;
        SimpleMovingInventory inv = SimpleMovingInventory.users.get(p.getUniqueId());
        if (event.getCurrentItem() == null)
            return;
        if (event.getCurrentItem().getItemMeta() == null)
            return;
        if (event.getCurrentItem().getItemMeta().getDisplayName() == null)
            return;
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(this.nextPageName)) {
            event.setCancelled(true);
            if (inv.currpage >= inv.pages.size() - 1) {
            } else {
                inv.currpage += 1;
                p.openInventory(inv.pages.get(inv.currpage));
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName()
                .equals(this.previousPageName)) {
            event.setCancelled(true);
            if (inv.currpage > 0) {
                inv.currpage -= 1;
                p.openInventory(inv.pages.get(inv.currpage));
            }
        }
    }
}
