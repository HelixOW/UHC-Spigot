package de.alphahelix.uhc.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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

	private Object pPOPlayerInfo;
	private Object pPONamedEntitySpawn;

	public NPCUtil(UHC uhc) {
		super(uhc);
	}

	public Object prepareNPC(Location loc, OfflinePlayer skin, int rank, Player toSend) {
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
					.newInstance(nmsServer, nmsWorld,
							new GameProfile(UUIDFetcher.getUUID(skin.getName()), skin.getName()), nmsPIM);

			npc.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class)
					.invoke(npc, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

			GameProfile gp = new GameProfile(UUIDFetcher.getUUID(skin.getName()), skin.getName());

			pPOPlayerInfo = PacketUtil.createPlayerInfoPacket(REnumPlayerInfoAction.ADD_PLAYER.getPlayerInfoAction(),
					gp, 0, ReflectionUtil.getEnumGamemode(skin), skin.getName());

			pPONamedEntitySpawn = ReflectionUtil.getNmsClass("PacketPlayOutNamedEntitySpawn")
					.getConstructor(ReflectionUtil.getNmsClass("EntityHuman")).newInstance(npc);

			Field name = npc.getClass().getField("displayName");
			name.setAccessible(true);
			name.set(npc, Integer.toString(rank));

			ReflectionUtil.sendPacket(toSend, pPOPlayerInfo);
			ReflectionUtil.sendPacket(toSend, pPONamedEntitySpawn);
			return npc;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException | NoSuchFieldException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void spawnRankingArmorStand(Location l, int rank) {
		ArmorStand as = l.getWorld().spawn(l, ArmorStand.class);
		ArmorStand holo = l.getWorld().spawn(l.subtract(0, 0.2, 0), ArmorStand.class);

		String name = getRegister().getStatsUtil().getPlayerByRank(rank).getName();
		
		ItemStack head = new ItemBuilder(Material.SKULL_ITEM)
				.addItemData(new SkullData(getRegister().getStatsUtil().getPlayerByRank(rank).getName())).build();
		ItemStack chest, pants, boots;
		if (rank == 1) {
			chest = new LeatherItemBuilder(Material.LEATHER_CHESTPLATE).setColor(Color.fromRGB(255, 215, 0)).build();
			pants = new LeatherItemBuilder(Material.LEATHER_LEGGINGS).setColor(Color.fromRGB(255, 215, 0)).build();
			boots = new LeatherItemBuilder(Material.LEATHER_BOOTS).setColor(Color.fromRGB(255, 215, 0)).build();
		}

		else if (rank == 2) {
			chest = new LeatherItemBuilder(Material.LEATHER_CHESTPLATE).setColor(Color.fromRGB(192, 192, 192)).build();
			pants = new LeatherItemBuilder(Material.LEATHER_LEGGINGS).setColor(Color.fromRGB(192, 192, 192)).build();
			boots = new LeatherItemBuilder(Material.LEATHER_BOOTS).setColor(Color.fromRGB(192, 192, 192)).build();
		}

		else if (rank == 3) {
			chest = new LeatherItemBuilder(Material.LEATHER_CHESTPLATE).setColor(Color.fromRGB(205, 127, 50)).build();
			pants = new LeatherItemBuilder(Material.LEATHER_LEGGINGS).setColor(Color.fromRGB(205, 127, 50)).build();
			boots = new LeatherItemBuilder(Material.LEATHER_BOOTS).setColor(Color.fromRGB(205, 127, 50)).build();
		}

		else {
			chest = new LeatherItemBuilder(Material.LEATHER_CHESTPLATE).setColor(Color.WHITE).build();
			pants = new LeatherItemBuilder(Material.LEATHER_LEGGINGS).setColor(Color.WHITE).build();
			boots = new LeatherItemBuilder(Material.LEATHER_BOOTS).setColor(Color.WHITE).build();
		}

		as.setHelmet(head);
		as.setChestplate(chest);
		as.setLeggings(pants);
		as.setBoots(boots);

		as.setBasePlate(false);
		as.setArms(true);
		as.setCustomName("§7" + Integer.toString(rank) + ".");
		as.setVisible(true);
		as.setGravity(false);
		as.setGlowing(true);
		as.setCustomNameVisible(true);
		
		holo.setBasePlate(false);
		holo.setArms(false);
		holo.setCustomName("§7"+name);
		holo.setVisible(false);
		holo.setGravity(false);
		holo.setCustomNameVisible(true);

		getRegister().getLocationsFile().addRankingArmorStand(l, rank);
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
			if (e.isCustomNameVisible() && ChatColor.stripColor(e.getCustomName()).equals(name)) {
				getRegister().getLocationsFile().removeArmorStand(name);
				e.remove();
			}
		}
	}
	
	public void removeRankingArmorStand(Location l, int rank) {
		String name = ChatColor.stripColor(getRegister().getStatsUtil().getPlayerByRank(rank).getName());
		for (Entity e : l.getWorld().getEntitiesByClass(ArmorStand.class)) {
			if (e.isCustomNameVisible() && (ChatColor.stripColor(e.getCustomName()).equals(name) || ChatColor.stripColor(e.getCustomName()).equals(rank+"."))) {
				getRegister().getLocationsFile().removeRankingArmorstand(rank);
				e.remove();
			}
		}
	}
}
