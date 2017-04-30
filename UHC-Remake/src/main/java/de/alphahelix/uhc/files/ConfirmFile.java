package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import org.bukkit.Material;

public class ConfirmFile extends SimpleFile {

    public ConfirmFile() {
        super("plugins/UHC-Remake","confirm.uhc");
    }

    @Override
    public void addValues() {
        setDefault("GUI name", "&cConfirm!");

        setInventoryItem("Accept", new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((short) 5).setName("§aConfirm!").build(), 0);
        setInventoryItem("Deny", new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((short) 14).setName("§cDeny!").build(), 0);
    }

    public String getInventoryName() {
        return getColorString("GUI name");
    }

    public InventoryItem getItem(boolean accept) {
        return (accept ? getInventoryItem("Accept") : getInventoryItem("Deny"));
    }
}
