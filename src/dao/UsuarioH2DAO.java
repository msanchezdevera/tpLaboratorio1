package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.ConnectionManager;
import db.QueryExecutor;
import exception.DatabaseException;
import model.Usuario;

public class UsuarioH2DAO implements Dao<Usuario> {

	private final UsuarioMapper mapper = new UsuarioMapper();

	@Override
	public Usuario buscarPorId(int id) throws DatabaseException {
		String query = "SELECT * FROM usuario WHERE id = " + id;
		List<Usuario> usuarios = QueryExecutor.ejecutarSelect(query, mapper);
		return usuarios.isEmpty() ? null : usuarios.get(0);
	}

	@Override
	public List<Usuario> listarTodos() throws DatabaseException {
		String query = "SELECT * FROM usuario";
		return QueryExecutor.ejecutarSelect(query, mapper);
	}

	@Override
	public int insertar(Usuario usuario) throws DatabaseException {
		String query = String.format(
				"INSERT INTO usuario (nombre, apellido, email, contrasena, tipoUsuario) VALUES ('%s', '%s', '%s', '%s', '%s')",
				usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getContrasena(),
				usuario.getTipoUsuario());
		int idGenerado = QueryExecutor.ejecutarInsert(query);
		usuario.setId(idGenerado);
		return idGenerado;
	}

	@Override
	public void actualizar(Usuario usuario) throws DatabaseException {
		String query = String.format(
				"UPDATE usuario SET nombre = '%s', apellido = '%s', email = '%s', contrasena = '%s', tipoUsuario = '%s' WHERE id = %d",
				usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getContrasena(),
				usuario.getTipoUsuario(), usuario.getId());
		QueryExecutor.ejecutarUpdate(query);
	}

	@Override
	public void eliminar(int id) throws DatabaseException {
		String query = String.format("DELETE FROM usuario WHERE id = %d", id);
		QueryExecutor.ejecutarUpdate(query);
	}

	/*
	 * Buscar un usuario por email. Este método recibirá un email como input y
	 * buscará un usuario por su email.
	 */
	public Usuario buscarPorEmail(String email) throws DatabaseException {
		String query = String.format("SELECT * FROM usuario WHERE email = '%s'", email);
		List<Usuario> usuarios = QueryExecutor.ejecutarSelect(query, mapper);
		return usuarios.isEmpty() ? null : usuarios.get(0);
	}

	/*
	 * Actualizar múltiples usuarios dentro de una transacción. Esta operación
	 * asegura que si una de las actualizaciones falla, ninguna de las
	 * actualizaciones se realiza.
	 */
	public void actualizarUsuarios(Usuario... usuarios) throws DatabaseException {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);

			for (Usuario usuario : usuarios) {
				actualizarUsuario(usuario, connection);
			}

			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					throw new DatabaseException("Error al realizar rollback de la transacción", rollbackEx);
				}
			}
			throw new DatabaseException("Error al actualizar los usuarios", e);
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

	private void actualizarUsuario(Usuario usuario, Connection connection) throws DatabaseException {
		String query = String.format(
				"UPDATE usuario SET nombre = '%s', apellido = '%s', email = '%s', contrasena = '%s', tipoUsuario = '%s' WHERE id = %d",
				usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getContrasena(),
				usuario.getTipoUsuario(), usuario.getId());
		QueryExecutor.ejecutarUpdate(query);
	}
}
