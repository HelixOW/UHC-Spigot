package de.alphahelix.uhc.listeners;

import de.alphahelix.alphalibary.UUID.UUIDFetcher;
import de.alphahelix.alphalibary.utils.MinecraftVersion;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.instances.Spectator;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class EquipListener extends SimpleListener {

    public EquipListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent e) {
        UUIDFetcher.getUUID(e.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        final Player p = e.getPlayer();

        e.setJoinMessage(null);

        UHCRegister.getPlayerUtil().clearUp(p);
        UHCRegister.getPlayerUtil().addAll(p);

        UHCFileRegister.getRanksFile().initRank(p);

        UHCRegister.getTablistUtil().sortTablist(p);

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
            if (!UHCRegister.getPlayerUtil().isSurivor(p)) {
                new Spectator(p, UHCFileRegister.getLocationsFile().getArena());
                UHCRegister.getScoreboardUtil().setIngameScoreboard(p);

                for (String other : UHCRegister.getPlayerUtil().getAll()) {
                    if (Bukkit.getPlayer(other) == null)
                        continue;
                    UHCRegister.getScoreboardUtil().setIngameScoreboard(Bukkit.getPlayer(other));
                }
                return;
            }

            UHCRegister.getGameEndsListener().getPlayerDummie(p).remove();
            p.teleport(UHCRegister.getGameEndsListener().getLogOutLocation(p));

            UHCRegister.getTablistUtil().sendTablist();
            UHCRegister.getScoreboardUtil().setIngameScoreboard(p);

            for (ItemStack content : UHCRegister.getGameEndsListener().getPlayerInv(p).getContents()) {
                if (content == null)
                    continue;
                p.getInventory().addItem(content);
            }

            return;
        }

        for (String other : UHCRegister.getPlayerUtil().getAll()) {
            if (Bukkit.getPlayer(other) == null)
                continue;
            Bukkit.getPlayer(other).sendMessage(getUhc().getPrefix() + UHCFileRegister.getMessageFile()
                    .getJoin().replace("[player]", p.getDisplayName()));
        }

        UHCRegister.getScoreboardUtil().setLobbyScoreboard(p);

        new BukkitRunnable() {
            @Override
            public void run() {
                UHCRegister.getNpcUtil().prepareNPC(UHCFileRegister.getLocationsFile().getStatsNPCLocation(), p, p);
            }
        }.runTaskLaterAsynchronously(getUhc(), 20);

        p.getInventory().setItem(UHCFileRegister.getAchievementFile().getItem().getSlot(), UHCFileRegister.getAchievementFile().getItem().getItemStack());

        if (getUhc().isScenarios()) {
            if (getUhc().isScenarioVoting())
                p.getInventory().setItem(UHCFileRegister.getScenarioFile().getItem(null).getSlot(),
                        UHCFileRegister.getScenarioFile().getItem(null).getItemStack());
            else
                p.getInventory().setItem(UHCFileRegister.getScenarioFile().getItem(null).getSlot(),
                        UHCFileRegister.getScenarioFile().getItem(Scenarios.getScenario()).getItemStack());

        } else if (getUhc().isKits()) {
            p.getInventory().setItem(UHCFileRegister.getKitsFile().getItem().getSlot(),
                    UHCFileRegister.getKitsFile().getItem().getItemStack());
        }

        if (getUhc().isTeams()) {
            p.getInventory().setItem(UHCFileRegister.getTeamFile().getItem().getSlot(),
                    UHCFileRegister.getTeamFile().getItem().getItemStack());
        }

        if (getUhc().isCrates()) {
            p.getInventory()
                    .setItem(UHCFileRegister.getCrateFile().getItem().getSlot(),
                            UHCFileRegister.getCrateFile().getItem().getItemStack());
        }

        if (getUhc().isLobby()) {
            p.getInventory().setItem(UHCFileRegister.getLobbyFile().getItem().getSlot(),
                    UHCFileRegister.getLobbyFile().getItem().getItemStack());
        }

    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        e.setQuitMessage(null);

        if (!UHCRegister.getPlayerUtil().isDead(p)) {
            for (String other : UHCRegister.getPlayerUtil().getAll()) {
                if (Bukkit.getPlayer(other) == null)
                    continue;
                Bukkit.getPlayer(other).sendMessage(getUhc().getPrefix() +
                        UHCFileRegister.getMessageFile().getLeave().replace("[player]", p.getDisplayName()));
            }
        }

        UHCRegister.getStatsUtil().pushInformations(p);

        if (GState.isState(GState.LOBBY) || GState.isState(GState.END))
            UHCRegister.getPlayerUtil().removeAll(p);
    }
}
