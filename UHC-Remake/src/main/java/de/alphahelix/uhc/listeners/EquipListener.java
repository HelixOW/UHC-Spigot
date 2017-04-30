package de.alphahelix.uhc.listeners;

import de.alphahelix.alphalibary.fakeapi.events.FakePlayerClickEvent;
import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.nms.REnumHand;
import de.alphahelix.alphalibary.utils.MinecraftVersion;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.PlayerDummie;
import de.alphahelix.uhc.instances.PlayerInfo;
import de.alphahelix.uhc.instances.Spectator;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class EquipListener extends SimpleListener {

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent e) {
        UUIDFetcher.getUUID(e.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID id = UUIDFetcher.getUUID(p);

        e.setJoinMessage(null);

        PlayerUtil.clearUp(p);
        PlayerUtil.addAll(p);

        if (UHC.isMySQLMode()) {
            if (!UHC.getDB().containsPlayer(p)) {
                UHC.getDB().insert(p.getName(), id.toString(),
                        new PlayerInfo(p,
                                0,
                                0,
                                0,
                                0,
                                0,
                                0,
                                new ArrayList<Kit>(),
                                new ArrayList<UHCAchievements>(),
                                new ArrayList<String>(),
                                new Date()).encode());
            } else {
                PlayerInfo.decode((String) UHC.getDB().getResult("UUID", id.toString(), "Infos"));
            }
        } else {
            UHCFileRegister.getPlayerFile().addPlayer(p);
            UHCFileRegister.getPlayerFile().getInfo(id);
        }

        UHCFileRegister.getRanksFile().initRank(p);
        TablistUtil.sortTablist(p);

        if (UHCFileRegister.getOptionsFile().isRemoveAttackCooldown()) {
            if (MinecraftVersion.getServer() != MinecraftVersion.EIGHT)
                try {
                    Object attributeInstance = p.getClass().getMethod("getAttribute", Class.forName("org.bukkit.attribute.Attribute")).invoke(p, Class.forName("org.bukkit.attribute.Attribute").getEnumConstants()[5]);
                    Class.forName("org.bukkit.attribute.AttributeInstance").getMethod("setBaseValue", double.class).invoke(attributeInstance, 18);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        }

        if (!GState.isState(GState.LOBBY)) {
            if (!PlayerUtil.isSurivor(p)) {
                new Spectator(p, Bukkit.getWorld("UHC").getSpawnLocation());
                ScoreboardUtil.setIngameScoreboard(p);

                for (String other : PlayerUtil.getAll()) {
                    if (Bukkit.getPlayer(other) == null)
                        continue;
                    ScoreboardUtil.setIngameScoreboard(Bukkit.getPlayer(other));
                }
                return;
            }

            PlayerDummie.restore(p);

            TablistUtil.sendTablist();
            ScoreboardUtil.setIngameScoreboard(p);

            return;
        }

        new BukkitRunnable() {
            public void run() {
                if (UHCFileRegister.getLocationsFile().getRewardNPCLocation() != null)
                    NPCUtil.createRewardNPC(UHCFileRegister.getLocationsFile().getRewardNPCLocation(), p);
                if (UHCFileRegister.getLocationsFile().getStatsNPCLocation() != null)
                    NPCUtil.prepareNPC(UHCFileRegister.getLocationsFile().getStatsNPCLocation(), p);
            }
        }.runTaskLater(UHC.getInstance(), 20);

        p.setGameMode(GameMode.ADVENTURE);
        UHCRegister.getLobbyTimer().startTimer();

        p.teleport(UHCFileRegister.getLocationsFile().getLobby().clone().add(0, 2, 0));
        TablistUtil.sendTablist();

        for (String other : PlayerUtil.getAll()) {
            if (Bukkit.getPlayer(other) == null)
                continue;
            Bukkit.getPlayer(other).sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile()
                    .getJoin().replace("[player]", p.getDisplayName()));
        }

        ScoreboardUtil.setLobbyScoreboard(p);

        RankingUtil.updateArmorStands(p);

        p.getInventory().setItem(UHCFileRegister.getAchievementFile().getItem().getSlot(), UHCFileRegister.getAchievementFile().getItem().getItemStack());

        if (UHC.isScenarios()) {
            if (UHC.isScenarioVoting())
                p.getInventory().setItem(UHCFileRegister.getScenarioFile().getItem(null).getSlot(),
                        UHCFileRegister.getScenarioFile().getItem(null).getItemStack());
            else
                p.getInventory().setItem(UHCFileRegister.getScenarioFile().getItem(null).getSlot(),
                        UHCFileRegister.getScenarioFile().getItem(Scenarios.getScenario()).getItemStack());

        } else if (UHC.isKits()) {
            p.getInventory().setItem(UHCFileRegister.getKitsFile().getItem().getSlot(),
                    UHCFileRegister.getKitsFile().getItem().getItemStack());
        }

        if (UHC.isTeams()) {
            p.getInventory().setItem(UHCFileRegister.getTeamFile().getItem().getSlot(),
                    UHCFileRegister.getTeamFile().getItem().getItemStack());
        }

        if (UHC.isCrates()) {
            p.getInventory()
                    .setItem(UHCFileRegister.getCrateFile().getItem().getSlot(),
                            UHCFileRegister.getCrateFile().getItem().getItemStack());
        }

        if (UHC.isLobby()) {
            p.getInventory().setItem(UHCFileRegister.getLobbyFile().getItem().getSlot(),
                    UHCFileRegister.getLobbyFile().getItem().getItemStack());
        }

    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID id = UUIDFetcher.getUUID(p);

        e.setQuitMessage(null);

        if (!PlayerUtil.isDead(p)) {
            for (String other : PlayerUtil.getAll()) {
                if (Bukkit.getPlayer(other) == null)
                    continue;
                Bukkit.getPlayer(other).sendMessage(UHC.getPrefix() +
                        UHCFileRegister.getMessageFile().getLeave().replace("[player]", p.getDisplayName()));
            }
        }

        UHCFileRegister.getKitsFile().removePlayedKit(p);

        if (PlayerInfo.getPlayerInfo(id) == null) return;

        new BukkitRunnable() {
            public void run() {
                if (UHC.isMySQLMode())
                    UHC.getDB().update(id, "Infos", PlayerInfo.getPlayerInfo(id).encode());
                else
                    UHCFileRegister.getPlayerFile().updateInfo(id, PlayerInfo.getPlayerInfo(id));
            }
        }.runTaskLaterAsynchronously(UHC.getInstance(), 2);


        if (GState.isState(GState.LOBBY) || GState.isState(GState.END))
            PlayerUtil.removeAll(p);
    }

    @EventHandler
    public void onRewardClick(FakePlayerClickEvent e) {
        if (e.getHand() != REnumHand.MAIN_HAND) return;
        if (!ChatColor.stripColor(e.getFakePlayer().getName()).equalsIgnoreCase(ChatColor.stripColor(UHCFileRegister.getStatsFile().getColorString("RewardNPC"))))
            return;
        if (StatsUtil.getUHCRank(UUIDFetcher.getUUID(e.getPlayer())) != null) {
            StatsUtil.getUHCRank(UUIDFetcher.getUUID(e.getPlayer())).rewardPlayer(e.getPlayer());
        }
    }
}
