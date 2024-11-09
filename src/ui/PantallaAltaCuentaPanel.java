package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import exception.CuentaBancariaServiceException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.ValorNoNumericoException;
import service.CuentaBancariaService;

public class PantallaAltaCuentaPanel extends FormularioCuentaBancariaPanel {

	private PanelManager panelManager;
	private CuentaBancariaService service;

	public PantallaAltaCuentaPanel(PanelManager panelManager, CuentaBancariaService service) {
		this.panelManager = panelManager;
		this.service = service;

		setGuardarButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarCuentaBancaria();
			}
		});

		setCancelarButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaListadoCuentas();
			}
		});

	}

	private void guardarCuentaBancaria() {
		try {
			String tipoCuentaSeleccionado = (String) comboTipoCuenta.getSelectedItem();
			service.crearCuentaBancaria(txtNumeroCuenta.getText(), Double.parseDouble(txtSaldo.getText()),
					tipoCuentaSeleccionado, Integer.parseInt(txtClienteId.getText()));

			JOptionPane.showMessageDialog(null, "Cuenta guardada exitosamente");
			panelManager.mostrarPantallaListadoCuentas();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(PantallaAltaCuentaPanel.this,
					"El saldo y el ID del cliente deben ser valores numéricos.", "Error de validación",
					JOptionPane.ERROR_MESSAGE);
		} catch (TextoVacioException ex) {
			JOptionPane.showMessageDialog(PantallaAltaCuentaPanel.this, "Todos los campos deben ser completados.",
					"Error de validación", JOptionPane.ERROR_MESSAGE);
		} catch (ValorNoNumericoException ex) {
			JOptionPane.showMessageDialog(PantallaAltaCuentaPanel.this,
					"El número de cuenta debe ser un valor numérico.", "Error de validación",
					JOptionPane.ERROR_MESSAGE);
		} catch (MenorACeroException ex) {
			JOptionPane.showMessageDialog(PantallaAltaCuentaPanel.this,
					"El saldo y el ID del cliente deben ser mayores a cero.", "Error de validación",
					JOptionPane.ERROR_MESSAGE);
		} catch (TipoCuentaBancariaInvalidaException ex) {
			JOptionPane.showMessageDialog(PantallaAltaCuentaPanel.this, "El tipo de cuenta ingresado no es válido.",
					"Error de validación", JOptionPane.ERROR_MESSAGE);
		} catch (CuentaBancariaServiceException ex) {
			JOptionPane.showMessageDialog(PantallaAltaCuentaPanel.this,
					"Error al guardar la cuenta bancaria: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(PantallaAltaCuentaPanel.this,
					"Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
