package de.alphahelix.uhc.util;

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
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class NPCUtil extends Util {

    public NPCUtil(UHC uhc) {
        super(uhc);
    }

    public void prepareNPC(Location loc, OfflinePlayer skin, Player toSend) {
        try {
            Class<?> nmsServerClass = ReflectionUtil.getNmsClass("MinecraftServer");
            Class<?> nmsWorldClass = ReflectionUtil.getNmsClass("World");
            Class<?> nmsWorldServerClass = ReflectionUtil.getNmsClass("WorldServer");
            Class<?> nmsPlayerInteractManagerClass = ReflectionUtil.getNmsClass("PlayerInteractManager");

            Field id = ReflectionUtil.getNmsClass("Entity").getDeclaredField("id");
            Field name = ReflectionUtil.getNmsClass("EntityPlayer").getField("listName");
            Field dName = ReflectionUtil.getNmsClass("EntityPlayer").getField("displayName");
            Field gPName = GameProfile.class.getDeclaredField("name");

            id.setAccessible(true);
            name.setAccessible(true);
            dName.setAccessible(true);
            gPName.setAccessible(true);

            Object nmsServer = Bukkit.getServer().getClass().getMethod("getServer").invoke(Bukkit.getServer());
            Object nmsWorld = loc.getWorld().getClass().getMethod("getHandle").invoke(loc.getWorld());
            Object nmsPIM = nmsPlayerInteractManagerClass.getConstructor(nmsWorldClass).newInstance(nmsWorld);
            Object npc = ReflectionUtil.getNmsClass("EntityPlayer")
                    .getConstructor(nmsServerClass, nmsWorldServerClass, GameProfile.class,
                            nmsPlayerInteractManagerClass)
                    .newInstance(nmsServer, nmsWorld,
                            new GameProfile(UUIDFetcher.getUUID(skin.getName()), skin.getName()), nmsPIM);

            ReflectionUtil.getNmsClass("Entity")
                    .getMethod("setLocation", double.class, double.class, double.class, float.class, float.class)
                    .invoke(npc, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

            GameProfile gp = new GameProfile(UUIDFetcher.getUUID(skin.getName()), skin.getName());

            name.set(npc, ReflectionUtil.serializeString(getRegister().getStatsFile().getColorString("StatsNPC")));
            dName.set(npc, getRegister().getStatsFile().getColorString("StatsNPC"));
            gPName.set(gp, getRegister().getStatsFile().getColorString("StatsNPC"));

            Object pPOPlayerInfo = PacketUtil.createPlayerInfoPacket(REnumPlayerInfoAction.ADD_PLAYER.getPlayerInfoAction(),
                    gp, 0, ReflectionUtil.getEnumGamemode(skin),
                    getRegister().getStatsFile().getColorString("StatsNPC"));

            Object pPOPPlayerInfoDestory = PacketUtil.createPlayerInfoPacket(REnumPlayerInfoAction.REMOVE_PLAYER.getPlayerInfoAction(),
                    gp, 0, ReflectionUtil.getEnumGamemode(skin),
                    getRegister().getStatsFile().getColorString("StatsNPC"));

            Object pPONamedEntitySpawn = ReflectionUtil.getNmsClass("PacketPlayOutNamedEntitySpawn")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityHuman")).newInstance(npc);

            Object pPOEntityHeadRotation = ReflectionUtil.getNmsClass("PacketPlayOutEntityHeadRotation")
                    .getConstructor(ReflectionUtil.getNmsClass("Entity"), byte.class)
                    .newInstance(npc, toAngle(loc.getYaw()));

            Object pPOEntityLook = ReflectionUtil.getNmsClass("PacketPlayOutEntity$PacketPlayOutEntityLook")
                    .getConstructor(int.class, byte.class, byte.class, boolean.class)
                    .newInstance(id.get(npc), toAngle(loc.getYaw()), toAngle(loc.getPitch()), true);

            Field yaw = ReflectionUtil.getNmsClass("Entity").getField("yaw");
            Field pitch = ReflectionUtil.getNmsClass("Entity").getField("pitch");
            Field lYaw = ReflectionUtil.getNmsClass("Entity").getField("lastYaw");
            Field lPitch = ReflectionUtil.getNmsClass("Entity").getField("lastPitch");
            Field aP = ReflectionUtil.getNmsClass("EntityLiving").getField("aP");
            Field aQ = ReflectionUtil.getNmsClass("EntityLiving").getField("aQ");
            Field aO = ReflectionUtil.getNmsClass("EntityLiving").getField("aO");

            yaw.setAccessible(true);
            pitch.setAccessible(true);
            lYaw.setAccessible(true);
            lPitch.setAccessible(true);
            aP.setAccessible(true);
            aQ.setAccessible(true);
            aO.setAccessible(true);

            yaw.set(npc, loc.getYaw());
            pitch.set(npc, loc.getPitch());
            aP.set(npc, (loc.getYaw() - 90));
            aQ.set(npc, loc.getYaw());
            aO.set(npc, loc.getYaw());

            ReflectionUtil.sendPacket(toSend, pPOPlayerInfo);
            ReflectionUtil.sendPacket(toSend, pPONamedEntitySpawn);
            ReflectionUtil.sendPacket(toSend, pPOEntityHeadRotation);
            ReflectionUtil.sendPacket(toSend, pPOEntityLook);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ReflectionUtil.sendPacket(toSend, pPOPPlayerInfoDestory);
                }
            }.runTaskLater(getUhc(), 20);

            createStatsArmorStands(toSend);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private byte toAngle(float v) {
        return (byte) ((int) (v * 256.0F / 360.0F));
    }

    private void createStatsArmorStands(final Player p) {
        try {
            Class<?>[] param = {double.class, double.class, double.class, float.class, float.class};
            Location loc = getRegister().getLocationsFile().getStatsNPCLocation();
            Object k = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object d = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object c = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object po = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object r = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));

            k.getClass().getMethod("setLocation", param).invoke(k, loc.getX(), loc.getY() + 0.9, loc.getZ(), 0F, 0F);
            k.getClass().getMethod("setInvisible", boolean.class).invoke(k, true);
            k.getClass().getMethod("setCustomName", String.class).invoke(k,
                    getRegister().getStatsFile().getColorString("Kills").replace("[kills]",
                            Integer.toString(getRegister().getStatsUtil().getKills(p))));
            k.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(k, true);

            d.getClass().getMethod("setLocation", param).invoke(d, loc.getX(), loc.getY() + 0.7, loc.getZ(), 0F, 0F);
            d.getClass().getMethod("setInvisible", boolean.class).invoke(d, true);
            d.getClass().getMethod("setCustomName", String.class).invoke(d,
                    getRegister().getStatsFile().getColorString("Deaths").replace("[deaths]",
                            Integer.toString(getRegister().getStatsUtil().getDeaths(p))));
            c.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(d, true);

            c.getClass().getMethod("setLocation", param).invoke(c, loc.getX(), loc.getY() + 0.5, loc.getZ(), 0F, 0F);
            c.getClass().getMethod("setInvisible", boolean.class).invoke(c, true);
            c.getClass().getMethod("setCustomName", String.class).invoke(c,
                    getRegister().getStatsFile().getColorString("Coins").replace("[coins]",
                            Integer.toString(getRegister().getStatsUtil().getCoins(p))));
            c.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(c, true);

            po.getClass().getMethod("setLocation", param).invoke(po, loc.getX(), loc.getY() + 0.3, loc.getZ(), 0F, 0F);
            po.getClass().getMethod("setInvisible", boolean.class).invoke(po, true);
            po.getClass().getMethod("setCustomName", String.class).invoke(po,
                    getRegister().getStatsFile().getColorString("Points").replace("[points]",
                            Integer.toString(getRegister().getStatsUtil().getPoints(p))));
            po.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(po, true);

            r.getClass().getMethod("setLocation", param).invoke(r, loc.getX(), loc.getY() + 0.1, loc.getZ(), 0F, 0F);
            r.getClass().getMethod("setInvisible", boolean.class).invoke(r, true);
            r.getClass().getMethod("setCustomName", String.class).invoke(r,
                    getRegister().getStatsFile().getColorString("Rank").replace("[rank]",
                            Integer.toString(getRegister().getStatsUtil().getRank(p))));
            r.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(r, true);

            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(k));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(d));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(c));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(po));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(r));
        } catch (Exception e) {
            e.printStackTrace();
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
        } else if (rank == 2) {
            chest = new LeatherItemBuilder(Material.LEATHER_CHESTPLATE).setColor(Color.fromRGB(192, 192, 192)).build();
            pants = new LeatherItemBuilder(Material.LEATHER_LEGGINGS).setColor(Color.fromRGB(192, 192, 192)).build();
            boots = new LeatherItemBuilder(Material.LEATHER_BOOTS).setColor(Color.fromRGB(192, 192, 192)).build();
        } else if (rank == 3) {
            chest = new LeatherItemBuilder(Material.LEATHER_CHESTPLATE).setColor(Color.fromRGB(205, 127, 50)).build();
            pants = new LeatherItemBuilder(Material.LEATHER_LEGGINGS).setColor(Color.fromRGB(205, 127, 50)).build();
            boots = new LeatherItemBuilder(Material.LEATHER_BOOTS).setColor(Color.fromRGB(205, 127, 50)).build();
        } else {
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
        holo.setCustomName("§7" + name);
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
            if (e.isCustomNameVisible() && (ChatColor.stripColor(e.getCustomName()).equals(name)
                    || ChatColor.stripColor(e.getCustomName()).equals(rank + "."))) {
                getRegister().getLocationsFile().removeRankingArmorstand(rank);
                e.remove();
            }
        }
    }
}
