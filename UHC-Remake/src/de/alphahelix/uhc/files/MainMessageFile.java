package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.EasyFile;

public class MainMessageFile extends EasyFile {

	public MainMessageFile(UHC uhc) {
		super("Main-Messages.uhc", uhc);
	}
	
	@Override
	public void addValues() {
		setDefault("Scenario Mode", "&7This UHC server is running in &cScenario Mode&7. Due to that the server doesn't allow kits.");
		setDefault("No Permissions", "&7You don't have &cpermissions &7to execute that command!");
		setDefault("Kit chosen", "&7You've §asuccessfully chosen §7the kit &8: &a[kit]§7!");
	}

}
