package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class TeamFile extends EasyFile {

	public TeamFile(UHC uhc) {
		super("teams.uhc", uhc);
	}

	@Override
	public void addValues() {
		setDefault("Teams enabled", true);
		setDefault("GUI.Name", "&bTeams");
		setDefault("GUI.Content Material", "WOOL");
		setDefault("Team.Item", "BED");
		setDefault("Team.Item Name", "&bTeams");
		setDefault("Team.Item Slot", 2);
		setDefault("Team.Chat", "@");
		addTeam("Test Team", "7", 2, 1, 0, true);
	}
	
	public void addTeam(String name, String prefix, int maxPlayer, int data, int slot, boolean colored) {
		setDefault("Teams."+name+".name", name);
		setDefault("Teams."+name+".prefix", prefix);
		setDefault("Teams."+name+".data", data);
		setDefault("Teams."+name+".max Players", maxPlayer);
		setDefault("Teams."+name+".slot", slot);
		setDefault("Teams."+name+".colored Name", colored);
	}
}
