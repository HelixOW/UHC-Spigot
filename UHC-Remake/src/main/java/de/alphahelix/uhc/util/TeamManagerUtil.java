package de.alphahelix.uhc.util;

import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class TeamManagerUtil {

    private static ArrayList<UHCTeam> teams = new ArrayList<>();
    private static int maxTeammember = 4;
    private static Material m;

    public static Material getIconMaterial() {
        return m;
    }

    public static void setIconMaterial(Material m) {
        TeamManagerUtil.m = m;
    }

    public static ArrayList<UHCTeam> getTeams() {
        return teams;
    }

    public static ItemStack getTeamItem() {
        return UHCFileRegister.getTeamFile().getItem().getItemStack();
    }

    public static UHCTeam getTeamByIcon(ItemStack icon, Material m) {
        UHCTeam s = null;
        for (UHCTeam t : teams)
            if (t.getIcon(m).equals(icon))
                s = t;
        return s;
    }

    public static UHCTeam getLowestTeam() {
        UHCTeam t = null;
        int i = Integer.MAX_VALUE;
        for (UHCTeam h : teams)
            if (i > h.getSize()) {
                i = h.getSize();
                t = h;
            }
        return t;
    }

    public static UHCTeam getTeamByName(String name) {
        for (UHCTeam t : teams)
            if (t.getName().equals(name))
                return t;
        return null;
    }

    public static UHCTeam getTeamByPrefix(String prefix) {
        for (UHCTeam t : teams)
            if (t.getPrefix().equals(prefix))
                return t;
        return null;
    }

    public static UHCTeam getTeamByData(byte data) {
        for (UHCTeam t : teams)
            if (t.getColorData() == data)
                return t;
        return null;
    }

    public static UHCTeam getTeamByInvSlot(int invSlot) {
        for (UHCTeam t : teams)
            if (t.getInvSlot() == invSlot)
                return t;
        return null;
    }

    public static UHCTeam getTeamByPlayer(Player p) {
        for (UHCTeam t : teams)
            if (t.getPlayers().contains(p))
                return t;
        return null;
    }

    public static int getTeamAmount() {
        return teams.size();
    }

    public static UHCTeam registerTeam(String name, String prefix, byte colorData, int invSlot, boolean isColoredName, Color c) {
        return new UHCTeam(name, prefix, colorData, maxTeammember, invSlot, isColoredName, c);
    }

    public static UHCTeam registerTeam(String name, String prefix, byte colorData, int maxPlayer, int invSlot,
                                       boolean isColoredName, Color c) {
        return new UHCTeam(name, prefix, colorData, maxPlayer, invSlot, isColoredName, c);
    }

    public static UHCTeam registerTeam(String name, String prefix, byte colorData, int invSlot, Color c) {
        return new UHCTeam(name, prefix, colorData, maxTeammember, invSlot, true, c);
    }

    public static UHCTeam registerTeam(String name, String prefix, byte colorData, int maxPlayer, int invSlot, Color c) {
        return new UHCTeam(name, prefix, colorData, maxPlayer, invSlot, true, c);
    }

    public static void addTeam(UHCTeam team) {
        teams.add(team);
    }

    public static boolean existTeam(UHCTeam team) {
        return teams.contains(team);
    }

    public static UHCTeam isInOneTeam(OfflinePlayer p) {
        for (UHCTeam t : teams)
            if (t.isInTeam(p))
                return t;
        return null;
    }

    public static boolean isSameTeam(OfflinePlayer p, OfflinePlayer p2) {
        return isInOneTeam(p) != null && isInOneTeam(p2) != null && isInOneTeam(p).equals(isInOneTeam(p2));
    }

    public static void splitPlayersIntoTeams() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (isInOneTeam(p) == null) {
                getLowestTeam().addToTeam(p);
            }
        }
    }
}
