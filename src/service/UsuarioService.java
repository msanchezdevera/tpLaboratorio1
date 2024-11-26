package service;

import java.util.List;

import dao.UsuarioH2DAO;
import exception.DatabaseException;
import exception.TextoVacioException;
import exception.UsuarioNoEncontradoException;
import exception.UsuarioServiceException;
import model.Usuario;
import validador.Validador;

public class UsuarioService {

	private final UsuarioH2DAO usuarioDAO;

	public UsuarioService() {
		this.usuarioDAO = new UsuarioH2DAO();
	}

	public Usuario crearUsuario(String nombre, String apellido, String email, String contrasena, String tipoUsuario)
			throws TextoVacioException, UsuarioServiceException {
		try {
			Validador.validarTexto(nombre);
			Validador.validarTexto(apellido);
			Validador.validarTexto(email);
			Validador.validarTexto(contrasena);
			Validador.validarTexto(tipoUsuario);

			Usuario usuario = new Usuario(nombre, apellido, email, contrasena, tipoUsuario);
			usuarioDAO.insertar(usuario);
			return usuario;
		} catch (DatabaseException e) {
			throw new UsuarioServiceException("Error al crear el usuario", e);
		}
	}

	public List<Usuario> obtenerTodosLosUsuarios() throws UsuarioServiceException {
		try {
			return usuarioDAO.listarTodos();
		} catch (DatabaseException e) {
			throw new UsuarioServiceException("Error al obtener todos los usuarios", e);
		}
	}

	public Usuario obtenerUsuarioPorId(int id) throws UsuarioNoEncontradoException, UsuarioServiceException {
		try {
			Usuario usuario = usuarioDAO.buscarPorId(id);
			if (usuario == null) {
				throw new UsuarioNoEncontradoException("El usuario con ID " + id + " no fue encontrado.");
			}
			return usuario;
		} catch (DatabaseException e) {
			throw new UsuarioServiceException("Error al obtener el usuario con ID " + id, e);
		}
	}

	public void actualizarUsuario(int usuarioId, String nombre, String apellido, String email, String contrasena,
			String tipoUsuario) throws TextoVacioException, UsuarioNoEncontradoException, UsuarioServiceException {
		Validador.validarTexto(nombre);
		Validador.validarTexto(apellido);
		Validador.validarTexto(email);
		Validador.validarTexto(contrasena);
		Validador.validarTexto(tipoUsuario);

		try {
			Usuario usuario = obtenerUsuarioPorId(usuarioId);
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setEmail(email);
			usuario.setContrasena(contrasena);
			usuario.setTipoUsuario(tipoUsuario);
			usuarioDAO.actualizar(usuario);
		} catch (DatabaseException e) {
			throw new UsuarioServiceException("Error al actualizar el usuario con ID " + usuarioId, e);
		}
	}

	public void eliminarUsuario(int id) throws UsuarioNoEncontradoException, UsuarioServiceException {
		try {
			obtenerUsuarioPorId(id); // esta llamada se hace para verificar que el usuario exista
			usuarioDAO.eliminar(id);
		} catch (DatabaseException e) {
			throw new UsuarioServiceException("Error al eliminar el usuario con ID " + id, e);
		}
	}

	public Usuario autenticarUsuario(String email, String contrasena)
			throws TextoVacioException, UsuarioServiceException {
		try {
			Validador.validarTexto(email);
			Validador.validarTexto(contrasena);

			return usuarioDAO.buscarPorEmailYContrasena(email, contrasena);
		} catch (DatabaseException e) {
			throw new UsuarioServiceException("Error al autenticar el usuario", e);
		}
	}
}