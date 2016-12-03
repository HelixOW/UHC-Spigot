package de.alphahelix.uhc;

import de.alphahelix.alphalibary.item.ItemBuilder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AlphaHelixDev.
 */
public enum UHCAchievements {

    SLAYER(null, "Slayer"),
    WINNER(null, "Winner"),
    DRAGON_SLAYER(null, "Dragonslayer"),
    OMG_DIAMONDS(null, "OMG diamonds"),
    BURN_BABY_BURN(null, "Burn baby burn!"),
    HIGHWAY_TO_HELL(null, "Highway to hell"),
    GLOWING_IN_THE_DARK(null, "glowing in the dark"),
    SENOR_BOOM_BOOM(null, "senor boom boom");

    private ItemStack icon;
    private String name;
    private String[] desc;

    UHCAchievements(ItemStack icon, String name, String... desc) {
        this.icon = icon;
        this.name = name;
        this.desc = desc;
    }

    public ItemStack getIcon(boolean unlocked) {
        List<String> description = new ArrayList<>(Arrays.asList(desc));

        if (unlocked) {
            description.add(0, " ");
            description.add(1, UHC.getInstance().getRegister().getAchievementFile().getColorString("hasAchievement.true"));
            description.add(2, " ");
        } else {
            description.add(0, " ");
            description.add(1, UHC.getInstance().getRegister().getAchievementFile().getColorString("hasAchievement.false"));
            description.add(2, " ");
        }

        ItemBuilder ib = new ItemBuilder(icon.getType());

        ib.setName(name).setLore(description.toArray(new String[description.size()]));
        ib.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

        return ib.build();
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getDesc() {
        return desc;
    }

    public void setDesc(String[] desc) {
        this.desc = desc;
    }
}
