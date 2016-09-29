package de.alphahelix.uhc.util;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.item.LeatherItemBuilder;
import de.popokaka.alphalibary.item.data.SkullData;
import de.popokaka.alphalibary.nms.REnumPlayerInfoAction;
import de.popokaka.alphalibary.reflection.PacketUtil;
import de.popokaka.alphalibary.reflection.ReflectionUtil;

public class NPCUtil extends Util {

	public NPCUtil(UHC uhc) {
		super(uhc);
	}

	public void spawnNPC(Location loc, Player p) {
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

			GameProfile gp = new GameProfile(UUIDFetcher.getUUID("AlphaHelix"), "AlphaHelix");

			Object PPOPlayerInfo = PacketUtil.createPlayerInfoPacket(
					REnumPlayerInfoAction.ADD_PLAYER.getPlayerInfoAction(), gp, 0, ReflectionUtil.getEnumGamemode(p),
					"AlphaHelix");

			Object PPONamedEntitySpawn = ReflectionUtil.getNmsClass("PacketPlayOutNamedEntitySpawn")
					.getConstructor(ReflectionUtil.getNmsClass("EntityHuman")).newInstance(npc);

			ReflectionUtil.sendPacket(p, PPOPlayerInfo);
			ReflectionUtil.sendPacket(p, PPONamedEntitySpawn);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			e.printStackTrace();
		}
	}

	public void spawnArmorStand(Location l, String name) {
		if (getRegister().getTeamManagerUtil().getTeamByName(name) == null)
			return;

		UHCTeam team = getRegister().getTeamManagerUtil().getTeamByName(name);

		ArmorStand as = l.getWorld().spawn(l, ArmorStand.class);

		ItemStack head = new ItemBuilder(Material.SKULL_ITEM).addItemData(new SkullData("AlphaHelix")).build();
		ItemStack chest = new LeatherItemBuilder(Material.LEATHER_CHESTPLATE).setColor(team.getColor()).build();
		ItemStack pants = new LeatherItemBuilder(Material.LEATHER_LEGGINGS).setColor(team.getColor()).build();
		ItemStack boots = new LeatherItemBuilder(Material.LEATHER_BOOTS).setColor(team.getColor()).build();

		as.setHelmet(head);
		as.setChestplate(chest);
		as.setLeggings(pants);
		as.setBoots(boots);

		as.setBasePlate(false);
		as.setArms(true);
		as.setCustomName(team.getPrefix() + team.getName());
		as.setVisible(true);
		as.setGravity(false);
		as.setCustomNameVisible(true);

		getRegister().getLocationsFile().addArmorStand(l, name);
	}

	public void removeArmorStand(Location l, String name) {
		for (Entity e : l.getWorld().getEntitiesByClass(ArmorStand.class)) {
			if (e.isCustomNameVisible() && e.getCustomName().equals(name)) {
				getRegister().getLocationsFile().removeArmorStand(name);
				e.remove();
			}
		}
	}
}
