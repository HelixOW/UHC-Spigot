package de.alpha.uhc.files;

import de.alpha.uhc.teams.ATeam;
import net.minetopix.library.main.file.SimpleFile;

public class TeamFile {
	
	public static SimpleFile getTeamFile() {
        return new SimpleFile("plugins/UHC", "teams.yml");
    }
	
	private static SimpleFile file = getTeamFile();
	
	public static void addDefaultTeams() {
		
		if(file.isConfigurationSection("Teams") == false) {
			
			file.setDefault("Teams.Red.name", "red");
			file.setDefault("Teams.Red.color", "red");
			
			file.setDefault("Teams.Yellow.name", "yellow");
			file.setDefault("Teams.Yellow.color", "yellow");
			
			file.setDefault("Teams.Blue.name", "blue");
			file.setDefault("Teams.Blue.color", "blue");
			
		}
	}
	
	public static String getTeamColorAsString(String team) {
		
		for(String names : file.getConfigurationSection("Teams").getKeys(false)) {
			if(team.equals(file.getString("Teams."+names+".name"))) {
				return file.getString("Teams."+names+".color").toUpperCase();
			}
		}
		return "null";
	}
	
	public static void loadTeams() {
		
		for(String names : file.getConfigurationSection("Teams").getKeys(false)) {
			if(!(ATeam.teamNames.contains(file.getString("Teams."+names+".name")))) {
				ATeam.teamNames.add(file.getString("Teams."+names+".name"));
			}
			if(!(ATeam.teamColors.contains(file.getString("Teams."+names+".color")))) {
				ATeam.teamColors.add(file.getString("Teams."+names+".color"));
			}
		}
	}
}
