package service;

import java.util.List;

import dao.CuentaBancariaH2DAO;
import exception.CuentaBancariaNoEncontradaException;
import exception.DatabaseException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.ValorNoNumericoException;
import exception.CuentaBancariaServiceException;
import model.CuentaBancaria;
import validador.Validador;

public class CuentaBancariaService {

	private final CuentaBancariaH2DAO cuentaBancariaDAO;

	public CuentaBancariaService() {
		this.cuentaBancariaDAO = new CuentaBancariaH2DAO();
	}

	public CuentaBancaria crearCuentaBancaria(String numeroCuenta, double saldo, String tipoCuenta, int clienteId) throws TipoCuentaBancariaInvalidaException, TextoVacioException, MenorACeroException, ValorNoNumericoException, CuentaBancariaServiceException {
		try {
			Validador.validarTexto(numeroCuenta);
			Validador.validarEsNumero(numeroCuenta);
			Validador.validarNumeroMayorACero(saldo);
			Validador.validarTexto(tipoCuenta);

			CuentaBancaria cuenta = new CuentaBancaria(numeroCuenta, saldo, tipoCuenta, clienteId);
			cuentaBancariaDAO.insertar(cuenta);

			return cuenta;
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al crear la cuenta bancaria", e);
		}
	}

	public List<CuentaBancaria> obtenerTodasLasCuentas() throws CuentaBancariaServiceException {
		try {
			return cuentaBancariaDAO.listarTodos();
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al obtener todas las cuentas bancarias", e);
		}
	}

	public CuentaBancaria obtenerCuentaPorId(int id) throws CuentaBancariaNoEncontradaException, CuentaBancariaServiceException {
		try {
			CuentaBancaria cuenta = cuentaBancariaDAO.buscarPorId(id);
			if (cuenta == null) {
				throw new CuentaBancariaNoEncontradaException("La cuenta bancaria con ID " + id + " no fue encontrada.");
			}
			return cuenta;
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al obtener la cuenta bancaria con ID " + id, e);
		}
	}

	public void actualizarSaldoCuentaBancaria(int cuentaId, double nuevoSaldo) throws MenorACeroException, CuentaBancariaNoEncontradaException, CuentaBancariaServiceException {
		Validador.validarNumeroMayorACero(nuevoSaldo);
		try {
			CuentaBancaria cuenta = cuentaBancariaDAO.buscarPorId(cuentaId);
			if (cuenta != null) {
				cuenta.setSaldo(nuevoSaldo);
				cuentaBancariaDAO.actualizar(cuenta);
			} else {
				throw new CuentaBancariaNoEncontradaException("La cuenta bancaria con ID " + cuentaId + " no fue encontrada.");
			}
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al actualizar el saldo de la cuenta bancaria con ID " + cuentaId, e);
		}
	}

	public void eliminarCuentaBancaria(int id) throws CuentaBancariaNoEncontradaException, CuentaBancariaServiceException {
		try {
			CuentaBancaria cuentaExistente = cuentaBancariaDAO.buscarPorId(id);
			if (cuentaExistente == null) {
				throw new CuentaBancariaNoEncontradaException("La cuenta bancaria con ID " + id + " no fue encontrada.");
			}
			cuentaBancariaDAO.eliminar(id);
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al eliminar la cuenta bancaria con ID " + id, e);
		}
	}
}
