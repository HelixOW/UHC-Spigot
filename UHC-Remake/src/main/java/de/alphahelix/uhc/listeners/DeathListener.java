package de.alphahelix.uhc.listeners;

import de.alphahelix.alphalibary.nms.SimpleTitle;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeathListener extends SimpleListener {

    private List<ItemStack> dropList = new ArrayList<>();

    public DeathListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(final EntityDeathEvent e) {
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END))
            return;

        double random = Math.random();

        if (e.getEntity() instanceof Villager && e.getEntity().isCustomNameVisible()) {
            e.getDrops().clear();
            UHCRegister.getPlayerUtil().removeSurvivor(e.getEntity().getCustomName());
            UHCRegister.getPlayerUtil().addDead(e.getEntity().getCustomName());

            for (String other : UHCRegister.getPlayerUtil().getAll()) {
                if (Bukkit.getPlayer(other) == null)
                    return;
                Bukkit.getPlayer(other)
                        .sendMessage(UHCFileRegister.getDeathmessageFile()
                                .getMessage(e.getEntity().getLastDamageCause().getCause())
                                .replace("[player]", e.getEntity().getCustomName())
                                .replace("[entity]", (e.getEntity().getKiller() == null
                                        ? UHCFileRegister.getDeathmessageFile().getIsAMob()
                                        : e.getEntity().getKiller().getName())));
            }

            for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Player")) {
                if (UHCFileRegister.getDropsFile().getBoolean("Deathchest")) {
                    e.getEntity().getLocation().getBlock().setType(Material.CHEST);
                    Chest c = (Chest) e.getEntity().getLocation().getBlock().getState();

                    if (random < UHCFileRegister.getDropsFile().getChance("Player", drops))
                        c.getBlockInventory().addItem(drops);
                } else {
                    if (random < UHCFileRegister.getDropsFile().getChance("Player", drops))
                        dropList.add(drops);
                }
            }

            if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player) {
                UHCRegister.getStatsUtil().addKill(e.getEntity().getKiller());
                UHCRegister.getStatsUtil().addPoints(e.getEntity().getKiller(),
                        UHCFileRegister.getOptionsFile().getPointsOnKill());
                UHCRegister.getStatsUtil().addCoins(e.getEntity().getKiller(),
                        UHCFileRegister.getOptionsFile().getCoinssOnKill());

                if (getUhc().isCrates()) {
                    Crate c = UHCFileRegister.getCrateFile().getRandomCrate();

                    if (Math.random() <= c.getRarity()) {
                        UHCRegister.getStatsUtil().addCrate(c, e.getEntity().getKiller());

                        if (e.getEntity().getKiller().isOnline())
                            e.getEntity().getKiller().sendMessage(getUhc().getPrefix() + UHCFileRegister.getMessageFile().getCrateDropped(c));
                    }
                }
            }

            UHCRegister.getStatsUtil()
                    .addDeath(Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName())));
            UHCRegister.getStatsUtil().removePoints(
                    Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName())),
                    UHCFileRegister.getOptionsFile().getPointsOnDeath());
            UHCRegister.getStatsUtil().addCoins(
                    Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName())),
                    UHCFileRegister.getOptionsFile().getCoinsOnDeath());

            if (!UHCFileRegister.getOptionsFile().getCommandOnKill().equals(""))
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), UHCFileRegister.getOptionsFile()
                        .getCommandOnKill().replace("[player]", e.getEntity().getCustomName())
                        .replace("[killer]",
                                (e.getEntity().getKiller() == null ? "" : e.getEntity().getKiller().getName())));
            if (!UHCFileRegister.getOptionsFile().getCommandOnDeath().equals(""))
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), UHCFileRegister.getOptionsFile()
                        .getCommandOnDeath().replace("[player]", e.getEntity().getCustomName())
                        .replace("[killer]",
                                (e.getEntity().getKiller() == null ? "" : e.getEntity().getKiller().getName())));

            if (UHCRegister.getPlayerUtil().getSurvivors().size() <= 1) {

                GState.setCurrentState(GState.END);

                if (UHCRegister.getPlayerUtil().getSurvivors().size() == 0) {

                    UHCRegister.getRestartTimer().startEndTimer();
                    return;
                }

                UHCRegister.getGameEndsListener().setWinnerName(UHCRegister.getPlayerUtil().getSurvivors().get(0));
                for (String pName : UHCRegister.getPlayerUtil().getAll()) {
                    Bukkit.getPlayer(pName)
                            .sendMessage(getUhc().getPrefix()
                                    + UHCFileRegister.getMessageFile().getWin(Bukkit.getPlayer(UHCRegister.getGameEndsListener().getWinnerName())));

                    SimpleTitle.sendTitle(Bukkit.getPlayer(pName), " ",
                            getUhc().getPrefix()
                                    + UHCFileRegister.getMessageFile().getWin(Bukkit.getPlayer(UHCRegister.getGameEndsListener().getWinnerName())),
                            2, 2, 2);
                }

                UHCRegister.getStatsUtil().addPoints(
                        Bukkit.getPlayer(UHCRegister.getGameEndsListener().getWinnerName()),
                        UHCFileRegister.getOptionsFile().getPointsOnWin());

                if (!UHCFileRegister.getOptionsFile().getCommandOnWin().equals(""))
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            UHCFileRegister.getOptionsFile().getCommandOnWin().replace("[player]",
                                    UHCRegister.getGameEndsListener().getWinnerName()));

                UHCRegister.getRestartTimer().startEndTimer();
            }

        } else {
            if (e.getEntity() instanceof Player) {
                for (String other : UHCRegister.getPlayerUtil().getAll()) {
                    if (Bukkit.getPlayer(other) == null)
                        return;
                    Bukkit.getPlayer(other)
                            .sendMessage(UHCFileRegister.getDeathmessageFile()
                                    .getMessage(e.getEntity().getLastDamageCause() == null ? DamageCause.SUICIDE
                                            : e.getEntity().getLastDamageCause().getCause())
                                    .replace("[player]", e.getEntity().getName())
                                    .replace("[entity]", (e.getEntity().getKiller() == null
                                            ? UHCFileRegister.getDeathmessageFile().getColorString("[entity] is a mob")
                                            : e.getEntity().getKiller().getName())));
                }
            } else if (e.getEntity() instanceof Pig) {
                e.getDrops().clear();
                for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Pig")) {
                    if (random < UHCFileRegister.getDropsFile().getChance("Pig", drops))
                        dropList.add(drops);
                }
            } else if (e.getEntity() instanceof Zombie) {
                e.getDrops().clear();
                for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Zombie")) {
                    if (random < UHCFileRegister.getDropsFile().getChance("Zombie", drops))
                        dropList.add(drops);
                }
            } else if (e.getEntity() instanceof Cow) {
                e.getDrops().clear();
                for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Cow")) {
                    if (random < UHCFileRegister.getDropsFile().getChance("Cow", drops))
                        dropList.add(drops);
                }
            } else if (e.getEntity() instanceof Chicken) {
                e.getDrops().clear();
                for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Chicken")) {
                    if (random < UHCFileRegister.getDropsFile().getChance("Chicken", drops))
                        dropList.add(drops);
                }
            } else if (e.getEntity() instanceof Spider) {
                e.getDrops().clear();
                for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Spider")) {
                    if (random < UHCFileRegister.getDropsFile().getChance("Spider", drops))
                        dropList.add(drops);
                }
            } else if (e.getEntity() instanceof Skeleton) {
                e.getDrops().clear();
                for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Skeleton")) {
                    if (random < UHCFileRegister.getDropsFile().getChance("Skeleton", drops))
                        dropList.add(drops);
                }
            } else if (e.getEntity() instanceof Sheep) {
                e.getDrops().clear();
                for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Sheep")) {
                    if (random < UHCFileRegister.getDropsFile().getChance("Sheep", drops))
                        dropList.add(drops);
                }
            } else if (e.getEntity() instanceof Rabbit) {
                e.getDrops().clear();
                for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Rabbit")) {
                    if (random < UHCFileRegister.getDropsFile().getChance("Rabbit", drops))
                        dropList.add(drops);
                }
            } else if (e.getEntity() instanceof Horse) {
                e.getDrops().clear();
                for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Horse")) {
                    if (random < UHCFileRegister.getDropsFile().getChance("Horse", drops))
                        dropList.add(drops);
                }
            } else if (e.getEntity() instanceof Creeper) {
                e.getDrops().clear();
                for (final ItemStack drops : UHCFileRegister.getDropsFile().getDrop("Creeper")) {
                    if (random < UHCFileRegister.getDropsFile().getChance("Creeper", drops))
                        dropList.add(drops);
                }
            }
        }

        // Bald Chicken

        if (e.getEntity() instanceof Chicken) {
            if (scenarioCheck(Scenarios.BALD_CHICKEN)) {
                ItemStack tr = null;
                for (ItemStack drop : dropList) {
                    if (drop.getType().equals(Material.FEATHER))
                        tr = drop;
                }
                if (tr != null)
                    dropList.remove(tr);
            }
        } else if (e.getEntity() instanceof Skeleton) {
            if (scenarioCheck(Scenarios.BALD_CHICKEN)) {
                ItemStack tr = null;
                for (ItemStack drop : dropList) {
                    if (drop.getType().equals(Material.ARROW))
                        tr = drop;
                }
                if (tr != null)
                    dropList.remove(tr);
                dropList.add(new ItemStack(Material.ARROW, new Random(4).nextInt(8)));
            }
        }

        // BareBones

        if (e.getEntity() instanceof Villager && e.getEntity().isCustomNameVisible()) {
            if (scenarioCheck(Scenarios.BAREBONES)) {
                dropList = new ArrayList<>();

                dropList.add(new ItemStack(Material.DIAMOND, 1));
                dropList.add(new ItemStack(Material.GOLDEN_APPLE, 1));
                dropList.add(new ItemStack(Material.ARROW, 32));
                dropList.add(new ItemStack(Material.STRING, 2));
            }
        }

        // Beta Zombie
        if (e.getEntity() instanceof Zombie)
            if (scenarioCheck(Scenarios.BETA_ZOMBIE))
                dropList.add(new ItemStack(Material.FEATHER));

        // Bombers
        if (scenarioCheck(Scenarios.BOMBERS))
            dropList.add(new ItemStack(Material.TNT));

        // Golden Fleece
        if (e.getEntity() instanceof Sheep) {
            if (scenarioCheck(Scenarios.GOLDEN_FLEECE)) {
                if (random < 0.6) {
                    dropList.add(makeArray(new ItemStack(Material.IRON_INGOT), new ItemStack(Material.GOLD_INGOT),
                            new ItemStack(Material.DIAMOND))[new Random().nextInt(
                            makeArray(new ItemStack(Material.IRON_INGOT), new ItemStack(Material.GOLD_INGOT),
                                    new ItemStack(Material.DIAMOND)).length)]);
                }
            }
        }

        // Sheep Lovers
        if (e.getEntity() instanceof Sheep) {
            if (scenarioCheck(Scenarios.SHEEP_LOVERS)) {
                dropList.clear();
                dropList.add(new ItemStack(Material.RAW_BEEF, UHCFileRegister.getDropsFile().getRandomInteger(1, 3)));
            }
        }

        for (ItemStack is : dropList) {
            e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), is);
        }
    }
}
