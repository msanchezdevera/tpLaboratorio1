package ui.cuenta;

import javax.swing.JOptionPane;

import exception.CuentaBancariaNoEncontradaException;
import exception.CuentaBancariaServiceException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.UsuarioNoEncontradoException;
import model.CuentaBancaria;
import service.CuentaBancariaService;
import ui.PanelManager;

public class PantallaModificarCuentaPanel extends FormularioCuentaBancariaPanel {

	private PanelManager panelManager;
	private CuentaBancariaService service;
	private CuentaBancaria cuenta;

	public PantallaModificarCuentaPanel(PanelManager panelManager, CuentaBancariaService service,
			CuentaBancaria cuenta) {
		super("Modificar Cuenta Bancaria");

		this.panelManager = panelManager;
		this.service = service;
		this.cuenta = cuenta;

		txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
		txtNumeroCuenta.setEditable(false);
		txtSaldo.setText(String.valueOf(cuenta.getSaldo()));
		comboTipoCuenta.setSelectedItem(cuenta.getTipoCuenta());
		txtClienteId.setText(String.valueOf(cuenta.getUsuario().getId()));
		txtClienteId.setEditable(false);
		txtCbu.setText(cuenta.getCbu());
		txtAlias.setText(cuenta.getAlias());
	}

	@Override
	protected void guardarAccion() {
		try {
			double nuevoSaldo = Double.parseDouble(txtSaldo.getText());
			String nuevoTipoCuenta = (String) comboTipoCuenta.getSelectedItem();
			service.actualizarCuentaBancaria(cuenta.getId(), nuevoSaldo, nuevoTipoCuenta, cuenta.getUsuario().getId(),
					txtCbu.getText(), txtAlias.getText());
			JOptionPane.showMessageDialog(null, "Cuenta actualizada exitosamente");
			panelManager.mostrarPantallaListadoCuentas();
		} catch (MenorACeroException | TextoVacioException | TipoCuentaBancariaInvalidaException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
		} catch (CuentaBancariaNoEncontradaException | CuentaBancariaServiceException ex) {
			JOptionPane.showMessageDialog(null, "Error al actualizar la cuenta bancaria: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Saldo inválido. Debe ser un número.", "Error de Validación",
					JOptionPane.ERROR_MESSAGE);
		} catch (UsuarioNoEncontradoException e) {
			JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error de Validación",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected void cancelarAccion() {
		panelManager.mostrarPantallaListadoCuentas();
	}
}
