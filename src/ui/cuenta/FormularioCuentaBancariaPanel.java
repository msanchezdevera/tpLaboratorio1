package ui.cuenta;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.CuentaBancaria;

public abstract class FormularioCuentaBancariaPanel extends JPanel {
	
    protected JTextField txtNumeroCuenta;
    protected JTextField txtSaldo;
    protected JComboBox<String> comboTipoCuenta;
    protected JTextField txtClienteId;
    protected JTextField txtCbu;
    protected JTextField txtAlias;
    protected JButton btnGuardar;
    protected JButton btnCancelar;

    public FormularioCuentaBancariaPanel() {
        armarFormulario();
    }

    private void armarFormulario() {
        setLayout(new GridLayout(7, 2));

        JLabel lblNumeroCuenta = new JLabel("Número de Cuenta:");
        txtNumeroCuenta = new JTextField();
        add(lblNumeroCuenta);
        add(txtNumeroCuenta);

        JLabel lblSaldo = new JLabel("Saldo:");
        txtSaldo = new JTextField();
        add(lblSaldo);
        add(txtSaldo);

        JLabel lblTipoCuenta = new JLabel("Tipo de Cuenta:");
        String[] tiposCuenta = CuentaBancaria.TIPOS_CUENTA_VALIDOS.toArray(new String[0]);
        comboTipoCuenta = new JComboBox<>(tiposCuenta);
        add(lblTipoCuenta);
        add(comboTipoCuenta);

        JLabel lblClienteId = new JLabel("Cliente ID:");
        txtClienteId = new JTextField();
        add(lblClienteId);
        add(txtClienteId);

        JLabel lblCbu = new JLabel("CBU:");
        txtCbu = new JTextField();
        add(lblCbu);
        add(txtCbu);

        JLabel lblAlias = new JLabel("Alias:");
        txtAlias = new JTextField();
        add(lblAlias);
        add(txtAlias);

        btnGuardar = new JButton("Guardar");
        add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        add(btnCancelar);
    }

    public void setGuardarButtonListener(ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void setCancelarButtonListener(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }
}
