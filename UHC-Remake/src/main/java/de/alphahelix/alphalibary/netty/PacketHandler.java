/*
 *     Copyright (C) <2016>  <AlphaHelixDev>
 *
 *     This program is free software: you can redistribute it under the
 *     terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.alphahelix.alphalibary.netty;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Needs a rework
 */
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
