package ui.cuenta;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.CuentaBancaria;

public class CuentaBancariaTableModel extends AbstractTableModel {

    private final String[] columnas = { "ID", "Número de Cuenta", "Saldo", "Tipo de Cuenta", "Cliente ID", "CBU", "Alias" };
    private List<CuentaBancaria> cuentas;

    public CuentaBancariaTableModel(List<CuentaBancaria> cuentas) {
        this.cuentas = cuentas;
    }

    @Override
    public int getRowCount() {
        return cuentas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CuentaBancaria cuenta = cuentas.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return cuenta.getId();
        case 1:
            return cuenta.getNumeroCuenta();
        case 2:
            return cuenta.getSaldo();
        case 3:
            return cuenta.getTipoCuenta();
        case 4:
            return cuenta.getClienteId();
        case 5:
            return cuenta.getCbu();
        case 6:
            return cuenta.getAlias();
        default:
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    public void setCuentas(List<CuentaBancaria> cuentas) {
        this.cuentas = cuentas;
    }
    
    public CuentaBancaria getCuentaAt(int selectedRow) {
        return cuentas.get(selectedRow);
    }
    
    public void eliminarCuenta(int row) {
        cuentas.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
