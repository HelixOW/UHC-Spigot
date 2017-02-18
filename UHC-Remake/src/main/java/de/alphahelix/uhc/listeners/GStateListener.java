package de.alphahelix.uhc.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.alphahelix.alphalibary.utils.LocationUtil;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.potion.PotionEffectType;

public class GStateListener extends SimpleListener {

    private Location lobbyCorner1, lobbyCorner2;

    public GStateListener(UHC uhc) {
        super(uhc);
        lobbyCorner1 = UHCFileRegister.getLocationsFile().getLobby().clone().subtract(75, 50, 75);
        lobbyCorner2 = UHCFileRegister.getLocationsFile().getLobby().clone().add(75, 50, 75);
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        if (!UHCFileRegister.getOptionsFile().isStatusMOTD())
            return;

        e.setMotd(UHCFileRegister.getMotdFile().getMOTD(GState.getCurrentState()));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!GState.isState(GState.LOBBY))
            return;
        if (e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockY() == e.getFrom().getBlockY()
                && e.getTo().getBlockZ() == e.getFrom().getBlockZ())
            return;
        if (UHCRegister.getLobbyUtil().hasBuildPermission(e.getPlayer()))
            return;
        if (!getUhc().isLobbyAsSchematic())
            return;

        if (!LocationUtil.isInside(e.getTo(), lobbyCorner1, lobbyCorner2)) {
            e.getPlayer().teleport(UHCFileRegister.getLocationsFile().getLobby().clone().add(0, 2, 0));
        }
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent e) {
        if (GState.isState(GState.END)) {
            e.disallow(Result.KICK_WHITELIST, getUhc().getRestartMessage());
            return;
        }

        if (UHCRegister.getPlayerUtil().getMaximumPlayerCount() <= UHCRegister.getPlayerUtil().getAll().size())
            e.disallow(Result.KICK_FULL, UHCFileRegister.getMessageFile().getFull());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        p.teleport(UHCFileRegister.getLocationsFile().getLobby().clone().add(0, 2, 0));

        UHCRegister.getTablistUtil().sendTablist();
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;

        Player p = (Player) e.getEntity();

        if (p.hasPotionEffect(PotionEffectType.REGENERATION))
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onItemComsune(PlayerItemConsumeEvent e) {
        if (GState.isState(GState.LOBBY))
            e.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null)
            return;

        if (!GState.isState(GState.LOBBY))
            return;
        if (UHCRegister.getLobbyUtil().hasBuildPermission((Player) e.getWhoClicked()))
            return;
        if (e.getClickedInventory().getType().equals(InventoryType.PLAYER))
            e.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerPortalEvent e) {
        if (!GState.isState(GState.LOBBY))
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockInvOpen(InventoryOpenEvent e) {
        if (!GState.isState(GState.LOBBY))
            return;
        if (UHCRegister.getLobbyUtil().hasBuildPermission((Player) e.getPlayer()))
            return;
        if (!(e.getInventory().getType().equals(InventoryType.CHEST)
                || e.getInventory().getType().equals(InventoryType.ENDER_CHEST))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent e) {
        if (!GState.isState(GState.LOBBY))
            return;
        if (e.getClickedBlock() == null)
            return;
        if (UHCRegister.getLobbyUtil().hasBuildPermission(e.getPlayer())) return;

        if (e.getClickedBlock().getType().equals(Material.CHEST)
                || e.getClickedBlock().getType().equals(Material.ENDER_CHEST))
            e.setCancelled(true);

        if (e.getPlayer().getInventory().getItemInHand() != null
                && e.getPlayer().getInventory().getItemInHand().hasItemMeta()
                && e.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName()
                && e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName()
                .equals(UHCFileRegister.getLobbyFile().getItem().getItemStack().getItemMeta().getDisplayName())) {
            if (getUhc().isBunggeMode()) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF("Connect");
                out.writeUTF(getUhc().getLobbyServer());

                e.getPlayer().sendPluginMessage(getUhc(), "BungeeCord", out.toByteArray());
            }
        }
    }

    @EventHandler
    public void onCollect(PlayerPickupItemEvent e) {
        if (GState.isState(GState.LOBBY)) {
            if (UHCRegister.getLobbyUtil().hasBuildPermission(e.getPlayer()))
                return;
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChunkUnLoad(ChunkUnloadEvent e) {
        if (e.getWorld().getName().equals("world")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (GState.isState(GState.LOBBY) || GState.isState(GState.PERIOD_OF_PEACE)) {
            e.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onAlternateSpawn(EntitySpawnEvent e) {
        if (GState.isState(GState.LOBBY)) {
            if (!(e.getEntity() instanceof ArmorStand)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (GState.isState(GState.LOBBY)) {
            if (!(e.getEntity() instanceof ArmorStand)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHurt(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (GState.isState(GState.LOBBY) || GState.isState(GState.PERIOD_OF_PEACE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityHurt(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player))
            return;
        if (e.getEntity() instanceof Player) {
            if (GState.isState(GState.LOBBY) || GState.isState(GState.PERIOD_OF_PEACE)
                    || GState.isState(GState.WARMUP)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        if (UHCRegister.getLobbyUtil().hasBuildPermission(e.getPlayer()))
            return;
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (UHCRegister.getLobbyUtil().hasBuildPermission(e.getPlayer()))
            return;
        if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (GState.isState(GState.LOBBY)) {
            if (UHCRegister.getLobbyUtil().hasBuildPermission(e.getPlayer()))
                return;
            e.setCancelled(true);
        }
    }
}
