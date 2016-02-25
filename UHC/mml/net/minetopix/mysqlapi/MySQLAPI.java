package net.minetopix.mysqlapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MySQLAPI {
	private static String username;
	private static String password;
	private static String database;
	private static String host;
	private static String port;
	private static Connection con;
	private static Plugin plugin;
	private static String mySQLFilePath = "./plugins/MySQLAPI/";

	public static void setMySQLConnection(String username, String pass,
			String database, String host, String port) {
		MySQLAPI.username = username;
		MySQLAPI.password = pass;
		MySQLAPI.database = database;
		MySQLAPI.host = host;
		MySQLAPI.port = port;
	}
	
	public static ResultSet query(String qry)
    {
        ResultSet rs = null;
        try
        {
            Statement st = con.createStatement();
            rs = st.executeQuery(qry);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return rs;
    }

	public static Connection getMySQLConnection() {
		return con;
	}

	public static String getMySQLFilePath() {
		return mySQLFilePath;
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
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":"
						+ port + "/" + database, username, password);
				Bukkit.getConsoleSender().sendMessage("MySQL Verbindung aufgebaut!");
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage(
						"Fehler bei Anmeldung in Database!");
				e.printStackTrace();
			}
		}
	}

	public static void closeMySQLConnection() throws SQLException {

		if (isConnected()) {
			con.close();
			Bukkit.getConsoleSender().sendMessage("MySQL Verbindung geschlossen!");
		}
	}
}
