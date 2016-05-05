package de.alpha.uhc.files;

import de.alpha.uhc.commands.StartCommand;
import de.alpha.uhc.commands.StatsCommand;
import de.popokaka.alphalibary.file.SimpleFile;

public class CommandsFile {
	
	public static SimpleFile getCmdFile() {
		return new SimpleFile("plugins/UHC", "commands.yml");
	}
	
	private static SimpleFile file = getCmdFile();
	
	public static void addCommands() {
		file.setDefault("Use /start command", "false");
		file.setDefault("Use /stats command", "false");
		
		file.setDefault("/start disabled", "§7Please use§8: §b/uhc start");
		file.setDefault("/stats disabled", "§7Please use§8: §b/uhc stats");
	}
	
	public static void loadCommands() {
		StartCommand.setUse(file.getBoolean("Use /start command"));
		StartCommand.setErr(file.getColorString("/start disabled"));
		
		StatsCommand.setUse(file.getBoolean("Use /stats command"));
		StatsCommand.setErr(file.getColorString("/stats disabled"));
	}

}
