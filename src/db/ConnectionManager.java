package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.DatabaseException;

public class ConnectionManager {

	private static String url;
	private static String user;
	private static String password;
	private static String driver = "org.h2.Driver";
	private static Connection connection;

	private ConnectionManager() {
	}

	public static void initialize(String driver, String host, String user, String password) throws DatabaseException {
		ConnectionManager.driver = driver;
		initialize(host, user, password);
	}

	public static void initialize(String host, String user, String password) throws DatabaseException {
		ConnectionManager.url = host;
		ConnectionManager.user = user;
		ConnectionManager.password = password;
		try {
			Class.forName(ConnectionManager.driver);
		} catch (ClassNotFoundException e) {
			throw new DatabaseException("Error al cargar el driver de la base de datos", e);
		}
	}

	public static Connection getConnection() throws DatabaseException {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				throw new DatabaseException("Error al obtener la conexión a la base de datos", e);
			}
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				System.err.println("Error al cerrar la conexión: " + e.getMessage());
			}
		}
	}

}
