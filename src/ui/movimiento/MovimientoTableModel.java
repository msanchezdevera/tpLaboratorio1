package ui.movimiento;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Movimiento;
import java.text.SimpleDateFormat;

public class MovimientoTableModel extends AbstractTableModel {

	private List<Movimiento> movimientos;
	private final String[] columnas = { "ID", "Tipo", "Fecha", "Monto", "Descripción", "Saldo Previo",
			"Saldo Posterior" };
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public MovimientoTableModel(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	@Override
	public int getRowCount() {
		return movimientos.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnas[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Movimiento movimiento = movimientos.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return movimiento.getId();
		case 1:
			return movimiento.getTipo();
		case 2:
			return dateFormat.format(movimiento.getFecha());
		case 3:
			return movimiento.getMonto();
		case 4:
			return movimiento.getDescripcion();
		case 5:
			return movimiento.getSaldoPrevio();
		case 6:
			return movimiento.getSaldoPosterior();
		default:
			return null;
		}
	}

	public Movimiento getMovimientoAt(int rowIndex) {
		return movimientos.get(rowIndex);
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
}
