package de.alphahelix.uhc.listeners;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.nms.SimpleTitle;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.PlayerDummie;
import de.alphahelix.uhc.instances.Spectator;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class GameEndsListener extends SimpleListener {

    private Inventory cInv = null;
    private String winnerName = "AlphaHelix";

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player dead = e.getEntity();
        UUID deadID = UUIDFetcher.getUUID(dead);

        e.setDeathMessage(null);

        for (Player p : Util.makePlayerArray(PlayerUtil.getAll())) {
            if (e.getEntity().getLastDamageCause() == null)
                p.sendMessage(UHCFileRegister.getDeathmessageFile().getMessage(null)
                        .replace("[player]", e.getEntity().getCustomName()).replace("[entity]",
                                UHCFileRegister.getDeathmessageFile().getIsAMob()));
            if (e.getEntity().getLastDamageCause().getCause() == null)
                p.sendMessage(UHCFileRegister.getDeathmessageFile().getMessage(null)
                        .replace("[player]", e.getEntity().getCustomName()).replace("[entity]",
                                UHCFileRegister.getDeathmessageFile().getIsAMob()));
            else
                p.sendMessage(UHCFileRegister.getDeathmessageFile()
                        .getMessage(e.getEntity().getLastDamageCause().getCause())
                        .replace("[player]", e.getEntity().getCustomName())
                        .replace("[entity]", (e.getEntity().getKiller() == null
                                ? UHCFileRegister.getDeathmessageFile().getIsAMob()
                                : e.getEntity().getKiller().getName())));
        }

        PlayerUtil.removeSurvivor(dead);
        PlayerUtil.addDead(dead);

        new Spectator(dead);

        dead.getWorld().strikeLightning(dead.getLocation());

        TablistUtil.sendTablist();

        if (dead.getKiller() != null && dead.getKiller() instanceof Player) {

            UUID id = UUIDFetcher.getUUID(dead.getKiller());

            StatsUtil.addKill(id);
            StatsUtil.addPoints(
                    id,
                    UHCFileRegister.getOptionsFile().getPointsOnKill());
            StatsUtil.addCoins(
                    id,
                    UHCFileRegister.getOptionsFile().getCoinssOnKill());

            if (UHC.isCrates()) {

                Crate c = UHCFileRegister.getCrateFile().getRandomCrate();

                if (c != null)
                    if (Math.random() <= c.getRarity()) {
                        StatsUtil.addCrate(id, c);
                        if (dead.getKiller().isOnline())
                            dead.getKiller().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getCrateDropped(c));
                    }
            }
        }

        StatsUtil.addDeath(deadID);
        StatsUtil.removePoints(deadID, UHCFileRegister.getOptionsFile().getPointsOnDeath());
        StatsUtil.addCoins(deadID, UHCFileRegister.getOptionsFile().getCoinsOnDeath());

        if (!UHCFileRegister.getOptionsFile().getCommandOnKill().equals(""))
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    UHCFileRegister.getOptionsFile().getCommandOnKill().replace("[player]", dead.getName())
                            .replace("[killer]", (dead.getKiller() == null ? "" : dead.getKiller().getName())));
        if (!UHCFileRegister.getOptionsFile().getCommandOnDeath().equals(""))
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    UHCFileRegister.getOptionsFile().getCommandOnDeath().replace("[player]", dead.getName())
                            .replace("[killer]", (dead.getKiller() == null ? "" : dead.getKiller().getName())));

        for (Player p : Util.makePlayerArray(PlayerUtil.getAll())) {
            ScoreboardUtil.updateAlive(p);
            ScoreboardUtil.updateSpecs(p);
        }

        ArrayList<ItemStack> dropList = new ArrayList<>();

        if (Scenarios.isPlayedAndEnabled(Scenarios.KILLSWITCH)) {
            if (dead.getKiller() instanceof Player) {
                dead.getKiller().getInventory().clear();
                dead.getKiller().getInventory().setContents(dead.getInventory().getContents());
                dead.getKiller().getInventory().setArmorContents(dead.getInventory().getArmorContents());
            }
        } else {
            if (UHCFileRegister.getDropsFile().isDeathchest()) {
                dead.getLocation().getBlock().setType(Material.CHEST);
                Chest c = (Chest) dead.getLocation().getBlock().getState();
                cInv = c.getBlockInventory();
            }

            for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Player")) {
                if (Math.random() < UHCFileRegister.getDropsFile().getChance("Player", drops))
                    dropList.add(drops);
            }

            for (final ItemStack drops : e.getDrops())
                dropList.add(drops);
        }

        if (Scenarios.isPlayedAndEnabled(Scenarios.BAREBONES)) {
            dropList = new ArrayList<>();

            dropList.add(new ItemStack(Material.DIAMOND, 1));
            dropList.add(new ItemStack(Material.GOLDEN_APPLE, 1));
            dropList.add(new ItemStack(Material.ARROW, 32));
            dropList.add(new ItemStack(Material.STRING, 2));
        }

        if (Scenarios.isPlayedAndEnabled(Scenarios.TIME_BOMB)) {
            for (ItemStack td : dropList) {
                cInv.addItem(td);
            }
            new BukkitRunnable() {
                public void run() {
                    dead.getWorld().createExplosion(dead.getLocation().getX(), dead.getLocation().getY(),
                            dead.getLocation().getZ(), 10, false, true);
                    dead.getLocation().getBlock().setType(Material.AIR);
                }
            }.runTaskLater(UHC.getInstance(), 600);
        } else {
            for (ItemStack td : dropList) {
                if (UHCFileRegister.getDropsFile().isDeathchest()) {
                    cInv.addItem(td);
                } else {
                    dead.getWorld().dropItemNaturally(dead.getLocation(), td);
                }
            }
        }

        if (Scenarios.isPlayedAndEnabled(Scenarios.ZOMBIES)) {
            dead.getWorld().spawnEntity(dead.getLocation(), EntityType.ZOMBIE);
        }

        if (UHC.isTeams() && TeamManagerUtil.isInOneTeam(dead) != null)
            if (PlayerUtil.getSurvivors()
                    .size() <= (TeamManagerUtil.isInOneTeam(dead).getPlayers().size()))
                UHCRegister.getTeamListener().setFFA();

        if (PlayerUtil.getSurvivors().size() == 4)
            UHCRegister.getDeathmatchTimer().startTimer();

        if (PlayerUtil.getSurvivors().size() <= 1) {

            GState.setCurrentState(GState.END);

            if (PlayerUtil.getSurvivors().size() == 0) {
                Bukkit.reload();
                return;
            }

            setWinnerName(PlayerUtil.getSurvivors().get(0));
            for (Player p : Util.makePlayerArray(PlayerUtil.getAll())) {
                p.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile()
                        .getWin(Bukkit.getPlayer(getWinnerName())));
                SimpleTitle.sendTitle(p,
                        " ", UHC.getPrefix() + UHCFileRegister.getMessageFile()
                                .getWin(Bukkit.getPlayer(getWinnerName())),
                        2, 2, 2);
            }

            StatsUtil.addWins(UUIDFetcher.getUUID(getWinnerName()), 1);
            StatsUtil.addCoins(UUIDFetcher.getUUID(getWinnerName()), UHCFileRegister.getOptionsFile().getCoinsOnWin());

            if (StatsUtil.getWins(UUIDFetcher.getUUID(getWinnerName())) == 50) {
                if (!StatsUtil.hasAchievement(UUIDFetcher.getUUID(getWinnerName()), UHCAchievements.WINNER)) {
                    StatsUtil.addAchievement(UUIDFetcher.getUUID(getWinnerName()), UHCAchievements.WINNER);
                    Bukkit.getPlayer(getWinnerName()).sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getAchievementUnlocked().replace("[achievement]", UHCAchievements.WINNER.getName()));
                }
            }

            StatsUtil.addPoints(UUIDFetcher.getUUID(getWinnerName()),
                    UHCFileRegister.getOptionsFile().getPointsOnWin());

            if (!UHCFileRegister.getOptionsFile().getCommandOnWin().equals(""))
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), UHCFileRegister.getOptionsFile()
                        .getCommandOnWin().replace("[player]", getWinnerName()));

            UHCRegister.getRestartTimer().startTimer();
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        boolean isSpec = PlayerUtil.isDead(p);
        TablistUtil.sendTablist();

        if (!(GState.isState(GState.LOBBY) || GState.isState(GState.END))) {
            if (!isSpec) {
                if (PlayerUtil.getSurvivors().size() > 1) { //TODO: Change to 2
                    new PlayerDummie(p);
                } else {
                    PlayerUtil.removeAll(p);
                }
            }

            for (Player other : Util.makePlayerArray(PlayerUtil.getAll())) {
                ScoreboardUtil.updateAlive(other);
                ScoreboardUtil.updateSpecs(other);
            }

            if (PlayerUtil.getSurvivors().size() <= 1) {
                GState.setCurrentState(GState.END);

                if (PlayerUtil.getSurvivors().size() == 0) {
                    Bukkit.reload();
                    return;
                }

                setWinnerName(PlayerUtil.getSurvivors().get(0));
                for (Player other : Util.makePlayerArray(PlayerUtil.getAll())) {
                    other.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile()
                            .getWin(Bukkit.getPlayer(getWinnerName())));
                    SimpleTitle.sendTitle(other, " ",
                            UHC.getPrefix() + UHCFileRegister.getMessageFile()
                                    .getWin(Bukkit.getPlayer(getWinnerName())),
                            2, 2, 2);
                }

                StatsUtil.addPoints(UUIDFetcher.getUUID(getWinnerName()),
                        UHCFileRegister.getOptionsFile().getPointsOnWin());

                if (!UHCFileRegister.getOptionsFile().getCommandOnWin().equals(""))
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), UHCFileRegister.getOptionsFile()
                            .getCommandOnWin().replace("[player]", getWinnerName()));

                UHCRegister.getRestartTimer().startTimer();
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent e) {
        if (GState.isState(GState.LOBBY))
            return;
        if (e.getRightClicked() instanceof Villager && e.getRightClicked().isCustomNameVisible())
            e.setCancelled(true);
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
}
