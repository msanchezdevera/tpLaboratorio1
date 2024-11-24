package ui.usuario;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Usuario;

public abstract class FormularioUsuarioPanel extends JPanel {

	protected JTextField txtNombre;
	protected JTextField txtApellido;
	protected JTextField txtEmail;
	protected JTextField txtContrasena;
	protected JComboBox<String> comboTipoUsuario;

	protected JButton btnGuardar;
	protected JButton btnCancelar;

	public FormularioUsuarioPanel(String titulo) {
		setLayout(new BorderLayout());
		armarFormulario(titulo);
	}

	private void armarFormulario(String titulo) {
		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		add(lblTitulo, BorderLayout.NORTH);

		JPanel formularioPanel = new JPanel(new GridLayout(5, 2, 5, 5));

		JLabel lblNombre = new JLabel("Nombre:");
		txtNombre = new JTextField();
		formularioPanel.add(lblNombre);
		formularioPanel.add(txtNombre);

		JLabel lblApellido = new JLabel("Apellido:");
		txtApellido = new JTextField();
		formularioPanel.add(lblApellido);
		formularioPanel.add(txtApellido);

		JLabel lblEmail = new JLabel("Email:");
		txtEmail = new JTextField();
		formularioPanel.add(lblEmail);
		formularioPanel.add(txtEmail);

		JLabel lblContrasena = new JLabel("Contraseña:");
		txtContrasena = new JTextField();
		formularioPanel.add(lblContrasena);
		formularioPanel.add(txtContrasena);

		JLabel lblTipoUsuario = new JLabel("Tipo de Usuario:");
		String[] tiposUsuario = Usuario.TIPOS_USUARIO_VALIDOS.toArray(new String[0]);
		comboTipoUsuario = new JComboBox<>(tiposUsuario);
		formularioPanel.add(lblTipoUsuario);
		formularioPanel.add(comboTipoUsuario);

		add(formularioPanel, BorderLayout.CENTER);

		JPanel botonesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		btnGuardar = new JButton("Guardar");
		btnCancelar = new JButton("Cancelar");
		botonesPanel.add(btnGuardar);
		botonesPanel.add(btnCancelar);

		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarAccion();
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelarAccion();
			}
		});

		add(botonesPanel, BorderLayout.SOUTH);
	}

	protected abstract void guardarAccion();

	protected abstract void cancelarAccion();
}
