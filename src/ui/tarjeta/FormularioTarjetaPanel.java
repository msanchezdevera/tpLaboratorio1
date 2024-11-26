package ui.tarjeta;

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

import model.Tarjeta;

public abstract class FormularioTarjetaPanel extends JPanel {

	protected JTextField txtNumeroTarjeta;
	protected JComboBox<String> comboTipoTarjeta;
	protected JTextField txtLimiteCredito;
	protected JTextField txtSaldoUtilizado;
	protected JTextField txtUsuarioId;
	protected JButton btnGuardar;
	protected JButton btnCancelar;

	public FormularioTarjetaPanel(String titulo) {
		setLayout(new BorderLayout());
		armarFormulario(titulo);
	}

	private void armarFormulario(String titulo) {
		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		add(lblTitulo, BorderLayout.NORTH);

		JPanel formularioPanel = new JPanel(new GridLayout(5, 2, 5, 5));

		JLabel lblNumeroTarjeta = new JLabel("Número de Tarjeta:");
		txtNumeroTarjeta = new JTextField();
		formularioPanel.add(lblNumeroTarjeta);
		formularioPanel.add(txtNumeroTarjeta);

		JLabel lblTipoTarjeta = new JLabel("Tipo de Tarjeta:");
		String[] tiposTarjeta = Tarjeta.TIPOS_TARJETA_VALIDOS.toArray(new String[0]);
		comboTipoTarjeta = new JComboBox<>(tiposTarjeta);
		formularioPanel.add(lblTipoTarjeta);
		formularioPanel.add(comboTipoTarjeta);

		JLabel lblLimiteCredito = new JLabel("Límite de Crédito:");
		txtLimiteCredito = new JTextField();
		formularioPanel.add(lblLimiteCredito);
		formularioPanel.add(txtLimiteCredito);

		JLabel lblSaldoUtilizado = new JLabel("Saldo Utilizado:");
		txtSaldoUtilizado = new JTextField();
		formularioPanel.add(lblSaldoUtilizado);
		formularioPanel.add(txtSaldoUtilizado);

		JLabel lblUsuarioId = new JLabel("Usuario ID:");
		txtUsuarioId = new JTextField();
		formularioPanel.add(lblUsuarioId);
		formularioPanel.add(txtUsuarioId);

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