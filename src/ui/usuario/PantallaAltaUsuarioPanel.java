package ui.usuario;

import javax.swing.JOptionPane;

import exception.TextoVacioException;
import exception.UsuarioServiceException;
import service.UsuarioService;
import ui.PanelManager;

public class PantallaAltaUsuarioPanel extends FormularioUsuarioPanel {

	private PanelManager panelManager;
	private UsuarioService service;

	public PantallaAltaUsuarioPanel(PanelManager panelManager, UsuarioService service) {
		super("Crear Usuario");
		this.panelManager = panelManager;
		this.service = service;
	}

	@Override
	protected void guardarAccion() {
		try {
			String tipoUsuarioSeleccionado = (String) comboTipoUsuario.getSelectedItem();
			service.crearUsuario(txtNombre.getText(), txtApellido.getText(), txtEmail.getText(),
					txtContrasena.getText(), tipoUsuarioSeleccionado);

			JOptionPane.showMessageDialog(null, "Usuario guardado exitosamente");
			panelManager.mostrarPantallaListadoUsuarios();
		} catch (TextoVacioException | UsuarioServiceException ex) {
			JOptionPane.showMessageDialog(PantallaAltaUsuarioPanel.this,
					"Error al guardar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(PantallaAltaUsuarioPanel.this,
					"Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected void cancelarAccion() {
		panelManager.mostrarPantallaListadoUsuarios();
	}
}
