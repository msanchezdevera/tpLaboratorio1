package dao;

import java.util.List;

import db.QueryExecutor;
import exception.DatabaseException;
import model.CuentaBancaria;

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
				"INSERT INTO cuenta_bancaria (numeroCuenta, saldo, tipoCuenta, clienteId) VALUES ('%s', %f, '%s', %d)",
				cuenta.getNumeroCuenta(), cuenta.getSaldo(), cuenta.getTipoCuenta(), cuenta.getClienteId());
		int idGenerado = QueryExecutor.ejecutarInsert(query);
		cuenta.setId(idGenerado);

		return idGenerado;
	}

	@Override
	public void actualizar(CuentaBancaria entidad) throws DatabaseException {
		String query = String.format(
				"UPDATE cuenta_bancaria SET saldo = %.2f, tipoCuenta = '%s', clienteId = %d WHERE id = %d",
				entidad.getSaldo(), entidad.getTipoCuenta(), entidad.getClienteId(), entidad.getId());
		QueryExecutor.ejecutarUpdate(query);
	}

	@Override
	public void eliminar(int id) throws DatabaseException {
		String query = String.format("DELETE FROM cuenta_bancaria WHERE id = %d", id);
		QueryExecutor.ejecutarUpdate(query);
	}

}
