package ui.cuenta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import exception.CuentaBancariaNoEncontradaException;
import exception.CuentaBancariaServiceException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.ValorNoNumericoException;
import model.CuentaBancaria;
import service.CuentaBancariaService;
import ui.PanelManager;

public class PantallaModificarCuentaPanel extends FormularioCuentaBancariaPanel {

    private PanelManager panelManager;
    private CuentaBancariaService service;
    private CuentaBancaria cuenta;

    public PantallaModificarCuentaPanel(PanelManager panelManager, CuentaBancariaService service, CuentaBancaria cuenta) {
        this.panelManager = panelManager;
        this.service = service;
        this.cuenta = cuenta;

        txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
        txtNumeroCuenta.setEditable(false);
        txtSaldo.setText(String.valueOf(cuenta.getSaldo()));
        comboTipoCuenta.setSelectedItem(cuenta.getTipoCuenta());
        txtClienteId.setText(String.valueOf(cuenta.getClienteId()));
        txtClienteId.setEditable(false);
        txtCbu.setText(cuenta.getCbu());
        txtAlias.setText(cuenta.getAlias());

        setGuardarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCuentaBancaria();
            }
        });

        setCancelarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPantallaListadoCuentas();
            }
        });
    }

    private void actualizarCuentaBancaria() {
        try {
            double nuevoSaldo = Double.parseDouble(txtSaldo.getText());
            String nuevoTipoCuenta = (String) comboTipoCuenta.getSelectedItem();
            service.actualizarCuentaBancaria(cuenta.getId(), nuevoSaldo, nuevoTipoCuenta, txtCbu.getText(), txtAlias.getText());
            JOptionPane.showMessageDialog(null, "Cuenta actualizada exitosamente");
            panelManager.mostrarPantallaListadoCuentas();
        } catch (MenorACeroException | TextoVacioException | TipoCuentaBancariaInvalidaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
        } catch (CuentaBancariaNoEncontradaException | CuentaBancariaServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la cuenta bancaria: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Saldo inválido. Debe ser un número.", "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
