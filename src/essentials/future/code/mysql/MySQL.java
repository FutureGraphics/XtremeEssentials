package essentials.future.code.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import essentials.future.code.ConfigManager.ConfigManager;

public class MySQL {

	public static String host = ConfigManager.getMySQLHost();
	public static String port = "0";
	public static String database = ConfigManager.getMySQLDatabase();
	public static String username = ConfigManager.getMySQLUsername();
	public static String password = ConfigManager.getMySQLPassword();
	public static Connection con = null;

	public static void connect() {
		if (!isConnected()) {
			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://" + host + "/" + database + "?" + "user=" + username + "&password=" + password);
				System.out.println("Essentials: MySQL > Connect");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void disconnect() {
		if (isConnected()) {
			try {
				con.close();
				System.out.println("Essentials: MySQL > Disconnect");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isConnected() {
		if (con == null) {
			return false;
		} else {
			return true;
		}
	}

	public static Connection getConnection() {
		try {
			if (con.isValid(28800))
				return con;
			else {
				connect();
				return con;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
}