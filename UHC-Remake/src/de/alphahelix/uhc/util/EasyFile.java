package de.alphahelix.uhc.util;

import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;
import de.popokaka.alphalibary.file.SimpleFile;

public class EasyFile extends SimpleFile {
	
	private UHC uhc;
	private Registery register;
	
	public EasyFile(String name, UHC uhc) {
		super("plugins/UHC", name);
		setUhc(uhc);
		setRegister(getUhc().getRegister());
	}
	
	public void addValues(){};
	public void loadValues(){};

	public UHC getUhc() {
		return uhc;
	}

	public void setUhc(UHC uhc) {
		this.uhc = uhc;
	}

	public Registery getRegister() {
		return register;
	}

	public void setRegister(Registery register) {
		this.register = register;
	}
	
	public static void register(EasyFile easy) {
		easy.addValues();
		easy.loadValues();
	}
}
