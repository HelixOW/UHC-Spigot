package de.alphahelix.uhc.util;

import com.mojang.authlib.GameProfile;
import de.alphahelix.alphalibary.UUID.UUIDFetcher;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.item.LeatherItemBuilder;
import de.alphahelix.alphalibary.item.data.SkullData;
import de.alphahelix.alphalibary.nms.REnumPlayerInfoAction;
import de.alphahelix.alphalibary.reflection.PacketUtil;
import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.instances.Util;
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
            Object games = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object kills = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object deaths = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object killrate = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object wins = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object coins = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object points = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));
            Object rank = ReflectionUtil.getNmsClass("EntityArmorStand")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));

            games.getClass().getMethod("setLocation", param).invoke(games, loc.getX(), loc.getY() + 1.5, loc.getZ(), 0F, 0F);
            games.getClass().getMethod("setInvisible", boolean.class).invoke(games, true);
            games.getClass().getMethod("setCustomName", String.class).invoke(games,
                    getRegister().getStatsFile().getColorString("Games").replace("[games]",
                            Long.toString(getRegister().getStatsUtil().getGames(p))));
            games.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(games, true);

            kills.getClass().getMethod("setLocation", param).invoke(kills, loc.getX(), loc.getY() + 1.3, loc.getZ(), 0F, 0F);
            kills.getClass().getMethod("setInvisible", boolean.class).invoke(kills, true);
            kills.getClass().getMethod("setCustomName", String.class).invoke(kills,
                    getRegister().getStatsFile().getColorString("Kills").replace("[kills]",
                            Long.toString(getRegister().getStatsUtil().getKills(p))));
            kills.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(kills, true);

            deaths.getClass().getMethod("setLocation", param).invoke(deaths, loc.getX(), loc.getY() + 1.1, loc.getZ(), 0F, 0F);
            deaths.getClass().getMethod("setInvisible", boolean.class).invoke(deaths, true);
            deaths.getClass().getMethod("setCustomName", String.class).invoke(deaths,
                    getRegister().getStatsFile().getColorString("Deaths").replace("[deaths]",
                            Long.toString(getRegister().getStatsUtil().getDeaths(p))));
            deaths.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(kills, true);

            killrate.getClass().getMethod("setLocation", param).invoke(killrate, loc.getX(), loc.getY() + 0.9, loc.getZ(), 0F, 0F);
            killrate.getClass().getMethod("setInvisible", boolean.class).invoke(killrate, true);
            killrate.getClass().getMethod("setCustomName", String.class).invoke(killrate,
                    getRegister().getStatsFile().getColorString("KillDeathRate").replace("[kdr]",
                            Double.toString(getRegister().getStatsUtil().getKillDeathRate(p))));
            killrate.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(killrate, true);

            wins.getClass().getMethod("setLocation", param).invoke(wins, loc.getX(), loc.getY() + 0.7, loc.getZ(), 0F, 0F);
            wins.getClass().getMethod("setInvisible", boolean.class).invoke(wins, true);
            wins.getClass().getMethod("setCustomName", String.class).invoke(wins,
                    getRegister().getStatsFile().getColorString("Wins").replace("[wins]",
                            Long.toString(getRegister().getStatsUtil().getWins(p))));
            wins.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(wins, true);

            coins.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(deaths, true);
            coins.getClass().getMethod("setLocation", param).invoke(coins, loc.getX(), loc.getY() + 0.5, loc.getZ(), 0F, 0F);
            coins.getClass().getMethod("setInvisible", boolean.class).invoke(coins, true);
            coins.getClass().getMethod("setCustomName", String.class).invoke(coins,
                    getRegister().getStatsFile().getColorString("Coins").replace("[coins]",
                            Long.toString(getRegister().getStatsUtil().getCoins(p))));
            coins.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(coins, true);

            points.getClass().getMethod("setLocation", param).invoke(points, loc.getX(), loc.getY() + 0.3, loc.getZ(), 0F, 0F);
            points.getClass().getMethod("setInvisible", boolean.class).invoke(points, true);
            points.getClass().getMethod("setCustomName", String.class).invoke(points,
                    getRegister().getStatsFile().getColorString("Points").replace("[points]",
                            Long.toString(getRegister().getStatsUtil().getPoints(p))));
            points.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(points, true);

            rank.getClass().getMethod("setLocation", param).invoke(rank, loc.getX(), loc.getY() + 0.1, loc.getZ(), 0F, 0F);
            rank.getClass().getMethod("setInvisible", boolean.class).invoke(rank, true);
            rank.getClass().getMethod("setCustomName", String.class).invoke(rank,
                    getRegister().getStatsFile().getColorString("Rank").replace("[rank]",
                            Long.toString(getRegister().getStatsUtil().getRank(p))));
            rank.getClass().getMethod("setCustomNameVisible", boolean.class).invoke(rank, true);

            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(games));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(kills));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(deaths));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(killrate));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(wins));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(coins));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(points));
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving")).newInstance(rank));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawnRankingArmorStand(Location l, int rank) {
        ArmorStand as = l.getWorld().spawn(l, ArmorStand.class);
        ArmorStand holo = l.getWorld().spawn(l.subtract(0, 0.2, 0), ArmorStand.class);

        String name = getRegister().getStatsUtil().getPlayerByRank(rank).getName();

        ItemStack head = new ItemBuilder
                (Material.SKULL_ITEM)
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
