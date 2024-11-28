package app;

import db.ConnectionManager;
import db.DatabaseSetup;
import exception.DatabaseException;
import service.CuentaBancariaService;
import service.MovimientoService;
import service.TarjetaService;
import service.UsuarioService;
import ui.PanelManager;

public class HomeBankingApp {

	public static void main(String[] args) {
		// La primera vez que se usa el proyecto no va a haber cuentas bancarias creadas
		// Se puede correr la clase CrearDatosIniciales para crear algunas cuentas por
		// primera vez
		try {
			DatabaseSetup.inicializarH2DB();
			DatabaseSetup.crearTablaCuentaBancaria();
			DatabaseSetup.crearTablaUsuario();
			DatabaseSetup.crearTablaTarjeta();
			DatabaseSetup.crearTablaMovimiento();

			MovimientoService movimientoService = new MovimientoService();
			CuentaBancariaService cuentaBancariaService = new CuentaBancariaService(movimientoService);
			UsuarioService usuarioService = new UsuarioService();
			TarjetaService tarjetaService = new TarjetaService(movimientoService);

			PanelManager manager = new PanelManager(cuentaBancariaService, usuarioService, tarjetaService,
					movimientoService);
			manager.showFrame();

			manager.mostrarPantallaLogin();
		} catch (DatabaseException e) {
			System.err.println("Ocurrió un error: " + e.getMessage() + " - " + e.getCause());
		} finally {
			ConnectionManager.closeConnection();
		}
	}
}
