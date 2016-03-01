package net.minetopix.library.main.nms;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.minetopix.library.main.utils.PacketUtil;
import net.minetopix.library.main.utils.PacketUtil.EquipmentSlot;
import net.minetopix.library.main.utils.ReflectionUtil;

public class DisguiseAPI {

	private static HashMap<UUID, DisguiseData> disguise = new HashMap<>();

	public static void disguiseEntity(final LivingEntity p, DisguiseData data, Player... receiver) {

		try {

			final PacketModifier modifier = new PacketModifier(ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving"),
					ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving").newInstance());

			modifier.set("a", ReflectionUtil.getID(p));

			modifier.set("b", (byte) data.getType().getId());

			modifier.set("c", ReflectionUtil.floor(p.getLocation().getX() * 32.0D));
			modifier.set("d", ReflectionUtil.floor(p.getLocation().getY() * 32.0D));
			modifier.set("e", ReflectionUtil.floor(p.getLocation().getZ() * 32.0D));

			modifier.set("i", (byte) (int) (p.getLocation().getYaw() * 256.0F / 360.0F));
			modifier.set("j", (byte) (int) (p.getLocation().getPitch() * 256.0F / 360.0F));
			modifier.set("k",
					(byte) (int) ((float) ReflectionUtil
							.getDeclaredField("aK", ReflectionUtil.getNmsClass("EntityLiving"))
							.get(ReflectionUtil.getEntity(p), true) * 256.0F / 360.0F));

			modifier.set("l", data.getDataWatcher());

			final Object packetPlayOutEntityDestroy = ReflectionUtil.getNmsClass("PacketPlayOutEntityDestroy")
					.getConstructor(int[].class).newInstance(new int[] { ReflectionUtil.getID(p) });

			disguise.put(p.getUniqueId(), data);

			new BukkitRunnable() {

				@Override
				public void run() {
					for (Player receiver : Bukkit.getOnlinePlayers()) {

						if (receiver == p)
							continue;
						ReflectionUtil.sendPacket(receiver, packetPlayOutEntityDestroy);
					}

					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {

					}

					for (Player receiver : Bukkit.getOnlinePlayers()) {

						if (receiver == p)
							continue;
						ReflectionUtil.sendPacket(receiver, modifier.getPacket());
					}
				}
			}.run();

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}
	
	public static void undisguisePlayer(final LivingEntity p) {

		try {

			final Object packetPlayOutEntityDestroy = ReflectionUtil.getNmsClass("PacketPlayOutEntityDestroy")
					.getConstructor(int[].class).newInstance(new int[] { ReflectionUtil.getID(p) });
			
			final Object packetPlayOutNamedEntitySpawn = ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
					.getConstructor(ReflectionUtil.getNmsClass("EntityLiving"))
					.newInstance(ReflectionUtil.getEntity(p));

			disguise.remove(p.getUniqueId());
			
			new BukkitRunnable() {

				@Override
				public void run() {
					for (Player receiver : Bukkit.getOnlinePlayers()) {

						if (receiver == p)
							continue;
						ReflectionUtil.sendPacket(receiver, packetPlayOutEntityDestroy);
					}

					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {

					}

					for (Player receiver : Bukkit.getOnlinePlayers()) {

						if (receiver == p)
							continue;
						ReflectionUtil.sendPacket(receiver, packetPlayOutNamedEntitySpawn);
					}
				}
			}.run();

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	public static DisguiseData getDisguise(Player p) {
		return disguise.get(p.getUniqueId());
	}

	public static void sendAllDisguisesToPlayer(Player p) {

		for (UUID uuid : disguise.keySet()) {

			disguiseEntity(Bukkit.getPlayer(uuid), getDisguise(p), p);

		}

	}

	public static enum DisguiseType {

		CREEPER(ReflectionUtil.getNmsClass("EntityCreeper") , ReflectionUtil.getCraftBukkitClass("entity.CraftCreeper")), 
		ZOMBIE(ReflectionUtil.getNmsClass("EntityZombie") , ReflectionUtil.getCraftBukkitClass("entity.CraftZombie")), 
		SKELETON(ReflectionUtil.getNmsClass("EntitySkeleton") , ReflectionUtil.getCraftBukkitClass("entity.CraftSkeleton")),
		PIG (ReflectionUtil.getNmsClass("EntityPig") , ReflectionUtil.getCraftBukkitClass("entity.CraftPig")),
		WITHER_BOSS(ReflectionUtil.getNmsClass("EntityWither") , ReflectionUtil.getCraftBukkitClass("entity.CraftWither")),
		WITCH (ReflectionUtil.getNmsClass("EntityWitch") , ReflectionUtil.getCraftBukkitClass("entity.CraftWitch")),
		CHICKEN (ReflectionUtil.getNmsClass("EntityChicken") , ReflectionUtil.getCraftBukkitClass("entity.CraftChicken"));

		private Class<?> clazz;
		private Class<?> craftClazz;

		private DisguiseType(Class<?> exectuer , Class<?> craftClass) {
			this.clazz = exectuer;
			this.craftClazz = craftClass;
		}

		public int getId() {
			try {

				Field fd = ReflectionUtil.getNmsClass("EntityTypes").getDeclaredField("f");

				fd.setAccessible(true);

				Map<?, ?> array = (Map<?, ?>) fd.get(null);

				return (Integer) array.get(clazz);

			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}

		public Class<?> getExecuter() {
			return clazz;
		}
		
		public Class<?> getAPIClass() {
			return craftClazz;
		}

		public Object createDataWatcher(World w) {
			try {

				Class<?> entityTypes = ReflectionUtil.getNmsClass("EntityTypes");
				Class<?> world = ReflectionUtil.getNmsClass("World");
				Class<?> entity = ReflectionUtil.getNmsClass("Entity");

				Field fd = entityTypes.getDeclaredField("d");

				fd.setAccessible(true);

				Map<?, ?> array = (Map<?, ?>) fd.get(null);

				String name = (String) array.get(clazz);

				Object finalEntity = entityTypes.getMethod("createEntityByName", String.class, world).invoke(null, name,
						ReflectionUtil.getWorldServer(w));

				return entity.getMethod("getDataWatcher").invoke(finalEntity);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}

	}

	public static class DisguiseData {

		private Object entity = null;
		private Object dataWatcher = null;
		private Object craftEntity = null;
		private int id;
		private LivingEntity p;
		private DisguiseType type;

		public DisguiseData(DisguiseType type, LivingEntity p) {
			this.type = type;
			this.id = ReflectionUtil.getID(p);
			this.p = p;
			
			createEntity(p.getWorld());
		}

		private void createEntity(World w) {
			try {

				Class<?> entityTypes = ReflectionUtil.getNmsClass("EntityTypes");
				Class<?> world = ReflectionUtil.getNmsClass("World");
				Class<?> entity = ReflectionUtil.getNmsClass("Entity");

				Field fd = entityTypes.getDeclaredField("d");

				fd.setAccessible(true);

				Map<?, ?> array = (Map<?, ?>) fd.get(null);

				String name = (String) array.get(type.getExecuter());

				this.entity = entityTypes.getMethod("createEntityByName", String.class, world).invoke(null, name,
						ReflectionUtil.getWorldServer(w));
								
				this.dataWatcher = entity.getMethod("getDataWatcher").invoke(this.entity);
						
				this.craftEntity = type.getAPIClass()
						.getConstructor(ReflectionUtil.getCraftBukkitClass("CraftServer") , type.getExecuter())
						.newInstance(Bukkit.getServer() , this.entity);

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		public DisguiseType getType() {
			return type;
		}

		public Object getDataWatcher() {
			return dataWatcher;
		}

		@SuppressWarnings("unchecked")
		public <T extends LivingEntity> T getEntity(Class<T> type) {
			try {
				return (T) craftEntity;
			} catch (Exception e) {
				return null;
			}
		}

		public void update() {
			try {
				
				/*
				 * Update DataWatcher
				 */
				
				final Object packetPlayOutEntityMetadata = ReflectionUtil.getNmsClass("PacketPlayOutEntityMetadata")
						.getConstructor(int.class , ReflectionUtil.getNmsClass("DataWatcher") , boolean.class)
						.newInstance(this.id , dataWatcher , true);
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						for(Player online : Bukkit.getOnlinePlayers()) {
							if(online == p) continue;
							ReflectionUtil.sendPacket(online, packetPlayOutEntityMetadata);
						}
					}
				}.run();
				
				LivingEntity entity = this.getEntity(LivingEntity.class);
				
				/*
				 * Update equipment
				 */
				PacketUtil.equipEntity(p, entity.getEquipment().getHelmet(), EquipmentSlot.HELMET, Bukkit.getOnlinePlayers().toArray(new Player[0]));
				PacketUtil.equipEntity(p, entity.getEquipment().getChestplate(), EquipmentSlot.CHESTPLATE, Bukkit.getOnlinePlayers().toArray(new Player[0]));
				PacketUtil.equipEntity(p, entity.getEquipment().getLeggings(), EquipmentSlot.LEGGINGS, Bukkit.getOnlinePlayers().toArray(new Player[0]));
				PacketUtil.equipEntity(p, entity.getEquipment().getBoots(), EquipmentSlot.BOOTS, Bukkit.getOnlinePlayers().toArray(new Player[0]));
				PacketUtil.equipEntity(p, entity.getEquipment().getItemInMainHand(), EquipmentSlot.INHAND, Bukkit.getOnlinePlayers().toArray(new Player[0]));
			
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
		}
		
	}

}
