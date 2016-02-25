package net.minetopix.library.main.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.util.HashMap;

import net.minetopix.library.main.utils.ReflectionUtil;

import org.bukkit.entity.Player;

public class NettyHandler {

	private static HashMap<Player, Channel> channels = new HashMap<>();
		
	public static Channel getChannel(Player p) {
		
		Channel c = channels.get(p);
		
		if(c == null) {
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
		
		private PacketHandlerStore store;
		private Player p;
		
		public PacketInjection(PacketHandlerStore handler , Player p) {
			this.store = handler;
			this.p = p;
		}
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			try {
				for(PacketHandler handler : store.getAllHandler()) {
					
					Method[] toInvoke = handler.getMethodesFor(msg.getClass());
					
					for(int i = 0 ; i < toInvoke.length ; i++) {
						msg = toInvoke[i].invoke(handler, msg , p);
					}
					
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			super.channelRead(ctx, msg);
		}
		
	}
	
}
