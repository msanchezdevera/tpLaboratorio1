package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DaoMapper<T> {
	T map(ResultSet resultSet) throws SQLException;
}
