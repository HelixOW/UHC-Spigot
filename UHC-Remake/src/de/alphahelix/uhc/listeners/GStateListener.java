package de.alphahelix.uhc.listeners;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.potion.PotionEffectType;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class GStateListener extends SimpleListener {

	public GStateListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onPing(ServerListPingEvent e) {
		if (!getRegister().getMainOptionsFile().getBoolean("Status MOTD"))
			return;
		
		switch (GState.getCurrentState()) {
		case LOBBY:
			e.setMotd(getRegister().getMOTDFile().getColorString("Lobby"));
			break;
		case PERIOD_OF_PEACE:
			e.setMotd(getRegister().getMOTDFile().getColorString("Period of peace"));
			break;
		case WARMUP:
			e.setMotd(getRegister().getMOTDFile().getColorString("Warmup"));
			break;
		case IN_GAME:
			e.setMotd(getRegister().getMOTDFile().getColorString("Ingame"));
			break;
		case DEATHMATCH_WARMUP:
			e.setMotd(getRegister().getMOTDFile().getColorString("Deathmatch warmup"));
			break;
		case DEATHMATCH:
			e.setMotd(getRegister().getMOTDFile().getColorString("Deathmatch"));
			break;
		case END:
			e.setMotd(getRegister().getMOTDFile().getColorString("End"));
			break;
		}
	}

	@EventHandler
	public void onLogin(AsyncPlayerPreLoginEvent e) {
		if (GState.isState(GState.END)) {
			e.disallow(Result.KICK_WHITELIST, getUhc().getRestartMessage());
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		p.teleport(getRegister().getLocationsFile().getLobby());

		getRegister().getTablistUtil().sendTablist();
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
		if (getRegister().getLobbyUtil().hasBuildPermission((Player) e.getWhoClicked()))
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
		if (getRegister().getLobbyUtil().hasBuildPermission((Player) e.getPlayer()))
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
		if (getRegister().getLobbyUtil().hasBuildPermission((Player) e.getPlayer()))
			return;
		if (e.getClickedBlock().getType().equals(Material.CHEST)
				|| e.getClickedBlock().getType().equals(Material.ENDER_CHEST))
			e.setCancelled(true);
	}

	@EventHandler
	public void onCollect(PlayerPickupItemEvent e) {
		if (GState.isState(GState.LOBBY)) {
			if (getRegister().getLobbyUtil().hasBuildPermission((Player) e.getPlayer()))
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

		if (getRegister().getLobbyUtil().hasBuildPermission(e.getPlayer()))
			return;
		if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (getRegister().getLobbyUtil().hasBuildPermission(e.getPlayer()))
			return;
		if (GState.isState(GState.LOBBY) || GState.isState(GState.END)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (GState.isState(GState.LOBBY)) {
			if (getRegister().getLobbyUtil().hasBuildPermission((Player) e.getPlayer()))
				return;
			e.setCancelled(true);
		}
	}
}
