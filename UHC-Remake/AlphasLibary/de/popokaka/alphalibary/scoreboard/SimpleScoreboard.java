package de.popokaka.alphalibary.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SimpleScoreboard {
	
	private static final List<ChatColor> colors = Arrays.asList(ChatColor.values()); //the colors

    private final Scoreboard scoreboard; //the scoreboard
    private final Objective objective; //the objective

    private final List<BoardLine> boardLines = new ArrayList<>(); //where we will store our lines

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
        final BoardLine boardLine = getBoardLine(line); //get our board line
        Validate.notNull(boardLine, "Unable to find BoardLine with index of " + line + "."); //make sure it exists
        objective.getScore(boardLine.getColor().toString()).setScore(line); //set the line on the scoreboard
        boardLine.getTeam().setPrefix(value); //set the actual display value. if you need more than 16 characters, you are able to use the suffix but doing this dynamically is a little harder
    }
    
    public void removeLine(int line) {
        final BoardLine boardLine = getBoardLine(line);
        Validate.notNull(boardLine, "Unable to find BoardLine with index of " + line + "."); //make sure it exists
        scoreboard.resetScores(boardLine.getColor().toString()); //remove our line from the scoreboard
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