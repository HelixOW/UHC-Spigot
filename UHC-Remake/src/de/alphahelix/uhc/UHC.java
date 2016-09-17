package de.alphahelix.uhc;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.mysql.MySQLAPI;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class UHC extends JavaPlugin implements PluginMessageListener {
	
	private static UHC instance;
	
	private Registery register;
	private String prefix;
	private String consolePrefix;
	private String lobbyServer;
	private String restartMessage;
	private String trackerName;
	private boolean MySQLMode;
	private boolean debug;
	private boolean bunggeMode;
	private boolean soup;
	private boolean scenarios;
	private boolean statusMOTD;
	private boolean kits;
	private boolean teams;
	private boolean tracker;
	private int spawnradius;
	private Logger log;
	
	/*
	 * - Stats
	 * - Ranking
	 * - PlayerFile
	 * - Kits (No Clickable things)
	 * - Stats Command
	 */
	
	
	@Override
	public void onEnable() {
		this.setInstance(this);
		this.setRegister(new Registery(getInstance()));
		this.setLog();
		this.setConsolePrefix("["+this.getName()+"] ");
		this.setRestartMessage("");
		
		getRegister().registerAll();
		
		GState.setCurrentState(GState.END);
		
		if(isMySQLMode()) {
			try {
				MySQLAPI.initMySQLAPI(getInstance());
				MySQLManager.exCreateTableQry(
						MySQLManager.createColumn("Player", 50),
						MySQLManager.createColumn("UUID", 75),
						MySQLManager.createColumn("Kills", 500),
						MySQLManager.createColumn("Deaths", 500),
						MySQLManager.createColumn("Coins", 500),
						MySQLManager.createColumn("Kits", 500),
						MySQLManager.createColumn("Count", 500),
						MySQLManager.createColumn("Points", 500));
			} catch (Exception e) {
				if(isDebug()) log.log(Level.WARNING, getConsolePrefix() + "Wasn't able to connect to a database. Using file backend now.", e.getMessage());
				setMySQLMode(false);
			}
		}
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(getInstance(), "BungeeCord");
		
		if(isDebug()) log.log(Level.INFO, getConsolePrefix() + "Successfully kicked "+Bukkit.getOnlinePlayers().size()+" Players.");
		
		for(Player toKick : Bukkit.getOnlinePlayers()) {
			if(isBunggeMode()) {
				ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF("Connect");
                out.writeUTF(getLobbyServer());

                toKick.sendPluginMessage(getInstance(), "BungeeCord", out.toByteArray());
			} else {
				toKick.kickPlayer(getPrefix() + getRestartMessage());
			}
		}
		
		new File("plugins/UHC/schematics").mkdirs();
		new File("plugins/UHC/scenarios").mkdirs();
		
		getRegister().getLocationsFile().initalizeLobbyAndArena();
		
		//TODO: Set Countdowntime to configvalue
		
		registerCrafting();
		
		//TODO: Create Ranking Wall
		
		log.log(Level.INFO, getConsolePrefix() + "UHC by AlphaHelix successfully loaded and enabled.");
		GState.setCurrentState(GState.LOBBY);
		
		final long lastModified = getFile().lastModified();

		new BukkitRunnable() {
		    public void run() {
		        if (getFile().lastModified() > lastModified) {
		            cancel();
		            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload");
		        }
		    }
		}.runTaskTimer(this, 0, 20);
	}
	
	@Override
	public void onDisable() {
		getRegister().getStatsUtil().pushCacheToBackUp();
		if(isMySQLMode()) {
			try {
				MySQLAPI.closeMySQLConnection();
			} catch (SQLException e) {
				log.log(Level.WARNING, getConsolePrefix() + "The MySQL connection wasn't closed correctly.", e.getMessage());
			}
		}
		log.log(Level.INFO, getConsolePrefix() + "UHC by AlphaHelix successfully unloaded and disabled.");
	}
	
	private void registerCrafting() {
		ItemBuilder gapple = new ItemBuilder(Material.GOLDEN_APPLE);
		
		gapple.setGlow();
		
		ShapedRecipe gappleRecipe = new ShapedRecipe(gapple.build());
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM);
		
		skull.setDurability((short) 3);
		
		gappleRecipe.shape("GGG", "GSG", "GGG");
		
		gappleRecipe.setIngredient('G', Material.GOLD_INGOT);
		gappleRecipe.setIngredient('S', Material.SKULL_ITEM);
		gappleRecipe.setIngredient('S', skull.getData());
		
		Bukkit.addRecipe(gappleRecipe);
		if(isDebug()) log.log(Level.INFO, getConsolePrefix() + "registered crafting recipe for Goldenhead.");
	}
	
	@Override
    public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {}

	public static UHC getInstance() {
		return instance;
	}

	private void setInstance(UHC plugin) {
		instance = plugin;
	}

	public Registery getRegister() {
		return register;
	}

	private void setRegister(Registery register) {
		this.register = register;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isMySQLMode() {
		return MySQLMode;
	}

	public void setMySQLMode(boolean mySQL) {
		MySQLMode = mySQL;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public Logger getLog() {
		return log;
	}

	private void setLog() {
		this.log = Bukkit.getLogger();
	}

	public boolean isBunggeMode() {
		return bunggeMode;
	}

	public void setBunggeMode(boolean bunggeMode) {
		this.bunggeMode = bunggeMode;
	}

	public String getLobbyServer() {
		return lobbyServer;
	}

	public void setLobbyServer(String lobbyServer) {
		this.lobbyServer = lobbyServer;
	}

	public String getRestartMessage() {
		return restartMessage;
	}

	public void setRestartMessage(String restartMessage) {
		this.restartMessage = restartMessage;
	}

	public String getConsolePrefix() {
		return consolePrefix;
	}

	private void setConsolePrefix(String consolePrefix) {
		this.consolePrefix = consolePrefix;
	}

	public boolean isSoup() {
		return soup;
	}

	public void setSoup(boolean soup) {
		this.soup = soup;
	}

	public boolean isScenarios() {
		return scenarios;
	}

	public void setScenarios(boolean scenarios) {
		this.scenarios = scenarios;
	}

	public boolean isStatusMOTD() {
		return statusMOTD;
	}

	public void setStatusMOTD(boolean statusMOTD) {
		this.statusMOTD = statusMOTD;
	}

	public int getSpawnradius() {
		return spawnradius;
	}

	public void setSpawnradius(int spawnradius) {
		this.spawnradius = spawnradius;
	}

	public boolean isKits() {
		return kits;
	}

	public void setKits(boolean kits) {
		this.kits = kits;
	}

	public boolean isTeams() {
		return teams;
	}

	public void setTeams(boolean teams) {
		this.teams = teams;
	}

	public boolean isTracker() {
		return tracker;
	}

	public void setTracker(boolean tracker) {
		this.tracker = tracker;
	}

	public String getTrackerName() {
		return trackerName;
	}

	public void setTrackerName(String trackerName) {
		this.trackerName = trackerName;
	}
}
