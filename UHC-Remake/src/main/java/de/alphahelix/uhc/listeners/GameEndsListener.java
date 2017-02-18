package de.alphahelix.uhc.listeners;

import de.alphahelix.alphalibary.nms.SimpleTitle;
import de.alphahelix.alphalibary.utils.MinecraftVersion;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.instances.Spectator;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class GameEndsListener extends SimpleListener {

    private Inventory cInv = null;
    private HashMap<String, Location> playerLogOut = new HashMap<>();
    private HashMap<String, Villager> playerDummies = new HashMap<>();
    private HashMap<String, PlayerInventory> playerInv = new HashMap<>();
    private String winnerName = "AlphaHelix";

    public GameEndsListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        final Player dead = e.getEntity();

        e.setDeathMessage(null);

        for (String other : UHCRegister.getPlayerUtil().getAll()) {
            if (Bukkit.getPlayer(other) == null)
                return;
            if (e.getEntity().getLastDamageCause() == null) {
                Bukkit.getPlayer(other)
                        .sendMessage(UHCFileRegister.getDeathmessageFile().getMessage(null)
                                .replace("[player]", e.getEntity().getCustomName()).replace("[entity]",
                                        UHCFileRegister.getDeathmessageFile().getIsAMob()));
            }
            if (e.getEntity().getLastDamageCause().getCause() == null) {
                Bukkit.getPlayer(other)
                        .sendMessage(UHCFileRegister.getDeathmessageFile().getMessage(null)
                                .replace("[player]", e.getEntity().getCustomName()).replace("[entity]",
                                        UHCFileRegister.getDeathmessageFile().getIsAMob()));
            }
            Bukkit.getPlayer(other)
                    .sendMessage(UHCFileRegister.getDeathmessageFile()
                            .getMessage(e.getEntity().getLastDamageCause().getCause())
                            .replace("[player]", e.getEntity().getCustomName())
                            .replace("[entity]", (e.getEntity().getKiller() == null
                                    ? UHCFileRegister.getDeathmessageFile().getIsAMob()
                                    : e.getEntity().getKiller().getName())));
        }

        UHCRegister.getPlayerUtil().removeSurvivor(dead);
        UHCRegister.getPlayerUtil().addDead(dead);

        new Spectator(dead);

        dead.getWorld().strikeLightning(dead.getLocation());

        UHCRegister.getTablistUtil().sendTablist();

        if (dead.getKiller() != null && dead.getKiller() instanceof Player) {
            UHCRegister.getStatsUtil().addKill(dead.getKiller());
            UHCRegister.getStatsUtil().addPoints(dead.getKiller(),
                    UHCFileRegister.getOptionsFile().getPointsOnKill());
            UHCRegister.getStatsUtil().addCoins(dead.getKiller(),
                    UHCFileRegister.getOptionsFile().getCoinssOnKill());

            if (getUhc().isCrates()) {

                Crate c = UHCFileRegister.getCrateFile().getRandomCrate();

                if (Math.random() <= c.getRarity()) {
                    UHCRegister.getStatsUtil().addCrate(c, dead.getKiller());
                    if (dead.getKiller().isOnline())
                        dead.getKiller().sendMessage(getUhc().getPrefix() + UHCFileRegister.getMessageFile().getCrateDropped(c));
                }
            }
        }

        UHCRegister.getStatsUtil().addDeath(dead);
        UHCRegister.getStatsUtil().removePoints(dead, UHCFileRegister.getOptionsFile().getPointsOnDeath());
        UHCRegister.getStatsUtil().addCoins(dead, UHCFileRegister.getOptionsFile().getCoinsOnDeath());

        if (!UHCFileRegister.getOptionsFile().getCommandOnKill().equals(""))
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    UHCFileRegister.getOptionsFile().getCommandOnKill().replace("[player]", dead.getName())
                            .replace("[killer]", (dead.getKiller() == null ? "" : dead.getKiller().getName())));
        if (!UHCFileRegister.getOptionsFile().getCommandOnDeath().equals(""))
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    UHCFileRegister.getOptionsFile().getCommandOnDeath().replace("[player]", dead.getName())
                            .replace("[killer]", (dead.getKiller() == null ? "" : dead.getKiller().getName())));

        for (String other : UHCRegister.getPlayerUtil().getAll()) {
            if (Bukkit.getPlayer(other) == null)
                continue;
            UHCRegister.getScoreboardUtil().updateAlive(Bukkit.getPlayer(other));
            UHCRegister.getScoreboardUtil().updateSpecs(Bukkit.getPlayer(other));
        }

        ArrayList<ItemStack> dropList = new ArrayList<>();

        if (scenarioCheck(Scenarios.KILLSWITCH)) {
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

        if (scenarioCheck(Scenarios.BAREBONES)) {
            dropList = new ArrayList<>();

            dropList.add(new ItemStack(Material.DIAMOND, 1));
            dropList.add(new ItemStack(Material.GOLDEN_APPLE, 1));
            dropList.add(new ItemStack(Material.ARROW, 32));
            dropList.add(new ItemStack(Material.STRING, 2));
        }

        if (scenarioCheck(Scenarios.TIME_BOMB)) {
            for (ItemStack td : dropList) {
                cInv.addItem(td);
            }
            new BukkitRunnable() {
                public void run() {
                    dead.getWorld().createExplosion(dead.getLocation().getX(), dead.getLocation().getY(),
                            dead.getLocation().getZ(), 10, false, true);
                    dead.getLocation().getBlock().setType(Material.AIR);
                }
            }.runTaskLater(getUhc(), 600);
        } else {
            for (ItemStack td : dropList) {
                if (UHCFileRegister.getDropsFile().isDeathchest()) {
                    cInv.addItem(td);
                } else {
                    dead.getWorld().dropItemNaturally(dead.getLocation(), td);
                }
            }
        }

        if (scenarioCheck(Scenarios.ZOMBIES)) {
            dead.getWorld().spawnEntity(dead.getLocation(), EntityType.ZOMBIE);
        }

        if (getUhc().isTeams() && UHCRegister.getTeamManagerUtil().isInOneTeam(dead) != null)
            if (UHCRegister.getPlayerUtil().getSurvivors()
                    .size() <= (UHCRegister.getTeamManagerUtil().isInOneTeam(dead).getPlayers().size()))
                UHCRegister.getTeamListener().setFFA();

        if (UHCRegister.getPlayerUtil().getSurvivors().size() == 4)
            UHCRegister.getDeathmatchTimer().startDeathMatchTimer();

        if (UHCRegister.getPlayerUtil().getSurvivors().size() <= 1) {

            GState.setCurrentState(GState.END);

            if (UHCRegister.getPlayerUtil().getSurvivors().size() == 0) {

                Bukkit.reload();
                return;
            }

            setWinnerName(UHCRegister.getPlayerUtil().getSurvivors().get(0));
            for (String pName : UHCRegister.getPlayerUtil().getAll()) {
                Bukkit.getPlayer(pName).sendMessage(getUhc().getPrefix() + UHCFileRegister.getMessageFile()
                        .getWin(Bukkit.getPlayer(getWinnerName())));
                SimpleTitle
                        .sendTitle(Bukkit.getPlayer(pName),
                                " ", getUhc().getPrefix() + UHCFileRegister.getMessageFile()
                                        .getWin(Bukkit.getPlayer(getWinnerName())),
                                2, 2, 2);
            }

            UHCRegister.getStatsUtil().addWin(Bukkit.getPlayer(getWinnerName()));

            if (UHCRegister.getStatsUtil().getWins(Bukkit.getPlayer(getWinnerName())) == 50) {
                if (!UHCRegister.getStatsUtil().hasAchievement(UHCAchievements.WINNER, Bukkit.getPlayer(getWinnerName()))) {
                    UHCRegister.getStatsUtil().addAchievement(UHCAchievements.WINNER, Bukkit.getPlayer(getWinnerName()));
                    Bukkit.getPlayer(getWinnerName()).sendMessage(getUhc().getPrefix() + UHCFileRegister.getMessageFile().getAchievementUnlocked().replace("[achievement]", UHCAchievements.WINNER.getName()));
                }
            }

            UHCRegister.getStatsUtil().addPoints(Bukkit.getPlayer(getWinnerName()),
                    UHCFileRegister.getOptionsFile().getPointsOnWin());

            if (!UHCFileRegister.getOptionsFile().getCommandOnWin().equals(""))
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), UHCFileRegister.getOptionsFile()
                        .getCommandOnWin().replace("[player]", getWinnerName()));

            UHCRegister.getRestartTimer().startEndTimer();
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        boolean isSpec = UHCRegister.getPlayerUtil().isDead(p);
        UHCRegister.getTablistUtil().sendTablist();

        if (!(GState.isState(GState.LOBBY) || GState.isState(GState.END))) {
            if (!isSpec) {
                if (!(UHCRegister.getPlayerUtil().getSurvivors().size() <= 2)) {
                    Villager villager = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);

                    villager.setCustomNameVisible(true);
                    villager.setCustomName(p.getName());

                    villager.getEquipment().setArmorContents(p.getInventory().getArmorContents());
                    villager.setHealth(p.getHealth());

                    try {
                        if (MinecraftVersion.getServer() != MinecraftVersion.EIGHT)
                            Class.forName("org.bukkit.entity.LivingEntity").getMethod("setAI", boolean.class).invoke(villager, false);
                        else {
                            villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    playerLogOut.put(p.getName(), p.getLocation());
                    playerDummies.put(p.getName(), villager);
                    playerInv.put(p.getName(), p.getInventory());
                }
            }

            for (String other : UHCRegister.getPlayerUtil().getAll()) {
                if (Bukkit.getPlayer(other) == null)
                    continue;
                UHCRegister.getScoreboardUtil().updateAlive(Bukkit.getPlayer(other));
                UHCRegister.getScoreboardUtil().updateSpecs(Bukkit.getPlayer(other));
            }

            if (UHCRegister.getPlayerUtil().getSurvivors().size() <= 1) {
                GState.setCurrentState(GState.END);

                for (String other : UHCRegister.getPlayerUtil().getSurvivors()) {
                    if (Bukkit.getPlayer(other) == null)
                        continue;
                    UHCRegister.getPlayerUtil().removeAll(Bukkit.getPlayer(other));
                }

                if (UHCRegister.getPlayerUtil().getSurvivors().size() == 0) {

                    Bukkit.reload();
                    return;
                }

                setWinnerName(UHCRegister.getPlayerUtil().getSurvivors().get(0));
                for (String pName : UHCRegister.getPlayerUtil().getAll()) {
                    Bukkit.getPlayer(pName).sendMessage(getUhc().getPrefix() + UHCFileRegister.getMessageFile()
                            .getWin(Bukkit.getPlayer(getWinnerName())));
                    SimpleTitle
                            .sendTitle(Bukkit.getPlayer(pName), " ",
                                    getUhc().getPrefix() + UHCFileRegister.getMessageFile()
                                            .getWin(Bukkit.getPlayer(getWinnerName())),
                                    2, 2, 2);
                }

                UHCRegister.getStatsUtil().addPoints(Bukkit.getPlayer(getWinnerName()),
                        UHCFileRegister.getOptionsFile().getPointsOnWin());

                if (!UHCFileRegister.getOptionsFile().getCommandOnWin().equals(""))
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), UHCFileRegister.getOptionsFile()
                            .getCommandOnWin().replace("[player]", getWinnerName()));

                UHCRegister.getRestartTimer().startEndTimer();
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

    public Location getLogOutLocation(Player p) {
        if (playerLogOut.containsKey(p.getName()))
            return playerLogOut.get(p.getName());
        return p.getWorld().getSpawnLocation();
    }

    public Villager getPlayerDummie(Player p) {
        if (playerDummies.containsKey(p.getName()))
            return playerDummies.get(p.getName());
        return null;
    }

    public PlayerInventory getPlayerInv(Player p) {
        if (playerInv.containsKey(p.getName()))
            return playerInv.get(p.getName());
        return p.getInventory();
    }
}
