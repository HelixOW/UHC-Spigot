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

import org.bukkit.entity.Player;

/**
 * Needs a rework
 */
public class NettyAPI {

    private static String handlerName;

    private static PacketHandlerStore store = new PacketHandlerStore();

    private static boolean init = false;

    public static void initialize(String channelName) {
        if (channelName != null) {
            handlerName = channelName;
            init = true;
        }
    }

    public static void addListener(PacketHandler handler) {
        store.addHandler(handler);
    }

    public static void removeListener(Class<?> clazz) {
        store.removeHandler(clazz);
    }


    public static void injectPlayer(Player p) {
        // Check for init
        if (!init) {
            System.err.println("NettyAPI is not initialized");
            return;
        }
        // UHCRegister Injection if not have done yet
        if (NettyHandler.getChannel(p).pipeline().get(handlerName) == null) {
            NettyHandler.PacketInjection in = new NettyHandler.PacketInjection(store, p);
            NettyHandler.getChannel(p).pipeline().addBefore("packet_handler", handlerName, in);
        }
    }

    public static void uninject(Player p) {
        // Check for init
        if (!init) {
            System.err.println("NettyAPI is not initialized");
            return;
        }
        NettyHandler.PacketInjection handle = (NettyHandler.PacketInjection) NettyHandler.getChannel(p).pipeline().get(handlerName);
        if (handle != null) {
            NettyHandler.getChannel(p).pipeline().remove(handle);
        }
    }

}
