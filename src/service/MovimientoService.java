package service;

import java.util.Date;
import java.util.List;

import dao.CuentaBancariaH2DAO;
import dao.MovimientoH2DAO;
import dao.TarjetaH2DAO;
import dao.UsuarioH2DAO;
import exception.DatabaseException;
import exception.MenorACeroException;
import exception.MovimientoServiceException;
import model.CuentaBancaria;
import model.Movimiento;
import model.Tarjeta;
import model.Usuario;
import validador.Validador;

public class MovimientoService {

	private final MovimientoH2DAO movimientoDAO;
	private final CuentaBancariaH2DAO cuentaBancariaDAO;
	private final UsuarioH2DAO usuarioDAO;
	private final TarjetaH2DAO tarjetaDAO;

	public MovimientoService() {
		this.movimientoDAO = new MovimientoH2DAO();
		this.cuentaBancariaDAO = new CuentaBancariaH2DAO();
		this.usuarioDAO = new UsuarioH2DAO();
		this.tarjetaDAO = new TarjetaH2DAO();
	}

	public void registrarMovimiento(Movimiento movimiento) throws MovimientoServiceException, MenorACeroException {
		try {
			validarMovimiento(movimiento);
			movimientoDAO.insertar(movimiento);
		} catch (DatabaseException e) {
			throw new MovimientoServiceException("Error al registrar el movimiento. " + e.getMessage());
		}
	}
	
	public List<Movimiento> obtenerMovimientosPorTarjetaYMes(int tarjetaId, int mes) throws MovimientoServiceException {
        try {
            return movimientoDAO.buscarPorTarjetaYMes(tarjetaId, mes);
        } catch (DatabaseException e) {
            throw new MovimientoServiceException("Error al obtener movimientos por tarjeta y mes. " + e.getMessage());
        }
    }

	public List<Movimiento> obtenerMovimientosCuentaOrigen(int cuentaId) throws MovimientoServiceException {
		try {
			List<Movimiento> movimientos = movimientoDAO.listarPorCuenta(cuentaId);
			cargarEntidadesRelacionadas(movimientos);
			return movimientos;
		} catch (DatabaseException e) {
			throw new MovimientoServiceException("Error al obtener movimientos para la cuenta con ID: " + cuentaId);
		}
	}

	public List<Movimiento> obtenerMovimientosPorUsuario(int usuarioId) throws MovimientoServiceException {
		try {
			List<Movimiento> movimientos = movimientoDAO.listarPorUsuario(usuarioId);
			cargarEntidadesRelacionadas(movimientos);
			return movimientos;
		} catch (DatabaseException e) {
			throw new MovimientoServiceException("Error al obtener movimientos para el usuario con ID: " + usuarioId);
		}
	}

	public List<Movimiento> obtenerMovimientosPorTarjeta(int tarjetaId) throws MovimientoServiceException {
		try {
			List<Movimiento> movimientos = movimientoDAO.listarPorTarjeta(tarjetaId);
			cargarEntidadesRelacionadas(movimientos);
			return movimientos;
		} catch (DatabaseException e) {
			throw new MovimientoServiceException("Error al obtener movimientos para la tarjeta con ID: " + tarjetaId);
		}
	}

	private void validarMovimiento(Movimiento movimiento) throws MovimientoServiceException, MenorACeroException {
		Validador.validarNumeroMayorACero(movimiento.getMonto());
		if (movimiento.getCuentaId() == null && movimiento.getTarjetaId() == null) {
			throw new MovimientoServiceException("El movimiento debe estar asociado a una cuenta o a una tarjeta.");
		}
	}

	private void cargarEntidadesRelacionadas(List<Movimiento> movimientos) throws DatabaseException {
		for (Movimiento movimiento : movimientos) {
			if (movimiento.getCuentaId() != null) {
				movimiento.setCuenta(cuentaBancariaDAO.buscarPorId(movimiento.getCuentaId()));
			}
			if (movimiento.getTarjetaId() != null) {
				movimiento.setTarjeta(tarjetaDAO.buscarPorId(movimiento.getTarjetaId()));
			}
			if (movimiento.getUsuarioId() != null) {
				movimiento.setUsuario(usuarioDAO.buscarPorId(movimiento.getUsuarioId()));
			}
		}
	}
}