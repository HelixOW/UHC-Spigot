package de.alphahelix.uhc.instances;

import java.util.logging.Logger;

import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;

public class Util {
	
	private UHC uhc;
	private Registery register;
	private Logger log;
	
	public Util(UHC uhc) {
		setUhc(uhc);
		setRegister(getUhc().getRegister());
		setLog(getUhc().getLog());
	}
	
	public UHC getUhc() {
		return uhc;
	}

	private void setUhc(UHC uhc) {
		this.uhc = uhc;
	}

	public Util getUtilClass() {
		return this;
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
	
	public double round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}
	
	public double calcMin(int sec) {
		return (sec / (3600/60.0));
	}
	
	public double calcHours(int sec) {
		return sec / 3600;
	}
}
