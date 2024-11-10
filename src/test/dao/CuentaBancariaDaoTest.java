package test.dao;

import java.util.List;

import dao.CuentaBancariaH2DAO;
import db.ConnectionManager;
import db.DatabaseSetup;
import exception.DatabaseException;
import exception.TipoCuentaBancariaInvalidaException;
import model.CuentaBancaria;

public class CuentaBancariaDaoTest {

	public static void main(String[] args) {
		try {
			DatabaseSetup.inicializarH2DB();
			DatabaseSetup.crearTablaCuentaBancaria();

			CuentaBancariaH2DAO cuentaDao = new CuentaBancariaH2DAO();

			CuentaBancaria cuenta1 = new CuentaBancaria("123456789", 1000.0, CuentaBancaria.CAJA_DE_AHORRO, 1, "99999",
					"alias1");
			CuentaBancaria cuenta2 = new CuentaBancaria("987654321", 1500.0, CuentaBancaria.CUENTA_CORRIENTE, 2,
					"88888", "alias2");
			cuentaDao.insertar(cuenta1);
			cuentaDao.insertar(cuenta2);

			System.out.println("ID de cuenta1 después de insertar: " + cuenta1.getId());
			System.out.println("ID de cuenta2 después de insertar: " + cuenta2.getId());

			System.out.println("Listar todas las cuentas bancarias:");
			List<CuentaBancaria> cuentas = cuentaDao.listarTodos();
			for (CuentaBancaria cuenta : cuentas) {
				System.out.println(cuenta);
			}

			System.out.println("Buscar cuenta bancaria con ID " + cuenta1.getId());
			CuentaBancaria cuentaEncontrada = cuentaDao.buscarPorId(cuenta1.getId());
			if (cuentaEncontrada != null) {
				System.out.println(cuentaEncontrada);
			} else {
				System.out.println("No se encontró la cuenta con ID " + cuenta1.getId());
			}

			System.out.println("Actualizar cuenta bancaria con ID " + cuenta1.getId());
			cuentaEncontrada.setSaldo(2000.0);
			cuentaDao.actualizar(cuentaEncontrada);
			CuentaBancaria cuentaActualizada = cuentaDao.buscarPorId(cuenta1.getId());
			System.out.println("Cuenta actualizada: " + cuentaActualizada);

			System.out.println("Eliminar cuentas bancarias");
			cuentaDao.eliminar(cuenta1.getId());
			cuentaDao.eliminar(cuenta2.getId());

			System.out.println("Listar todas las cuentas bancarias después de la eliminación:");
			cuentas = cuentaDao.listarTodos();
			for (CuentaBancaria cuenta : cuentas) {
				System.out.println(cuenta);
			}

		} catch (DatabaseException e) {
			System.err.println("Ocurrió un error de base de datos: " + e.getMessage());
		} catch (TipoCuentaBancariaInvalidaException e) {
			System.err.println("Cuenta bancaria invalida: " + e.getMessage());
		} finally {
			ConnectionManager.closeConnection();
		}
	}

}
