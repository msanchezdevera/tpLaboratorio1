package dao;

import java.util.List;

import exception.DatabaseException;

public interface Dao<T> {
	T buscarPorId(int id) throws DatabaseException;

	List<T> listarTodos() throws DatabaseException;

	int insertar(T entidad) throws DatabaseException;

	void actualizar(T entidad) throws DatabaseException;

	void eliminar(int id) throws DatabaseException;
}
