package de.alphahelix.uhc.instances;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;

public abstract class SimpleListener implements Listener {

	private UHC uhc;
	private Registery register;
	private Logger log;

	public SimpleListener(UHC uhc) {
		setUhc(uhc);
		setRegister(getUhc().getRegister());
		getRegister().getListeners().add(this);
		setLog(getUhc().getLog());
	}

	public UHC getUhc() {
		return uhc;
	}

	private void setUhc(UHC uhc) {
		this.uhc = uhc;
	}

	public Registery getRegister() {
		return register;
	}

	private void setRegister(Registery register) {
		this.register = register;
	}

	public Logger getLog() {
		return log;
	}

	private void setLog(Logger log) {
		this.log = log;
	}

	public boolean scenarioCheck(Scenarios s) {
		if (!(GState.isState(GState.LOBBY) || GState.isState(GState.END))) {
			return Scenarios.isScenario(s);
		}
		return false;
	}

	public <T> void cooldown(int length, final T key, final List<T> cooldownList) {
		cooldownList.add(key);
		new BukkitRunnable() {
			public void run() {
				cooldownList.remove(key);
			}
		}.runTaskLaterAsynchronously(getUhc(), length);
	}
}
