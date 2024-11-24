package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PantallaHomePanel extends JPanel {

	private PanelManager panelManager;

	public PantallaHomePanel(PanelManager panelManager) {
		this.panelManager = panelManager;
		armarPantallaHome();
	}

	private void armarPantallaHome() {
		setLayout(new GridLayout(3, 1, 10, 10));

		JLabel lblTitulo = new JLabel("Bienvenido al Mini Home Banking");
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		add(lblTitulo);

		JButton btnVerCuentas = new JButton("Ver Cuentas Bancarias");
		btnVerCuentas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaListadoCuentas();
			}
		});
		add(btnVerCuentas);

		JButton btnVerUsuarios = new JButton("Ver Usuarios");
		btnVerUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaListadoUsuarios();
			}
		});
		add(btnVerUsuarios);
	}
}
