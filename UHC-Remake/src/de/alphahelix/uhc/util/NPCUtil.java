package de.alphahelix.uhc.util;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.nms.REnumPlayerInfoAction;
import de.popokaka.alphalibary.reflection.PacketUtil;
import de.popokaka.alphalibary.reflection.ReflectionUtil;
import de.popokaka.alphalibary.utils.GameProfileFetcher;

public class NPCUtil extends Util {

	public NPCUtil(UHC uhc) {
		super(uhc);
	}

	// return ((CraftPlayer)who).getHandle().ping; <- NMS
	/*
	 * Object handle = p.getClass().getMethod("getHandle").invoke(p);
	 * handle.getClass().getDeclaredField("ping").getInt(handle) <- Reflections
	 */

	public void spawn(Location loc, Player p) {
		try {
			Class<?> nmsServerClass = ReflectionUtil.getNmsClass("MinecraftServer");
			Class<?> nmsWorldClass = ReflectionUtil.getNmsClass("World");
			Class<?> nmsWorldServerClass = ReflectionUtil.getNmsClass("WorldServer");
			Class<?> nmsPlayerInteractManagerClass = ReflectionUtil.getNmsClass("PlayerInteractManager");
			
			Object nmsServer = Bukkit.getServer().getClass().getMethod("getServer").invoke(Bukkit.getServer());
			Object nmsWorld = loc.getWorld().getClass().getMethod("getHandle").invoke(loc.getWorld());
			Object nmsPIM = nmsPlayerInteractManagerClass.getConstructor(nmsWorldClass).newInstance(nmsWorld);
			Object npc = ReflectionUtil.getNmsClass("EntityPlayer")
					.getConstructor(nmsServerClass, nmsWorldServerClass, GameProfile.class,
							nmsPlayerInteractManagerClass)
					.newInstance(nmsServer, nmsWorld, new GameProfile(UUIDFetcher.getUUID("AlphaHelix"), "AlphaHelix"),
							nmsPIM);

			npc.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class)
					.invoke(npc, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 0, 0);
			
			GameProfile gp = GameProfileFetcher.getGameProfile("AlphaHelix", "AlphaHelix");
			
			Object PPOPlayerInfo = PacketUtil.createPlayerInfoPacket(
					REnumPlayerInfoAction.ADD_PLAYER.getPlayerInfoAction(),
					gp,
					0,
					ReflectionUtil.getNmsClass("WorldSettings$EnumGamemode").getEnumConstants()[0],
					"AlphaHelix");

			Object PPONamedEntitySpawn = ReflectionUtil.getNmsClass("PacketPlayOutNamedEntitySpawn")
					.getConstructor(ReflectionUtil.getNmsClass("EntityHuman"))
					.newInstance(npc);
			
			
			
			ReflectionUtil.sendPacket(p, PPOPlayerInfo);
			ReflectionUtil.sendPacket(p, PPONamedEntitySpawn);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			e.printStackTrace();
		}

		/*
			MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
			WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
			EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld,
					new GameProfile(UUIDFetcher.getUUID("AlphaHelix"), "AlphaHelix"), new PlayerInteractManager(nmsWorld));

			npc.setLocation(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 0, 0);

			PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		*/
	}
}
