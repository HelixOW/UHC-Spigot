package de.alphahelix.alphalibary.item;

import de.alphahelix.alphalibary.item.data.ItemData;
import de.alphahelix.alphalibary.item.data.WrongDataException;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.Map.Entry;

public class ItemBuilder {

    private final HashMap<Enchantment, Integer> enchantments = new HashMap<>();
    private final ArrayList<ItemFlag> itemflags = new ArrayList<>();
    private final ArrayList<ItemData> itemData = new ArrayList<>();
    private String name = "";
    private Material m = Material.AIR;
    private int amount = 1;
    private short damage = 0;
    private List<String> lore = new ArrayList<>();
    private boolean unbreakable = false;

    /**
     * Create a new ItemStack with the given {@code Material}
     *
     * @param material the Material of the ItemStack
     */
    public ItemBuilder(Material material) {
        m = material;
    }

    /**
     * Add a new enchantment to the ItemStack
     *
     * @param e     The enchantment which ItemStack should have then
     * @param level The level of this enchantment
     */
    public ItemBuilder addEnchantment(Enchantment e, int level) {
        enchantments.put(e, level);
        return this;
    }

    public ItemBuilder addItemData(ItemData data) {
        itemData.add(data);
        return this;
    }

    public ArrayList<ItemData> getAllData() {
        return itemData;
    }

    /**
     * Add a gloweffect to the ItemStack
     */
    public ItemBuilder setGlow() {
        enchantments.put(Enchantment.ARROW_DAMAGE, 1);
        itemflags.add(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Add new ItemFlags to the ItemStack
     *
     * @param flagsToAdd The ItemFlag you want to add
     */
    public ItemBuilder addItemFlags(ItemFlag... flagsToAdd) {
        Collections.addAll(itemflags, flagsToAdd);
        return this;
    }

    /**
     * Remove ItemFlags from the ItemStack
     *
     * @param flagsToRemove The ItemFlag you want to remove
     */
    public ItemBuilder removeItemFlags(ItemFlag... flagsToRemove) {
        for (ItemFlag iFlag : flagsToRemove) {
            itemflags.remove(iFlag);
        }
        return this;
    }

    /**
     * Get all enchantments this ItemStack has
     *
     * @return all enchantments of this ItemStack
     */
    public HashMap<Enchantment, Integer> getAllEnchantments() {
        return enchantments;
    }

    /**
     * Get the custom name of this ItemStack
     *
     * @return the custom name of this ItemStack
     */
    public String getName() {
        return name;
    }

    /**
     * Set a custom name for the ItemStack
     *
     * @param name The new custom name of the ItemStack
     */
    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the material of this ItemStack
     *
     * @return the material of this ItemStack
     */
    public Material getMaterial() {
        return m;
    }

    /**
     * Get the amount of this ItemStack
     *
     * @return the amount of this ItemStack
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Set a custom amount for the ItemStack
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Get the durability of this ItemStack
     *
     * @return the durability of this ItemStack
     */
    public short getDamage() {
        return damage;
    }

    /**
     * Set the durability for the ItemStack
     *
     * @param damage The durability of the ItemStack
     */
    public ItemBuilder setDamage(short damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Get the lore of this ItemStack
     *
     * @return the lore of this ItemStack
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Set a custom lore for the ItemStack
     *
     * @param newLore The new custom lore of the ItemStack
     */
    public ItemBuilder setLore(String... newLore) {
        this.lore = Arrays.asList(newLore);
        return this;
    }

    /**
     * Get the breakstatus of this ItemStack
     *
     * @return the breakstatus of this ItemStack
     */
    public boolean isUnbreakable() {
        return unbreakable;
    }

    /**
     * Set the Status if a item can break or not
     *
     * @param status if the item can break or nor
     */
    public ItemBuilder setUnbreakable(boolean status) {
        this.unbreakable = status;
        return this;
    }

    /**
     * Get the final ItemStack with all the attributes you have been adding
     *
     * @return the ItemStack of this ItemBuilder
     */
    public ItemStack build() {
        ItemStack s = new ItemStack(m);
        s.setAmount(amount);
        s.setDurability(damage);
        ItemMeta m = s.getItemMeta();

        for (ItemFlag iflag : itemflags)
            m.addItemFlags(iflag);
        m.setDisplayName(name);
        m.setLore(lore);
        s.setItemMeta(m);
        for (Entry<Enchantment, Integer> temp : enchantments.entrySet())
            s.addUnsafeEnchantment(temp.getKey(), temp.getValue());

        for (ItemData id : getAllData())
            try {
                id.applyOn(s);
            } catch (WrongDataException e) {
                e.printStackTrace();
            }

        return s;
    }
}
