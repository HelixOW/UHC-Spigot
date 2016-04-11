package de.popokaka.alphalibary.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ReflectionUtil {
	
private static String version;
	
	static {
		String packageName = Bukkit.getServer().getClass().getPackage().getName();
		version = packageName.substring(packageName.lastIndexOf(".") + 1);
	}
	
	//############################################## FIELDS #################################################
		
	public static SaveField getField(String name , Class<?> clazz) {
		try {
			Field f = clazz.getField(name);
			return new SaveField(f);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static SaveField getDeclaredField(String name , Class<?> clazz) {
		try {
			Field f = clazz.getDeclaredField(name);
			return new SaveField(f);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//############################################## METHODS #####################################
	
	public static SaveMethod getMethode(String name , Class<?> clazz , Class<?>... parameterClasses) {
		try {	
			Method m = clazz.getMethod(name, parameterClasses);
			return new SaveMethod(m);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static SaveMethod getDeclaredMethode(String name , Class<?> clazz , Class<?>... parameterClasses) {
		try {	
			Method m = clazz.getDeclaredMethod(name, parameterClasses);
			m.setAccessible(true);
			return new SaveMethod(m);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getClass(String name , boolean asArray) {
		try {
			if(asArray) return Array.newInstance(Class.forName(name), 0).getClass();
			else return Class.forName(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//######################################################## MINECRAFT ###################################

	public static String getNmsPrefix() {
		return "net.minecraft.server." + version + ".";
	}
	
	public static String getCraftBukkitPrefix() {
		return "org.bukkit.craftbukkit." + version + ".";
	}
	
	public static Class<?> getNmsClass(String name) {
		return getClass("net.minecraft.server." + version + "." + name , false);
	}
	
	public static Class<?> getNmsClassAsArray(String name) {
		return getClass("net.minecraft.server." + version + "." + name , true);
	}
	
	public static Class<?> getCraftBukkitClass(String name) {
		return getClass("org.bukkit.craftbukkit." + version + "." + name , false);
	}
	
	public static Class<?> getCraftBukkitClassAsArray(String name) {
		return getClass("org.bukkit.craftbukkit." + version + "." + name , true);
	}
	
	public static Object getEntityPlayer(Player p) {
		try {
			return getCraftBukkitClass("entity.CraftPlayer").getMethod("getHandle").invoke(p);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object serializeString(String s) {
		try {
			Class<?> chatSerelizer = getCraftBukkitClass("util.CraftChatMessage");
			
			Method mSerelize = chatSerelizer.getMethod("fromString", String.class);

			return ((Object[])mSerelize.invoke(null, s))[0];
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static String fromIChatBaseComponent(Object component) {
		
		try {
			Class<?> chatSerelizer = getCraftBukkitClass("util.CraftChatMessage");
			
			Method mSerelize = chatSerelizer.getMethod("fromComponent", ReflectionUtil.getNmsClass("IChatBaseComponent"));

			return (String) mSerelize.invoke(null, component);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		
	}
	
	public static Object getEnumGamemode(Player p) {
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
	
	public static void sendPacket(Player p , Object packet) {
		try {
			Object nmsPlayer = getEntityPlayer(p);
			
			Field fieldCon = nmsPlayer.getClass().getDeclaredField("playerConnection");
			Object nmsCon = fieldCon.get(nmsPlayer);
			
			Method sendPacket = nmsCon.getClass().getMethod("sendPacket", getNmsClass("Packet"));
			sendPacket.invoke(nmsCon,packet);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static int getID(Entity e) {
		try {
			
			Object entityEntity = getCraftBukkitClass("entity.CraftEntity").getMethod("getHandle").invoke(e);
			
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
	
	public static Object getWorldServer(World w) {
		try {
			return getCraftBukkitClass("CraftWorld").getMethod("getHandle").invoke(w);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object getObjectNMSItemStack(ItemStack item) {
		try {
			Object copy = getCraftBukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null,item);
			return copy;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String[] fromIChatBaseComponent(Object[] baseComponentArray) {
		
		String[] array = new String[baseComponentArray.length];
		
		for(int i = 0 ; i < array.length ; i++) {
			array[i] = fromIChatBaseComponent(baseComponentArray[i]);
		}
		
		return array;
	}
	
	public static Object[] serializeString(String[] strings) {
		
		Object[] array = (Object[]) Array.newInstance(getNmsClass("IChatBaseComponent"), strings.length);
		
		for(int i = 0 ; i < array.length ; i++) {
			array[i] = serializeString(strings[i]);
		}
		
		return array;
	}
	
	//################################################## OTHER STUFF ###################################
	
	public static int floor(double d) {
		try {
			return (int) ReflectionUtil.getNmsClass("MathHelper").getMethod("floor", double.class).invoke(null, d);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
	}

}
