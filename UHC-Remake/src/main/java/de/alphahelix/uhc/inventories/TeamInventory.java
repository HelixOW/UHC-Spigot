package de.alphahelix.uhc.inventories;

import de.alphahelix.alphaapi.item.ItemBuilder;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.TeamManagerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeamInventory {

    private Inventory i;

    public TeamInventory() {
        setInventory(Bukkit.createInventory(null, ((TeamManagerUtil.getTeamAmount() / 9) + 1) * 9,
                UHCFileRegister.getTeamFile().getInventoryName()));
    }

    public void fillInventory() {
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            getInventory().setItem(slot,
                    new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7).build());
        }

        for (UHCTeam t : TeamManagerUtil.getTeams()) {
            getInventory().setItem(t.getInvSlot(),
                    t.getIcon(UHCFileRegister.getTeamFile().getContentMaterial()));
        }
    }

    public void openInventory(Player p) {
        p.openInventory(getInventory());
    }

    public Inventory getInventory() {
        return i;
    }

    public void setInventory(Inventory i) {
        this.i = i;
    }

}
