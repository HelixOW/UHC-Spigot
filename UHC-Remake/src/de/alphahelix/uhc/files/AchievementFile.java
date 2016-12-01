package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.UHCAchievements;
import de.alphahelix.uhc.instances.EasyFile;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlphaHelixDev.
 */
public class AchievementFile extends EasyFile {

    public AchievementFile(UHC plugin) {
        super("achievements.uhc", plugin);
    }

    @Override
    public void addValues() {
        setDefault("Item.Name", "&aAchievements");
        setDefault("Item.Type", "book");
        setDefault("Item.Slot", 6);
        setDefault("GUI.Name", "&aAchievements");
        setDefault("hasAchievement.true", "&aUnlocked!");
        setDefault("hasAchievement.false", "&cLocked!");
        setDefault("Achievement unlocked", "&7You just unlocked the achievement &8: &a[achievement]");

        for (UHCAchievements uhcAchievements : UHCAchievements.values()) {
            List<String> save = new ArrayList<>();

            save.add("§7Add your description here!");

            setDefault("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".name", "&7" + uhcAchievements.name().replace("_", " ").toLowerCase());
            setDefault("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".icon", "paper");
            setDefault("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".description", save);
        }
    }

    public ItemStack getItem() {
        return new ItemBuilder(Material.getMaterial(getString("Item.Type").replace(" ", "_").toUpperCase())).setName(getColorString("Item.Name")).build();
    }

    public void registerAchievements() {
        for (UHCAchievements uhcAchievements : UHCAchievements.values()) {
            uhcAchievements.setIcon(new ItemStack(Material.getMaterial(getString("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".icon").replace(" ", "_").toUpperCase())));
            uhcAchievements.setName(getColorString("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".name"));
            uhcAchievements.setDesc(getStringList("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".description").toArray(new String[getStringList("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".description").size()]));
        }
    }
}
