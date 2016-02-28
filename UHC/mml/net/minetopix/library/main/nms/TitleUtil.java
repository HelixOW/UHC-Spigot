package net.minetopix.library.main.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import net.minetopix.library.main.utils.ReflectionUtil;

public class TitleUtil {
	
	public static void sendTitle(Player p , String title , String sub , int fadeIn, int stay, int fadeOut) {
		try {
			
			Class<?> cEnumTitleAction = ReflectionUtil.getNmsClass("PacketPlayOutTitle$EnumTitleAction");
			Class<?> cIChatBaseComponent = ReflectionUtil.getNmsClass("IChatBaseComponent");
			
			Constructor<?> titleConstructor = ReflectionUtil.getNmsClass("PacketPlayOutTitle").getConstructor(cEnumTitleAction , cIChatBaseComponent);
			Constructor<?> timingConstructor = ReflectionUtil.getNmsClass("PacketPlayOutTitle").getConstructor(int.class , int.class , int.class);
			
			Object pTitle = titleConstructor.newInstance(TitleAction.getNmsEnumObject(TitleAction.TITLE) , ReflectionUtil.serializeString(title));
			Object pSubTitle = titleConstructor.newInstance(TitleAction.getNmsEnumObject(TitleAction.SUBTITLE), ReflectionUtil.serializeString(sub));
			Object pTimings = timingConstructor.newInstance(fadeIn * 20 , stay * 20 , fadeOut * 20);
			
			ReflectionUtil.sendPacket(p, pTimings);
			ReflectionUtil.sendPacket(p, pTitle);
			ReflectionUtil.sendPacket(p, pSubTitle);
			
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
				
	}
	
	public static final class TitleAction {
		
		public static final int TITLE = 0;
		public static final int SUBTITLE = 1;
		public static final int TIMES = 2;
		public static final int CLEAR = 3;
		public static final int RESET = 4;
		
		public static Object getNmsEnumObject(int action) {
			if(action < 0 || action > 4) {
				return null;
			}
			return ReflectionUtil.getNmsClass("PacketPlayOutTitle$EnumTitleAction").getEnumConstants()[action];
		}
		
	}
}
