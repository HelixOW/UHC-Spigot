package net.minetopix.library.main.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.minetopix.library.main.utils.ReflectionUtil;

public class TablistUtil {
	
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
