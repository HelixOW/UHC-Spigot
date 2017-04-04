package de.alphahelix.uhc.files;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.alphaapi.item.ItemBuilder;
import org.bukkit.Material;

public class LobbyFile extends SimpleFile {

    public LobbyFile() {
        super("lobby.uhc");
    }

    @Override
    public void addValues() {
        setDefault("Enabled", false);

        setInventoryItem("Item", new ItemBuilder(Material.REDSTONE).setName("&cLeave").build(), 8);
    }

    public boolean isLobbymode() {
        return getBoolean("Enabled");
    }

    public InventoryItem getItem() {
        return getInventoryItem("Item");
    }
}
