package ui.movimiento;

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

import exception.MovimientoServiceException;
import model.CuentaBancaria;
import model.Movimiento;
import service.MovimientoService;
import ui.PanelManager;

public class PantallaListadoMovimientosPanel extends JPanel {

	private PanelManager panelManager;
	private JTable tableMovimientos;
	private MovimientoTableModel tableModel;

	private JButton btnVolverListadoCuentas;

	private MovimientoService service;
	private CuentaBancaria cuentaOrigen;

	public PantallaListadoMovimientosPanel(PanelManager panelManager, MovimientoService service,
			CuentaBancaria cuentaOrigen) {
		this.panelManager = panelManager;
		this.service = service;
		this.cuentaOrigen = cuentaOrigen;
		armarPantallaListado();
	}

	private void armarPantallaListado() {
		setLayout(new BorderLayout());

		JLabel lblTitulo = new JLabel("Resumen de Movimientos de la Cuenta: " + cuentaOrigen.getNumeroCuenta());
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		add(lblTitulo, BorderLayout.NORTH);

		try {
			List<Movimiento> movimientos = service.obtenerMovimientosCuentaOrigen(cuentaOrigen.getId());
			tableModel = new MovimientoTableModel(movimientos);
			tableMovimientos = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(tableMovimientos);
			add(scrollPane, BorderLayout.CENTER);
		} catch (MovimientoServiceException e) {
			JOptionPane.showMessageDialog(this, "Error al obtener los movimientos, pruebe más tarde: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		JPanel botonesPanel = new JPanel();

		btnVolverListadoCuentas = new JButton("Volver al Listado de Cuentas");
		btnVolverListadoCuentas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaListadoCuentas();
			}
		});
		botonesPanel.add(btnVolverListadoCuentas);

		add(botonesPanel, BorderLayout.SOUTH);
	}

	public void actualizarTabla() {
		try {
			List<Movimiento> movimientos = service.obtenerMovimientosCuentaOrigen(cuentaOrigen.getId());
			tableModel.setMovimientos(movimientos);
			tableModel.fireTableDataChanged();
		} catch (MovimientoServiceException e) {
			JOptionPane.showMessageDialog(this, "Error al obtener los movimientos, pruebe más tarde: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}