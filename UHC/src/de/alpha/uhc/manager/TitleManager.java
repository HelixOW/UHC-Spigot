package de.alpha.uhc.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleManager {
	
    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke((Object)player, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", TitleManager.getNMSClass("Packet")).invoke(playerConnection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        try {
            if (title != null) {
                title = ChatColor.translateAlternateColorCodes('&', title);
                title = title.replaceAll("%player%", player.getDisplayName());
                Object enumTitle = TitleManager.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                Object chatTitle = TitleManager.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                @SuppressWarnings("rawtypes")
				Constructor titleConstructor = TitleManager.getNMSClass("PacketPlayOutTitle").getConstructor(TitleManager.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], TitleManager.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object titlePacket = titleConstructor.newInstance(enumTitle, chatTitle, fadeIn, stay, fadeOut);
                TitleManager.sendPacket(player, titlePacket);
            }
            if (subtitle != null) {
                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
                subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
                Object enumSubtitle = TitleManager.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                Object chatSubtitle = TitleManager.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");
                @SuppressWarnings("rawtypes")
				Constructor subtitleConstructor = TitleManager.getNMSClass("PacketPlayOutTitle").getConstructor(TitleManager.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], TitleManager.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object subtitlePacket = subtitleConstructor.newInstance(enumSubtitle, chatSubtitle, fadeIn, stay, fadeOut);
                TitleManager.sendPacket(player, subtitlePacket);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTabTitle(Player player, String header, String footer) {
        if (header == null) {
            header = "";
        }
        header = ChatColor.translateAlternateColorCodes('&', header);
        if (footer == null) {
            footer = "";
        }
        footer = ChatColor.translateAlternateColorCodes('&', footer);
        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());
        try {
            Object tabHeader = TitleManager.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
            Object tabFooter = TitleManager.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
            @SuppressWarnings("rawtypes")
			Constructor titleConstructor = TitleManager.getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(TitleManager.getNMSClass("IChatBaseComponent"));
            Object packet = titleConstructor.newInstance(tabHeader);
            Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabFooter);
            TitleManager.sendPacket(player, packet);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
    }

    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    public static void sendAction(Player p, String msg) {
        try {
            if (TitleManager.getServerVersion().equalsIgnoreCase("v1_8_R2") || TitleManager.getServerVersion().equalsIgnoreCase("v1_8_R3")) {
                Object icbc = TitleManager.getNmsClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, "{'text': '" + msg + "'}");
                Object ppoc = TitleManager.getNmsClass("PacketPlayOutChat").getConstructor(TitleManager.getNmsClass("IChatBaseComponent"), Byte.TYPE).newInstance(icbc, Byte.valueOf((byte) 2));
                Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke((Object)p, new Object[0]);
                Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
                pcon.getClass().getMethod("sendPacket", TitleManager.getNmsClass("Packet")).invoke(pcon, ppoc);
            } else {
                Object icbc = TitleManager.getNmsClass("ChatSerializer").getMethod("a", String.class).invoke(null, "{'text': '" + msg + "'}");
                Object ppoc = TitleManager.getNmsClass("PacketPlayOutChat").getConstructor(TitleManager.getNmsClass("IChatBaseComponent"), Byte.TYPE).newInstance(icbc, Byte.valueOf((byte) 2));
                Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke((Object)p, new Object[0]);
                Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
                pcon.getClass().getMethod("sendPacket", TitleManager.getNmsClass("Packet")).invoke(pcon, ppoc);
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

