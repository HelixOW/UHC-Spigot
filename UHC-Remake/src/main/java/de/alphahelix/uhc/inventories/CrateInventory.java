package de.alphahelix.uhc.inventories;

import de.alphahelix.alphalibary.inventorys.SimpleMovingInventory;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CrateInventory {

    public void fillInventory(Player p, List<String> crates) {
        ArrayList<ItemStack> stacks = new ArrayList<>();

        for (String crateName : crates) {
            if (Crate.getCrateByRawName(crateName) == null) continue;
            stacks.add(Crate.getCrateByRawName(crateName).getIcon());
        }

        new SimpleMovingInventory(
                p,
                ((crates.size() / 9) + 1) * 18,
                stacks,
                UHCFileRegister.getCrateFile().getInventoryName(),
                UHCFileRegister.getKitsFile().getNextItem().getItemStack(),
                UHCFileRegister.getKitsFile().getPreviousItem().getItemStack());
    }
}
