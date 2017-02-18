package de.alphahelix.uhc.inventories;

import de.alphahelix.alphalibary.inventorys.SimpleMovingInventory;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CrateInventory extends Util {

    public CrateInventory(UHC uhc) {
        super(uhc);
    }

    public void fillInventory(Player p, String... c) {
        ArrayList<ItemStack> stacks = new ArrayList<>();

        for (String crateName : c) {
            if(Crate.getCrateByRawName(crateName) == null) continue;
            stacks.add(Crate.getCrateByRawName(crateName).getIcon());
        }

        new SimpleMovingInventory(
                getUhc(),
                p,
                ((c.length / 9) + 1) * 18,
                stacks,
                UHCFileRegister.getCrateFile().getInventoryName(),
                UHCFileRegister.getKitsFile().getNextItem().getItemStack(),
                UHCFileRegister.getKitsFile().getPreviousItem().getItemStack());
    }
}
