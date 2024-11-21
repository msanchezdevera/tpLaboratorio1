package exception;

public class UsuarioServiceException extends Exception {
	
	public UsuarioServiceException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

	public UsuarioServiceException(String mensaje) {
		super(mensaje);
	}

}
