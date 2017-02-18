/*
 * Copyright (C) <2017>  <AlphaHelixDev>
 *
 *       This program is free software: you can redistribute it under the
 *       terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.alphahelix.alphalibary.reflection;

import com.mojang.authlib.GameProfile;
import de.alphahelix.alphalibary.utils.MinecraftVersion;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PacketUtil {

    public static Object createPlayerInfoPacket(Object enumPlayerInfoAction, GameProfile gameProfile, int ping, Object enumGamemode, String name) {

        Class<?> cIChatBaseComponent = ReflectionUtil.getNmsClass("IChatBaseComponent");
        Class<?> cPacketPlayOutPlayerInfo = ReflectionUtil.getNmsClass("PacketPlayOutPlayerInfo");
        Class<?> cPlayerInfoData = ReflectionUtil.getNmsClass("PacketPlayOutPlayerInfo$PlayerInfoData");

        Class<?> cEnumGamemode;
        if(MinecraftVersion.getServer() == MinecraftVersion.NINE || MinecraftVersion.getServer() == MinecraftVersion.EIGHT) {
            cEnumGamemode = ReflectionUtil.getNmsClass("WorldSettings$EnumGamemode");
        } else {
            cEnumGamemode = ReflectionUtil.getNmsClass("EnumGamemode");
        }

        try {
            Object pPacketPlayOutInfo = cPacketPlayOutPlayerInfo.getConstructor().newInstance();

            Field fa = pPacketPlayOutInfo.getClass().getDeclaredField("a");
            fa.setAccessible(true);
            fa.set(pPacketPlayOutInfo, enumPlayerInfoAction);

            Object oPlayerInfoData = cPlayerInfoData.getConstructor(cPacketPlayOutPlayerInfo, GameProfile.class, int.class, cEnumGamemode, cIChatBaseComponent)
                    .newInstance(pPacketPlayOutInfo, gameProfile, ping, enumGamemode, ReflectionUtil.serializeString(name));

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
}
