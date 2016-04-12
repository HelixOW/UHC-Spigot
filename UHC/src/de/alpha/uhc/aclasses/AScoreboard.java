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
	
	public static  boolean isShowLobbyScoreboard() {
		return ShowLobbyScoreboard;
	}

	public static  void setShowLobbyScoreboard(boolean showLobbyScoreboard) {
		ShowLobbyScoreboard = showLobbyScoreboard;
	}

	public static  boolean isShowLobbyKills() {
		return ShowLobbyKills;
	}

	public static  void setShowLobbyKills(boolean showLobbyKills) {
		ShowLobbyKills = showLobbyKills;
	}

	public static  boolean isShowLobbyDeaths() {
		return ShowLobbyDeaths;
	}

	public static  void setShowLobbyDeaths(boolean showLobbyDeaths) {
		ShowLobbyDeaths = showLobbyDeaths;
	}

	public static  boolean isShowLobbyCoins() {
		return ShowLobbyCoins;
	}

	public static  void setShowLobbyCoins(boolean showLobbyCoins) {
		ShowLobbyCoins = showLobbyCoins;
	}

	public static  boolean isShowLobbyPlayercount() {
		return ShowLobbyPlayercount;
	}

	public static  void setShowLobbyPlayercount(boolean showLobbyPlayercount) {
		ShowLobbyPlayercount = showLobbyPlayercount;
	}

	public static  boolean isShowLobbyTeam() {
		return ShowLobbyTeam;
	}

	public static  void setShowLobbyTeam(boolean showLobbyTeam) {
		ShowLobbyTeam = showLobbyTeam;
	}

	public static  boolean isShowLobbyKit() {
		return ShowLobbyKit;
	}

	public static  void setShowLobbyKit(boolean showLobbyKit) {
		ShowLobbyKit = showLobbyKit;
	}

	public static  boolean isShowLobbyBar() {
		return ShowLobbyBar;
	}

	public static  void setShowLobbyBar(boolean showLobbyBar) {
		ShowLobbyBar = showLobbyBar;
	}

	public static  String getLobbyTitle() {
		return lobbyTitle;
	}

	public static  void setLobbyTitle(String lobbyTitle) {
		AScoreboard.lobbyTitle = lobbyTitle;
	}

	public static  String getLobbyKills() {
		return lobbyKills;
	}

	public static  void setLobbyKills(String lobbyKills) {
		AScoreboard.lobbyKills = lobbyKills;
	}

	public static  String getLobbyDeaths() {
		return lobbyDeaths;
	}

	public static  void setLobbyDeaths(String lobbyDeaths) {
		AScoreboard.lobbyDeaths = lobbyDeaths;
	}

	public static  String getLobbyCoins() {
		return lobbyCoins;
	}

	public static  void setLobbyCoins(String lobbyCoins) {
		AScoreboard.lobbyCoins = lobbyCoins;
	}

	public static  String getLobbyPlayercount() {
		return lobbyPlayercount;
	}

	public static  void setLobbyPlayercount(String lobbyPlayercount) {
		AScoreboard.lobbyPlayercount = lobbyPlayercount;
	}

	public static  String getLobbyTeam() {
		return lobbyTeam;
	}

	public static  void setLobbyTeam(String lobbyTeam) {
		AScoreboard.lobbyTeam = lobbyTeam;
	}

	public static  String getLobbyKit() {
		return lobbyKit;
	}

	public static  void setLobbyKit(String lobbyKit) {
		AScoreboard.lobbyKit = lobbyKit;
	}

	public static  String getLobbyBar() {
		return lobbyBar;
	}

	public static  void setLobbyBar(String lobbyBar) {
		AScoreboard.lobbyBar = lobbyBar;
	}

	public static  boolean isShowInGameScoreboard() {
		return ShowInGameScoreboard;
	}

	public static  void setShowInGameScoreboard(boolean showInGameScoreboard) {
		ShowInGameScoreboard = showInGameScoreboard;
	}

	public static  boolean isShowInGamePlayersLiving() {
		return ShowInGamePlayersLiving;
	}

	public static  void setShowInGamePlayersLiving(boolean showInGamePlayersLiving) {
		ShowInGamePlayersLiving = showInGamePlayersLiving;
	}

	public static  boolean isShowInGameSpectators() {
		return ShowInGameSpectators;
	}

	public static  void setShowInGameSpectators(boolean showInGameSpectators) {
		ShowInGameSpectators = showInGameSpectators;
	}

	public static  boolean isShowInGameKit() {
		return ShowInGameKit;
	}

	public static  void setShowInGameKit(boolean showInGameKit) {
		ShowInGameKit = showInGameKit;
	}

	public static  boolean isShowInGameCenter() {
		return ShowInGameCenter;
	}

	public static  void setShowInGameCenter(boolean showInGameCenter) {
		ShowInGameCenter = showInGameCenter;
	}

	public static  boolean isShowInGameTeam() {
		return ShowInGameTeam;
	}

	public static  void setShowInGameTeam(boolean showInGameTeam) {
		ShowInGameTeam = showInGameTeam;
	}

	public static  boolean isShowInGameBorder() {
		return ShowInGameBorder;
	}

	public static  void setShowInGameBorder(boolean showInGameBorder) {
		ShowInGameBorder = showInGameBorder;
	}

	public static  boolean isShowInGamePvP() {
		return ShowInGamePvP;
	}

	public static  void setShowInGamePvP(boolean showInGamePvP) {
		ShowInGamePvP = showInGamePvP;
	}

	public static  boolean isShowInGameBar() {
		return ShowInGameBar;
	}

	public static  void setShowInGameBar(boolean showInGameBar) {
		ShowInGameBar = showInGameBar;
	}

	public static  String getIngameTitle() {
		return ingameTitle;
	}

	public static  void setIngameTitle(String ingameTitle) {
		AScoreboard.ingameTitle = ingameTitle;
	}

	public static  String getIngamePlayersLiving() {
		return ingamePlayersLiving;
	}

	public static  void setIngamePlayersLiving(String ingamePlayersLiving) {
		AScoreboard.ingamePlayersLiving = ingamePlayersLiving;
	}

	public static  String getIngameSpectators() {
		return ingameSpectators;
	}

	public static  void setIngameSpectators(String ingameSpectators) {
		AScoreboard.ingameSpectators = ingameSpectators;
	}

	public static  String getIngameKit() {
		return ingameKit;
	}

	public static  void setIngameKit(String ingameKit) {
		AScoreboard.ingameKit = ingameKit;
	}

	public static  String getIngameCenter() {
		return ingameCenter;
	}

	public static  void setIngameCenter(String ingameCenter) {
		AScoreboard.ingameCenter = ingameCenter;
	}

	public static  String getIngameTeam() {
		return ingameTeam;
	}

	public static  void setIngameTeam(String ingameTeam) {
		AScoreboard.ingameTeam = ingameTeam;
	}

	public static  String getIngameBorder() {
		return ingameBorder;
	}

	public static  void setIngameBorder(String ingameBorder) {
		AScoreboard.ingameBorder = ingameBorder;
	}

	public static  String getIngamePvP() {
		return ingamePvP;
	}

	public static  void setIngamePvP(String ingamePvP) {
		AScoreboard.ingamePvP = ingamePvP;
	}

	public static  String getIngameBar() {
		return ingameBar;
	}

	public static  void setIngameBar(String ingameBar) {
		AScoreboard.ingameBar = ingameBar;
	}

	public static  boolean isShowHealthUName() {
		return ShowHealthUName;
	}

	public static  void setShowHealthUName(boolean showHealthUName) {
		ShowHealthUName = showHealthUName;
	}

	public static  boolean isShowHealthInTab() {
		return ShowHealthInTab;
	}

	public static  void setShowHealthInTab(boolean showHealthInTab) {
		ShowHealthInTab = showHealthInTab;
	}

	public static  String getDmgin() {
		return dmgin;
	}

	public static  void setDmgin(String dmgin) {
		AScoreboard.dmgin = dmgin;
	}

	public static  String getPvpin() {
		return pvpin;
	}

	public static  void setPvpin(String pvpin) {
		AScoreboard.pvpin = pvpin;
	}

	public static  HashMap<Player, String> getA() {
		return A;
	}

	public static  void setA(HashMap<Player, String> a) {
		A = a;
	}

	public static  HashMap<Player, String> getB() {
		return B;
	}

	public static  void setB(HashMap<Player, String> b) {
		B = b;
	}

	public static  int getAa() {
		return aa;
	}

	public static  void setAa(int aa) {
		AScoreboard.aa = aa;
	}

	public static  int getBb() {
		return bb;
	}

	public static  void setBb(int bb) {
		AScoreboard.bb = bb;
	}

	public static  Scoreboard getSb() {
		return sb;
	}

	public static  void setSb(Scoreboard sb) {
		AScoreboard.sb = sb;
	}

	public static  Objective getObj() {
		return obj;
	}

	public static  void setObj(Objective obj) {
		AScoreboard.obj = obj;
	}

	public static  HashMap<Player, String> getlA() {
		return lA;
	}

	public static  void setlA(HashMap<Player, String> lA) {
		AScoreboard.lA = lA;
	}

	public static  HashMap<Player, String> getlB() {
		return lB;
	}

	public static  void setlB(HashMap<Player, String> lB) {
		AScoreboard.lB = lB;
	}

	public static  HashMap<Player, String> getlC() {
		return lC;
	}

	public static  void setlC(HashMap<Player, String> lC) {
		AScoreboard.lC = lC;
	}

	public static  HashMap<Player, String> getlD() {
		return lD;
	}

	public static  void setlD(HashMap<Player, String> lD) {
		AScoreboard.lD = lD;
	}

	public static  HashMap<Player, String> getlE() {
		return lE;
	}

	public static  void setlE(HashMap<Player, String> lE) {
		AScoreboard.lE = lE;
	}

	public static  HashMap<Player, String> getlF() {
		return lF;
	}

	public static  void setlF(HashMap<Player, String> lF) {
		AScoreboard.lF = lF;
	}

	public static  HashMap<Player, String> getlG() {
		return lG;
	}

	public static  void setlG(HashMap<Player, String> lG) {
		AScoreboard.lG = lG;
	}

	public static  int getLa() {
		return la;
	}

	public static  void setLa(int la) {
		AScoreboard.la = la;
	}

	public static  int getLb() {
		return lb;
	}

	public static  void setLb(int lb) {
		AScoreboard.lb = lb;
	}

	public static  int getLc() {
		return lc;
	}

	public static  void setLc(int lc) {
		AScoreboard.lc = lc;
	}

	public static  int getLd() {
		return ld;
	}

	public static  void setLd(int ld) {
		AScoreboard.ld = ld;
	}

	public static  int getLe() {
		return le;
	}

	public static  void setLe(int le) {
		AScoreboard.le = le;
	}

	public static  int getLf() {
		return lf;
	}

	public static  void setLf(int lf) {
		AScoreboard.lf = lf;
	}

	public static  int getLg() {
		return lg;
	}

	public static  void setLg(int lg) {
		AScoreboard.lg = lg;
	}

	public static  String getIngamePvPmsg() {
		return ingamePvPmsg;
	}

	public static  void setIngamePvPmsg(String ingamePvPmsg) {
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
