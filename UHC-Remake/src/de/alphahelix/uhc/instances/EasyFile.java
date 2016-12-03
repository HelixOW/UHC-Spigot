package de.alphahelix.uhc.instances;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

public abstract class EasyFile extends SimpleFile<UHC> {

    private Registery register;

    public EasyFile(String name, UHC uhc) {
        super("plugins/UHC", name, uhc);
        setRegister(getPluginInstance().getRegister());
        getRegister().getEasyFiles().add(this);
    }

    public EasyFile(String path, String name, UHC uhc) {
        super("plugins/UHC/" + path, name, uhc);
        setRegister(getPluginInstance().getRegister());
        getRegister().getEasyFiles().add(this);
    }

    public abstract void addValues();

    public void loadValues() {
    }

    public void register(EasyFile easy) {
        easy.addValues();
        easy.loadValues();
    }

    public Material getMaterial(String path) {
        return Material.getMaterial(getString(path).replace(" ", "_").toUpperCase());
    }

    public SimpleFile<UHC> getFile() {
        return this;
    }

    public ItemBuilder getItemBuilder(String path) {
        String[] data = getString(path).split(";");
        return new ItemBuilder(Material.getMaterial(data[0])).setAmount(Integer.parseInt(data[1]))
                .setDamage(Short.parseShort(data[2]));
    }

    public void setItem(String path, ItemStack item) {
        set(path, item.getType() + ";" + item.getAmount() + ";" + item.getDurability());
        save();
    }

    public boolean configContains(String arg) {
        boolean boo = false;
        ArrayList<String> keys = new ArrayList<>();
        keys.addAll(this.getKeys(true));
        for (String key : keys)
            if (key.equalsIgnoreCase(arg))
                boo = true;

        return boo;

    }

    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> toList(T... args) {
        ArrayList<T> toReturn = new ArrayList<>();
        Collections.addAll(toReturn, args);
        return toReturn;
    }

    public Registery getRegister() {
        return register;
    }

    private void setRegister(Registery register) {
        this.register = register;
    }

    public Logger getLog() {
        return Bukkit.getLogger();
    }
}
