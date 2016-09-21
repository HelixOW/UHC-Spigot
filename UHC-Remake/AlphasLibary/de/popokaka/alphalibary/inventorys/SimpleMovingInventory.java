package de.popokaka.alphalibary.inventorys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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

import de.alphahelix.uhc.UHC;
import de.popokaka.alphalibary.item.ItemBuilder;

public class SimpleMovingInventory implements Listener {

	private String title;
	private Inventory inv;
	private ArrayList<Inventory> pages = new ArrayList<Inventory>();
	private int currpage = 0;
	private static HashMap<UUID, SimpleMovingInventory> users = new HashMap<UUID, SimpleMovingInventory>();
	private static final String nextPageName = UHC.getInstance().getRegister().getKitsFile()
			.getColorString("Preview GUI.Next page");
	private static final String previousPageName = UHC.getInstance().getRegister().getKitsFile()
			.getColorString("Preview GUI.Previous page");

	// Running this will open a paged inventory for the specified player, with
	// the items in the arraylist specified.
	public SimpleMovingInventory(Plugin pl, ArrayList<ItemStack> items, String name, Player p, int size) {
		Bukkit.getPluginManager().registerEvents(this, pl);
		// create new blank page
		title = name;
		Inventory page = getBlankPage(name, size);
		// According to the items in the arraylist, add items to the
		// SimpleMovingInventory
		for (int i = 0; i < items.size(); i++) {
			// If the current page is full, add the page to the inventory's
			// pages arraylist, and create a new page to add the items.
			if (page.firstEmpty() == -1) {
				pages.add(page);
				page = getBlankPage(name, size);
				page.addItem(items.get(i));
			} else {
				// Add the item to the current page as per normal
				page.addItem(items.get(i));
			}
		}
		pages.add(page);
		// open page 0 for the specified player
		p.openInventory(pages.get(currpage));
		users.put(p.getUniqueId(), this);
	}

	private Inventory getBlankPage(String name, int size) {
		inv = Bukkit.createInventory(null, size, name);

		ItemStack nextpage = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta meta = nextpage.getItemMeta();
		meta.setDisplayName(nextPageName);
		nextpage.setItemMeta(meta);

		ItemStack prevpage = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
		meta = prevpage.getItemMeta();
		meta.setDisplayName(previousPageName);
		prevpage.setItemMeta(meta);
		
		for(int i = size-8; i < size-1; i++) {
			inv.setItem(i,
					new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7).build());
		}

		inv.setItem(size-1, nextpage);
		inv.setItem(size-9, prevpage);
		return inv;
	}

	@EventHandler(ignoreCancelled = true)
	public void onClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player))
			return;
		Player p = (Player) event.getWhoClicked();
		
		if(event.getClickedInventory() == null) return;
		if(event.getClickedInventory().getTitle() == title) event.setCancelled(true);
		
		if (!SimpleMovingInventory.users.containsKey(p.getUniqueId()))
			return;
		SimpleMovingInventory inv = SimpleMovingInventory.users.get(p.getUniqueId());
		if (event.getCurrentItem() == null)
			return;
		if (event.getCurrentItem().getItemMeta() == null)
			return;
		if (event.getCurrentItem().getItemMeta().getDisplayName() == null)
			return;
		// If the pressed item was a nextpage button
		if (event.getCurrentItem().getItemMeta().getDisplayName().equals(SimpleMovingInventory.nextPageName)) {
			event.setCancelled(true);
			// If there is no next page, don't do anything
			if (inv.currpage >= inv.pages.size() - 1) {
				return;
			} else {
				// Next page exists, flip the page
				inv.currpage += 1;
				p.openInventory(inv.pages.get(inv.currpage));
			}
			// if the pressed item was a previous page button
		} else if (event.getCurrentItem().getItemMeta().getDisplayName()
				.equals(SimpleMovingInventory.previousPageName)) {
			event.setCancelled(true);
			// If the page number is more than 0 (So a previous page exists)
			if (inv.currpage > 0) {
				// Flip to previous page
				inv.currpage -= 1;
				p.openInventory(inv.pages.get(inv.currpage));
			}
		}
	}
}
