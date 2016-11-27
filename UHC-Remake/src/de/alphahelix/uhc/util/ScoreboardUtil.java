package de.alphahelix.uhc.util;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.scoreboard.SimpleScoreboard;
import org.bukkit.entity.Player;

public class ScoreboardUtil extends Util {

	private static String iden;

	public ScoreboardUtil(UHC uhc) {
		super(uhc);
		iden = getRegister().getScoreboardFile().getColorString("Lobby.message.identifier ([i])");
	}

	public void setLobbyScoreboard(Player p) {
		if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.scoreboard"))
			return;

		SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), "uhc-lobby",
				getRegister().getScoreboardFile().getColorString("Lobby.title"), iden);
		int max = getRegister().getScoreboardConstructFile().getInt("Lobby.lines");

		if (max > 16)
			max = 16;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Lobby.line." + i);
			if (lineValue.contains("[blank]")) {
				String repeat = new String(new char[i]).replace("\0", " ");
				ssb.setValue(i, repeat, iden);
			}

			else if (lineValue.contains("[kills]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.kills"))
					continue;
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Lobby.message.kills")
								.replace("[kills]", Integer.toString(getRegister().getStatsUtil().getKills(p)))
								.replace("[i]", iden),
						iden);
			}

			else if (lineValue.contains("[deaths]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.deaths"))
					continue;
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Lobby.message.deaths")
								.replace("[deaths]", Integer.toString(getRegister().getStatsUtil().getDeaths(p)))
								.replace("[i]", iden),
						iden);
			}

			else if (lineValue.contains("[coins]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.coins"))
					continue;
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Lobby.message.coins")
								.replace("[coins]", Integer.toString(getRegister().getStatsUtil().getCoins(p)))
								.replace("[i]", iden),
						iden);
			}

			else if (lineValue.contains("[points]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.points"))
					continue;
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Lobby.message.points")
								.replace("[points]", Integer.toString(getRegister().getStatsUtil().getPoints(p)))
								.replace("[i]", iden),
						iden);
			}

			else if (lineValue.contains("[team]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.team"))
					continue;
				if (getRegister().getTeamManagerUtil().isInOneTeam(p) == null) {
					ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.team")
							.replace("[team]", "-").replace("[i]", iden), iden);
				}
			}

			else if (lineValue.contains("[kit]") || lineValue.contains("[scenario]")) {
				if (getUhc().isKits() && !getUhc().isScenarios()) {
					if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.kit"))
						continue;
					ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.kit")
							.replace("[kit]", "-").replace("[i]", iden), iden);
				}

				else if (getUhc().isScenarios()) {
					if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.scenario"))
						continue;

					ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.scenario")
							.replace("[scenario]", "-").replace("[i]", iden), iden);

					if (!getUhc().isScenarioVoting())
						ssb.updateValue(i,
								getRegister().getScoreboardFile().getColorString("Lobby.message.scenario")
										.replace("[scenario]",
												getRegister().getScenarioFile()
														.getCustomScenarioName(Scenarios.getScenario()))
										.replace("[i]", iden),
								iden);
				}
			}

			else if (lineValue.contains("[bar]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.bar"))
					continue;
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Lobby.message.bar").replace("[i]", iden),
						iden);
			}
		}

		p.setScoreboard(ssb.getScoreboard());
	}

	public void updateScenario(Player p, Scenarios played) {
		SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-lobby");

		int max = getRegister().getScoreboardConstructFile().getInt("Lobby.lines");

		if (max > 16)
			max = 16;

		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Lobby.line." + i);
			if (!lineValue.contains("[kit]") || lineValue.contains("[scenario]"))
				continue;

			ssb.updateValue(i,
					getRegister().getScoreboardFile().getColorString("Lobby.message.scenario")
							.replace("[scenario]", getRegister().getScenarioFile().getCustomScenarioName(played))
							.replace("[i]", iden),
					iden);
		}
	}

	public void updateKit(Player p, Kit k) {
		SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-lobby");

		int max = getRegister().getScoreboardConstructFile().getInt("Lobby.lines");

		if (max > 16)
			max = 16;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Lobby.line." + i);
			if (!lineValue.contains("[kit]"))
				continue;
			ssb.updateValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.kit")
					.replace("[kit]", k.getName().replace("_", " ")).replace("[i]", iden), iden);
		}
	}

	public void updateTeam(Player p, UHCTeam t) {
		SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-lobby");

		int max = getRegister().getScoreboardConstructFile().getInt("Lobby.lines");

		if (max > 16)
			max = 16;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Lobby.line." + i);
			if (!lineValue.contains("[team]"))
				continue;
			ssb.updateValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.team")
					.replace("[team]", t.getPrefix() + t.getName()).replace("[i]", iden), iden);
		}
	}

	public void setIngameScoreboard(Player p) {
		if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.scoreboard"))
			return;

		SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), "uhc-ingame",
				getRegister().getScoreboardFile().getColorString("Ingame.title"), iden);
		int max = getRegister().getScoreboardConstructFile().getInt("Ingame.lines");

		if (max > 16)
			max = 16;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Ingame.line." + i);
			if (lineValue.contains("[out]"))
				continue;

			else if (lineValue.contains("[blank]")) {
				String repeat = new String(new char[i]).replace("\0", " ");
				ssb.setValue(i, repeat, iden);
			}

			else if (lineValue.contains("[alive]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.alive"))
					continue;
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Ingame.message.alive")
								.replace("[alive]",
										Integer.toString(getRegister().getPlayerUtil().getSurvivors().size()))
								.replace("[i]", iden),
						iden);
			}

			else if (lineValue.contains("[specs]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.specs"))
					continue;
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Ingame.message.specs")
								.replace("[specs]", Integer.toString(getRegister().getPlayerUtil().getDeads().size()))
								.replace("[i]", iden),
						iden);
			}

			if (lineValue.contains("[kit]") || lineValue.contains("[scenario]")) {
				if (getUhc().isKits() && !getUhc().isScenarios()) {
					if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.kit"))
						continue;

					ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.kit")
							.replace("[kit]", "-"), iden);

					if (getRegister().getKitsFile().getKitByPlayer(p) != null) {
						ssb.updateValue(i,
								getRegister().getScoreboardFile()
										.getColorString("Ingame.message.kit").replace("[kit]", getRegister()
												.getKitsFile().getKitByPlayer(p).getName().replace("_", " "))
										.replace("[i]", iden),
								iden);
					}
				} else if (getUhc().isScenarios()) {
					if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.scenario"))
						continue;

					ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.scenario")
							.replace("[scenario]", "-"), iden);

					ssb.updateValue(i,
							getRegister().getScoreboardFile().getColorString("Ingame.message.scenario")
									.replace("[scenario]",
											getRegister().getScenarioFile()
													.getCustomScenarioName(Scenarios.getScenario()))
									.replace("[i]", iden),
							iden);

				}
			}

			else if (lineValue.contains("[team]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.team"))
					continue;

				if (getRegister().getTeamManagerUtil().isInOneTeam(p) == null) {
					ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.team")
							.replace("[team]", "-").replace("[i]", iden), iden);
				}

				else {
					ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.team")
							.replace("[team]", "-").replace("[i]", iden), iden);

					ssb.updateValue(i,
							getRegister().getScoreboardFile().getColorString("Ingame.message.team")
									.replace("[team]",
											getRegister().getTeamManagerUtil().isInOneTeam(p).getPrefix()
													+ getRegister().getTeamManagerUtil().isInOneTeam(p).getName())
									.replace("[i]", iden),
							iden);
				}
			}

			else if (lineValue.contains("[center]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.center"))
					continue;
				String v = getRegister().getScoreboardFile().getColorString("Ingame.message.center")
						.replace("[center]", getRegister().getLocationsFile().getArena().getBlockX() + "/"
								+ getRegister().getLocationsFile().getArena().getBlockZ())
						.replace("[i]", iden);

				ssb.setValue(i, v, iden);
			}

			else if (lineValue.contains("[border]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.border"))
					continue;
				String v = getRegister().getScoreboardFile().getColorString("Ingame.message.border")
						.replace("[border]", Integer.toString(getRegister().getBorderUtil().getSize()))
						.replace("[i]", iden);

				ssb.setValue(i, v, iden);
			}

			else if (lineValue.contains("[time infos]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.time infos"))
					continue;
				if (GState.isState(GState.PERIOD_OF_PEACE)) {
					String v = getRegister().getScoreboardFile()
							.getColorString("Ingame.message.time infos.until damage")
							.replace("[time]", getRegister().getGraceTimer().getTime()).replace("[i]", iden);

					ssb.setValue(i, v, iden);

				}

				else if (GState.isState(GState.WARMUP)) {
					String v = getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until pvp")
							.replace("[time]", getRegister().getWarmUpTimer().getTime()).replace("[i]", iden);

					ssb.setValue(i, v, iden);
				}

				else if (GState.isState(GState.IN_GAME)) {
					String v = getRegister().getScoreboardFile()
							.getColorString("Ingame.message.time infos.until deathmatch")
							.replace("[time]", getRegister().getDeathmatchTimer().getTime()).replace("[i]", iden);

					System.out.println(v.length());

					ssb.setValue(i, v, iden);
				}

				else if (GState.isState(GState.DEATHMATCH_WARMUP)) {
					String v = getRegister().getScoreboardFile()
							.getColorString("Ingame.message.time infos.until deathmatch")
							.replace("[time]", getRegister().getStartDeathmatchTimer().getTime()).replace("[i]", iden);

					ssb.setValue(i, v, iden);
				}

				else if (GState.isState(GState.DEATHMATCH)) {
					String v = getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.deathmatch")
							.replace("[i]", iden);

					ssb.setValue(i, v, iden);
				}

				else if (GState.isState(GState.END)) {
					String v = getRegister().getScoreboardFile().getColorString("Ingame.message.end").replace("[i]",
							iden);

					ssb.setValue(i, v, iden);
				}
			}

			else if (lineValue.contains("[bar]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.bar"))
					continue;
				String v = getRegister().getScoreboardFile().getColorString("Ingame.message.bar").replace("[i]", iden);

				ssb.setValue(i, v, iden);
			}
		}

		p.setScoreboard(ssb.getScoreboard());
	}

	public void updateAlive(Player p) {
		SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-ingame");

		int max = getRegister().getScoreboardConstructFile().getInt("Ingame.lines");

		if (max > 16)
			max = 16;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Ingame.line." + i);
			if (!lineValue.contains("[alive]"))
				continue;
			if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.alive"))
				continue;
			ssb.setValue(i,
					getRegister().getScoreboardFile().getColorString("Ingame.message.alive")
							.replace("[alive]", Integer.toString(getRegister().getPlayerUtil().getSurvivors().size()))
							.replace("[i]", iden),
					iden);
		}
	}

	public void updateSpecs(Player p) {
		SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-ingame");

		int max = getRegister().getScoreboardConstructFile().getInt("Ingame.lines");

		if (max > 16)
			max = 16;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Ingame.line." + i);
			if (!lineValue.contains("[specs]"))
				continue;
			if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.specs"))
				continue;
			ssb.setValue(i,
					getRegister().getScoreboardFile().getColorString("Ingame.message.specs")
							.replace("[specs]", Integer.toString(getRegister().getPlayerUtil().getDeads().size()))
							.replace("[i]", iden),
					iden);
		}
	}

	public void updateTime(Player p) {
		SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-ingame");

		int max = getRegister().getScoreboardConstructFile().getInt("Ingame.lines");

		if (max > 16)
			max = 16;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Ingame.line." + i);
			if (!lineValue.contains("[time infos]"))
				continue;
			if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.time infos"))
				continue;

			if (GState.isState(GState.PERIOD_OF_PEACE)) {
				String v = getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until damage")
						.replace("[time]", getRegister().getGraceTimer().getTime()).replace("[i]", iden);

				ssb.setValue(i, v, iden);

			}

			else if (GState.isState(GState.WARMUP)) {
				String v = getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until pvp")
						.replace("[time]", getRegister().getWarmUpTimer().getTime()).replace("[i]", iden);

				ssb.setValue(i, v, iden);

			}

			else if (GState.isState(GState.IN_GAME)) {
				String v = getRegister().getScoreboardFile()
						.getColorString("Ingame.message.time infos.until deathmatch")
						.replace("[time]", getRegister().getDeathmatchTimer().getTime()).replace("[i]", iden);

				ssb.setValue(i, v, iden);
			}

			else if (GState.isState(GState.DEATHMATCH_WARMUP)) {
				String v = getRegister().getScoreboardFile()
						.getColorString("Ingame.message.time infos.until deathmatch")
						.replace("[time]", getRegister().getStartDeathmatchTimer().getTime()).replace("[i]", iden);

				ssb.setValue(i, v, iden);
			}

			else if (GState.isState(GState.DEATHMATCH)) {
				String v = getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.deathmatch")
						.replace("[i]", iden);

				ssb.setValue(i, v, iden);
			}

			else if (GState.isState(GState.END)) {
				String v = getRegister().getScoreboardFile().getColorString("Ingame.message.end").replace("[i]", iden);

				ssb.setValue(i, v, iden);
			}
		}
	}

	public void updateBorder(Player p) {
		SimpleScoreboard<UHC> ssb = new SimpleScoreboard<>(getUhc(), p, "uhc-ingame");

		int max = getRegister().getScoreboardConstructFile().getInt("Ingame.lines");

		if (max > 16)
			max = 16;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Ingame.line." + i);
			if (!lineValue.contains("[border]"))
				continue;
			if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.border"))
				continue;

			String v = getRegister().getScoreboardFile().getColorString("Ingame.message.border")
					.replace("[border]", Integer.toString(getRegister().getBorderUtil().getSize()))
					.replace("[i]", iden);

			ssb.setValue(i, v, iden);
		}
	}
}
