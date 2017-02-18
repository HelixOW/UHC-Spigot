package de.alphahelix.uhc.instances;

import de.alphahelix.alphalibary.UUID.UUIDFetcher;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class Crate implements Serializable {

    private static final ArrayList<Crate> CRATE_ARRAY_LIST = new ArrayList<>();
    private static final HashMap<UUID, String> CRATE_AMOUNTS = new HashMap<>();
    private double rarity;
    private String name;
    private ItemStack icon;
    private ArrayList<Kit> kits = new ArrayList<>();

    public Crate(String name, double rarity, ItemStack icon, Kit... kits) {
        this.rarity = rarity;
        this.name = name;
        this.icon = icon;
        Collections.addAll(this.kits, kits);
        CRATE_ARRAY_LIST.add(this);
    }

    public static ArrayList<Crate> getCrateArrayList() {
        return CRATE_ARRAY_LIST;
    }

    public static Crate getCrateByName(String name) {
        for (Crate c : CRATE_ARRAY_LIST) {
            if (c.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase())) return c;
        }
        return null;
    }

    public static Crate getCrateByRawName(String name) {
        for (Crate c : CRATE_ARRAY_LIST) {
            if (c.getRawName().toLowerCase().equalsIgnoreCase(name.toLowerCase())) return c;
        }
        return null;
    }

    public static void setCrates(OfflinePlayer p, String crates) {
        CRATE_AMOUNTS.put(UUIDFetcher.getUUID(p), crates);
    }

    public static void addCrate(Crate c, OfflinePlayer p) {
        if (CRATE_AMOUNTS.containsKey(UUIDFetcher.getUUID(p))) {
            CRATE_AMOUNTS.put(
                    UUIDFetcher.getUUID(p),
                    CRATE_AMOUNTS.get(UUIDFetcher.getUUID(p)) + c.getRawName() + ";");
        } else {
            CRATE_AMOUNTS.put(UUIDFetcher.getUUID(p), c.getRawName() + ";");
        }
    }

    public static void removeCrate(Crate c, OfflinePlayer p) {
        if (!CRATE_AMOUNTS.containsKey(UUIDFetcher.getUUID(p))) return;
        if (getCrateCount(c, p) >= 1) {
            CRATE_AMOUNTS.put(
                    UUIDFetcher.getUUID(p),
                    CRATE_AMOUNTS.get(UUIDFetcher.getUUID(p)).replaceFirst(c.getRawName() + ";", ""));
        } else {
            CRATE_AMOUNTS.remove(UUIDFetcher.getUUID(p));
        }
    }

    public static long getCrateCount(Crate crate, OfflinePlayer p) {
        if (!CRATE_AMOUNTS.containsKey(UUIDFetcher.getUUID(p))) {
            return 0;
        }
        long amount = 0;

        for (String crates : CRATE_AMOUNTS.get(UUIDFetcher.getUUID(p)).split(";")) {
            if (getCrateByRawName(crates) == null) continue;
            if (!getCrateByRawName(crates).equals(crate)) continue;
            amount += 1;
        }

        return amount;
    }

    public static boolean hasCrate(Crate c, OfflinePlayer p) {
        return getCrateCount(c, p) >= 1;
    }

    public void registerCrate() {
        UHCFileRegister.getCrateFile().addCrate(this);
    }

    public boolean equals(Crate crate) {
        return ChatColor.stripColor(this.getName()).equals(ChatColor.stripColor(crate.getName()));
    }

    public double getRarity() {
        return rarity;
    }

    public String getRawName() {
        return ChatColor.stripColor(name).replace(" ", "_");
    }

    public String getName() {
        return name.replace("&", "§").replace("_", " ");
    }

    public ItemStack getIcon() {
        return new ItemBuilder(icon).setName(getName()).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build();
    }

    public ArrayList<Kit> getKits() {
        return kits;
    }

    public ArrayList<ItemStack> getKitIcons() {
        ArrayList<ItemStack> icons = new ArrayList<>();
        for (Kit k : getKits()) {
            icons.add(new ItemBuilder(k.getGuiBlock()).setName(k.getName()).build());
        }
        return icons;
    }
}
