package de.popokaka.alphalibary.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import de.alphahelix.uhc.UHC;

public class SimpleScoreboard {

	private static final List<ChatColor> colors = Arrays.asList(ChatColor.values());

	private Scoreboard scoreboard = null;
	private Objective objective = null;
	private Scroller scroller;

	private final List<BoardLine> boardLines = new ArrayList<>();

	public SimpleScoreboard(Player p, String scoreboardRegisterName) {
		scoreboard = p.getScoreboard();
		objective = scoreboard.getObjective(scoreboardRegisterName);

		for (int i = 0; i < colors.size(); i++) {
			final ChatColor color = colors.get(i);
			final Team team = scoreboard.getTeam("line" + i);
			boardLines.add(new BoardLine(color, i, team));
		}
	}

	public SimpleScoreboard(String scoreboardRegisterName, String displayName, String... lines) {
		Validate.isTrue(lines.length < colors.size(), "Too many lines!");

		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective(scoreboardRegisterName, "dummy");

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(displayName);

		for (int i = 0; i < colors.size(); i++) {
			final ChatColor color = colors.get(i);
			final Team team = scoreboard.registerNewTeam("line" + i);

			team.addEntry(color.toString());
			boardLines.add(new BoardLine(color, i, team));
		}

		for (int i = 0; i < lines.length; i++)
			setValue(i, lines[i]);
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
		Iterable<String> res = Splitter.fixedLength(value.length() / 2).split(value);
		String[] pns = Iterables.toArray(res, String.class);
		BoardLine bl = getBoardLine(line);

		String lastColorPartOne = ChatColor.getLastColors(pns[0]);
		String firstColorPartTwo = getFirstColorOfText(pns[1]);

		String partOne = pns[0];
		String partTwo = pns[1];

		Validate.notNull(bl, "Unable to find BoardLine with index of " + line + ".");

		objective.getScore(bl.getColor().toString()).setScore(line);

		bl.getTeam().setPrefix(partOne);
		if (partTwo.startsWith("§"))
			for (ChatColor c : ChatColor.values()) {
				String t = firstColorPartTwo + partTwo.replaceFirst("" + c.getChar(), "");
				bl.getTeam().setSuffix(t);
			}
		else {
			String t = lastColorPartOne + partTwo;
			bl.getTeam().setSuffix(t);
		}
	}
	
	public void updateValue(final int line, final  String value, final String identifier) {
		if(!value.contains(identifier)) return;
		
		final String[] text = value.split(identifier);
		final BoardLine bl = getBoardLine(line);
		
		scroller = new Scroller(text[1], 10, 2, '§');
		
		bl.getTeam().setPrefix(text[0]);
		bl.getTeam().setSuffix(text[1]);
		
		new BukkitRunnable() {
			public void run() {
				bl.getTeam().setPrefix(text[0] + identifier);
				bl.getTeam().setSuffix(scroller.next());
			}
		}.runTaskTimerAsynchronously(UHC.getInstance(), 0, 10);
	}

//	public void updateValue(int line, String value) {
//		Iterable<String> res = Splitter.fixedLength(value.length() / 2).split(value);
//		String[] pns = Iterables.toArray(res, String.class);
//		final BoardLine bl = getBoardLine(line);
//
//		final String lastColorPartOne = ChatColor.getLastColors(pns[0]);
//		final String firstColorPartTwo = getFirstColorOfText(pns[1]);
//
//		final String partOne = pns[0];
//		final String partTwo = pns[1];
//
//		Validate.notNull(bl, "Unable to find BoardLine with index of " + line + ".");
//
//		objective.getScore(bl.getColor().toString()).setScore(line);
//
//		bl.getTeam().setPrefix(partOne);
//		if (partTwo.startsWith("§"))
//			for (ChatColor c : ChatColor.values()) {
//				String t = firstColorPartTwo + partTwo.replaceFirst("" + c.getChar(), "");
//				scrollerPart1 = new Scroller(t, 12, 5, '§');
//				bl.getTeam().setSuffix(t);
//			}
//		else {
//			String t = lastColorPartOne + partTwo;
//			scrollerPart2 = new Scroller(t, 12, 5, '§');
//			bl.getTeam().setSuffix(t);
//		}
//
//		new BukkitRunnable() {
//			public void run() {
//				if (partTwo.startsWith("§"))
//					bl.getTeam().setSuffix(scrollerPart1.next());
//				else
//					bl.getTeam().setSuffix(scrollerPart2.next());
//			}
//		}.runTaskTimer(UHC.getInstance(), 0, 10);
//	}

	private String getFirstColorOfText(String text) {
		if (text.contains("§") && !text.endsWith("§"))
			return "" + ChatColor.getByChar(text.charAt(text.indexOf("§") + 1)).getChar();
		return "";
	}

	public void removeLine(int line) {
		final BoardLine boardLine = getBoardLine(line);
		Validate.notNull(boardLine, "Unable to find BoardLine with index of " + line + ".");
		scoreboard.resetScores(boardLine.getColor().toString());
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

}

class BoardLine {

	private final ChatColor color;
	private final int line;
	private final Team team;

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