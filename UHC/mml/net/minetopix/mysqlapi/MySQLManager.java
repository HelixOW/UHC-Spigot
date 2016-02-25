package net.minetopix.mysqlapi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MySQLManager {
	
	public static HashMap<String, String> tableinfo = new HashMap<String, String>();
	public static ArrayList<String> tablenames = new ArrayList<String>();

	public static void exCreateTableQry(String tablename, String ...columns) {
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
		
		tableinfo = tableinfo.toString().replaceFirst(", ", "");
		if(!tablenames.contains(tablename)) tablenames.add(tablename);
		
		if(!MySQLManager.tableinfo.containsKey(tableinfo)) MySQLManager.tableinfo.put(tablename, tableinfo);
		
		if(MySQLAPI.isConnected()){	
			try {
				String qry = "CREATE TABLE IF NOT EXISTS " + tablename + " (" + tableinfo + ")";
				PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
				prepstate.executeUpdate();
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public static void exInsertQry(String table, String ...values) {
		StringBuilder tableinfostr = new StringBuilder();
		for(int i = 1; i <= getColumnAmount(table); i++) {
			tableinfostr.append(", ").append(getColumnName(table, i));
		}
		StringBuilder builder = new StringBuilder();
		for(String str : values) {
			builder.append(", '").append(str).append("'");
		}
		
		if(MySQLAPI.isConnected()) {
			try {
				String qry  = "INSERT INTO " + table + " VALUES (" + builder.toString().replaceFirst(", ", "") + ")";
				PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
				prepstate.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void exUpdateQry(String table, String condition, String conditionvalue, String column, String updatevalue) {
		if(MySQLAPI.isConnected()) {		
			try {
				String qry = "UPDATE " + table + " SET " + column + "=? WHERE " + condition + "=?";
				PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
				prepstate.setString(1, updatevalue);
				prepstate.setString(2, conditionvalue);
				prepstate.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
//Name VARCHAR(234)
	public static String createColumn(String name, MySQLDataType type, int size) {
		return name + " " + type.toString() + "(" + Integer.toString(size) + ")";
	}
	
	public static int getColumnAmount(String table) {
		if(tableinfo.get(table) == null) {
			return 0;
		}
		if(!tableinfo.get(table).contains(", ")) {
			return 1;
		}
		String[] info = tableinfo.get(table).split(", ");
		return info.length;
	}

	public static String getColumnName(String table, int column) {
		if(tableinfo.get(table) == null) {
			return null;
		}
		String[] info = tableinfo.get(table).split(", ");
		String columnname = info[column - 1].split(" ")[0];
		
		return columnname;
	}
	
	public static String getColumnSize(String table, int column) {
		if(tableinfo.get(table) == null) {
			return null;
		}
		String[] info = tableinfo.get(table).split(", ");
		String columnsize = info[column - 1].split(" ")[1].split("(")[1].replace(")", "");
		
		return columnsize;
	}
	
	public static MySQLDataType getComlumnDataType(String table, int column) {
		if(tableinfo.get(table) == null) {
			return null;
		}
		String[] info = tableinfo.get(table).split(", ");
		MySQLDataType columntype = MySQLDataType.valueOf(info[column - 1].split(" ")[1].split("(")[0]);
		
		return columntype;
	}
	
	public static Object getObjectConditionResult(String table, String condition, String value, String column) {
		if(MySQLAPI.isConnected()) {
			try {
				String qry = "SELECT * FROM " + table + " WHERE (" + condition + "=?)";
				PreparedStatement prepstate = MySQLAPI.getMySQLConnection().prepareStatement(qry);
				prepstate.setString(1, value);
				ResultSet rs = prepstate.executeQuery();
				if(rs == null) {
					return null;
				}
				while(rs.next()) {
					return rs.getObject(column);
				}
			} catch (SQLException e) {
				e.printStackTrace();
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
		
		ArrayList<UUID> list = new ArrayList<UUID>();
		
		ResultSet rs = MySQLManager.getResult("SELECT * FROM " + table);
		
		try{
			if(rs == null) {
				return new ArrayList<UUID>();
			}
			while(rs.next()) {
				if(rs.getString(condition).contains(value)) {
					if(!rs.getString(column).contains(", ")) {
						if(!rs.getString(column).replace("[", "").replace("]", "").contains("-")) {
							return new ArrayList<UUID>();
						}
						list.add(UUID.fromString(rs.getString(column).replace("[", "").replace("]", "")));
					} else {
						if(!rs.getString(column).replace("[", "").replace("]", "").contains("-")) {
							return new ArrayList<UUID>();
						}
						String[] strlist = rs.getString(column).split(", ");
						for(int i = 0; i < strlist.length; i++) {
							list.add(UUID.fromString(strlist[i].replace("[", "").replace("]", "")));
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
		
		ArrayList<Object> list = new ArrayList<Object>();
		
		ResultSet rs = MySQLManager.getResult("SELECT * FROM " + table);
		
		try{
			if(rs == null) {
				return new ArrayList<Object>();
			}
			while(rs.next()) {
				if(!rs.getString(column).contains(", ")) {
					list.add(rs.getString(column).replace("[", "").replace("]", ""));
				} else {
					String[] strlist = rs.getString(column).split(", ");
					for(int i = 0; i < strlist.length; i++) {
						list.add(strlist[i].replace("[", "").replace("]", ""));
					}
				}
			}
			return list;
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
				if(cname.split(" ")[0] == columnname) {
					
					for(Object objectname : getResultObjectArray(table, columnname)) {
						
						if((String) objectname == match) {
							return true;
						}
					}		
				}		
			}
		}
		return false;
	}
	
	public static List<Object> getResultObjectArray(String table, String columnname) {
		List<Object> list = new ArrayList<Object>();
		
		ResultSet rs = getResult("SELECT * FROM " + table);
		
		try {
			while (rs.next()) {
				list.add(rs.getObject(columnname));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;	
	}
	
	public static ResultSet getResult(String qry) {
		
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

