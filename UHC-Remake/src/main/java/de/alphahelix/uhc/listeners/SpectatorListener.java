package de.alphahelix.uhc.listeners;

import de.alphahelix.alphalibary.inventorys.SimpleMovingInventory;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.item.data.SkullData;
import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class SpectatorListener extends SimpleListener {

    private static ArrayList<ItemStack> skulls = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (PlayerUtil.isDead(p)) {
            for (Entity near : p.getNearbyEntities(6, 6, 6)) {
                if (near instanceof Player) {
                    Vector v = p.getLocation().getDirection().add(new Vector(-1, 2, -1));
                    p.setVelocity(v);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onExp(PlayerExpChangeEvent e) {
        if (PlayerUtil.isDead(e.getPlayer())) {
            e.setAmount(0);
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation().subtract(0, 5, 0),
                    EntityType.EXPERIENCE_ORB);
        }
    }

    @EventHandler
    public void onDmg(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;

        Player p = (Player) e.getEntity();
        if (PlayerUtil.isDead(p))
            e.setCancelled(true);
    }

    @EventHandler
    public void onHungerMeterChange(FoodLevelChangeEvent e) {

        Player p = (Player) e.getEntity();

        if (PlayerUtil.isDead(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {

        Player p = e.getPlayer();

        if (PlayerUtil.isDead(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player))
            return;

        if (PlayerUtil.isDead((Player) e.getDamager())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        if (PlayerUtil.isDead(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {

        Player p = e.getPlayer();

        if (PlayerUtil.isDead(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTrack(EntityTargetLivingEntityEvent e) {
        if (((e.getTarget() instanceof Player)) && (PlayerUtil.isDead((Player) e.getTarget()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (PlayerUtil.isSurivor(p))
            return;

        if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.CHEST))
            e.setCancelled(true);

        if (p.getInventory().getItemInHand().getType() == UHCFileRegister.getSpectatorFile().getItem().getItemStack().getType()) {
            for (String pl : PlayerUtil.getSurvivors()) {

                ItemStack item = new ItemBuilder(Material.SKULL_ITEM).setDamage((short) 3)
                        .setName("§l§o" + Bukkit.getPlayer(pl).getDisplayName()).addItemData(new SkullData(pl)).build();

                skulls.add(item);
            }

            new SimpleMovingInventory(
                    p,
                    ((PlayerUtil.getSurvivors().size() / 9) + 1) * 18,
                    skulls,
                    UHCFileRegister.getSpectatorFile().getInventoryName(),
                    UHCFileRegister.getKitsFile().getNextItem().getItemStack(),
                    UHCFileRegister.getKitsFile().getPreviousItem().getItemStack());

            skulls.clear();
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null)
            return;

        if (e.getClickedInventory().getTitle().contains(UHCFileRegister.getSpectatorFile().getInventoryName())) {

            e.setCancelled(true);

            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {

                String playername = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                Player player = Bukkit.getPlayerExact(playername);

                if (player != null) {
                    e.setCancelled(true);
                    p.teleport(player);
                    p.closeInventory();
                }
            }
            if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                e.setCancelled(true);
            }
        }
    }

}
