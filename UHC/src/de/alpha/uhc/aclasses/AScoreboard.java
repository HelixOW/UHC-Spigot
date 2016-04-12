package de.alpha.uhc.aclasses;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.border.Border;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.Stats;

public class AScoreboard {
	
	private static boolean ShowLobbyScoreboard;
	private static boolean ShowLobbyKills;
	private static boolean ShowLobbyDeaths;
	private static boolean ShowLobbyCoins;
	private static boolean ShowLobbyPlayercount;
	private static boolean ShowLobbyTeam;
	private static boolean ShowLobbyKit;
	private static boolean ShowLobbyBar;
	
	private static String lobbyTitle;
	private static String lobbyKills;
	private static String lobbyDeaths;
	private static String lobbyCoins;
	private static String lobbyPlayercount;
	private static String lobbyTeam;
	private static String lobbyKit;
	private static String lobbyBar;
	
	private static boolean ShowInGameScoreboard;
	private static boolean ShowInGamePlayersLiving;
	private static boolean ShowInGameSpectators;
	private static boolean ShowInGameKit;
	private static boolean ShowInGameCenter;
	private static boolean ShowInGameTeam;
	private static boolean ShowInGameBorder;
	private static boolean ShowInGamePvP;
	private static boolean ShowInGameBar;
	
	private static String ingameTitle;
	private static String ingamePlayersLiving;
	private static String ingameSpectators;
	private static String ingameKit;
	private static String ingameCenter;
	private static String ingameTeam;
	private static String ingameBorder;
	private static String ingamePvP;
	private static String ingameBar;
	
	private static boolean ShowHealthUName;
	private static boolean ShowHealthInTab;
	
	private static String dmgin;
	private static String pvpin;
	
	public static synchronized boolean isShowLobbyScoreboard() {
		return ShowLobbyScoreboard;
	}

	public static synchronized void setShowLobbyScoreboard(boolean showLobbyScoreboard) {
		ShowLobbyScoreboard = showLobbyScoreboard;
	}

	public static synchronized boolean isShowLobbyKills() {
		return ShowLobbyKills;
	}

	public static synchronized void setShowLobbyKills(boolean showLobbyKills) {
		ShowLobbyKills = showLobbyKills;
	}

	public static synchronized boolean isShowLobbyDeaths() {
		return ShowLobbyDeaths;
	}

	public static synchronized void setShowLobbyDeaths(boolean showLobbyDeaths) {
		ShowLobbyDeaths = showLobbyDeaths;
	}

	public static synchronized boolean isShowLobbyCoins() {
		return ShowLobbyCoins;
	}

	public static synchronized void setShowLobbyCoins(boolean showLobbyCoins) {
		ShowLobbyCoins = showLobbyCoins;
	}

	public static synchronized boolean isShowLobbyPlayercount() {
		return ShowLobbyPlayercount;
	}

	public static synchronized void setShowLobbyPlayercount(boolean showLobbyPlayercount) {
		ShowLobbyPlayercount = showLobbyPlayercount;
	}

	public static synchronized boolean isShowLobbyTeam() {
		return ShowLobbyTeam;
	}

	public static synchronized void setShowLobbyTeam(boolean showLobbyTeam) {
		ShowLobbyTeam = showLobbyTeam;
	}

	public static synchronized boolean isShowLobbyKit() {
		return ShowLobbyKit;
	}

	public static synchronized void setShowLobbyKit(boolean showLobbyKit) {
		ShowLobbyKit = showLobbyKit;
	}

	public static synchronized boolean isShowLobbyBar() {
		return ShowLobbyBar;
	}

	public static synchronized void setShowLobbyBar(boolean showLobbyBar) {
		ShowLobbyBar = showLobbyBar;
	}

	public static synchronized String getLobbyTitle() {
		return lobbyTitle;
	}

	public static synchronized void setLobbyTitle(String lobbyTitle) {
		AScoreboard.lobbyTitle = lobbyTitle;
	}

	public static synchronized String getLobbyKills() {
		return lobbyKills;
	}

	public static synchronized void setLobbyKills(String lobbyKills) {
		AScoreboard.lobbyKills = lobbyKills;
	}

	public static synchronized String getLobbyDeaths() {
		return lobbyDeaths;
	}

	public static synchronized void setLobbyDeaths(String lobbyDeaths) {
		AScoreboard.lobbyDeaths = lobbyDeaths;
	}

	public static synchronized String getLobbyCoins() {
		return lobbyCoins;
	}

	public static synchronized void setLobbyCoins(String lobbyCoins) {
		AScoreboard.lobbyCoins = lobbyCoins;
	}

	public static synchronized String getLobbyPlayercount() {
		return lobbyPlayercount;
	}

	public static synchronized void setLobbyPlayercount(String lobbyPlayercount) {
		AScoreboard.lobbyPlayercount = lobbyPlayercount;
	}

	public static synchronized String getLobbyTeam() {
		return lobbyTeam;
	}

	public static synchronized void setLobbyTeam(String lobbyTeam) {
		AScoreboard.lobbyTeam = lobbyTeam;
	}

	public static synchronized String getLobbyKit() {
		return lobbyKit;
	}

	public static synchronized void setLobbyKit(String lobbyKit) {
		AScoreboard.lobbyKit = lobbyKit;
	}

	public static synchronized String getLobbyBar() {
		return lobbyBar;
	}

	public static synchronized void setLobbyBar(String lobbyBar) {
		AScoreboard.lobbyBar = lobbyBar;
	}

	public static synchronized boolean isShowInGameScoreboard() {
		return ShowInGameScoreboard;
	}

	public static synchronized void setShowInGameScoreboard(boolean showInGameScoreboard) {
		ShowInGameScoreboard = showInGameScoreboard;
	}

	public static synchronized boolean isShowInGamePlayersLiving() {
		return ShowInGamePlayersLiving;
	}

	public static synchronized void setShowInGamePlayersLiving(boolean showInGamePlayersLiving) {
		ShowInGamePlayersLiving = showInGamePlayersLiving;
	}

	public static synchronized boolean isShowInGameSpectators() {
		return ShowInGameSpectators;
	}

	public static synchronized void setShowInGameSpectators(boolean showInGameSpectators) {
		ShowInGameSpectators = showInGameSpectators;
	}

	public static synchronized boolean isShowInGameKit() {
		return ShowInGameKit;
	}

	public static synchronized void setShowInGameKit(boolean showInGameKit) {
		ShowInGameKit = showInGameKit;
	}

	public static synchronized boolean isShowInGameCenter() {
		return ShowInGameCenter;
	}

	public static synchronized void setShowInGameCenter(boolean showInGameCenter) {
		ShowInGameCenter = showInGameCenter;
	}

	public static synchronized boolean isShowInGameTeam() {
		return ShowInGameTeam;
	}

	public static synchronized void setShowInGameTeam(boolean showInGameTeam) {
		ShowInGameTeam = showInGameTeam;
	}

	public static synchronized boolean isShowInGameBorder() {
		return ShowInGameBorder;
	}

	public static synchronized void setShowInGameBorder(boolean showInGameBorder) {
		ShowInGameBorder = showInGameBorder;
	}

	public static synchronized boolean isShowInGamePvP() {
		return ShowInGamePvP;
	}

	public static synchronized void setShowInGamePvP(boolean showInGamePvP) {
		ShowInGamePvP = showInGamePvP;
	}

	public static synchronized boolean isShowInGameBar() {
		return ShowInGameBar;
	}

	public static synchronized void setShowInGameBar(boolean showInGameBar) {
		ShowInGameBar = showInGameBar;
	}

	public static synchronized String getIngameTitle() {
		return ingameTitle;
	}

	public static synchronized void setIngameTitle(String ingameTitle) {
		AScoreboard.ingameTitle = ingameTitle;
	}

	public static synchronized String getIngamePlayersLiving() {
		return ingamePlayersLiving;
	}

	public static synchronized void setIngamePlayersLiving(String ingamePlayersLiving) {
		AScoreboard.ingamePlayersLiving = ingamePlayersLiving;
	}

	public static synchronized String getIngameSpectators() {
		return ingameSpectators;
	}

	public static synchronized void setIngameSpectators(String ingameSpectators) {
		AScoreboard.ingameSpectators = ingameSpectators;
	}

	public static synchronized String getIngameKit() {
		return ingameKit;
	}

	public static synchronized void setIngameKit(String ingameKit) {
		AScoreboard.ingameKit = ingameKit;
	}

	public static synchronized String getIngameCenter() {
		return ingameCenter;
	}

	public static synchronized void setIngameCenter(String ingameCenter) {
		AScoreboard.ingameCenter = ingameCenter;
	}

	public static synchronized String getIngameTeam() {
		return ingameTeam;
	}

	public static synchronized void setIngameTeam(String ingameTeam) {
		AScoreboard.ingameTeam = ingameTeam;
	}

	public static synchronized String getIngameBorder() {
		return ingameBorder;
	}

	public static synchronized void setIngameBorder(String ingameBorder) {
		AScoreboard.ingameBorder = ingameBorder;
	}

	public static synchronized String getIngamePvP() {
		return ingamePvP;
	}

	public static synchronized void setIngamePvP(String ingamePvP) {
		AScoreboard.ingamePvP = ingamePvP;
	}

	public static synchronized String getIngameBar() {
		return ingameBar;
	}

	public static synchronized void setIngameBar(String ingameBar) {
		AScoreboard.ingameBar = ingameBar;
	}

	public static synchronized boolean isShowHealthUName() {
		return ShowHealthUName;
	}

	public static synchronized void setShowHealthUName(boolean showHealthUName) {
		ShowHealthUName = showHealthUName;
	}

	public static synchronized boolean isShowHealthInTab() {
		return ShowHealthInTab;
	}

	public static synchronized void setShowHealthInTab(boolean showHealthInTab) {
		ShowHealthInTab = showHealthInTab;
	}

	public static synchronized String getDmgin() {
		return dmgin;
	}

	public static synchronized void setDmgin(String dmgin) {
		AScoreboard.dmgin = dmgin;
	}

	public static synchronized String getPvpin() {
		return pvpin;
	}

	public static synchronized void setPvpin(String pvpin) {
		AScoreboard.pvpin = pvpin;
	}

	public static synchronized HashMap<Player, String> getA() {
		return A;
	}

	public static synchronized void setA(HashMap<Player, String> a) {
		A = a;
	}

	public static synchronized HashMap<Player, String> getB() {
		return B;
	}

	public static synchronized void setB(HashMap<Player, String> b) {
		B = b;
	}

	public static synchronized int getAa() {
		return aa;
	}

	public static synchronized void setAa(int aa) {
		AScoreboard.aa = aa;
	}

	public static synchronized int getBb() {
		return bb;
	}

	public static synchronized void setBb(int bb) {
		AScoreboard.bb = bb;
	}

	public static synchronized Scoreboard getSb() {
		return sb;
	}

	public static synchronized void setSb(Scoreboard sb) {
		AScoreboard.sb = sb;
	}

	public static synchronized Objective getObj() {
		return obj;
	}

	public static synchronized void setObj(Objective obj) {
		AScoreboard.obj = obj;
	}

	public static synchronized HashMap<Player, String> getlA() {
		return lA;
	}

	public static synchronized void setlA(HashMap<Player, String> lA) {
		AScoreboard.lA = lA;
	}

	public static synchronized HashMap<Player, String> getlB() {
		return lB;
	}

	public static synchronized void setlB(HashMap<Player, String> lB) {
		AScoreboard.lB = lB;
	}

	public static synchronized HashMap<Player, String> getlC() {
		return lC;
	}

	public static synchronized void setlC(HashMap<Player, String> lC) {
		AScoreboard.lC = lC;
	}

	public static synchronized HashMap<Player, String> getlD() {
		return lD;
	}

	public static synchronized void setlD(HashMap<Player, String> lD) {
		AScoreboard.lD = lD;
	}

	public static synchronized HashMap<Player, String> getlE() {
		return lE;
	}

	public static synchronized void setlE(HashMap<Player, String> lE) {
		AScoreboard.lE = lE;
	}

	public static synchronized HashMap<Player, String> getlF() {
		return lF;
	}

	public static synchronized void setlF(HashMap<Player, String> lF) {
		AScoreboard.lF = lF;
	}

	public static synchronized HashMap<Player, String> getlG() {
		return lG;
	}

	public static synchronized void setlG(HashMap<Player, String> lG) {
		AScoreboard.lG = lG;
	}

	public static synchronized int getLa() {
		return la;
	}

	public static synchronized void setLa(int la) {
		AScoreboard.la = la;
	}

	public static synchronized int getLb() {
		return lb;
	}

	public static synchronized void setLb(int lb) {
		AScoreboard.lb = lb;
	}

	public static synchronized int getLc() {
		return lc;
	}

	public static synchronized void setLc(int lc) {
		AScoreboard.lc = lc;
	}

	public static synchronized int getLd() {
		return ld;
	}

	public static synchronized void setLd(int ld) {
		AScoreboard.ld = ld;
	}

	public static synchronized int getLe() {
		return le;
	}

	public static synchronized void setLe(int le) {
		AScoreboard.le = le;
	}

	public static synchronized int getLf() {
		return lf;
	}

	public static synchronized void setLf(int lf) {
		AScoreboard.lf = lf;
	}

	public static synchronized int getLg() {
		return lg;
	}

	public static synchronized void setLg(int lg) {
		AScoreboard.lg = lg;
	}

	public static synchronized String getIngamePvPmsg() {
		return ingamePvPmsg;
	}

	public static synchronized void setIngamePvPmsg(String ingamePvPmsg) {
		AScoreboard.ingamePvPmsg = ingamePvPmsg;
	}

	private static HashMap<Player, String> A = new HashMap<Player, String>();
	private static HashMap<Player, String> B = new HashMap<Player, String>();
	
	private static int aa;
	private static int bb;
	
	private static Scoreboard sb;
	private static Objective obj;
	
	public static void setLobbyScoreboard(Player p) {
		if(ShowLobbyScoreboard == false) return;
		
		int score = 0;
		
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = sb.registerNewObjective("UHCLobby", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(lobbyTitle);
		
		if(ShowLobbyBar) {
			obj.getScore(lobbyBar).setScore(score);
			score--;
		}
		
		if(ShowLobbyKills) {
			String a = lobbyKills.replace("[kills]", ""+new Stats(p).getKills());
			obj.getScore(a).setScore(score);
			score--;
		}
		
		if(ShowLobbyDeaths) {
			String a = lobbyDeaths.replace("[deaths]", ""+new Stats(p).getDeaths());
			obj.getScore(a).setScore(score);
			score--;
		}
		
		if(ShowLobbyCoins) {
			String a = lobbyCoins.replace("[coins]", ""+new Stats(p).getCoins());
			obj.getScore(a).setScore(score);
			score--;
		}
		
		if(ShowLobbyBar) {
			obj.getScore("§a"+lobbyBar).setScore(score);
			score--;
		}
		
		if(ShowLobbyPlayercount) {
			String a = lobbyPlayercount.replace("[playercount]", Integer.toString(Bukkit.getOnlinePlayers().size()));
			obj.getScore(a).setScore(score);
			score--;
		}
		
		if(ShowLobbyBar) {
			obj.getScore("§b"+lobbyBar).setScore(score);
			score--;
		}
		
		if(ShowLobbyTeam) {
			String a = lobbyTeam.replace("[team]", ""+ATeam.getTeamColor(ATeam.getPlayerTeam(p))+ATeam.getPlayerTeam(p));
			obj.getScore(a).setScore(score);
			A.put(p, a);
			aa = score;
			score--;
		}
		
		if(ShowLobbyKit) {
			String a = lobbyKit.replace("[kit]", ""+LobbyListener.getSelKit(p));
			obj.getScore(a).setScore(score);
			B.put(p, a);
			bb = score;
			score--;
		}
		
		p.setScoreboard(sb);
	}
	
	public static void updateLobbyTeam(Player p) {
		Objective objP = p.getScoreboard().getObjective("UHCLobby");
		String a = lobbyTeam.replace("[team]", ""+ATeam.getTeamColor(ATeam.getPlayerTeam(p))+ATeam.getPlayerTeam(p));
		p.getScoreboard().resetScores(A.get(p));
		A.put(p, a);
		objP.getScore(a).setScore(aa);
	}
	
	public static void updateLobbyKit(Player p) {
		Objective objP = p.getScoreboard().getObjective("UHCLobby");
		String a = lobbyKit.replace("[kit]", ""+LobbyListener.getSelKit(p));
		p.getScoreboard().resetScores(B.get(p));
		B.put(p, a);
		objP.getScore(a).setScore(bb);
	}
	
	private static HashMap<Player, String> lA = new HashMap<Player, String>();
	private static HashMap<Player, String> lB = new HashMap<Player, String>();
	private static HashMap<Player, String> lC = new HashMap<Player, String>();
	private static HashMap<Player, String> lD = new HashMap<Player, String>();
	private static HashMap<Player, String> lE = new HashMap<Player, String>();
	private static HashMap<Player, String> lF = new HashMap<Player, String>();
	private static HashMap<Player, String> lG = new HashMap<Player, String>();
	
	
	private static int la;
	private static int lb;
	private static int lc;
	private static int ld;
	private static int le;
	private static int lf;
	private static int lg;
	
	public static void setInGameScoreboard(final Player p) {
		if(ShowInGameScoreboard == false) return;
		
		int score = 0;
		
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = sb.registerNewObjective("UHCInGame", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ingameTitle);
		
		if(ShowInGameBar) {
			obj.getScore(ingameBar).setScore(score);
			score--;
		}
		
		if(ShowInGamePlayersLiving) {
			String a = ingamePlayersLiving.replace("[livingPlayerscount]", Integer.toString(Core.getInGamePlayers().size()));
			lA.put(p, a);
			la = score;
			obj.getScore(a).setScore(score);
			score--;
		}
		
		if(ShowInGameSpectators) {
			String a = ingameSpectators.replace("[spectatorcount]", Integer.toString(Core.getSpecs().size()));
			lB.put(p, a);
			lb = score;
			obj.getScore(a).setScore(score);
			score--;
		}
		
		if(ShowInGameBar) {
			obj.getScore("§a"+ingameBar).setScore(score);
			score--;
		}
		
		if(ShowInGameKit) {
			String a = ingameKit.replace("[kit]", LobbyListener.getSelKit(p));
			obj.getScore(a).setScore(score);
			score--;
		}
		
		if(ShowInGameTeam) {
			String a = ingameTeam.replace("[team]", ""+ATeam.getTeamColor(ATeam.getPlayerTeam(p))+ ATeam.getPlayerTeam(p));
			obj.getScore(a).setScore(score);
			score--;
		}
		
		if(ShowInGameBar) {
			obj.getScore("§b"+ingameBar).setScore(score);
			score--;
		}
		
		if(ShowInGameCenter) {
			String a = ingameCenter.replace("[distanceToCenter]", Integer.toString((int) p.getLocation().distance(Border.getArena())));
			lE.put(p, a);
			le = score;
			obj.getScore(a).setScore(score);
			score--;
		}
		
		if(ShowInGameBorder) {
			String a = ingameBorder.replace("[bordersize]", Integer.toString(Border.getSize()));
			lF.put(p, a);
			lf = score;
			obj.getScore(a).setScore(score);
			score--;
		}
		
		if(ShowInGamePvP) {
			if(GState.isState(GState.INGAME)) {
				String a = ingamePvP.replace("[time]", Integer.toString(Timer.getuDM()));
				lC.put(p, a);
				lc = score;
				ld = score;
				lg = score;
				obj.getScore(a).setScore(score);
				score--;
			} else if(GState.isState(GState.GRACE)){
				String a = dmgin.replace("[time]", Integer.toString(Timer.getGracetime()));
				lD.put(p, a);
				lc = score;
				ld = score;
				lg = score;
				obj.getScore(a).setScore(score);
				score--;
			} else if(GState.isState(GState.PREGAME)) {
				String a = pvpin.replace("[time]", Integer.toString(Timer.getPrePvP()));
				lG.put(p, a);
				lc = score;
				ld = score;
				lg = score;
				obj.getScore(a).setScore(score);
				score--;
			}
		}
		
		if(ShowHealthUName) {
			Objective objName = sb.registerNewObjective("UHCHealthName", "health");
			objName.setDisplaySlot(DisplaySlot.BELOW_NAME);
			objName.setDisplayName("❤");
		}
		
		if(ShowHealthInTab) {
			Objective objName = sb.registerNewObjective("UHCHealthTab", "health");
			objName.setDisplaySlot(DisplaySlot.PLAYER_LIST);
			objName.setDisplayName("❤");
		}
		
		p.setScoreboard(sb);
	}
	
	public static void updateInGamePlayersLiving(Player p) {
		Objective objP = p.getScoreboard().getObjective("UHCInGame");
		String a = ingamePlayersLiving.replace("[livingPlayerscount]", Integer.toString(Core.getInGamePlayers().size()));
		p.getScoreboard().resetScores(lA.get(p));
		lA.put(p, a);
		objP.getScore(a).setScore(la);
	}
	
	public static void updateInGameSpectators(Player p) {
		Objective objP = p.getScoreboard().getObjective("UHCInGame");
		String a = ingameSpectators.replace("[spectatorcount]", Integer.toString(Core.getSpecs().size()));
		p.getScoreboard().resetScores(lB.get(p));
		lB.put(p, a);
		objP.getScore(a).setScore(lb);
	}
	
	public static void updateInGameCenter(Player p) {
		Objective objP = p.getScoreboard().getObjective("UHCInGame");
		if(SpawnFileManager.getSpawn() == null) {
			String  a = ingameCenter.replace("[distanceToCenter]", Integer.toString((int) p.getLocation().distance(SpawnFileManager.getSpawn().getWorld().getHighestBlockAt(SpawnFileManager.getSpawn().getBlockX(), SpawnFileManager.getSpawn().getBlockZ()).getLocation())));
			p.getScoreboard().resetScores(lE.get(p));
			lE.put(p, a);
			objP.getScore(a).setScore(le);
		} else {
			String a = ingameCenter.replace("[distanceToCenter]", Integer.toString((int) p.getLocation().distance(p.getWorld().getSpawnLocation())));
			p.getScoreboard().resetScores(lE.get(p));
			lE.put(p, a);
			objP.getScore(a).setScore(le);
		}
	}
	
	public static void updateInGameBorder(Player p) {
		Objective objP = p.getScoreboard().getObjective("UHCInGame");
		String a = ingameBorder.replace("[bordersize]", Integer.toString(Border.getSize()));
		p.getScoreboard().resetScores(lF.get(p));
		lF.put(p, a);
		objP.getScore(a).setScore(lf);
	}
	
	public static void updateInGamePvPTime(final Player p) {
		if(GState.isState(GState.INGAME)) {
			Objective objP = p.getScoreboard().getObjective("UHCInGame");
			String a = ingamePvP.replace("[time]", Integer.toString(Timer.getuDM()));
			if(lC.containsKey(p)) p.getScoreboard().resetScores(lC.get(p));
			lC.put(p, a);
			objP.getScore(a).setScore(lc);
		} 
		Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
			@Override
			public void run() {
				if(GState.isState(GState.GRACE)) {
					Objective objP = p.getScoreboard().getObjective("UHCInGame");
					String a = dmgin.replace("[time]", Integer.toString(Timer.getGracetime()));
					if(lC.containsKey(p)) p.getScoreboard().resetScores(lC.get(p));
					if(lD.containsKey(p)) p.getScoreboard().resetScores(lD.get(p));
					lD.put(p, a);
					objP.getScore(a).setScore(ld);
				}
			}
		}, 2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
			@Override
			public void run() {
				if(GState.isState(GState.PREGAME)) {
					Objective objP = p.getScoreboard().getObjective("UHCInGame");
					String a = pvpin.replace("[time]", Integer.toString(Timer.getPrePvP()));
					if(lC.containsKey(p)) p.getScoreboard().resetScores(lC.get(p));
					if(lD.containsKey(p)) p.getScoreboard().resetScores(lD.get(p));
					if(lG.containsKey(p)) p.getScoreboard().resetScores(lG.get(p));
					lG.put(p, a);
					objP.getScore(a).setScore(lg);
				}
			}
		}, 2);
	}
	
	public static String ingamePvPmsg;
	
	public static void setInGamePvPTime(Player p) {
		Objective objP = p.getScoreboard().getObjective("UHCInGame");
		p.getScoreboard().resetScores(lC.get(p));
		lC.put(p, ingamePvPmsg);
		objP.getScore(ingamePvPmsg).setScore(lc);
	}
	
}
