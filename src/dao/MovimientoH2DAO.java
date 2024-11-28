package dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import db.QueryExecutor;
import exception.DatabaseException;
import model.Movimiento;

public class MovimientoH2DAO implements Dao<Movimiento> {
	private final MovimientoMapper mapper = new MovimientoMapper();

	@Override
	public Movimiento buscarPorId(int id) throws DatabaseException {
		String query = "SELECT * FROM movimiento WHERE id = " + id;
		List<Movimiento> movimientos = QueryExecutor.ejecutarSelect(query, mapper);
		return movimientos.isEmpty() ? null : movimientos.get(0);
	}

	@Override
	public List<Movimiento> listarTodos() throws DatabaseException {
		String query = "SELECT * FROM movimiento";
		return QueryExecutor.ejecutarSelect(query, mapper);
	}

	@Override
	public int insertar(Movimiento movimiento) throws DatabaseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaFormateada = dateFormat.format(movimiento.getFecha());

		String query = String.format(
				"INSERT INTO movimiento (tipo, monto, fecha, descripcion, cuentaId, tarjetaId, usuarioId, saldoPrevio, saldoPosterior) "
						+ "VALUES ('%s', %f, '%s', '%s', %d, %d, %d, %f, %f)",
				movimiento.getTipo(), movimiento.getMonto(), fechaFormateada, movimiento.getDescripcion(),
				movimiento.getCuentaId(), movimiento.getTarjetaId(), movimiento.getUsuarioId(),
				movimiento.getSaldoPrevio(), movimiento.getSaldoPosterior());
		int idGenerado = QueryExecutor.ejecutarInsert(query);
		movimiento.setId(idGenerado);
		return idGenerado;
	}

	@Override
	public void actualizar(Movimiento movimiento) throws DatabaseException {
		String query = String.format(
				"UPDATE movimiento SET tipo = '%s', monto = %f, fecha = '%s', descripcion = '%s', cuentaId = %d, tarjetaId = %d, usuarioId = %d, saldoPrevio = %f, saldoPosterior = %f WHERE id = %d",
				movimiento.getTipo(), movimiento.getMonto(), movimiento.getFecha(), movimiento.getDescripcion(),
				movimiento.getCuentaId(), movimiento.getTarjetaId(), movimiento.getUsuarioId(),
				movimiento.getSaldoPrevio(), movimiento.getSaldoPosterior());
		QueryExecutor.ejecutarUpdate(query);
	}

	@Override
	public void eliminar(int id) throws DatabaseException {
		String query = String.format("DELETE FROM movimiento WHERE id = %d", id);
		QueryExecutor.ejecutarUpdate(query);
	}

	public List<Movimiento> listarPorTarjeta(int tarjetaId) throws DatabaseException {
		String query = String.format("SELECT * FROM movimiento WHERE tarjetaId = %d", tarjetaId);
		return QueryExecutor.ejecutarSelect(query, mapper);
	}

	public List<Movimiento> listarPorCuenta(int cuentaId) throws DatabaseException {
		String query = String.format("SELECT * FROM movimiento WHERE cuentaId = %d", cuentaId);
		return QueryExecutor.ejecutarSelect(query, mapper);
	}

	public List<Movimiento> listarPorUsuario(int usuarioId) throws DatabaseException {
		String query = String.format("SELECT * FROM movimiento WHERE usuarioId = %d", usuarioId);
		return QueryExecutor.ejecutarSelect(query, mapper);
	}

	public List<Movimiento> buscarPorTarjetaYMes(int tarjetaId, int mes) throws DatabaseException {
		String query = String.format("SELECT * FROM movimiento WHERE tarjetaId = %d AND MONTH(fecha) = %d", tarjetaId,
				mes);
		return QueryExecutor.ejecutarSelect(query, mapper);
	}
}
