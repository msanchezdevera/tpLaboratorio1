package test;

import db.DatabaseSetup;
import exception.CuentaBancariaServiceException;
import exception.DatabaseException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.ValorNoNumericoException;
import model.CuentaBancaria;
import service.CuentaBancariaService;

public class CrearCuentasBancarias {

	public static void main(String[] args) {
		try {
			DatabaseSetup.inicializarH2DB();
			DatabaseSetup.crearTablaCuentaBancaria();

			CuentaBancariaService service = new CuentaBancariaService();

			System.out.println("Creando cuentas bancarias iniciales...");

			service.crearCuentaBancaria("123456", 5000.0, CuentaBancaria.CAJA_DE_AHORRO, 1, "CBU001", "Alias1");
			service.crearCuentaBancaria("654321", 10000.0, CuentaBancaria.CUENTA_CORRIENTE, 2, "CBU002", "Alias2");
			service.crearCuentaBancaria("789123", 2000.0, CuentaBancaria.CAJA_DE_AHORRO_EN_DOLARES, 3, "CBU003",
					"Alias3");
			service.crearCuentaBancaria("321789", 8000.0, CuentaBancaria.FONDO_FIMA, 4, "CBU004", "Alias4");

			System.out.println("Cuentas bancarias creadas exitosamente.");
		} catch (DatabaseException | TipoCuentaBancariaInvalidaException | TextoVacioException | MenorACeroException
				| ValorNoNumericoException | CuentaBancariaServiceException e) {
			System.err.println("Error al crear cuentas bancarias: " + e.getMessage());
		}
	}
}
