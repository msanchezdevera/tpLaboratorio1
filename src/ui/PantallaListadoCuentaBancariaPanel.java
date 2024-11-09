package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import exception.CuentaBancariaServiceException;
import model.CuentaBancaria;
import service.CuentaBancariaService;

public class PantallaListadoCuentaBancariaPanel extends JPanel {
	private PanelManager panelManager;
	private JTable tableCuentas;
	private CuentaBancariaTableModel tableModel;
	private JButton btnAgregarCuenta;
	private JButton btnModificarCuenta;
	private CuentaBancariaService service;

	public PantallaListadoCuentaBancariaPanel(PanelManager panelManager, CuentaBancariaService service) {
		this.panelManager = panelManager;
		this.service = service;
		armarPantallaListado();
	}

	private void armarPantallaListado() {
		setLayout(new BorderLayout());

		try {
			List<CuentaBancaria> cuentas = service.obtenerTodasLasCuentas();
			tableModel = new CuentaBancariaTableModel(cuentas);
			tableCuentas = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(tableCuentas);
			add(scrollPane, BorderLayout.CENTER);
		} catch (CuentaBancariaServiceException e) {
			System.err.println("Error al obtener cuentas bancarias: " + e.getMessage());
		}

		JPanel botonesPanel = new JPanel();

		btnAgregarCuenta = new JButton("Agregar Cuenta");
		btnAgregarCuenta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaAltaCuenta();
			}
		});
		botonesPanel.add(btnAgregarCuenta);

		btnModificarCuenta = new JButton("Modificar Cuenta");
		btnModificarCuenta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificarCuentaBancaria();
			}
		});
		botonesPanel.add(btnModificarCuenta);

		add(botonesPanel, BorderLayout.SOUTH);
	}

	public void actualizarTabla() {
		try {
			List<CuentaBancaria> cuentas = service.obtenerTodasLasCuentas();
			tableModel.setCuentas(cuentas);
			tableModel.fireTableDataChanged();
		} catch (CuentaBancariaServiceException e) {
			JOptionPane.showMessageDialog(this, "Error al actualizar las cuentas bancarias: " + e.getMessage(), "Error",
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

}
