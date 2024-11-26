package ui;

import javax.swing.JFrame;

import model.CuentaBancaria;
import model.Usuario;
import service.CuentaBancariaService;
import service.UsuarioService;
import ui.cuenta.PantallaAltaCuentaPanel;
import ui.cuenta.PantallaListadoCuentaBancariaPanel;
import ui.cuenta.PantallaModificarCuentaPanel;
import ui.cuenta.PantallaTransferenciaPanel;
import ui.usuario.PantallaAltaUsuarioPanel;
import ui.usuario.PantallaListadoUsuarioPanel;
import ui.usuario.PantallaModificarUsuarioPanel;

public class PanelManager {
	private JFrame frame;

	private PantallaHomePanel pantallaHomePanel;
	private PantallaLoginPanel pantallaLoginPanel;

	// CuentaBancaria
	private PantallaListadoCuentaBancariaPanel pantallaListadoCuentasPanel;
	private PantallaAltaCuentaPanel pantallaAltaCuentaPanel;
	private PantallaModificarCuentaPanel pantallaModificarCuentaPanel;
	private PantallaTransferenciaPanel pantallaTransferenciaPanel;
	private CuentaBancariaService cuentaBancariaService;

	// Usuario
	private PantallaListadoUsuarioPanel pantallaListadoUsuarioPanel;
	private PantallaModificarUsuarioPanel pantallaModificarUsuarioPanel;
	private PantallaAltaUsuarioPanel pantallaAltaUsuarioPanel;
	private UsuarioService usuarioService;

	private Usuario usuarioLogueado;

	public PanelManager(CuentaBancariaService cuentaBancariaService, UsuarioService usuarioService) {
		this.cuentaBancariaService = cuentaBancariaService;
		this.usuarioService = usuarioService;
		armarManager();
	}

	public void armarManager() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pantallaLoginPanel = new PantallaLoginPanel(usuarioService, this);
		pantallaAltaCuentaPanel = new PantallaAltaCuentaPanel(this, cuentaBancariaService);
		pantallaListadoUsuarioPanel = new PantallaListadoUsuarioPanel(this, usuarioService);

	}

	public void showFrame() {
		frame.setVisible(true);
		mostrarPantallaLogin();
	}

	public void mostrarPantallaLogin() {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(pantallaLoginPanel);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

	public void setUsuarioLogueado(Usuario usuario) {
		this.usuarioLogueado = usuario;
	}

	public void mostrarPantallaHome() {
		pantallaHomePanel = new PantallaHomePanel(this, usuarioLogueado);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(pantallaHomePanel);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

	public void mostrarPantallaListadoCuentas() {
		pantallaListadoCuentasPanel = new PantallaListadoCuentaBancariaPanel(this, cuentaBancariaService,
				usuarioLogueado);
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

	public void mostrarPantallaAltaUsuario() {
		pantallaAltaUsuarioPanel = new PantallaAltaUsuarioPanel(this, usuarioService);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(pantallaAltaUsuarioPanel);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

	public void mostrarPantallaModificarUsuario(Usuario usuario) {
		pantallaModificarUsuarioPanel = new PantallaModificarUsuarioPanel(this, usuarioService, usuario);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(pantallaModificarUsuarioPanel);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

	public void mostrarPantallaMisCuentas(Usuario usuario) {
		PantallaListadoCuentaBancariaPanel pantallaMisCuentas = new PantallaListadoCuentaBancariaPanel(this,
				cuentaBancariaService, usuario);
		pantallaMisCuentas.actualizarTabla();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(pantallaMisCuentas);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

	public void mostrarPantallaMisTarjetas(Usuario usuario) {
		/*
		 * PantallaListadoTarjetaPanel pantallaMisTarjetas = new
		 * PantallaListadoTarjetaPanel(this, usuarioService, usuario);
		 * pantallaMisTarjetas.actualizarTabla(); frame.getContentPane().removeAll();
		 * frame.getContentPane().add(pantallaMisTarjetas);
		 * frame.getContentPane().validate(); frame.getContentPane().repaint();
		 */
	}

	public void mostrarPantallaListadoTarjetas() {
		/*
		 * PantallaListadoTarjetaPanel pantallaListadoTarjetas = new
		 * PantallaListadoTarjetaPanel(this, usuarioService);
		 * pantallaListadoTarjetas.actualizarTabla();
		 * frame.getContentPane().removeAll();
		 * frame.getContentPane().add(pantallaListadoTarjetas);
		 * frame.getContentPane().validate(); frame.getContentPane().repaint();
		 */
	}

}
