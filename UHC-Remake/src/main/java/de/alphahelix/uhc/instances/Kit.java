package de.alphahelix.uhc.instances;

import de.alphahelix.alphaapi.utils.SerializationUtil;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class Kit implements Serializable {

    private static final SerializationUtil<Kit> SERIALIZER = new SerializationUtil<>();
    private String name;
    private double price;
    private ItemStack guiBlock;
    private ItemStack[] items;

    public Kit(String name, double price, ItemStack guiBlock, ItemStack... items) {
        setName(name.replace("_", " ").replace("§", "&"));
        setPrice(price);
        setGuiBlock(guiBlock);
        setItems(items);
    }

    public static Kit fromString(String json) {
        return SERIALIZER.deserialize(SerializationUtil.stringToJson(json));
    }

    public void registerKit() {
        UHCFileRegister.getKitsFile().addKit(getName(), getPrice(), getGuiBlock(), getItems());
    }

    public String getName() {
        return name.replace("&", "§");
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ItemStack getGuiBlock() {
        return guiBlock;
    }

    public void setGuiBlock(ItemStack guiBlock) {
        this.guiBlock = guiBlock;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return getName() + " : " + getPrice() + " : " + getGuiBlock() + " : " + getItems();
    }

    public String serialize() {
        return SerializationUtil.jsonToString(SERIALIZER.serialize(this));
    }
}
