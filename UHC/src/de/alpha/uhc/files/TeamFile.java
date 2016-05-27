package de.alpha.uhc.files;

import de.alpha.uhc.Core;
import de.alpha.uhc.aclasses.ATeam;
import de.popokaka.alphalibary.file.SimpleFile;

public class TeamFile {
	
	private Core pl;
	
	public TeamFile(Core c) {
		this.pl = c;
	}

    private  final SimpleFile file = getTeamFile();

    public  SimpleFile getTeamFile() {
        return new SimpleFile("plugins/UHC", "teams.yml");
    }

    public  void addDefaultTeams() {

        if (!file.isConfigurationSection("Teams")) {

            file.setDefault("Teams.Red.name", "Red");
            file.setDefault("Teams.Red.color", "red");
            file.setDefault("Teams.Red.maxSize", 2);

            file.setDefault("Teams.Yellow.name", "Yellow");
            file.setDefault("Teams.Yellow.color", "yellow");
            file.setDefault("Teams.Yellow.maxSize", 2);

            file.setDefault("Teams.Blue.name", "Blue");
            file.setDefault("Teams.Blue.color", "blue");
            file.setDefault("Teams.Blue.maxSize", 2);

            file.setDefault("Teams.DarkGreen.name", "Dark Green");
            file.setDefault("Teams.DarkGreen.color", "dark_green");
            file.setDefault("Teams.DarkGreen.maxSize", 2);

        }
    }

    public  String getTeamColorAsString(String team) {

        for (String names : file.getConfigurationSection("Teams").getKeys(false)) {
            if (team.equalsIgnoreCase(file.getString("Teams." + names + ".name"))) {
                return file.getString("Teams." + names + ".color").toUpperCase();
            }
        }
        return "null";
    }

    public  void loadTeams() {

        for (String names : file.getConfigurationSection("Teams").getKeys(false)) {
            if (!(ATeam.getTeamNames().contains(file.getString("Teams." + names + ".name")))) {
                ATeam.getTeamNames().add(file.getString("Teams." + names + ".name"));
            }
            if (!(ATeam.getTeamColors().contains(file.getString("Teams." + names + ".color")))) {
                ATeam.getTeamColors().add(file.getString("Teams." + names + ".color"));
            }
            if (!(ATeam.getTeamMax().containsKey("Teams." + names + ".maxSize"))) {
                ATeam.getTeamMax().put(file.getString("Teams." + names + ".name"), file.getInt("Teams." + names + ".maxSize"));
            }
        }
    }
}
