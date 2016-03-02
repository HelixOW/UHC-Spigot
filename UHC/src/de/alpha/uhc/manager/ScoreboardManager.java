package de.alpha.uhc.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import de.alpha.border.Border;
import de.alpha.uhc.Core;
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.teams.TeamManager;
import de.alpha.uhc.utils.Stats;

public class ScoreboardManager {
	
	public static String lobbytitle;
	public static String lobbyKills;
	public static String lobbyDeaths;
	public static String lobbyCoins;
	
	public static String ingametitle;
	public static String ingamePlayersLiving;
	public static String ingameSpectators;
	public static String ingameKit;
	public static String center;
	public static String team;
	public static String noTeam;
	
	public static void setLobbyBoard(Player p) {
		Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective o = s.registerNewObjective("Lobby", "dummy");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if(lobbytitle.contains("[Player]")) {
			String b = lobbytitle.replace("[Player]", p.getDisplayName());
			o.setDisplayName(b);
		}else {
			o.setDisplayName(lobbytitle);
		}

		{
			Score a = o.getScore(lobbyKills);
			a.setScore(new Stats(p).getKills());
		}
		
		{
			Score a = o.getScore(lobbyDeaths);
			a.setScore(new Stats(p).getDeaths());
		}
		
		{
			Score a = o.getScore(lobbyCoins);
			a.setScore(new Stats(p).getCoins());
		}
		
		
		p.setScoreboard(s);
		
	}
	private static Scoreboard s;
	private static Objective o;
	
		public static void setInGameBoard(Player p) {
		
		s = Bukkit.getScoreboardManager().getNewScoreboard();
		
		o = s.registerNewObjective("InGame", "dummy");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if(ingametitle.contains("[Player]")) {
			String b = ingametitle.replace("[Player]", p.getDisplayName());
			o.setDisplayName(b);
		}else {
			o.setDisplayName(ingametitle);
		}
		
		{
			Score a = o.getScore(ingamePlayersLiving);
			a.setScore(Core.getInGamePlayers().size());
		}
		{
			Score a = o.getScore(ingameSpectators);
			a.setScore(Core.getSpecs().size());
		}
		{
			Score a = o.getScore("  ");
			a.setScore(-1);
		}
		{
			Score a = o.getScore(ingameKit);
			a.setScore(-2);
		}
		{
			Score a = o.getScore("§7 "+LobbyListener.getSelKit(p));
			a.setScore(-3);
		}
		{
			Score a = o.getScore(" ");
			a.setScore(-4);
		}
		{
			Score a = o.getScore(team);
			a.setScore(-5);
		}
		{
			if(TeamManager.hasTeam(p)) {
				Score a = o.getScore(ChatColor.valueOf(TeamManager.getTeam(p).getTeamColor().toString()) + TeamManager.getTeam(p).getName());
				a.setScore(-6);
			} else {
				Score a = o.getScore(noTeam);
				a.setScore(-6);
			}
		}
		{
			Score a = o.getScore(center);
			a.setScore((int) p.getLocation().distance(Border.arena));
		}
		
		
		p.setScoreboard(s);
		
	}
	
	public static void updateInGameBoard(Player p) {
		Scoreboard sc = p.getScoreboard();
		Score a = sc.getObjective("InGame").getScore(center);
		if(SpawnFileManager.getSpawn() == null) {
			a.setScore((int) p.getLocation().distance(p.getWorld().getSpawnLocation()));
		} else {
			a.setScore((int) p.getLocation().distance(Border.arena));
		}
	}
}
