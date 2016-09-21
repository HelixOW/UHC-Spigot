package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class SpectatorFile extends EasyFile {

	public SpectatorFile(UHC uhc) {
		super("spectator.uhc", uhc);
	}
	
	@Override
	public void addValues() {
		setDefault("GUI.Name", "§aTeleport");
		setDefault("Spectator.Item", "magma cream");
		setDefault("Spectator.Item Name", "&cTeleporter");
		setDefault("Spectator.Item Slot", 4);
	}

}
