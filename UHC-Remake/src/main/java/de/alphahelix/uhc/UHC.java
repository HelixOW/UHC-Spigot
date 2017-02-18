package de.alphahelix.uhc;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.alphahelix.alphalibary.fakeapi.FakeAPI;
import de.alphahelix.alphalibary.mysql.Database;
import de.alphahelix.alphalibary.mysql.MySQLAPI;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
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

public class UHC extends FakeAPI implements PluginMessageListener {

    private static UHC instance;

    private String consolePrefix, lobbyServer, restartMessage, trackerName;
    private boolean mySQLMode, debug, bunggeMode, soup, scenarios, scenarioVoting, statusMOTD, kits;
    private boolean teams, tracker, lobbyAsSchematic, crates, lobby, pregen;
    private int spawnradius;
    private Logger log;

    public static UHC getInstance() {
        return instance;
    }

    private void setInstance(UHC plugin) {
        instance = plugin;
    }

    @Override
    public void onLoad() {
        for (World world : Bukkit.getWorlds())
            world.setAutoSave(false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.setInstance(this);
        this.setLog();
        this.setConsolePrefix("[" + this.getName() + "] ");
        this.setRestartMessage("");

        UHCRegister.registerAll();

        GState.setCurrentState(GState.END);

        if (isMySQLMode()) {
            try {
                MySQLAPI.initMySQLAPI(getPluginInstance());
                Database.exCreateTableQry(
                        "UHC",
                        Database.createColumn("Player"),
                        Database.createColumn("uuid"),
                        Database.createColumn("Games"),
                        Database.createColumn("Kills"),
                        Database.createColumn("Deaths"),
                        Database.createColumn("Coins"),
                        Database.createColumn("Points"),
                        Database.createColumn("Wins"),
                        Database.createColumn("Kits"),
                        Database.createColumn("Achievements"),
                        Database.createColumn("Crates"));
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

        new File("plugins/UHC-Remake/schematics").mkdirs();

        registerCrafting();

        UHCRegister.getRankingUtil().updateArmorStands();

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
        UHCFileRegister.getCraftingFile().registerAllCrafting();
        if (isDebug())
            log.log(Level.INFO, getConsolePrefix() + "registered crafting recipes.");

        new BukkitRunnable() {
            public void run() {
                if (UHCFileRegister.getScenarioFile().isEnabled(Scenarios.getRawScenarioName(Scenarios.MOUNTAINEERING))) {
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
