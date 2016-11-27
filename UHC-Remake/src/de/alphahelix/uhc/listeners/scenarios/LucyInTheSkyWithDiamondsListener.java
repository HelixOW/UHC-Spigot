package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class LucyInTheSkyWithDiamondsListener extends SimpleListener {

	private Player lucy;
	private boolean wasLucyDamaged;

	public LucyInTheSkyWithDiamondsListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.LUCYS_IN_THE_SKY_WITH_DIAMONDS))
			return;

		lucy = Bukkit.getPlayer(getRegister().getPlayerUtil().getSurvivors()
				.get(new Random().nextInt(getRegister().getPlayerUtil().getSurvivors().size())));
		lucy.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1));
		lucy.setAllowFlight(true);
		getRegister().getPlayerUtil().removeSurvivor(lucy);
		broadcastLucy();
		
		for(Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.getInventory().addItem(new ItemStack(Material.BOW), new ItemStack(Material.ARROW, 16));
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;
		if (!(e.getEntity() instanceof Player))
			return;
		if (!(e.getDamager() instanceof Player))
			return;

		if (!scenarioCheck(Scenarios.LUCYS_IN_THE_SKY_WITH_DIAMONDS))
			return;

		Player dmg = (Player) e.getEntity();
		Player dmger = (Player) e.getDamager();

		if (dmg.getName().equals(lucy.getName())) {
			if (wasLucyDamaged)
				return;
			wasLucyDamaged = true;

			dmger.getInventory().addItem(new ItemStack(Material.DIAMOND));

			new BukkitRunnable() {
				public void run() {
					wasLucyDamaged = false;
				}
			}.runTaskLaterAsynchronously(getUhc(), 20);
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.LUCYS_IN_THE_SKY_WITH_DIAMONDS))
			return;

		if(e.getPlayer().getName().equals(lucy.getName())) e.setCancelled(true);
	}

	private void broadcastLucy() {
		new BukkitRunnable() {
			public void run() {
				for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
					p.sendMessage(getUhc().getPrefix() + "Lucy :" + lucy.getLocation().getBlockX() + " / "
							+ lucy.getLocation().getBlockZ());
				}
			}
		}.runTaskTimer(getUhc(), 0, 20 * 180);
	}
}
