package de.alphahelix.alphalibary.scoreboard;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import de.alphahelix.alphalibary.AlphaPlugin;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleScoreboard<P extends AlphaPlugin> {

    private static final List<ChatColor> colors = Arrays.asList(ChatColor.values());
    private final List<BoardLine> boardLines = new ArrayList<>();
    private Scoreboard scoreboard = null;
    private Objective objective = null;
    private P plugin;

    public SimpleScoreboard(P pl, Player p, String scoreboardRegisterName) {
        this.plugin = pl;
        scoreboard = p.getScoreboard();
        objective = scoreboard.getObjective(scoreboardRegisterName);

        for (int i = 0; i < colors.size(); i++) {
            final ChatColor color = colors.get(i);
            final Team team = scoreboard.getTeam("line" + i);
            boardLines.add(new BoardLine(color, i, team));
        }
    }

    public SimpleScoreboard(P pl, String scoreboardRegisterName, String displayName, String iden, String... lines) {
        Validate.isTrue(lines.length < colors.size(), "Too many lines!");

        this.plugin = pl;

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
            setValue(i, lines[i], iden);
    }

    private BoardLine getBoardLine(final int line) {
        for(BoardLine lines : boardLines) {
            if(lines.getLine() == line) {
                return lines;
            }
        }
        return null;
    }

    public void setValue(int line, String value, final String iden) {
        if (!value.contains(iden)) {
            Iterable<String> res = Splitter.fixedLength(value.length() / 2).split(value);
            String[] text = Iterables.toArray(res, String.class);
            final BoardLine bl = getBoardLine(line);
            final String cc = ChatColor.getLastColors(text[0]);

            objective.getScore(bl.getColor().toString()).setScore(line);

            bl.getTeam().setPrefix(text[0]);
            bl.getTeam().setSuffix(text[1].startsWith("?") ? text[1] : cc + text[1]);
        } else {

            final String[] text = value.split(iden);
            final BoardLine bl = getBoardLine(line);

            if (objective == null) return;
            if (objective.getScore(bl.getColor().toString()) == null) return;
            objective.getScore(bl.getColor().toString()).setScore(line);

            if (text[0].length() > 12) {
                if (text[1].length() > 12) {
                    final Scroller left = new Scroller(text[0], 10, 2, '?');
                    final Scroller right = new Scroller(text[1], 8, 2, '?');

                    bl.getTeam().setPrefix(left.next());
                    bl.getTeam().setSuffix(iden + right.next());

                    new BukkitRunnable() {
                        public void run() {
                            bl.getTeam().setPrefix(left.next());
                            bl.getTeam().setSuffix(iden + right.next());
                        }
                    }.runTaskTimerAsynchronously(getPluginInstance(), 0, 10);
                }

                // When only left is too long
                else {
                    final Scroller left = new Scroller(text[0], 10, 2, '?');

                    bl.getTeam().setPrefix(left.next());
                    bl.getTeam().setSuffix(iden + text[1]);

                    new BukkitRunnable() {
                        public void run() {
                            bl.getTeam().setPrefix(left.next());
                        }
                    }.runTaskTimerAsynchronously(getPluginInstance(), 0, 10);
                }
            }

            // When left is correct
            else {

                if (text[1].length() > 12) {
                    final Scroller right = new Scroller(text[1], 8, 2, '?');

                    bl.getTeam().setPrefix(text[0]);
                    bl.getTeam().setSuffix(iden + right.next());

                    new BukkitRunnable() {
                        public void run() {
                            bl.getTeam().setSuffix(iden + right.next());
                        }
                    }.runTaskTimerAsynchronously(getPluginInstance(), 0, 10);
                }

                // When everything is perfect
                else {

                    bl.getTeam().setPrefix(text[0]);
                    bl.getTeam().setSuffix(iden + text[1]);
                }
            }
        }
    }

    public void updateValue(final int line, final String value, final String identifier) {
        if (!value.contains(identifier))
            return;

        final String[] text = value.split(identifier);
        final BoardLine bl = getBoardLine(line);

        if (text[0].length() > 12) {
            if (text[1].length() > 12) {
                final Scroller left = new Scroller(text[0], 10, 2, '?');
                final Scroller right = new Scroller(text[1], 8, 2, '?');

                bl.getTeam().setPrefix(left.next());
                bl.getTeam().setSuffix(identifier + right.next());

                new BukkitRunnable() {
                    public void run() {
                        bl.getTeam().setPrefix(left.next());
                        bl.getTeam().setSuffix(identifier + right.next());
                    }
                }.runTaskTimerAsynchronously(getPluginInstance(), 0, 10);
            }

            // When only left is too long
            else {
                final Scroller left = new Scroller(text[0], 10, 2, '?');

                bl.getTeam().setPrefix(left.next());
                bl.getTeam().setSuffix(identifier + text[1]);

                new BukkitRunnable() {
                    public void run() {
                        bl.getTeam().setPrefix(left.next());
                    }
                }.runTaskTimerAsynchronously(getPluginInstance(), 0, 10);
            }
        }

        // When left is correct
        else {
            if (text[1].length() > 12) {
                final Scroller right = new Scroller(text[1], 8, 2, '?');

                bl.getTeam().setPrefix(text[0]);
                bl.getTeam().setSuffix(identifier + right.next());

                new BukkitRunnable() {
                    public void run() {
                        bl.getTeam().setSuffix(identifier + right.next());
                    }
                }.runTaskTimerAsynchronously(getPluginInstance(), 0, 10);
            }

            // When everything is perfect
            else {
                bl.getTeam().setPrefix(text[0]);
                bl.getTeam().setSuffix(identifier + text[1]);
            }
        }
    }

    public void removeLine(int line) {
        final BoardLine boardLine = getBoardLine(line);
        Validate.notNull(boardLine, "Unable to find BoardLine with index of " + line + ".");
        scoreboard.resetScores(boardLine.getColor().toString());
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public P getPluginInstance() {
        return plugin;
    }

    public void setPluginInstance(P plugin) {
        this.plugin = plugin;
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
