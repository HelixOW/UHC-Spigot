package de.alphahelix.uhc.listeners;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.TeamManagerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TeamListener extends SimpleListener {

    private static boolean isFFA = false;

    public void setFFA() {
        isFFA = true;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!GState.isState(GState.LOBBY))
            return;

        if (p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType()
                .equals(TeamManagerUtil.getTeamItem().getType())) {
            UHCRegister.getTeamInventory().openInventory(p);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (GState.isState(GState.LOBBY))
            return;
        if (e.getEntity() instanceof Villager && e.getEntity().isCustomNameVisible()
                && e.getDamager() instanceof Player) {
            if (TeamManagerUtil.isSameTeam(
                    (Player) Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName())),
                    (Player) e.getDamager()))
                if (PlayerUtil.getSurvivors().size() > TeamManagerUtil
                        .isInOneTeam(
                                (Player) Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName())))
                        .getSize())
                    if (!isFFA)
                        e.setCancelled(true);
        } else if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            if (TeamManagerUtil.isSameTeam((Player) e.getEntity(), (Player) e.getDamager()))
                if (PlayerUtil.getSurvivors().size() > TeamManagerUtil
                        .isInOneTeam((Player) e.getEntity()).getSize())
                    if (!isFFA)
                        e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (e.getPlayer() == null)
            return;
        if (!GState.isState(GState.LOBBY)) return;
        if (TeamManagerUtil.isInOneTeam(e.getPlayer()) == null)
            return;
        if (!TeamManagerUtil
                .existTeam(TeamManagerUtil.isInOneTeam(e.getPlayer())))
            return;
        TeamManagerUtil.isInOneTeam(e.getPlayer()).removeTeam(e.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Material m = TeamManagerUtil.getIconMaterial();

        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() == null)
            return;
        if (!e.getClickedInventory().getTitle().contains(UHCFileRegister.getTeamFile().getInventoryName()))
            return;

        e.setCancelled(true);
        if (e.getCurrentItem().getType().equals(m)) {
            if (TeamManagerUtil
                    .existTeam(TeamManagerUtil.getTeamByIcon(e.getCurrentItem(), m))) {
                UHCTeam team = TeamManagerUtil.getTeamByIcon(e.getCurrentItem(), m);

                if (team == null)
                    return;

                UHCTeam is = TeamManagerUtil.isInOneTeam(p);

                if (is != null)
                    is.removeTeam(p);

                team.addToTeam(p);
                p.closeInventory();
            }
        }
    }

    @EventHandler
    public void onArmorStandDestroy(EntityDamageByEntityEvent e) {
        if (!GState.isState(GState.LOBBY))
            return;
        if (e.getEntity() instanceof ArmorStand)
            e.setCancelled(true);
    }

    @EventHandler
    public void onTeamJoin(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof ArmorStand))
            return;
        if (!(GState.isState(GState.LOBBY)))
            return;
        if (!(e.getRightClicked().isCustomNameVisible()))
            return;
        e.setCancelled(true);

        if (TeamManagerUtil
                .getTeamByName(ChatColor.stripColor(e.getRightClicked().getCustomName())) == null)
            return;

        TeamManagerUtil.getTeamByName(ChatColor.stripColor(e.getRightClicked().getCustomName()))
                .addToTeam(e.getPlayer());
    }
}
