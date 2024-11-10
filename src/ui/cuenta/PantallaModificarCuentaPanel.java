package ui.cuenta;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import exception.CuentaBancariaNoEncontradaException;
import exception.CuentaBancariaServiceException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import model.CuentaBancaria;
import service.CuentaBancariaService;
import ui.PanelManager;

public class PantallaModificarCuentaPanel extends FormularioCuentaBancariaPanel {

	private PanelManager panelManager;
	private CuentaBancariaService service;
	private CuentaBancaria cuenta;

	public PantallaModificarCuentaPanel(PanelManager panelManager, CuentaBancariaService service,
			CuentaBancaria cuenta) {
		this.panelManager = panelManager;
		this.service = service;
		this.cuenta = cuenta;
		
		txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
		txtNumeroCuenta.setEditable(false);
        txtSaldo.setText(String.valueOf(cuenta.getSaldo()));
        comboTipoCuenta.setSelectedItem(cuenta.getTipoCuenta());
        txtClienteId.setText(String.valueOf(cuenta.getClienteId()));
        txtClienteId.setEditable(false);

        setGuardarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	actualizarCuentaBancaria();
            }
        });

        setCancelarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPantallaListadoCuentas();
            }
        });
	}

	private void armarFormulario() {
		setLayout(new GridLayout(4, 2));

		JLabel lblNumeroCuenta = new JLabel("Número de Cuenta:");
		JTextField txtNumeroCuenta = new JTextField(cuenta.getNumeroCuenta());
		txtNumeroCuenta.setEditable(false);
		add(lblNumeroCuenta);
		add(txtNumeroCuenta);

		JLabel lblSaldo = new JLabel("Saldo:");
		txtSaldo = new JTextField(String.valueOf(cuenta.getSaldo()));
		add(lblSaldo);
		add(txtSaldo);

		JLabel lblTipoCuenta = new JLabel("Tipo de Cuenta:");
		String[] tiposCuenta = CuentaBancaria.TIPOS_CUENTA_VALIDOS.toArray(new String[0]);
		comboTipoCuenta = new JComboBox<>(tiposCuenta);
		comboTipoCuenta.setSelectedItem(cuenta.getTipoCuenta());
		add(lblTipoCuenta);
		add(comboTipoCuenta);

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarCuentaBancaria();
			}
		});
		add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaListadoCuentas();
			}
		});
		add(btnCancelar);
	}

	private void actualizarCuentaBancaria() {
		try {
			double nuevoSaldo = Double.parseDouble(txtSaldo.getText());
			String nuevoTipoCuenta = (String) comboTipoCuenta.getSelectedItem();
			service.actualizarCuentaBancaria(cuenta.getId(), nuevoSaldo, nuevoTipoCuenta);
			panelManager.mostrarPantallaListadoCuentas();
		} catch (MenorACeroException | TextoVacioException | TipoCuentaBancariaInvalidaException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de Validación",
					JOptionPane.ERROR_MESSAGE);
		} catch (CuentaBancariaNoEncontradaException | CuentaBancariaServiceException ex) {
			JOptionPane.showMessageDialog(null, "Error al actualizar la cuenta bancaria: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Saldo inválido. Debe ser un número.", "Error de Validación",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
