package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.CuentaBancaria;

public class CuentaBancariaMapper implements DaoMapper<CuentaBancaria> {

	@Override
	public CuentaBancaria map(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String numeroCuenta = resultSet.getString("numeroCuenta");
		double saldo = resultSet.getDouble("saldo");
		String tipoCuenta = resultSet.getString("tipoCuenta");
		int clienteId = resultSet.getInt("clienteId");

		try {
			return new CuentaBancaria(id, numeroCuenta, saldo, tipoCuenta, clienteId);
		} catch (Exception e) {
			throw new SQLException("Error al mapear el objeto CuentaBancaria", e);
		}
	}

}
