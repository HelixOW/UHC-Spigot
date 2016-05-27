package de.alpha.uhc.kits;

import de.alpha.uhc.Core;
import de.popokaka.alphalibary.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUI {

    private static Inventory kits;
    private static String title;


    public static Inventory getKits() {
        return kits;
    }

    public static void setKits(Inventory kits) {
        GUI.kits = kits;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        GUI.title = title;
    }

    public static void fill() {
        kits = Bukkit.createInventory(null, 54, title);

        for (String kitName : new KitFileManager().getAllKits()) {

            try {
                int slot = new KitFileManager().getSlot(kitName);
                Material m = Material.getMaterial(new KitFileManager().getMaterial(kitName).toUpperCase());
                String lore = new KitFileManager().getLore(kitName);

                kits.setItem(slot,
                        new ItemBuilder(m)
                                .setName(kitName)
                                .setLore(new String[]{lore})
                                .build());
            } catch (NullPointerException e) {
                Bukkit.getConsoleSender().sendMessage(Core.getInstance().getPrefix() + "§cYour Kits.yml is invalid. Block has to be a valid Material [google Bukkit materials]");
            }
        }
    }

    public static void open(Player p) {
        p.openInventory(kits);
    }

}
