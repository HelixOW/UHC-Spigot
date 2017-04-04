package de.alphahelix.uhc.timers;

import de.alphahelix.alphaapi.nms.SimpleActionBar;
import de.alphahelix.alphaapi.nms.SimpleTitle;
import de.alphahelix.alphaapi.utils.Cuboid;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.*;
import de.alphahelix.uhc.util.schematic.SchematicManagerUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class LobbyTimer extends AbstractTimer {

    private static boolean isAlreadyPasted;

    @Override
    public void startTimer() {
        if (!GState.isState(GState.LOBBY)) return;
        if (isRunning()) return;

        stopTimer();

        if (!isAlreadyPasted) {
            isAlreadyPasted = true;
            SchematicManagerUtil.load(UHCFileRegister.getOptionsFile().getLobbyFileName());
            SchematicManagerUtil.paste(Bukkit.getWorld("UHC").getSpawnLocation().add(0, 140, 0));
        }

        setSecondTimer(() -> {
            if (PlayerUtil.getAll().size() < PlayerUtil.getMinimumPlayerCount()) {
                Bukkit.broadcastMessage(UHC.getPrefix()
                        + UHCFileRegister.getMessageFile().getNotEnoughPlayers());
                stopTimer();
                return;
            }

            if (getSeconds() > 0) {
                setSeconds(getSeconds() - 1);

                if (getSeconds() == 0) {
                    Bukkit.getPluginManager().callEvent(new LobbyEndEvent());
                    if (UHC.isLobbyAsSchematic()) {
                        World w = Bukkit.getWorld("UHC").getSpawnLocation().getWorld();
                        Location l1 = new Location(w, -75, 155, -75);
                        Location l2 = new Location(w, 75, 255, 75);

                        for (Block b : new Cuboid(l1, l2).getBlocks()) {
                            if (b.getType().equals(Material.AIR))
                                continue;
                            b.setType(Material.AIR);
                        }
                    }

                    Bukkit.getWorld("UHC").setGameRuleValue("naturalRegenration", "false");

                    BorderUtil.setArena(Bukkit.getWorld("UHC").getSpawnLocation());

                    BorderUtil.createBorder();
                    BorderUtil.setMovingListener();
                }

                if (getSeconds() == 10) {
                    if (UHC.isScenarioVoting())
                        Scenarios.setPlayedScenario(
                                UHCRegister.getScenarioInventory().getScenarioWithMostVotes());
                }

                for (Player p : Util.makePlayerArray(PlayerUtil.getAll())) {
                    UUID id = UUIDFetcher.getUUID(p);
                    String time = (getMinTime() >= 1 ? UHCFileRegister.getMessageFile().getTimeLeftInfo(Util.round(getMinTime(), 1), UHCFileRegister.getUnitFile().getMinutes()) : UHCFileRegister.getMessageFile().getTimeLeftInfo(Util.round(getSeconds(), 1), UHCFileRegister.getUnitFile().getSeconds()));

                    p.setLevel((int) getSeconds());

                    if (getSeconds() % 60 == 0 && getSeconds() > 10 && getSeconds() != 0) {
                        p.sendMessage(UHC.getPrefix() + time);
                        SimpleTitle.sendTitle(p,
                                "",
                                UHC.getPrefix() + time,
                                1,
                                2,
                                1);
                        p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                    }

                    if (getSeconds() % 30 == 0 && !(getSeconds() % 60 == 0)) {
                        p.sendMessage(UHC.getPrefix() + time);
                        SimpleTitle.sendTitle(p,
                                "",
                                UHC.getPrefix() + time,
                                1,
                                2,
                                1);
                        p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                    }

                    if (getSeconds() == 10) {
                        if (UHC.isScenarios()) {
                            ScoreboardUtil.updateScenario(p, Scenarios.getScenario());
                            p.getInventory().setItem(
                                    UHCFileRegister.getScenarioFile().getItem(null).getSlot(),
                                    UHCFileRegister.getScenarioFile().getItem(Scenarios.getScenario()).getItemStack());
                        }
                    }

                    if (getSeconds() < 10 && getSeconds() != 0) {
                        p.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(getSeconds(), UHCFileRegister.getUnitFile().getSeconds()));
                        SimpleActionBar.send(p, UHC.getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(getSeconds(), UHCFileRegister.getUnitFile().getSeconds()));
                        p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                    }

                    if (getSeconds() == 0) {
                        p.getInventory().clear();

                        Location worldSpawn = Bukkit.getWorld("UHC").getSpawnLocation();
                        Location playerSpawn = worldSpawn.getWorld()
                                .getHighestBlockAt(UHC.getRandomLocation(worldSpawn,
                                        worldSpawn.getBlockX() - UHC.getSpawnradius(),
                                        worldSpawn.getBlockX() + UHC.getSpawnradius(),
                                        worldSpawn.getBlockZ() - UHC.getSpawnradius(),
                                        worldSpawn.getBlockZ() + UHC.getSpawnradius()))
                                .getLocation();

                        p.teleport(playerSpawn);

                        p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1F, 0F);

                        p.setGameMode(GameMode.SURVIVAL);

                        PlayerUtil.addSurvivor(p);

                        StatsUtil.addGames(id, 1);

                        ScoreboardUtil.setIngameScoreboard(p);

                        if (UHC.isKits()) {
                            if (UHCFileRegister.getKitsFile().hasKit(p)) {
                                for (ItemStack is : UHCFileRegister.getKitsFile().getPlayedKit(p).getItems()) {
                                    if (is != null) {
                                        p.getInventory().addItem(is);
                                    }
                                }
                            }
                        }
                    }
                }

                if (getSeconds() == 0) {
                    stopTimer();
                    GState.setCurrentState(GState.PERIOD_OF_PEACE);

                    UHCRegister.getGraceTimer().startTimer();

                    TablistUtil.sendTablist();

                    for (Player p : Util.makePlayerArray(PlayerUtil.getAll())) {
                        ScoreboardUtil.setIngameScoreboard(p);
                        p.setLevel(0);
                    }

                    if (PlayerUtil.getAll().size() <= 1) {
                        GState.setCurrentState(GState.END);
                        UHCRegister.getRestartTimer().startTimer();
                    }
                }
            }
        });
    }
}
