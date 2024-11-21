package ui;

import javax.swing.JFrame;

import model.CuentaBancaria;
import service.CuentaBancariaService;
import service.UsuarioService;
import ui.cuenta.PantallaAltaCuentaPanel;
import ui.cuenta.PantallaListadoCuentaBancariaPanel;
import ui.cuenta.PantallaModificarCuentaPanel;
import ui.cuenta.PantallaTransferenciaPanel;
import ui.usuario.PantallaListadoUsuarioPanel;

public class PanelManager {
	private JFrame frame;

	// CuentaBancaria
	private PantallaListadoCuentaBancariaPanel pantallaListadoCuentasPanel;
	private PantallaAltaCuentaPanel pantallaAltaCuentaPanel;
	private PantallaModificarCuentaPanel pantallaModificarCuentaPanel;
	private PantallaTransferenciaPanel pantallaTransferenciaPanel;
	private CuentaBancariaService cuentaBancariaService;
	
	// Usuario
	private PantallaListadoUsuarioPanel pantallaListadoUsuarioPanel;
	private UsuarioService usuarioService;

	public PanelManager(CuentaBancariaService cuentaBancariaService, UsuarioService usuarioService) {
		this.cuentaBancariaService = cuentaBancariaService;
		this.usuarioService = usuarioService;
		armarManager();
	}

	public void armarManager() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pantallaListadoCuentasPanel = new PantallaListadoCuentaBancariaPanel(this, cuentaBancariaService);
		pantallaAltaCuentaPanel = new PantallaAltaCuentaPanel(this, cuentaBancariaService);
		pantallaListadoUsuarioPanel = new PantallaListadoUsuarioPanel(this, usuarioService);

	}

	public void showFrame() {
		frame.setVisible(true);
	}

	public void mostrarPantallaListadoCuentas() {
		pantallaListadoCuentasPanel.actualizarTabla();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(pantallaListadoCuentasPanel);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

	public void mostrarPantallaAltaCuenta() {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(pantallaAltaCuentaPanel);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

	public void mostrarPantallaModificarCuenta(CuentaBancaria cuenta) {
		pantallaModificarCuentaPanel = new PantallaModificarCuentaPanel(this, cuentaBancariaService, cuenta);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(pantallaModificarCuentaPanel);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

	public void mostrarPantallaTransferencia(CuentaBancaria cuentaOrigen) {
		frame.getContentPane().removeAll();
		pantallaTransferenciaPanel = new PantallaTransferenciaPanel(this, cuentaBancariaService, cuentaOrigen);
		frame.getContentPane().add(pantallaTransferenciaPanel);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}
	
	public void mostrarPantallaListadoUsuarios() {
		pantallaListadoUsuarioPanel.actualizarTabla();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(pantallaListadoUsuarioPanel);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

}
