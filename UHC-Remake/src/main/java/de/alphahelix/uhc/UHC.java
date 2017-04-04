package de.alphahelix.uhc;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.alphahelix.alphaapi.AlphaAPI;
import de.alphahelix.alphaapi.mysql.MySQLAPI;
import de.alphahelix.alphaapi.mysql.MySQLDatabase;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

public class UHC extends AlphaAPI implements PluginMessageListener {

    private static MySQLDatabase database;
    private static UHC instance;

    private static String consolePrefix, prefix, lobbyServer, restartMessage, trackerName;
    private static boolean mySQLMode, debug, bunggeMode, scenarios, scenarioVoting, statusMOTD, kits;
    private static boolean teams, tracker, lobbyAsSchematic, crates, lobby, pregen;
    private static int spawnradius;
    private static Logger log;

    public static UHC getInstance() {
        return instance;
    }

    private void setInstance(UHC plugin) {
        instance = plugin;
    }

    public static MySQLDatabase getDB() {
        return database;
    }

    public static Location getRandomLocation(Location player, int Xminimum, int Xmaximum, int Zminimum, int Zmaximum) {
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

    public static String getConsolePrefix() {
        return consolePrefix;
    }

    public static void setConsolePrefix(String consolePrefix) {
        UHC.consolePrefix = consolePrefix;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        UHC.prefix = prefix;
    }

    public static String getLobbyServer() {
        return lobbyServer;
    }

    public static void setLobbyServer(String lobbyServer) {
        UHC.lobbyServer = lobbyServer;
    }

    public static String getRestartMessage() {
        return restartMessage;
    }

    public static void setRestartMessage(String restartMessage) {
        UHC.restartMessage = restartMessage;
    }

    public static String getTrackerName() {
        return trackerName;
    }

    public static void setTrackerName(String trackerName) {
        UHC.trackerName = trackerName;
    }

    public static boolean isMySQLMode() {
        return mySQLMode;
    }

    public static void setMySQLMode(boolean mySQLMode) {
        UHC.mySQLMode = mySQLMode;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        UHC.debug = debug;
    }

    public static boolean isBunggeMode() {
        return bunggeMode;
    }

    public static void setBunggeMode(boolean bunggeMode) {
        UHC.bunggeMode = bunggeMode;
    }

    public static boolean isScenarios() {
        return scenarios;
    }

    public static void setScenarios(boolean scenarios) {
        UHC.scenarios = scenarios;
    }

    public static boolean isScenarioVoting() {
        return scenarioVoting;
    }

    public static void setScenarioVoting(boolean scenarioVoting) {
        UHC.scenarioVoting = scenarioVoting;
    }

    public static boolean isStatusMOTD() {
        return statusMOTD;
    }

    public static void setStatusMOTD(boolean statusMOTD) {
        UHC.statusMOTD = statusMOTD;
    }

    public static boolean isKits() {
        return kits;
    }

    public static void setKits(boolean kits) {
        UHC.kits = kits;
    }

    public static boolean isTeams() {
        return teams;
    }

    public static void setTeams(boolean teams) {
        UHC.teams = teams;
    }

    public static boolean isTracker() {
        return tracker;
    }

    public static void setTracker(boolean tracker) {
        UHC.tracker = tracker;
    }

    public static boolean isLobbyAsSchematic() {
        return lobbyAsSchematic;
    }

    public static void setLobbyAsSchematic(boolean lobbyAsSchematic) {
        UHC.lobbyAsSchematic = lobbyAsSchematic;
    }

    public static boolean isCrates() {
        return crates;
    }

    public static void setCrates(boolean crates) {
        UHC.crates = crates;
    }

    public static boolean isLobby() {
        return lobby;
    }

    public static void setLobby(boolean lobby) {
        UHC.lobby = lobby;
    }

    public static boolean isPregen() {
        return pregen;
    }

    public static void setPregen(boolean pregen) {
        UHC.pregen = pregen;
    }

    public static int getSpawnradius() {
        return spawnradius;
    }

    public static void setSpawnradius(int spawnradius) {
        UHC.spawnradius = spawnradius;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        UHC.log = log;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        for (World world : Bukkit.getWorlds())
            world.setAutoSave(false);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.setInstance(this);
        setLog(Bukkit.getLogger());
        setConsolePrefix("[" + this.getName() + "] ");

        UHCRegister.registerAll();

        GState.setCurrentState(GState.END);

        if (isMySQLMode()) {
            for (MySQLAPI api : MySQLAPI.getMysqlDBs()) {
                api.initMySQLAPI();
                database = new MySQLDatabase("UHC", api.getDatabase());
            }

            database.create(
                    database.createColumn("Player", MySQLAPI.MySQLDataType.VARCHAR, 50),
                    database.createColumn("UUID", MySQLAPI.MySQLDataType.VARCHAR, 50),
                    database.createColumn("Infos", MySQLAPI.MySQLDataType.TEXT, 5000000));
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

        log.log(Level.INFO, getConsolePrefix() + "UHC by AlphaHelix successfully loaded and enabled.");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (isMySQLMode()) {
            try {
                for (MySQLAPI api : MySQLAPI.getMysqlDBs()) {
                    api.closeMySQLConnection();
                }
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
        }.runTaskLaterAsynchronously(getInstance(), 2);
    }

    @Override
    public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
    }
}
