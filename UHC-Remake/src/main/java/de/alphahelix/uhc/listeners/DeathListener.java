package de.alphahelix.uhc.listeners;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.nms.SimpleTitle;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.StatsUtil;
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
import java.util.UUID;

public class DeathListener extends SimpleListener {

    private List<ItemStack> dropList = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(final EntityDeathEvent e) {
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END))
            return;

        double random = Math.random();

        if (e.getEntity() instanceof Villager && e.getEntity().isCustomNameVisible()) {
            e.getDrops().clear();
            PlayerUtil.removeSurvivor(e.getEntity().getCustomName());
            PlayerUtil.addDead(e.getEntity().getCustomName());

            for (String other : PlayerUtil.getAll()) {
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
                UUID id = UUIDFetcher.getUUID(e.getEntity().getKiller());
                StatsUtil.addKill(id);
                StatsUtil.addPoints(id,
                        UHCFileRegister.getOptionsFile().getPointsOnKill());
                StatsUtil.addCoins(id,
                        UHCFileRegister.getOptionsFile().getCoinssOnKill());

                if (UHC.isCrates()) {
                    Crate c = UHCFileRegister.getCrateFile().getRandomCrate();

                    if (Math.random() <= c.getRarity()) {
                        StatsUtil.addCrate(id, c);

                        if (e.getEntity().getKiller().isOnline())
                            e.getEntity().getKiller().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getCrateDropped(c));
                    }
                }
            }

            StatsUtil.addDeath(
                    UUIDFetcher.getUUID(e.getEntity().getCustomName()));

            StatsUtil.removePoints(
                    UUIDFetcher.getUUID(e.getEntity().getCustomName()),
                    UHCFileRegister.getOptionsFile().getPointsOnDeath());

            StatsUtil.removeCoins(
                    UUIDFetcher.getUUID(e.getEntity().getCustomName()),
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

            if (PlayerUtil.getSurvivors().size() <= 1) {

                GState.setCurrentState(GState.END);

                if (PlayerUtil.getSurvivors().size() == 0) {

                    UHCRegister.getRestartTimer().startTimer();
                    return;
                }

                UHCRegister.getGameEndsListener().setWinnerName(PlayerUtil.getSurvivors().get(0));
                for (String pName : PlayerUtil.getAll()) {
                    Bukkit.getPlayer(pName)
                            .sendMessage(UHC.getPrefix()
                                    + UHCFileRegister.getMessageFile().getWin(Bukkit.getPlayer(UHCRegister.getGameEndsListener().getWinnerName())));

                    SimpleTitle.sendTitle(Bukkit.getPlayer(pName), " ",
                            UHC.getPrefix()
                                    + UHCFileRegister.getMessageFile().getWin(Bukkit.getPlayer(UHCRegister.getGameEndsListener().getWinnerName())),
                            2, 2, 2);
                }

                StatsUtil.addPoints(
                        UUIDFetcher.getUUID(UHCRegister.getGameEndsListener().getWinnerName()),
                        UHCFileRegister.getOptionsFile().getPointsOnWin());

                if (!UHCFileRegister.getOptionsFile().getCommandOnWin().equals(""))
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            UHCFileRegister.getOptionsFile().getCommandOnWin().replace("[player]",
                                    UHCRegister.getGameEndsListener().getWinnerName()));

                UHCRegister.getRestartTimer().startTimer();
            }

        } else {
            if (e.getEntity() instanceof Player) {
                for (String other : PlayerUtil.getAll()) {
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
            if (Scenarios.isPlayedAndEnabled(Scenarios.BALD_CHICKEN)) {
                ItemStack tr = null;
                for (ItemStack drop : dropList) {
                    if (drop.getType().equals(Material.FEATHER))
                        tr = drop;
                }
                if (tr != null)
                    dropList.remove(tr);
            }
        } else if (e.getEntity() instanceof Skeleton) {
            if (Scenarios.isPlayedAndEnabled(Scenarios.BALD_CHICKEN)) {
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
            if (Scenarios.isPlayedAndEnabled(Scenarios.BAREBONES)) {
                dropList = new ArrayList<>();

                dropList.add(new ItemStack(Material.DIAMOND, 1));
                dropList.add(new ItemStack(Material.GOLDEN_APPLE, 1));
                dropList.add(new ItemStack(Material.ARROW, 32));
                dropList.add(new ItemStack(Material.STRING, 2));
            }
        }

        // Beta Zombie
        if (e.getEntity() instanceof Zombie)
            if (Scenarios.isPlayedAndEnabled(Scenarios.BETA_ZOMBIE))
                dropList.add(new ItemStack(Material.FEATHER));

        // Bombers
        if (Scenarios.isPlayedAndEnabled(Scenarios.BOMBERS))
            dropList.add(new ItemStack(Material.TNT));

        // Golden Fleece
        if (e.getEntity() instanceof Sheep) {
            if (Scenarios.isPlayedAndEnabled(Scenarios.GOLDEN_FLEECE)) {
                if (random < 0.6) {
                    dropList.add(Util.makeArray(new ItemStack(Material.IRON_INGOT), new ItemStack(Material.GOLD_INGOT),
                            new ItemStack(Material.DIAMOND))[new Random().nextInt(
                            Util.makeArray(new ItemStack(Material.IRON_INGOT), new ItemStack(Material.GOLD_INGOT),
                                    new ItemStack(Material.DIAMOND)).length)]);
                }
            }
        }

        // Sheep Lovers
        if (e.getEntity() instanceof Sheep) {
            if (Scenarios.isPlayedAndEnabled(Scenarios.SHEEP_LOVERS)) {
                dropList.clear();
                dropList.add(new ItemStack(Material.RAW_BEEF, UHCFileRegister.getDropsFile().getRandomInteger(1, 3)));
            }
        }

        for (ItemStack is : dropList) {
            e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), is);
        }
    }
}
