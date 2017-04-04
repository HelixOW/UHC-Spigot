package de.alphahelix.uhc.files;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.alphaapi.item.ItemBuilder;
import org.bukkit.Material;

public class SpectatorFile extends SimpleFile {

    public SpectatorFile() {
        super("spectator.uhc");
    }

    @Override
    public void addValues() {
        setDefault("GUI name", "§aTeleport");

        setInventoryItem("Item", new ItemBuilder(Material.MAGMA_CREAM).setName("&cTeleporter").build(), 4);
    }

    public String getInventoryName() {
        return getColorString("GUI name");
    }

    public InventoryItem getItem() {
        return getInventoryItem("Item");
    }
}
