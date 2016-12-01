package de.alphahelix.uhc.inventories;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.instances.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeamInventory extends Util {

    private Inventory i;

    public TeamInventory(UHC uhc) {
        super(uhc);
        setInventory(Bukkit.createInventory(null, ((getRegister().getTeamManagerUtil().getTeamAmount() / 9) + 1) * 9,
                getRegister().getTeamFile().getColorString("GUI.Name")));
    }

    public void fillInventory() {
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            getInventory().setItem(slot,
                    new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7).build());
        }

        for (UHCTeam t : getRegister().getTeamManagerUtil().getTeams()) {
            getInventory().setItem(t.getInvSlot(),
                    t.getIcon(Material.getMaterial(getRegister().getTeamFile().getString("GUI.Content Material"))));
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
