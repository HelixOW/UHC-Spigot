package de.alphahelix.uhc.listeners;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.instances.Spectator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class EquipListener extends SimpleListener {

    public EquipListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        final Player p = e.getPlayer();

        e.setJoinMessage(null);

        getRegister().getPlayerUtil().clearUp(p);
        getRegister().getPlayerUtil().addAll(p);

        getRegister().getRanksFile().initRank(p);
        getRegister().getTablistUtil().sortTablist(p);

        if (getRegister().getMainOptionsFile().getBoolean("Remove Attack Cooldown"))
            p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(32);

        if (!GState.isState(GState.LOBBY)) {
            if (!getRegister().getPlayerUtil().isSurivor(p)) {
                new Spectator(p, getRegister().getLocationsFile().getArena());
                getRegister().getScoreboardUtil().setIngameScoreboard(p);

                for (String other : getRegister().getPlayerUtil().getAll()) {
                    if (Bukkit.getPlayer(other) == null)
                        continue;
                    getRegister().getScoreboardUtil().setIngameScoreboard(Bukkit.getPlayer(other));
                }
                return;
            }

            getRegister().getGameEndsListener().getPlayerDummie(p).remove();
            p.teleport(getRegister().getGameEndsListener().getLogOutLocation(p));

            getRegister().getTablistUtil().sendTablist();
            getRegister().getScoreboardUtil().setIngameScoreboard(p);

            for (ItemStack content : getRegister().getGameEndsListener().getPlayerInv(p).getContents()) {
                if (content == null)
                    continue;
                p.getInventory().addItem(content);
            }

            return;
        }

        for (String other : getRegister().getPlayerUtil().getAll()) {
            if (Bukkit.getPlayer(other) == null)
                continue;
            Bukkit.getPlayer(other).sendMessage(getUhc().getPrefix() + getRegister().getMessageFile()
                    .getColorString("Player has joined").replace("[player]", p.getDisplayName()));
        }

        getRegister().getScoreboardUtil().setLobbyScoreboard(p);

        new BukkitRunnable() {
            @Override
            public void run() {
                getRegister().getHologramUtil().showHologram(p);
                getRegister().getNpcUtil().prepareNPC(getRegister().getLocationsFile().getStatsNPCLocation(), p, p);
            }
        }.runTaskLaterAsynchronously(getUhc(), 20);

        p.getInventory().setItem(getRegister().getAchievementFile().getInt("Item.Slot"), getRegister().getAchievementFile().getItem());

        if (getUhc().isScenarios()) {
            if (getUhc().isScenarioVoting())
                p.getInventory().setItem(getRegister().getScenarioFile().getInt("Scenarios Item Slot"),
                        new ItemBuilder(Material.getMaterial(getRegister().getScenarioFile().getString("Scenarios Item")
                                .replace(" ", "_").toUpperCase()))
                                .setName(
                                        getRegister().getScenarioFile().getColorString("Scenarios Item Name"))
                                .setLore(getRegister().getScenarioHelpFile()
                                        .getScenarioDescription(Scenarios.NONE))
                                .build());
            else
                p.getInventory().setItem(getRegister().getScenarioFile().getInt("Scenarios Item Slot"),
                        new ItemBuilder(Material.getMaterial(getRegister().getScenarioFile().getString("Scenarios Item")
                                .replace(" ", "_").toUpperCase()))
                                .setName(
                                        getRegister().getScenarioFile().getColorString("Scenarios Item Name")
                                                .replace("-",
                                                        getRegister().getScenarioFile()
                                                                .getCustomScenarioName(Scenarios.getScenario())))
                                .setLore(getRegister().getScenarioHelpFile()
                                        .getScenarioDescription(Scenarios.getScenario()))
                                .build());
        } else if (getUhc().isKits()) {
            p.getInventory().setItem(getRegister().getKitsFile().getInt("Kit.Item Slot"),
                    new ItemBuilder(Material.getMaterial(
                            getRegister().getKitsFile().getString("Kit.Item").replace(" ", "_").toUpperCase()))
                            .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                            .setName(getRegister().getKitsFile().getColorString("Kit.Item Name")).build());
        }

        if (getUhc().isTeams()) {
            p.getInventory().setItem(getRegister().getTeamFile().getInt("Team.Item Slot"),
                    new ItemBuilder(Material.getMaterial(
                            getRegister().getTeamFile().getString("Team.Item").replace(" ", "_").toUpperCase()))
                            .setName(getRegister().getTeamFile().getColorString("Team.Item Name")).build());
        }

        if (getUhc().isCrates()) {
            p.getInventory()
                    .setItem(getRegister().getUhcCrateFile().getInt("Crate.Item slot"),
                            new ItemBuilder(Material.getMaterial(getRegister().getUhcCrateFile().getString("Crate.Item")
                                    .replace(" ", "_").toUpperCase()))
                                    .setName(getRegister().getUhcCrateFile().getColorString("Crate.Item Name"))
                                    .build());
        }

        if (getUhc().isLobby()) {
            p.getInventory().setItem(getRegister().getLobbyFile().getInt("Item slot"),
                    new ItemBuilder(Material.getMaterial(
                            getRegister().getLobbyFile().getString("Item").replace(" ", "_").toUpperCase()))
                            .setName(getRegister().getLobbyFile().getColorString("Item name")).build());
        }

    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        e.setQuitMessage(null);

        if (!getRegister().getPlayerUtil().isDead(p)) {
            for (String other : getRegister().getPlayerUtil().getAll()) {
                if (Bukkit.getPlayer(other) == null)
                    continue;
                Bukkit.getPlayer(other).sendMessage(getUhc().getPrefix() + getRegister().getMessageFile()
                        .getColorString("Player has left").replace("[player]", p.getDisplayName()));
            }
        }

        getRegister().getStatsUtil().pushInformations(p);

        if (GState.isState(GState.LOBBY) || GState.isState(GState.END))
            getRegister().getPlayerUtil().removeAll(p);
    }
}
