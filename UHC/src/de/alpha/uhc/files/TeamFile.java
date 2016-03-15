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
			
			file.setDefault("Teams.Red.name", "Red");
			file.setDefault("Teams.Red.color", "red");
			
			file.setDefault("Teams.Yellow.name", "Yellow");
			file.setDefault("Teams.Yellow.color", "yellow");
			
			file.setDefault("Teams.Blue.name", "Blue");
			file.setDefault("Teams.Blue.color", "blue");
			
			file.setDefault("Teams.DarkBlue.name", "Dark Green");
			file.setDefault("Teams.DarkBlue.color", "dark_green");
			
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
