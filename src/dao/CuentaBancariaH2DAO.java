package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.ConnectionManager;
import db.QueryExecutor;
import exception.DatabaseException;
import model.CuentaBancaria;
import model.Usuario;

public class CuentaBancariaH2DAO implements Dao<CuentaBancaria> {

	private final CuentaBancariaMapper mapper = new CuentaBancariaMapper();

	@Override
	public CuentaBancaria buscarPorId(int id) throws DatabaseException {
		String query = "SELECT * FROM cuenta_bancaria WHERE id = " + id;
		List<CuentaBancaria> cuentas = QueryExecutor.ejecutarSelect(query, mapper);
		return cuentas.isEmpty() ? null : cuentas.get(0);
	}

	@Override
	public List<CuentaBancaria> listarTodos() throws DatabaseException {
		String query = "SELECT * FROM cuenta_bancaria";
		return QueryExecutor.ejecutarSelect(query, mapper);
	}

	@Override
	public int insertar(CuentaBancaria cuenta) throws DatabaseException {
		String query = String.format(
				"INSERT INTO cuenta_bancaria (numeroCuenta, saldo, tipoCuenta, clienteId, cbu, alias) VALUES ('%s', %f, '%s', %d, '%s', '%s')",
				cuenta.getNumeroCuenta(), cuenta.getSaldo(), cuenta.getTipoCuenta(), cuenta.getUsuario().getId(),
				cuenta.getCbu(), cuenta.getAlias());
		int idGenerado = QueryExecutor.ejecutarInsert(query);
		cuenta.setId(idGenerado);

		return idGenerado;
	}

	@Override
	public void actualizar(CuentaBancaria entidad) throws DatabaseException {
		String query = String.format(
				"UPDATE cuenta_bancaria SET saldo = %.2f, tipoCuenta = '%s', clienteId = %d, cbu = '%s', alias = '%s' WHERE id = %d",
				entidad.getSaldo(), entidad.getTipoCuenta(), entidad.getUsuario().getId(), entidad.getCbu(),
				entidad.getAlias(), entidad.getId());
		QueryExecutor.ejecutarUpdate(query);
	}

	@Override
	public void eliminar(int id) throws DatabaseException {
		String query = String.format("DELETE FROM cuenta_bancaria WHERE id = %d", id);
		QueryExecutor.ejecutarUpdate(query);
	}

	/*
	 * Buscar una cuenta bancaria. Se recibira un string como input que puede ser el
	 * id, el CBU o el alias de la cuenta.
	 */
	public CuentaBancaria buscarCuenta(String input) throws DatabaseException {
		List<CuentaBancaria> cuentas;

		try {
			// Primero intentar buscar por ID si el input es numerico
			int id = Integer.parseInt(input);
			String queryPorId = String.format("SELECT * FROM cuenta_bancaria WHERE id = %d", id);
			cuentas = QueryExecutor.ejecutarSelect(queryPorId, mapper);

			if (!cuentas.isEmpty()) {
				return cuentas.get(0);
			}
		} catch (NumberFormatException e) {
			// Si el campo no es numerico, continuar buscando por CBU o alias
		}

		// Si no se encontro la cuenta por ID o el input no era numerico buscar por CBU
		// o alias
		String queryPorCbuAlias = String.format("SELECT * FROM cuenta_bancaria WHERE cbu = '%s' OR alias = '%s'", input,
				input);
		cuentas = QueryExecutor.ejecutarSelect(queryPorCbuAlias, mapper);

		return cuentas.isEmpty() ? null : cuentas.get(0);
	}

	/*
	 * Este metodo actualiza los saldos de las dos cuentas dadas. Esta operacion se
	 * realiza dentro de una transaccion, asegurando que si una de las
	 * actualizaciones falla, ninguna actualizacion se hara en las cuentas.
	 */
	public void actualizarSaldos(CuentaBancaria cuentaOrigen, CuentaBancaria cuentaDestino) throws DatabaseException {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			actualizarSaldo(cuentaOrigen);
			actualizarSaldo(cuentaDestino);

			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					throw new DatabaseException("Error al realizar rollback de la transacción", rollbackEx);
				}
			}
			throw new DatabaseException("Error al actualizar los saldos", e);
		} finally {
			if (connection != null) {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					throw new DatabaseException("Error al setear autocommit", e);
				}
			}
		}
	}

	public List<CuentaBancaria> listarCuentasUsuario(Usuario usuario) throws DatabaseException {
		String queryPorId = String.format("SELECT * FROM cuenta_bancaria WHERE clienteId = %d", usuario.getId());
		return QueryExecutor.ejecutarSelect(queryPorId, mapper);
	}

	public void actualizarSaldo(CuentaBancaria cuenta) throws DatabaseException {
		String queryOrigen = String.format("UPDATE cuenta_bancaria SET saldo = %.2f WHERE id = %d", cuenta.getSaldo(),
				cuenta.getId());
		QueryExecutor.ejecutarUpdate(queryOrigen);
	}

}
