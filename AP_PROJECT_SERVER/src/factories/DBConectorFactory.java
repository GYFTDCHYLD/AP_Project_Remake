package factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConectorFactory {
	private static java.sql.Connection connection = null;
	static String url = "jdbc:mysql://localhost:3306/microstar";
	Connection myConn = null;
	
	public static java.sql.Connection getDatabaseConnection() {
		try {
			if(connection == null)
				connection =  DriverManager.getConnection(url, "root", "");
		} catch (SQLException e) {
			System.err.println("Could not establish a connection to database!!");
		}
		return connection;
	}

}
