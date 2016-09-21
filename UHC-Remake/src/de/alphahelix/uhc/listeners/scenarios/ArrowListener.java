package de.alphahelix.uhc.listeners.scenarios;

import java.util.ArrayList;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class ArrowListener extends SimpleListener {
	
	private static ArrayList<String> hasShot = new ArrayList<>();

	public ArrowListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onShot(ProjectileLaunchEvent e) {
		if(!(e.getEntity().getShooter() instanceof Player)) return;
		Player p = (Player) e.getEntity().getShooter();
		
		if(e.isCancelled()) return;
		if(!scenarioCheck(Scenarios.THREE_TIME_ARROWS)) return;
		if(!(e.getEntity() instanceof Arrow)) return;
		if(hasShot.contains(p.getName())) return;
		
		cooldown(5, p.getName(), hasShot);
		
		p.launchProjectile(Arrow.class);
		p.launchProjectile(Arrow.class);
	}
}
