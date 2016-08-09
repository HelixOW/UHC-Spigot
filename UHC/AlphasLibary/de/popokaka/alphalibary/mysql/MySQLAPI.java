package de.popokaka.alphalibary.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.alpha.uhc.Core;

public class MySQLAPI {
	private static String username;
	private static String password;
	private static String database;
	private static String host;
	private static String port;
	private static Connection con;
	private static Plugin plugin;

	public static void setMySQLConnection(String username, String pass, String database, String host, String port) {
		MySQLAPI.username = username;
		MySQLAPI.password = pass;
		MySQLAPI.database = database;
		MySQLAPI.host = host;
		MySQLAPI.port = port;
	}

	public static Connection getMySQLConnection() {
		return con;
	}

	public static int getCountNumber() {
		if(Core.getInstance().isMySQLActive()) {
			if(!isConnected()) return 0;
			try {
				String qry = "SELECT COUNT FROM uhc";
				PreparedStatement prepstate = getMySQLConnection().prepareStatement(qry);
				ResultSet rs = prepstate.executeQuery();
	
				int in = 0;
	
				while (rs.next()) {
					in = Integer.parseInt(rs.getString("COUNT")) + 1;
				}
				return in;
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return Core.getInstance().getRegistery().getPlayerFile().getRows();
		}
	}

	public static String getMySQLFilePath() {
		return "./plugins/MySQLAPI/";
	}

	public static boolean isConnected() {
		return con != null;
	}

	public static Plugin getPlugin() {
		return MySQLAPI.plugin;
	}

	public static void initMySQLAPI(Plugin plugin) {
		MySQLAPI.plugin = plugin;

		MySQLFileManager.setStandardMySQL();
		MySQLFileManager.readMySQL();

		if (!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username,
						password);
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage(Core.getInstance().getPrefix() + "§cCan't connect to database!");
				Bukkit.getConsoleSender().sendMessage(Core.getInstance().getPrefix() + "§cPlease check your mysql.yml file at plugins/MySQLAPI!");
			}
		}
	}

	public static void closeMySQLConnection() throws SQLException {

		if (isConnected()) {
			con.close();
		}
	}
}
