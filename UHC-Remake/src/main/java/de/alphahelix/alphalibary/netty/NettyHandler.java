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
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Needs a rework
 */
public class NettyHandler {

    private static HashMap<Player, Channel> channels = new HashMap<>();

    public static Channel getChannel(Player p) {

        Channel c = channels.get(p);

        if (c == null) {
            Object entityPlayer = ReflectionUtil.getEntityPlayer(p);
            Object connection = ReflectionUtil.getField("playerConnection", ReflectionUtil.getNmsClass("EntityPlayer")).get(entityPlayer, true);
            Object network = ReflectionUtil.getField("networkManager", ReflectionUtil.getNmsClass("PlayerConnection")).get(connection, true);

            Channel channel = (Channel) ReflectionUtil.getDeclaredField("channel", ReflectionUtil.getNmsClass("NetworkManager")).get(network, true);

            channels.put(p, channel);
            c = channel;
        }

        return c;
    }

    public static class PacketInjection extends ChannelDuplexHandler {

        private de.alphahelix.alphalibary.netty.PacketHandlerStore store;
        private Player p;

        public PacketInjection(de.alphahelix.alphalibary.netty.PacketHandlerStore handler, Player p) {
            this.store = handler;
            this.p = p;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            try {
                for (PacketHandler handler : store.getAllHandler()) {

                    Method[] toInvoke = handler.getMethodesFor(msg.getClass());

                    for (Method aToInvoke : toInvoke) {
                        msg = aToInvoke.invoke(handler, msg, p);
                    }

                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            super.channelRead(ctx, msg);
        }

    }

}
