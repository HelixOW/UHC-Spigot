package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.UHCAchievements;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by AlphaHelixDev.
 */
public class AchievementFile extends SimpleFile<UHC> {

    public AchievementFile(UHC plugin) {
        super("achievements.uhc", plugin);
    }

    @Override
    public void addValues() {
        setInventoryItem("Item", new ItemBuilder(Material.BOOK).setName("§aAchievements").build(), 6);
        setDefault("GUI name", "&aAchievements");
        setDefault("hasAchievement.true", "&aUnlocked!");
        setDefault("hasAchievement.false", "&cLocked!");

        for (UHCAchievements uhcAchievements : UHCAchievements.values()) {
            setDefault("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".name", "&7" + uhcAchievements.name().replace("_", " ").toLowerCase());
            setDefault("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".icon", "paper");
            setArgumentList("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".description", "&7Add your description here.");
        }
    }

    public InventoryItem getItem() {
        return getInventoryItem("Item");
    }

    public String getInventoryName() {
        return getColorString("GUI name");
    }

    public String getAchievementUnlockName(boolean unlocked) {
        return (unlocked ? getColorString("hasAchievement.true") : getColorString("hasAchievement.false"));
    }

    public void registerAchievements() {
        for (UHCAchievements uhcAchievements : UHCAchievements.values()) {
            uhcAchievements.setIcon(new ItemStack(Material.getMaterial(getString("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".icon").replace(" ", "_").toUpperCase())));
            uhcAchievements.setName(getColorString("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".name"));
            uhcAchievements.setDesc(getColorStringList("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".description").toArray(new String[getColorStringList("Achievements." + uhcAchievements.name().replace("_", " ").toLowerCase() + ".description").size()]));
        }
    }
}
