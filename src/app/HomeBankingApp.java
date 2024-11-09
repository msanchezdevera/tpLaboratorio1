package app;

import db.ConnectionManager;
import db.DatabaseSetup;
import exception.DatabaseException;
import service.CuentaBancariaService;
import ui.PanelManager;

public class HomeBankingApp {

	public static void main(String[] args) {
		try {
			DatabaseSetup.inicializarH2DB();
			DatabaseSetup.crearTablaCuentaBancaria();

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
