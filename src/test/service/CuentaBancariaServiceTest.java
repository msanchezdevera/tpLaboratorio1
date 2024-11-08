package test.service;

import java.util.List;

import db.DatabaseSetup;
import exception.CuentaBancariaNoEncontradaException;
import exception.CuentaBancariaServiceException;
import exception.DatabaseException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.ValorNoNumericoException;
import model.CuentaBancaria;
import service.CuentaBancariaService;

public class CuentaBancariaServiceTest {
    public static void main(String[] args) {
        try {
        	DatabaseSetup.inicializarH2DB();
			DatabaseSetup.crearTablaCuentaBancaria();
			
            CuentaBancariaService cuentaService = new CuentaBancariaService();

            System.out.println("Creando cuentas bancarias...");
            CuentaBancaria cuenta1 = cuentaService.crearCuentaBancaria("123456789", 1000.0, CuentaBancaria.CAJA_DE_AHORRO, 1);
            CuentaBancaria cuenta2 = cuentaService.crearCuentaBancaria("987654321", 1500.0, CuentaBancaria.CUENTA_CORRIENTE, 2);
            System.out.println("Cuentas creadas:");
            System.out.println(cuenta1);
            System.out.println(cuenta2);

            System.out.println("Obteniendo todas las cuentas bancarias...");
            List<CuentaBancaria> cuentas = cuentaService.obtenerTodasLasCuentas();
            for (CuentaBancaria cuenta : cuentas) {
                System.out.println(cuenta);
            }

            System.out.println("Obteniendo cuenta bancaria 1");
            CuentaBancaria cuentaEncontrada = cuentaService.obtenerCuentaPorId(cuenta1.getId());
            System.out.println("Cuenta encontrada: " + cuentaEncontrada);

            System.out.println("Actualizando saldo de la cuenta 1");
            cuentaService.actualizarSaldoCuentaBancaria(cuenta1.getId(), 2000.0);
            CuentaBancaria cuentaActualizada = cuentaService.obtenerCuentaPorId(cuenta1.getId());
            System.out.println("Cuenta actualizada: " + cuentaActualizada);

            System.out.println("Eliminando cuenta bancaria 1");
            cuentaService.eliminarCuentaBancaria(cuenta1.getId());
            System.out.println("Cuenta bancaria 1 eliminada.");

            System.out.println("Obteniendo todas las cuentas bancarias después de la eliminación");
            cuentas = cuentaService.obtenerTodasLasCuentas();
            for (CuentaBancaria cuenta : cuentas) {
                System.out.println(cuenta);
            }

        } catch (TextoVacioException | TipoCuentaBancariaInvalidaException | MenorACeroException | ValorNoNumericoException e) {
            System.err.println("Error de validación: " + e.getMessage());
        } catch (CuentaBancariaNoEncontradaException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (CuentaBancariaServiceException e) {
            System.err.println("Error de servicio: " + e.getMessage());
            e.printStackTrace();
        } catch (DatabaseException e) {
        	System.err.println("Error de base de datos: " + e.getMessage());
			e.printStackTrace();
		}
    }
}
