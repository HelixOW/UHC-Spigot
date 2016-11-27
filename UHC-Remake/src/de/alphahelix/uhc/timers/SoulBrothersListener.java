package de.alphahelix.uhc.timers;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import de.popokaka.alphalibary.nms.SimpleActionBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;

public class SoulBrothersListener extends SimpleListener {

	private HashMap<String, Double> soulBrotherhealth = new HashMap<>();

	public SoulBrothersListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.SOUL_BROTHERS))
			return;
		if (!(e.getEntity() instanceof Player))
			return;

		Player p = (Player) e.getEntity();

		if (!p.isSneaking())
			return;
		if (soulBrotherhealth.containsKey(p.getName())) {
			if(soulBrotherhealth.get(p.getName()) > e.getDamage()) {
				soulBrotherhealth.put(p.getName(), soulBrotherhealth.get(p.getName()) - e.getDamage());
				e.setCancelled(true);
				SimpleActionBar.send(p, "§7" + soulBrotherhealth.get(p.getName()).intValue());
			}
		} else {
			soulBrotherhealth.put(p.getName(), p.getMaxHealth() - e.getDamage());
			e.setCancelled(true);
			SimpleActionBar.send(p, "§7" + soulBrotherhealth.get(p.getName()).intValue());
		}
	}
}
