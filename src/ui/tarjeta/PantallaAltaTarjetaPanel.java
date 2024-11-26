package ui.tarjeta;

import javax.swing.JOptionPane;

import exception.TextoVacioException;
import exception.TipoTarjetaInvalidaException;
import exception.UsuarioNoEncontradoException;
import service.TarjetaService;
import ui.PanelManager;

public class PantallaAltaTarjetaPanel extends FormularioTarjetaPanel {

	private PanelManager panelManager;
	private TarjetaService service;

	public PantallaAltaTarjetaPanel(PanelManager panelManager, TarjetaService service) {
		super("Crear Tarjeta");
		this.panelManager = panelManager;
		this.service = service;
	}

	@Override
	protected void guardarAccion() {
		try {
			String tipoTarjetaSeleccionado = (String) comboTipoTarjeta.getSelectedItem();
			service.crearTarjeta(txtNumeroTarjeta.getText(), tipoTarjetaSeleccionado,
					Double.parseDouble(txtLimiteCredito.getText()), Double.parseDouble(txtSaldoUtilizado.getText()),
					Integer.parseInt(txtUsuarioId.getText()));

			JOptionPane.showMessageDialog(null, "Tarjeta guardada exitosamente");
			panelManager.mostrarPantallaListadoTarjetas();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(PantallaAltaTarjetaPanel.this,
					"El límite de crédito, saldo utilizado y el ID del usuario deben ser valores numéricos.",
					"Error de validación", JOptionPane.ERROR_MESSAGE);
		} catch (TextoVacioException ex) {
			JOptionPane.showMessageDialog(PantallaAltaTarjetaPanel.this, "Todos los campos deben ser completados.",
					"Error de validación", JOptionPane.ERROR_MESSAGE);
		} catch (TipoTarjetaInvalidaException ex) {
			JOptionPane.showMessageDialog(PantallaAltaTarjetaPanel.this, "El tipo de tarjeta ingresado no es válido.",
					"Error de validación", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(PantallaAltaTarjetaPanel.this,
					"Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected void cancelarAccion() {
		panelManager.mostrarPantallaListadoTarjetas();
	}
}