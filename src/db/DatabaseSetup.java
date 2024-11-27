package db;

import exception.DatabaseException;

public class DatabaseSetup {

	public static void inicializarH2DB() throws DatabaseException {
		ConnectionManager.initialize("jdbc:h2:tcp://localhost/~/test", "sa", "");
	}

	public static void dropAllTables() throws DatabaseException {
		String query = "DROP TABLE IF EXISTS movimiento, tarjeta, cuenta_bancaria, usuario";
		QueryExecutor.ejecutarUpdate(query);
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

	public static void crearTablaTarjeta() throws DatabaseException {
		String query = "CREATE TABLE IF NOT EXISTS tarjeta (" + "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "numeroTarjeta VARCHAR(255), " + "tipo VARCHAR(255), " + "limiteCredito DOUBLE, "
				+ "saldoUtilizado DOUBLE, " + "saldoDisponible DOUBLE, " + "usuarioId INT)";
		QueryExecutor.ejecutarUpdate(query);
	}

	public static void crearTablaMovimiento() throws DatabaseException {
		String query = "CREATE TABLE IF NOT EXISTS movimiento (" + "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "tipo VARCHAR(255), " + "fecha TIMESTAMP, " + "monto DOUBLE, " + "descripcion VARCHAR(255), "
				+ "cuentaId INT, " + "tarjetaId INT, " + "usuarioId INT, "
				+ "saldoPrevio DOUBLE, " + "saldoPosterior DOUBLE)";
		QueryExecutor.ejecutarUpdate(query);
	}
}