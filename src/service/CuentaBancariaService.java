package service;

import java.util.List;

import dao.CuentaBancariaH2DAO;
import exception.CuentaBancariaNoEncontradaException;
import exception.DatabaseException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.ValorNoNumericoException;
import model.CuentaBancaria;
import validador.Validador;

public class CuentaBancariaService {

	private final CuentaBancariaH2DAO cuentaBancariaDAO;

	public CuentaBancariaService() {
		this.cuentaBancariaDAO = new CuentaBancariaH2DAO();
	}

	public CuentaBancaria crearCuentaBancaria(String numeroCuenta, double saldo, String tipoCuenta, int clienteId) throws DatabaseException, TipoCuentaBancariaInvalidaException, TextoVacioException, MenorACeroException, ValorNoNumericoException {
		Validador.validarTexto(numeroCuenta);
		Validador.validarEsNumero(numeroCuenta);
		Validador.validarNumeroMayorACero(saldo);
		Validador.validarTexto(tipoCuenta);

		CuentaBancaria cuenta = new CuentaBancaria(numeroCuenta, saldo, tipoCuenta, clienteId);
		cuentaBancariaDAO.insertar(cuenta);

		return cuenta;
	}
	public List<CuentaBancaria> obtenerTodasLasCuentas() throws DatabaseException {
		return cuentaBancariaDAO.listarTodos();
	}

	public CuentaBancaria obtenerCuentaPorId(int id) throws DatabaseException, CuentaBancariaNoEncontradaException {
		CuentaBancaria cuenta = cuentaBancariaDAO.buscarPorId(id);
		if (cuenta == null) {
			throw new CuentaBancariaNoEncontradaException("La cuenta bancaria con ID " + id + " no fue encontrada.");
		}

		return cuenta;
	}

	public void actualizarSaldoCuentaBancaria(int cuentaId, double nuevoSaldo) throws DatabaseException, MenorACeroException, CuentaBancariaNoEncontradaException {
		Validador.validarNumeroMayorACero(nuevoSaldo);
		CuentaBancaria cuenta = cuentaBancariaDAO.buscarPorId(cuentaId);
		if (cuenta != null) {
			cuenta.setSaldo(nuevoSaldo);
			cuentaBancariaDAO.actualizar(cuenta);
		} else {
			throw new CuentaBancariaNoEncontradaException("La cuenta bancaria con ID " + cuentaId + " no fue encontrada.");
		}
	}

	public void eliminarCuentaBancaria(int id) throws DatabaseException, CuentaBancariaNoEncontradaException {
		CuentaBancaria cuentaExistente = cuentaBancariaDAO.buscarPorId(id);
		if (cuentaExistente == null) {
			throw new CuentaBancariaNoEncontradaException("La cuenta bancaria con ID " + id + " no fue encontrada.");
		}

		cuentaBancariaDAO.eliminar(id);
	}
}
