package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import exception.TextoVacioException;
import exception.UsuarioServiceException;
import model.Usuario;
import service.UsuarioService;

public class PantallaLoginPanel extends JPanel {

	private JTextField txtEmail;
	private JPasswordField txtContrasena;
	private JButton btnLogin;

	private UsuarioService usuarioService;
	private PanelManager panelManager;

	public PantallaLoginPanel(UsuarioService usuarioService, PanelManager panelManager) {
		this.usuarioService = usuarioService;
		this.panelManager = panelManager;
		armarPantallaLogin();
	}

	private void armarPantallaLogin() {
		setLayout(new BorderLayout());

		JLabel lblTitulo = new JLabel("Inicio de Sesión");
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		add(lblTitulo, BorderLayout.NORTH);

		JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));

		JLabel lblEmail = new JLabel("Correo Electrónico:");
		txtEmail = new JTextField();
		panelFormulario.add(lblEmail);
		panelFormulario.add(txtEmail);

		JLabel lblContrasena = new JLabel("Contraseña:");
		txtContrasena = new JPasswordField();
		panelFormulario.add(lblContrasena);
		panelFormulario.add(txtContrasena);

		add(panelFormulario, BorderLayout.CENTER);

		btnLogin = new JButton("Iniciar Sesión");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarSesion();
			}
		});
		add(btnLogin, BorderLayout.SOUTH);
	}

	private void iniciarSesion() {
		String email = txtEmail.getText();
		String contrasena = new String(txtContrasena.getPassword());

		try {
			Usuario usuario = usuarioService.autenticarUsuario(email, contrasena);
			if (usuario != null) {
				panelManager.setUsuarioLogueado(usuario);
				panelManager.mostrarPantallaHome();
			} else {
				JOptionPane.showMessageDialog(this, "Correo electrónico o contraseña incorrectos.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (UsuarioServiceException e) {
			JOptionPane.showMessageDialog(this, "Error al iniciar sesión: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (TextoVacioException e) {
			JOptionPane.showMessageDialog(this,
					"Por favor completar ambos campos para iniciar sesión: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
