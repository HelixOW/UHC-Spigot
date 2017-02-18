/*
 * Copyright (C) <2017>  <AlphaHelixDev>
 *
 *       This program is free software: you can redistribute it under the
 *       terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.alphahelix.alphalibary.file;

import de.alphahelix.alphalibary.AlphaPlugin;
import de.alphahelix.alphalibary.utils.SerializationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class SimpleFile<P extends AlphaPlugin> extends YamlConfiguration {

    private P pl;
    private File source = null;

    /**
     * Create a new {@link SimpleFile} inside the given path with the name 'name'
     *
     * @param path   the path where the {@link File} should be created in
     * @param name   the name which the {@link File} should have
     * @param plugin your main class which extends '{@link AlphaPlugin}'
     */
    public SimpleFile(String path, String name, P plugin) {
        this.pl = plugin;
        new File(path).getParentFile().mkdirs();
        source = new File(path, name);
        createIfNotExist();
    }

    /**
     * Create a new {@link SimpleFile} inside the plugin path with the name 'name'
     *
     * @param plugin your main class which extends '{@link AlphaPlugin}'
     * @param name   the name which the file should have
     */
    public SimpleFile(String name, P plugin) {
        if (plugin == null) {
            return;
        }
        this.pl = plugin;
        source = new File(plugin.getDataFolder().getPath(), name);
        createIfNotExist();
    }

    /**
     * Convert a normal {@link File} into a {@link SimpleFile}
     *
     * @param f      the old File which you want to convert
     * @param plugin your main class which extends {@link AlphaPlugin}
     */
    public SimpleFile(File f, P plugin) {
        this.pl = plugin;
        source = f;
        createIfNotExist();
    }

    /**
     * Finish the setup of the {@link SimpleFile}
     */
    private void finishSetup() {
        try {
            load(source);
        } catch (Exception ignored) {

        }
    }

    /**
     * Overridden method to add new standart values to a config
     */
    public void addValues() {
    }

    /**
     * Create a new {@link SimpleFile} if it's not existing
     */
    private void createIfNotExist() {
        options().copyDefaults(true);
        if (source == null || !source.exists()) {
            try {
                source.createNewFile();
            } catch (IOException e) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            source.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.runTaskLaterAsynchronously(getPluginInstance(), 20);
            }
        }
        finishSetup();
    }

    /**
     * Get a colored {@link String}
     *
     * @param path the path inside this {@link SimpleFile}
     * @return the {@link String} with Colors
     */
    public String getColorString(String path) {
        if (!contains(path))
            return "";

        try {
            String toReturn = getString(path);
            return ChatColor.translateAlternateColorCodes('&', toReturn);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get a colored {@link ArrayList} out of this {@link SimpleFile}
     *
     * @param path the path inside this {@link SimpleFile}
     * @return the {@link ArrayList} with Colors
     */
    public ArrayList<String> getColorStringList(String path) {
        if (!configContains(path)) return new ArrayList<>();
        if (!isList(path)) return new ArrayList<>();

        try {
            ArrayList<String> tR = new ArrayList<>();
            for (String str : getStringList(path)) {
                tR.add(ChatColor.translateAlternateColorCodes('&', str));
            }
            return tR;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Save a base64 encoded {@link ItemStack}[] inside this {@link SimpleFile}
     *
     * @param path   where to save the {@link ItemStack}[]
     * @param toSave the {@link ItemStack}[] to save
     */
    public void setBase64ItemStackArray(String path, ItemStack... toSave) {
        SerializationUtil<ItemStack[]> serializer = new SerializationUtil<>();

        setDefault(path, SerializationUtil.jsonToString(serializer.serialize(toSave)));
    }

    public void setBase64InventoryItemArray(String path, InventoryItem... toSave) {
        SerializationUtil<InventoryItem[]> serializer = new SerializationUtil<>();

        setDefault(path, SerializationUtil.jsonToString(serializer.serialize(toSave)));
    }

    /**
     * Gets a base64 encoded {@link ItemStack}[] out of this {@link SimpleFile}
     *
     * @param path where the {@link ItemStack}[] should be located at
     * @return the {@link ItemStack}[] which was saved
     */
    public ItemStack[] getBase64ItemStackArray(String path) {
        SerializationUtil<ItemStack[]> serializer = new SerializationUtil<ItemStack[]>();

        return serializer.deserialize(SerializationUtil.stringToJson(getString(path)));
    }

    public InventoryItem[] getBase64InventoryItemArray(String path) {
        SerializationUtil<InventoryItem[]> serializer = new SerializationUtil<>();

        return serializer.deserialize(SerializationUtil.stringToJson(getString(path)));
    }

    public void setInventoryItem(String path, ItemStack itemStack, int slot) {
        setDefault(path + ".name", "null");
        setMaterial(path + ".type", itemStack.getType());
        setDefault(path + ".amount", itemStack.getAmount());
        setDefault(path + ".damage", itemStack.getDurability());
        setDefault(path + ".slot", slot);
        setMap(path + ".enchantments", new HashMap<>());
        setArgumentList(path + ".lore", "");

        if (itemStack.hasItemMeta()) {
            if (itemStack.getItemMeta().hasDisplayName()) {
                setDefault(path + ".name", itemStack.getItemMeta().getDisplayName());
            }

            if (itemStack.getItemMeta().hasLore()) {
                setArgumentList(path + ".lore", itemStack.getItemMeta().getLore().toArray(new String[itemStack.getItemMeta().getLore().size()]));
            }

            if (itemStack.getItemMeta().hasEnchants()) {
                setMap(path + ".enchantments", itemStack.getItemMeta().getEnchants());
            }
        }
    }

    public InventoryItem getInventoryItem(String path) {
        String name = getColorString(path + ".name");
        Material type = getMaterial(path + ".type");
        int amount = getInt(path + ".amount");
        short damage = (short) getInt(path + ".damage");
        HashMap<String, String> ench = getMap(path + ".enchantments");
        List<String> lore = getStringList(path + ".lore");

        ItemStack stack = new ItemStack(type, amount, damage);
        ItemMeta meta = stack.getItemMeta();

        meta.setLore(lore);
        meta.setDisplayName(name);

        for (String enchantment : ench.keySet()) {
            meta.addEnchant(Enchantment.getByName(enchantment), Integer.getInteger(ench.get(enchantment)), true);
        }

        stack.setItemMeta(meta);

        return new InventoryItem(stack, getInt(path + ".slot"));
    }

    /**
     * Saves an array of {@link org.bukkit.Material} names as a {@link List} inside this {@link SimpleFile}
     *
     * @param path      where to save the {@link List}
     * @param materials the name of the {@link org.bukkit.Material}s you want to save
     */
    public void setMaterialStringList(String path, String... materials) {
        ArrayList<String> stacks = new ArrayList<>();
        Collections.addAll(stacks, materials);
        set(path, stacks);
        save();
    }

    /**
     * Gets the {@link List} with all {@link org.bukkit.Material} names from this {@link SimpleFile}
     *
     * @param path where the {@link List} should be located at
     * @return the {@link List} with all {@link org.bukkit.Material} names
     */
    public List<String> getMaterialStringList(String path) {
        return getStringList(path);
    }

    public Material getMaterial(String path) {
        return Material.getMaterial(getString(path).replace(" ", "_").toUpperCase());
    }

    public void setMaterial(String path, Material material) {
        override(path, material.name().replace("_", " ").toLowerCase());
    }

    public void setItemInventory(String path, ItemInventory itemInventory) {
        override(path + ".title", itemInventory.getInventory().getTitle());
        override(path + ".size", itemInventory.getInventory().getSize());
        setBase64InventoryItemArray(path + ".content", itemInventory.getItems());
    }

    public Inventory getItemInventory(String path) {
        Inventory inventory = Bukkit.createInventory(null, getInt(path + ".size"), getColorString(path + ".title"));

        for (InventoryItem is : getBase64InventoryItemArray(path + ".content")) {
            if (is != null && is.getItemStack() != null && is.getItemStack().getType() != Material.AIR)
                inventory.setItem(is.getSlot(), is.getItemStack());
        }

        return inventory;
    }

    /**
     * Saves a {@link Inventory} inside this {@link SimpleFile}
     *
     * @param path   where to save the {@link Inventory}
     * @param toSave the {@link Inventory} to save
     */
    public void setInventory(String path, Inventory toSave) {
        override(path + ".title", toSave.getTitle());
        override(path + ".size", toSave.getSize());
        setBase64ItemStackArray(path + ".content", toSave.getContents());
    }

    /**
     * Gets the {@link Inventory} from this {@link SimpleFile}
     *
     * @param path where the {@link Inventory} is located at
     * @return the {@link Inventory}
     */
    public Inventory getInventory(String path) {
        Inventory toReturn = Bukkit.createInventory(null, getInt(path + ".size"), getColorString(path + ".title"));

        for (ItemStack is : getBase64ItemStackArray(path + ".content")) {
            if (is != null && is.getType() != Material.AIR)
                toReturn.addItem(is);
        }

        return toReturn;
    }

    /**
     * Saves a {@link Location} inside the {@link SimpleFile}
     *
     * @param path where to save the {@link Location}
     * @param loc  the {@link Location} to save
     */
    public void setLocation(String path, Location loc) {
        SerializationUtil<Location> serializer = new SerializationUtil<>();

        override(path, SerializationUtil.jsonToString(serializer.serialize(loc)));
    }

    /**
     * Gets a {@link Location} from this {@link SimpleFile}
     *
     * @param path where the {@link Location} should be located at
     * @return the {@link Location} which is saved
     */
    public Location getLocation(String path) {
        SerializationUtil serializer = new SerializationUtil<Location>();

        return (Location) serializer.deserialize(SerializationUtil.stringToJson(getString(path)));
    }

    public <T> void setBase64ArgumentList(String path, T... listArguments) {
        List<String> argsAtBase64 = new ArrayList<>();
        SerializationUtil<T> serializer = new SerializationUtil<>();

        for (T arg : listArguments) {
            argsAtBase64.add(SerializationUtil.jsonToString(serializer.serialize(arg)));
        }

        setDefault(path, argsAtBase64);
    }

    public <T> ArrayList<T> getBase64ArgumentList(String path) {
        ArrayList<T> args = new ArrayList<>();
        SerializationUtil<T> serializer = new SerializationUtil<>();

        if (configContains(path))
            for (Object base64arg : getList(path)) {
                args.add(serializer.deserialize(SerializationUtil.stringToJson((String) base64arg)));
            }

        return args;
    }

    public <T> void addBase64ArgumentsToList(String path, T... arguments) {
        ArrayList<T> args = getBase64ArgumentList(path);

        for (T arg : arguments) {
            args.add(arg);
        }

        setBase64ArgumentList(path, args.toArray());
    }

    public <T> void removeBase64ArgumentsFromList(String path, T... arguments) {
        ArrayList<T> args = getBase64ArgumentList(path);

        for (T arg : arguments) {
            if (args.contains(arg)) {
                args.remove(arg);
                break;
            }
        }

        if (!args.isEmpty())
            setBase64ArgumentList(path, args.toArray());
        else
            override(path, null);
    }

    public void setArgumentList(String path, String... listArguments) {
        List<String> arguments = new ArrayList<>();

        for (String arg : listArguments) {
            arguments.add(arg);
        }

        setDefault(path, arguments);
    }

    public void addArgumentsToList(String path, String... arguments) {
        List<String> args = getStringList(path);

        for (String arg : arguments) {
            args.add(arg);
        }

        setArgumentList(path, args.toArray(new String[args.size()]));
    }

    public void removeArgumentsFromList(String path, String... arguments) {
        List<String> args = getStringList(path);

        for (String arg : arguments) {
            if (args.contains(arg)) {
                args.remove(arg);
                break;
            }
        }

        if (!args.isEmpty())
            setArgumentList(path, args.toArray(new String[args.size()]));
        else
            override(path, null);
    }

    public <K, V> void setMap(String path, Map<K, V> map) {
        ArrayList<String> keyToValue = new ArrayList<>();

        for (K k : map.keySet()) {
            keyToValue.add(k.toString() + " <:> " + map.get(k).toString());
        }

        setArgumentList(path, keyToValue.toArray(new String[keyToValue.size()]));
    }

    public HashMap<String, String> getMap(String path) {
        HashMap<String, String> map = new HashMap<>();
        List<String> keyToValue = getStringList(path);

        for (String seri : keyToValue) {
            String k = seri.split(" <:> ")[0];
            String v = seri.split(" <:> ")[1];

            map.put(k, v);
        }

        return map;
    }

    /**
     * Checks if this {@link SimpleFile} contains a specific {@link String}
     *
     * @param toCheck {@link String} which might be inside this {@link SimpleFile}
     * @return whether or not this {@link SimpleFile} contains the {@link String}
     */
    public boolean configContains(String toCheck) {
        boolean cContains = false;


        for (String key : getKeys(true)) {
            if (key.equalsIgnoreCase(toCheck))
                cContains = true;
        }

        return cContains;

    }

    /**
     * Save and load this {@link SimpleFile}
     */
    public void save() {
        try {
            if (source == null) return;
            save(source);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * Add a new value to this {@link SimpleFile}
     *
     * @param path  where the value should be saved at
     * @param value the value which you want to save
     */
    public void setDefault(String path, Object value) {
        if (path.contains("§")) {
            path = path.replaceAll("§", "&");
        }
        if (value instanceof String)
            value = ((String) value).replaceAll("§", "&");

        addDefault(path, value);

        save();
    }

    /**
     * Replaces a value inside this {@link SimpleFile}
     *
     * @param path  where the value is located at
     * @param value the new value which should be saved
     */
    public void override(String path, Object value) {
        if (value instanceof String)
            value = ((String) value).replaceAll("§", "&");

        if (configContains(path))
            set(path, value);
        else
            addDefault(path, value);
        save();
    }

    /**
     * Gets the {@link AlphaPlugin} which was used to create this {@link SimpleFile}
     *
     * @return the {@link AlphaPlugin}
     */
    public P getPluginInstance() {
        return pl;
    }

    public class InventoryItem implements Serializable {

        private ItemStack itemStack;
        private int slot;

        public InventoryItem(ItemStack itemStack, int slot) {
            this.itemStack = itemStack;
            this.slot = slot;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        public int getSlot() {
            return slot;
        }
    }

    public class ItemInventory implements Serializable {

        private Inventory inventory;
        private InventoryItem[] items;

        public ItemInventory(Inventory inventory, InventoryItem... items) {
            this.inventory = inventory;
            this.items = items;
        }

        public ItemInventory(String name, int size, InventoryItem... items) {
            this.inventory = Bukkit.createInventory(null, size, name);
            this.items = items;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public InventoryItem[] getItems() {
            return items;
        }
    }
}