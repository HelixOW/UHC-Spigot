package de.alpha.uhc.kits;

import de.alpha.uhc.Core;
import de.popokaka.alphalibary.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUI {
	
	private Core pl;
	
	public GUI(Core c) {
		this.pl = c;
	}

    private  Inventory kits;
    private  String title;


    public  Inventory getKits() {
        return kits;
    }

    public  void setKits(Inventory kits) {
        GUI.kits = kits;
    }

    public  String getTitle() {
        return title;
    }

    public  void setTitle(String title) {
        GUI.title = title;
    }

    public  void fill() {
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

    public  void open(Player p) {
        p.openInventory(kits);
    }

}
