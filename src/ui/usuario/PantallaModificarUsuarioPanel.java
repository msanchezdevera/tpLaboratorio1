package ui.usuario;

import javax.swing.JOptionPane;

import exception.TextoVacioException;
import exception.UsuarioNoEncontradoException;
import exception.UsuarioServiceException;
import model.Usuario;
import service.UsuarioService;
import ui.PanelManager;

public class PantallaModificarUsuarioPanel extends FormularioUsuarioPanel {

	private PanelManager panelManager;
	private UsuarioService service;
	private Usuario usuario;

	public PantallaModificarUsuarioPanel(PanelManager panelManager, UsuarioService service, Usuario usuario) {
		super("Modificar Usuario");
		this.panelManager = panelManager;
		this.service = service;
		this.usuario = usuario;

		txtNombre.setText(usuario.getNombre());
		txtApellido.setText(usuario.getApellido());
		txtEmail.setText(usuario.getEmail());
		txtContrasena.setText(usuario.getContrasena());
		comboTipoUsuario.setSelectedItem(usuario.getTipoUsuario());
	}

	@Override
	protected void guardarAccion() {
		try {
			service.actualizarUsuario(usuario.getId(), txtNombre.getText(), txtApellido.getText(), txtEmail.getText(),
					txtContrasena.getText(), (String) comboTipoUsuario.getSelectedItem());
			JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente");
			panelManager.mostrarPantallaListadoUsuarios();
		} catch (TextoVacioException | UsuarioServiceException | UsuarioNoEncontradoException ex) {
			JOptionPane.showMessageDialog(PantallaModificarUsuarioPanel.this,
					"Error al actualizar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(PantallaModificarUsuarioPanel.this,
					"Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected void cancelarAccion() {
		panelManager.mostrarPantallaListadoUsuarios();
	}
}
