package de.alpha.uhc.aclasses;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Registery;

public class AScoreboard {
	
	private Core pl;
	private Registery r;
	
	public AScoreboard(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  final HashMap<Player, Integer> sTeamA = new HashMap<>();
    private  final HashMap<Player, Integer> sTeamS = new HashMap<>();
    private  final HashMap<Player, Integer> sTeamK = new HashMap<>();
    private  final HashMap<Player, Integer> sTeamT = new HashMap<>();
    private  final HashMap<Player, Integer> sTeamD = new HashMap<>();
    private  final HashMap<Player, Integer> sTeamB = new HashMap<>();
    private  final HashMap<Player, Integer> sTeamP = new HashMap<>();
    private  final HashMap<Player, String> cTeamA = new HashMap<>();
    private  final HashMap<Player, String> cTeamS = new HashMap<>();
    private  final HashMap<Player, String> cTeamK = new HashMap<>();
    private  final HashMap<Player, String> cTeamT = new HashMap<>();
    private  final HashMap<Player, String> cTeamD = new HashMap<>();
    private  final HashMap<Player, String> cTeamB = new HashMap<>();
    private  final HashMap<Player, String> cTeamP = new HashMap<>();
    private  boolean ShowLobbyScoreboard;
    private  boolean ShowLobbyKills;
    private  boolean ShowLobbyDeaths;
    private  boolean ShowLobbyCoins;
    private  boolean ShowLobbyPlayercount;
    private  boolean ShowLobbyTeam;
    private  boolean ShowLobbyBar;
    private  String lobbyTitle;
    private  String lobbyKills;
    private  String lobbyDeaths;
    private  String lobbyCoins;
    private  String lobbyPlayercount;
    private  String lobbyTeam;
    private  String lobbyBar;
    private  boolean ShowIngameScoreboard;
    private  boolean ShowInGamePlayersLiving;
    private  boolean ShowInGameSpectators;
    private  boolean ShowInGameKit;
    private  boolean ShowInGameCenter;
    private  boolean ShowInGameTeam;
    private  boolean ShowInGameBorder;
    private  boolean ShowInGamePvP;
    private  boolean ShowInGameBar;
    private  String ingameTitle;
    private  String ingamePlayersLiving;
    private  String ingameSpectators;
    private  String ingameKit;
    private  String ingameCenter;
    private  String ingameTeam;
    private  String ingameBorder;
    private  String ingamePvP;
    private  String ingameBar;
    private  boolean ShowHealthUName;
    private  boolean ShowHealthInTab;
    private  String dmgin;
    private  String pvpin;
    private  HashMap<Player, String> A = new HashMap<>();
    private  HashMap<Player, String> B = new HashMap<>();
    private  int aa;
    private  Scoreboard sb;
    private  Objective obj;
    private  String ingamePvPmsg;

    public  void setShowLobbyScoreboard(boolean showLobbyScoreboard) {
    	this.ShowLobbyScoreboard = showLobbyScoreboard;
    }

    public  void setShowLobbyKills(boolean showLobbyKills) {
    	this.ShowLobbyKills = showLobbyKills;
    }

    public  void setShowLobbyDeaths(boolean showLobbyDeaths) {
    	this.ShowLobbyDeaths = showLobbyDeaths;
    }

    public  void setShowLobbyCoins(boolean showLobbyCoins) {
    	this.ShowLobbyCoins = showLobbyCoins;
    }

    public  void setShowLobbyPlayercount(boolean showLobbyPlayercount) {
    	this.ShowLobbyPlayercount = showLobbyPlayercount;
    }

    public  void setShowLobbyTeam(boolean showLobbyTeam) {
    	this.ShowLobbyTeam = showLobbyTeam;
    }

    public  void setShowLobbyBar(boolean showLobbyBar) {
    	this.ShowLobbyBar = showLobbyBar;
    }

    public  void setLobbyTitle(String lobbyTitle) {
        this.lobbyTitle = lobbyTitle;
    }

    public  void setLobbyKills(String lobbyKills) {
    	this.lobbyKills = lobbyKills;
    }

    public  void setLobbyDeaths(String lobbyDeaths) {
    	this.lobbyDeaths = lobbyDeaths;
    }

    public  void setLobbyCoins(String lobbyCoins) {
    	this.lobbyCoins = lobbyCoins;
    }

    public  void setLobbyPlayercount(String lobbyPlayercount) {
    	this.lobbyPlayercount = lobbyPlayercount;
    }

    public  void setLobbyTeam(String lobbyTeam) {
    	this.lobbyTeam = lobbyTeam;
    }

    public  void setLobbyBar(String lobbyBar) {
    	this.lobbyBar = lobbyBar;
    }

    public  void setShowInGameScoreboard(boolean showInGameScoreboard) {
    	this.ShowIngameScoreboard = showInGameScoreboard;
    }

    public  void setShowInGamePlayersLiving(boolean showInGamePlayersLiving) {
    	this.ShowInGamePlayersLiving = showInGamePlayersLiving;
    }

    public  void setShowInGameSpectators(boolean showInGameSpectators) {
    	this.ShowInGameSpectators = showInGameSpectators;
    }

    public  void setShowInGameKit(boolean showInGameKit) {
    	this.ShowInGameKit = showInGameKit;
    }

    public  void setShowInGameCenter(boolean showInGameCenter) {
    	this.ShowInGameCenter = showInGameCenter;
    }

    public  void setShowInGameTeam(boolean showInGameTeam) {
    	this.ShowInGameTeam = showInGameTeam;
    }

    public  void setShowInGameBorder(boolean showInGameBorder) {
    	this.ShowInGameBorder = showInGameBorder;
    }

    public  void setShowInGamePvP(boolean showInGamePvP) {
    	this.ShowInGamePvP = showInGamePvP;
    }

    public  void setShowInGameBar(boolean showInGameBar) {
    	this.ShowInGameBar = showInGameBar;
    }

    public  void setIngameTitle(String ingameTitle) {
    	this.ingameTitle = ingameTitle;
    }

    public  void setIngamePlayersLiving(String ingamePlayersLiving) {
    	this.ingamePlayersLiving = ingamePlayersLiving;
    }

    public  void setIngameSpectators(String ingameSpectators) {
    	this.ingameSpectators = ingameSpectators;
    }

    public  void setIngameKit(String ingameKit) {
    	this.ingameKit = ingameKit;
    }

    public  void setIngameCenter(String ingameCenter) {
    	this.ingameCenter = ingameCenter;
    }

    //                 		-=X Scores X=-

    public  void setIngameTeam(String ingameTeam) {
    	this.ingameTeam = ingameTeam;
    }

    public  void setIngameBorder(String ingameBorder) {
    	this.ingameBorder = ingameBorder;
    }

    public  void setIngamePvP(String ingamePvP) {
    	this.ingamePvP = ingamePvP;
    }

    public  void setIngameBar(String ingameBar) {
    	this.ingameBar = ingameBar;
    }

    public  void setShowHealthUName(boolean showHealthUName) {
    	this.ShowHealthUName = showHealthUName;
    }

    public  void setShowHealthInTab(boolean showHealthInTab) {
    	this.ShowHealthInTab = showHealthInTab;
    }

    public  void setDmgin(String dmgin) {
    	this.dmgin = dmgin;
    }

    //  					-=X Strings on Board X=-

    public  void setPvpin(String pvpin) {
    	this.pvpin = pvpin;
    }

    public  HashMap<Player, String> getA() {
        return A;
    }

    public  void setA(HashMap<Player, String> a) {
        A = a;
    }

    public  HashMap<Player, String> getB() {
        return B;
    }

    public  void setB(HashMap<Player, String> b) {
        B = b;
    }

    public  void setIngamePvPmsg(String ingamePvPmsg) {
    	this.ingamePvPmsg = ingamePvPmsg;
    }

    public  void setLobbyScoreboard(Player p) {
        if (!ShowLobbyScoreboard) return;

        int score = 0;

        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = sb.registerNewObjective("UHCLobby", "dummy");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(lobbyTitle);

        if (ShowLobbyTeam) {
            String a = lobbyTeam.replace("[team]", "" + pl.getRegistery().getATeam().getTeamColor(pl.getRegistery().getATeam().getPlayerTeam(p)) + pl.getRegistery().getATeam().getPlayerTeam(p));
            obj.getScore(a).setScore(score);
            A.put(p, a);
            aa = score;
            score++;
        }

        if (ShowLobbyBar) {
            obj.getScore("§b" + lobbyBar).setScore(score);
            score++;
        }

        if (ShowLobbyPlayercount) {
            String a = lobbyPlayercount.replace("[playercount]", Integer.toString(Bukkit.getOnlinePlayers().size()));
            obj.getScore(a).setScore(score);
            score++;
        }

        if (ShowLobbyBar) {
            obj.getScore("§a" + lobbyBar).setScore(score);
            score++;
        }

        if (ShowLobbyCoins) {
            String a = lobbyCoins.replace("[coins]", "" + r.getStats().getCoins(p));
            obj.getScore(a).setScore(score);
            score++;
        }

        if (ShowLobbyDeaths) {
            String a = lobbyDeaths.replace("[deaths]", "" + r.getStats().getDeaths(p));
            obj.getScore(a).setScore(score);
            score++;
        }

        if (ShowLobbyKills) {
            String a = lobbyKills.replace("[kills]", "" + r.getStats().getKills(p));
            obj.getScore(a).setScore(score);
            score++;
        }

        if (ShowLobbyBar) {
            obj.getScore(lobbyBar).setScore(score);
        }


        p.setScoreboard(sb);
    }

    public  void updateLobbyTeam(Player p) {
        Objective objP = p.getScoreboard().getObjective("UHCLobby");
        String a = lobbyTeam.replace("[team]", "" + pl.getRegistery().getATeam().getTeamColor(pl.getRegistery().getATeam().getPlayerTeam(p)) + pl.getRegistery().getATeam().getPlayerTeam(p));
        p.getScoreboard().resetScores(A.get(p));
        A.put(p, a);
        objP.getScore(a).setScore(aa);
    }

    public  void sendAntiFlickerInGameBoard(final Player p) {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = sb.registerNewObjective("UHCInGame", "dummy");

        int score = 0;

        if(!ShowIngameScoreboard) { return; }

        String alive = ingamePlayersLiving.replace("[livingPlayerscount]", Integer.toString(Core.getInstance().getInGamePlayers().size()));
        String specs = ingameSpectators.replace("[spectatorcount]", Integer.toString(Core.getInstance().getSpecs().size()));
        String kit = ingameKit.replace("[kit]", pl.getRegistery().getLobbyListener().getSelKit(p));
        String team = ingameTeam.replace("[team]", "" + pl.getRegistery().getATeam().getTeamColor(pl.getRegistery().getATeam().getPlayerTeam(p)) + pl.getRegistery().getATeam().getPlayerTeam(p));
        String bordersize = ingameBorder.replace("[bordersize]", Integer.toString(pl.getRegistery().getBorder().getSize()));
        String inGameTime = ingamePvP.replace("[time]", Integer.toString(pl.getRegistery().getTimer().getuDM()));
        String GraceTime = dmgin.replace("[time]", Integer.toString(pl.getRegistery().getTimer().getGracetime()));
        String PreGame = pvpin.replace("[time]", Integer.toString(pl.getRegistery().getTimer().getPrePvP()));

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ingameTitle);

        if (ShowInGamePvP) {
            if (GState.isState(GState.PREGAME)) {
                obj.getScore(PreGame).setScore(score++);
                sTeamP.put(p, score);
                cTeamP.put(p, PreGame);
            } else if (GState.isState(GState.GRACE)) {
                obj.getScore(GraceTime).setScore(score++);
                sTeamP.put(p, score);
                cTeamP.put(p, GraceTime);
            } else if (GState.isState(GState.INGAME)) {
                obj.getScore(inGameTime).setScore(score++);
                sTeamP.put(p, score);
                cTeamP.put(p, inGameTime);
            }
        }

        if (ShowInGameBorder) {
            obj.getScore(bordersize).setScore(score++);
            sTeamB.put(p, score);
            cTeamB.put(p, bordersize);
        }

        if (ShowInGameCenter) {
            if (pl.getRegistery().getSpawnFileManager().getSpawn() == null && p.getWorld().getName().equals(pl.getRegistery().getSpawnFileManager().getSpawnWorldName())) {
                int dis = (int) p.getLocation().distance(pl.getRegistery().getSpawnFileManager().getSpawn().getWorld().getHighestBlockAt(pl.getRegistery().getSpawnFileManager().getSpawn().getBlockX(), pl.getRegistery().getSpawnFileManager().getSpawn().getBlockZ()).getLocation());
                String a = ingameCenter.replace("[distanceToCenter]", Integer.toString(dis));
                cTeamD.put(p, a);
                obj.getScore(a).setScore(score++);
                sTeamD.put(p, score);
            } else {
                int dis = (int) p.getLocation().distance(p.getWorld().getSpawnLocation());
                String a = ingameCenter.replace("[distanceToCenter]", Integer.toString(dis));
                cTeamD.put(p, a);
                obj.getScore(a).setScore(score++);
                sTeamD.put(p, score);
            }
        }

        if (ShowInGameBar) {
            obj.getScore("§b" + ingameBar).setScore(score++);
        }

        if (ShowInGameTeam) {
            obj.getScore(team).setScore(score++);
            sTeamT.put(p, score);
            cTeamT.put(p, team);
        }

        if (ShowInGameKit) {
            obj.getScore(kit).setScore(score++);
            sTeamK.put(p, score);
            cTeamK.put(p, kit);
        }

        if (ShowInGameBar) {
            obj.getScore("§a" + ingameBar).setScore(score++);
        }

        if (ShowInGameSpectators) {
            obj.getScore(specs).setScore(score++);
            sTeamS.put(p, score);
            cTeamS.put(p, specs);
        }

        if (ShowInGamePlayersLiving) {
            obj.getScore(alive).setScore(score++);
            sTeamA.put(p, score);
            cTeamA.put(p, alive);
        }

        if (ShowInGameBar) {
            obj.getScore(ingameBar).setScore(score);
        }

        if (ShowHealthUName) {
            Objective objName = sb.registerNewObjective("UHCHealthName", "health");
            objName.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objName.setDisplayName("§4❤");
        }

        if (ShowHealthInTab) {
            Objective objName = sb.registerNewObjective("UHCHealthTab", "health");
            objName.setDisplaySlot(DisplaySlot.PLAYER_LIST);
            objName.setDisplayName("§4❤");
        }

        p.setScoreboard(sb);
    }

    public  void updateInGamePlayersLiving(Player p) {
        Objective b = p.getScoreboard().getObjective("UHCInGame");
        String a = ingamePlayersLiving.replace("[livingPlayerscount]", Integer.toString(Core.getInstance().getInGamePlayers().size()));
        p.getScoreboard().resetScores(cTeamA.get(p));
        cTeamA.put(p, a);
        b.getScore(a).setScore(sTeamA.get(p));
    }

    public  void updateInGameSpectators(Player p) {
        Objective b = p.getScoreboard().getObjective("UHCInGame");
        String a = ingameSpectators.replace("[spectatorcount]", Integer.toString(Core.getInstance().getSpecs().size()));
        p.getScoreboard().resetScores(cTeamS.get(p));
        cTeamS.put(p, a);
        b.getScore(a).setScore(sTeamS.get(p));
    }

    public  void updateInGameCenter(final Player p) {
        final Objective b = p.getScoreboard().getObjective("UHCInGame");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!(pl.getRegistery().getSpawnFileManager().getSpawn() == null && p.getWorld().getName().equals(pl.getRegistery().getSpawnFileManager().getSpawnWorldName()))) {
                    int dis = (int) p.getLocation().distance(pl.getRegistery().getSpawnFileManager().getSpawn().getWorld().getHighestBlockAt(pl.getRegistery().getSpawnFileManager().getSpawn().getBlockX(), pl.getRegistery().getSpawnFileManager().getSpawn().getBlockZ()).getLocation());
                    String a = ingameCenter.replace("[distanceToCenter]", Integer.toString(dis));
                    p.getScoreboard().resetScores(cTeamD.get(p));
                    cTeamD.put(p, a);
                    b.getScore(a).setScore(sTeamD.get(p));
                } else {
                    int dis = (int) p.getLocation().distance(p.getWorld().getSpawnLocation());
                    String a = ingameCenter.replace("[distanceToCenter]", Integer.toString(dis));
                    p.getScoreboard().resetScores(cTeamD.get(p));
                    cTeamD.put(p, a);
                    b.getScore(a).setScore(sTeamD.get(p));
                }
            }
        }.runTaskLater(Core.getInstance(), 20);
    }

    public  void updateInGameBorder(Player p) {
        Objective b = p.getScoreboard().getObjective("UHCInGame");
        String a = ingameBorder.replace("[bordersize]", Integer.toString(pl.getRegistery().getBorder().getSize()));
        p.getScoreboard().resetScores(cTeamB.get(p));
        cTeamB.put(p, a);
        b.getScore(a).setScore(sTeamB.get(p));
    }

    public  void updateInGamePvPTime(final Player p) {
        final Objective b = p.getScoreboard().getObjective("UHCInGame");
        String inGameTime = ingamePvP.replace("[time]", Integer.toString(pl.getRegistery().getTimer().getuDM()));
        String GraceTime = dmgin.replace("[time]", Integer.toString(pl.getRegistery().getTimer().getGracetime()));
        String PreGame = pvpin.replace("[time]", Integer.toString(pl.getRegistery().getTimer().getPrePvP()));
        if (!(cTeamP.containsKey(p))) {
            if (ShowInGamePvP) {
                if (GState.isState(GState.PREGAME)) {
                    obj.getScore(PreGame).setScore(sTeamB.get(p) - 1);
                    sTeamP.put(p, sTeamB.get(p) - 1);
                    cTeamP.put(p, PreGame);
                } else if (GState.isState(GState.GRACE)) {
                    obj.getScore(GraceTime).setScore(sTeamB.get(p) - 1);
                    sTeamP.put(p, sTeamB.get(p) - 1);
                    cTeamP.put(p, GraceTime);
                } else if (GState.isState(GState.INGAME)) {
                    obj.getScore(inGameTime).setScore(sTeamB.get(p) - 1);
                    sTeamP.put(p, sTeamB.get(p) - 1);
                    cTeamP.put(p, inGameTime);
                }
            }
            return;
        }
        if (GState.isState(GState.INGAME)) {
            String a = ingamePvP.replace("[time]", Integer.toString(pl.getRegistery().getTimer().getuDM()));
            p.getScoreboard().resetScores(cTeamP.get(p));
            cTeamP.put(p, a);
            b.getScore(a).setScore(sTeamP.get(p));
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (GState.isState(GState.GRACE)) {
                    String a = dmgin.replace("[time]", Integer.toString(pl.getRegistery().getTimer().getGracetime()));
                    p.getScoreboard().resetScores(cTeamP.get(p));
                    cTeamP.put(p, a);
                    b.getScore(a).setScore(sTeamP.get(p));
                }
            }
        }, 2);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (GState.isState(GState.PREGAME)) {
                    String a = pvpin.replace("[time]", Integer.toString(pl.getRegistery().getTimer().getPrePvP()));
                    p.getScoreboard().resetScores(cTeamP.get(p));
                    cTeamP.put(p, a);
                    b.getScore(a).setScore(sTeamP.get(p));
                }
            }
        }, 2);
    }

    public  void setInGamePvPTime(Player p) {
        Objective b = p.getScoreboard().getObjective("UHCInGame");
        p.getScoreboard().resetScores(cTeamP.get(p));
        cTeamP.put(p, ingamePvPmsg);
        b.getScore(ingamePvPmsg).setScore(sTeamP.get(p));
    }

}
