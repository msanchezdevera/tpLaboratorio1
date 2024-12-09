package ui.auditoria;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Movimiento;
import service.MovimientoService;
import ui.PanelManager;
import ui.movimiento.MovimientoTableModel;

public class PantallaAuditoriaPanel extends JPanel {

    private PanelManager panelManager;
    private MovimientoService movimientoService;
    private JTable tableMovimientos;
    private MovimientoTableModel tableModel;

    public PantallaAuditoriaPanel(PanelManager panelManager, MovimientoService movimientoService) {
        this.panelManager = panelManager;
        this.movimientoService = movimientoService;
        armarPantalla();
    }

    private void armarPantalla() {
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Reporte de Auditoría");
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);
        
        JPanel botonesPanel = new JPanel();
        
        JButton btnVolverInicio = new JButton("Volver a Inicio");
		btnVolverInicio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.mostrarPantallaHome();
			}
		});
		botonesPanel.add(btnVolverInicio);

		add(botonesPanel, BorderLayout.SOUTH);

        try {
            List<Movimiento> movimientos = movimientoService.obtenerMovimientosAuditoria();
            tableModel = new MovimientoTableModel(movimientos);
            tableMovimientos = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(tableMovimientos);
            add(scrollPane, BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel lblError = new JLabel("Error al cargar los datos de auditoría: " + e.getMessage());
            lblError.setHorizontalAlignment(JLabel.CENTER);
            add(lblError, BorderLayout.CENTER);
        }
    }
}