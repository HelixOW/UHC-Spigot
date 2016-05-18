package de.alpha.uhc;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.alpha.uhc.Listener.*;
import de.alpha.uhc.aclasses.ATeam;
import de.alpha.uhc.aclasses.AWorld;
import de.alpha.uhc.commands.CoinsCommand;
import de.alpha.uhc.commands.StartCommand;
import de.alpha.uhc.commands.StatsCommand;
import de.alpha.uhc.commands.UHCCommand;
import de.alpha.uhc.files.*;
import de.alpha.uhc.kits.GUI;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.MapReset;
import de.alpha.uhc.utils.Regions;
import de.alpha.uhc.utils.Spectator;
import de.popokaka.alphalibary.file.SimpleFile;
import de.popokaka.alphalibary.mysql.MySQLAPI;
import de.popokaka.alphalibary.mysql.MySQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class Core extends JavaPlugin implements PluginMessageListener {

    private static Core instance;
    private static String prefix;

    private static boolean isMySQLActive;

    //Getting & Setting of the variables
    private static ArrayList<Player> ig;
    private static ArrayList<Player> spectator;

    public static boolean isMySQLActive() {
        return isMySQLActive;
    }

    public static void setMySQLActive(boolean isMySQLActive) {
        Core.isMySQLActive = isMySQLActive;
    }

    private static void setIg(ArrayList<Player> ig) {
        Core.ig = ig;
    }

    private static void setSpectator(ArrayList<Player> spectator) {
        Core.spectator = spectator;
    }

    public static ArrayList<Player> getInGamePlayers() {
        return ig;
    }

    public static void addInGamePlayer(Player p) {
        ig.add(p);
    }

    //Start of a new UHC Round

    public static void removeInGamePlayer(Player p) {
        ig.remove(p);
    }

    public static ArrayList<Player> getSpecs() {
        return spectator;
    }

    public static void addSpec(Player p) {
        spectator.add(p);
    }

    public static void removeSpec(Player p) {
        spectator.remove(p);
    }

    public static Core getInstance() {
        return instance;
    }

    private static void setInstance(Core instance) {
        Core.instance = instance;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        Core.prefix = prefix;
    }

    @Override
    public void onEnable() {
        setInstance(this);
        setIg(new ArrayList<Player>());
        setSpectator(new ArrayList<Player>());

        //Make a sort of Whitelist
        GState.setGameState(GState.RESTART);

        //Load Variables out of Configs
        OptionsFileManager.addOptions();
        OptionsFileManager.loadOptions();

        MessageFileManager.addMessages();
        MessageFileManager.loadMessages();

        SpawnFileManager.getSpawnFile();
        SpawnFileManager.registerRegions();

        TeamFile.addDefaultTeams();
        TeamFile.loadTeams();

        HologramFileManager.getHologramFile().save();

        ScoreboardFile.addScores();
        ScoreboardFile.loadScores();

        DropFile.addDrops();
        DropFile.loadDrops();

        DeathMessageFile.addDeathMessages();
        DeathMessageFile.loadDeathMessages();

        CommandsFile.addCommands();
        CommandsFile.loadCommands();

        //Register Commands & Events
        registerCommands();
        registerEvents();

        //Fill the Kits Inventory
        GUI.fill();

        //Checks if MySQL is active
        if (isMySQLActive()) {
            try {
                //Connect to Database and create the Tables if it's not existing
                MySQLAPI.initMySQLAPI(this);
                createTables();
            } catch (Exception e) {
                //Use the file backup
                isMySQLActive = false;
            }
        }

        //Register Channel for BungeeCord
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (GameEndListener.isBungeeMode()) {
                //Send all Players to Lobby Server
                ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF("Connect");
                out.writeUTF(GameEndListener.getBungeeServer());

                all.sendPluginMessage(Core.getInstance(), "BungeeCord", out.toByteArray());
            } else {
                //Kick the players from the server
                all.kickPlayer(Core.getPrefix() + GameEndListener.getKick());
            }
        }
        //Create schematics folder
        new SimpleFile("plugins/UHC/schematics", "NoUse.yml").save();

        //Set the Countdowntime to the value of the config
        Timer.setCountdownTime();

        //Registers the custom crafting recipes
        registerCrafting();

        //Recreate the World
        AWorld.performReset();

        if (Timer.getPc() <= 1) {
            //Print error message to inform serverowner
            Bukkit.getConsoleSender().sendMessage(prefix + "§cUHC won't end until you reload or leave the Server. If it's only 1 Player.");
        }

        //Print out successfully loaded message
        Bukkit.getConsoleSender().sendMessage(prefix + "§aUHC by AlphaHelix is now enabled!");
    }

    @Override
    public void onDisable() {

        if (isMySQLActive) {
            try {
                //Close the connection to the database
                MySQLAPI.closeMySQLConnection();
            } catch (SQLException e) {
                //Should pretty much never happen (R.I.P English)
                e.printStackTrace();
                Bukkit.getLogger().log(Level.WARNING, "The MySQL Connection wasn't closed.");
            }
        }

        //Reset placed Blocks
        MapReset.restore();
        //Successfull disabled message
        Bukkit.getConsoleSender().sendMessage(prefix + "§cUHC by AlphaHelix is now disabled!");
    }

    @SuppressWarnings("deprecation")
    private void registerCrafting() {

        //create golden Apple recipe
        ShapedRecipe goldenApple = new ShapedRecipe(new ItemStack(Material.GOLDEN_APPLE));

        goldenApple.shape(
                "GGG",
                "GxG",
                "GGG");

        goldenApple.setIngredient('G', Material.GOLD_INGOT);
        goldenApple.setIngredient('x', Material.SKULL_ITEM, 3);
        Bukkit.addRecipe(goldenApple);

    }

    private void registerCommands() {
        getCommand("uhc").setExecutor(new UHCCommand());
        new CoinsCommand(this, new String[]{});
        new StartCommand(this, new String[]{});
        new StatsCommand(this, new String[]{});
    }

    private void createTables() {

        MySQLManager.exCreateTableQry(
                MySQLManager.createColumn("Player", 50),
                MySQLManager.createColumn("UUID", 75),
                MySQLManager.createColumn("Kills", 500),
                MySQLManager.createColumn("Deaths", 500),
                MySQLManager.createColumn("Coins", 500),
                MySQLManager.createColumn("Kits", 500));


    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new InGameListener(), this);
        Bukkit.getPluginManager().registerEvents(new MiningListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new LobbyListener(), this);
        Bukkit.getPluginManager().registerEvents(new CraftListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new SoupListener(), this);
        Bukkit.getPluginManager().registerEvents(new CustomDeathListener(), this);

        Bukkit.getPluginManager().registerEvents(new MapReset(), this);
        Bukkit.getPluginManager().registerEvents(new Spectator(), this);
        Bukkit.getPluginManager().registerEvents(new Regions(), this);
        Bukkit.getPluginManager().registerEvents(new ATeam(), this);

        Bukkit.getPluginManager().registerEvents(new MotdListener(), this);
        Bukkit.getPluginManager().registerEvents(new GameEndListener(), this);
    }

    @Override
    public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("uhc")) {
            ArrayList<String> l = new ArrayList<>();

            if (args.length == 1) {
                l.add("stats");
                l.add("team");
                if (sender.hasPermission("uhc.start")) {
                    l.add("start");
                }
                if (sender.hasPermission("uhc.admin")) {
                    l.add("restart");
                    l.add("reload");
                    l.add("setSpawn");
                    l.add("setLobby");
                    l.add("createLobby");
                    l.add("createWorld");
                    l.add("createHologram");
                    l.add("addKit");
                    l.add("tpToWorld");
                    l.add("createTeamJoiner");
                }
            }
            if (args.length == 2) {
                l.add("team");
                if (sender.hasPermission("uhc.admin")) {
                    l.add("name");
                    l.add("UltraHardCoreWorld");
                    l.add("lowerby");
                }
            }
            if (args.length == 3) {
                if (sender.hasPermission("uhc.admin")) {
                    l.add("deep");
                    l.add("block");
                }
            }
            if (args.length == 4) {
                if (sender.hasPermission("uhc.admin")) {
                    l.add("slot");
                }
            }
            if (args.length == 5) {
                if (sender.hasPermission("uhc.admin")) {
                    l.add("price");
                }
            }
            if (args.length == 6) {
                if (sender.hasPermission("uhc.admin")) {
                    l.add("lore");
                }
            }

            Collections.sort(l);

            return l;
        }
        return null;
    }

}
