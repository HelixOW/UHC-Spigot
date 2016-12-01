package de.alphahelix.alphalibary.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleTitle {

    /**
     * Send a Title & Subtitle to a Player
     *
     * @param p       Player you want to send the title
     * @param title   The Bigtitle
     * @param sub     The Undertitle
     * @param fadeIn  How long to fade in in seconds
     * @param stay    How long to stay in seconds
     * @param fadeOut How long to fade out in seconds
     */
    public static void sendTitle(Player p, String title, String sub, int fadeIn, int stay, int fadeOut) {
        try {

            Class<?> cEnumTitleAction = de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("PacketPlayOutTitle$EnumTitleAction");
            Class<?> cIChatBaseComponent = de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("IChatBaseComponent");

            Constructor<?> titleConstructor = de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("PacketPlayOutTitle").getConstructor(cEnumTitleAction, cIChatBaseComponent);
            Constructor<?> timingConstructor = de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("PacketPlayOutTitle").getConstructor(int.class, int.class, int.class);

            Object pTitle = titleConstructor.newInstance(TitleAction.getNmsEnumObject(TitleAction.TITLE), de.alphahelix.alphalibary.reflection.ReflectionUtil.serializeString(title));
            Object pSubTitle = titleConstructor.newInstance(TitleAction.getNmsEnumObject(TitleAction.SUBTITLE), de.alphahelix.alphalibary.reflection.ReflectionUtil.serializeString(sub));
            Object pTimings = timingConstructor.newInstance(fadeIn * 20, stay * 20, fadeOut * 20);

            de.alphahelix.alphalibary.reflection.ReflectionUtil.sendPacket(p, pTimings);
            de.alphahelix.alphalibary.reflection.ReflectionUtil.sendPacket(p, pTitle);
            de.alphahelix.alphalibary.reflection.ReflectionUtil.sendPacket(p, pSubTitle);

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
            if (action < 0 || action > 4) {
                return null;
            }
            return de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("PacketPlayOutTitle$EnumTitleAction").getEnumConstants()[action];
        }

    }
}
