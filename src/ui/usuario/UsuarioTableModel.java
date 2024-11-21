package ui.usuario;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Usuario;

public class UsuarioTableModel extends AbstractTableModel {

    private final String[] columnas = { "ID", "Nombre", "Apellido", "Email", "Tipo de Usuario" };
    private List<Usuario> usuarios;

    public UsuarioTableModel(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = usuarios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return usuario.getId();
            case 1:
                return usuario.getNombre();
            case 2:
                return usuario.getApellido();
            case 3:
                return usuario.getEmail();
            case 4:
                return usuario.getTipoUsuario();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuarioAt(int selectedRow) {
        return usuarios.get(selectedRow);
    }

    public void eliminarUsuario(int row) {
        usuarios.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
