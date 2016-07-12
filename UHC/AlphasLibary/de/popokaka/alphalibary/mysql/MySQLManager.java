package de.popokaka.alphalibary.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.bukkit.Bukkit;

public class MySQLManager {
	
	private static final HashMap<String, String> tableinfo = new HashMap<>();
	private static final ArrayList<String> tablenames = new ArrayList<>();

	public static void exCreateTableQry(String... columns) {
		String tableinfo;
		if(columns.length > 1) {
			StringBuilder builder = new StringBuilder();
			for(String str : columns) {
				builder.append(", ").append(str);
			}
			tableinfo = builder.toString();
		} else {
			tableinfo = columns[0];
		}
		if(tableinfo == null) return;
		
		tableinfo = tableinfo.replaceFirst(", ", "");
		if(!tablenames.contains("UHC")) tablenames.add("UHC");
		
		if(!MySQLManager.tableinfo.containsKey(tableinfo)) MySQLManager.tableinfo.put("UHC", tableinfo);
		
		if(MySQLAPI.isConnected()){	
			try {
				if(MySQLAPI.tableExists()) {
					String qry = "ALTER TABLE UHC ADD (Count INT)";
					PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
					prepstate.executeUpdate();
				} else {
					String qry = "CREATE TABLE IF NOT EXISTS " + "UHC" + " (" + tableinfo + ")";
					PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
					prepstate.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
	
	public static void exRemoveQry(String table, String condition, String value) {
		if(MySQLAPI.isConnected()) {
			try {
				String qry  = "DELETE FROM " + table + " WHERE(" + condition +"='" + value +"')";
				PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
				prepstate.executeUpdate();
			} catch (Exception e) {
				Bukkit.reload();
			}
		}	
	}
	
	public static void exInsertQry(String... values) {
		StringBuilder tableinfostr = new StringBuilder();
		for(int i = 1; i <= getColumnAmount(); i++) {
			tableinfostr.append(", ").append(getColumnName(i));
		}
		StringBuilder builder = new StringBuilder();
		for(String str : values) {
			builder.append(", '").append(str).append("'");
		}
		
		if(MySQLAPI.isConnected()) {
			try {
				String qry  = "INSERT INTO " + "UHC" + " VALUES (" + builder.toString().replaceFirst(", ", "") + ")";
				PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
				prepstate.executeUpdate();
			} catch (Exception e) {
				Bukkit.reload();
			}
		}
	}
	
	public static void exUpdateQry(String conditionvalue, String column, String updatevalue) {
		if(MySQLAPI.isConnected()) {		
			try {
				String qry = "UPDATE " + "UHC" + " SET " + column + "=? WHERE " + "UUID" + "=?";
				PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
				prepstate.setString(1, updatevalue);
				prepstate.setString(2, conditionvalue);
				prepstate.executeUpdate();
			} catch (Exception e) {
				Bukkit.reload();
			}
			
		}
	}
//Name VARCHAR(234)
	public static String createColumn(String name, int size) {
		return name + " " + MySQLDataType.VARCHAR.toString() + "(" + Integer.toString(size) + ")";
	}
	
	private static int getColumnAmount() {
		if(tableinfo.get("UHC") == null) {
			return 0;
		}
		if(!tableinfo.get("UHC").contains(", ")) {
			return 1;
		}
		String[] info = tableinfo.get("UHC").split(", ");
		return info.length;
	}

	private static String getColumnName(int column) {
		if(tableinfo.get("UHC") == null) {
			return null;
		}
		String[] info = tableinfo.get("UHC").split(", ");

		return info[column - 1].split(" ")[0];
	}

	public static Object getObjectConditionResult(String condition, String value, String column) {
		if(MySQLAPI.isConnected()) {
			try {
				String qry = "SELECT * FROM " + "UHC" + " WHERE (" + condition + "=?)";
				PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
				prepstate.setString(1, value);
				ResultSet rs = prepstate.executeQuery();
				if(rs == null) {
					return null;
				}
				while(rs.next()) {
					return rs.getObject(column);
				}
			} catch (Exception e) {
				Bukkit.reload();
			}
		}
		return null;
	}
	
	public static Object getObjectResult(String table, String column) {
		if(MySQLAPI.isConnected()) {
			try {
				String qry = "SELECT * FROM " + table;
				PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
				ResultSet rs = prepstate.executeQuery();
				while(rs.next()) {
					return rs.getObject(column);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static ArrayList<UUID> getConditionListResult(String table, String condition, String value, String column) {
		
		ArrayList<UUID> list = new ArrayList<>();
		
		ResultSet rs = MySQLManager.getResult("SELECT * FROM " + table);
		
		try{
			if(rs == null) {
				return new ArrayList<>();
			}
			while(rs.next()) {
				if(rs.getString(condition).contains(value)) {
					if(!rs.getString(column).contains(", ")) {
						if(!rs.getString(column).replace("[", "").replace("]", "").contains("-")) {
							return new ArrayList<>();
						}
						list.add(UUID.fromString(rs.getString(column).replace("[", "").replace("]", "")));
					} else {
						if(!rs.getString(column).replace("[", "").replace("]", "").contains("-")) {
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
		} catch (SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<Object> getListResult(String table, String column) {
		
		ArrayList<Object> list = new ArrayList<>();
		
		ResultSet rs = MySQLManager.getResult("SELECT * FROM " + table);
		
		try{
			if(rs == null) {
				return new ArrayList<>();
			}
			while(rs.next()) {
				if(!rs.getString(column).contains(", ")) {
					list.add(rs.getString(column).replace("[", "").replace("]", ""));
				} else {
					String[] strlist = rs.getString(column).split(", ");
					for (String aStrlist : strlist) {
						list.add(aStrlist.replace("[", "").replace("]", ""));
					}
				}
				return list;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static boolean existInColumn(String table, String columnname, String match) {
		if(MySQLAPI.isConnected()) {
			if(tableinfo.get(table) == null) {
				return false;
			}

			
			String[] info = tableinfo.get(table).split(", ");
			
			for(String cname : info) {
				if(Objects.equals(cname.split(" ")[0], columnname)) {
					
					for(Object objectname : getResultObjectArray(table, columnname)) {
						
						if(objectname == match) {
							return true;
						}
					}		
				}		
			}
		}
		return false;
	}
	
	private static List<Object> getResultObjectArray(String table, String columnname) {
		List<Object> list = new ArrayList<>();
		
		ResultSet rs = getResult("SELECT * FROM " + table);
		
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
		
		if(MySQLAPI.isConnected()){
			try {
				return MySQLAPI.getMySQLConnection().createStatement().executeQuery(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
}

