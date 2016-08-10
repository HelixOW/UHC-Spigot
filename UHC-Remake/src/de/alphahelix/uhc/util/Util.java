package de.alphahelix.uhc.util;

import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;

public class Util {
	
	private UHC uhc;
	private Registery register;
	
	public Util(UHC uhc) {
		setUhc(uhc);
		setRegister(getUhc().getRegister());
	}
	
	public UHC getUhc() {
		return uhc;
	}

	public void setUhc(UHC uhc) {
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
}
