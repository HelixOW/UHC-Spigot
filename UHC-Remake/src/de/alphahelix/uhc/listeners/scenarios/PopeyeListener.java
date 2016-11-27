package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PopeyeListener extends SimpleListener {

	public PopeyeListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.POPEYE))
			return;

		for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.getInventory().addItem(new ItemStack(Material.INK_SACK, 1, (short) 2));
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.POPEYE))
			return;
		if (!e.getAction().name().contains("RIGHT"))
			return;
		
		if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.INK_SACK)
				&& e.getPlayer().getInventory().getItemInMainHand().getDurability() == 2) {
			e.getPlayer().getInventory().remove(new ItemStack(Material.INK_SACK, 1, (short) 2));
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 1));
		}
	}
}
