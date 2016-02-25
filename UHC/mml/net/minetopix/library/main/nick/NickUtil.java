package net.minetopix.library.main.nick;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

import net.minetopix.library.main.nms.classes.REnumPlayerInfoAction;
import net.minetopix.library.main.utils.PacketUtil;
import net.minetopix.library.main.utils.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

public class NickUtil {

	private static HashMap<Player, GameProfile> lastProfile = new HashMap<>();
	private static HashMap<UUID, String[]> nickData = new HashMap<>();
	
	public static void nickPlayer(final Player p, final String name, final String skin) {

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Object entityPlayer = ReflectionUtil.getEntityPlayer(p);
				
				try {
					
					if(Bukkit.getPlayer(ChatColor.stripColor(name)) != null) {
						p.sendMessage(ChatColor.RED + "Der Spieler ist online");
						return;
					}
					
					//Gameprofile
					Object gameProfile = lastProfile.containsKey(p) ? lastProfile.get(p) : ReflectionUtil.getNmsClass("EntityHuman").getMethod("getProfile").invoke(entityPlayer);
					GameProfile customGameProfile = GameProfileFetcher.getGameProfile(name, skin);
					
					//Catch NullPointer Exceptoin
					if(customGameProfile == null) {
						System.err.println("Cannot read Gameprofile");
						return;
					}
					
					// Saves last Profile
					lastProfile.put(p,customGameProfile);
					nickData.put(p.getUniqueId(), new String[]{
						name , skin
					});
					
					// Create the Player remove Packets
					Object pPacketPlayerInfoRemove = PacketUtil.createPlayerInfoPacket(REnumPlayerInfoAction.REMOVE_PLAYER.getPlayerInfoAction(), gameProfile, -1, null, null);
					Object pPacketPlayerInfoRemove2 = PacketUtil.createPlayerInfoPacket(REnumPlayerInfoAction.REMOVE_PLAYER.getPlayerInfoAction(), ReflectionUtil.getNmsClass("EntityHuman").getMethod("getProfile").invoke(entityPlayer), -1, null, null);
					
					Object pPacketPlayOutEntityDestroy = ReflectionUtil.getNmsClass("PacketPlayOutEntityDestroy").getConstructor(int[].class).newInstance(new int[]{
							ReflectionUtil.getID(p)
					});
					
					// Create the Player add Packets
					Object pPacketPlayerInfoAdd = PacketUtil.createPlayerInfoPacket(REnumPlayerInfoAction.ADD_PLAYER.getPlayerInfoAction(), customGameProfile, ReflectionUtil.getPing(p), ReflectionUtil.getEnumGamemode(p), name);
					
					Object pPacketPlayOutEntitySpawn = ReflectionUtil.getNmsClass("PacketPlayOutNamedEntitySpawn").getConstructor(ReflectionUtil.getNmsClass("EntityHuman")).newInstance(entityPlayer);
					
					//Set the UUID in PlayerAddPacket
					Field uuid = pPacketPlayOutEntitySpawn.getClass().getDeclaredField("b");
					uuid.setAccessible(true);
					uuid.set(pPacketPlayOutEntitySpawn, customGameProfile.getClass().getMethod("getId").invoke(customGameProfile));
							
					//Send the Packet
					for(Player all : Bukkit.getOnlinePlayers()) {
						if(all == p) continue;
						
						ReflectionUtil.sendPacket(all, pPacketPlayOutEntityDestroy);
						ReflectionUtil.sendPacket(all, pPacketPlayerInfoRemove);
						ReflectionUtil.sendPacket(all, pPacketPlayerInfoRemove2);
					}
					
					Thread.sleep(50);
										
					for(Player all : Bukkit.getOnlinePlayers()) {
						if(all == p) continue;
						
						ReflectionUtil.sendPacket(all, pPacketPlayerInfoAdd);
						ReflectionUtil.sendPacket(all, pPacketPlayOutEntitySpawn);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				
			}
		}).start();
		
	}
	
	public static void performLeft(final Player p) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					
					Object gameProfile = lastProfile.containsKey(p) ? lastProfile.get(p) : null;
					
					if(gameProfile == null) {
						return;
					}
				
					Object pPacketPlayerInfoRemove = PacketUtil.createPlayerInfoPacket(REnumPlayerInfoAction.REMOVE_PLAYER.getPlayerInfoAction(), gameProfile, -1, null, null);
					
					Object pPacketPlayOutEntityDestroy = ReflectionUtil.getNmsClass("PacketPlayOutEntityDestroy").getConstructor(int[].class).newInstance(new int[]{
							ReflectionUtil.getID(p)
					});
					
					for(Player all : Bukkit.getOnlinePlayers()) {
						if(all == p) continue;
						
						ReflectionUtil.sendPacket(all, pPacketPlayOutEntityDestroy);
						ReflectionUtil.sendPacket(all, pPacketPlayerInfoRemove);
					}
					
					lastProfile.remove(p);
			
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				
			}
		}).start();
		
	}
	
	public static void respawnPlayer(Player p) {
				
		try {
			
			for(Player all : Bukkit.getOnlinePlayers()) {
				
				if(all != p) {
					spawnPlayer(p, all);
					spawnPlayer(all, p);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public static void spawnPlayer(Player p , Player reciver) {
		try {
			
			Object entityPlayer = ReflectionUtil.getEntityPlayer(p);
			
			GameProfile pr = lastProfile.containsKey(p) ? lastProfile.get(p) : (GameProfile) ReflectionUtil.getNmsClass("EntityHuman")
					.getMethod("getProfile")
					.invoke(entityPlayer);
			
			Object pPacketPlayOutEntitySpawn = ReflectionUtil.getNmsClass("PacketPlayOutNamedEntitySpawn")
					.getConstructor(ReflectionUtil.getNmsClass("EntityHuman"))
					.newInstance(entityPlayer);
			
			Field uuid = pPacketPlayOutEntitySpawn.getClass().getDeclaredField("b");
			uuid.setAccessible(true);
			uuid.set(pPacketPlayOutEntitySpawn, pr.getClass().getMethod("getId").invoke(pr));
			
			ReflectionUtil.sendPacket(reciver , pPacketPlayOutEntitySpawn);
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static String[] getNickData(Player p) {
		return nickData.get(p);
	}

	
}
