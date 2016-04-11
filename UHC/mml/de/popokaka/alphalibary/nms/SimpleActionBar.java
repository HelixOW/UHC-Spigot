package de.popokaka.alphalibary.nms;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;

import de.popokaka.alphalibary.reflection.ReflectionUtil;

public class SimpleActionBar {
	
	/**
	 * Sends a player a ActionBar
	 * @param player to send the ActionBar
	 * @param message to be displayed inside the ActionBar
	 */
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
	
	/**
	 * Send a list of players a ActionBar
	 * @param player to send the ActionBar
	 * @param message to be displayed inside the ActionBar
	 */
	public static void send(List<Player> player, String message){
		for(Player p : player) {
			send(p, message);
		}
	}
	
	/**
	 * Send a ActionBar into a World
	 * @param world to send the Actionbar
	 * @param message to be displayed inside the ActionBar
	 */
	public static void send(World world, String message){
		for(Player p : world.getPlayers()){
			send(p, message);
		}
	}
	
	/**
	 * Clear the ActionBar for a player
	 * @param player
	 */
	public static void clear(Player player){
		send(player, "");
	}
	
	/**
	 * Clear the ActionBar for a list of players
	 * @param player
	 */
	public static void clear(List<Player> player){
		for(Player p : player){
			clear(p);
		}
	}
	
	/**
	 * Clear the ActionBar inside a world
	 * @param player
	 */
	public static void clear(World world){
		for(Player p : world.getPlayers()){
			clear(p);
		}
	}

}
