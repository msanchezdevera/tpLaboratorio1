package app;

import db.ConnectionManager;
import db.DatabaseSetup;
import exception.DatabaseException;
import service.CuentaBancariaService;
import ui.PanelManager;

public class HomeBankingApp {

	public static void main(String[] args) {
		// La primera vez que se usa el proyecto no va a haber cuentas bancarias creadas
		// Se puede correr la clase CrearCuentasBancarias para crear algunas cuentas por
		// primera vez
		try {
			DatabaseSetup.inicializarH2DB();
			DatabaseSetup.crearTablaCuentaBancaria();
			DatabaseSetup.crearTablaUsuario();

			CuentaBancariaService service = new CuentaBancariaService();

			PanelManager manager = new PanelManager(service);
			manager.showFrame();
			manager.mostrarPantallaListadoCuentas();
		} catch (DatabaseException e) {
			System.err.println("Ocurrió un error: " + e.getMessage() + " - " + e.getCause());
		} finally {
			ConnectionManager.closeConnection();
		}
	}
}
