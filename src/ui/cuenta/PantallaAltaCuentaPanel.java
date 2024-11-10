package ui.cuenta;

import javax.swing.JOptionPane;

import exception.CuentaBancariaServiceException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.ValorNoNumericoException;
import service.CuentaBancariaService;
import ui.PanelManager;

public class PantallaAltaCuentaPanel extends FormularioCuentaBancariaPanel {

	private PanelManager panelManager;
	private CuentaBancariaService service;

	public PantallaAltaCuentaPanel(PanelManager panelManager, CuentaBancariaService service) {
		super("Crear Cuenta Bancaria");
		this.panelManager = panelManager;
		this.service = service;
	}

	@Override
	protected void guardarAccion() {
		try {
			String tipoCuentaSeleccionado = (String) comboTipoCuenta.getSelectedItem();
			service.crearCuentaBancaria(txtNumeroCuenta.getText(), Double.parseDouble(txtSaldo.getText()),
					tipoCuentaSeleccionado, Integer.parseInt(txtClienteId.getText()), txtCbu.getText(),
					txtAlias.getText());

			JOptionPane.showMessageDialog(null, "Cuenta guardada exitosamente");
			panelManager.mostrarPantallaListadoCuentas();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(PantallaAltaCuentaPanel.this,
					"El saldo, el ID del cliente, el CBU y el Alias deben ser valores numéricos donde corresponda.",
					"Error de validación", JOptionPane.ERROR_MESSAGE);
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

	@Override
	protected void cancelarAccion() {
		panelManager.mostrarPantallaListadoCuentas();
	}

}
