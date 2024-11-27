package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import model.Movimiento;

public class MovimientoMapper implements DaoMapper<Movimiento> {

	@Override
	public Movimiento map(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String tipo = resultSet.getString("tipo");
		Timestamp fecha = resultSet.getTimestamp("fecha");
		double monto = resultSet.getDouble("monto");
		String descripcion = resultSet.getString("descripcion");
		Integer cuentaId = resultSet.getInt("cuentaId");
		if (resultSet.wasNull()) {
			cuentaId = null;
		}
		Integer tarjetaId = resultSet.getInt("tarjetaId");
		if (resultSet.wasNull()) {
			tarjetaId = null;
		}
		Integer usuarioId = resultSet.getInt("usuarioId");
		if (resultSet.wasNull()) {
			usuarioId = null;
		}
		double saldoPrevio = resultSet.getDouble("saldoPrevio");
		double saldoPosterior = resultSet.getDouble("saldoPosterior");

		try {
			return new Movimiento(id, tipo, fecha, monto, descripcion, cuentaId, tarjetaId,
					usuarioId, saldoPrevio, saldoPosterior);
		} catch (Exception e) {
			throw new SQLException("Error al mapear el objeto Movimiento", e);
		}
	}
}