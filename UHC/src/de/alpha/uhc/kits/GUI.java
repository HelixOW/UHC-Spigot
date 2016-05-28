package de.alpha.uhc.kits;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUI {
	
	private Core pl;
	private Registery r;
	
	public GUI(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  Inventory kits;
    private  String title;


    public  Inventory getKits() {
        return kits;
    }

    public  void setKits(Inventory kits) {
        this.kits = kits;
    }

    public  String getTitle() {
        return title;
    }

    public  void setTitle(String title) {
        this.title = title;
    }

    public  void fill() {
        kits = Bukkit.createInventory(null, 54, title);

        for (String kitName : r.getKitFile().getAllKits()) {

            try {
                int slot = r.getKitFile().getSlot(kitName);
                Material m = Material.getMaterial(r.getKitFile().getMaterial(kitName).toUpperCase());
                String lore = r.getKitFile().getLore(kitName);

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
