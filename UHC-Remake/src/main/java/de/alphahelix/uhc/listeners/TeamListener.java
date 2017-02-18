package de.alphahelix.uhc.listeners;

import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
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

    public TeamListener(UHC uhc) {
        super(uhc);
    }

    public void setFFA() {
        isFFA = true;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!GState.isState(GState.LOBBY))
            return;

        if (p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType()
                .equals(UHCRegister.getTeamManagerUtil().getTeamItem())) {
            UHCRegister.getTeamInventory().openInventory(p);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (GState.isState(GState.LOBBY))
            return;
        if (e.getEntity() instanceof Villager && e.getEntity().isCustomNameVisible()
                && e.getDamager() instanceof Player) {
            if (UHCRegister.getTeamManagerUtil().isSameTeam(
                    (Player) Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName())),
                    (Player) e.getDamager()))
                if (UHCRegister.getPlayerUtil().getSurvivors().size() > UHCRegister.getTeamManagerUtil()
                        .isInOneTeam(
                                (Player) Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName())))
                        .getSize())
                    if (!isFFA)
                        e.setCancelled(true);
        } else if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            if (UHCRegister.getTeamManagerUtil().isSameTeam((Player) e.getEntity(), (Player) e.getDamager()))
                if (UHCRegister.getPlayerUtil().getSurvivors().size() > UHCRegister.getTeamManagerUtil()
                        .isInOneTeam((Player) e.getEntity()).getSize())
                    if (!isFFA)
                        e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (UHCRegister.getTeamManagerUtil() == null)
            return;
        if (e.getPlayer() == null)
            return;
        if (GState.isState(GState.LOBBY))
            if (UHCRegister.getTeamManagerUtil().isInOneTeam(e.getPlayer()) == null)
                return;
        if (!UHCRegister.getTeamManagerUtil()
                .existTeam(UHCRegister.getTeamManagerUtil().isInOneTeam(e.getPlayer())))
            return;
        UHCRegister.getTeamManagerUtil().isInOneTeam(e.getPlayer()).removeTeam(e.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Material m = UHCRegister.getTeamManagerUtil().getIconMaterial();

        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() == null)
            return;
        if (!e.getClickedInventory().getTitle().contains(UHCFileRegister.getTeamFile().getInventoryName()))
            return;

        e.setCancelled(true);
        if (e.getCurrentItem().getType().equals(m)) {
            if (UHCRegister.getTeamManagerUtil()
                    .existTeam(UHCRegister.getTeamManagerUtil().getTeamByIcon(e.getCurrentItem(), m))) {
                UHCTeam team = UHCRegister.getTeamManagerUtil().getTeamByIcon(e.getCurrentItem(), m);

                if (team == null)
                    return;

                UHCTeam is = UHCRegister.getTeamManagerUtil().isInOneTeam(p);

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

        if (UHCRegister.getTeamManagerUtil()
                .getTeamByName(ChatColor.stripColor(e.getRightClicked().getCustomName())) == null)
            return;

        UHCRegister.getTeamManagerUtil().getTeamByName(ChatColor.stripColor(e.getRightClicked().getCustomName()))
                .addToTeam(e.getPlayer());
    }
}
