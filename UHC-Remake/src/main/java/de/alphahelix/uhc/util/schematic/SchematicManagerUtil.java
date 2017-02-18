package de.alphahelix.uhc.util.schematic;

import de.alphahelix.alphalibary.utils.Cuboid;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SchematicManagerUtil extends Util {

    private HashMap<String, ArrayList<String>> schematics = new HashMap<>();
    private HashMap<String, Location> loc1 = new HashMap<>(), loc2 = new HashMap<>();

    public SchematicManagerUtil(UHC uhc) {
        super(uhc);
    }

    public boolean load(String name, Player p) {
        if (!new File("plugins/UHC/schematics", name + ".uhcSchem").exists()) return false;
        ArrayList<String> list = getStringListFromFile(name);
        if (list != null && list.size() > 0) {
            schematics.put(p.getName(), list);
            return true;
        }
        return false;
    }

    public void load(String name) {
        if (!new File("plugins/UHC/schematics", name + ".uhcSchem").exists()) return;
        ArrayList<String> list = getStringListFromFile(name);
        if (list != null && list.size() > 0) {
            schematics.put("UHCSpigot", list);
        }
    }

    public ArrayList<String> getStringListFromFile(String filename) {
        ArrayList<String> list = new ArrayList<>();
        try {
            File f = new File("plugins/UHC/schematics", filename + ".uhcSchem");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null)
                list.add(line);

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean save(String filename, ArrayList<String> list) {

        File f = new File("plugins/UHC/schematics", filename + ".uhcSchem");
        if (f.exists())
            return false;

        try {
            f.createNewFile();

            BufferedWriter bw = new BufferedWriter(new FileWriter(f));

            for (String str : list) {
                bw.write(str);
                bw.write(System.lineSeparator());
            }
            bw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<String> convertToStringlist(ArrayList<Block> blocks, Location start) {
        ArrayList<String> list = new ArrayList<>();

        for (Block b : blocks) {
            list.add(convertToString(b, start));
        }
        return list;
    }

    public HashMap<String, ArrayList<String>> getSchematics() {
        return schematics;
    }

    public ArrayList<Block> getBlocks(Location l1, Location l2) {
        return (ArrayList<Block>) new Cuboid(l1, l2).getBlocks();
    }

    @SuppressWarnings("deprecation")
    public boolean paste(Player p) {
        if (schematics.containsKey(p.getName())) {
            Location loc = p.getTargetBlock((Set<Material>) null, 40).getLocation().add(0, 1, 0);
            for (String str : schematics.get(p.getName())) {
                String[] s = str.split(":");

                int x = Integer.parseInt(s[0]), y = Integer.parseInt(s[1]), z = Integer.parseInt(s[2]);
                Material m = Material.getMaterial(s[3]);
                byte data = Byte.parseByte(s[4]);

                loc.clone().add(x, y, z).getBlock().setType(m);
                loc.clone().add(x, y, z).getBlock().setData(data);
            }
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public void paste(Location loc) {
        if (schematics.containsKey("UHCSpigot")) {
            for (String str : schematics.get("UHCSpigot")) {
                String[] s = str.split(":");

                int x = Integer.parseInt(s[0]), y = Integer.parseInt(s[1]), z = Integer.parseInt(s[2]);
                Material m = Material.getMaterial(s[3]);
                byte data = Byte.parseByte(s[4]);

                loc.clone().add(0, 1, 0).add(x, y, z).getBlock().setType(m);
                loc.clone().add(0, 1, 0).add(x, y, z).getBlock().setData(data);
            }
        }
    }

    public boolean delete(String filename) {
        File f = new File("plugins/UHC/schematics", filename + ".uhcSchem");
        return f.exists() && f.delete();
    }

    @SuppressWarnings("deprecation")
    public String convertToString(Block b, Location start) {
        int diffX = b.getX() - start.getBlockX();
        int diffY = b.getY() - start.getBlockY();
        int diffZ = b.getZ() - start.getBlockZ();

        return diffX + ":" + diffY + ":" + diffZ + ":" + b.getType().name() + ":" + b.getData();
    }

    public void putFirstLocation(Player p, Location l) {
        loc1.put(p.getName(), l);
    }

    public void putSecondLocation(Player p, Location l) {
        loc2.put(p.getName(), l);
    }

    public Location getFirstLocation(Player p) {
        if (loc1.containsKey(p.getName())) return loc1.get(p.getName());
        return null;
    }

    public Location getSecondLocation(Player p) {
        if (loc2.containsKey(p.getName())) return loc2.get(p.getName());
        return null;
    }
}
