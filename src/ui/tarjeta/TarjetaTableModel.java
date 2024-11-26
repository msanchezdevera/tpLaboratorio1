package ui.tarjeta;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Tarjeta;

public class TarjetaTableModel extends AbstractTableModel {

    private final String[] columnas = { "ID", "Número de Tarjeta", "Tipo", "Límite de Crédito", "Saldo Utilizado", "Saldo Disponible", "Usuario ID" };
    private List<Tarjeta> tarjetas;

    public TarjetaTableModel(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }

    @Override
    public int getRowCount() {
        return tarjetas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarjeta tarjeta = tarjetas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tarjeta.getId();
            case 1:
                return tarjeta.getNumeroTarjeta();
            case 2:
                return tarjeta.getTipo();
            case 3:
                return tarjeta.getLimiteCredito();
            case 4:
                return tarjeta.getSaldoUtilizado();
            case 5:
                return tarjeta.getSaldoDisponible();
            case 6:
                return tarjeta.getUsuarioId();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    public void setTarjetas(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public Tarjeta getTarjetaAt(int selectedRow) {
        return tarjetas.get(selectedRow);
    }

    public void eliminarTarjeta(int row) {
        tarjetas.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
