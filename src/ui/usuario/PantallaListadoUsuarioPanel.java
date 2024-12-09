package ui.usuario;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import exception.MovimientoServiceException;
import exception.UsuarioNoEncontradoException;
import exception.UsuarioServiceException;
import model.Usuario;
import service.MovimientoService;
import service.UsuarioService;
import javax.swing.JFileChooser;
import ui.PanelManager;

public class PantallaListadoUsuarioPanel extends JPanel {

	private PanelManager panelManager;
	private JTable tableUsuarios;
	private UsuarioTableModel tableModel;

	private JButton btnAgregarUsuario;
	private JButton btnModificarUsuario;
	private JButton btnEliminarUsuario;
	private JButton btnVolverInicio;

	private UsuarioService service;
	private MovimientoService movimientoService;

	public PantallaListadoUsuarioPanel(PanelManager panelManager, UsuarioService service, MovimientoService movimientoService) {
		this.panelManager = panelManager;
		this.service = service;
		this.movimientoService = movimientoService;
		armarPantallaListado();
	}

	private void armarPantallaListado() {
		setLayout(new BorderLayout());

		JLabel lblTitulo = new JLabel("Listado de Usuarios");
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		add(lblTitulo, BorderLayout.NORTH);

		try {
			List<Usuario> usuarios = service.obtenerTodosLosUsuarios();
			tableModel = new UsuarioTableModel(usuarios);
			tableUsuarios = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(tableUsuarios);
			add(scrollPane, BorderLayout.CENTER);
		} catch (UsuarioServiceException e) {
			JOptionPane.showMessageDialog(this, "Error al obtener usuarios, pruebe más tarde: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		JPanel botonesPanel = new JPanel();

		btnAgregarUsuario = new JButton("Agregar Usuario");
		btnAgregarUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaAltaUsuario();
			}
		});
		botonesPanel.add(btnAgregarUsuario);

		btnModificarUsuario = new JButton("Modificar Usuario");
		btnModificarUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificarUsuario();
			}
		});
		botonesPanel.add(btnModificarUsuario);

		btnEliminarUsuario = new JButton("Eliminar Usuario");
		btnEliminarUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarUsuario();
			}
		});
		botonesPanel.add(btnEliminarUsuario);
		
		JButton btnGenerarReporte = new JButton("Generar Reporte de Movimientos");
		btnGenerarReporte.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        generarReporteMovimientos();
		    }
		});
		botonesPanel.add(btnGenerarReporte);

		btnVolverInicio = new JButton("Volver a Inicio");
		btnVolverInicio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaHome();
			}
		});
		botonesPanel.add(btnVolverInicio);

		add(botonesPanel, BorderLayout.SOUTH);
	}

	public void actualizarTabla() {
		try {
			List<Usuario> usuarios = service.obtenerTodosLosUsuarios();
			tableModel.setUsuarios(usuarios);
			tableModel.fireTableDataChanged();
		} catch (UsuarioServiceException e) {
			JOptionPane.showMessageDialog(this, "Error al obtener usuarios, pruebe más tarde: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modificarUsuario() {
		int selectedRow = tableUsuarios.getSelectedRow();
		if (selectedRow != -1) {
			Usuario usuario = tableModel.getUsuarioAt(selectedRow);
			panelManager.mostrarPantallaModificarUsuario(usuario);
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione un usuario para modificar.", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void eliminarUsuario() {
		int selectedRow = tableUsuarios.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(PantallaListadoUsuarioPanel.this, "Seleccione un usuario para eliminar.",
					"Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(PantallaListadoUsuarioPanel.this,
				"¿Está seguro de que desea eliminar el usuario seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			try {
				int usuarioId = (int) tableModel.getValueAt(selectedRow, 0);
				service.eliminarUsuario(usuarioId);
				tableModel.eliminarUsuario(selectedRow);
			} catch (UsuarioServiceException | UsuarioNoEncontradoException ex) {
				JOptionPane.showMessageDialog(PantallaListadoUsuarioPanel.this,
						"Error al eliminar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void generarReporteMovimientos() {
		int selectedRow = tableUsuarios.getSelectedRow();
        if (selectedRow != -1) {
            Usuario usuario = tableModel.getUsuarioAt(selectedRow);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte de Movimientos");
            int userSelection = fileChooser.showSaveDialog(PantallaListadoUsuarioPanel.this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                try {
                    movimientoService.generarReporteMovimientosPorUsuario(usuario.getId(), archivo.getAbsolutePath());
                    JOptionPane.showMessageDialog(PantallaListadoUsuarioPanel.this, "Reporte generado exitosamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (MovimientoServiceException ex) {
                    JOptionPane.showMessageDialog(PantallaListadoUsuarioPanel.this,
                            "Error al generar el reporte de movimientos: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para generar el reporte.", "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
	}
}