package de.alphahelix.uhc;

import org.bukkit.Material;

public enum ArmorBar {

    NONE(0),

    LEATHER_HELMET(0.5),
    LEATHER_CHESTPLATE(1.5),
    LEATHER_LEGGINGS(1),
    LEATHER_BOOTS(0.5),

    GOLD_HELMET(1),
    GOLD_CHESTPLATE(2.5),
    GOLD_LEGGINGS(1.5),
    GOLD_BOOTS(0.5),

    CHAIN_HELMET(1),
    CHAIN_CHESTPLATE(2.5),
    CHAIN_LEGGINGS(2),
    CHAIN_BOOTS(0.5),

    IRON_HELMET(1),
    IRON_CHESTPLATE(3),
    IRON_LEGGINGS(2.5),
    IRON_BOOTS(1),

    DIAMOND_HELMET(1.5),
    DIAMOND_CHESTPLATE(4),
    DIAMOND_LEGGINGS(3),
    DIAMOND_BOOTS(2.5);

    private final double points;

    ArmorBar(double points) {
        this.points = points;
    }

    public static ArmorBar getArmorBarByMaterial(Material m) {
        try {
            return ArmorBar.valueOf(m.name());
        } catch (Exception e) {
            return ArmorBar.NONE;
        }
    }

    public double getPoints() {
        return points;
    }
}
