package de.alphahelix.alphalibary.reflection;

import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PacketUtil {

    public static void equipEntity(final Entity p, ItemStack toEquip, EquipmentSlot slot, final Player[] players) {

        try {
            int id = de.alphahelix.alphalibary.reflection.ReflectionUtil.getID(p);

            final Object packet = de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("PacketPlayOutEntityEquipment").getConstructor(new Class<?>[]{
                    int.class, int.class, de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("ItemStack")
            }).newInstance(id, slot.getID(), de.alphahelix.alphalibary.reflection.ReflectionUtil.getObjectNMSItemStack(toEquip));

            new BukkitRunnable() {

                @Override
                public void run() {
                    for (Player all : players) {

                        if (all == p) {
                            continue;
                        }

                        de.alphahelix.alphalibary.reflection.ReflectionUtil.sendPacket(all, packet);
                    }
                }
            }.run();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public static Object createPlayerInfoPacket(Object enumPlayerInfoAction, Object gameProfile, int ping, Object enumGamemode, String name) {

        Class<?> cIChatBaseComponent = de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("IChatBaseComponent");
        Class<?> cPacketPlayOutPlayerInfo = de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("PacketPlayOutPlayerInfo");
        Class<?> cPlayerInfoData = de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("PacketPlayOutPlayerInfo$PlayerInfoData");
        Class<?> cEnumGamemode = de.alphahelix.alphalibary.reflection.ReflectionUtil.getNmsClass("WorldSettings$EnumGamemode");

        try {

            Object pPacketPlayOutInfo = cPacketPlayOutPlayerInfo.getConstructor().newInstance();

            Field fa = pPacketPlayOutInfo.getClass().getDeclaredField("a");
            fa.setAccessible(true);
            fa.set(pPacketPlayOutInfo, enumPlayerInfoAction);

            Object oPlayerInfoData = cPlayerInfoData.getConstructor(cPacketPlayOutPlayerInfo, GameProfile.class, int.class, cEnumGamemode, cIChatBaseComponent)
                    .newInstance(pPacketPlayOutInfo, gameProfile, ping, enumGamemode, de.alphahelix.alphalibary.reflection.ReflectionUtil.serializeString(name));

            Field b = pPacketPlayOutInfo.getClass().getDeclaredField("b");
            b.setAccessible(true);
            ArrayList<Object> array = (ArrayList<Object>) b.get(pPacketPlayOutInfo);

            array.add(oPlayerInfoData);

            b.set(pPacketPlayOutInfo, array);

            return pPacketPlayOutInfo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public enum EquipmentSlot {

        INHAND(0),
        BOOTS(1),
        LEGGINGS(2),
        CHESTPLATE(3),
        HELMET(4);

        private final int id;

        EquipmentSlot(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

}
