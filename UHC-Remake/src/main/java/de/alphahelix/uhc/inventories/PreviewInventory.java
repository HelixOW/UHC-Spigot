package de.alphahelix.uhc.inventories;

import de.alphahelix.alphalibary.inventorys.SimpleMovingInventory;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PreviewInventory extends Util {

    public PreviewInventory(UHC uhc) {
        super(uhc);
    }

    public void fillInventory(Player p, Kit k) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (ItemStack is : k.getItems()) {
            if (is != null)
                stacks.add(is);
        }

        new SimpleMovingInventory(
                getUhc(),
                p,
                9 * 5,
                stacks,
                UHCFileRegister.getKitsFile().getPreviewInventoryName(k),
                UHCFileRegister.getKitsFile().getNextItem().getItemStack(),
                UHCFileRegister.getKitsFile().getPreviousItem().getItemStack());
    }
}
