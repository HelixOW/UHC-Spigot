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

import de.alphahelix.alphalibary.UUID.UUIDFetcher;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Needs a rework
 */
public class Database {

    private static final HashMap<String, String> TABLEINFO = new HashMap<>();
    private static final ArrayList<String> TABLENAMES = new ArrayList<>();

    public static boolean containsPlayer(String tableName, Player p) {
        return getResult(tableName, "UUID", UUIDFetcher.getUUID(p).toString(), "UUID") != null;
    }

    public static boolean containsPlayer(String tableName, OfflinePlayer p) {
        return getResult(tableName, "UUID", UUIDFetcher.getUUID(p).toString(), "UUID") != null;
    }

    public static void exCreateTableQry(String tableName, String... columns) {
        String tableinfo;
        if (columns.length > 1) {
            StringBuilder builder = new StringBuilder();
            for (String str : columns) {
                builder.append(", ").append(str);
            }
            tableinfo = builder.toString();
        } else {
            tableinfo = columns[0];
        }

        if (tableinfo == null)
            return;

        tableinfo = tableinfo.replaceFirst(", ", "");
        if (!TABLENAMES.contains("UHC"))
            TABLENAMES.add("UHC");

        if (!Database.TABLEINFO.containsKey(tableinfo))
            Database.TABLEINFO.put("UHC", tableinfo);

        if (MySQLAPI.isConnected()) {
            try {
                String qry = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableinfo + ")";
                PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
                prepstate.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exRemoveQry(String tableName, String condition, String value) {
        if (MySQLAPI.isConnected()) {
            try {
                String qry = "DELETE FROM " + tableName + " WHERE(" + condition + "='" + value + "')";
                PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
                prepstate.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void exInsertQry(String tableName, String... values) {
        StringBuilder tableinfostr = new StringBuilder();
        for (int i = 1; i <= getColumnAmount(tableName); i++) {
            tableinfostr.append(", ").append(getColumnName(tableName, i));
        }
        StringBuilder builder = new StringBuilder();
        for (String str : values) {
            builder.append(", '").append(str).append("'");
        }

        if (MySQLAPI.isConnected()) {
            try {
                String qry = "INSERT INTO " + tableName + " VALUES (" + builder.toString().replaceFirst(", ", "") + ")";
                PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
                prepstate.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet exOrderQry(String tableName, String sel, String orderColumn) {
        if (MySQLAPI.isConnected()) {
            try {
                String qry = "SELECT " + sel + " FROM " + tableName + " ORDER BY " + orderColumn + " asc";
                PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
                return prepstate.getResultSet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void update(String tableName, UUID uuid, String column, String updatevalue) {
        if (MySQLAPI.isConnected()) {
            try {
                String qry = "UPDATE " + tableName + " SET " + column + "=? WHERE " + "UUID" + "=?";
                PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
                prepstate.setString(1, updatevalue);
                prepstate.setString(2, uuid.toString());
                prepstate.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Name VARCHAR(234)
    public static String createColumn(String name) {
        return name + " " + MySQLDataType.TEXT.toString() + "(5000)";
    }

    private static int getColumnAmount(String tableName) {
        if (TABLEINFO.get(tableName) == null) {
            return 0;
        }
        if (!TABLEINFO.get(tableName).contains(", ")) {
            return 1;
        }
        String[] info = TABLEINFO.get(tableName).split(", ");
        return info.length;
    }

    private static String getColumnName(String tableName, int column) {
        if (TABLEINFO.get(tableName) == null) {
            return null;
        }
        String[] info = TABLEINFO.get(tableName).split(", ");

        return info[column - 1].split(" ")[0];
    }

    public static Object getResult(String tableName, String condition, String value, String column) {
        if (MySQLAPI.isConnected()) {
            try {
                String qry = "SELECT * FROM " + tableName + " WHERE (" + condition + "=?)";
                PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
                prepstate.setString(1, value);
                ResultSet rs = prepstate.executeQuery();
                if (rs == null) {
                    return null;
                }
                while (rs.next()) {
                    return rs.getObject(column);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object getObjectResult(String tableName, String column) {
        if (MySQLAPI.isConnected()) {
            try {
                String qry = "SELECT * FROM " + tableName;
                PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
                ResultSet rs = prepstate.executeQuery();
                while (rs.next()) {
                    return rs.getObject(column);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<UUID> getConditionListResult(String tableName, String condition, String value, String column) {

        ArrayList<UUID> list = new ArrayList<>();

        ResultSet rs = Database.getResult("SELECT * FROM " + tableName);

        try {
            if (rs == null) {
                return new ArrayList<>();
            }
            while (rs.next()) {
                if (rs.getString(condition).contains(value)) {
                    if (!rs.getString(column).contains(", ")) {
                        if (!rs.getString(column).replace("[", "").replace("]", "").contains("-")) {
                            return new ArrayList<>();
                        }
                        list.add(UUID.fromString(rs.getString(column).replace("[", "").replace("]", "")));
                    } else {
                        if (!rs.getString(column).replace("[", "").replace("]", "").contains("-")) {
                            return new ArrayList<>();
                        }
                        String[] strlist = rs.getString(column).split(", ");
                        for (String aStrlist : strlist) {
                            list.add(UUID.fromString(aStrlist.replace("[", "").replace("]", "")));
                        }
                    }
                    return list;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Object> getListResult(String tableName, String column) {

        ArrayList<Object> list = new ArrayList<>();

        ResultSet rs = Database.getResult("SELECT * FROM " + tableName);

        try {
            if (rs == null) {
                return new ArrayList<>();
            }
            while (rs.next()) {
                if (!rs.getString(column).contains(", ")) {
                    list.add(rs.getString(column).replace("[", "").replace("]", ""));
                } else {
                    String[] strlist = rs.getString(column).split(", ");
                    for (String aStrlist : strlist) {
                        list.add(aStrlist.replace("[", "").replace("]", ""));
                    }
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean existInColumn(String tableName, String columnname, String match) {
        if (MySQLAPI.isConnected()) {
            if (TABLEINFO.get(tableName) == null) {
                return false;
            }

            String[] info = TABLEINFO.get(tableName).split(", ");

            for (String cname : info) {
                if (Objects.equals(cname.split(" ")[0], columnname)) {

                    for (Object objectname : getResultObjectArray(tableName, columnname)) {

                        if (objectname == match) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static List<Object> getResultObjectArray(String tableName, String columnname) {
        List<Object> list = new ArrayList<>();

        ResultSet rs = getResult("SELECT * FROM " + tableName);

        try {
            while (rs != null && rs.next()) {
                list.add(rs.getObject(columnname));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static ResultSet getResult(String qry) {

        if (MySQLAPI.isConnected()) {
            try {
                return MySQLAPI.getMySQLConnection().createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
