package test.model;

import exception.TipoCuentaBancariaInvalidaException;
import model.CuentaBancaria;

public class CuentaBancariaTest {

	public static void main(String[] args) {
		try {
			CuentaBancaria cuentaAhorro = new CuentaBancaria("123456789", 1000.0, CuentaBancaria.CAJA_DE_AHORRO, 1,
					"99999", "alias1");
			CuentaBancaria cuentaCorriente = new CuentaBancaria("987654321", 2000.0, CuentaBancaria.CUENTA_CORRIENTE, 2,
					"7789789", "alias2");
			CuentaBancaria cuentaDolares = new CuentaBancaria("112233445", 1500.0,
					CuentaBancaria.CAJA_DE_AHORRO_EN_DOLARES, 3, "4566", "alias3");
			CuentaBancaria cuentaFondoFima = new CuentaBancaria("556677889", 3000.0, CuentaBancaria.FONDO_FIMA, 4,
					"456456", "alias4");

			System.out.println(cuentaAhorro);
			System.out.println(cuentaCorriente);
			System.out.println(cuentaDolares);
			System.out.println(cuentaFondoFima);

		} catch (TipoCuentaBancariaInvalidaException e) {
			System.err.println("Ocurrió un error inesperado: " + e.getMessage());
		}

		try {
			new CuentaBancaria("999999999", 5000.0, "Cuenta Invalida", 5, "3212", "alias5");
		} catch (TipoCuentaBancariaInvalidaException e) {
			System.err.println("Creación de tipo de cuenta invalida: " + e.getMessage());
		}
	}

}
