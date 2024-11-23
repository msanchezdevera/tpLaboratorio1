package app;

import javax.swing.JOptionPane;

import db.ConnectionManager;
import db.DatabaseSetup;
import exception.DatabaseException;
import service.CuentaBancariaService;
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

			CuentaBancariaService cuentaBancariaService = new CuentaBancariaService();
			UsuarioService usuarioService = new UsuarioService();

			PanelManager manager = new PanelManager(cuentaBancariaService, usuarioService);
			manager.showFrame();
			
			String[] opciones = {"Usuarios", "Cuentas Bancarias"};
            int eleccion = JOptionPane.showOptionDialog(null, "Seleccione una opción para comenzar:",
                    "Inicio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            if (eleccion == 0) {
                manager.mostrarPantallaListadoUsuarios();
            } else {
                manager.mostrarPantallaListadoCuentas();
            }
		} catch (DatabaseException e) {
			System.err.println("Ocurrió un error: " + e.getMessage() + " - " + e.getCause());
		} finally {
			ConnectionManager.closeConnection();
		}
	}
}
