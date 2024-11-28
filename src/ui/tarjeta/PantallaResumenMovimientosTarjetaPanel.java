package ui.tarjeta;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.Movimiento;
import model.Tarjeta;
import service.MovimientoService;
import ui.PanelManager;
import ui.movimiento.MovimientoTableModel;

public class PantallaResumenMovimientosTarjetaPanel extends JPanel {

	private PanelManager panelManager;
	private MovimientoService movimientoService;

	private JComboBox<String> comboMes;
	private JTextField txtNroTarjeta;
	private JButton btnVerResumen;
	private JButton btnCancelar;
	private JTable tableMovimientos;
	private MovimientoTableModel tableModel;
	
	private Tarjeta tarjeta;

	public PantallaResumenMovimientosTarjetaPanel(PanelManager panelManager, Tarjeta tarjeta, MovimientoService movimientoService) {
		this.panelManager = panelManager;
		this.tarjeta = tarjeta;
		this.movimientoService = movimientoService;
		armarFormulario();
	}

	private void armarFormulario() {
		setLayout(new BorderLayout());

		JPanel formularioPanel = new JPanel(new GridLayout(3, 2, 10, 10));

		JLabel lblNroTarjeta = new JLabel("Numero de la Tarjeta:");
		txtNroTarjeta = new JTextField();
		txtNroTarjeta.setText(tarjeta.getNumeroTarjeta());
        txtNroTarjeta.setEditable(false);
		formularioPanel.add(lblNroTarjeta);
		formularioPanel.add(txtNroTarjeta);

		JLabel lblMes = new JLabel("Mes:");
		comboMes = new JComboBox<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
				"Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" });
		formularioPanel.add(lblMes);
		formularioPanel.add(comboMes);

		btnVerResumen = new JButton("Ver Resumen");
		btnVerResumen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verResumenMovimientos();
			}
		});
		formularioPanel.add(btnVerResumen);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaHome();
			}
		});
		formularioPanel.add(btnCancelar);

		add(formularioPanel, BorderLayout.NORTH);
	}

	private void verResumenMovimientos() {
		try {
			int mes = comboMes.getSelectedIndex() + 1;

			List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorTarjetaYMes(tarjeta.getId(), mes);
			if (movimientos.isEmpty()) {
				JOptionPane.showMessageDialog(this,
						"No se encontraron movimientos para la tarjeta en el mes seleccionado.", "Información",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			tableModel = new MovimientoTableModel(movimientos);
			tableMovimientos = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(tableMovimientos);
			add(scrollPane, BorderLayout.CENTER);
			revalidate();
			repaint();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El ID de la tarjeta debe ser un valor numérico.",
					"Error de Validación", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al obtener el resumen: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}