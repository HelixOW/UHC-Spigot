package de.alphahelix.alphalibary.netty;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;


public class PacketHandler {

    public Method[] getMethodesFor(Class<?> packet) {

        ArrayList<Method> found = new ArrayList<>();
        for (Method m : getClass().getMethods()) {

            if (m.getParameterTypes().length != 2) {
                continue;
            }

            if (!m.getParameterTypes()[0].equals(Object.class) || !m.getParameterTypes()[1].equals(Player.class)) {
                continue;
            }

            if (!m.getReturnType().equals(Object.class)) {
                continue;
            }

            NettyListener an = m.getAnnotation(NettyListener.class);

            Class<?> nms = ReflectionUtil.getNmsClass(an.packetName());

            if (nms.isAssignableFrom(packet)) {
                found.add(m);
            }
        }

        if (found.isEmpty()) {
            return new Method[0];
        }

        return found.toArray(new Method[0]);
    }

}
