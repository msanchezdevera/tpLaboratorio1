package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exception.CuentaBancariaServiceException;
import exception.MenorACeroException;
import exception.TipoMovimientoInvalidoException;
import model.Usuario;
import service.CuentaBancariaService;
import service.MovimientoService;

public class PantallaHomePanel extends JPanel {

	private PanelManager panelManager;
	private CuentaBancariaService cuentaBancariaService;
	private Usuario usuario;

	public PantallaHomePanel(PanelManager panelManager, CuentaBancariaService cuentaBancariaService, Usuario usuario) {
		this.panelManager = panelManager;
		this.usuario = usuario;
		this.cuentaBancariaService = cuentaBancariaService;
		armarPantallaHome();

	}

	private void armarPantallaHome() {
		setLayout(new GridLayout(3, 1, 10, 10));

		JLabel lblTitulo = new JLabel("Bienvenido al Mini Home Banking");
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		add(lblTitulo);

		if (usuario.esAdmin()) {
			JButton btnVerUsuarios = new JButton("Listado de Usuarios");
			btnVerUsuarios.addActionListener(e -> panelManager.mostrarPantallaListadoUsuarios());
			add(btnVerUsuarios);

			JButton btnVerCuentas = new JButton("Listado de Cuentas");
			btnVerCuentas.addActionListener(e -> panelManager.mostrarPantallaListadoCuentas());
			add(btnVerCuentas);

			JButton btnVerTarjetas = new JButton("Listado de Tarjetas");
			btnVerTarjetas.addActionListener(e -> panelManager.mostrarPantallaListadoTarjetas());
			add(btnVerTarjetas);

			JButton btnGenerarIntereses = new JButton("Generar Intereses para Cuentas");
			btnGenerarIntereses.addActionListener(e -> generarIntereses());
			add(btnGenerarIntereses);
			
			JButton btnAuditoria = new JButton("Reporte de Auditoría");
			btnAuditoria.addActionListener(e -> panelManager.mostrarPantallaAuditoria());
			add(btnAuditoria);
		} else {
			JButton btnMisCuentas = new JButton("Mis Cuentas");
			btnMisCuentas.addActionListener(e -> panelManager.mostrarPantallaMisCuentas(usuario));
			add(btnMisCuentas);

			JButton btnMisTarjetas = new JButton("Mis Tarjetas");
			btnMisTarjetas.addActionListener(e -> panelManager.mostrarPantallaListadoTarjetas());
			add(btnMisTarjetas);
		}
	}

	private void generarIntereses() {
		try {
			cuentaBancariaService.generarInteresesParaCuentas();
			JOptionPane.showMessageDialog(this, "Intereses generados exitosamente para las cuentas aplicables.",
					"Generar Intereses", JOptionPane.INFORMATION_MESSAGE);
		} catch (CuentaBancariaServiceException | TipoMovimientoInvalidoException | MenorACeroException e) {
			JOptionPane.showMessageDialog(this, "Error al generar intereses: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
