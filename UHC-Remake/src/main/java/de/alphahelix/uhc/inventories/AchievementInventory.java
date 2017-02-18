package de.alphahelix.uhc.inventories;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by AlphaHelixDev.
 */
public class AchievementInventory extends Util {

    private static Inventory inventory;

    public AchievementInventory(UHC uhc) {
        super(uhc);
        inventory = Bukkit.createInventory(null, ((UHCAchievements.values().length / 9) + 1) * 9, UHCFileRegister.getAchievementFile().getInventoryName());
    }

    public void openInventory(Player p) {
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            try {
                getInventory().setItem(slot, UHCAchievements.values()[slot].getIcon(UHCRegister.getStatsUtil().hasAchievement(UHCAchievements.values()[slot], p)));
            } catch (Exception ignore) {
            }
        }
        p.openInventory(getInventory());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        AchievementInventory.inventory = inventory;
    }
}
