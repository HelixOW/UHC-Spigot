package de.alphahelix.alphalibary.nms;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class SimpleTablist {

    /**
     * Set the Tablist of the Player
     *
     * @param p      Player you want to change the Tablist for
     * @param header What stands above the players
     * @param footer What stands below the players
     */
    public static void setTablistHeaderFooter(Player p, String header, String footer) {

        if (header == null)
            header = "";
        if (footer == null)
            footer = "";

        try {
            Object headerPacket = ReflectionUtil.getNmsClass("ChatComponentText").getConstructor(new Class[]{String.class})
                    .newInstance(ChatColor.translateAlternateColorCodes('&', header));
            Object footerPacket = ReflectionUtil.getNmsClass("ChatComponentText").getConstructor(new Class[]{String.class})
                    .newInstance(ChatColor.translateAlternateColorCodes('&', footer));

            Object ppoplhf = ReflectionUtil.getNmsClass("PacketPlayOutPlayerListHeaderFooter")
                    .getConstructor(new Class[]{ReflectionUtil.getNmsClass("IChatBaseComponent")})
                    .newInstance(headerPacket);

            Field f = ppoplhf.getClass().getDeclaredField("b");
            f.setAccessible(true);
            f.set(ppoplhf, footerPacket);

            Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke(p);
            Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);

            pcon.getClass().getMethod("sendPacket", new Class[]{ReflectionUtil.getNmsClass("Packet")})
                    .invoke(pcon, ppoplhf);

        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | NoSuchFieldException ignored) {

        }
    }

}
