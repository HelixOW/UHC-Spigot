package de.alphahelix.alphalibary.nms;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


public class SimpleActionBar {

    /**
     * Sends a player a ActionBar
     *
     * @param player  to send the ActionBar
     * @param message to be displayed inside the ActionBar
     */
    public static void send(Player player, String message) {
        try {
            Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + ReflectionUtil.getVersion() + ".entity.CraftPlayer");
            Object p = c1.cast(player);
            Object ppoc;
            Class<?> c4 = Class.forName("net.minecraft.server." + ReflectionUtil.getVersion() + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + ReflectionUtil.getVersion() + ".Packet");
            Class<?> c2 = Class.forName("net.minecraft.server." + ReflectionUtil.getVersion() + ".ChatComponentText");
            Class<?> c3 = Class.forName("net.minecraft.server." + ReflectionUtil.getVersion() + ".IChatBaseComponent");
            Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
            ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(o, (byte) 2);
            Method m1 = c1.getDeclaredMethod("getHandle");
            Object h = m1.invoke(p);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Send a list of players a ActionBar
     *
     * @param player  to send the ActionBar
     * @param message to be displayed inside the ActionBar
     */
    public static void send(List<Player> player, String message) {
        for (Player p : player) {
            send(p, message);
        }
    }

    /**
     * Send a ActionBar into a World
     *
     * @param world   to send the Actionbar
     * @param message to be displayed inside the ActionBar
     */
    public static void send(World world, String message) {
        for (Player p : world.getPlayers()) {
            send(p, message);
        }
    }

    /**
     * Clear the ActionBar for a player
     *
     * @param player
     */
    private static void clear(Player player) {
        send(player, "");
    }

    /**
     * Clear the ActionBar for a list of players
     *
     * @param player
     */
    public static void clear(List<Player> player) {
        for (Player p : player) {
            clear(p);
        }
    }

    /**
     * Clear the ActionBar inside a world
     *
     * @param world
     */
    public static void clear(World world) {
        for (Player p : world.getPlayers()) {
            clear(p);
        }
    }

}
