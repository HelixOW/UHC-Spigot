package de.alphahelix.uhc.util;

import de.alphahelix.alphaapi.scoreboard.SimpleScoreboard;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ScoreboardUtil {

    private static String iden;

    public static void setIden(String iden) {
        ScoreboardUtil.iden = iden;
    }

    public static void setLobbyScoreboard(Player p) {
        if (!UHCFileRegister.getScoreboardFile().isLobbyShow("scoreboard"))
            return;

        SimpleScoreboard ssb = new SimpleScoreboard("uhc-lobby",
                UHCFileRegister.getScoreboardFile().getLobbyTitle(), iden);
        int max = UHCFileRegister.getScoreboardConstructFile().getLobbyLines();
        UUID id = UUIDFetcher.getUUID(p);

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
                                .replace("[kills]", Long.toString(StatsUtil.getKills(id)))
                                .replace("[i]", iden),
                        iden);
            } else if (lineValue.contains("[deaths]")) {
                if (!UHCFileRegister.getScoreboardFile().isLobbyShow("deaths"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getLobbyPart("deaths")
                                .replace("[deaths]", Long.toString(StatsUtil.getDeaths(id)))
                                .replace("[i]", iden),
                        iden);
            } else if (lineValue.contains("[coins]")) {
                if (!UHCFileRegister.getScoreboardFile().isLobbyShow("coins"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getLobbyPart("coins")
                                .replace("[coins]", Long.toString(StatsUtil.getCoins(id)))
                                .replace("[i]", iden),
                        iden);
            } else if (lineValue.contains("[points]")) {
                if (!UHCFileRegister.getScoreboardFile().isLobbyShow("points"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getLobbyPart("points")
                                .replace("[points]", Long.toString(StatsUtil.getPoints(id)))
                                .replace("[i]", iden),
                        iden);
            } else if (lineValue.contains("[team]")) {
                if (!UHCFileRegister.getScoreboardFile().isLobbyShow("team"))
                    continue;
                if (TeamManagerUtil.isInOneTeam(p) == null) {
                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getLobbyPart("team")
                            .replace("[team]", "-").replace("[i]", iden), iden);
                }
            } else if (lineValue.contains("[kit]") || lineValue.contains("[scenario]")) {
                if (UHC.isKits() && !UHC.isScenarios()) {
                    if (!UHCFileRegister.getScoreboardFile().isLobbyShow("kit"))
                        continue;
                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getLobbyPart("kit")
                            .replace("[kit]", "-").replace("[i]", iden), iden);
                } else if (UHC.isScenarios()) {
                    if (!UHCFileRegister.getScoreboardFile().isLobbyShow("scenario"))
                        continue;

                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getLobbyPart("scenario")
                            .replace("[scenario]", "-").replace("[i]", iden), iden);

                    if (!UHC.isScenarioVoting())
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

        p.setScoreboard(ssb.buildScoreboard());
    }

    public static void updateScenario(Player p, Scenarios played) {
        SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-lobby");

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

    public static void updateKit(Player p, Kit k) {
        SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-lobby");

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

    public static void updateCoins(Player p, long coins) {
        SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-lobby");

        int max = UHCFileRegister.getScoreboardConstructFile().getLobbyLines();

        if (max > 16)
            max = 16;
        for (int i = 1; i < max; i++) {
            String lineValue = UHCFileRegister.getScoreboardConstructFile().getLobbyLine(i);
            if (!lineValue.contains("[coins]"))
                continue;
            ssb.updateValue(i, UHCFileRegister.getScoreboardFile().getLobbyPart("coins")
                    .replace("[coins]", Long.toString(coins)).replace("[i]", iden), iden);
        }
    }

    public static void updateTeam(Player p, UHCTeam t) {
        SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-lobby");

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

    public static void setIngameScoreboard(Player p) {
        if (!UHCFileRegister.getScoreboardFile().isIngameShow("scoreboard"))
            return;

        iden = UHCFileRegister.getScoreboardFile().getIngameIdentifier();

        SimpleScoreboard ssb = new SimpleScoreboard("uhc-ingame",
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
                                        Integer.toString(PlayerUtil.getSurvivors().size()))
                                .replace("[i]", iden),
                        iden);
            } else if (lineValue.contains("[specs]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("specs"))
                    continue;
                ssb.setValue(i,
                        UHCFileRegister.getScoreboardFile().getIngamePart("specs")
                                .replace("[specs]", Integer.toString(PlayerUtil.getDeads().size()))
                                .replace("[i]", iden),
                        iden);
            }

            if (lineValue.contains("[kit]") || lineValue.contains("[scenario]")) {
                if (UHC.isKits() && !UHC.isScenarios()) {
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
                } else if (UHC.isScenarios()) {
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

                if (TeamManagerUtil.isInOneTeam(p) == null) {
                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getIngamePart("team")
                            .replace("[team]", "-").replace("[i]", iden), iden);
                } else {
                    ssb.setValue(i, UHCFileRegister.getScoreboardFile().getIngamePart("team")
                            .replace("[team]", "-").replace("[i]", iden), iden);

                    ssb.updateValue(i,
                            UHCFileRegister.getScoreboardFile().getIngamePart("team")
                                    .replace("[team]",
                                            TeamManagerUtil.isInOneTeam(p).getPrefix()
                                                    + TeamManagerUtil.isInOneTeam(p).getName())
                                    .replace("[i]", iden),
                            iden);
                }
            } else if (lineValue.contains("[center]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("center"))
                    continue;
                String v = UHCFileRegister.getScoreboardFile().getIngamePart("center")
                        .replace("[center]", Bukkit.getWorld("UHC").getSpawnLocation().getBlockX() + "/"
                                + Bukkit.getWorld("UHC").getSpawnLocation().getBlockZ())
                        .replace("[i]", iden);

                ssb.setValue(i, v, iden);
            } else if (lineValue.contains("[border]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("border"))
                    continue;
                String v = UHCFileRegister.getScoreboardFile().getIngamePart("border")
                        .replace("[border]", Integer.toString(BorderUtil.getSize()))
                        .replace("[i]", iden);

                ssb.setValue(i, v, iden);
            } else if (lineValue.contains("[time infos]")) {
                if (!UHCFileRegister.getScoreboardFile().isIngameShow("time infos"))
                    continue;
                if (GState.isState(GState.PERIOD_OF_PEACE)) {
                    String v = UHCFileRegister.getScoreboardFile()
                            .getIngamePart("time infos.until damage")
                            .replace("[time]", UHCRegister.getGraceTimer().getSecondsText()).replace("[i]", iden);

                    ssb.setValue(i, v, iden);

                } else if (GState.isState(GState.WARMUP)) {
                    String v = UHCFileRegister.getScoreboardFile().getIngamePart("time infos.until pvp")
                            .replace("[time]", UHCRegister.getWarmUpTimer().getSecondsText()).replace("[i]", iden);

                    ssb.setValue(i, v, iden);
                } else if (GState.isState(GState.IN_GAME)) {
                    String v = UHCFileRegister.getScoreboardFile()
                            .getIngamePart("time infos.until deathmatch")
                            .replace("[time]", UHCRegister.getDeathmatchTimer().getSecondsText()).replace("[i]", iden);

                    ssb.setValue(i, v, iden);
                } else if (GState.isState(GState.DEATHMATCH_WARMUP)) {
                    String v = UHCFileRegister.getScoreboardFile()
                            .getIngamePart("time infos.until deathmatch")
                            .replace("[time]", UHCRegister.getStartDeathMatchTimer().getSecondsText()).replace("[i]", iden);

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

        p.setScoreboard(ssb.buildScoreboard());
    }

    public static void updateAlive(Player p) {
        SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-ingame");

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
                            .replace("[alive]", Integer.toString(PlayerUtil.getSurvivors().size()))
                            .replace("[i]", iden),
                    iden);
        }
    }

    public static void updateSpecs(Player p) {
        SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-ingame");

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
                            .replace("[specs]", Integer.toString(PlayerUtil.getDeads().size()))
                            .replace("[i]", iden),
                    iden);
        }
    }

    public static void updateTime(Player p) {
        SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-ingame");

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
                        .replace("[time]", UHCRegister.getGraceTimer().getSecondsText()).replace("[i]", iden);

                ssb.setValue(i, v, iden);

            } else if (GState.isState(GState.WARMUP)) {
                String v = UHCFileRegister.getScoreboardFile().getIngamePart("time infos.until pvp")
                        .replace("[time]", UHCRegister.getWarmUpTimer().getSecondsText()).replace("[i]", iden);

                ssb.setValue(i, v, iden);

            } else if (GState.isState(GState.IN_GAME)) {
                String v = UHCFileRegister.getScoreboardFile()
                        .getIngamePart("time infos.until deathmatch")
                        .replace("[time]", UHCRegister.getDeathmatchTimer().getSecondsText()).replace("[i]", iden);

                ssb.setValue(i, v, iden);
            } else if (GState.isState(GState.DEATHMATCH_WARMUP)) {
                String v = UHCFileRegister.getScoreboardFile()
                        .getIngamePart("time infos.until deathmatch")
                        .replace("[time]", UHCRegister.getStartDeathMatchTimer().getSecondsText()).replace("[i]", iden);

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

    public static void updateBorder(Player p) {
        SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-ingame");

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
                    .replace("[border]", Integer.toString(BorderUtil.getSize()))
                    .replace("[i]", iden);

            ssb.setValue(i, v, iden);
        }
    }
}
