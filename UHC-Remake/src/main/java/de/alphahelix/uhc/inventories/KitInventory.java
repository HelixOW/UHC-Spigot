package de.alphahelix.uhc.inventories;

import de.alphahelix.alphalibary.inventorys.SimpleMovingInventory;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class KitInventory extends Util {


    public KitInventory(UHC uhc) {
        super(uhc);
    }

    public void openInventory(Player p) {

        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (Kit k : UHCFileRegister.getKitsFile().getKits()) {
            stacks.add(new ItemBuilder(k.getGuiBlock().getType()).setLore(" ", "§e" + k.getPrice() + " Coins", " ", hasKit(p, k)).setName(k.getName()).build());
        }

        new SimpleMovingInventory(getUhc(), p, ((UHCFileRegister.getKitsFile().getKits().size() / 9) + 1) * 18, stacks, UHCFileRegister.getKitsFile().getInventoryName(),
                UHCFileRegister.getKitsFile().getNextItem().getItemStack(),
                UHCFileRegister.getKitsFile().getPreviousItem().getItemStack());
    }

    private String hasKit(Player p, Kit k) {
        return UHCFileRegister.getAchievementFile().getAchievementUnlockName(UHCRegister.getStatsUtil().hasKit(k, p));
    }
}
