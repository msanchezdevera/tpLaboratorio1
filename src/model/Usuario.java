package model;

import java.util.HashSet;
import java.util.Set;

public class Usuario {

	public static final String ADMIN = "ADMIN";
	public static final String CLIENTE = "CLIENTE";
	public static final Set<String> TIPOS_USUARIO_VALIDOS = new HashSet<>();

	static {
		TIPOS_USUARIO_VALIDOS.add(ADMIN);
		TIPOS_USUARIO_VALIDOS.add(CLIENTE);
	}

	private Integer id;
	private String nombre;
	private String apellido;
	private String email;
	private String contrasena;
	private String tipoUsuario; // Puede ser ADMIN o CLIENTE.

	public Usuario(String nombre, String apellido, String email, String contrasena, String tipoUsuario)
			throws IllegalArgumentException {
		validarTipoUsuario(tipoUsuario);
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.contrasena = contrasena;
		this.tipoUsuario = tipoUsuario;
	}

	public Usuario(int id, String nombre, String apellido, String email, String contrasena, String tipoUsuario)
			throws IllegalArgumentException {
		validarTipoUsuario(tipoUsuario);
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.contrasena = contrasena;
		this.tipoUsuario = tipoUsuario;
	}

	private void validarTipoUsuario(String tipoUsuario) {
		if (!TIPOS_USUARIO_VALIDOS.contains(tipoUsuario)) {
			throw new IllegalArgumentException("Tipo de usuario no válido: " + tipoUsuario);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		validarTipoUsuario(tipoUsuario);
		this.tipoUsuario = tipoUsuario;
	}

	@Override
	public String toString() {
		return "Usuario{id=" + id + ", nombre='" + nombre + "', apellido='" + apellido + "', email='" + email
				+ "', tipoUsuario='" + tipoUsuario + "'}";
	}
}
