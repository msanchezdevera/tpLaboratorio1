package ui.tarjeta;

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

import exception.TarjetaServiceException;
import model.Tarjeta;
import model.Usuario;
import service.TarjetaService;
import ui.PanelManager;

public class PantallaListadoTarjetasPanel extends JPanel {

    private PanelManager panelManager;
    private JTable tableTarjetas;
    private TarjetaTableModel tableModel;

    private JButton btnAgregarTarjeta;
    private JButton btnModificarTarjeta;
    private JButton btnEliminarTarjeta;
    private JButton btnVolverInicio;

    private TarjetaService service;
    private Usuario usuario;

    public PantallaListadoTarjetasPanel(PanelManager panelManager, TarjetaService service, Usuario usuario) {
        this.panelManager = panelManager;
        this.service = service;
        this.usuario = usuario;
        armarPantallaListado();
    }

    private void armarPantallaListado() {
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Listado de Tarjetas");
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        obtenerTarjetas(lblTitulo);

        JPanel botonesPanel = new JPanel();

        if (usuario.esAdmin()) {
            btnAgregarTarjeta = new JButton("Agregar Tarjeta");
            btnAgregarTarjeta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panelManager.mostrarPantallaAltaTarjeta();
                }
            });
            botonesPanel.add(btnAgregarTarjeta);
        }

        btnModificarTarjeta = new JButton("Modificar Tarjeta");
        btnModificarTarjeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarTarjeta();
            }
        });
        botonesPanel.add(btnModificarTarjeta);

        btnEliminarTarjeta = new JButton("Eliminar Tarjeta");
        btnEliminarTarjeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarTarjeta();
            }
        });
        botonesPanel.add(btnEliminarTarjeta);

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

    private void obtenerTarjetas(JLabel lblTitulo) {
        try {
            List<Tarjeta> tarjetas;
            if (usuario.esAdmin()) {
                lblTitulo.setText("Listado de Tarjetas (Administrador)");
                tarjetas = service.obtenerTodasLasTarjetas();
            } else {
                lblTitulo.setText("Mis Tarjetas");
                tarjetas = service.obtenerTarjetasUsuario(usuario);
            }
            tableModel = new TarjetaTableModel(tarjetas);
            tableTarjetas = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(tableTarjetas);
            add(scrollPane, BorderLayout.CENTER);
        } catch (TarjetaServiceException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener tarjetas, pruebe más tarde: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarTabla() {
        try {
            List<Tarjeta> tarjetas;
            if (usuario.esAdmin()) {
                tarjetas = service.obtenerTodasLasTarjetas();
            } else {
                tarjetas = service.obtenerTarjetasUsuario(usuario);
            }
            tableModel.setTarjetas(tarjetas);
            tableModel.fireTableDataChanged();
        } catch (TarjetaServiceException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener tarjetas, pruebe más tarde: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarTarjeta() {
        int selectedRow = tableTarjetas.getSelectedRow();
        if (selectedRow != -1) {
            Tarjeta tarjeta = tableModel.getTarjetaAt(selectedRow);
            panelManager.mostrarPantallaModificarTarjeta(tarjeta);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una tarjeta para modificar.", "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarTarjeta() {
        int selectedRow = tableTarjetas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(PantallaListadoTarjetasPanel.this,
                    "Seleccione una tarjeta para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(PantallaListadoTarjetasPanel.this,
                "¿Está seguro de que desea eliminar la tarjeta seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int tarjetaId = (int) tableModel.getValueAt(selectedRow, 0);
                service.eliminarTarjeta(tarjetaId);
                tableModel.eliminarTarjeta(selectedRow);
            } catch (TarjetaServiceException ex) {
                JOptionPane.showMessageDialog(PantallaListadoTarjetasPanel.this,
                        "Error al eliminar la tarjeta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
