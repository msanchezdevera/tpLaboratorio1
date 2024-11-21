package db;

import exception.DatabaseException;

public class DatabaseSetup {

	public static void inicializarH2DB() throws DatabaseException {
		ConnectionManager.initialize("jdbc:h2:tcp://localhost/~/test", "sa", "");
	}

	public static void crearTablaCuentaBancaria() throws DatabaseException {
		String query = "CREATE TABLE IF NOT EXISTS cuenta_bancaria (" + "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "numeroCuenta VARCHAR(255), " + "saldo DOUBLE, " + "tipoCuenta VARCHAR(255), " + "clienteId INT, "
				+ "cbu VARCHAR(255), " + "alias VARCHAR(255))";
		QueryExecutor.ejecutarUpdate(query);
	}

	public static void crearTablaUsuario() throws DatabaseException {
		String query = "CREATE TABLE IF NOT EXISTS usuario (" + "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "nombre VARCHAR(255), " + "apellido VARCHAR(255), " + "email VARCHAR(255), "
				+ "contrasena VARCHAR(255), " + "tipoUsuario VARCHAR(255))";
		QueryExecutor.ejecutarUpdate(query);
	}

}
