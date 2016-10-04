package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class StatsFile extends EasyFile {

	public StatsFile(UHC uhc) {
		super("statsMessages.uhc", uhc);
	}
	
	@Override
	public void addValues() {
		setDefault("Command Errormessage", "&7Please use &c&l/stats <player>");
		setDefault("No Player", "&7The given Player doesn't exists.");
		setDefault("Name", "&6Name &7: &6[player]");
		setDefault("Rank", "&6Rank &7: &6[rank]");
		setDefault("Kills", "&6Kills &7: &6[kills]");
		setDefault("Deaths", "&6Deaths &7: &6[deaths]");
		setDefault("Coins", "&6Coins &7: &6[coins]");
		setDefault("Points", "&6Points &7: &6[points]");
		setDefault("Kits", "&6Kits &7: &6[kits]");
		setDefault("StatsNPC", "&5Stats");
	}
	
	public String getMessage() {
		return getColorString("Name") +"\n"
						+ getColorString("Rank") + "\n"
						+ getColorString("Kills") + "\n"
						+ getColorString("Deaths") + "\n"
						+ getColorString("Coins") + "\n"
						+ getColorString("Points") + "\n"
						+ getColorString("Kits") + "\n";
	}
	
	public String getErrorMessage() {
		return getColorString("Command Errormessage");
	}
	
	public String getNoPlayerMessage() {
		return getColorString("No Player");
	}
}
