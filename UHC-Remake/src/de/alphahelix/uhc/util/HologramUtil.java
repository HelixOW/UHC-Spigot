package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class HologramUtil extends Util {

    public HologramUtil(UHC uhc) {
        super(uhc);
    }

    public void createHologram(Player p, int id, double remove) {
        Location loc = getRegister().getHologramFile().getLocationByID(id).subtract(0, remove, 0);

        String name = getRegister().getHologramFile().getHologramNameByID(id);

        Class<?> cWorld = ReflectionUtil.getNmsClass("World");
        Class<?>[] param = {double.class, double.class, double.class, float.class, float.class};

        Constructor<?> holoConstructor = null;
        try {
            holoConstructor = ReflectionUtil.getNmsClass("EntityArmorStand").getConstructor(cWorld);
        } catch (NoSuchMethodException | SecurityException e2) {
            e2.printStackTrace();
        }
        Class<?> cholo = ReflectionUtil.getNmsClass("EntityArmorStand");
        try {

            Object hc = holoConstructor != null
                    ? holoConstructor.newInstance(ReflectionUtil.getWorldServer(loc.getWorld())) : null;

            name = name.replace("&", "§");
            name = name.replace("[player]", p.getDisplayName());
            name = name.replace("[kills]", Long.toString(getRegister().getStatsUtil().getKills(p)));
            name = name.replace("[deaths]", Long.toString(getRegister().getStatsUtil().getDeaths(p)));
            name = name.replace("[coins]", Long.toString(getRegister().getStatsUtil().getCoins(p)));
            name = name.replace("[rank]", Long.toString(getRegister().getStatsUtil().getRank(p)));

            if (getUhc().isScenarios()) {
                name = name.replace("[scenario]", getRegister().getScenarioFile().getCustomScenarioName(Scenarios.getScenario()));
            } else {
                name = name.replace("[scenario]", getRegister().getMessageFile().getColorString("Kit Mode"));
            }

            if (name.contains("show [kits]")) {
                name = " ";
                Location lastLocation = p.getLocation();
                for (String kitName : getRegister().getStatsUtil().getKitsAsList(p)) {
                    lastLocation = lastLocation.subtract(0, 0.2, 0);
                    getRegister().getHologramFile().addHologram("[k]" + kitName, lastLocation, 0);
                }
            }

            cholo.getMethod("setLocation", param).invoke(hc, loc.getX(), loc.getY(), loc.getZ(), 0F, 0F);
            cholo.getMethod("setInvisible", boolean.class).invoke(hc, true);
            cholo.getMethod("setCustomName", String.class).invoke(hc, name);
            cholo.getMethod("setCustomNameVisible", boolean.class).invoke(hc, true);

            Constructor<?> conpacket = ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityLiving"));
            Object packet = conpacket.newInstance(hc);

            ReflectionUtil.sendPacket(p, packet);

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | InstantiationException e1) {
            e1.printStackTrace();
        }
    }

    public void showHologram(Player p) {
        for (int id = 0; id < getRegister().getHologramFile().getHologramcount(); id++) {

            Location loc = getRegister().getHologramFile().getLocationByID(id);

            String name = getRegister().getHologramFile().getHologramNameByID(id);

            Class<?> cWorld = ReflectionUtil.getNmsClass("World");
            Class<?>[] param = {double.class, double.class, double.class, float.class, float.class};

            Constructor<?> holoConstructor = null;
            try {
                holoConstructor = ReflectionUtil.getNmsClass("EntityArmorStand").getConstructor(cWorld);
            } catch (NoSuchMethodException | SecurityException e2) {
                e2.printStackTrace();
            }
            Class<?> cholo = ReflectionUtil.getNmsClass("EntityArmorStand");
            try {

                Object hc = holoConstructor != null
                        ? holoConstructor.newInstance(ReflectionUtil.getWorldServer(loc.getWorld())) : null;

                name = name.replace("&", "§");
                name = name.replace("[player]", p.getDisplayName());
                name = name.replace("[kills]", Long.toString(getRegister().getStatsUtil().getKills(p)));
                name = name.replace("[deaths]", Long.toString(getRegister().getStatsUtil().getDeaths(p)));
                name = name.replace("[coins]", Long.toString(getRegister().getStatsUtil().getCoins(p)));
                name = name.replace("[rank]", Long.toString(getRegister().getStatsUtil().getRank(p)));
                name = name.replace("show [kits]", "");

                if (getUhc().isScenarios()) {
                    name = name.replace("[scenario]", getRegister().getScenarioFile().getCustomScenarioName(Scenarios.getScenario()));
                } else {
                    name = name.replace("[scenario]", getRegister().getMessageFile().getColorString("Kit Mode"));
                }

                if (name.startsWith("[k]")) {
                    if (!getRegister().getStatsUtil().hasKit(getRegister().getKitsFile().getKit(name.replace("[k]", "").replace("§", "&")), p)) {
                        continue;
                    } else
                        name = name.replace("[k]", "§7- ").replace("_", " ");
                }

                cholo.getMethod("setLocation", param).invoke(hc, loc.getX(), loc.getY(), loc.getZ(), 0F, 0F);
                cholo.getMethod("setInvisible", boolean.class).invoke(hc, true);
                cholo.getMethod("setCustomName", String.class).invoke(hc, name);
                cholo.getMethod("setCustomNameVisible", boolean.class).invoke(hc, true);

                Constructor<?> conpacket = ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving")
                        .getConstructor(ReflectionUtil.getNmsClass("EntityLiving"));
                Object packet = conpacket.newInstance(hc);

                ReflectionUtil.sendPacket(p, packet);

            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | InstantiationException e1) {
                e1.printStackTrace();
            }
        }
    }

}
