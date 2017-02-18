/*
 * Copyright (C) <2017>  <AlphaHelixDev>
 *
 *       This program is free software: you can redistribute it under the
 *       terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.alphahelix.alphalibary.reflection;

import de.alphahelix.alphalibary.utils.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {

    private static final String version;

    static {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        version = packageName.substring(packageName.lastIndexOf(".") + 1);
    }

    public static String getVersion() {
        return version;
    }

    public static SaveField getField(String name, Class<?> clazz) {
        try {
            Field f = clazz.getField(name);
            return new SaveField(f);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SaveField getDeclaredField(Class<?> clazz) {
        try {
            Field f = clazz.getDeclaredField("aK");
            return new SaveField(f);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SaveField getDeclaredField(String name, Class<?> clazz) {
        try {
            Field f = clazz.getDeclaredField(name);
            return new SaveField(f);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void setPrivateField(String fieldName, Class clazz, Object object, Object newValue) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, newValue);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static SaveMethod getMethod(String name, Class<?> clazz, Class<?>... parameterClasses) {
        try {
            Method m = clazz.getMethod(name, parameterClasses);
            return new SaveMethod(m);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SaveMethod getDeclaredMethod(String name, Class<?> clazz, Class<?>... parameterClasses) {
        try {
            Method m = clazz.getDeclaredMethod(name, parameterClasses);
            m.setAccessible(true);
            return new SaveMethod(m);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Class<?> getClass(String name, boolean asArray) {
        try {
            if (asArray)
                return Array.newInstance(Class.forName(name), 0).getClass();
            else
                return Class.forName(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getNmsPrefix() {
        return "net.minecraft.server." + version + ".";
    }

    public static String getCraftBukkitPrefix() {
        return "org.bukkit.craftbukkit." + version + ".";
    }

    public static Class<?> getNmsClass(String name) {
        return getClass("net.minecraft.server." + version + "." + name, false);
    }

    public static Class<?> getNmsClassAsArray(String name) {
        return getClass("net.minecraft.server." + version + "." + name, true);
    }

    public static Class<?> getCraftBukkitClass(String name) {
        return getClass("org.bukkit.craftbukkit." + version + "." + name, false);
    }

    public static Class<?> getCraftBukkitClassAsArray(String name) {
        return getClass("org.bukkit.craftbukkit." + version + "." + name, true);
    }

    public static Object getEntityPlayer(OfflinePlayer p) {
        try {
            return getCraftBukkitClass("entity.CraftPlayer").getMethod("getHandle").invoke(p);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object serializeString(String s) {
        try {
            Class<?> chatSerelizer;
            if (MinecraftVersion.getServer() == MinecraftVersion.EIGHT) {
                chatSerelizer = getCraftBukkitClass("interfaces.CraftChatMessage");
            } else {
                chatSerelizer = getCraftBukkitClass("util.CraftChatMessage");
            }

            Method mSerelize = chatSerelizer.getMethod("fromString", String.class);

            return ((Object[]) mSerelize.invoke(null, s))[0];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String fromIChatBaseComponent(Object component) {

        try {
            Class<?> chatSerelizer = getCraftBukkitClass("interfaces.CraftChatMessage");

            Method mSerelize = chatSerelizer.getMethod("fromComponent",
                    ReflectionUtil.getNmsClass("IChatBaseComponent"));

            return (String) mSerelize.invoke(null, component);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Object getEnumGamemode(OfflinePlayer p) {
        try {

            Field fInteractManager = ReflectionUtil.getNmsClass("EntityPlayer").getField("playerInteractManager");
            fInteractManager.setAccessible(true);
            Object oInteractManager = fInteractManager.get(getEntityPlayer(p));

            Field enumGamemode = ReflectionUtil.getNmsClass("PlayerInteractManager").getDeclaredField("gamemode");
            enumGamemode.setAccessible(true);

            return enumGamemode.get(oInteractManager);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getPing(Player p) {
        try {
            Field ping = getNmsClass("EntityPlayer").getDeclaredField("ping");
            ping.setAccessible(true);
            return (int) ping.get(getEntityPlayer(p));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void sendPacket(Player p, Object packet) {
        try {
            Object nmsPlayer = getEntityPlayer(p);

            Field fieldCon = nmsPlayer != null ? nmsPlayer.getClass().getDeclaredField("playerConnection") : null;
            Object nmsCon = fieldCon != null ? fieldCon.get(nmsPlayer) : null;

            Method sendPacket = nmsCon.getClass().getMethod("sendPacket", getNmsClass("Packet"));
            sendPacket.invoke(nmsCon, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendPackets(final Object... packets) {
        for (Player p : Bukkit.getOnlinePlayers())
            for (Object packet : packets)
                ReflectionUtil.sendPacket(p, packet);
    }

    public static int getID(Entity kills) {
        try {
            Object entityEntity = getCraftBukkitClass("entity.CraftEntity").getMethod("getHandle").invoke(kills);

            return (int) getNmsClass("Entity").getMethod("getId").invoke(entityEntity);

        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public static Object getEntity(Entity entity) {
        try {
            return getCraftBukkitClass("entity.CraftEntity").getMethod("getHandle").invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getEntityID(Object entity) throws NoSuchFieldException, IllegalAccessException {
        Field id = ReflectionUtil.getNmsClass("Entity").getDeclaredField("id");
        id.setAccessible(true);
        return id.getInt(entity);
    }

    public static Object getWorldServer(World world) {
        try {
            return getCraftBukkitClass("CraftWorld").getMethod("getHandle").invoke(world);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getMinecraftServer(Server server) {
        try {
            return getCraftBukkitClass("CraftServer").getMethod("getServer").invoke(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getObjectNMSItemStack(ItemStack item) {
        try {
            return getCraftBukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null,
                    item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] fromIChatBaseComponent(Object[] baseComponentArray) {

        String[] array = new String[baseComponentArray.length];

        for (int i = 0; i < array.length; i++) {
            array[i] = fromIChatBaseComponent(baseComponentArray[i]);
        }

        return array;
    }

    public static Object[] serializeString(String[] strings) {

        Object[] array = (Object[]) Array.newInstance(getNmsClass("IChatBaseComponent"), strings.length);

        for (int i = 0; i < array.length; i++) {
            array[i] = serializeString(strings[i]);
        }

        return array;
    }
}
