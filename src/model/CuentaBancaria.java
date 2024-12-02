package model;

import java.util.HashSet;
import java.util.Set;

import exception.MenorACeroException;
import exception.TipoCuentaBancariaInvalidaException;
import model.intereses.InteresFondoFimaStrategy;
import model.intereses.InteresStrategy;
import model.intereses.SinInteresStrategy;

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
	private String cbu;
	private String alias;
	private Usuario usuario;
	private Integer clienteId;
	private InteresStrategy interesStrategy;

	public CuentaBancaria(String numeroCuenta, double saldo, String tipoCuenta, String cbu, String alias,
			Usuario usuario) throws TipoCuentaBancariaInvalidaException {
		validarTipoCuenta(tipoCuenta);
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.tipoCuenta = tipoCuenta;
		this.cbu = cbu;
		this.alias = alias;
		this.cbu = cbu;
		this.alias = alias;
		this.usuario = usuario;
		asignarInteresStrategy();
	}

	public CuentaBancaria(int id, String numeroCuenta, double saldo, String tipoCuenta, String cbu, String alias,
			Usuario usuario) throws TipoCuentaBancariaInvalidaException {
		validarTipoCuenta(tipoCuenta);
		this.id = id;
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.tipoCuenta = tipoCuenta;
		this.cbu = cbu;
		this.alias = alias;
		this.usuario = usuario;
		asignarInteresStrategy();
	}

	public CuentaBancaria(int id, String numeroCuenta, double saldo, String tipoCuenta, String cbu, String alias,
			int clienteId) throws TipoCuentaBancariaInvalidaException {
		validarTipoCuenta(tipoCuenta);
		this.id = id;
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.tipoCuenta = tipoCuenta;
		this.cbu = cbu;
		this.alias = alias;
		this.setClienteId(clienteId);
		asignarInteresStrategy();
	}

	private void validarTipoCuenta(String tipoCuenta) throws TipoCuentaBancariaInvalidaException {
		if (!TIPOS_CUENTA_VALIDOS.contains(tipoCuenta)) {
			throw new TipoCuentaBancariaInvalidaException("Tipo de cuenta bancaria no válido: " + tipoCuenta);
		}
	}
	
	private void asignarInteresStrategy() {
		if(this.tipoCuenta == FONDO_FIMA) {
			this.interesStrategy = new InteresFondoFimaStrategy();
		} else {
			this.interesStrategy = new SinInteresStrategy();
		}
	}
	
	public double calcularInteres() {
        return interesStrategy.calcularInteres(this.saldo);
    }
	
	public void debitar(double monto) {
        this.saldo -= monto;
    }

    public void acreditar(double monto) {
        this.saldo += monto;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	@Override
	public String toString() {
		return "CuentaBancaria{id=" + id + ", numeroCuenta='" + numeroCuenta + "', saldo=" + saldo + ", tipoCuenta='"
				+ tipoCuenta + "', cbu='" + cbu + "', alias='" + alias + "', usuarioId="
				+ (usuario != null ? usuario.getId() : "null") + "}";
	}

}
