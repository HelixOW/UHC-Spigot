package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class StatusFile extends EasyFile {

	public StatusFile(UHC uhc) {
		super("status.uhc", uhc);
	}

	@Override
	public void addValues() {
        setDefault("State.Lobby", "&alobby");
        setDefault("State.Period of peace", "&dperiod of peace");
        setDefault("State.Warmup", "&bwarmup");
        setDefault("State.In game", "&cin game");
        setDefault("State.Deathmatch warmup", "&3DM - warmup");
        setDefault("State.Deathmatch", "&4Deathmatch");
        setDefault("State.End", "&8Restart");
	}
}
