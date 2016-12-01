package de.alphahelix.uhc;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.alphahelix.alphalibary.AlphaPlugin;
import de.alphahelix.alphalibary.mysql.MySQLAPI;
import de.alphahelix.alphalibary.mysql.MySQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UHC extends AlphaPlugin implements PluginMessageListener {

    private static UHC instance;

    private Registery register;
    private String consolePrefix;
    private String lobbyServer;
    private String restartMessage;
    private String trackerName;
    private boolean mySQLMode;
    private boolean debug;
    private boolean bunggeMode;
    private boolean soup;
    private boolean scenarios, scenarioVoting;
    private boolean statusMOTD;
    private boolean kits;
    private boolean teams;
    private boolean tracker;
    private boolean lobbyAsSchematic;
    private boolean crates;
    private boolean lobby;
    private boolean pregen;
    private int spawnradius;
    private Logger log;

    public static UHC getInstance() {
        return instance;
    }

    private void setInstance(UHC plugin) {
        instance = plugin;
    }

    @Override
    public void onEnable() {
        this.setInstance(this);
        this.setRegister(new Registery(getInstance()));
        this.setLog();
        this.setConsolePrefix("[" + this.getName() + "] ");
        this.setRestartMessage("");

        getRegister().registerAll();

        GState.setCurrentState(GState.END);

        if (isMySQLMode()) {
            try {
                MySQLAPI.initMySQLAPI(getPluginInstance());
                MySQLManager.exCreateTableQry(
                        MySQLManager.createColumn("Player", 50),
                        MySQLManager.createColumn("UUID", 75),
                        MySQLManager.createColumn("Kills", 500),
                        MySQLManager.createColumn("Deaths", 500),
                        MySQLManager.createColumn("Coins", 500),
                        MySQLManager.createColumn("Points", 500),
                        MySQLManager.createColumn("Wins", 500),
                        MySQLManager.createColumn("Count", 500),
                        MySQLManager.createColumn("Kits", 500),
                        MySQLManager.createColumn("Achievements", 500),
                        MySQLManager.createColumn("NORMALCrates", 500),
                        MySQLManager.createColumn("UNCOMMONCrates", 500),
                        MySQLManager.createColumn("RARECrates", 500),
                        MySQLManager.createColumn("SUPERRARECrates", 500),
                        MySQLManager.createColumn("EPICCrates", 500),
                        MySQLManager.createColumn("LEGENDARYCrates", 500)
                );
            } catch (Exception e) {
                if (isDebug())
                    log.log(Level.WARNING,
                            getConsolePrefix() + "Wasn't able to connect to a database. Using file backend now.");
                e.printStackTrace();
                setMySQLMode(false);
            }
        }

        Bukkit.getMessenger().registerOutgoingPluginChannel(getInstance(), "BungeeCord");

        if (isDebug())
            log.log(Level.INFO,
                    getConsolePrefix() + "Successfully kicked " + Bukkit.getOnlinePlayers().size() + " Players.");

        for (Player toKick : Bukkit.getOnlinePlayers()) {
            if (isBunggeMode()) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF("Connect");
                out.writeUTF(getLobbyServer());

                toKick.sendPluginMessage(getInstance(), "BungeeCord", out.toByteArray());
            } else {
                toKick.kickPlayer(getPrefix() + getRestartMessage());
            }
        }

        new File("plugins/UHC/schematics").mkdirs();

        registerCrafting();

        getRegister().getRankingUtil().updateArmorStands();

        log.log(Level.INFO, getConsolePrefix() + "UHC by AlphaHelix successfully loaded and enabled.");
    }

    @Override
    public void onDisable() {
        if (isMySQLMode()) {
            try {
                MySQLAPI.closeMySQLConnection();
            } catch (SQLException e) {
                log.log(Level.WARNING, getConsolePrefix() + "The MySQL connection wasn't closed correctly.",
                        e.getMessage());
            }
        }
        log.log(Level.INFO, getConsolePrefix() + "UHC by AlphaHelix successfully unloaded and disabled.");
    }

    private void registerCrafting() {
        getRegister().getCraftingFile().registerAllCrafting();
        if (isDebug())
            log.log(Level.INFO, getConsolePrefix() + "registered crafting recipes.");

        new BukkitRunnable() {
            public void run() {
                if (getRegister().getScenarioFile().isEnabled(Scenarios.getRawScenarioName(Scenarios.MOUNTAINEERING))) {
                    Iterator<Recipe> recipes = Bukkit.recipeIterator();
                    Recipe r;
                    while (recipes.hasNext()) {
                        r = recipes.next();

                        if (r != null && r.getResult().getType().equals(Material.ENCHANTMENT_TABLE))
                            recipes.remove();
                    }

                    ShapedRecipe sr = new ShapedRecipe(new ItemStack(Material.ENCHANTMENT_TABLE));

                    sr.shape("xbx", "eoe", "ooo");

                    sr.setIngredient('b', Material.BOOK);
                    sr.setIngredient('e', Material.EMERALD);
                    sr.setIngredient('o', Material.OBSIDIAN);

                    Bukkit.addRecipe(sr);
                }
            }
        }.runTaskLater(getInstance(), 80);
    }

    @Override
    public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
    }

    public Registery getRegister() {
        return register;
    }

    private void setRegister(Registery register) {
        this.register = register;
    }

    public boolean isMySQLMode() {
        return mySQLMode;
    }

    public void setMySQLMode(boolean mySQL) {
        mySQLMode = mySQL;
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

    public boolean isLobbyAsSchematic() {
        return lobbyAsSchematic;
    }

    public void setLobbyAsSchematic(boolean lobbyAsSchematic) {
        this.lobbyAsSchematic = lobbyAsSchematic;
    }

    public boolean isCrates() {
        return crates;
    }

    public void setCrates(boolean crates) {
        this.crates = crates;
    }

    public boolean isLobby() {
        return lobby;
    }

    public void setLobby(boolean lobby) {
        this.lobby = lobby;
    }

    public boolean isPregen() {
        return pregen;
    }

    public void setPregen(boolean pregen) {
        this.pregen = pregen;
    }

    public boolean isScenarioVoting() {
        return scenarioVoting;
    }

    public void setScenarioVoting(boolean scenarioVoting) {
        this.scenarioVoting = scenarioVoting;
    }
}
