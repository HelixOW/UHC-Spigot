package de.alphahelix.uhc.inventories;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class KitInventory extends Util {

    private Inventory inv;

    public KitInventory(UHC uhc) {
        super(uhc);
        setInv(Bukkit.createInventory(null, ((getRegister().getKitsFile().getKits().size() / 9) + 1) * 9, getRegister().getKitsFile().getColorString("GUI.Name")));
    }

    public void fillInventory() {
        for (int slot = 0; slot < getInv().getSize(); slot++) {
            getInv().setItem(slot,
                    new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7).build());
        }
        for (Kit kit : getRegister().getKitsFile().getKits()) {
            getInv().setItem(kit.getGuiSlot(),
                    new ItemBuilder(kit.getGuiBlock().getType()).setName(kit.getName().replace("_", " "))
                            .setLore("§7" + Integer.toString(kit.getPrice()) + " §eCoins").build());
        }
    }

    public void openInventory(Player p) {
        p.openInventory(getInv());
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }
}
