package ui.cuenta;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import exception.CuentaBancariaNoEncontradaException;
import exception.CuentaBancariaServiceException;
import exception.MenorACeroException;
import model.CuentaBancaria;
import service.CuentaBancariaService;
import ui.PanelManager;

public class PantallaTransferenciaPanel extends JPanel {

	private PanelManager panelManager;
	private CuentaBancariaService service;
	private CuentaBancaria cuentaOrigen;

	private JTextField txtCuentaDestino;
	private JTextField txtMonto;
	private JButton btnTransferir;
	private JButton btnCancelar;

	public PantallaTransferenciaPanel(PanelManager panelManager, CuentaBancariaService service,
			CuentaBancaria cuentaOrigen) {
		this.panelManager = panelManager;
		this.service = service;
		this.cuentaOrigen = cuentaOrigen;
		armarFormulario();
	}

	private void armarFormulario() {
		setLayout(new GridLayout(4, 2));

		JLabel lblCuentaOrigen = new JLabel("Cuenta Origen:");
		JTextField txtCuentaOrigen = new JTextField(cuentaOrigen.getNumeroCuenta());
		txtCuentaOrigen.setEditable(false);
		add(lblCuentaOrigen);
		add(txtCuentaOrigen);

		JLabel lblCuentaDestino = new JLabel("ID, CBU o Alias Cuenta Destino:");
		txtCuentaDestino = new JTextField();
		add(lblCuentaDestino);
		add(txtCuentaDestino);

		JLabel lblMonto = new JLabel("Monto a Transferir:");
		txtMonto = new JTextField();
		add(lblMonto);
		add(txtMonto);

		btnTransferir = new JButton("Transferir");
		btnTransferir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				realizarTransferencia();
			}
		});
		add(btnTransferir);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaListadoCuentas();
			}
		});
		add(btnCancelar);
	}

	private void realizarTransferencia() {
		try {
			String cuentaDestinoInput = txtCuentaDestino.getText();
			double monto = Double.parseDouble(txtMonto.getText());
			service.realizarTransferencia(cuentaOrigen, cuentaDestinoInput, monto);
			JOptionPane.showMessageDialog(null, "Transferencia realizada exitosamente");
			panelManager.mostrarPantallaListadoCuentas();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(PantallaTransferenciaPanel.this, "El monto debe ser un valor numérico.",
					"Error de validación", JOptionPane.ERROR_MESSAGE);
		} catch (MenorACeroException ex) {
			JOptionPane.showMessageDialog(PantallaTransferenciaPanel.this, "El monto debe ser mayor a cero.",
					"Error de validación", JOptionPane.ERROR_MESSAGE);
		} catch (CuentaBancariaNoEncontradaException ex) {
			JOptionPane.showMessageDialog(PantallaTransferenciaPanel.this, "La cuenta destino no fue encontrada.",
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (CuentaBancariaServiceException ex) {
			JOptionPane.showMessageDialog(PantallaTransferenciaPanel.this,
					"Error al realizar la transferencia: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(PantallaTransferenciaPanel.this,
					"Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
