package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.Kit;
import org.bukkit.Material;

import java.util.Random;

public class CrateFile extends SimpleFile<UHC> {
    private static final Random RANDOM = new Random();

    public CrateFile(UHC plugin) {
        super("crates.uhc", plugin);
    }

    @Override
    public void addValues() {
        setDefault("Enabled", true);
        setDefault("GUI name", "&5Crates");
        setDefault("Open GUI name", "&5Crate &7: [crate]");

        setInventoryItem("Item", new ItemBuilder(Material.CHEST).setName("&5Crates").build(), 4);
    }

    public void addCrate(Crate crate) {
        setDefault("CustomCrates." + crate.getRawName() + ".display name", crate.getName());
        setDefault("CustomCrates." + crate.getRawName() + ".rarity value", crate.getRarity());
        setInventoryItem("CustomCrates." + crate.getRawName() + ".icon", crate.getIcon(), 0);
        setBase64ArgumentList("CustomCrates." + crate.getRawName() + ".kits", crate.getKits().toArray(new Kit[crate.getKits().size()]));
    }

    public Crate getRandomCrate() {
        return Crate.getCrateArrayList().get(RANDOM.nextInt(Crate.getCrateArrayList().size()));
    }

    public void initCrates() {
        if(!isConfigurationSection("CustomCrates")) return;
        for(String cratesKeys : getConfigurationSection("CustomCrates").getKeys(false)) {
            new Crate(
                    getColorString("CustomCrates." + cratesKeys + ".display name"),
                    getInt("CustomCrates." + cratesKeys + ".rarity value"),
                    getInventoryItem("CustomCrates." + cratesKeys + ".icon").getItemStack(),
                    getBase64ArgumentList("CustomCrates." + cratesKeys + ".kits").toArray(new Kit[getBase64ArgumentList("CustomCrates." + cratesKeys + ".kits").size()]));
        }
    }

    public boolean isEnabled() {
        return getBoolean("Enabled");
    }

    public String getInventoryName() {
        return getColorString("GUI name");
    }

    public String getOpenInventoryName(String crateName) {
        return getColorString("Open GUI name").replace("[crate]", getColorString("CustomCrates." + crateName + ".display name"));
    }

    public InventoryItem getItem() {
        return getInventoryItem("Item");
    }
}
