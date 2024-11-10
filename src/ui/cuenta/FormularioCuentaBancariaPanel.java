package ui.cuenta;

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

import model.CuentaBancaria;

public abstract class FormularioCuentaBancariaPanel extends JPanel {

	protected JTextField txtNumeroCuenta;
	protected JTextField txtSaldo;
	protected JComboBox<String> comboTipoCuenta;
	protected JTextField txtClienteId;
	protected JTextField txtCbu;
	protected JTextField txtAlias;
	protected JButton btnGuardar;
	protected JButton btnCancelar;

	public FormularioCuentaBancariaPanel(String titulo) {
		setLayout(new BorderLayout());
		armarFormulario(titulo);
	}

	private void armarFormulario(String titulo) {
		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		add(lblTitulo, BorderLayout.NORTH);

		JPanel formularioPanel = new JPanel(new GridLayout(7, 2, 5, 5));

		JLabel lblNumeroCuenta = new JLabel("Número de Cuenta:");
		txtNumeroCuenta = new JTextField();
		formularioPanel.add(lblNumeroCuenta);
		formularioPanel.add(txtNumeroCuenta);

		JLabel lblSaldo = new JLabel("Saldo:");
		txtSaldo = new JTextField();
		formularioPanel.add(lblSaldo);
		formularioPanel.add(txtSaldo);

		JLabel lblTipoCuenta = new JLabel("Tipo de Cuenta:");
		String[] tiposCuenta = CuentaBancaria.TIPOS_CUENTA_VALIDOS.toArray(new String[0]);
		comboTipoCuenta = new JComboBox<>(tiposCuenta);
		formularioPanel.add(lblTipoCuenta);
		formularioPanel.add(comboTipoCuenta);

		JLabel lblClienteId = new JLabel("Cliente ID:");
		txtClienteId = new JTextField();
		formularioPanel.add(lblClienteId);
		formularioPanel.add(txtClienteId);

		JLabel lblCbu = new JLabel("CBU:");
		txtCbu = new JTextField();
		formularioPanel.add(lblCbu);
		formularioPanel.add(txtCbu);

		JLabel lblAlias = new JLabel("Alias:");
		txtAlias = new JTextField();
		formularioPanel.add(lblAlias);
		formularioPanel.add(txtAlias);

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
