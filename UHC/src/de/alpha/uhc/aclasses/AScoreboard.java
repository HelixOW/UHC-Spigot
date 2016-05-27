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
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.border.Border;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.Stats;

public class AScoreboard {
	
	private Core pl;
	
	public AScoreboard(Core c) {
		this.pl = c;
	}

    private static final HashMap<Player, Integer> sTeamA = new HashMap<>();
    private static final HashMap<Player, Integer> sTeamS = new HashMap<>();
    private static final HashMap<Player, Integer> sTeamK = new HashMap<>();
    private static final HashMap<Player, Integer> sTeamT = new HashMap<>();
    private static final HashMap<Player, Integer> sTeamD = new HashMap<>();
    private static final HashMap<Player, Integer> sTeamB = new HashMap<>();
    private static final HashMap<Player, Integer> sTeamP = new HashMap<>();
    private static final HashMap<Player, String> cTeamA = new HashMap<>();
    private static final HashMap<Player, String> cTeamS = new HashMap<>();
    private static final HashMap<Player, String> cTeamK = new HashMap<>();
    private static final HashMap<Player, String> cTeamT = new HashMap<>();
    private static final HashMap<Player, String> cTeamD = new HashMap<>();
    private static final HashMap<Player, String> cTeamB = new HashMap<>();
    private static final HashMap<Player, String> cTeamP = new HashMap<>();
    private static boolean ShowLobbyScoreboard;
    private static boolean ShowLobbyKills;
    private static boolean ShowLobbyDeaths;
    private static boolean ShowLobbyCoins;
    private static boolean ShowLobbyPlayercount;
    private static boolean ShowLobbyTeam;
    private static boolean ShowLobbyBar;
    private static String lobbyTitle;
    private static String lobbyKills;
    private static String lobbyDeaths;
    private static String lobbyCoins;
    private static String lobbyPlayercount;
    private static String lobbyTeam;
    private static String lobbyBar;
    private static boolean ShowIngameScoreboard;
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
    private static HashMap<Player, String> A = new HashMap<>();
    private static HashMap<Player, String> B = new HashMap<>();
    private static int aa;
    private static Scoreboard sb;
    private static Objective obj;
    private static String ingamePvPmsg;

    public static void setShowLobbyScoreboard(boolean showLobbyScoreboard) {
        ShowLobbyScoreboard = showLobbyScoreboard;
    }

    public static void setShowLobbyKills(boolean showLobbyKills) {
        ShowLobbyKills = showLobbyKills;
    }

    public static void setShowLobbyDeaths(boolean showLobbyDeaths) {
        ShowLobbyDeaths = showLobbyDeaths;
    }

    public static void setShowLobbyCoins(boolean showLobbyCoins) {
        ShowLobbyCoins = showLobbyCoins;
    }

    public static void setShowLobbyPlayercount(boolean showLobbyPlayercount) {
        ShowLobbyPlayercount = showLobbyPlayercount;
    }

    public static void setShowLobbyTeam(boolean showLobbyTeam) {
        ShowLobbyTeam = showLobbyTeam;
    }

    public static void setShowLobbyBar(boolean showLobbyBar) {
        ShowLobbyBar = showLobbyBar;
    }

    public static void setLobbyTitle(String lobbyTitle) {
        AScoreboard.lobbyTitle = lobbyTitle;
    }

    public static void setLobbyKills(String lobbyKills) {
        AScoreboard.lobbyKills = lobbyKills;
    }

    public static void setLobbyDeaths(String lobbyDeaths) {
        AScoreboard.lobbyDeaths = lobbyDeaths;
    }

    public static void setLobbyCoins(String lobbyCoins) {
        AScoreboard.lobbyCoins = lobbyCoins;
    }

    public static void setLobbyPlayercount(String lobbyPlayercount) {
        AScoreboard.lobbyPlayercount = lobbyPlayercount;
    }

    public static void setLobbyTeam(String lobbyTeam) {
        AScoreboard.lobbyTeam = lobbyTeam;
    }

    public static void setLobbyBar(String lobbyBar) {
        AScoreboard.lobbyBar = lobbyBar;
    }

    public static void setShowInGameScoreboard(boolean showInGameScoreboard) {
        ShowIngameScoreboard = showInGameScoreboard;
    }

    public static void setShowInGamePlayersLiving(boolean showInGamePlayersLiving) {
        ShowInGamePlayersLiving = showInGamePlayersLiving;
    }

    public static void setShowInGameSpectators(boolean showInGameSpectators) {
        ShowInGameSpectators = showInGameSpectators;
    }

    public static void setShowInGameKit(boolean showInGameKit) {
        ShowInGameKit = showInGameKit;
    }

    public static void setShowInGameCenter(boolean showInGameCenter) {
        ShowInGameCenter = showInGameCenter;
    }

    public static void setShowInGameTeam(boolean showInGameTeam) {
        ShowInGameTeam = showInGameTeam;
    }

    public static void setShowInGameBorder(boolean showInGameBorder) {
        ShowInGameBorder = showInGameBorder;
    }

    public static void setShowInGamePvP(boolean showInGamePvP) {
        ShowInGamePvP = showInGamePvP;
    }

    public static void setShowInGameBar(boolean showInGameBar) {
        ShowInGameBar = showInGameBar;
    }

    public static void setIngameTitle(String ingameTitle) {
        AScoreboard.ingameTitle = ingameTitle;
    }

    public static void setIngamePlayersLiving(String ingamePlayersLiving) {
        AScoreboard.ingamePlayersLiving = ingamePlayersLiving;
    }

    public static void setIngameSpectators(String ingameSpectators) {
        AScoreboard.ingameSpectators = ingameSpectators;
    }

    public static void setIngameKit(String ingameKit) {
        AScoreboard.ingameKit = ingameKit;
    }

    public static void setIngameCenter(String ingameCenter) {
        AScoreboard.ingameCenter = ingameCenter;
    }

    //                 		-=X Scores X=-

    public static void setIngameTeam(String ingameTeam) {
        AScoreboard.ingameTeam = ingameTeam;
    }

    public static void setIngameBorder(String ingameBorder) {
        AScoreboard.ingameBorder = ingameBorder;
    }

    public static void setIngamePvP(String ingamePvP) {
        AScoreboard.ingamePvP = ingamePvP;
    }

    public static void setIngameBar(String ingameBar) {
        AScoreboard.ingameBar = ingameBar;
    }

    public static void setShowHealthUName(boolean showHealthUName) {
        ShowHealthUName = showHealthUName;
    }

    public static void setShowHealthInTab(boolean showHealthInTab) {
        ShowHealthInTab = showHealthInTab;
    }

    public static void setDmgin(String dmgin) {
        AScoreboard.dmgin = dmgin;
    }

    //  					-=X Strings on Board X=-

    public static void setPvpin(String pvpin) {
        AScoreboard.pvpin = pvpin;
    }

    public static HashMap<Player, String> getA() {
        return A;
    }

    public static void setA(HashMap<Player, String> a) {
        A = a;
    }

    public static HashMap<Player, String> getB() {
        return B;
    }

    public static void setB(HashMap<Player, String> b) {
        B = b;
    }

    public static void setIngamePvPmsg(String ingamePvPmsg) {
        AScoreboard.ingamePvPmsg = ingamePvPmsg;
    }

    public static void setLobbyScoreboard(Player p) {
        if (!ShowLobbyScoreboard) return;

        int score = 0;

        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = sb.registerNewObjective("UHCLobby", "dummy");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(lobbyTitle);

        if (ShowLobbyTeam) {
            String a = lobbyTeam.replace("[team]", "" + ATeam.getTeamColor(ATeam.getPlayerTeam(p)) + ATeam.getPlayerTeam(p));
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
            String a = lobbyCoins.replace("[coins]", "" + new Stats(p).getCoins());
            obj.getScore(a).setScore(score);
            score++;
        }

        if (ShowLobbyDeaths) {
            String a = lobbyDeaths.replace("[deaths]", "" + new Stats(p).getDeaths());
            obj.getScore(a).setScore(score);
            score++;
        }

        if (ShowLobbyKills) {
            String a = lobbyKills.replace("[kills]", "" + new Stats(p).getKills());
            obj.getScore(a).setScore(score);
            score++;
        }

        if (ShowLobbyBar) {
            obj.getScore(lobbyBar).setScore(score);
        }


        p.setScoreboard(sb);
    }

    public static void updateLobbyTeam(Player p) {
        Objective objP = p.getScoreboard().getObjective("UHCLobby");
        String a = lobbyTeam.replace("[team]", "" + ATeam.getTeamColor(ATeam.getPlayerTeam(p)) + ATeam.getPlayerTeam(p));
        p.getScoreboard().resetScores(A.get(p));
        A.put(p, a);
        objP.getScore(a).setScore(aa);
    }

    public static void sendAntiFlickerInGameBoard(final Player p) {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = sb.registerNewObjective("UHCInGame", "dummy");

        int score = 0;

        if(!ShowIngameScoreboard) { return; }

        String alive = ingamePlayersLiving.replace("[livingPlayerscount]", Integer.toString(Core.getInstance().getInGamePlayers().size()));
        String specs = ingameSpectators.replace("[spectatorcount]", Integer.toString(Core.getInstance().getSpecs().size()));
        String kit = ingameKit.replace("[kit]", LobbyListener.getSelKit(p));
        String team = ingameTeam.replace("[team]", "" + ATeam.getTeamColor(ATeam.getPlayerTeam(p)) + ATeam.getPlayerTeam(p));
        String bordersize = ingameBorder.replace("[bordersize]", Integer.toString(Border.getSize()));
        String inGameTime = ingamePvP.replace("[time]", Integer.toString(Timer.getuDM()));
        String GraceTime = dmgin.replace("[time]", Integer.toString(Timer.getGracetime()));
        String PreGame = pvpin.replace("[time]", Integer.toString(Timer.getPrePvP()));

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
            if (SpawnFileManager.getSpawn() == null && p.getWorld().getName().equals(SpawnFileManager.getSpawnWorldName())) {
                int dis = (int) p.getLocation().distance(SpawnFileManager.getSpawn().getWorld().getHighestBlockAt(SpawnFileManager.getSpawn().getBlockX(), SpawnFileManager.getSpawn().getBlockZ()).getLocation());
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

    public static void updateInGamePlayersLiving(Player p) {
        Objective b = p.getScoreboard().getObjective("UHCInGame");
        String a = ingamePlayersLiving.replace("[livingPlayerscount]", Integer.toString(Core.getInstance().getInGamePlayers().size()));
        p.getScoreboard().resetScores(cTeamA.get(p));
        cTeamA.put(p, a);
        b.getScore(a).setScore(sTeamA.get(p));
    }

    public static void updateInGameSpectators(Player p) {
        Objective b = p.getScoreboard().getObjective("UHCInGame");
        String a = ingameSpectators.replace("[spectatorcount]", Integer.toString(Core.getInstance().getSpecs().size()));
        p.getScoreboard().resetScores(cTeamS.get(p));
        cTeamS.put(p, a);
        b.getScore(a).setScore(sTeamS.get(p));
    }

    public static void updateInGameCenter(final Player p) {
        final Objective b = p.getScoreboard().getObjective("UHCInGame");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!(SpawnFileManager.getSpawn() == null && p.getWorld().getName().equals(SpawnFileManager.getSpawnWorldName()))) {
                    int dis = (int) p.getLocation().distance(SpawnFileManager.getSpawn().getWorld().getHighestBlockAt(SpawnFileManager.getSpawn().getBlockX(), SpawnFileManager.getSpawn().getBlockZ()).getLocation());
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

    public static void updateInGameBorder(Player p) {
        Objective b = p.getScoreboard().getObjective("UHCInGame");
        String a = ingameBorder.replace("[bordersize]", Integer.toString(Border.getSize()));
        p.getScoreboard().resetScores(cTeamB.get(p));
        cTeamB.put(p, a);
        b.getScore(a).setScore(sTeamB.get(p));
    }

    public static void updateInGamePvPTime(final Player p) {
        final Objective b = p.getScoreboard().getObjective("UHCInGame");
        String inGameTime = ingamePvP.replace("[time]", Integer.toString(Timer.getuDM()));
        String GraceTime = dmgin.replace("[time]", Integer.toString(Timer.getGracetime()));
        String PreGame = pvpin.replace("[time]", Integer.toString(Timer.getPrePvP()));
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
            String a = ingamePvP.replace("[time]", Integer.toString(Timer.getuDM()));
            p.getScoreboard().resetScores(cTeamP.get(p));
            cTeamP.put(p, a);
            b.getScore(a).setScore(sTeamP.get(p));
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (GState.isState(GState.GRACE)) {
                    String a = dmgin.replace("[time]", Integer.toString(Timer.getGracetime()));
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
                    String a = pvpin.replace("[time]", Integer.toString(Timer.getPrePvP()));
                    p.getScoreboard().resetScores(cTeamP.get(p));
                    cTeamP.put(p, a);
                    b.getScore(a).setScore(sTeamP.get(p));
                }
            }
        }, 2);
    }

    public static void setInGamePvPTime(Player p) {
        Objective b = p.getScoreboard().getObjective("UHCInGame");
        p.getScoreboard().resetScores(cTeamP.get(p));
        cTeamP.put(p, ingamePvPmsg);
        b.getScore(ingamePvPmsg).setScore(sTeamP.get(p));
    }

}
