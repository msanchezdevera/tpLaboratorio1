package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DaoMapper;
import exception.DatabaseException;

public class QueryExecutor {

	public static <T> List<T> ejecutarSelect(String query, DaoMapper<T> mapper) throws DatabaseException {
		Statement statement = null;
		ResultSet resultSet = null;
		List<T> results = new ArrayList<T>();
		try {
			Connection connection = ConnectionManager.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				T mappedObject = mapper.map(resultSet);
				results.add(mappedObject);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error al ejecutar la consulta SELECT", e);
		} finally {
			cerrarResultSet(resultSet);
			cerrarStatement(statement);
		}

		return results;
	}

	public static int ejecutarUpdate(String query) throws DatabaseException {
		Statement statement = null;
		int rowsAffected;
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.createStatement();
			rowsAffected = statement.executeUpdate(query);
			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					System.err.println("Error al realizar rollback: " + rollbackEx.getMessage());
				}
			}
			throw new DatabaseException("Error al ejecutar la consulta de actualización", e);
		} finally {
			cerrarStatement(statement);
		}

		return rowsAffected;
	}

	public static int ejecutarInsert(String query) throws DatabaseException {
		Statement statement = null;
		ResultSet generatedKeys = null;
		int generatedId = -1;
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				generatedId = generatedKeys.getInt(1);
			} else {
				throw new DatabaseException("No se pudo obtener el ID generado en el insert");
			}
			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					System.err.println("Error al realizar rollback: " + rollbackEx.getMessage());
				}
			}
			throw new DatabaseException("Error al ejecutar insert", e);
		} finally {
			cerrarResultSet(generatedKeys);
			cerrarStatement(statement);
		}

		return generatedId;
	}

	public static void cerrarResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.err.println("Error al cerrar el resultSet: " + e.getMessage());
			}
		}
	}

	public static void cerrarStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.err.println("Error al cerrar el statement: " + e.getMessage());
			}
		}
	}

}
