package exception;

public class UsuarioNoEncontradoException extends Exception {

	public UsuarioNoEncontradoException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

	public UsuarioNoEncontradoException(String mensaje) {
		super(mensaje);
	}

}
