package de.alphahelix.uhc.inventories;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.UHCAchievements;
import de.alphahelix.uhc.instances.Util;
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
        inventory = Bukkit.createInventory(null, ((UHCAchievements.values().length / 9) + 1) * 9, getRegister().getAchievementFile().getColorString("GUI.Name"));
    }

    public void openInventory(Player p) {
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            try {
                getInventory().setItem(slot, UHCAchievements.values()[slot].getIcon(getRegister().getStatsUtil().hasAchievement(UHCAchievements.values()[slot], p)));
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
