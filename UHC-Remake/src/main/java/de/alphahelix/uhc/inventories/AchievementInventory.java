package de.alphahelix.uhc.inventories;

import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by AlphaHelixDev.
 */
public class AchievementInventory {

    private static Inventory inventory;

    public AchievementInventory() {
        inventory = Bukkit.createInventory(null, ((UHCAchievements.values().length / 9) + 1) * 9, UHCFileRegister.getAchievementFile().getInventoryName());
    }

    public void openInventory(Player p) {
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            try {
                getInventory().setItem(slot, UHCAchievements.values()[slot].getIcon(StatsUtil.hasAchievement(UUIDFetcher.getUUID(p), UHCAchievements.values()[slot])));
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
