package de.popokaka.alphalibary.nms;

import java.lang.reflect.Constructor;
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
			Class<?> classIChatBaseComponent = ReflectionUtil.getNmsClass("IChatBaseComponent");

			Constructor<?> listConstructor = ReflectionUtil.getNmsClass("PacketPlayOutPlayerListHeaderFooter")
					.getConstructor(classIChatBaseComponent);

			Object packageHeader = listConstructor
					.newInstance(ReflectionUtil.serializeString(ChatColor.translateAlternateColorCodes('&', header)));
			Object packageFooter = listConstructor
					.newInstance(ReflectionUtil.serializeString(ChatColor.translateAlternateColorCodes('&', footer)));

			ReflectionUtil.sendPacket(p, packageHeader);
			ReflectionUtil.sendPacket(p, packageFooter);

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {

		}
	}
	
}
