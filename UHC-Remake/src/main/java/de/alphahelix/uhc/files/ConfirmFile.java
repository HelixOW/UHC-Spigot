package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.UHC;
import org.bukkit.Material;

public class ConfirmFile extends SimpleFile<UHC> {

    public ConfirmFile(UHC uhc) {
        super("confirm.uhc", uhc);
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
