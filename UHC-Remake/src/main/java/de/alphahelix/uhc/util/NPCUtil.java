package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.fakeapi.FakeAPI;
import de.alphahelix.alphalibary.fakeapi.instances.FakeArmorstand;
import de.alphahelix.alphalibary.fakeapi.instances.FakePlayer;
import de.alphahelix.alphalibary.fakeapi.instances.NoSuchFakeEntityException;
import de.alphahelix.alphalibary.fakeapi.utils.ArmorstandFakeUtil;
import de.alphahelix.alphalibary.fakeapi.utils.PlayerFakeUtil;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.item.LeatherItemBuilder;
import de.alphahelix.alphalibary.item.data.SkullData;
import de.alphahelix.alphalibary.nms.REnumEquipSlot;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class NPCUtil {

    public static void createRewardNPC(Location loc, Player toSend) {
        PlayerFakeUtil.spawnTemporaryPlayer(toSend, loc, UHCFileRegister.getOptionsFile().getRewardPlayer(),
                UHCFileRegister.getStatsFile().getColorString("RewardNPC"));
    }

    public static void prepareNPC(Location loc, Player toSend) {
        PlayerFakeUtil.spawnTemporaryPlayer(toSend, loc, toSend, UHCFileRegister.getStatsFile().getColorString("StatsNPC"));
        createStatsArmorStands(toSend);
    }

    private static void createStatsArmorStands(Player p) {
        try {
            Location loc = UHCFileRegister.getLocationsFile().getStatsNPCLocation();
            UUID id = UUIDFetcher.getUUID(p);

            FakeArmorstand games = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Games").replace("[games]", Long.toString(StatsUtil.getGames(id))));

            FakeArmorstand kills = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Kills").replace("[kills]", Long.toString(StatsUtil.getKills(id))));

            FakeArmorstand deaths = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Deaths").replace("[deaths]",
                            Long.toString(StatsUtil.getDeaths(id))));

            FakeArmorstand killrate = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("KillDeathRate").replace("[kdr]",
                            Double.toString(StatsUtil.getKillDeathRate(id))));

            FakeArmorstand wins = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Wins").replace("[wins]",
                            Long.toString(StatsUtil.getWins(id))));

            FakeArmorstand coins = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Coins").replace("[coins]",
                            Long.toString(StatsUtil.getCoins(id))));

            FakeArmorstand points = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Points").replace("[points]",
                            Long.toString(StatsUtil.getPoints(id))));

            FakeArmorstand rank = ArmorstandFakeUtil.spawnTemporaryArmorstand(
                    p,
                    loc.clone(),
                    UHCFileRegister.getStatsFile().getColorString("Rank").replace("[rank]",
                            Long.toString(StatsUtil.getRank(id))));

            ArmorstandFakeUtil.moveArmorstand(p, 0, 0.1, 0, rank);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 0.3, 0, points);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 0.5, 0, coins);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 0.7, 0, wins);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 0.9, 0, killrate);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 1.1, 0, deaths);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 1.3, 0, kills);
            ArmorstandFakeUtil.moveArmorstand(p, 0, 1.5, 0, games);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void spawnRanking(Location l, int rank, Player p) {
        String name = StatsUtil.getPlayernameByRank(rank);

        FakePlayer ranked = PlayerFakeUtil.spawnTemporaryPlayer(p, l, Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(name)), "§7" + Integer.toString(rank) + ".");
        ArmorstandFakeUtil.spawnTemporaryArmorstand(p, l.clone().add(0, 0.2, 0), "§7" + name);

        ItemStack chest, pants, boots;
        if (rank == 1) {
            chest = new ItemBuilder(Material.GOLD_CHESTPLATE).build();
            pants = new ItemBuilder(Material.GOLD_LEGGINGS).build();
            boots = new ItemBuilder(Material.GOLD_BOOTS).build();
        } else if (rank == 2) {
            chest = new ItemBuilder(Material.IRON_CHESTPLATE).build();
            pants = new ItemBuilder(Material.IRON_LEGGINGS).build();
            boots = new ItemBuilder(Material.IRON_BOOTS).build();
        } else if (rank == 3) {
            chest = new ItemBuilder(Material.LEATHER_CHESTPLATE).build();
            pants = new ItemBuilder(Material.LEATHER_LEGGINGS).build();
            boots = new ItemBuilder(Material.LEATHER_BOOTS).build();
        } else {
            chest = new LeatherItemBuilder(Material.LEATHER_CHESTPLATE).setColor(Color.WHITE).build();
            pants = new LeatherItemBuilder(Material.LEATHER_LEGGINGS).setColor(Color.WHITE).build();
            boots = new LeatherItemBuilder(Material.LEATHER_BOOTS).setColor(Color.WHITE).build();
        }

        PlayerFakeUtil.equipPlayer(p, ranked, chest, REnumEquipSlot.CHESTPLATE);
        PlayerFakeUtil.equipPlayer(p, ranked, pants, REnumEquipSlot.LEGGINGS);
        PlayerFakeUtil.equipPlayer(p, ranked, boots, REnumEquipSlot.BOOTS);

        UHCFileRegister.getLocationsFile().addRankingArmorStand(l, rank);
    }

    public static void removeRankingArmorStand(int rank, Player p) {
        try {
            PlayerFakeUtil.removePlayer(p, FakeAPI.getFakePlayerByName(p, rank + "."));
            ArmorstandFakeUtil.destroyArmorstand(p, FakeAPI.getFakeArmorstandByName(p, StatsUtil.getPlayernameByRank(rank)));
        } catch (NoSuchFakeEntityException e) {
            e.printStackTrace();
        }
    }

    public static void spawnArmorStand(Location l, String name) {
        if (TeamManagerUtil.getTeamByName(name) == null)
            return;

        UHCTeam team = TeamManagerUtil.getTeamByName(name);

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

    public static void removeArmorStand(Location l, String name) {
        for (Entity e : l.getWorld().getEntitiesByClass(ArmorStand.class)) {
            if (e.isCustomNameVisible() && ChatColor.stripColor(e.getCustomName()).equals(name)) {
                UHCFileRegister.getLocationsFile().removeArmorStand(name);
                e.remove();
            }
        }
    }
}
