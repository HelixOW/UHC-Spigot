/*
 * Copyright (C) <2017>  <AlphaHelixDev>
 *
 *       This program is free software: you can redistribute it under the
 *       terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.alphahelix.alphalibary.mysql;

import de.alphahelix.alphalibary.AlphaPlugin;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Needs a rework
 */
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

    public static String getMySQLFilePath() {
        return "./plugins/UHC-Remake/";
    }

    public static boolean isConnected() {
        return con != null;
    }

    public static Plugin getPlugin() {
        return MySQLAPI.plugin;
    }

    public static void initMySQLAPI(AlphaPlugin plugin) throws SQLException {
        MySQLAPI.plugin = plugin;
        MySQLFileManager fm = new MySQLFileManager(plugin);

        fm.setStandardMySQL();
        fm.readMySQL();

        if (!isConnected()) {
            con = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
        }
    }

    public static void closeMySQLConnection() throws SQLException {
        if (isConnected()) {
            con.close();
        }
    }
}
