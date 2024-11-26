package ui.cuenta;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import exception.CuentaBancariaNoEncontradaException;
import exception.CuentaBancariaServiceException;
import model.CuentaBancaria;
import model.Usuario;
import service.CuentaBancariaService;
import ui.PanelManager;

public class PantallaListadoCuentaBancariaPanel extends JPanel {

	private PanelManager panelManager;
	private JTable tableCuentas;
	private CuentaBancariaTableModel tableModel;

	private JButton btnAgregarCuenta;
	private JButton btnModificarCuenta;
	private JButton btnEliminarCuenta;
	private JButton btnTransferir;
	private JButton btnVerMovimientos;
	private JButton btnVolverInicio;

	private CuentaBancariaService service;
	private Usuario usuario;

	public PantallaListadoCuentaBancariaPanel(PanelManager panelManager, CuentaBancariaService service,
			Usuario usuario) {
		this.panelManager = panelManager;
		this.service = service;
		this.usuario = usuario;
		armarPantallaListado();
	}

	private void armarPantallaListado() {
		setLayout(new BorderLayout());

		JLabel lblTitulo = new JLabel("Listado de Cuentas Bancarias");
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		add(lblTitulo, BorderLayout.NORTH);

		obtenerCuentasBancarias(lblTitulo);

		JPanel botonesPanel = new JPanel();

		if (usuario.esAdmin()) {
			btnAgregarCuenta = new JButton("Agregar Cuenta");
			btnAgregarCuenta.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					panelManager.mostrarPantallaAltaCuenta();
				}
			});
			botonesPanel.add(btnAgregarCuenta);
		}

		btnModificarCuenta = new JButton("Modificar Cuenta");
		btnModificarCuenta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificarCuentaBancaria();
			}
		});
		botonesPanel.add(btnModificarCuenta);

		btnEliminarCuenta = new JButton("Eliminar Cuenta");
		btnEliminarCuenta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarCuenta();
			}
		});
		botonesPanel.add(btnEliminarCuenta);

		btnTransferir = new JButton("Transferir");
		btnTransferir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nuevaTransferencia();
			}
		});
		botonesPanel.add(btnTransferir);

		btnVerMovimientos = new JButton("Ver Movimientos");
		btnVerMovimientos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verMovimientosCuenta();
			}
		});
		botonesPanel.add(btnVerMovimientos);

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

	private void obtenerCuentasBancarias(JLabel lblTitulo) {
		try {
			List<CuentaBancaria> cuentas;
			if (usuario.esAdmin()) {
				lblTitulo.setText("Listado de Cuentas Bancarias (Administrador)");
				cuentas = service.obtenerTodasLasCuentas();
			} else {
				lblTitulo.setText("Mis Cuentas Bancarias");
				cuentas = service.obtenerCuentasBancarias(usuario);
			}
			tableModel = new CuentaBancariaTableModel(cuentas);
			tableCuentas = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(tableCuentas);
			add(scrollPane, BorderLayout.CENTER);
		} catch (CuentaBancariaServiceException e) {
			JOptionPane.showMessageDialog(this,
					"Error al obtener cuentas bancarias, pruebe más tarde: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actualizarTabla() {
		try {
			List<CuentaBancaria> cuentas;
			if (usuario.esAdmin()) {
				cuentas = service.obtenerTodasLasCuentas();
			} else {
				cuentas = service.obtenerCuentasBancarias(usuario);
			}
			tableModel.setCuentas(cuentas);
			tableModel.fireTableDataChanged();
		} catch (CuentaBancariaServiceException e) {
			JOptionPane.showMessageDialog(this,
					"Error al obtener cuentas bancarias, pruebe más tarde: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modificarCuentaBancaria() {
		int selectedRow = tableCuentas.getSelectedRow();
		if (selectedRow != -1) {
			CuentaBancaria cuenta = tableModel.getCuentaAt(selectedRow);
			panelManager.mostrarPantallaModificarCuenta(cuenta);
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione una cuenta para modificar.", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void eliminarCuenta() {
		int selectedRow = tableCuentas.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(PantallaListadoCuentaBancariaPanel.this,
					"Seleccione una cuenta para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(PantallaListadoCuentaBancariaPanel.this,
				"¿Está seguro de que desea eliminar la cuenta seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			try {
				int cuentaId = (int) tableModel.getValueAt(selectedRow, 0);
				service.eliminarCuentaBancaria(cuentaId);
				tableModel.eliminarCuenta(selectedRow);
			} catch (CuentaBancariaServiceException | CuentaBancariaNoEncontradaException ex) {
				JOptionPane.showMessageDialog(PantallaListadoCuentaBancariaPanel.this,
						"Error al eliminar la cuenta bancaria: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void nuevaTransferencia() {
		int selectedRow = tableCuentas.getSelectedRow();
		if (selectedRow != -1) {
			CuentaBancaria cuenta = tableModel.getCuentaAt(selectedRow);
			panelManager.mostrarPantallaTransferencia(cuenta);
		} else {
			JOptionPane.showMessageDialog(PantallaListadoCuentaBancariaPanel.this,
					"Debe seleccionar una cuenta para realizar la transferencia.", "Advertencia",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void verMovimientosCuenta() {
		int selectedRow = tableCuentas.getSelectedRow();
		if (selectedRow != -1) {
			CuentaBancaria cuenta = tableModel.getCuentaAt(selectedRow);
			panelManager.mostrarPantallaListadoMovimientos(cuenta);
		} else {
			JOptionPane.showMessageDialog(PantallaListadoCuentaBancariaPanel.this,
					"Debe seleccionar una cuenta para ver los movimientos.", "Advertencia",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}
