package inventory.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.prefs.Preferences;


/**
 * Class to use JDBC and connect to MySQL
 * @author gaut2172
 *
 */
public class DBConnection {
	
	// used for logging database credentials into Windows Registry
	Preferences preferences = Preferences.userNodeForPackage(DBConnection.class);
	
	private static final String URL = "jdbc:mysql://localhost:3306/inventory";
	private static final String PARAMETERS = "?verifyServerCertificate=false&useSSL=true";
	
	
	/*
	 * get MySQL username from Windows Registry
	 */
	public String getPrefUsername() {
		return preferences.get("db_username", null);
	}
	
	
	/*
	 * get MySQL password from Windows Registry
	 */
	public String getPrefPassword() {
		return preferences.get("db_password", null);
	}
	
	
	/*
	 * get connection to MySQL database
	 */
	public static Connection getConnection() {
		
		System.out.println("\nAttempting connection...");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// FIXME: Move this DB username and password to an encrypted external file
			Connection conn = DriverManager.getConnection(URL+PARAMETERS, "inventoryAdmin", "password");
			if (conn == null) {
				System.out.println("Problem connecting to database");
			}
			else {
				System.out.println("Connected\n\n");
				return conn;
			}

		} catch(Exception e) {e.printStackTrace();}
		
		return null;
	}
	
	public static void disconnect(Connection conn) throws SQLException {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
