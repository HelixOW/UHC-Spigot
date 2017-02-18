/*
 * Copyright (C) <2017>  <AlphaHelixDev>
 *
 *       This program is free software: you can redistribute it under the
 *       terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.alphahelix.alphalibary.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class SimpleMovingInventory implements Listener, Serializable {

    private static HashMap<UUID, SimpleMovingInventory> users = new HashMap<>();
    private String title;
    private ItemStack nextPage = null;
    private ItemStack previousPage = null;
    private ArrayList<Inventory> pages = new ArrayList<>();
    private int currpage = 0;

    /**
     * Creates a new {@link Inventory} with multiple Sites
     *
     * @param plugin   the {@link Plugin} to register the {@link Inventory} on
     * @param p        the {@link Player} to open the {@link Inventory} for
     * @param size     the site of the {@link Inventory}
     * @param items    a {@link ArrayList} of {@link ItemStack}s which should be inside the {@link Inventory}
     * @param name     the name of the {@link Inventory}
     * @param nextPage the name for the {@link ItemStack} for the next page
     * @param prevPage the name for the {@link ItemStack} for the previous page
     */
    public SimpleMovingInventory(Plugin plugin, Player p, int size, ArrayList<ItemStack> items, String name, ItemStack nextPage, ItemStack prevPage) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        title = name;

        this.nextPage = nextPage;
        this.previousPage = prevPage;

        Inventory page = getBlankPage(name, size);

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

        for (int i = size - 8; i < size - 1; i++) {
            inv.setItem(i,
                    new de.alphahelix.alphalibary.item.ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7).build());
        }

        inv.setItem(size - 1, nextPage);
        inv.setItem(size - 9, previousPage);
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
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(this.nextPage.getItemMeta().getDisplayName())) {
            event.setCancelled(true);
            if (inv.currpage >= inv.pages.size() - 1) {
            } else {
                inv.currpage += 1;
                p.openInventory(inv.pages.get(inv.currpage));
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName()
                .equals(this.previousPage.getItemMeta().getDisplayName())) {
            event.setCancelled(true);
            if (inv.currpage > 0) {
                inv.currpage -= 1;
                p.openInventory(inv.pages.get(inv.currpage));
            }
        }
    }
}
