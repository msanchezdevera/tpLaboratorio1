package service;

import java.util.Date;
import java.util.List;

import dao.CuentaBancariaH2DAO;
import dao.UsuarioH2DAO;
import exception.CuentaBancariaNoEncontradaException;
import exception.CuentaBancariaServiceException;
import exception.DatabaseException;
import exception.MenorACeroException;
import exception.MovimientoServiceException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.TipoMovimientoInvalidoException;
import exception.UsuarioNoEncontradoException;
import exception.ValorNoNumericoException;
import model.CuentaBancaria;
import model.Movimiento;
import model.Usuario;
import validador.Validador;

public class CuentaBancariaService {

	private final CuentaBancariaH2DAO cuentaBancariaDAO;
	private final UsuarioH2DAO usuarioDAO;
	private final MovimientoService movimientoService;

	public CuentaBancariaService(MovimientoService movimientoService) {
		this.cuentaBancariaDAO = new CuentaBancariaH2DAO();
		this.usuarioDAO = new UsuarioH2DAO();
		this.movimientoService = movimientoService;
	}

	public CuentaBancaria crearCuentaBancaria(String numeroCuenta, double saldo, String tipoCuenta, int usuarioId,
			String cbu, String alias)
			throws TipoCuentaBancariaInvalidaException, TextoVacioException, MenorACeroException,
			ValorNoNumericoException, CuentaBancariaServiceException, UsuarioNoEncontradoException {
		try {
			Validador.validarTexto(numeroCuenta);
			Validador.validarEsNumero(numeroCuenta);
			Validador.validarNumeroMayorACero(saldo);
			Validador.validarTexto(tipoCuenta);
			Validador.validarTexto(cbu);
			Validador.validarTexto(alias);

			Usuario usuario = usuarioDAO.buscarPorId(usuarioId);
			if (usuario == null) {
				throw new UsuarioNoEncontradoException("Usuario con ID " + usuarioId + " no encontrado.");
			}

			CuentaBancaria cuenta = new CuentaBancaria(numeroCuenta, saldo, tipoCuenta, cbu, alias, usuario);
			cuentaBancariaDAO.insertar(cuenta);

			return cuenta;
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al crear la cuenta bancaria", e);
		}
	}

	public List<CuentaBancaria> obtenerTodasLasCuentas() throws CuentaBancariaServiceException {
		try {
			List<CuentaBancaria> cuentas = cuentaBancariaDAO.listarTodos();
			for (CuentaBancaria cuenta : cuentas) {
				Usuario usuario = usuarioDAO.buscarPorId(cuenta.getClienteId());
				cuenta.setUsuario(usuario);
			}
			return cuentas;
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al obtener todas las cuentas bancarias", e);
		}
	}

	public List<CuentaBancaria> obtenerCuentasBancarias(Usuario usuario) throws CuentaBancariaServiceException {
		try {
			List<CuentaBancaria> cuentas = cuentaBancariaDAO.listarCuentasUsuario(usuario);
			for (CuentaBancaria cuenta : cuentas) {
				cuenta.setUsuario(usuario);
			}
			return cuentas;
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al obtener las cuentas bancarias", e);
		}
	}

	public CuentaBancaria obtenerCuentaPorId(int id)
			throws CuentaBancariaNoEncontradaException, CuentaBancariaServiceException {
		try {
			CuentaBancaria cuenta = cuentaBancariaDAO.buscarPorId(id);
			if (cuenta == null) {
				throw new CuentaBancariaNoEncontradaException(
						"La cuenta bancaria con ID " + id + " no fue encontrada.");
			}

			Usuario usuario = usuarioDAO.buscarPorId(cuenta.getClienteId());
			cuenta.setUsuario(usuario);

			return cuenta;
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al obtener la cuenta bancaria con ID " + id, e);
		}
	}

	public void actualizarCuentaBancaria(int cuentaId, double nuevoSaldo, String nuevoTipoCuenta, int nuevoUsuarioId,
			String nuevoCbu, String nuevoAlias)
			throws MenorACeroException, TextoVacioException, TipoCuentaBancariaInvalidaException,
			CuentaBancariaNoEncontradaException, CuentaBancariaServiceException, UsuarioNoEncontradoException {
		Validador.validarNumeroMayorACero(nuevoSaldo);
		Validador.validarTexto(nuevoTipoCuenta);
		Validador.validarTexto(nuevoCbu);
		Validador.validarTexto(nuevoAlias);

		try {
			CuentaBancaria cuenta = cuentaBancariaDAO.buscarPorId(cuentaId);
			if (cuenta == null) {
				throw new CuentaBancariaNoEncontradaException(
						"La cuenta bancaria con ID " + cuentaId + " no fue encontrada.");
			}

			Usuario nuevoUsuario = usuarioDAO.buscarPorId(nuevoUsuarioId);
			if (nuevoUsuario == null) {
				throw new UsuarioNoEncontradoException("Usuario con ID " + nuevoUsuarioId + " no encontrado.");
			}

			cuenta.setSaldo(nuevoSaldo);
			cuenta.setTipoCuenta(nuevoTipoCuenta);
			cuenta.setUsuario(nuevoUsuario);
			cuenta.setCbu(nuevoCbu);
			cuenta.setAlias(nuevoAlias);

			cuentaBancariaDAO.actualizar(cuenta);
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al actualizar la cuenta bancaria con ID " + cuentaId, e);
		}
	}

	public void eliminarCuentaBancaria(int id)
			throws CuentaBancariaNoEncontradaException, CuentaBancariaServiceException {
		try {
			CuentaBancaria cuentaExistente = cuentaBancariaDAO.buscarPorId(id);
			if (cuentaExistente == null) {
				throw new CuentaBancariaNoEncontradaException(
						"La cuenta bancaria con ID " + id + " no fue encontrada.");
			}
			cuentaBancariaDAO.eliminar(id);
		} catch (DatabaseException e) {
			throw new CuentaBancariaServiceException("Error al eliminar la cuenta bancaria con ID " + id, e);
		}
	}
	
	public void realizarTransferencia(CuentaBancaria cuentaOrigen, String cuentaDestinoInput, double monto)
            throws CuentaBancariaNoEncontradaException, CuentaBancariaServiceException, MenorACeroException {
        
        Validador.validarNumeroMayorACero(monto);

        try {
            CuentaBancaria cuentaDestino = cuentaBancariaDAO.buscarCuenta(cuentaDestinoInput);
            if (cuentaDestino == null) {
                throw new CuentaBancariaNoEncontradaException(
                        "La cuenta bancaria de destino " + cuentaDestinoInput + " no fue encontrada.");
            }

            if (cuentaOrigen.getSaldo() < monto) {
                throw new CuentaBancariaServiceException(
                        "La cuenta de origen no tiene suficiente saldo para realizar la transferencia.");
            }
            
            double cuentaOrigenSaldoOriginal = cuentaOrigen.getSaldo();
            double cuentaDestinoSaldoOriginal = cuentaDestino.getSaldo();

            cuentaOrigen.debitar(monto);
            cuentaDestino.acreditar(monto);
            
            cuentaBancariaDAO.actualizarSaldos(cuentaOrigen, cuentaDestino);

            registrarMovimientosTransferencia(cuentaOrigen, cuentaDestino, monto, cuentaOrigenSaldoOriginal, cuentaDestinoSaldoOriginal);
        } catch (DatabaseException e) {
            throw new CuentaBancariaServiceException("Error al realizar la transferencia", e);
        } catch (MovimientoServiceException | TipoMovimientoInvalidoException e) {
            throw new CuentaBancariaServiceException("Error al registrar los movimientos de la transferencia: " + e.getMessage(), e);
        }
    }
	
	private void registrarMovimientosTransferencia(CuentaBancaria cuentaOrigen, CuentaBancaria cuentaDestino, double monto, double cuentaOrigenSaldoOriginal, double cuentaDestinoSaldoOriginal)
            throws MovimientoServiceException, TipoMovimientoInvalidoException, MenorACeroException {
		// Movimiento de debito en la cuenta destino
        Movimiento movimientoDebito = new Movimiento(
                Movimiento.TIPO_TRANSFERENCIA_DEBITO,
                new Date(),
                monto,
                "Transferencia a cuenta destino " + cuentaDestino.getNumeroCuenta(),
                cuentaOrigen.getId(),
                null,
                cuentaOrigen.getClienteId(),
                cuentaOrigenSaldoOriginal,
                cuentaOrigen.getSaldo()           // Saldo posterior
        );

        // Movimiento de crédito en la cuenta destino
        Movimiento movimientoCredito = new Movimiento(
                Movimiento.TIPO_TRANSFERENCIA_CREDITO,
                new Date(),
                monto,
                "Transferencia recibida de cuenta origen " + cuentaOrigen.getNumeroCuenta(),
                cuentaDestino.getId(),
                null,
                cuentaOrigen.getClienteId(),
                cuentaDestinoSaldoOriginal,
                cuentaDestino.getSaldo()           // Saldo posterior
        );

        movimientoService.registrarMovimiento(movimientoDebito);
        movimientoService.registrarMovimiento(movimientoCredito);
    }
	
	public void generarInteresesParaCuentas() throws CuentaBancariaServiceException, TipoMovimientoInvalidoException, MenorACeroException {
        try {
            List<CuentaBancaria> todasLasCuentas = obtenerTodasLasCuentas();
            for (CuentaBancaria cuenta : todasLasCuentas) {
                double intereses = cuenta.calcularInteres();
                if (intereses > 0) {
                	double saldoPrevio = cuenta.getSaldo();
                    cuenta.acreditar(intereses);
                    cuentaBancariaDAO.actualizarSaldo(cuenta);

                    Movimiento movimiento = new Movimiento(
                            Movimiento.TIPO_ACREDITACION_INTERES,
                            new Date(),
                            intereses,
                            "Acreditación de intereses para " + cuenta.getTipoCuenta(),
                            cuenta.getId(),
                            null,
                            cuenta.getUsuario().getId(),
                            saldoPrevio,
                            cuenta.getSaldo()
                    );
                    movimientoService.registrarMovimiento(movimiento);
                }
            }
        } catch (DatabaseException | MovimientoServiceException e) {
            throw new CuentaBancariaServiceException("Error al generar intereses para las cuentas.", e);
        }
    }
	
}
