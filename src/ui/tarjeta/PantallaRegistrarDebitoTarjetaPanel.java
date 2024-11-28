package ui.tarjeta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exception.MovimientoServiceException;
import exception.TarjetaServiceException;
import exception.MenorACeroException;
import model.Tarjeta;
import service.MovimientoService;
import service.TarjetaService;
import ui.PanelManager;

public class PantallaRegistrarDebitoTarjetaPanel extends JPanel {

    private PanelManager panelManager;
    private TarjetaService tarjetaService;

    private JTextField txtNroTarjeta;
    private JTextField txtMonto;
    private JTextField txtDescripcion;
    private JButton btnRegistrarDebito;
    private JButton btnCancelar;
    
    private Tarjeta tarjeta;

    public PantallaRegistrarDebitoTarjetaPanel(PanelManager panelManager, Tarjeta tarjeta, TarjetaService tarjetaService) {
        this.panelManager = panelManager;
        this.tarjeta = tarjeta;
        this.tarjetaService = tarjetaService;
        armarFormulario();
    }

    private void armarFormulario() {
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel lblNroTarjeta = new JLabel("Numero de la Tarjeta:");
        txtNroTarjeta = new JTextField();
        txtNroTarjeta.setText(tarjeta.getNumeroTarjeta());
        txtNroTarjeta.setEditable(false);
        add(lblNroTarjeta);
        add(txtNroTarjeta);

        JLabel lblMonto = new JLabel("Monto a Debitar:");
        txtMonto = new JTextField();
        add(lblMonto);
        add(txtMonto);

        JLabel lblDescripcion = new JLabel("Descripción:");
        txtDescripcion = new JTextField();
        add(lblDescripcion);
        add(txtDescripcion);

        btnRegistrarDebito = new JButton("Registrar Débito");
        btnRegistrarDebito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDebitoTarjeta();
            }
        });
        add(btnRegistrarDebito);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPantallaHome();
            }
        });
        add(btnCancelar);
    }

    private void registrarDebitoTarjeta() {
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            String descripcion = txtDescripcion.getText();

            tarjetaService.realizarDebitoTarjeta(tarjeta, monto, descripcion);
            
            JOptionPane.showMessageDialog(null, "Débito registrado exitosamente.");
            panelManager.mostrarPantallaHome();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID de la tarjeta y el monto deben ser valores numéricos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (TarjetaServiceException | MovimientoServiceException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar el débito: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}