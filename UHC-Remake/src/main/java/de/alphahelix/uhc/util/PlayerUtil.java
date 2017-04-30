package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.utils.MinecraftVersion;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.LinkedList;

public class PlayerUtil {

    private static LinkedList<String> all = new LinkedList<>();
    private static LinkedList<String> survivors = new LinkedList<>();
    private static LinkedList<String> deaths = new LinkedList<>();

    public static void clearUp(Player p) {
        p.getInventory().clear();
        p.getEnderChest().clear();
        p.getEquipment().clear();

        p.setAllowFlight(false);
        p.setFlySpeed((float) 0.1);
        p.getInventory().setArmorContents(null);
        p.setBedSpawnLocation(null);
        p.setExp(0);
        p.setFireTicks(0);
        p.setFlying(false);
        p.setFoodLevel(20);
        p.setGameMode(GameMode.SURVIVAL);
        try {
            if (MinecraftVersion.getServer() != MinecraftVersion.EIGHT) {
                Class.forName("org.bukkit.entity.Entity").getMethod("setGlowing", boolean.class).invoke(p, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.setMaxHealth(20.0);
        p.setHealth(20.0);
        try {
            if (MinecraftVersion.getServer() != MinecraftVersion.EIGHT) {
                Class.forName("org.bukkit.entity.Entity").getMethod("setInvulnerable", boolean.class).invoke(p, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.setLevel(0);
        p.setMaxHealth(20);
        p.setTotalExperience(0);
        for (PotionEffect pe : p.getActivePotionEffects()) {
            p.removePotionEffect(pe.getType());
        }
    }

    //All Players

    public static LinkedList<String> getAll() {
        return all;
    }

    public static void addAll(Player p) {
        if (!(all.contains(p.getName()))) all.add(p.getName());
    }

    public static void removeAll(Player p) {
        if (all.contains(p.getName())) all.remove(p.getName());
        removeSurvivor(p);
        removeDead(p);
    }

    //Living Players

    public static LinkedList<String> getSurvivors() {
        return survivors;
    }

    public static void addSurvivor(Player p) {
        if (!(survivors.contains(p.getName()))) survivors.add(p.getName());
    }

    public static void addSurvivor(String name) {
        if (!(survivors.contains(name))) survivors.add(name);
    }

    public static void removeSurvivor(Player p) {
        if (survivors.contains(p.getName())) survivors.remove(p.getName());
    }

    public static void removeSurvivor(String name) {
        if (survivors.contains(name)) survivors.remove(name);
    }

    public static boolean isSurivor(Player p) {
        return survivors.contains(p.getName());
    }

    public static boolean isSurvivor(String name) {
        return survivors.contains(name);
    }

    //Death Players | Spectators |

    public static LinkedList<String> getDeads() {
        return deaths;
    }

    public static void addDead(Player p) {
        if (!(deaths.contains(p.getName()))) deaths.add(p.getName());
    }

    public static void removeDead(Player p) {
        if (deaths.contains(p.getName())) deaths.remove(p.getName());
    }

    public static void addDead(String name) {
        if (!(deaths.contains(name))) deaths.add(name);
    }

    public static void removeDead(String name) {
        if (deaths.contains(name)) deaths.remove(name);
    }

    public static boolean isDead(Player p) {
        return deaths.contains(p.getName());
    }

    public static boolean isDead(String name) {
        return deaths.contains(name);
    }

    //Other

    public static int getMinimumPlayerCount() {
        return UHCFileRegister.getOptionsFile().getMinimumPlayers();
    }

    public static int getMaximumPlayerCount() {
        return UHCFileRegister.getOptionsFile().getMaximumPlayers();
    }
}
