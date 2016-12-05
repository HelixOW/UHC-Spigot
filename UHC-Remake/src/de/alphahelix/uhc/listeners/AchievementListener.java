package de.alphahelix.uhc.listeners;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.UHCAchievements;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by AlphaHelixDev.
 */
public class AchievementListener extends SimpleListener {

    public AchievementListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (!GState.isState(GState.LOBBY))
            return;
        if (e.getPlayer().getInventory().getItemInHand() == null)
            return;
        if (!e.getPlayer().getInventory().getItemInHand().getType().equals(getRegister().getAchievementFile().getItem().getType()))
            return;

        final Player p = e.getPlayer();

        getRegister().getAchievementInventory().openInventory(p);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        if (!e.getInventory().getName().contains(ChatColor.stripColor(getRegister().getAchievementInventory().getInventory().getTitle())))
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) return;
        if (e.getEntity().getKiller() == null) return;

        if (e.getEntityType() == EntityType.ENDER_DRAGON) {
            if (!getRegister().getStatsUtil().hasAchievement(UHCAchievements.DRAGON_SLAYER, e.getEntity().getKiller())) {
                getRegister().getStatsUtil().addAchievement(UHCAchievements.DRAGON_SLAYER, e.getEntity().getKiller());
                e.getEntity().getKiller().sendMessage(getUhc().getPrefix() + getRegister().getAchievementFile().getColorString("Achievement unlocked").replace("[achievement]", UHCAchievements.DRAGON_SLAYER.getName()));
            }

        } else if (e.getEntityType() == EntityType.PLAYER) {
            if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE ||
                    e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                    e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
                if (!getRegister().getStatsUtil().hasAchievement(UHCAchievements.BURN_BABY_BURN, e.getEntity().getKiller())) {
                    getRegister().getStatsUtil().addAchievement(UHCAchievements.BURN_BABY_BURN, e.getEntity().getKiller());
                    e.getEntity().getKiller().sendMessage(getUhc().getPrefix() + getRegister().getAchievementFile().getColorString("Achievement unlocked").replace("[achievement]", UHCAchievements.BURN_BABY_BURN.getName()));
                }
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                if (!getRegister().getStatsUtil().hasAchievement(UHCAchievements.SENOR_BOOM_BOOM, e.getEntity().getKiller())) {
                    getRegister().getStatsUtil().addAchievement(UHCAchievements.SENOR_BOOM_BOOM, e.getEntity().getKiller());
                    e.getEntity().getKiller().sendMessage(getUhc().getPrefix() + getRegister().getAchievementFile().getColorString("Achievement unlocked").replace("[achievement]", UHCAchievements.SENOR_BOOM_BOOM.getName()));
                }
            }
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) return;
        if (e.getEntity().getKiller() == null) return;

        if (getRegister().getStatsUtil().getKills(e.getEntity().getKiller()) >= 500) {
            if (!getRegister().getStatsUtil().hasAchievement(UHCAchievements.SLAYER, e.getEntity().getKiller())) {
                getRegister().getStatsUtil().addAchievement(UHCAchievements.SLAYER, e.getEntity().getKiller());
                e.getEntity().getKiller().sendMessage(getUhc().getPrefix() + getRegister().getAchievementFile().getColorString("Achievement unlocked").replace("[achievement]", UHCAchievements.SLAYER.getName()));
            }
        }
    }

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) return;
        if (e.getBlock().getType() != Material.DIAMOND_BLOCK) return;

        if (!getRegister().getStatsUtil().hasAchievement(UHCAchievements.OMG_DIAMONDS, e.getPlayer())) {
            getRegister().getStatsUtil().addAchievement(UHCAchievements.OMG_DIAMONDS, e.getPlayer());
            e.getPlayer().sendMessage(getUhc().getPrefix() + getRegister().getAchievementFile().getColorString("Achievement unlocked").replace("[achievement]", UHCAchievements.OMG_DIAMONDS.getName()));
        }
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if (e.isCancelled()) return;
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) return;
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            if (e.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
                if (!getRegister().getStatsUtil().hasAchievement(UHCAchievements.HIGHWAY_TO_HELL, e.getPlayer())) {
                    getRegister().getStatsUtil().addAchievement(UHCAchievements.HIGHWAY_TO_HELL, e.getPlayer());
                    e.getPlayer().sendMessage(getUhc().getPrefix() + getRegister().getAchievementFile().getColorString("Achievement unlocked").replace("[achievement]", UHCAchievements.HIGHWAY_TO_HELL.getName()));
                }
            }
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent e) {
        if (e.isCancelled()) return;
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) return;
        if (!(e.getItem().getType().name().contains("HELMET") ||
                e.getItem().getType().name().contains("CHESTPLATE") ||
                e.getItem().getType().name().contains("LEGGINGS") ||
                e.getItem().getType().name().contains("BOOTS"))) return;

        if (!getRegister().getStatsUtil().hasAchievement(UHCAchievements.GLOWING_IN_THE_DARK, e.getEnchanter())) {
            getRegister().getStatsUtil().addAchievement(UHCAchievements.GLOWING_IN_THE_DARK, e.getEnchanter());
            e.getEnchanter().sendMessage(getUhc().getPrefix() + getRegister().getAchievementFile().getColorString("Achievement unlocked").replace("[achievement]", UHCAchievements.GLOWING_IN_THE_DARK.getName()));
        }
    }
}
