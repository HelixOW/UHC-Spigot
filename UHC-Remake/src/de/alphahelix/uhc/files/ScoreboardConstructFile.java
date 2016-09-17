package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class ScoreboardConstructFile extends EasyFile{

	public ScoreboardConstructFile(UHC uhc) {
		super("scoreboardConstruction.uhc", uhc);
	}

	@Override
	public void addValues() {
		setDefault("Lobby.lines", 16);
		
		setDefault("Lobby.line.16", "[out]");
		setDefault("Lobby.line.15", "[out]");
		setDefault("Lobby.line.14", "[out]");
		setDefault("Lobby.line.13", "[out]");
		setDefault("Lobby.line.12", "[out]");
		setDefault("Lobby.line.11", "[team]");
		setDefault("Lobby.line.10", "[out]");
		setDefault("Lobby.line.9", "[out]");
		setDefault("Lobby.line.8", "[blank]");
		setDefault("Lobby.line.7", "[kills]");
		setDefault("Lobby.line.6", "[deaths]");
		setDefault("Lobby.line.5", "[bar]");
		setDefault("Lobby.line.4", "[coins]");
		setDefault("Lobby.line.3", "[points]");
		setDefault("Lobby.line.2", "[bar]");
		setDefault("Lobby.line.1", "[kit]");
		
		setDefault("Ingame.lines", 16);
		
		setDefault("Ingame.line.16", "[out]");
		setDefault("Ingame.line.15", "[blank]");
		setDefault("Ingame.line.14", "[alive]");
		setDefault("Ingame.line.13", "[specs]");
		setDefault("Ingame.line.12", "[blank]");
		setDefault("Ingame.line.11", "[kit]");
		setDefault("Ingame.line.10", "[team]");
		setDefault("Ingame.line.9", "[blank]");
		setDefault("Ingame.line.8", "[center]");
		setDefault("Ingame.line.7", "[border]");
		setDefault("Ingame.line.6", "[blank]");
		setDefault("Ingame.line.5", "[time infos]");
		setDefault("Ingame.line.4", "[bar]");
		setDefault("Ingame.line.3", "[out]");
		setDefault("Ingame.line.2", "[out]");
		setDefault("Ingame.line.1", "[out]");
	}

}
