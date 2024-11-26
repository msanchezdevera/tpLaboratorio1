package ui.tarjeta;

import javax.swing.JOptionPane;

import exception.TextoVacioException;
import exception.TipoTarjetaInvalidaException;
import model.Tarjeta;
import service.TarjetaService;
import ui.PanelManager;

public class PantallaModificarTarjetaPanel extends FormularioTarjetaPanel {

	private PanelManager panelManager;
	private TarjetaService service;
	private Tarjeta tarjeta;

	public PantallaModificarTarjetaPanel(PanelManager panelManager, TarjetaService service, Tarjeta tarjeta) {
		super("Modificar Tarjeta");
		this.panelManager = panelManager;
		this.service = service;
		this.tarjeta = tarjeta;

		txtNumeroTarjeta.setText(tarjeta.getNumeroTarjeta());
		txtNumeroTarjeta.setEditable(false);
		comboTipoTarjeta.setSelectedItem(tarjeta.getTipo());
		txtLimiteCredito.setText(String.valueOf(tarjeta.getLimiteCredito()));
		txtSaldoUtilizado.setText(String.valueOf(tarjeta.getSaldoUtilizado()));
		txtUsuarioId.setText(String.valueOf(tarjeta.getUsuarioId()));
		txtUsuarioId.setEditable(false);
	}

	@Override
	protected void guardarAccion() {
		try {
			String nuevoTipoTarjeta = (String) comboTipoTarjeta.getSelectedItem();
			double nuevoLimiteCredito = Double.parseDouble(txtLimiteCredito.getText());
			double nuevoSaldoUtilizado = Double.parseDouble(txtSaldoUtilizado.getText());

			service.actualizarTarjeta(tarjeta.getId(), nuevoTipoTarjeta, nuevoLimiteCredito, nuevoSaldoUtilizado);

			JOptionPane.showMessageDialog(null, "Tarjeta actualizada exitosamente");
			panelManager.mostrarPantallaListadoTarjetas();
		} catch (TextoVacioException | TipoTarjetaInvalidaException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Límite de crédito o saldo utilizado inválidos. Deben ser números.",
					"Error de Validación", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error al actualizar la tarjeta: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected void cancelarAccion() {
		panelManager.mostrarPantallaListadoTarjetas();
	}
}