package exception;

public class DatabaseException extends Exception {

	public DatabaseException(String mensaje) {
		super(mensaje);
	}

	public DatabaseException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
