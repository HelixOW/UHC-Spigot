package de.alphahelix.uhc.listeners.scenarios;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;

public class PotionSwapListener extends SimpleListener {

	private static ArrayList<PotionEffect> potionEffects = new ArrayList<>();

	public PotionSwapListener(UHC uhc) {
		super(uhc);

		for (PotionEffectType e : PotionEffectType.values()) {
			if (e == null)
				continue;
			if (e.equals(PotionEffectType.LEVITATION))
				continue;
			potionEffects.add(new PotionEffect(e, 9999999, 0));
		}
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.POTION_SWAP))
			return;

		PotionEffect[] potiontype = makeArray(new PotionEffect(PotionEffectType.SPEED, 99999, 1),
				new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 0),
				new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 0),
				new PotionEffect(PotionEffectType.JUMP, 99999, 0),
				new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0),
				new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 0),
				new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0));

		for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
			p.addPotionEffect(potiontype[new Random().nextInt(potiontype.length)]);
		}

		new BukkitRunnable() {
			public void run() {
				new BukkitRunnable() {
					public void run() {
						for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
							p.addPotionEffect(potionEffects.get(new Random().nextInt(potionEffects.size())));
						}
					}
				}.runTaskTimer(getUhc(), 0, (20 * 60) * 5);
			}
		}.runTaskLaterAsynchronously(getUhc(), (20 * 60) * 5);
	}

}
