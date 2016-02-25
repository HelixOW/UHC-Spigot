package net.minetopix.library.main.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.authlib.GameProfile;

public class PacketUtil {

	public static enum EquipmentSlot {
		
		INHAND(0),
		BOOTS(1),
		LEGGINGS(2),
		CHESTPLATE(3),
		HELMET(4);
		
		private int id;
		
		private EquipmentSlot(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
	}
	
	public static void equipEntity(final Entity p , ItemStack toEquip , EquipmentSlot slot , final Player[] players) {
		
		try {
			int id = ReflectionUtil.getID(p);
			
			final Object packet = ReflectionUtil.getNmsClass("PacketPlayOutEntityEquipment").getConstructor(new Class<?>[] {
					int.class , int.class , ReflectionUtil.getNmsClass("ItemStack")
			}).newInstance(new Object[] {
					id , slot.getID() , ReflectionUtil.getObjectNMSItemStack(toEquip)
			});
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					for(Player all : players) {
						
						if(all == p) {
							continue;
						}
						
						ReflectionUtil.sendPacket(all, packet);
					}
				}
			}.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
	@SuppressWarnings("unchecked")
	public static Object createPlayerInfoPacket(Object enumPlayerInfoAction, Object gameProfile , int ping , Object enumGamemode, String name) {
		
		Class<?> cIChatBaseComponent = ReflectionUtil.getNmsClass("IChatBaseComponent");
		Class<?> cPacketPlayOutPlayerInfo = ReflectionUtil.getNmsClass("PacketPlayOutPlayerInfo");
		Class<?> cPlayerInfoData = ReflectionUtil.getNmsClass("PacketPlayOutPlayerInfo$PlayerInfoData");
		Class<?> cEnumGamemode = ReflectionUtil.getNmsClass("WorldSettings$EnumGamemode");

		
		
		try {
			
			Object pPacketPlayOutInfo = cPacketPlayOutPlayerInfo.getConstructor().newInstance();
			
			Field fa = pPacketPlayOutInfo.getClass().getDeclaredField("a");
			fa.setAccessible(true);
			fa.set(pPacketPlayOutInfo, enumPlayerInfoAction);
			
			Object oPlayerInfoData = cPlayerInfoData.getConstructor(cPacketPlayOutPlayerInfo , GameProfile.class , int.class , cEnumGamemode, cIChatBaseComponent)
					.newInstance(pPacketPlayOutInfo , gameProfile , ping , enumGamemode , ReflectionUtil.serializeString(name));
			
			Field b = pPacketPlayOutInfo.getClass().getDeclaredField("b");
			b.setAccessible(true);
			ArrayList<Object> array = (ArrayList<Object>) b.get(pPacketPlayOutInfo);
			
			array.add(oPlayerInfoData);
			
			b.set(pPacketPlayOutInfo, array);
					
			return pPacketPlayOutInfo;
			
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
}
