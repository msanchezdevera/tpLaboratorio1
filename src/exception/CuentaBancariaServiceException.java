package exception;

public class CuentaBancariaServiceException extends Exception {
    public CuentaBancariaServiceException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public CuentaBancariaServiceException(String mensaje) {
        super(mensaje);
    }
}
