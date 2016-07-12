package de.alpha.uhc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.popokaka.alphalibary.file.SimpleFile;
import de.popokaka.alphalibary.mysql.MySQLAPI;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class Core extends JavaPlugin implements PluginMessageListener {

    private static Core instance;
    private Registery reg;
    private String prefix;

    private boolean isMySQLActive;

    //Getting & Setting of the variables
    private ArrayList<Player> ig;
    private ArrayList<Player> spectator;

    public boolean isMySQLActive() {
        return this.isMySQLActive;
    }

    public void setMySQLActive(boolean isMySQLActive) {
    	this.isMySQLActive = isMySQLActive;
    }

    private void setIg(ArrayList<Player> ig) {
    	this.ig = ig;
    }

    private void setSpectator(ArrayList<Player> spectator) {
    	this.spectator = spectator;
    }

    public ArrayList<Player> getInGamePlayers() {
        return this.ig;
    }

    public void addInGamePlayer(Player p) {
    	this.ig.add(p);
    }

    //Start of a new UHC Round

    public void removeInGamePlayer(Player p) {
    	this.ig.remove(p);
    }

    public ArrayList<Player> getSpecs() {
        return this.spectator;
    }

    public void addSpec(Player p) {
        this.spectator.add(p);
    }

    public void removeSpec(Player p) {
    	this.spectator.remove(p);
    }

    private void setInstance(Core instance) {
        Core.instance = instance;
    }
    
	private void setReg(Registery reg) {
		this.reg = reg;
	}

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void onEnable() {
        this.setInstance(this);
        this.setIg(new ArrayList<Player>());
        this.setSpectator(new ArrayList<Player>());
        this.setReg(new Registery(getInstance()));
        
        reg.registerAll();

        //Make a sort of Whitelist
        GState.setGameState(GState.RESTART);

        //Load Variables out of Configs
        getRegistery().getOptionsFile().addOptions();
        getRegistery().getOptionsFile().loadOptions();

        getRegistery().getMessageFile().addMessages();
        getRegistery().getMessageFile().loadMessages();

        getRegistery().getSpawnFileManager().getSpawnFile();
        getRegistery().getSpawnFileManager().registerRegions();

        getRegistery().getTeamFile().addDefaultTeams();
        getRegistery().getTeamFile().loadTeams();

        getRegistery().getHologramFile().getHologramFile().save();

        getRegistery().getScoreboardFile().addScores();
        getRegistery().getScoreboardFile().loadScores();

        getRegistery().getDropFile().addDrops();
        getRegistery().getDropFile().loadDrops();

        getRegistery().getDeathMessagesFile().addDeathMessages();
        getRegistery().getDeathMessagesFile().loadDeathMessages();

        getRegistery().getCommandsFile().addCommands();
        getRegistery().getCommandsFile().loadCommands();
        
        getRegistery().getRankingFile().addLines();
        getRegistery().getRankingFile().loadLines();
        getRegistery().getRankingFile().loadSigns();
        
        getRegistery().getArmorstandFile().getASFile().save();

        //Fill the Kits Inventory
        getRegistery().getGui().fill();
        
        //Checks if MySQL is active
        if (isMySQLActive()) {
        	getRegistery().getStats().setMySQL(true);
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
            if (getRegistery().getGameEndListener().isBungeeMode()) {
                //Send all Players to Lobby Server
                ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF("Connect");
                out.writeUTF(getRegistery().getGameEndListener().getBungeeServer());

                all.sendPluginMessage(Core.getInstance(), "BungeeCord", out.toByteArray());
            } else {
                //Kick the players from the server
                all.kickPlayer(this.getPrefix() + getRegistery().getGameEndListener().getKick());
            }
        }
        //Create schematics folder
        new SimpleFile("plugins/UHC/schematics", "NoUse.yml").save();

        //Set the Countdowntime to the value of the config
        getRegistery().getTimer().setCountdownTime();

        //Registers the custom crafting recipes
        registerCrafting();

        //Recreate the World
        getRegistery().getAWorld().performReset();
        
        //Crate Ranking Wall
        getRegistery().getARanking().update();

        if (getRegistery().getTimer().getPc() <= 1) {
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
        getRegistery().getMapReset().restore();
        //Successfull disabled message
        Bukkit.getConsoleSender().sendMessage(prefix + "§cUHC by AlphaHelix is now disabled!");
    }

    private void registerCrafting() {

        //create golden Apple recipe
        ShapedRecipe goldenApple = new ShapedRecipe(new ItemStack(Material.GOLDEN_APPLE));
        MaterialData data = new MaterialData(Material.SKULL_ITEM);
        
        data.toItemStack(1).setDurability((short) 3);
        
        goldenApple.shape(
                "GGG",
                "GxG",
                "GGG");

        goldenApple.setIngredient('G', Material.GOLD_INGOT);
        goldenApple.setIngredient('x', Material.SKULL_ITEM);
        goldenApple.setIngredient('x', data);
        Bukkit.addRecipe(goldenApple);

    }

    private void createTables() {
    	
        MySQLManager.exCreateTableQry(
                MySQLManager.createColumn("Player", 50),
                MySQLManager.createColumn("UUID", 75),
                MySQLManager.createColumn("Kills", 500),
                MySQLManager.createColumn("Deaths", 500),
                MySQLManager.createColumn("Coins", 500),
                MySQLManager.createColumn("Kits", 500),
                MySQLManager.createColumn("Count", 500),
                MySQLManager.createColumn("Points", 500));
    }

    @Override
    public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {}
    
    
    public static Core getInstance() {
        return instance;
    }

	public Registery getRegistery() {
		return reg;
	}
}
