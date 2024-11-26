package test;

import db.DatabaseSetup;
import exception.CuentaBancariaServiceException;
import exception.DatabaseException;
import exception.MenorACeroException;
import exception.TextoVacioException;
import exception.TipoCuentaBancariaInvalidaException;
import exception.TipoTarjetaInvalidaException;
import exception.UsuarioNoEncontradoException;
import exception.UsuarioServiceException;
import exception.ValorNoNumericoException;
import exception.TarjetaServiceException;
import model.CuentaBancaria;
import model.Usuario;
import model.Tarjeta;
import service.CuentaBancariaService;
import service.UsuarioService;
import service.TarjetaService;

public class CrearDatosIniciales {

    public static void main(String[] args) {
        try {
            DatabaseSetup.inicializarH2DB();
            DatabaseSetup.crearTablaCuentaBancaria();
            DatabaseSetup.crearTablaUsuario();
            DatabaseSetup.crearTablaTarjeta();

            CuentaBancariaService cuentaBancariaService = new CuentaBancariaService();
            UsuarioService usuarioService = new UsuarioService();
            TarjetaService tarjetaService = new TarjetaService();

            System.out.println("Creando usuarios iniciales...");

            Usuario usuario1 = usuarioService.crearUsuario("Juan", "Pérez", "juan@example.com", "password123",
                    Usuario.CLIENTE);
            Usuario usuario2 = usuarioService.crearUsuario("María", "González", "maria@example.com",
                    "password456", Usuario.CLIENTE);
            Usuario usuario3 = usuarioService.crearUsuario("Administrador", "Banco", "admin@admin.com", "admin",
                    Usuario.ADMIN);

            System.out.println("Usuarios creados exitosamente.");

            System.out.println("Creando cuentas bancarias iniciales...");

            cuentaBancariaService.crearCuentaBancaria("123456", 5000.0, CuentaBancaria.CAJA_DE_AHORRO, usuario1.getId(),
                    "CBU001", "Alias1");
            cuentaBancariaService.crearCuentaBancaria("654321", 10000.0, CuentaBancaria.CUENTA_CORRIENTE,
                    usuario2.getId(), "CBU002", "Alias2");
            cuentaBancariaService.crearCuentaBancaria("789123", 2000.0, CuentaBancaria.CAJA_DE_AHORRO_EN_DOLARES,
                    usuario3.getId(), "CBU003", "Alias3");
            cuentaBancariaService.crearCuentaBancaria("321789", 8000.0, CuentaBancaria.FONDO_FIMA, usuario1.getId(),
                    "CBU004", "Alias4");

            System.out.println("Cuentas bancarias creadas exitosamente.");

            System.out.println("Creando tarjetas iniciales...");

            tarjetaService.crearTarjeta("1111222233334444", Tarjeta.TIPO_CREDITO, 20000.0, 0.0, usuario1.getId());
            tarjetaService.crearTarjeta("5555666677778888", Tarjeta.TIPO_DEBITO, 10000.0, 0.0, usuario2.getId());
            tarjetaService.crearTarjeta("9999000011112222", Tarjeta.TIPO_CREDITO, 15000.0, 5000.0, usuario3.getId());

            System.out.println("Tarjetas creadas exitosamente.");

        } catch (UsuarioNoEncontradoException | DatabaseException | TipoCuentaBancariaInvalidaException
                | TextoVacioException | MenorACeroException | ValorNoNumericoException | CuentaBancariaServiceException
                | UsuarioServiceException | TarjetaServiceException | TipoTarjetaInvalidaException e) {
            System.err.println("Error al crear datos iniciales: " + e.getMessage());
        }
    }
}