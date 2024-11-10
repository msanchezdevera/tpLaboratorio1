package ui;

import javax.swing.JFrame;

import model.CuentaBancaria;
import service.CuentaBancariaService;
import ui.cuenta.PantallaAltaCuentaPanel;
import ui.cuenta.PantallaListadoCuentaBancariaPanel;
import ui.cuenta.PantallaModificarCuentaPanel;

public class PanelManager {
	private JFrame frame;
	private PantallaListadoCuentaBancariaPanel pantallaListadoCuentasPanel;
	private PantallaAltaCuentaPanel pantallaAltaCuentaPanel;
	private PantallaModificarCuentaPanel pantallaModificarCuentaPanel;
	private CuentaBancariaService cuentaBancariaService;

	public PanelManager(CuentaBancariaService cuentaBancariaService) {
		this.cuentaBancariaService = cuentaBancariaService;
		armarManager();
	}

	public void armarManager() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pantallaListadoCuentasPanel = new PantallaListadoCuentaBancariaPanel(this, cuentaBancariaService);
		pantallaAltaCuentaPanel = new PantallaAltaCuentaPanel(this, cuentaBancariaService);
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

}
