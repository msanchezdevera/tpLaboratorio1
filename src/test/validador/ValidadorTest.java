package test.validador;

import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.ValorFueraDeRangoException;
import exception.ValorNoNumericoException;
import validador.Validador;

public class ValidadorTest {
	public static void main(String[] args) {
		try {
			Validador.validarTexto("");
		} catch (TextoVacioException e) {
			System.out.println(e.getMessage());
		}

		try {
			Validador.validarRango(150, 0, 100);
		} catch (ValorFueraDeRangoException e) {
			System.out.println(e.getMessage());
		}

		try {
			Validador.validarEsNumero("11aaa22");
		} catch (ValorNoNumericoException e) {
			System.out.println(e.getMessage());
		}

		try {
			Validador.validarNumeroMayorACero(-5);
		} catch (MenorACeroException e) {
			System.out.println(e.getMessage());
		}

		try {
			Validador.validarTexto("Hola mundo");
			Validador.validarRango(50, 0, 100);
			Validador.validarEsNumero("123");
			Validador.validarNumeroMayorACero(10);
			System.out.println("Todas las validaciones pasaron correctamente.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
