package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.fakeapi.instances.FakeArmorstand;
import de.alphahelix.alphalibary.fakeapi.utils.ArmorstandUtil;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.item.LeatherItemBuilder;
import de.alphahelix.alphalibary.item.data.SkullData;
import de.alphahelix.alphalibary.utils.MinecraftVersion;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NPCUtil extends Util {

    public NPCUtil(UHC uhc) {
        super(uhc);
    }

    public void prepareNPC(Location loc, OfflinePlayer skin, final Player toSend) {
        de.alphahelix.alphalibary.fakeapi.utils.PlayerUtil.spawnTemporaryPlayer(toSend, loc, skin, UHCFileRegister.getStatsFile().getColorString("StatsNPC"));
        createStatsArmorStands(toSend);
    }

    private void createStatsArmorStands(Player p) {
        try {
            Location loc = UHCFileRegister.getLocationsFile().getStatsNPCLocation();

            FakeArmorstand games = ArmorstandUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Games").replace("[games]", Long.toString(UHCRegister.getStatsUtil().getGames(p))));

            FakeArmorstand kills = ArmorstandUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Kills").replace("[kills]", Long.toString(UHCRegister.getStatsUtil().getKills(p))));

            FakeArmorstand deaths = ArmorstandUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Deaths").replace("[deaths]",
                            Long.toString(UHCRegister.getStatsUtil().getDeaths(p))));

            FakeArmorstand killrate = ArmorstandUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("KillDeathRate").replace("[kdr]",
                            Double.toString(UHCRegister.getStatsUtil().getKillDeathRate(p))));

            FakeArmorstand wins = ArmorstandUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Wins").replace("[wins]",
                            Long.toString(UHCRegister.getStatsUtil().getWins(p))));

            FakeArmorstand coins = ArmorstandUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Coins").replace("[coins]",
                            Long.toString(UHCRegister.getStatsUtil().getCoins(p))));

            FakeArmorstand points = ArmorstandUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Points").replace("[points]",
                            Long.toString(UHCRegister.getStatsUtil().getPoints(p))));

            FakeArmorstand rank = ArmorstandUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Rank").replace("[rank]",
                            Long.toString(UHCRegister.getStatsUtil().getRank(p))));

            ArmorstandUtil.moveArmorstand(p, 0, 0.1, 0, rank);
            ArmorstandUtil.moveArmorstand(p, 0, 0.3, 0, points);
            ArmorstandUtil.moveArmorstand(p, 0, 0.5, 0, coins);
            ArmorstandUtil.moveArmorstand(p, 0, 0.7, 0, wins);
            ArmorstandUtil.moveArmorstand(p, 0, 0.9, 0, killrate);
            ArmorstandUtil.moveArmorstand(p, 0, 1.1, 0, deaths);
            ArmorstandUtil.moveArmorstand(p, 0, 1.3, 0, kills);
            ArmorstandUtil.moveArmorstand(p, 0, 1.5, 0, games);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawnRankingArmorStand(Location l, int rank) {
        ArmorStand as = l.getWorld().spawn(l, ArmorStand.class);
        ArmorStand holo = l.getWorld().spawn(l.subtract(0, 0.2, 0), ArmorStand.class);

        String name = UHCRegister.getStatsUtil().getPlayerByRank(rank).getName();

        ItemStack head = new ItemBuilder
                (Material.SKULL_ITEM)
                .addItemData(new SkullData(UHCRegister.getStatsUtil().getPlayerByRank(rank).getName())).build();
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
        try {
            if (MinecraftVersion.getServer() != MinecraftVersion.EIGHT) {
                Class.forName("org.bukkit.entity.Entity").getMethod("setGlowing", boolean.class).invoke(as, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        as.setCustomNameVisible(true);

        holo.setBasePlate(false);
        holo.setArms(false);
        holo.setCustomName("§7" + name);
        holo.setVisible(false);
        holo.setGravity(false);
        holo.setCustomNameVisible(true);

        UHCFileRegister.getLocationsFile().addRankingArmorStand(l, rank);
    }

    public void spawnArmorStand(Location l, String name) {
        if (UHCRegister.getTeamManagerUtil().getTeamByName(name) == null)
            return;

        UHCTeam team = UHCRegister.getTeamManagerUtil().getTeamByName(name);

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

        UHCFileRegister.getLocationsFile().addArmorStand(l, name);
    }

    public void removeArmorStand(Location l, String name) {
        for (Entity e : l.getWorld().getEntitiesByClass(ArmorStand.class)) {
            if (e.isCustomNameVisible() && ChatColor.stripColor(e.getCustomName()).equals(name)) {
                UHCFileRegister.getLocationsFile().removeArmorStand(name);
                e.remove();
            }
        }
    }

    public void removeRankingArmorStand(Location l, int rank) {
        String name = ChatColor.stripColor(UHCRegister.getStatsUtil().getPlayerByRank(rank).getName());
        for (Entity e : l.getWorld().getEntitiesByClass(ArmorStand.class)) {
            if (e.isCustomNameVisible() && (ChatColor.stripColor(e.getCustomName()).equals(name)
                    || ChatColor.stripColor(e.getCustomName()).equals(rank + "."))) {
                UHCFileRegister.getLocationsFile().removeRankingArmorstand(rank);
                e.remove();
            }
        }
    }
}
