package de.alpha.uhc.files;

import de.alpha.uhc.Core;
import de.alpha.uhc.commands.StartCommand;
import de.alpha.uhc.commands.StatsCommand;
import de.popokaka.alphalibary.file.SimpleFile;

public class CommandsFile {
	
	private Core pl;
	
	public CommandsFile(Core c) {
		this.pl = c;
	}

    private  final SimpleFile file = getCmdFile();

    private  SimpleFile getCmdFile() {
        return new SimpleFile("plugins/UHC", "commands.yml");
    }

    public  void addCommands() {
        file.setDefault("Use start command", true);
        file.setDefault("Use stats command", true);

        file.setDefault("start command disabled", "&7Please use&8: &b/uhc start");
        file.setDefault("stats command disabled", "&7Please use&8: &b/uhc stats");
    }

    public  void loadCommands() {
        StartCommand.setUse(file.getBoolean("Use start command"));
        StatsCommand.setUs(file.getBoolean("Use stats command"));


        StartCommand.setErr(file.getColorString("start command disabled"));
        StatsCommand.setEr(file.getColorString("stats command disabled"));

    }

}
