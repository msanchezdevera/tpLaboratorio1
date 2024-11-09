package validador;

import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.ValorFueraDeRangoException;
import exception.ValorNoNumericoException;

public class Validador {

	public static void validarTexto(String texto) throws TextoVacioException {
		if (texto.length() == 0) {
			throw new TextoVacioException("El texto es inválido");
		}
	}

	public static void validarRango(int numero, int minimo, int maximo) throws ValorFueraDeRangoException {
		if (numero < minimo || numero > maximo) {
			throw new ValorFueraDeRangoException(
					"El valor " + numero + " está fuera del rango permitido [" + minimo + ", " + maximo + "].");
		}
	}

	public static void validarEsNumero(String texto) throws ValorNoNumericoException {
		try {
			Integer.parseInt(texto);
		} catch (NumberFormatException e) {
			throw new ValorNoNumericoException("El valor " + texto + " no es un número válido.");
		}
	}

	public static void validarNumeroMayorACero(int numero) throws MenorACeroException {
		if (numero < 0) {
			throw new MenorACeroException("El número " + numero + " es negativo.");
		}
	}

	public static void validarNumeroMayorACero(double numero) throws MenorACeroException {
		if (numero < 0) {
			throw new MenorACeroException("El número " + numero + " es negativo.");
		}
	}
}
