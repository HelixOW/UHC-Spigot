package de.alphahelix.uhc.inventories;

import de.alphahelix.alphalibary.inventorys.SimpleMovingInventory;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ScenarioAdminInventory extends Util {

    public ScenarioAdminInventory(UHC uhc) {
        super(uhc);
    }

    public void fillInventory(Player p) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (Scenarios s : Scenarios.values()) {
            if (s == null)
                continue;
            if (s.equals(Scenarios.NONE))
                continue;
            ItemStack ta = UHCFileRegister.getScenarioFile().getScenarioItem(s);
            ItemMeta meta = ta.getItemMeta();
            List<String> lore = meta.getLore();

            lore.add("§4Enabled §7: §4" + UHCFileRegister.getScenarioFile().isEnabled(Scenarios.getRawScenarioName(s)));

            meta.setLore(lore);

            ta.setItemMeta(meta);

            stacks.add(ta);
        }

        new SimpleMovingInventory(
                getUhc(),
                p,
                9 * 5,
                stacks,
                "§7Change §4Scenarios",
                UHCFileRegister.getKitsFile().getNextItem().getItemStack(),
                UHCFileRegister.getKitsFile().getPreviousItem().getItemStack()
        );
    }
}
