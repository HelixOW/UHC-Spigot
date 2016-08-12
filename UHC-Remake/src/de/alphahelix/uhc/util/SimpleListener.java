package de.alphahelix.uhc.util;

import java.util.logging.Logger;

import org.bukkit.event.Listener;

import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;

public class SimpleListener implements Listener {
	
	private UHC uhc;
	private Registery register;
	private Logger log;

	public SimpleListener(UHC uhc) {
		setUhc(uhc);
		setRegister(getUhc().getRegister());
		getUhc().getRegister().getListeners().add(this);
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
	
	
}
