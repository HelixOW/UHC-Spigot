package de.alphahelix.uhc.util;

import java.util.logging.Logger;

import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;
import de.popokaka.alphalibary.file.SimpleFile;

public class EasyFile extends SimpleFile {

	private UHC uhc;
	private Registery register;
	private Logger log;
	private String name;

	public EasyFile(String name, UHC uhc) {
		super("plugins/UHC", name);
		setUhc(uhc);
		setRegister(getUhc().getRegister());
		setLog(getUhc().getLog());
		setName(name);
		getRegister().getEasyFiles().add(this);
	}

	public void addValues() {
	};

	public void loadValues() {
	};

	public void register(EasyFile easy) {
		easy.addValues();
		easy.loadValues();
	}

	public SimpleFile getFile() {
		return new SimpleFile("plugins/UHC", name);
	}

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

	public Logger getLog() {
		return log;
	}

	private void setLog(Logger log) {
		this.log = log;
	}

	private void setName(String filename) {
		this.name = filename;
	}
}