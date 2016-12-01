package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;
import org.bukkit.Location;

public class HologramFile extends EasyFile {

    public HologramFile(UHC uhc) {
        super("holograms.uhc", uhc);
    }

    public void addHologram(String name, Location loc, double minus) {

        int id = getHologramcount();

        setDefault(id + ".name", name);
        setLocation(id + ".location", loc, true);

    }

    public String getHologramNameByID(int id) {
        return getColorString(id + ".name");
    }

    public String getHologramNameByLocation(Location loc) {
        for (String count : getKeys(false)) {
            if (getLocation(count + ".location", true).build().getBlockX() == loc.getBlockX()
                    && getLocation(count + ".location", true).build().getBlockX() == loc.getBlockZ()) {
                return getColorString(count + ".name");
            }
        }
        return "";
    }

    public int getHologramcount() {
        return getKeys(false).size();
    }

    public Location getLocationByID(int id) {
        return getLocation(id + ".location", true).build();
    }

    public Location getLocationByName(String name) {
        for (String count : getKeys(false)) {
            if (getColorString(count + ".name").equals(name))
                return getLocation(count + ".location", true).build();
        }
        return null;
    }

    @Override
    public void addValues() {

    }
}
