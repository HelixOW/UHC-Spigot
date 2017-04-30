package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.instances.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class KitsFile extends SimpleFile {

    private HashMap<String, Kit> kits = new HashMap<>();

    public KitsFile() {
        super("plugins/UHC-Remake","kits.uhc");
    }

    @Override
    public void addValues() {
        setDefault("Enabled", true);

        setDefault("GUI name", "&6Kits");
        setDefault("Preview GUI name", "&3Preview&7: [kit]");

        setInventoryItem("Page.Next", new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((short) 5).setName("&aNext page").build(), 0);
        setInventoryItem("Page.Previous", new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((short) 14).setName("&cPrevious page").build(), 0);

        setInventoryItem("Item", new ItemBuilder(Material.IRON_SWORD).setName("&6Kits").build(), 0);
    }

    public boolean isKitmode() {
        return getBoolean("Enabled");
    }

    public String getInventoryName() {
        return getColorString("GUI name");
    }

    public String getPreviewInventoryName(Kit kit) {
        return getColorString("Preview GUI name").replace("[kit]", kit.getName());
    }

    public InventoryItem getNextItem() {
        return getInventoryItem("Page.Next");
    }

    public InventoryItem getPreviousItem() {
        return getInventoryItem("Page.Previous");
    }

    public InventoryItem getItem() {
        return getInventoryItem("Item");
    }

    public void addKit(String name, double price, ItemStack block, ItemStack... items) {
        setInventoryItem("Custom Kits." + name + ".block", block, 0);
        setDefault("Custom Kits." + name + ".price", price);
        setBase64ItemStackArray("Custom Kits." + name + ".contents", items);
    }

    public Kit getKit(String name) {
        name = name.replace(" ", "_");
        name = name.replace("§", "&");

        if (configContains("Custom Kits." + name))
            return new Kit(name, getDouble("Custom Kits." + name + ".price"), getInventoryItem("Custom Kits." + name + ".block").getItemStack(), getBase64ItemStackArray("Custom Kits." + name + ".contents"));

        Bukkit.getLogger().log(Level.SEVERE, "The kit " + name + " doesn't exist inside the kits.uhc");
        return null;
    }

    public ArrayList<Kit> getKits() {
        ArrayList<Kit> kits = new ArrayList<>();

        if (!isConfigurationSection("Custom Kits")) return kits;

        for (String names : getConfigurationSection("Custom Kits").getKeys(false)) {
            kits.add(getKit(names));
        }
        return kits;
    }

    public void setPlayedKit(Player p, Kit k) {
        kits.put(p.getName(), k);
    }

    public void removePlayedKit(Player p) {
        kits.remove(p.getName());
    }

    public Kit getPlayedKit(Player p) {
        return kits.containsKey(p.getName()) ? kits.get(p.getName()) : null;
    }

    public boolean hasKit(Player p) {
        return getPlayedKit(p) != null;
    }

}
