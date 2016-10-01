package de.alphahelix.uhc.timers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.Sounds;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.nms.SimpleActionBar;
import de.popokaka.alphalibary.nms.SimpleTitle;
import de.popokaka.alphalibary.utils.Cuboid;

public class LobbyTimer extends Util {

	private static BukkitTask timer;
	private static BukkitTask lobby;
	private int time;
	private double min;
	private static boolean isAlreadyPasted;

	public LobbyTimer(UHC uhc) {
		super(uhc);
	}

	public void stopTimer() {
		if (timer != null)
			timer.cancel();
		timer = null;
		if (lobby != null)
			lobby.cancel();
		lobby = null;
		resetTime();
	}

	public boolean isRunning() {
		if (timer != null)
			return true;
		return false;
	}

	public void startLobbyCountdown() {
		if (GState.isState(GState.LOBBY)) {

			if (timer != null) {
				if (Bukkit.getScheduler().isCurrentlyRunning(timer.getTaskId()))
					return;
				return;
			}

			resetTime();

			if (!isAlreadyPasted) {
				isAlreadyPasted = true;
				getRegister().getSchematicManagerUtil().load(getRegister().getMainOptionsFile().getString("Lobby.filename"));
				getRegister().getSchematicManagerUtil().paste(getRegister().getLocationsFile().getArena().add(0, 140, 0));
			}

			timer = new BukkitRunnable() {
				public void run() {

					if (!(getRegister().getPlayerUtil().getAll().size() >= getRegister().getPlayerUtil()
							.getMinimumPlayerCount()))
						return;

					if (time > 0) {
						time--;

						lobby = new BukkitRunnable() {
							public void run() {
								if (getRegister().getPlayerUtil().getAll().size() >= getRegister().getPlayerUtil()
										.getMinimumPlayerCount()) {
									if (time == 0) {
										new BukkitRunnable() {
											public void run() {
												Bukkit.getPluginManager().callEvent(new LobbyEndEvent());
											}
										}.runTaskLater(getUhc(), 20);
									}
									for (String pName : getRegister().getPlayerUtil().getAll()) {
										Player p = Bukkit.getPlayer(pName);

										if (p == null)
											return;

										min = calcMin(time);

										p.setLevel(time);

										if (min % 1 == 0 && time > 10 && time != 0) {
											p.sendMessage(getUhc().getPrefix() + getRegister().getMessageFile()
													.getColorString("Lobby time left info")
													.replace("[time]", Double.toString(round(min, 1)))
													.replace("[unit]", (min >= 0.5
															? getRegister().getUnitFile().getColorString("Minutes")
															: getRegister().getUnitFile().getColorString("Seconds"))));
											SimpleTitle.sendTitle(p, getUhc().getPrefix(), getRegister()
													.getMessageFile().getColorString("Lobby time left info")
													.replace("[time]", Double.toString(round(min, 1)))
													.replace("[unit]", (min >= 0.5
															? getRegister().getUnitFile().getColorString("Minutes")
															: getRegister().getUnitFile().getColorString("Seconds"))),
													1, 2, 1);
											p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);

										}

										if (time % 30 == 0 && !(min % 1 == 0)) {
											p.sendMessage(getUhc().getPrefix() + getRegister().getMessageFile()
													.getColorString("Lobby time left info")
													.replace("[time]", Double.toString(round(min, 1)))
													.replace("[unit]", (min >= 0.5
															? getRegister().getUnitFile().getColorString("Minutes")
															: getRegister().getUnitFile().getColorString("Seconds"))));
											SimpleTitle.sendTitle(p, getUhc().getPrefix(), getRegister()
													.getMessageFile().getColorString("Lobby time left info")
													.replace("[time]", Double.toString(round(min, 1)))
													.replace("[unit]", (min >= 0.5
															? getRegister().getUnitFile().getColorString("Minutes")
															: getRegister().getUnitFile().getColorString("Seconds"))),
													1, 2, 1);
											p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
										}

										if (time < 10 && time != 0) {
											p.sendMessage(getUhc().getPrefix() + getRegister().getMessageFile()
													.getColorString("Lobby time left info")
													.replace("[time]", Double.toString(time))
													.replace("[unit]", "seconds"));
											SimpleActionBar.send(p,
													getUhc().getPrefix() + getRegister().getMessageFile()
															.getColorString("Lobby time left info")
															.replace("[time]", Double.toString(time))
															.replace("[unit]", "seconds"));
											p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
										}

										if (time == 0) {
											timer.cancel();

											p.getInventory().clear();

											if (getUhc().isLobbyAsSchematic()) {
												World w = getRegister().getLocationsFile().getArena().getWorld();
												Location l1 = new Location(w, -75, 155, -75);
												Location l2 = new Location(w, 75, 255, 75);

												for (Block b : new Cuboid(l1, l2).getBlocks()) {
													b.setType(Material.AIR);
												}
											}

											Location worldSpawn = getRegister().getLocationsFile().getArena();
											Location playerSpawn = worldSpawn.getWorld()
													.getHighestBlockAt(getRandomLocation(worldSpawn,
															worldSpawn.getBlockX() - getUhc().getSpawnradius(),
															worldSpawn.getBlockX() + getUhc().getSpawnradius(),
															worldSpawn.getBlockZ() - getUhc().getSpawnradius(),
															worldSpawn.getBlockZ() + getUhc().getSpawnradius()))
													.getLocation();

											p.teleport(playerSpawn);
											getRegister().getBorderUtil().setArena(worldSpawn);

											lobby.cancel();

											p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1F, 0F);
											p.getWorld().setGameRuleValue("naturalRegenration", "false");

											getRegister().getBorderUtil().createBorder();
											getRegister().getBorderUtil().setMovingListener();

											GState.setCurrentState(GState.PERIOD_OF_PEACE);
											p.setGameMode(GameMode.SURVIVAL);

											getRegister().getGraceTimer().startGraceTimer();

											getRegister().getPlayerUtil().addSurvivor(p);

											getRegister().getTablistUtil().sendTablist();
											getRegister().getScoreboardUtil().setIngameScoreboard(p);
											for (String o : getRegister().getPlayerUtil().getAll()) {
												if (Bukkit.getPlayer(o) == null)
													continue;
												getRegister().getScoreboardUtil()
														.setIngameScoreboard(Bukkit.getPlayer(o));
											}

											if (getUhc().isKits()) {
												if (getRegister().getKitsFile().hasKit(p)) {
													for (ItemStack is : getRegister().getKitsFile().getKitByPlayer(p)
															.getInventory().getContents()) {
														if (is != null) {
															p.getInventory().addItem(is);
														}
													}
												}
											}

										}
									}
								} else {
									Bukkit.broadcastMessage(getUhc().getPrefix()
											+ getRegister().getMessageFile().getColorString("Not enough players"));
									timer.cancel();
									lobby.cancel();
									resetTime();
								}
							}
						}.runTask(getUhc());
					}
				}
			}.runTaskTimer(getUhc(), 0, 20);
		}
	}

	private Location getRandomLocation(Location player, int Xminimum, int Xmaximum, int Zminimum, int Zmaximum) {
		try {
			World world = player.getWorld();
			int randomZ = Zminimum + ((int) (Math.random() * ((Zmaximum - Zminimum) + 1)));
			double x = Double.parseDouble(
					Integer.toString(Xminimum + ((int) (Math.random() * ((Xmaximum - Xminimum) + 1))))) + 0.5d;
			double z = Double.parseDouble(Integer.toString(randomZ)) + 0.5d;
			player.setY(200);
			return new Location(world, x, player.getY(), z);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void changeTime() {
		time = 10;
	}

	public void changeTime(int time) {
		this.time = time;
	}

	private void resetTime() {
		time = getRegister().getTimerFile().getInt("Lobby.lenght");
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.setLevel(time);
		}
	}
}
