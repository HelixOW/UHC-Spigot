package de.alpha.uhc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.alpha.uhc.Listener.ChatListener;
import de.alpha.uhc.Listener.CraftListener;
import de.alpha.uhc.Listener.CustomDeathListener;
import de.alpha.uhc.Listener.DeathListener;
import de.alpha.uhc.Listener.GameEndListener;
import de.alpha.uhc.Listener.InGameListener;
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.Listener.MiningListener;
import de.alpha.uhc.Listener.MotdListener;
import de.alpha.uhc.Listener.PlayerJoinListener;
import de.alpha.uhc.Listener.SoupListener;
import de.alpha.uhc.aclasses.ATeam;
import de.alpha.uhc.aclasses.AWorld;
import de.alpha.uhc.commands.UHCCommand;
import de.alpha.uhc.files.DeathMessageFile;
import de.alpha.uhc.files.DropFile;
import de.alpha.uhc.files.HologramFileManager;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.OptionsFileManager;
import de.alpha.uhc.files.ScoreboardFile;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.files.TeamFile;
import de.alpha.uhc.kits.GUI;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.MapReset;
import de.alpha.uhc.utils.Regions;
import de.alpha.uhc.utils.Spectator;
import de.popokaka.alphalibary.file.SimpleFile;
import de.popokaka.alphalibary.mysql.MySQLAPI;
import de.popokaka.alphalibary.mysql.MySQLDataType;
import de.popokaka.alphalibary.mysql.MySQLManager;

public class Core extends JavaPlugin implements PluginMessageListener{
	
	private static Core instance;
	private static String prefix;
	
	private static boolean isMySQLActive;
	
	public static  boolean isMySQLActive() {
		return isMySQLActive;
	}

	public static  void setMySQLActive(boolean isMySQLActive) {
		Core.isMySQLActive = isMySQLActive;
	}

	public static  ArrayList<Player> getIg() {
		return ig;
	}

	public static  void setIg(ArrayList<Player> ig) {
		Core.ig = ig;
	}

	public static  ArrayList<Player> getSpectator() {
		return spectator;
	}

	public static  void setSpectator(ArrayList<Player> spectator) {
		Core.spectator = spectator;
	}

	public static  void setInstance(Core instance) {
		Core.instance = instance;
	}

	public static  void setPrefix(String prefix) {
		Core.prefix = prefix;
	}

	private static ArrayList<Player> ig;
	private static ArrayList<Player> spectator;
	
	@Override
	public void onEnable() {
		instance = this;
		ig = new ArrayList<Player>();
		spectator = new ArrayList<Player>();
		
		GState.setGameState(GState.RESTART);
			
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
		
		registerCommands();
		registerEvents();
		
		GUI.fill();
		
		if(isMySQLActive == true) {
			try {
				MySQLAPI.initMySQLAPI(this);
				createTables();
			} catch(Exception e) {
				isMySQLActive = false;                                                 
			}
		}
		
		if(Bukkit.getOnlinePlayers().size() != 0) {
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.setGameMode(GameMode.SURVIVAL);
			}
		}
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(GameEndListener.isBungeeMode() == true) {
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				
				out.writeUTF("Connect");
				out.writeUTF(GameEndListener.getBungeeServer());
				
				all.sendPluginMessage(Core.getInstance(), "BungeeCord", out.toByteArray());
			} else {
				all.kickPlayer(Core.getPrefix() + GameEndListener.getKick());
			}
		}
		new SimpleFile("plugins/UHC/schematics", "NoUse.yml").save();
		
		Timer.setCountdownTime();
		
		registerCrafting();
		
		AWorld.performReset();
		
		if(Timer.getPc() <= 1) {
			Bukkit.getConsoleSender().sendMessage(prefix + "§cUHC won't end until you reload or leave the Server. If it's only 1 Player.");
		}
		
		Bukkit.getConsoleSender().sendMessage(prefix + "§aUHC by AlphaHelix is now enabled!");
		Bukkit.setWhitelist(false);
	}
	
	@Override
	public void onDisable() {
		
		if(isMySQLActive == true) {
			try {
				MySQLAPI.closeMySQLConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				Bukkit.getLogger().log(Level.WARNING, "The MySQL Connection wasn't closed.");
			}
		}
		
		MapReset.restore();
		Bukkit.getConsoleSender().sendMessage(prefix + "§cUHC by AlphaHelix is now disabled!");
	}
	
	@SuppressWarnings("deprecation")
	private void registerCrafting() {
		
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
	}
	
	private void createTables() {
		
		MySQLManager.exCreateTableQry("UHC",
				MySQLManager.createColumn("Player", MySQLDataType.VARCHAR, 50),
				MySQLManager.createColumn("UUID", MySQLDataType.VARCHAR, 75),
				MySQLManager.createColumn("Kills", MySQLDataType.VARCHAR, 500),
				MySQLManager.createColumn("Deaths", MySQLDataType.VARCHAR, 500),
				MySQLManager.createColumn("Coins", MySQLDataType.VARCHAR, 500),
				MySQLManager.createColumn("Kits", MySQLDataType.VARCHAR, 500));
		
		
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
	
	public static ArrayList<Player> getInGamePlayers() {
		return ig;
	}
	
	public static void addInGamePlayer(Player p) {
		ig.add(p);
	}
	
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
	
	public static String getPrefix() {
		return prefix;
	}
	
	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		if(!(arg0.equalsIgnoreCase("BungeeCord"))) {
			return;
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("uhc")) {
			ArrayList<String> l = new ArrayList<>();
			
			if(args.length == 1) {
				l.add("stats");
				l.add("team");
				if(sender.hasPermission("uhc.start")) {
					l.add("start");
				}
				if(sender.hasPermission("uhc.admin")) {
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
			if(args.length == 2) {
				l.add("team");
				if(sender.hasPermission("uhc.admin")) {
					l.add("name");
					l.add("UltraHardCoreWorld");
					l.add("lowerby");
				}
			}
			if(args.length == 3) {
				if(sender.hasPermission("uhc.admin")) {
					l.add("deep");
					l.add("block");
				}
			}
			if(args.length == 4) {
				if(sender.hasPermission("uhc.admin")) {
					l.add("slot");
				}
			}
			if(args.length == 5) {
				if(sender.hasPermission("uhc.admin")) {
					l.add("price");
				}
			}
			if(args.length == 6) {
				if(sender.hasPermission("uhc.admin")) {
					l.add("lore");
				}
			}
			
			Collections.sort(l);
			
			return l;
		}
		return null;
	}
	
}
