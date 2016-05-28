package de.alpha.uhc.files;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.file.SimpleFile;

public class CommandsFile {
	
	
	private Core pl;
	private Registery r;
	
	public CommandsFile(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
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
        r.getStartCommand().setUse(file.getBoolean("Use start command"));
        r.getStatsCommand().setUs(file.getBoolean("Use stats command"));


        r.getStartCommand().setErr(file.getColorString("start command disabled"));
        r.getStatsCommand().setEr(file.getColorString("stats command disabled"));

    }

}
