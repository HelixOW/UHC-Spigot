package de.alphahelix.uhc.util;

import de.alphahelix.alphalibary.scoreboard.SimpleScoreboard;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.entity.Player;

public class ScoreboardUtil extends Util {

    private static String iden;

    public ScoreboardUtil(UHC uhc) {
        super(uhc);
        iden = UHCFileRegister.getScoreboardFile().getLobbyIdentifier();
    }

    public void setLobbyScoreboard(Player p) {
        if (!UHCFileRegister.getScoreboardFile().isLobbyShow("scoreboard"))
            return;

        SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), "uhc-lobby",
                UHCFileRegister.getScoreboardFile().getLobbyTitle(), iden);
        int max = UHCFileRegister.getScoreboardConstructFile().getLobbyLines();

        if (max > 16)
            max = 16;
        for (int i = 1; i < max; i++) {
            String lineValue = UHCFileRegister.getScoreboardConstructFile().getLobbyLine(i);

            if (lineValue.contains("[blank]")) {
                String repeat = new String(new char[i]).replace("\0", " ");
                ssb.setValue(i, repeat, iden);
            } else if (lineValue.contains("[kills]")) {
                if (!UHCFileRegister.getScoreboardFile().isLobbyShow("kills"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getLobbyPart("kills")
                                .replace("[kills]", Long.toString(UHCRegister.getStatsUtil().getKills(p)))
                                .replace("[i]", iden),
                        iden);
            } else if (lineValue.contains("[deaths]")) {
                if (!UHCFileRegister.getScoreboardFile().isLobbyShow("deaths"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getLobbyPart("deaths")
                                .replace("[deaths]", Long.toString(UHCRegister.getStatsUtil().getDeaths(p)))
                                .replace("[i]", iden),
                        iden);
            } else if (lineValue.contains("[coins]")) {
                if (!UHCFileRegister.getScoreboardFile().isLobbyShow("coins"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getLobbyPart("coins")
                                .replace("[coins]", Long.toString(UHCRegister.getStatsUtil().getCoins(p)))
                                .replace("[i]", iden),
                        iden);
            } else if (lineValue.contains("[points]")) {
                if (!UHCFileRegister.getScoreboardFile().isLobbyShow("points"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getLobbyPart("points")
                                .replace("[points]", Long.toString(UHCRegister.getStatsUtil().getPoints(p)))
                                .replace("[i]", iden),
                        iden);
            } else if (lineValue.contains("[team]")) {
                if (!UHCFileRegister.getScoreboardFile().isLobbyShow("team"))
                    continue;
                if (UHCRegister.getTeamManagerUtil().isInOneTeam(p) == null) {
                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getLobbyPart("team")
                            .replace("[team]", "-").replace("[i]", iden), iden);
                }
            } else if (lineValue.contains("[kit]") || lineValue.contains("[scenario]")) {
                if (getUhc().isKits() && !getUhc().isScenarios()) {
                    if (!UHCFileRegister.getScoreboardFile().isLobbyShow("kit"))
                        continue;
                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getLobbyPart("kit")
                            .replace("[kit]", "-").replace("[i]", iden), iden);
                } else if (getUhc().isScenarios()) {
                    if (!UHCFileRegister.getScoreboardFile().isLobbyShow("scenario"))
                        continue;

                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getLobbyPart("scenario")
                            .replace("[scenario]", "-").replace("[i]", iden), iden);

                    if (!getUhc().isScenarioVoting())
                        ssb.updateValue(i,
                                UHCFileRegister.getScoreboardFile().getLobbyPart("scenario")
                                        .replace("[scenario]",
                                                UHCFileRegister.getScenarioFile()
                                                        .getCustomScenarioName(Scenarios.getScenario()))
                                        .replace("[i]", iden),
                                iden);
                }
            } else if (lineValue.contains("[bar]")) {
                if (!UHCFileRegister.getScoreboardFile().isLobbyShow("bar"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getLobbyPart("bar").replace("[i]", iden),
                        iden);
            }
        }

        p.setScoreboard(ssb.getScoreboard());
    }

    public void updateScenario(Player p, Scenarios played) {
        SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-lobby");

        int max = UHCFileRegister.getScoreboardConstructFile().getLobbyLines();

        if (max > 16)
            max = 16;

        for (int i = 1; i < max; i++) {
            String lineValue = UHCFileRegister.getScoreboardConstructFile().getLobbyLine(i);
            if (!lineValue.contains("[kit]") || lineValue.contains("[scenario]"))
                continue;

            ssb.updateValue(i,
                    UHCFileRegister.getScoreboardFile().getLobbyPart("scenario")
                            .replace("[scenario]", UHCFileRegister.getScenarioFile().getCustomScenarioName(played))
                            .replace("[i]", iden),
                    iden);
        }
    }

    public void updateKit(Player p, Kit k) {
        SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-lobby");

        int max = UHCFileRegister.getScoreboardConstructFile().getLobbyLines();

        if (max > 16)
            max = 16;
        for (int i = 1; i < max; i++) {
            String lineValue = UHCFileRegister.getScoreboardConstructFile().getLobbyLine(i);
            if (!lineValue.contains("[kit]"))
                continue;
            ssb.updateValue(i, UHCFileRegister.getScoreboardFile().getLobbyPart("kit")
                    .replace("[kit]", k.getName().replace("_", " ")).replace("[i]", iden), iden);
        }
    }

    public void updateTeam(Player p, UHCTeam t) {
        SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-lobby");

        int max = UHCFileRegister.getScoreboardConstructFile().getLobbyLines();

        if (max > 16)
            max = 16;
        for (int i = 1; i < max; i++) {
            String lineValue = UHCFileRegister.getScoreboardConstructFile().getLobbyLine(i);
            if (!lineValue.contains("[team]"))
                continue;
            ssb.updateValue(i, UHCFileRegister.getScoreboardFile().getLobbyPart("team")
                    .replace("[team]", t.getPrefix() + t.getName()).replace("[i]", iden), iden);
        }
    }

    public void setIngameScoreboard(Player p) {
        if (!UHCFileRegister.getScoreboardFile().isIngameShow("scoreboard"))
            return;

        SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), "uhc-ingame",
                UHCFileRegister.getScoreboardFile().getIngameTitle(), iden);
        int max = UHCFileRegister.getScoreboardConstructFile().getIngameLines();

        if (max > 16)
            max = 16;
        for (int i = 1; i < max; i++) {
            String lineValue = UHCFileRegister.getScoreboardConstructFile().getIngameLine(i);
            if (lineValue.contains("[out]"))
                continue;

            else if (lineValue.contains("[blank]")) {
                String repeat = new String(new char[i]).replace("\0", " ");
                ssb.setValue(i, repeat, iden);
            } else if (lineValue.contains("[alive]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("alive"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getIngamePart("alive")
                                .replace("[alive]",
                                        Integer.toString(UHCRegister.getPlayerUtil().getSurvivors().size()))
                                .replace("[i]", iden),
                        iden);
            } else if (lineValue.contains("[specs]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("specs"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getIngamePart("specs")
                                .replace("[specs]", Integer.toString(UHCRegister.getPlayerUtil().getDeads().size()))
                                .replace("[i]", iden),
                        iden);
            }

            if (lineValue.contains("[kit]") || lineValue.contains("[scenario]")) {
                if (getUhc().isKits() && !getUhc().isScenarios()) {
                    if (!UHCFileRegister.getScoreboardFile().isIngameShow("kit"))
                        continue;

                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getIngamePart("kit")
                            .replace("[kit]", "-"), iden);

                    if (UHCFileRegister.getKitsFile().getPlayedKit(p) != null) {
                        ssb.updateValue(i,
                                UHCFileRegister.getScoreboardFile()
                                        .getIngamePart("kit").replace("[kit]", UHCFileRegister
                                        .getKitsFile().getPlayedKit(p).getName().replace("_", " "))
                                        .replace("[i]", iden),
                                iden);
                    }
                } else if (getUhc().isScenarios()) {
                    if (!UHCFileRegister.getScoreboardFile().isIngameShow("scenario"))
                        continue;

                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getIngamePart("scenario")
                            .replace("[scenario]", "-"), iden);

                    ssb.updateValue(i,
                            UHCFileRegister.getScoreboardFile().getIngamePart("scenario")
                                    .replace("[scenario]",
                                            UHCFileRegister.getScenarioFile()
                                                    .getCustomScenarioName(Scenarios.getScenario()))
                                    .replace("[i]", iden),
                            iden);

                }
            } else if (lineValue.contains("[team]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("team"))
                    continue;

                if (UHCRegister.getTeamManagerUtil().isInOneTeam(p) == null) {
                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getIngamePart("team")
                            .replace("[team]", "-").replace("[i]", iden), iden);
                } else {
                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getIngamePart("team")
                            .replace("[team]", "-").replace("[i]", iden), iden);

                    ssb.updateValue(i,
                            UHCFileRegister.getScoreboardFile().getIngamePart("team")
                                    .replace("[team]",
                                            UHCRegister.getTeamManagerUtil().isInOneTeam(p).getPrefix()
                                                    + UHCRegister.getTeamManagerUtil().isInOneTeam(p).getName())
                                    .replace("[i]", iden),
                            iden);
                }
            } else if (lineValue.contains("[center]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("center"))
                    continue;
                String v = UHCFileRegister.getScoreboardFile().getIngamePart("center")
                        .replace("[center]", UHCFileRegister.getLocationsFile().getArena().getBlockX() + "/"
                                + UHCFileRegister.getLocationsFile().getArena().getBlockZ())
                        .replace("[i]", iden);

                ssb.setValue(i, v, iden);
            } else if (lineValue.contains("[border]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("border"))
                    continue;
                String v = UHCFileRegister.getScoreboardFile().getIngamePart("border")
                        .replace("[border]", Integer.toString(UHCRegister.getBorderUtil().getSize()))
                        .replace("[i]", iden);

                ssb.setValue(i, v, iden);
            } else if (lineValue.contains("[time infos]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("time infos"))
                    continue;
                if (GState.isState(GState.PERIOD_OF_PEACE)) {
                    String v = UHCFileRegister.getScoreboardFile()
                            .getIngamePart("time infos.until damage")
                            .replace("[time]", UHCRegister.getGraceTimer().getTime()).replace("[i]", iden);

                    ssb.setValue(i, v, iden);

                } else if (GState.isState(GState.WARMUP)) {
                    String v = UHCFileRegister.getScoreboardFile().getIngamePart("time infos.until pvp")
                            .replace("[time]", UHCRegister.getWarmUpTimer().getTime()).replace("[i]", iden);

                    ssb.setValue(i, v, iden);
                } else if (GState.isState(GState.IN_GAME)) {
                    String v = UHCFileRegister.getScoreboardFile()
                            .getIngamePart("time infos.until deathmatch")
                            .replace("[time]", UHCRegister.getDeathmatchTimer().getTime()).replace("[i]", iden);

                    ssb.setValue(i, v, iden);
                } else if (GState.isState(GState.DEATHMATCH_WARMUP)) {
                    String v = UHCFileRegister.getScoreboardFile()
                            .getIngamePart("time infos.until deathmatch")
                            .replace("[time]", UHCRegister.getStartDeathmatchTimer().getTime()).replace("[i]", iden);

                    ssb.setValue(i, v, iden);
                } else if (GState.isState(GState.DEATHMATCH)) {
                    String v = UHCFileRegister.getScoreboardFile().getIngamePart("time infos.deathmatch")
                            .replace("[i]", iden);

                    ssb.setValue(i, v, iden);
                } else if (GState.isState(GState.END)) {
                    String v = UHCFileRegister.getScoreboardFile().getIngamePart("end").replace("[i]",
                            iden);

                    ssb.setValue(i, v, iden);
                }
            } else if (lineValue.contains("[bar]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("bar"))
                    continue;
                String v = UHCFileRegister.getScoreboardFile().getIngamePart("bar").replace("[i]", iden);

                ssb.setValue(i, v, iden);
            }
        }

        p.setScoreboard(ssb.getScoreboard());
    }

    public void updateAlive(Player p) {
        SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-ingame");

        int max = UHCFileRegister.getScoreboardConstructFile().getIngameLines();

        if (max > 16)
            max = 16;
        for (int i = 1; i < max; i++) {
            String lineValue = UHCFileRegister.getScoreboardConstructFile().getIngameLine(i);
            if (!lineValue.contains("[alive]"))
                continue;
            if (!UHCFileRegister.getScoreboardFile().isIngameShow("alive"))
                continue;
            ssb.setValue(i,
                    UHCFileRegister.getScoreboardFile().getIngamePart("alive")
                            .replace("[alive]", Integer.toString(UHCRegister.getPlayerUtil().getSurvivors().size()))
                            .replace("[i]", iden),
                    iden);
        }
    }

    public void updateSpecs(Player p) {
        SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-ingame");

        int max = UHCFileRegister.getScoreboardConstructFile().getIngameLines();

        if (max > 16)
            max = 16;
        for (int i = 1; i < max; i++) {
            String lineValue = UHCFileRegister.getScoreboardConstructFile().getIngameLine(i);
            if (!lineValue.contains("[specs]"))
                continue;
            if (!UHCFileRegister.getScoreboardFile().isIngameShow("specs"))
                continue;
            ssb.setValue(i,
                    UHCFileRegister.getScoreboardFile().getIngamePart("specs")
                            .replace("[specs]", Integer.toString(UHCRegister.getPlayerUtil().getDeads().size()))
                            .replace("[i]", iden),
                    iden);
        }
    }

    public void updateTime(Player p) {
        SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-ingame");

        int max = UHCFileRegister.getScoreboardConstructFile().getIngameLines();

        if (max > 16)
            max = 16;
        for (int i = 1; i < max; i++) {
            String lineValue = UHCFileRegister.getScoreboardConstructFile().getIngameLine(i);
            if (!lineValue.contains("[time infos]"))
                continue;
            if (!UHCFileRegister.getScoreboardFile().isIngameShow("time infos"))
                continue;

            if (GState.isState(GState.PERIOD_OF_PEACE)) {
                String v = UHCFileRegister.getScoreboardFile().getIngamePart("time infos.until damage")
                        .replace("[time]", UHCRegister.getGraceTimer().getTime()).replace("[i]", iden);

                ssb.setValue(i, v, iden);

            } else if (GState.isState(GState.WARMUP)) {
                String v = UHCFileRegister.getScoreboardFile().getIngamePart("time infos.until pvp")
                        .replace("[time]", UHCRegister.getWarmUpTimer().getTime()).replace("[i]", iden);

                ssb.setValue(i, v, iden);

            } else if (GState.isState(GState.IN_GAME)) {
                String v = UHCFileRegister.getScoreboardFile()
                        .getIngamePart("time infos.until deathmatch")
                        .replace("[time]", UHCRegister.getDeathmatchTimer().getTime()).replace("[i]", iden);

                ssb.setValue(i, v, iden);
            } else if (GState.isState(GState.DEATHMATCH_WARMUP)) {
                String v = UHCFileRegister.getScoreboardFile()
                        .getIngamePart("time infos.until deathmatch")
                        .replace("[time]", UHCRegister.getStartDeathmatchTimer().getTime()).replace("[i]", iden);

                ssb.setValue(i, v, iden);
            } else if (GState.isState(GState.DEATHMATCH)) {
                String v = UHCFileRegister.getScoreboardFile().getIngamePart("time infos.deathmatch")
                        .replace("[i]", iden);

                ssb.setValue(i, v, iden);
            } else if (GState.isState(GState.END)) {
                String v = UHCFileRegister.getScoreboardFile().getIngamePart("end").replace("[i]", iden);

                ssb.setValue(i, v, iden);
            }
        }
    }

    public void updateBorder(Player p) {
        SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-ingame");

        int max = UHCFileRegister.getScoreboardConstructFile().getIngameLines();

        if (max > 16)
            max = 16;
        for (int i = 1; i < max; i++) {
            String lineValue = UHCFileRegister.getScoreboardConstructFile().getIngameLine(i);
            if (!lineValue.contains("[border]"))
                continue;
            if (!UHCFileRegister.getScoreboardFile().isIngameShow("border"))
                continue;

            String v = UHCFileRegister.getScoreboardFile().getIngamePart("border")
                    .replace("[border]", Integer.toString(UHCRegister.getBorderUtil().getSize()))
                    .replace("[i]", iden);

            ssb.setValue(i, v, iden);
        }
    }
}
