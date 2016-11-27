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

import java.util.ArrayList;

public class OneHealListener extends SimpleListener {

	private ArrayList<String> healed = new ArrayList<>();

	public OneHealListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.ONE_HEAL))
			return;

		for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.getInventory().addItem(new ItemStack(Material.GOLD_HOE));
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.ONE_HEAL))
			return;
		if (healed.contains(e.getPlayer().getName()))
			return;
		if (!e.getAction().name().contains("RIGHT"))
			return;

		if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLD_HOE)) {
			healed.add(e.getPlayer().getName());
			e.getPlayer().setHealth(20.0);
			e.getPlayer().getInventory().remove(Material.GOLD_HOE);
		}
	}
}
