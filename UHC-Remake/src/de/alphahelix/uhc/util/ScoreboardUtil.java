package de.alphahelix.uhc.util;

import java.util.logging.Level;

import org.bukkit.entity.Player;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.scoreboard.SimpleScoreboard;

public class ScoreboardUtil extends Util {

	public ScoreboardUtil(UHC uhc) {
		super(uhc);
	}

	public void setLobbyScoreboard(Player p) {
		if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.scoreboard"))
			return;

		SimpleScoreboard ssb = new SimpleScoreboard("uhc-lobby",
				getRegister().getScoreboardFile().getColorString("Lobby.title"));
		int max = getRegister().getScoreboardConstructFile().getInt("Lobby.lines");

		if (max > 32)
			return;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Lobby.line." + i);
			if (lineValue.contains("[out]"))
				continue;
			else if (lineValue.contains("[blank]")) {
				String repeat = new String(new char[i]).replace("\0", " ");
				ssb.setValue(i, repeat);
			} else if (lineValue.contains("[kills]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.kills"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Lobby.message.kills")
						.replace("[kills]", Integer.toString(getRegister().getStatsUtil().getKills(p))).length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for kills is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.kills")
						.replace("[kills]", Integer.toString(getRegister().getStatsUtil().getKills(p))));
			} else if (lineValue.contains("[deaths]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.deaths"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Lobby.message.deaths")
						.replace("[deaths]", Integer.toString(getRegister().getStatsUtil().getDeaths(p)))
						.length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for deaths is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.deaths")
						.replace("[deaths]", Integer.toString(getRegister().getStatsUtil().getDeaths(p))));
			} else if (lineValue.contains("[coins]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.coins"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Lobby.message.coins")
						.replace("[coins]", Integer.toString(getRegister().getStatsUtil().getCoins(p))).length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for coins is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.coins")
						.replace("[coins]", Integer.toString(getRegister().getStatsUtil().getCoins(p))));
			} else if (lineValue.contains("[points]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.points"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Lobby.message.points")
						.replace("[points]", Integer.toString(getRegister().getStatsUtil().getPoints(p)))
						.length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for points is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.points")
						.replace("[points]", Integer.toString(getRegister().getStatsUtil().getPoints(p))));
			} else if (lineValue.contains("[team]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.team"))
					continue;
				if (getRegister().getTeamManagerUtil().isInOneTeam(p) == null) {
					if (getRegister().getScoreboardFile().getColorString("Lobby.message.team").replace("[team]", "-")
							.length() > 32) {
						getUhc().getLog().log(Level.WARNING,
								"String for team is too long. Short in your scoreboard.uhc");
						continue;
					}
					ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.team")
							.replace("[team]", "-"));
				}
			} else if (lineValue.contains("[kit]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.kit"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Lobby.message.kit").replace("[kit]", "-")
						.length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for kit is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Lobby.message.kit").replace("[kit]", "-"));
			} else if (lineValue.contains("[bar]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Lobby.show.bar"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Lobby.message.bar").length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for bar is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.bar"));
			}
		}

		p.setScoreboard(ssb.getScoreboard());
	}

	public void updateKit(Player p, Kit k) {
		SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-lobby");

		int max = getRegister().getScoreboardConstructFile().getInt("Lobby.lines");

		if (max > 32)
			return;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Lobby.line." + i);
			if (!lineValue.contains("[kit]"))
				continue;
			if (getRegister().getScoreboardFile().getColorString("Lobby.message.kit").replace("[kit]", k.getName())
					.length() > 32) {
				getUhc().getLog().log(Level.WARNING, "String for kit is too long. Short in your scoreboard.uhc");
				continue;
			}
			ssb.updateValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.kit").replace("[kit]",
					k.getName()));
		}
	}

	public void updateTeam(Player p, UHCTeam t) {
		SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-lobby");

		int max = getRegister().getScoreboardConstructFile().getInt("Lobby.lines");

		if (max > 32)
			return;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Lobby.line." + i);
			if (!lineValue.contains("[team]"))
				continue;
			if (getRegister().getScoreboardFile().getColorString("Lobby.message.team")
					.replace("[team]", t.getPrefix() + t.getName()).length() > 32) {
				getUhc().getLog().log(Level.WARNING, "String for team is too long. Short in your scoreboard.uhc");
				continue;
			}
			ssb.updateValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.team").replace("[team]",
					t.getPrefix() + t.getName()));
		}
	}

	public void setIngameScoreboard(Player p) {
		if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.scoreboard"))
			return;

		SimpleScoreboard ssb = new SimpleScoreboard("uhc-ingame",
				getRegister().getScoreboardFile().getColorString("Ingame.title"));
		int max = getRegister().getScoreboardConstructFile().getInt("Ingame.lines");

		if (max > 32)
			return;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Ingame.line." + i);
			if (lineValue.contains("[out]"))
				continue;
			else if (lineValue.contains("[blank]")) {
				String repeat = new String(new char[i]).replace("\0", " ");
				ssb.setValue(i, repeat);
			} else if (lineValue.contains("[alive]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.alive"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Ingame.message.alive")
						.replace("[alive]", Integer.toString(getRegister().getPlayerUtil().getSurvivors().size()))
						.length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for alive is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.alive")
						.replace("[alive]", Integer.toString(getRegister().getPlayerUtil().getSurvivors().size())));
			} else if (lineValue.contains("[specs]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.specs"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Ingame.message.specs")
						.replace("[specs]", Integer.toString(getRegister().getPlayerUtil().getDeads().size()))
						.length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for dead is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.specs")
						.replace("[specs]", Integer.toString(getRegister().getPlayerUtil().getDeads().size())));
			} else if (lineValue.contains("[kit]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.kit"))
					continue;

				if (getRegister().getScoreboardFile().getColorString("Ingame.message.kit").replace("[kit]", "-")
						.length() > 32) {
					getUhc().getLog().log(Level.WARNING,
							"String for kit[ingame] is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Ingame.message.kit").replace("[kit]", "-"));

				if (getRegister().getKitsFile().getKitByPlayer(p) != null) {
					if (getRegister().getScoreboardFile().getColorString("Ingame.message.kit")
							.replace("[kit]", getRegister().getKitsFile().getKitByPlayer(p).getName()).length() > 32) {
						getUhc().getLog().log(Level.WARNING,
								"String for kit[ingame] is too long. Short in your scoreboard.uhc");
						continue;
					}
					ssb.updateValue(i, getRegister().getScoreboardFile().getColorString("Lobby.message.kit")
							.replace("[kit]", getRegister().getKitsFile().getKitByPlayer(p).getName()));
				}
			} else if (lineValue.contains("[team]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.team"))
					continue;
				if (getRegister().getTeamManagerUtil().isInOneTeam(p) == null) {
					if (getRegister().getScoreboardFile().getColorString("Ingame.message.team").replace("[team]", "-")
							.length() > 32) {
						getUhc().getLog().log(Level.WARNING,
								"String for team is too long. Short in your scoreboard.uhc");
						continue;
					}
					ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.team")
							.replace("[team]", "-"));
				} else {
					if (getRegister().getScoreboardFile().getColorString("Ingame.message.team")
							.replace("[team]",
									getRegister().getTeamManagerUtil().isInOneTeam(p).getPrefix()
											+ getRegister().getTeamManagerUtil().isInOneTeam(p).getName())
							.length() > 32) {
						getUhc().getLog().log(Level.WARNING,
								"String for team is too long. Short in your scoreboard.uhc");
						continue;
					}
					ssb.setValue(i,
							getRegister().getScoreboardFile().getColorString("Ingame.message.team").replace("[team]",
									getRegister().getTeamManagerUtil().isInOneTeam(p).getPrefix()
											+ getRegister().getTeamManagerUtil().isInOneTeam(p).getName()));
				}
			} else if (lineValue.contains("[center]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.center"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Ingame.message.center")
						.replace("[center]", getRegister().getLocationsFile().getArena().getBlockX() + "/"
								+ getRegister().getLocationsFile().getArena().getBlockZ())
						.length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for center is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Ingame.message.center").replace("[center]",
								getRegister().getLocationsFile().getArena().getBlockX() + "/"
										+ getRegister().getLocationsFile().getArena().getBlockZ()));
			} else if (lineValue.contains("[border]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.border"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Ingame.message.border")
						.replace("[border]", Integer.toString(getRegister().getBorderUtil().getSize())).length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for border is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.border")
						.replace("[border]", Integer.toString(getRegister().getBorderUtil().getSize())));
			} else if (lineValue.contains("[time infos]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.time infos"))
					continue;
				if (GState.isState(GState.PERIOD_OF_PEACE)) {
					if (getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until damage")
							.replace("[time]", getRegister().getGraceTimer().getTime()).length() > 32) {
						getUhc().getLog().log(Level.WARNING,
								"String for time infos[until damage] is too long. Short in your scoreboard.uhc");
						continue;
					}
					ssb.setValue(i,
							getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until damage")
									.replace("[time]", getRegister().getGraceTimer().getTime()));

				} else if (GState.isState(GState.WARMUP)) {
					if (getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until pvp")
							.replace("[time]", getRegister().getWarmUpTimer().getTime()).length() > 32) {
						getUhc().getLog().log(Level.WARNING,
								"String for time infos[until damage] is too long. Short in your scoreboard.uhc");
						continue;
					}
					ssb.setValue(i,
							getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until pvp")
									.replace("[time]", getRegister().getWarmUpTimer().getTime()));
				} else if (GState.isState(GState.IN_GAME)) {
					if (getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until deathmatch")
							.replace("[time]", getRegister().getDeathmatchTimer().getTime()).length() > 32) {
						getUhc().getLog().log(Level.WARNING,
								"String for time infos[until damage] is too long. Short in your scoreboard.uhc");
						continue;
					}
					ssb.setValue(i,
							getRegister().getScoreboardFile()
									.getColorString("Ingame.message.time infos.until deathmatch")
									.replace("[time]", getRegister().getDeathmatchTimer().getTime()));
				} else if (GState.isState(GState.DEATHMATCH_WARMUP)) {
					if (getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until deathmatch")
							.replace("[time]", getRegister().getStartDeathmatchTimer().getTime()).length() > 32) {
						getUhc().getLog().log(Level.WARNING,
								"String for time infos[until damage] is too long. Short in your scoreboard.uhc");
						continue;
					}
					ssb.setValue(i,
							getRegister().getScoreboardFile()
									.getColorString("Ingame.message.time infos.until deathmatch")
									.replace("[time]", getRegister().getStartDeathmatchTimer().getTime()));
				} else if (GState.isState(GState.DEATHMATCH)) {
					if (getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.deathmatch")
							.length() > 32) {
						getUhc().getLog().log(Level.WARNING,
								"String for time infos[until damage] is too long. Short in your scoreboard.uhc");
						continue;
					}
					ssb.setValue(i,
							getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.deathmatch"));
				} else if (GState.isState(GState.END)) {
					if (getRegister().getScoreboardFile().getColorString("Ingame.message.winner")
							.replace("[player]", getRegister().getGameEndsListener().getWinnerName()).length() > 32) {
						getUhc().getLog().log(Level.WARNING,
								"String for winner is too long. Short in your scoreboard.uhc");
						continue;
					}
					ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.winner")
							.replace("[player]", getRegister().getGameEndsListener().getWinnerName()));
				}
			} else if (lineValue.contains("[bar]")) {
				if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.bar"))
					continue;
				if (getRegister().getScoreboardFile().getColorString("Ingame.message.bar").length() > 32) {
					getUhc().getLog().log(Level.WARNING,
							"String for bar[ingame] is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.bar"));
			}
		}

		p.setScoreboard(ssb.getScoreboard());
	}

	public void updateAlive(Player p) {
		SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-ingame");

		int max = getRegister().getScoreboardConstructFile().getInt("Ingame.lines");

		if (max > 32)
			return;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Ingame.line." + i);
			if (!lineValue.contains("[alive]"))
				continue;
			if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.alive"))
				continue;
			if (getRegister().getScoreboardFile().getColorString("Ingame.message.alive")
					.replace("[alive]", Integer.toString(getRegister().getPlayerUtil().getSurvivors().size()))
					.length() > 32) {
				getUhc().getLog().log(Level.WARNING, "String for alive is too long. Short in your scoreboard.uhc");
				continue;
			}
			ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.alive").replace("[alive]",
					Integer.toString(getRegister().getPlayerUtil().getSurvivors().size())));
		}
	}

	public void updateSpecs(Player p) {
		SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-ingame");

		int max = getRegister().getScoreboardConstructFile().getInt("Ingame.lines");

		if (max > 32)
			return;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Ingame.line." + i);
			if (!lineValue.contains("[specs]"))
				continue;
			if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.specs"))
				continue;
			if (getRegister().getScoreboardFile().getColorString("Ingame.message.specs")
					.replace("[specs]", Integer.toString(getRegister().getPlayerUtil().getDeads().size()))
					.length() > 32) {
				getUhc().getLog().log(Level.WARNING, "String for specs is too long. Short in your scoreboard.uhc");
				continue;
			}
			ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.specs").replace("[specs]",
					Integer.toString(getRegister().getPlayerUtil().getDeads().size())));
		}
	}

	public void updateTime(Player p) {
		SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-ingame");

		int max = getRegister().getScoreboardConstructFile().getInt("Ingame.lines");

		if (max > 32)
			return;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Ingame.line." + i);
			if (!lineValue.contains("[time infos]"))
				continue;
			if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.time infos"))
				continue;
			if (GState.isState(GState.PERIOD_OF_PEACE)) {
				if (getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until damage")
						.replace("[time]", getRegister().getGraceTimer().getTime()).length() > 32) {
					getUhc().getLog().log(Level.WARNING,
							"String for time infos[until damage] is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until damage")
								.replace("[time]", getRegister().getGraceTimer().getTime()));
			}

			else if (GState.isState(GState.WARMUP)) {
				if (getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until pvp")
						.replace("[time]", getRegister().getWarmUpTimer().getTime()).length() > 32) {
					getUhc().getLog().log(Level.WARNING,
							"String for time infos[until damage] is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.updateValue(i,
						getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until pvp")
								.replace("[time]", getRegister().getWarmUpTimer().getTime()));
			}

			else if (GState.isState(GState.IN_GAME)) {
				if (getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until deathmatch")
						.replace("[time]", getRegister().getDeathmatchTimer().getTime()).length() > 32) {
					getUhc().getLog().log(Level.WARNING,
							"String for time infos[until damage] is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until deathmatch")
								.replace("[time]", getRegister().getDeathmatchTimer().getTime()));
			}

			else if (GState.isState(GState.DEATHMATCH_WARMUP)) {
				if (getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until deathmatch")
						.replace("[time]", getRegister().getStartDeathmatchTimer().getTime()).length() > 32) {
					getUhc().getLog().log(Level.WARNING,
							"String for time infos[until damage] is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i,
						getRegister().getScoreboardFile().getColorString("Ingame.message.time infos.until deathmatch")
								.replace("[time]", getRegister().getStartDeathmatchTimer().getTime()));
			}

			else if (GState.isState(GState.END)) {
				if (getRegister().getScoreboardFile().getColorString("Ingame.message.winner")
						.replace("[player]", getRegister().getGameEndsListener().getWinnerName()).length() > 32) {
					getUhc().getLog().log(Level.WARNING, "String for winner is too long. Short in your scoreboard.uhc");
					continue;
				}
				ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.winner")
						.replace("[player]", getRegister().getGameEndsListener().getWinnerName()));
			}
		}
	}

	public void updateBorder(Player p) {
		SimpleScoreboard ssb = new SimpleScoreboard(p, "uhc-ingame");

		int max = getRegister().getScoreboardConstructFile().getInt("Ingame.lines");

		if (max > 32)
			return;
		for (int i = 0; i < max; i++) {
			String lineValue = getRegister().getScoreboardConstructFile().getColorString("Ingame.line." + i);
			if (!lineValue.contains("[border]"))
				continue;
			if (!getRegister().getScoreboardFile().getBoolean("Ingame.show.border"))
				continue;

			if (getRegister().getScoreboardFile().getColorString("Ingame.message.border")
					.replace("[border]", Integer.toString(getRegister().getBorderUtil().getSize())).length() > 32) {
				getUhc().getLog().log(Level.WARNING, "String for border is too long. Short in your scoreboard.uhc");
				continue;
			}
			ssb.setValue(i, getRegister().getScoreboardFile().getColorString("Ingame.message.border")
					.replace("[border]", Integer.toString(getRegister().getBorderUtil().getSize())));
		}
	}
}
