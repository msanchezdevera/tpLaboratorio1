package test;

import db.DatabaseSetup;
import exception.CuentaBancariaServiceException;
import exception.DatabaseException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.UsuarioNoEncontradoException;
import exception.UsuarioServiceException;
import exception.ValorNoNumericoException;
import model.CuentaBancaria;
import model.Usuario;
import service.CuentaBancariaService;
import service.UsuarioService;

public class CrearDatosIniciales {

	public static void main(String[] args) {
		try {
			DatabaseSetup.inicializarH2DB();
			DatabaseSetup.crearTablaCuentaBancaria();
			DatabaseSetup.crearTablaUsuario();

			CuentaBancariaService cuentaBancariaService = new CuentaBancariaService();
			UsuarioService usuarioService = new UsuarioService();

			System.out.println("Creando usuarios iniciales...");

			Usuario usuario1 = usuarioService.crearUsuario("Juan", "Pérez", "juan.perez@example.com", "password123",
					Usuario.CLIENTE);
			Usuario usuario2 = usuarioService.crearUsuario("María", "González", "maria.gonzalez@example.com",
					"password456", Usuario.CLIENTE);
			Usuario usuario3 = usuarioService.crearUsuario("Administrador", "Banco", "admin@example.com", "adminpass",
					Usuario.ADMIN);

			System.out.println("Usuarios creados exitosamente.");

			System.out.println("Creando cuentas bancarias iniciales...");

			cuentaBancariaService.crearCuentaBancaria("123456", 5000.0, CuentaBancaria.CAJA_DE_AHORRO, usuario1.getId(),
					"CBU001", "Alias1");
			cuentaBancariaService.crearCuentaBancaria("654321", 10000.0, CuentaBancaria.CUENTA_CORRIENTE,
					usuario2.getId(), "CBU002", "Alias2");
			cuentaBancariaService.crearCuentaBancaria("789123", 2000.0, CuentaBancaria.CAJA_DE_AHORRO_EN_DOLARES,
					usuario3.getId(), "CBU003", "Alias3");
			cuentaBancariaService.crearCuentaBancaria("321789", 8000.0, CuentaBancaria.FONDO_FIMA, usuario1.getId(),
					"CBU004", "Alias4");

			System.out.println("Cuentas bancarias creadas exitosamente.");
		} catch (UsuarioNoEncontradoException | DatabaseException | TipoCuentaBancariaInvalidaException
				| TextoVacioException | MenorACeroException | ValorNoNumericoException | CuentaBancariaServiceException
				| UsuarioServiceException e) {
			System.err.println("Error al crear datos iniciales: " + e.getMessage());
		}
	}
}