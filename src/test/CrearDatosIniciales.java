package test;

import db.DatabaseSetup;
import exception.CuentaBancariaServiceException;
import exception.DatabaseException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.TipoMovimientoInvalidoException;
import exception.TipoTarjetaInvalidaException;
import exception.UsuarioNoEncontradoException;
import exception.UsuarioServiceException;
import exception.ValorNoNumericoException;
import exception.TarjetaServiceException;
import exception.MovimientoServiceException;
import model.CuentaBancaria;
import model.Movimiento;
import model.Usuario;
import model.Tarjeta;
import service.CuentaBancariaService;
import service.UsuarioService;
import service.TarjetaService;
import service.MovimientoService;
import java.util.Date;

public class CrearDatosIniciales {

	public static void main(String[] args) {
		try {
			DatabaseSetup.inicializarH2DB();
			DatabaseSetup.dropAllTables();
			DatabaseSetup.crearTablaCuentaBancaria();
			DatabaseSetup.crearTablaUsuario();
			DatabaseSetup.crearTablaTarjeta();
			DatabaseSetup.crearTablaMovimiento();

			CuentaBancariaService cuentaBancariaService = new CuentaBancariaService();
			UsuarioService usuarioService = new UsuarioService();
			TarjetaService tarjetaService = new TarjetaService();
			MovimientoService movimientoService = new MovimientoService();

			System.out.println("Creando usuarios iniciales...");

			Usuario usuario1 = usuarioService.crearUsuario("Juan", "Pérez", "juan@example.com", "password123",
					Usuario.CLIENTE);
			Usuario usuario2 = usuarioService.crearUsuario("María", "González", "maria@example.com", "password456",
					Usuario.CLIENTE);
			Usuario usuario3 = usuarioService.crearUsuario("Administrador", "Banco", "admin@admin.com", "admin",
					Usuario.ADMIN);

			System.out.println("Usuarios creados exitosamente.");

			System.out.println("Creando cuentas bancarias iniciales...");

			CuentaBancaria cuenta1 = cuentaBancariaService.crearCuentaBancaria("123456", 5000.0,
					CuentaBancaria.CAJA_DE_AHORRO, usuario1.getId(), "CBU001", "Alias1");
			CuentaBancaria cuenta2 = cuentaBancariaService.crearCuentaBancaria("654321", 10000.0,
					CuentaBancaria.CUENTA_CORRIENTE, usuario2.getId(), "CBU002", "Alias2");
			CuentaBancaria cuenta3 = cuentaBancariaService.crearCuentaBancaria("789123", 2000.0,
					CuentaBancaria.CAJA_DE_AHORRO_EN_DOLARES, usuario3.getId(), "CBU003", "Alias3");
			CuentaBancaria cuenta4 = cuentaBancariaService.crearCuentaBancaria("321789", 8000.0,
					CuentaBancaria.FONDO_FIMA, usuario1.getId(), "CBU004", "Alias4");

			System.out.println("Cuentas bancarias creadas exitosamente.");

			System.out.println("Creando tarjetas iniciales...");

			Tarjeta tarjeta1 = tarjetaService.crearTarjeta("1111222233334444", Tarjeta.TIPO_CREDITO, 20000.0, 0.0,
					usuario1.getId());
			Tarjeta tarjeta2 = tarjetaService.crearTarjeta("5555666677778888", Tarjeta.TIPO_DEBITO, 10000.0, 0.0,
					usuario2.getId());
			Tarjeta tarjeta3 = tarjetaService.crearTarjeta("9999000011112222", Tarjeta.TIPO_CREDITO, 15000.0, 5000.0,
					usuario3.getId());

			System.out.println("Tarjetas creadas exitosamente.");

			System.out.println("Creando movimientos iniciales...");

			// Crear un movimiento de depósito para la cuenta de Juan
			Movimiento deposito = new Movimiento("Depósito", new Date(), 1000.0, "Depósito inicial", cuenta1.getId(),
					null, null, usuario1.getId(), cuenta1.getSaldo(), cuenta1.getSaldo() + 1000.0);
			movimientoService.registrarMovimiento(deposito);

			// Crear un movimiento de pago de tarjeta para la tarjeta de María
			Movimiento pagoTarjeta = new Movimiento("Pago de Tarjeta", new Date(), 500.0, "Pago de tarjeta de crédito",
					cuenta1.getId(), null, tarjeta2.getId(), usuario2.getId(), tarjeta2.getSaldoUtilizado(),
					tarjeta2.getSaldoUtilizado() - 500.0);
			movimientoService.registrarMovimiento(pagoTarjeta);

			System.out.println("Movimientos creados exitosamente.");

		} catch (UsuarioNoEncontradoException | DatabaseException | TipoCuentaBancariaInvalidaException
				| TextoVacioException | MenorACeroException | ValorNoNumericoException | CuentaBancariaServiceException
				| UsuarioServiceException | TarjetaServiceException | TipoTarjetaInvalidaException
				| MovimientoServiceException | TipoMovimientoInvalidoException e) {
			System.err.println("Error al crear datos iniciales: " + e.getMessage());
		}
	}
}
