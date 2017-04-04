package de.alphahelix.uhc.inventories;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.alphaapi.item.ItemBuilder;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ConfirmInventory {

    private Inventory i;

    public ConfirmInventory() {
        setInventory(Bukkit.createInventory(null, 27, UHCFileRegister.getConfirmFile().getInventoryName()));
    }

    public void fillInventory() {
        for (int slot = 0; slot < getInv().getSize(); slot++) {
            getInv().setItem(slot,
                    new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7).build());
        }

        SimpleFile.InventoryItem a = UHCFileRegister.getConfirmFile().getItem(true);
        SimpleFile.InventoryItem d = UHCFileRegister.getConfirmFile().getItem(false);

        getInv().setItem(11, a.getItemStack());

        getInv().setItem(15, d.getItemStack());
    }

    public void openInventory(Player p) {
        p.closeInventory();
        p.openInventory(getInv());
    }

    private Inventory getInv() {
        return i;
    }

    private void setInventory(Inventory i) {
        this.i = i;
    }

}
