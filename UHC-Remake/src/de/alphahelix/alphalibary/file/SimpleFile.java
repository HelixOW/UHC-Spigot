package de.alphahelix.alphalibary.file;

import de.alphahelix.alphalibary.AlphaPlugin;
import de.alphahelix.alphalibary.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleFile<P extends AlphaPlugin> extends YamlConfiguration {

    private P pl;
    private File source = null;

    /**
     * Create a new SimpleFile inside the given path with the name 'name'
     *
     * @param path the path where the file should be created in
     * @param name the name which the file should have
     */
    public SimpleFile(String path, String name, P pl) {
        setPluginInstance(pl);
        source = new File(path, name);
        createIfNotExist();
    }

    public SimpleFile(P plugin, String name) {
        if (plugin == null) {
            return;
        }
        setPluginInstance(plugin);
        source = new File(plugin.getDataFolder().getPath(), name);
        createIfNotExist();
    }

    /**
     * Convert a normal File into a SimpleFile
     *
     * @param f the old File which you want to convert
     */
    public SimpleFile(File f, P pl) {
        setPluginInstance(pl);
        source = f;
        createIfNotExist();
    }

    /**
     * Finish the setup of the SimpleFile
     */
    private void finishSetup() {
        try {
            load(source);
        } catch (Exception ignored) {

        }
    }

    public void addValues() {

    }

    /**
     * Create a new SimpleFile if it's not existing
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
     * Get a colored String out of e.g.(&aHey)
     *
     * @param path the path inside your file
     * @return the String with Colors
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
     * Save a ItemStackArray inside an file
     *
     * @param path  The path inside the file where the ItemStackArray should be
     *              serialized to
     * @param array The ItemStackArray you want to serialize
     */
    public void setItemStackArray(String path, ItemStack... array) {
        ArrayList<String> gInfo = new ArrayList<>();
        ArrayList<String> mInfo = new ArrayList<>();
        for (ItemStack s : array) {
            if (s == null)
                continue;
            // Material:Amout:Damage
            gInfo.add(s.getType().name().toLowerCase() + ":" + s.getAmount() + ":" + s.getDurability() + ":"
                    + s.getEnchantments());

            // Name:ItemFlags:Lore
            ItemMeta m = s.getItemMeta();
            mInfo.add(m.getDisplayName() + ":" + m.getItemFlags());
        }
        set(path, gInfo);
        set(path + ".meta", mInfo);
        save();
    }

    /**
     * Get a ItemStackArray out of an file
     *
     * @param path The path where the ItemStackArray should be serialized in
     * @return The ItemStackArray at the given path
     */
    public ItemStack[] getItemStackArray(String path) {
        List<String> gInfo = getStringList(path);
        List<String> mInfo = getStringList(path + ".meta");
        ArrayList<ItemStack> tr = new ArrayList<>();

        for (String infos : gInfo) {
            String[] g = infos.split(":");

            Material mat = Material.getMaterial(g[0]);
            int amount = Integer.parseInt(g[1]);
            short dura = Short.parseShort(g[2]);

            for (String infosB : mInfo) {
                String[] m = infosB.split(":");

                String name = m[0];
                ItemFlag ifg = ItemFlag.valueOf(m[1].replace(" ", "_").toUpperCase());

                tr.add(new ItemBuilder(mat).setAmount(amount).setDamage(dura).setName(name).addItemFlags(ifg).build());
            }
        }

        return tr.toArray(new ItemStack[tr.size()]);
    }

    public void setMaterialStringList(String path, String... array) {
        ArrayList<String> stacks = new ArrayList<>();
        for (String is : array)
            stacks.add(is);
        set(path, stacks);
        save();
    }

    public List<String> getMaterialStringList(String path) {
        return getStringList(path);
    }

    public void setItemStackList(String path, ItemStack... array) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (ItemStack is : array)
            stacks.add(is);
        set(path, stacks);
    }

    @SuppressWarnings("unchecked")
    public List<ItemStack> getItemStackList(String path) {
        return (List<ItemStack>) getList(path);
    }

    public void setLocation(String path, Location loc, boolean deserialized) {
        if (deserialized) {
            String location = loc.getX() + "," + loc.getY() + "," + loc.getZ() + ","
                    + String.valueOf(loc.getWorld().getName()) + "," + loc.getYaw() + "," + loc.getPitch();
            set(path, location);
        } else {
            set(path + ".x", loc.getX());
            set(path + ".y", loc.getY());
            set(path + ".z", loc.getZ());
            set(path + ".world", loc.getWorld().getName());
            set(path + ".yaw", loc.getYaw());
            set(path + ".pitch", loc.getPitch());
        }
        save();
    }

    public NotInitLocation getLocation(String path, boolean serialized) {

        if (serialized) {
            try {

                String s = getString(path);
                String[] array = s.split(",");
                double x = Double.parseDouble(array[0]);
                double y = Double.valueOf(array[1]);
                double z = Double.valueOf(array[2]);
                String world = array[3];
                float yaw = Float.valueOf(array[4]);
                float pitch = Float.valueOf(array[5]);

                return new NotInitLocation(x, y, z, world, yaw, pitch);
            } catch (Exception e) {
                e.printStackTrace();
                // System.out.println("Die Location war nicht deserialized");
            }
            return null;
        } else {

            double x;
            double y;
            double z;
            String world = "";
            float yaw = 0F;
            float pitch = 0F;

            try {
                x = getDouble(path + ".x");
                y = getDouble(path + ".y");
                z = getDouble(path + ".z");
            } catch (Exception e) {
                System.out.println("Location " + path + ": " + ChatColor.DARK_RED + "Konnte nicht gelesen werden!");
                return null;
            }

            try {
                world = String.valueOf(get(path + ".world"));
            } catch (Exception e) {
                System.out.println("Location " + path + ": Weltname nicht vorhanden!");
            }
            try {
                yaw = getLong(path + ".yaw");
                pitch = getLong(path + ".pitch");
            } catch (Exception e) {
                System.out.println("Location " + path + ": Weltname nicht vorhanden!");
            }

            return new NotInitLocation(x, y, z, world, yaw, pitch);
        }
    }

    /**
     * Save & load the file
     */
    public void save() {
        try {
            save(source);
        } catch (IOException ignored) {
        }
    }

    /**
     * Add a new value to your file
     *
     * @param path  The path where the value should be saved at
     * @param value The value which you want to save inside your file
     */
    public void setDefault(String path, Object value) {
        if (value instanceof String)
            value = ((String) value).replaceAll("§", "&");

        addDefault(path, value);
        save();
    }

    /**
     * @return the pl
     */
    public P getPluginInstance() {
        return pl;
    }

    /**
     * @param pl the pl to set
     */
    public void setPluginInstance(P pl) {
        this.pl = pl;
    }

    public void addMaterial(String path, Material material) {
        this.setDefault(path, material.name().replace("_", " ").toLowerCase());
    }

    public Material getMaterial(String path) {
        return Material.getMaterial(getString(path).replace(" ", "_").toUpperCase());
    }

    public boolean configContains(String arg) {
        boolean boo = false;
        ArrayList<String> keys = new ArrayList<String>();
        keys.addAll(this.getKeys(false));
        for (int i = 0; i < keys.size(); i++)
            if (keys.get(i).equalsIgnoreCase(arg))
                boo = true;

        return boo;

    }
}
