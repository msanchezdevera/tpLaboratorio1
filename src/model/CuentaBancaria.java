package model;

import java.util.HashSet;
import java.util.Set;

import exception.TipoCuentaBancariaInvalidaException;

public class CuentaBancaria {

    public static final String CAJA_DE_AHORRO = "Caja de Ahorro";
    public static final String CUENTA_CORRIENTE = "Cuenta Corriente";
    public static final String CAJA_DE_AHORRO_EN_DOLARES = "Caja de Ahorro en dolares";
    public static final String FONDO_FIMA = "Fondo Fima";

    public static final Set<String> TIPOS_CUENTA_VALIDOS = new HashSet<String>();

    static {
        TIPOS_CUENTA_VALIDOS.add(CAJA_DE_AHORRO);
        TIPOS_CUENTA_VALIDOS.add(CUENTA_CORRIENTE);
        TIPOS_CUENTA_VALIDOS.add(CAJA_DE_AHORRO_EN_DOLARES);
        TIPOS_CUENTA_VALIDOS.add(FONDO_FIMA);
    }

    private Integer id;
    private String numeroCuenta;
    private double saldo;
    private String tipoCuenta;
    private Integer clienteId;
    private String cbu;
    private String alias;

    public CuentaBancaria(String numeroCuenta, double saldo, String tipoCuenta, Integer clienteId, String cbu, String alias) throws TipoCuentaBancariaInvalidaException {
        validarTipoCuenta(tipoCuenta);
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
        this.clienteId = clienteId;
        this.cbu = cbu;
        this.alias = alias;
        this.cbu = cbu;
        this.alias = alias;
    }

    public CuentaBancaria(int id, String numeroCuenta, double saldo, String tipoCuenta, Integer clienteId, String cbu, String alias) throws TipoCuentaBancariaInvalidaException {
        validarTipoCuenta(tipoCuenta);
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
        this.clienteId = clienteId;
    }

    private void validarTipoCuenta(String tipoCuenta) throws TipoCuentaBancariaInvalidaException {
        if (!TIPOS_CUENTA_VALIDOS.contains(tipoCuenta)) {
            throw new TipoCuentaBancariaInvalidaException("Tipo de cuenta bancaria no válido: " + tipoCuenta);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{id='" + id + "',numeroCuenta='" + numeroCuenta + "', saldo=" + saldo + ", tipoCuenta='"
                + tipoCuenta + "', clienteId=" + clienteId + ", cbu='" + cbu + "', alias='" + alias + "'}";
    }

}
