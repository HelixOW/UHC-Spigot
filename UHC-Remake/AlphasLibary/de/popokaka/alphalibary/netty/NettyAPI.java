package de.popokaka.alphalibary.netty;

import org.bukkit.entity.Player;

import de.popokaka.alphalibary.netty.NettyHandler.PacketInjection;

public class NettyAPI {

	private static String handlerName;

	private static PacketHandlerStore store = new PacketHandlerStore();

	private static boolean init = false;

	public static void initialize(String channelName) {
		if(channelName != null) {
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
		// Register Injection if not have done yet
		if (NettyHandler.getChannel(p).pipeline().get(handlerName) == null) {
			PacketInjection in = new PacketInjection(store , p);
			NettyHandler.getChannel(p).pipeline().addBefore("packet_handler", handlerName, in);
		}
	}

	public static void uninject(Player p) {
		// Check for init
		if (!init) {
			System.err.println("NettyAPI is not initialized");
			return;
		}
		PacketInjection handle = (PacketInjection) NettyHandler.getChannel(p).pipeline().get(handlerName);
		if (handle != null) {
			NettyHandler.getChannel(p).pipeline().remove(handle);
		}
	}

}
