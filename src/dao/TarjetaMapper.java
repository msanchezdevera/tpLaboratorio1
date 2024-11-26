package dao;

import model.Tarjeta;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TarjetaMapper implements DaoMapper<Tarjeta> {

    @Override
    public Tarjeta map(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String numeroTarjeta = resultSet.getString("numeroTarjeta");
        String tipo = resultSet.getString("tipo");
        double limiteCredito = resultSet.getDouble("limiteCredito");
        double saldoUtilizado = resultSet.getDouble("saldoUtilizado");
        int usuarioId = resultSet.getInt("usuarioId");

        try {
            return new Tarjeta(id, numeroTarjeta, tipo, limiteCredito, saldoUtilizado, usuarioId);
        } catch (Exception e) {
            throw new SQLException("Error al mapear el objeto Tarjeta", e);
        }
    }
}