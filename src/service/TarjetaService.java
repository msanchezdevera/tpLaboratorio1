package service;

import dao.TarjetaH2DAO;
import exception.DatabaseException;
import exception.MenorACeroException;
import exception.MovimientoServiceException;
import exception.TarjetaServiceException;
import exception.TextoVacioException;
import exception.TipoTarjetaInvalidaException;
import exception.ValorNoNumericoException;
import model.Movimiento;
import model.Tarjeta;
import model.Usuario;
import validador.Validador;

import java.util.Date;
import java.util.List;

public class TarjetaService {

	private final TarjetaH2DAO tarjetaDAO;
	private final MovimientoService movimientoService;

	public TarjetaService(MovimientoService movimientoService) {
		this.tarjetaDAO = new TarjetaH2DAO();
		this.movimientoService = movimientoService;
	}

	public Tarjeta crearTarjeta(String numeroTarjeta, String tipo, double limiteCredito, double saldoUtilizado,
			int usuarioId) throws TextoVacioException, TipoTarjetaInvalidaException, TarjetaServiceException,
			MenorACeroException, ValorNoNumericoException {
		try {
			Validador.validarTexto(tipo);
			Validador.validarTexto(numeroTarjeta);
			Validador.validarNumeroMayorACero(limiteCredito);
			Validador.validarNumeroMayorACero(saldoUtilizado);

			Tarjeta tarjeta = new Tarjeta(numeroTarjeta, tipo, limiteCredito, saldoUtilizado, usuarioId);
			tarjetaDAO.insertar(tarjeta);
			return tarjeta;
		} catch (DatabaseException e) {
			throw new TarjetaServiceException("Error al crear la tarjeta");
		}
	}

	public List<Tarjeta> obtenerTodasLasTarjetas() throws TarjetaServiceException {
		try {
			return tarjetaDAO.listarTodos();
		} catch (DatabaseException e) {
			throw new TarjetaServiceException("Error al obtener todas las tarjetas");
		}
	}

	public List<Tarjeta> obtenerTarjetasUsuario(Usuario usuario) throws TarjetaServiceException {
		try {
			return tarjetaDAO.listarTarjetas(usuario);
		} catch (DatabaseException e) {
			throw new TarjetaServiceException("Error al obtener tarjetas para el usuario " + usuario.getId());
		}
	}

	public Tarjeta obtenerTarjetaPorId(int id) throws TarjetaServiceException {
		try {
			Tarjeta tarjeta = tarjetaDAO.buscarPorId(id);
			if (tarjeta == null) {
				throw new TarjetaServiceException("La tarjeta con ID " + id + " no fue encontrada.");
			}
			return tarjeta;
		} catch (DatabaseException e) {
			throw new TarjetaServiceException("Error al obtener la tarjeta con ID " + id);
		}
	}
	
	public void realizarDebitoTarjeta(Tarjeta tarjeta, double monto, String descripcion) throws TarjetaServiceException, MovimientoServiceException {
	    try {
	        if (monto <= 0 || monto > tarjeta.getSaldoDisponible()) {
	            throw new TarjetaServiceException("Monto inválido o excede el saldo disponible.");
	        }

	        double saldoPrevio = tarjeta.getSaldoUtilizado();
	        tarjeta.debitar(monto);

	        Movimiento movimiento = new Movimiento(
	            Movimiento.TIPO_PAGO_TARJETA,
	            new Date(),
	            monto,
	            descripcion,
	            null,
	            tarjeta.getId(),
	            tarjeta.getUsuarioId(),
	            saldoPrevio,
	            tarjeta.getSaldoUtilizado() // Saldo posterior
	        );

	        movimientoService.registrarMovimiento(movimiento);

	        tarjetaDAO.actualizar(tarjeta);
	    } catch (Exception e) {
	        throw new TarjetaServiceException("Error al realizar el débito de la tarjeta. " + e.getMessage());
	    }
	}

	public void actualizarTarjeta(int tarjetaId, String tipo, double limiteCredito, double saldoUtilizado)
			throws TextoVacioException, TipoTarjetaInvalidaException, TarjetaServiceException, MenorACeroException {
		Validador.validarTexto(tipo);
		Validador.validarNumeroMayorACero(limiteCredito);
		Validador.validarNumeroMayorACero(saldoUtilizado);

		try {
			Tarjeta tarjeta = tarjetaDAO.buscarPorId(tarjetaId);
			if (tarjeta != null) {
				tarjeta.setTipo(tipo);
				tarjeta.setLimiteCredito(limiteCredito);
				tarjeta.setSaldoUtilizado(saldoUtilizado);
				tarjetaDAO.actualizar(tarjeta);
			} else {
				throw new TarjetaServiceException("La tarjeta con ID " + tarjetaId + " no fue encontrada.");
			}
		} catch (DatabaseException e) {
			throw new TarjetaServiceException("Error al actualizar la tarjeta con ID " + tarjetaId);
		}
	}

	public void eliminarTarjeta(int id) throws TarjetaServiceException {
		try {
			Tarjeta tarjetaExistente = tarjetaDAO.buscarPorId(id);
			if (tarjetaExistente == null) {
				throw new TarjetaServiceException("La tarjeta con ID " + id + " no fue encontrada.");
			}
			tarjetaDAO.eliminar(id);
		} catch (DatabaseException e) {
			throw new TarjetaServiceException("Error al eliminar la tarjeta con ID " + id);
		}
	}
}
