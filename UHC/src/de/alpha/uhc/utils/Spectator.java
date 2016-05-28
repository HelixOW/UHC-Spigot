package de.alpha.uhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.alpha.uhc.Core;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.item.data.SkullData;

public class Spectator implements Listener {
	
	private Core pl;
	
	public Spectator(Core c) {
		this.pl = c;
	}

    private  String specItem;
    private  String specName;
    private  String title;

    public  void setSpecItem(String specItem) {
        this.specItem = specItem;
    }

    public  void setSpecName(String specName) {
        this.specName = specName;
    }

    public  void setTitle(String title) {
        this.title = title;
    }

    public void setSpec(Player p) {

        p.setCanPickupItems(false);
        p.setFoodLevel(20);
        p.setHealth(20);
        p.setVelocity(p.getVelocity().setY(20D));
        p.setTotalExperience(0);
        p.setGameMode(GameMode.ADVENTURE);
        p.setPlayerListName("§7[§4X§7] §c" + p.getDisplayName());
        p.setAllowFlight(true);
        p.setFlying(true);
        equipSpecStuff(p);
        for (Player ig : pl.getInGamePlayers()) {
            ig.hidePlayer(p);
        }
    }

    private  void equipSpecStuff(Player p) {
        p.getInventory().addItem(new ItemBuilder(Material.getMaterial(specItem.toUpperCase())).setName(specName).build());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (pl.getSpecs().contains(p)) {
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
        if (pl.getSpecs().contains(e.getPlayer())) {
            e.setAmount(0);
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation().subtract(0, 5, 0), EntityType.EXPERIENCE_ORB);
        }
    }

    @EventHandler
    public void onDmg(EntityDamageEvent e) {

        if (!(e.getEntity() instanceof Player)) return;

        Player p = (Player) e.getEntity();
        if (pl.getSpecs().contains(p)) e.setCancelled(true);
    }

    @EventHandler
    public void onHungerMeterChange(FoodLevelChangeEvent e) {

        Player p = (Player) e.getEntity();

        if (pl.getSpecs().contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {

        Player p = e.getPlayer();

        if (pl.getSpecs().contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player)) return;

        if (pl.getSpecs().contains(e.getDamager())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        if (pl.getSpecs().contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {

        Player p = e.getPlayer();

        if (pl.getSpecs().contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTrack(EntityTargetEvent e) {
        if (((e.getTarget() instanceof Player)) && (pl.getSpecs().contains(e.getTarget()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (!(pl.getSpecs().contains(p))) return;

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {


            if (p.getInventory().getItemInMainHand().getType().equals(Material.getMaterial(specItem.toUpperCase()))) {//TODO: multi

                Inventory inv = Bukkit.createInventory(null, 54, title);

                for (Player pl : pl.getInGamePlayers()) {

                    ItemStack item = new ItemBuilder(Material.SKULL_ITEM).setDamage((short) 3).setName("§l§o" + pl.getDisplayName()).addItemData(new SkullData(pl.getName())).build();

                    inv.addItem(item);

                }

                for (int i = 45; i < 54; i++) {
                    inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 15).build());
                }

                p.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null) return;
        Inventory inv = e.getClickedInventory();

        if (inv.getTitle().equals(title)) {

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
