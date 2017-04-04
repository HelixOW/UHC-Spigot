package de.alphahelix.uhc.util;

import org.bukkit.entity.Player;

import java.util.LinkedList;

public class LobbyUtil {

    private static LinkedList<String> builders = new LinkedList<>();

    public static void grandBuildPermission(Player p) {
        if (!builders.contains(p.getName())) builders.add(p.getName());
    }

    public static void revokeBuildPermission(Player p) {
        if (builders.contains(p.getName())) builders.remove(p.getName());
    }

    public static boolean hasBuildPermission(Player p) {
        return builders.contains(p.getName());
    }

}
