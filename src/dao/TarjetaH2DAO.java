package dao;

import db.QueryExecutor;
import exception.DatabaseException;
import model.Tarjeta;
import model.Usuario;

import java.util.List;

public class TarjetaH2DAO implements Dao<Tarjeta> {

	private final TarjetaMapper mapper = new TarjetaMapper();

	@Override
	public Tarjeta buscarPorId(int id) throws DatabaseException {
		String query = "SELECT * FROM tarjeta WHERE id = " + id;
		List<Tarjeta> tarjetas = QueryExecutor.ejecutarSelect(query, mapper);
		return tarjetas.isEmpty() ? null : tarjetas.get(0);
	}

	@Override
	public List<Tarjeta> listarTodos() throws DatabaseException {
		String query = "SELECT * FROM tarjeta";
		return QueryExecutor.ejecutarSelect(query, mapper);
	}

	public List<Tarjeta> listarTarjetas(Usuario usuario) throws DatabaseException {
		String query = String.format("SELECT * FROM tarjeta WHERE usuarioId = %d", usuario.getId());
		return QueryExecutor.ejecutarSelect(query, mapper);
	}

	@Override
	public int insertar(Tarjeta tarjeta) throws DatabaseException {
		String query = String.format(
				"INSERT INTO tarjeta (numeroTarjeta, tipo, limiteCredito, saldoUtilizado, usuarioId) "
						+ "VALUES ('%s', '%s', %.2f, %.2f, %d)",
				tarjeta.getNumeroTarjeta(), tarjeta.getTipo(), tarjeta.getLimiteCredito(), tarjeta.getSaldoUtilizado(),
				tarjeta.getUsuarioId());
		int idGenerado = QueryExecutor.ejecutarInsert(query);
		tarjeta.setId(idGenerado);
		return idGenerado;
	}

	@Override
	public void actualizar(Tarjeta tarjeta) throws DatabaseException {
		String query = String.format(
				"UPDATE tarjeta SET numeroTarjeta = '%s', tipo = '%s', limiteCredito = %.2f, "
						+ "saldoUtilizado = %.2f, usuarioId = %d WHERE id = %d",
				tarjeta.getNumeroTarjeta(), tarjeta.getTipo(), tarjeta.getLimiteCredito(), tarjeta.getSaldoUtilizado(),
				tarjeta.getUsuarioId(), tarjeta.getId());
		QueryExecutor.ejecutarUpdate(query);
	}

	@Override
	public void eliminar(int id) throws DatabaseException {
		String query = "DELETE FROM tarjeta WHERE id = " + id;
		QueryExecutor.ejecutarUpdate(query);
	}
}