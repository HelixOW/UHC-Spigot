package de.alphahelix.uhc.inventories;

import de.alphahelix.alphalibary.inventorys.SimpleMovingInventory;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class KitInventory extends Util {


    public KitInventory(UHC uhc) {
        super(uhc);
    }

    public void openInventory(Player p) {
        ArrayList<ItemStack> stacks = getRegister().getKitsFile().getKits().stream().map(k -> new ItemBuilder(k.getGuiBlock().getType()).setLore(" ", "§e"+k.getPrice() + " Coins", " ", hasKit(p, k)).setName(k.getName()).build()).collect(Collectors.toCollection(ArrayList::new));
        new SimpleMovingInventory(getUhc(), stacks, getRegister().getKitsFile().getColorString("GUI.Name"), p, ((getRegister().getKitsFile().getKits().size() / 9) + 1) * 18, getRegister().getKitsFile().getColorString("Preview GUI.Next page"),
                getRegister().getKitsFile().getColorString("Preview GUI.Previous page"));
    }

    private String hasKit(Player p, Kit k) {
        if(getRegister().getStatsUtil().hasKit(k, p)) {
            return getRegister().getAchievementFile().getColorString("hasAchievement.true");
        } else {
            return getRegister().getAchievementFile().getColorString("hasAchievement.false");
        }
    }
}
