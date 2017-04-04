package de.alphahelix.uhc.inventories;

import de.alphahelix.alphaapi.inventorys.SimpleMovingInventory;
import de.alphahelix.alphaapi.item.ItemBuilder;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class KitInventory {


    public void openInventory(Player p) {

        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (Kit k : UHCFileRegister.getKitsFile().getKits()) {
            stacks.add(new ItemBuilder(k.getGuiBlock().getType()).setLore(" ", "§e" + k.getPrice() + " Coins", " ", hasKit(p, k)).setName(k.getName()).build());
        }

        new SimpleMovingInventory(
                p, ((UHCFileRegister.getKitsFile().getKits().size() / 9) + 1) * 18, stacks, UHCFileRegister.getKitsFile().getInventoryName(),
                UHCFileRegister.getKitsFile().getNextItem().getItemStack(),
                UHCFileRegister.getKitsFile().getPreviousItem().getItemStack());
    }

    private String hasKit(Player p, Kit k) {
        return UHCFileRegister.getAchievementFile().getAchievementUnlockName(StatsUtil.hasKit(UUIDFetcher.getUUID(p), k));
    }
}
