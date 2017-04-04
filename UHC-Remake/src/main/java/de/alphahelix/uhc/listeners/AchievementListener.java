package de.alphahelix.uhc.listeners;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.UHCAchievements;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.StatsUtil;
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

import java.util.UUID;

/**
 * Created by AlphaHelixDev.
 */
public class AchievementListener extends SimpleListener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (!GState.isState(GState.LOBBY))
            return;
        if (e.getPlayer().getInventory().getItemInHand() == null)
            return;
        if (e.getPlayer().getInventory().getItemInHand().getType() != UHCFileRegister.getAchievementFile().getItem().getItemStack().getType())
            return;

        final Player p = e.getPlayer();

        UHCRegister.getAchievementInventory().openInventory(p);
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
        if (!e.getInventory().getName().contains(ChatColor.stripColor(UHCRegister.getAchievementInventory().getInventory().getTitle())))
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) return;
        if (e.getEntity().getKiller() == null) return;
        UUID id = UUIDFetcher.getUUID(e.getEntity().getKiller());

        if (e.getEntityType() == EntityType.ENDER_DRAGON) {
            if (!StatsUtil.hasAchievement(id, UHCAchievements.DRAGON_SLAYER)) {
                StatsUtil.addAchievement(id, UHCAchievements.DRAGON_SLAYER);
                e.getEntity().getKiller().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getAchievementUnlocked().replace("[achievement]", UHCAchievements.DRAGON_SLAYER.getName()));
            }

        } else if (e.getEntityType() == EntityType.PLAYER) {
            if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE ||
                    e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                    e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
                if (!StatsUtil.hasAchievement(id, UHCAchievements.BURN_BABY_BURN)) {
                    StatsUtil.addAchievement(id, UHCAchievements.BURN_BABY_BURN);
                    e.getEntity().getKiller().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getAchievementUnlocked().replace("[achievement]", UHCAchievements.BURN_BABY_BURN.getName()));
                }
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                if (!StatsUtil.hasAchievement(id, UHCAchievements.SENOR_BOOM_BOOM)) {
                    StatsUtil.addAchievement(id, UHCAchievements.SENOR_BOOM_BOOM);
                    e.getEntity().getKiller().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getAchievementUnlocked().replace("[achievement]", UHCAchievements.SENOR_BOOM_BOOM.getName()));
                }
            }
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) return;
        if (e.getEntity().getKiller() == null) return;
        UUID id = UUIDFetcher.getUUID(e.getEntity().getKiller());

        if (StatsUtil.getKills(id) >= 500) {
            if (!StatsUtil.hasAchievement(id, UHCAchievements.SLAYER)) {
                StatsUtil.addAchievement(id, UHCAchievements.SLAYER);
                e.getEntity().getKiller().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getAchievementUnlocked().replace("[achievement]", UHCAchievements.SLAYER.getName()));
            }
        }
    }

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) return;
        if (e.getBlock().getType() != Material.DIAMOND_BLOCK) return;
        UUID id = UUIDFetcher.getUUID(e.getPlayer());

        if (!StatsUtil.hasAchievement(id, UHCAchievements.OMG_DIAMONDS)) {
            StatsUtil.addAchievement(id, UHCAchievements.OMG_DIAMONDS);
            e.getPlayer().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getAchievementUnlocked().replace("[achievement]", UHCAchievements.OMG_DIAMONDS.getName()));
        }
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if (e.isCancelled()) return;
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) return;
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            UUID id = UUIDFetcher.getUUID(e.getPlayer());

            if (e.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
                if (!StatsUtil.hasAchievement(id, UHCAchievements.HIGHWAY_TO_HELL)) {
                    StatsUtil.addAchievement(id, UHCAchievements.HIGHWAY_TO_HELL);
                    e.getPlayer().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getAchievementUnlocked().replace("[achievement]", UHCAchievements.HIGHWAY_TO_HELL.getName()));
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

        UUID id = UUIDFetcher.getUUID(e.getEnchanter());
        if (!StatsUtil.hasAchievement(id, UHCAchievements.GLOWING_IN_THE_DARK)) {
            StatsUtil.addAchievement(id, UHCAchievements.GLOWING_IN_THE_DARK);
            e.getEnchanter().sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getAchievementUnlocked().replace("[achievement]", UHCAchievements.GLOWING_IN_THE_DARK.getName()));
        }
    }
}
