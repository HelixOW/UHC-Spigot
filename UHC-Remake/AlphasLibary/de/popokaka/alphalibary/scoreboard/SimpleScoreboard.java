package de.popokaka.alphalibary.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SimpleScoreboard {
	
	private static final List<ChatColor> colors = Arrays.asList(ChatColor.values()); //the colors

    private Scoreboard scoreboard = null; //the scoreboard
    private Objective objective = null; //the objective

    private final List<BoardLine> boardLines = new ArrayList<>(); //where we will store our lines
    
    public SimpleScoreboard(Player p, String scoreboardRegisterName) {
		scoreboard = p.getScoreboard();
		objective = scoreboard.getObjective(scoreboardRegisterName);
		for(int i=0; i < colors.size(); i++) { //loop through the color values
            final ChatColor color = colors.get(i);
            final Team team = scoreboard.getTeam("line" + i);
            boardLines.add(new BoardLine(color, i, team)); //register a BoardLine for this team
        }
	}

	public SimpleScoreboard(String scoreboardRegisterName, String displayName, String... lines) {
        Validate.isTrue(lines.length < colors.size(), "Too many lines!"); //make sure there isn't too many lines
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard(); //get a new scoreboard
        objective = scoreboard.registerNewObjective(scoreboardRegisterName, "dummy"); //create a dummy objective
        objective.setDisplaySlot(DisplaySlot.SIDEBAR); //set the display slot
        objective.setDisplayName(displayName); //set the display name
        for(int i=0; i < colors.size(); i++) { //loop through the color values
            final ChatColor color = colors.get(i);
            final Team team = scoreboard.registerNewTeam("line" + i); //register our team for that line
            team.addEntry(color.toString()); //add the entry to the board
            boardLines.add(new BoardLine(color, i, team)); //register a BoardLine for this team
        }
        for(int i=0; i < lines.length; i++) setValue(i, lines[i]); //set the values of our lines from intialization
	}
    
    private BoardLine getBoardLine(final int line) {
        return boardLines.stream().filter(new Predicate<BoardLine>() {
			@Override
			public boolean test(BoardLine boardLine) {
				return boardLine.getLine() == line;
			}
		}).findFirst().orElse(null);
    }
    
    public void setValue(int line, String value) {
    	final int mid = (value.length() / 2);
    	String cc = "";
    	if(value.contains("§")) {
    		cc += "§" + value.charAt(value.indexOf("§")+1);
    	}
    	String[] parts = {value.substring(0, mid), value.substring(mid)};
        final BoardLine boardLine = getBoardLine(line); //get our board line
        Validate.notNull(boardLine, "Unable to find BoardLine with index of " + line + "."); //make sure it exists
        objective.getScore(boardLine.getColor().toString()).setScore(line); //set the line on the scoreboard
        boardLine.getTeam().setPrefix(parts[0].endsWith("§") ? parts[0].substring(0, parts[0].length()-1) : parts[0]); //set the actual display value. if you need more than 16 characters, you are able to use the suffix but doing this dynamically is a little harder
        boardLine.getTeam().setSuffix(cc + (parts[1].startsWith("§") ? parts[1].substring(0) : parts[1]));
    }
    
    public void updateValue(int line, String value) {
    	final int mid = (value.length() / 2);
    	String cc = "§";
    	String[] parts = {value.substring(0, mid), value.substring(mid)};
        final BoardLine boardLine = getBoardLine(line); //get our board line
        Validate.notNull(boardLine, "Unable to find BoardLine with index of " + line + "."); //make sure it exists
        objective.getScore(boardLine.getColor().toString()).setScore(line); //set the line on the scoreboard
        
        if(parts[0].substring(0, parts[0].length()-1).endsWith("§")) {
        	cc += parts[0].charAt(parts[0].lastIndexOf("§")+1);
        } else if(parts[0].contains("§")){
        	if(parts[0].lastIndexOf("§")+1 > parts[0].lastIndexOf("§")) {
        		cc += parts[1].charAt(0);
        	} else {
        		cc += parts[0].charAt(parts[0].lastIndexOf("§")+1);
        	}
        } else if(parts[1].contains("§")) {
        	if(parts[1].indexOf("§")+1 < parts[1].indexOf("§")) {
        		cc += parts[1].charAt(parts[1].lastIndexOf("§")+1);
        	}
        } else {
        	cc = "";
        }
        
        if(parts[0].endsWith("§")) {
        	boardLine.getTeam().setPrefix(parts[0].substring(0, parts[0].length()-1));
        	boardLine.getTeam().setSuffix(cc + parts[1].substring(1));
        } else if(parts[0].substring(0, parts[0].length()-1).endsWith("§")) {
        	boardLine.getTeam().setPrefix(parts[0]);
        	boardLine.getTeam().setSuffix(parts[1]);
        } else {
        	boardLine.getTeam().setPrefix(parts[0]);
        	boardLine.getTeam().setSuffix(parts[1]);
        }
    }
    
    public void removeLine(int line) {
        final BoardLine boardLine = getBoardLine(line);
        Validate.notNull(boardLine, "Unable to find BoardLine with index of " + line + "."); //make sure it exists
        scoreboard.resetScores(boardLine.getColor().toString()); //remove our line from the scoreboard
    }
    
    public Scoreboard getScoreboard() {
    	return scoreboard;
    }

} class BoardLine {
	
	private final ChatColor color; //the chatcolor name of our entry
    private final int line; //the line that the team will be on
    private final Team team; //the actual team itself

    public BoardLine(ChatColor color, int line, Team team) {
        this.color = color;
        this.line = line;
        this.team = team;
    }

    public ChatColor getColor() {
        return color;
    }

    public int getLine() {
        return line;
    }

    public Team getTeam() {
        return team;
    }

}