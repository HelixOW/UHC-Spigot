package de.popokaka.alphalibary.nms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.popokaka.alphalibary.reflection.ReflectionUtil;

public class SimpleTablist {

	/**
	 * Set the Tablist of the Player
	 * @param p Player you want to change the Tablist for
	 * @param header What stands above the players
	 * @param footer What stands below the players
	 */
	public static void setTablistHeaderFooter(Player p, String header, String footer) {

		if (header == null)
			header = "";
		if (footer == null)
			footer = "";

		try {
			Object headerPacket = ReflectionUtil.getNmsClass("ChatComponentText").getConstructor(new Class[] { String.class })
					.newInstance(new Object[] { ChatColor.translateAlternateColorCodes('&', header) });
			Object footerPacket = ReflectionUtil.getNmsClass("ChatComponentText").getConstructor(new Class[] { String.class })
					.newInstance(new Object[] { ChatColor.translateAlternateColorCodes('&', footer) });

			Object ppoplhf = ReflectionUtil.getNmsClass("PacketPlayOutPlayerListHeaderFooter")
					.getConstructor(new Class[] { ReflectionUtil.getNmsClass("IChatBaseComponent") })
					.newInstance(new Object[] { headerPacket });

			Field f = ppoplhf.getClass().getDeclaredField("b");
			f.setAccessible(true);
			f.set(ppoplhf, footerPacket);

			Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
			Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);

			pcon.getClass().getMethod("sendPacket", new Class[] { ReflectionUtil.getNmsClass("Packet") })
					.invoke(pcon, new Object[] { ppoplhf });

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {

		}
	}
	
}
