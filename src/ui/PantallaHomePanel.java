package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Usuario;

public class PantallaHomePanel extends JPanel {

	private PanelManager panelManager;
	private Usuario usuario;

	public PantallaHomePanel(PanelManager panelManager, Usuario usuario) {
		this.panelManager = panelManager;
		this.usuario = usuario;
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

		} else {
			JButton btnMisCuentas = new JButton("Mis Cuentas");
			btnMisCuentas.addActionListener(e -> panelManager.mostrarPantallaMisCuentas(usuario));
			add(btnMisCuentas);

			JButton btnMisTarjetas = new JButton("Mis Tarjetas");
			btnMisTarjetas.addActionListener(e -> panelManager.mostrarPantallaListadoTarjetas());
			add(btnMisTarjetas);
		}
	}
}
