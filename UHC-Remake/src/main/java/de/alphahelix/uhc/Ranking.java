package de.alphahelix.uhc;

import de.alphahelix.alphalibary.UUID.UUIDFetcher;
import de.alphahelix.alphalibary.mysql.MySQLAPI;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.logging.Level;

public class Ranking extends Util {

    private HashMap<Long, String> rank;
    private Location firstPlace;
    private Location secondPlace;
    private Location thirdPlace;
    private String line1;
    private String line2;
    private String line3;
    private String line4;

    public Ranking(UHC uhc) {
        super(uhc);
        setRank(new HashMap<Long, String>());
    }

    public void update() {
        if (getUhc().isMySQLMode()) {
            if (MySQLAPI.isConnected()) {
                getLog().log(Level.INFO, "Now using MySQL backend to load ranks");

                if (firstPlace == null || secondPlace == null || thirdPlace == null)
                    return;

                try {
                    ResultSet rs;
                    rs = MySQLAPI.getMySQLConnection().createStatement()
                            .executeQuery("SELECT " + "UUID" + " FROM " + "UHC" + " ORDER BY " + "Points" + " asc");

                    ResultSet counts = MySQLAPI.getMySQLConnection().createStatement()
                            .executeQuery("SELECT COUNT(*) FROM UHC");

                    long in = 1;

                    while (counts.next()) {
                        in++;
                    }

                    while (rs.next()) {
                        in--;
                        rank.put(in, rs.getString("UUID"));
                    }

                    LinkedList<Location> locs = new LinkedList<>();

                    locs.add(firstPlace.add(0, 1, 0));
                    locs.add(secondPlace.add(0, 1, 0));
                    locs.add(thirdPlace.add(0, 1, 0));

                    for (int i = 0; i < locs.size(); i++) {
                        int id = i + 1;

                        if (!(locs.get(i).getBlock().getType().equals(Material.SKULL)))
                            locs.get(i).getBlock().setType(Material.SKULL);

                        Skull s = (Skull) locs.get(i).getBlock().getState();

                        s.setSkullType(SkullType.PLAYER);

                        if (!(rank.containsKey(id)))
                            continue;
                        String name = Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))).getName();

                        s.setOwner(name);
                        s.update();

                        Location sig = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY(),
                                locs.get(i).getZ());

                        if (sig.getBlock().getState() instanceof Sign) {
                            Sign sign = (Sign) sig.getBlock().getState();

                            String a = line1.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                                    .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                            .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
                            String b = line2.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                                    .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                            .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
                            String c = line3.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                                    .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                            .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
                            String d = line4.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                                    .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                            .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));

                            sign.setLine(0, a);
                            sign.setLine(1, b);
                            sign.setLine(2, c);
                            sign.setLine(3, d);

                            sign.update();
                        }
                    }

                } catch (Exception e) {
                    if (getUhc().isDebug())
                        getLog().log(Level.SEVERE, "Something went during loading the stats. Skipping...",
                                e.getMessage());
                }
            } else {
                if (getUhc().isDebug()) {
                    getLog().log(Level.SEVERE, "The server has no connection to the database.");
                    getLog().log(Level.INFO, "Now using file backend to load ranks.");
                }
                if (firstPlace == null || secondPlace == null || thirdPlace == null)
                    return;

                LinkedList<Location> locs = new LinkedList<>();

                locs.add(firstPlace.add(0, 1, 0));
                locs.add(secondPlace.add(0, 1, 0));
                locs.add(thirdPlace.add(0, 1, 0));

                for (String name : UHCFileRegister.getPlayerFile().getConfigurationSection("Players").getKeys(false)) {
                    rank.put(
                            UHCRegister.getStatsUtil()
                                    .getRank(Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(name))),
                            UUIDFetcher.getUUID(name).toString());
                }
                for (int i = 0; i < locs.size(); i++) {
                    int id = i + 1;

                    if (!(locs.get(i).getBlock().getType().equals(Material.SKULL)))
                        locs.get(i).getBlock().setType(Material.SKULL);

                    Skull s = (Skull) locs.get(i).getBlock().getState();

                    s.setSkullType(SkullType.PLAYER);

                    if (!rank.containsKey(id))
                        continue;

                    String name = Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))).getName();

                    s.setOwner(name);
                    s.update();

                    Location sig = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY(),
                            locs.get(i).getZ());

                    if (sig.getBlock().getState() instanceof Sign) {
                        Sign sign = (Sign) sig.getBlock().getState();

                        String a = line1.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                                .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                        .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
                        String b = line2.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                                .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                        .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
                        String c = line3.replace("[rank]", Long.toString(id)).replace("[player]", name)
                                .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                        .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
                        String d = line4.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                                .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                        .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));

                        sign.setLine(0, a);
                        sign.setLine(1, b);
                        sign.setLine(2, c);
                        sign.setLine(3, d);

                        sign.update();
                    }
                }
            }
        } else {
            getLog().log(Level.INFO, "Now using file backend to load ranks.");
            if (firstPlace == null || secondPlace == null || thirdPlace == null)
                return;

            LinkedList<Location> locs = new LinkedList<>();

            locs.add(firstPlace.add(0, 1, 0));
            locs.add(secondPlace.add(0, 1, 0));
            locs.add(thirdPlace.add(0, 1, 0));

            for (String name : UHCFileRegister.getPlayerFile().getConfigurationSection("Players").getKeys(false)) {
                rank.put(
                        UHCRegister.getStatsUtil()
                                .getRank(Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(name))),
                        UUIDFetcher.getUUID(name).toString());
            }
            for (int i = 0; i < locs.size(); i++) {
                int id = i + 1;

                if (!(locs.get(i).getBlock().getType().equals(Material.SKULL)))
                    locs.get(i).getBlock().setType(Material.SKULL);

                Skull s = (Skull) locs.get(i).getBlock().getState();

                s.setSkullType(SkullType.PLAYER);

                if (!rank.containsKey(id))
                    continue;

                String name = Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))).getName();

                s.setOwner(name);
                s.update();

                Location sig = new Location(locs.get(i).getWorld(), locs.get(i).getX(), locs.get(i).getY(),
                        locs.get(i).getZ());

                if (sig.getBlock().getState() instanceof Sign) {
                    Sign sign = (Sign) sig.getBlock().getState();

                    String a = line1.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                            .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                    .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
                    String b = line2.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                            .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                    .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
                    String c = line3.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                            .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                    .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));
                    String d = line4.replace("[rank]", Integer.toString(id)).replace("[player]", name)
                            .replace("[points]", Long.toString(UHCRegister.getStatsUtil()
                                    .getPoints(Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))))));

                    sign.setLine(0, a);
                    sign.setLine(1, b);
                    sign.setLine(2, c);
                    sign.setLine(3, d);

                    sign.update();
                }
            }
        }

    }

    public HashMap<Long, String> getRank() {
        return rank;
    }

    private void setRank(HashMap<Long, String> rank) {
        this.rank = rank;
    }

    public Location getFirstPlace() {
        return firstPlace;
    }

    public void setFirstPlace(Location firstPlace) {
        this.firstPlace = firstPlace;
    }

    public Location getSecondPlace() {
        return secondPlace;
    }

    public void setSecondPlace(Location secondPlace) {
        this.secondPlace = secondPlace;
    }

    public Location getThirdPlace() {
        return thirdPlace;
    }

    public void setThirdPlace(Location thirdPlace) {
        this.thirdPlace = thirdPlace;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }
}
