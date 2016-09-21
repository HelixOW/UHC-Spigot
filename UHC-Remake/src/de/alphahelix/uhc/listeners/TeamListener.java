package de.alphahelix.uhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.instances.UHCTeam;
import de.popokaka.alphalibary.UUID.UUIDFetcher;

public class TeamListener extends SimpleListener {

	public TeamListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (!GState.isState(GState.LOBBY))
			return;

		if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType()
				.equals(getRegister().getTeamManagerUtil().getTeamItem())) {
			getRegister().getTeamInventory().openInventory(p);
		}
	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Villager && e.getEntity().isCustomNameVisible()
				&& e.getDamager() instanceof Player) {
			if (getRegister().getTeamManagerUtil().isInOneTeam(
					(Player) Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(e.getEntity().getCustomName()))) != null) {
				if (getRegister().getTeamManagerUtil().isInOneTeam((Player) e.getDamager()) != null) {
					e.setCancelled(true);
				}
			}
		} else if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			if (getRegister().getTeamManagerUtil().isInOneTeam((Player) e.getEntity()) != null) {
				if (getRegister().getTeamManagerUtil().isInOneTeam((Player) e.getDamager()) != null) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if (getRegister().getTeamManagerUtil() == null)
			return;
		if (e.getPlayer() == null)
			return;
		if (GState.isState(GState.LOBBY))
			if (getRegister().getTeamManagerUtil().isInOneTeam(e.getPlayer()) == null)
				return;
		if (!getRegister().getTeamManagerUtil()
				.existTeam(getRegister().getTeamManagerUtil().isInOneTeam(e.getPlayer())))
			return;
		getRegister().getTeamManagerUtil().isInOneTeam(e.getPlayer()).removeTeam(e.getPlayer());
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Material m = getRegister().getTeamManagerUtil().getIconMaterial();

		if (e.getClickedInventory() == null)
			return;
		if (e.getCurrentItem() == null)
			return;
		if (!e.getClickedInventory().getTitle().contains(getRegister().getTeamFile().getColorString("GUI.Name")))
			return;

		e.setCancelled(true);
		if (e.getCurrentItem().getType().equals(m)) {
			if (getRegister().getTeamManagerUtil()
					.existTeam(getRegister().getTeamManagerUtil().getTeamByIcon(e.getCurrentItem(), m))) {
				UHCTeam team = getRegister().getTeamManagerUtil().getTeamByIcon(e.getCurrentItem(), m);

				if (team == null)
					return;

				UHCTeam is = getRegister().getTeamManagerUtil().isInOneTeam(p);

				if (is != null)
					is.removeTeam(p);

				team.addToTeam(p);
				p.closeInventory();
				getRegister().getTeamInventory().openInventory(p);
			}
		}
	}
}
