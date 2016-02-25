package net.minetopix.library.main.nms;

import java.util.List;

import net.minetopix.library.main.utils.ReflectionUtil;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class ActionBarUtil {

	public static void send(Player player, String message){
		try {
			Object notify = ReflectionUtil.serializeString(message);
			Object packet = ReflectionUtil.getNmsClass("PacketPlayOutChat").getConstructor(new Class<?>[] {
					ReflectionUtil.getNmsClass("IChatBaseComponent") , byte.class
			}).newInstance(new Object[] {
					notify , (byte) 2
			});
			
			ReflectionUtil.sendPacket(player, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void send(List<Player> player, String message){
		for(Player p : player) {
			send(p, message);
		}
	}
	
	public static void send(World world, String message){
		for(Player p : world.getPlayers()){
			send(p, message);
		}
	}
	
	public static void clear(Player player){
		send(player, "");
	}
	
	public static void clear(List<Player> player){
		for(Player p : player){
			clear(p);
		}
	}
	
	public static void clear(World world){
		for(Player p : world.getPlayers()){
			clear(p);
		}
	}
	
}
