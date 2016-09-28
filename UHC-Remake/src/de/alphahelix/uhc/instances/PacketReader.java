package de.alphahelix.uhc.instances;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.popokaka.alphalibary.netty.NettyHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class PacketReader {

	Player p;
	Channel ch;

	public PacketReader(Player injectet) {
		p = injectet;
	}

	public void inject() {
		ch = NettyHandler.getChannel(p);
		ch.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Object>() {
			@Override
			protected void decode(ChannelHandlerContext arg0, Object pack, List<Object> arg2) throws Exception {
				arg2.add(pack);
				readPacket(pack.getClass());
			}
		});
	}

	public void uninject() {
		if (ch.pipeline().get("PacketInjector") != null) {
			ch.pipeline().remove("PacketInjector");
		}
	}

	public void readPacket(Class<?> packet) {
		Bukkit.getConsoleSender().sendMessage(packet.getSimpleName());
	}

	public void setValue(Class<?> obj, String name, Object value) {
		try {
			Field field = obj.getDeclaredField(name);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
		}
	}

	public Object getValue(Class<?> obj, String name) {
		try {
			Field field = obj.getDeclaredField(name);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
		}
		return null;
	}

}
