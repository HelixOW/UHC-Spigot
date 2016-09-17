package de.alphahelix.uhc.listeners;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class GStateListener extends SimpleListener {

	public GStateListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		p.teleport(getRegister().getLocationsFile().getLobby());

		getRegister().getTablistUtil().sendTablist();
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;

		if (!GState.isState(GState.LOBBY))
			return;
		if(getRegister().getLobbyUtil().hasBuildPermission((Player) e.getWhoClicked())) return;
		if (e.getClickedInventory().getType().equals(InventoryType.PLAYER))
			e.setCancelled(true);
	}

	@EventHandler
	public void onBlockInvOpen(InventoryOpenEvent e) {
		if (!GState.isState(GState.LOBBY))
			return;
		if(getRegister().getLobbyUtil().hasBuildPermission((Player) e.getPlayer())) return;
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
		if(getRegister().getLobbyUtil().hasBuildPermission((Player) e.getPlayer())) return;
		if (e.getClickedBlock().getType().equals(Material.CHEST)
				|| e.getClickedBlock().getType().equals(Material.ENDER_CHEST))
			e.setCancelled(true);
	}

	@EventHandler
	public void onCollect(PlayerPickupItemEvent e) {
		if (GState.isState(GState.LOBBY)) {
			if(getRegister().getLobbyUtil().hasBuildPermission((Player) e.getPlayer())) return;
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
			if(getRegister().getLobbyUtil().hasBuildPermission((Player) e.getPlayer())) return;
			e.setCancelled(true);
		}
	}
}
