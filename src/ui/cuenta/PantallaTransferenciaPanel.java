package ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import model.CuentaBancaria;
import service.CuentaBancariaService;
import ui.PanelManager;
import exception.CuentaBancariaServiceException;

public class PantallaTransferenciaPanel extends JPanel {
    
	private PanelManager panelManager;
    private CuentaBancariaService service;
    private JComboBox<CuentaBancaria> comboCuentaOrigen;
    private JTextField txtCuentaDestino;
    private JTextField txtMonto;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    public PantallaTransferenciaPanel(PanelManager panelManager, CuentaBancariaService service) {
        this.panelManager = panelManager;
        this.service = service;
        armarFormulario();
    }

    private void armarFormulario() {
        setLayout(new GridLayout(5, 2));

        JLabel lblCuentaOrigen = new JLabel("Cuenta de Origen:");
        comboCuentaOrigen = new JComboBox<>();
        cargarCuentasOrigen();
        add(lblCuentaOrigen);
        add(comboCuentaOrigen);

        JLabel lblCuentaDestino = new JLabel("Cuenta de Destino (ID, CBU o Alias):");
        txtCuentaDestino = new JTextField();
        add(lblCuentaDestino);
        add(txtCuentaDestino);

        JLabel lblMonto = new JLabel("Monto a Transferir:");
        txtMonto = new JTextField();
        add(lblMonto);
        add(txtMonto);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarTransferencia();
            }
        });
        add(btnConfirmar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPantallaListadoCuentas();
            }
        });
        add(btnCancelar);
    }

    private void cargarCuentasOrigen() {
        try {
            List<CuentaBancaria> cuentas = service.obtenerTodasLasCuentas();
            for (CuentaBancaria cuenta : cuentas) {
                comboCuentaOrigen.addItem(cuenta);
            }
        } catch (CuentaBancariaServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las cuentas de origen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarTransferencia() {
        try {
            CuentaBancaria cuentaOrigen = (CuentaBancaria) comboCuentaOrigen.getSelectedItem();
            String cuentaDestinoInput = txtCuentaDestino.getText();
            double monto = Double.parseDouble(txtMonto.getText());

            if (cuentaOrigen != null) {
                service.realizarTransferencia(cuentaOrigen, cuentaDestinoInput, monto);
                JOptionPane.showMessageDialog(this, "Transferencia realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                panelManager.mostrarPantallaListadoCuentas();
            }
        } catch (CuentaBancariaServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al realizar la transferencia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El monto ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al realizar la transferencia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
