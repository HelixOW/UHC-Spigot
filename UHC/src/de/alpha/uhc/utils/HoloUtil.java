package de.alpha.uhc.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.alpha.uhc.files.HologramFileManager;
import net.minetopix.library.main.utils.ReflectionUtil;

public class HoloUtil {
	
	public void createHologram(Player p, int id){
		Location loc = new HologramFileManager().getLocation(id);
		
		String name = new HologramFileManager().getName(id);
		
		Class<?> cWorld = ReflectionUtil.getNmsClass("World");
		Class<?>[] param = {double.class, double.class, double.class, float.class, float.class};
		
		Constructor<?> holoConstructor = null;
		try {
			holoConstructor = ReflectionUtil.getNmsClass("EntityArmorStand").getConstructor(cWorld);
		} catch (NoSuchMethodException | SecurityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Class<?> cholo = ReflectionUtil.getNmsClass("EntityArmorStand");
		try {
			
//		new EntityArmorStand(world).setLocation(d0, d1, d2, f, f1);
			
			Object hc = holoConstructor.newInstance(ReflectionUtil.getWorldServer(loc.getWorld()));
			name = name.replace("[Player]", p.getDisplayName());
			name = name.replace("[Kills]", Integer.toString(new Stats(p).getKills()));
			name = name.replace("[Deaths]", Integer.toString(new Stats(p).getDeaths()));
			name = name.replace("[Coins]", Integer.toString(new Stats(p).getCoins()));
			
			cholo.getMethod("setLocation", param)
				.invoke(hc,
						new Object[] {
							loc.getX(),
							loc.getY(),
							loc.getZ(),
							0F,
							0F
						});
			cholo.getMethod("setInvisible", boolean.class)
				.invoke(hc, true);
			cholo.getMethod("setCustomName", String.class)
			.invoke(hc, name);
			cholo.getMethod("setCustomNameVisible", boolean.class)
			.invoke(hc, true);
			
			Constructor<?> conpacket = ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving").getConstructor(ReflectionUtil.getNmsClass("EntityLiving"));
			Object packet = conpacket.newInstance(hc);
			
			ReflectionUtil.sendPacket(p, packet);
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		name = new HologramFileManager().getName(id); 
	}
	
	public void showHologram(Player p) {
		for(int id = 0; id < new HologramFileManager().holocount(); id++) {
			
			Location loc = new HologramFileManager().getLocation(id);
			
		
			String name = new HologramFileManager().getName(id);
			
			Class<?> cWorld = ReflectionUtil.getNmsClass("World");
			Class<?>[] param = {double.class, double.class, double.class, float.class, float.class};
			
			Constructor<?> holoConstructor = null;
			try {
				holoConstructor = ReflectionUtil.getNmsClass("EntityArmorStand").getConstructor(cWorld);
			} catch (NoSuchMethodException | SecurityException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Class<?> cholo = ReflectionUtil.getNmsClass("EntityArmorStand");
			try {
				
	//		new EntityArmorStand(world).setLocation(d0, d1, d2, f, f1);
				
				Object hc = holoConstructor.newInstance(ReflectionUtil.getWorldServer(loc.getWorld()));
				name = name.replace("[Player]", p.getDisplayName());
				
				cholo.getMethod("setLocation", param)
					.invoke(hc,
							new Object[] {
								loc.getX(),
								loc.getY(),
								loc.getZ(),
								0F,
								0F
							});
				cholo.getMethod("setInvisible", boolean.class)
					.invoke(hc, true);
				cholo.getMethod("setCustomName", String.class)
				.invoke(hc, name);
				cholo.getMethod("setCustomNameVisible", boolean.class)
				.invoke(hc, true);
				
				Constructor<?> conpacket = ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving").getConstructor(ReflectionUtil.getNmsClass("EntityLiving"));
				Object packet = conpacket.newInstance(hc);
				
				ReflectionUtil.sendPacket(p, packet);
				
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			name = new HologramFileManager().getName(id); 
		}
	}

}
