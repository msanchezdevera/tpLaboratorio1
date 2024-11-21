package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;

public class UsuarioMapper implements DaoMapper<Usuario> {

	@Override
	public Usuario map(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		String apellido = resultSet.getString("apellido");
		String email = resultSet.getString("email");
		String contrasena = resultSet.getString("contrasena");
		String tipoUsuario = resultSet.getString("tipoUsuario");

		return new Usuario(id, nombre, apellido, email, contrasena, tipoUsuario);
	}
}
