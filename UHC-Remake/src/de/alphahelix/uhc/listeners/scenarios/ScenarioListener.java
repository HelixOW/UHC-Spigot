package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.Bukkit;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class ScenarioListener extends SimpleListener {

	public ScenarioListener(UHC uhc) {
		super(uhc);
		getRegister().getScenListeners().add(this);
	}
	
	public void register() {
		Bukkit.getPluginManager().registerEvents(this, getUhc());
	}
}
