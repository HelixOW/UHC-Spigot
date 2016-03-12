package de.alpha.uhc.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import de.alpha.border.Border;
import de.alpha.uhc.Core;
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.teams.ATeam;
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
	public static String border;
	
	public static void setLobbyBoard(Player p) {
		Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective o = s.registerNewObjective("UHCLobby", "dummy");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if(lobbytitle.contains("[Player]")) {
			String b = lobbytitle.replace("[Player]", p.getDisplayName());
			o.setDisplayName(b);
		}else {
			o.setDisplayName(lobbytitle);
		}

		{
			Score a = o.getScore(lobbyKills);
			a.setScore(7);
		}
		
		{
			Score a = o.getScore(Integer.toString(new Stats(p).getKills()));
			a.setScore(6);
		}
		
		{
			Score a = o.getScore(" ");
			a.setScore(5);
		}
		
		{
			Score a = o.getScore(lobbyDeaths);
			a.setScore(4);
		}
		
		{
			Score a = o.getScore(Integer.toString(new Stats(p).getDeaths()));
			a.setScore(3);
		}
		
		{
			Score a = o.getScore("  ");
			a.setScore(2);
		}
		
		{
			Score a = o.getScore(lobbyCoins);
			a.setScore(1);
		}
		
		{
			Score a = o.getScore(Integer.toString(new Stats(p).getCoins()));
			a.setScore(0);
		}
		
		p.setScoreboard(s);
		
	}

	
	private static Scoreboard s;
	private static Objective o;
	
		public static Scoreboard setInGameBoard(Player p) {
		
		s = Bukkit.getScoreboardManager().getNewScoreboard();
		
		o = s.registerNewObjective("UHCInGame", "dummy");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if(ingametitle.contains("[Player]")) {
			String b = ingametitle.replace("[Player]", p.getDisplayName());
			o.setDisplayName(b);
		}else {
			o.setDisplayName(ingametitle);
		}
		
		{
			Score b = o.getScore("   ");
			b.setScore(-1);
		}
		
		{
			if(ATeam.hasTeam(p)) {
				Score a = o.getScore(team);
				a.setScore(-2);
				
				Score b = o.getScore("  "+ATeam.getTeamColor(ATeam.getPlayerTeam(p)) + ATeam.getPlayerTeam(p));
				b.setScore(-3);
				
				Score c = o.getScore("  ");
				c.setScore(-4);
			} else {
				Score a = o.getScore(team);
				a.setScore(-2);
				
				Score b = o.getScore(" ");
				b.setScore(-3);
			}
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
			Score a = o.getScore(center);
			a.setScore((int) p.getLocation().distance(Border.arena));
		}
		
		{
			Score a = o.getScore(border);
			a.setScore(Border.size);
		}
		
		{
			Score a = o.getScore(ingameKit);
			a.setScore(-5);
		}
		
		{
			Score a = o.getScore("§7  " + LobbyListener.getSelKit(p));
			a.setScore(-6);
		}
		
		Objective ob = s.registerNewObjective("UHCHealthName", "health");
		ob.setDisplaySlot(DisplaySlot.BELOW_NAME);
		ob.setDisplayName("§4❤");
		
		Objective obj = s.registerNewObjective("UHCHealthList", "health");
		obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		obj.setDisplayName("§4❤");
		
		p.setHealth(p.getHealth());
		p.setScoreboard(s);
		return s;
		
	}
		
	public static void updatePlayerSpecScore() {
		try {
			o.getScore(ingameSpectators).setScore(Core.getSpecs().size());
		} catch (Exception e) {}
	}
		
	public static void updatePlayerIGScore() {
		try {
			o.getScore(ingamePlayersLiving).setScore(Core.getInGamePlayers().size());
		} catch (Exception e) {}
	}
	
	public static void updateCenterScore(Player p) {
		try {
			Scoreboard sc = p.getScoreboard();
			Objective ob = sc.getObjective("UHCInGame");
			if(SpawnFileManager.getSpawn() == null) {
				ob.getScore(center).setScore((int) p.getLocation().distance(p.getWorld().getSpawnLocation()));
			} else {
				ob.getScore(center).setScore((int) p.getLocation().distance(SpawnFileManager.getSpawn()));
			}
		} catch (Exception e) {}
	}
	
	public static void updateBorderScore(Player p) {
		try {
			Scoreboard sc = p.getScoreboard();
			Objective ob = sc.getObjective("UHCInGame");
			ob.getScore(border).setScore(Border.size);
		} catch (Exception e) {}
	}
}
